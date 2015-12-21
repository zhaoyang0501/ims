package com.hsae.ims.controller;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.config.TimeStampPropertyEditor;
import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkFlowDayoff;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.interceptor.AvoidDuplicateSubmission;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceDayOffService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowDayoffService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.SpringWorkflow;
/***
 * @author panchaoyang
 * @param <A>
 *
 */
@Controller
@RequestMapping("/workflow/dayoff")
public class WorkFlowDayoffController {
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private WorkFlowDayoffService workFlowDayoffService;
	@Autowired
	private AttenceDayOffService attenceDayOffService;
	@Autowired
	private  AttenceBrushRecordService attenceBrushRecordService;
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Timestamp.class, new TimeStampPropertyEditor("yyyy-MM-dd HH:mm"));  
	}  
	
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("workflow/dayoff/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "请假流程");
		breadCrumbMap.put("url", "workflow/dayoff/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	@AvoidDuplicateSubmission(needSaveToken = true)
	@RequestMapping("create")
	public ModelAndView create( ){
		ModelAndView mav = new ModelAndView("workflow/dayoff/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/dayoff", "请假申请"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/dayoff/create", "填写请假单"));
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("dayyOffTypes", codeService.findDayOffCode());
		mav.addObject("user", userService.findOne(RightUtil.getCurrentUserId()));
		return mav;
	}
	/***
	 * 查询我的报销记录
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param reimburseDate
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin,String end,Integer state ) throws ParseException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.addDays( DateUtils.parseDate(end, "yyyy-MM-dd"), 1);
		
		Page<WorkFlowDayoff> list = workFlowDayoffService.findAll(pageNumber, pageSize, 
				beginDate, endDate,state,RightUtil.getCurrentUserId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	/***
	 * 提交请假单
	 * @param reimburse
	 * @param isSend
	 * @param userIds
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	@AvoidDuplicateSubmission(needRemoveToken = true)
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String save(WorkFlowDayoff workFlowDayoff,Long leader,RedirectAttributes redirectAttributes) throws ParseException, UnsupportedEncodingException{
		User user=userService.findOne(RightUtil.getCurrentUserId());
		workFlowDayoff.setUser(user);
		workFlowDayoff.setDayoffDate(workFlowDayoff.getStartTime());
		workFlowDayoff.setSaveTime(new Timestamp(System.currentTimeMillis()));
		workFlowDayoffService.save(workFlowDayoff);
		/**初始化一个工作流实例**/
		Long workFlowid=null;
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("creater",user.getId());
		argMap.put("dept",user.getDept().getId());
		//找部门经理
		User manager=userService.findManagerByDept(user.getDept().getId());
		if(manager==null){
			redirectAttributes.addFlashAttribute("response",new FailedResponse("300","找不到部门经理，流程无法继续了！"));
		    return  "redirect:/workflow/dayoff/create";
		}
			
		argMap.put("nextowner", manager.getId());
		argMap.put("leader",leader);
		
		try {
			springWorkflow.SetContext(String.valueOf( user.getId()));
			workFlowid=springWorkflow.initialize(WorkFlowConstants.WORLFLOW_DAYOFF, 10, argMap);
			/**保存工作流相关信息*/
			Wfentry wfentry=workFlowService.findWfentry(workFlowid);
			WfentryExtend wfentryExtend=new WfentryExtend();
			wfentryExtend.setCreater(user);
			wfentryExtend.setTitle(user.getChinesename()+"请假单");
			wfentryExtend.setWfentry(wfentry);
			wfentryExtend.setSn(workFlowIdGeneratorService.getDayOffWorkFlowId());
			workFlowService.saveWfentryExtend(wfentryExtend);
			/**提交到产品经理*/
			springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
			if(leader!=null) 
				springWorkflow.getPropertySet(workFlowid).setLong("leader",leader);
		} catch (WorkflowException e) {
			redirectAttributes.addFlashAttribute("response",new FailedResponse("300","工作流异常"+e.getMessage()));
		    return  "redirect:/workflow/dayoff/create";
		}
		/**将工作流属性关联到报销*/
		workFlowDayoff.setWfentry(workFlowService.findWfentry(workFlowid));
		workFlowDayoffService.save(workFlowDayoff);
		
		User currentUser=workFlowService.getCurrentstepUser(workFlowDayoff.getWfentry());
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
	    return  "redirect:/workflow/dayoff/create";
	}
	/***
	 * 报销审批动作
	 * @param wfentryId
	 * @param actionId
	 * @param approvals
	 * @param cash
	 * @return
	 * @throws WorkflowException
	 */
	@RequestMapping(value="/doApprove/{wfentryId}", method = RequestMethod.POST)
	public String doApprove(@PathVariable Long wfentryId, Integer  actionId,String approvals,AttenceDayoff attenceDayoff,RedirectAttributes redirectAttributes) throws WorkflowException{
		/**工作流引擎开始工作*/
		springWorkflow.SetContext(String.valueOf( RightUtil.getCurrentUserId()));
		Wfentry wfentry=workFlowService.findWfentry(wfentryId);
		WorkFlowDayoff workFlowDayoff=workFlowDayoffService.findByWfentry(wfentry);
		
		Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
		osworkflowArgsMap.put("workFlowDayoff", workFlowDayoffService.findByWfentry(wfentry));
		osworkflowArgsMap.put("creater", wfentry.getWfentryExtend().getCreater().getId());
		
		Long lead=springWorkflow.getPropertySet(wfentryId).getLong("leader");
		if(lead==0)
			lead=null;
		osworkflowArgsMap.put("leader", lead);
		/**产品经理审核 找部门经理*/
		User user=workFlowDayoff.getUser();
		User manager=userService.findManagerByDept(user.getDept().getId());
		if(manager==null){
			redirectAttributes.addFlashAttribute("response",new FailedResponse("400","找不到部门经理，流程无法继续"));
			return  "redirect:/workflow/toapprove/goApprove/"+wfentryId;
		}
		Step step=(Step)springWorkflow.getCurrentSteps(wfentryId).get(0);
		if(step.getStepId()<=2){
			osworkflowArgsMap.put("nextowner",manager.getId());
		}
		/**人事点击处理按钮**/
		if(step.getStepId()==6&&actionId==61){
			/**保存请假单，修改考勤状态*/
			attenceDayoff.setDayoffDate(DateUtils.truncate(attenceDayoff.getStartTime(), Calendar.DAY_OF_MONTH) );
			attenceDayoff.setUser(workFlowDayoff.getUser());
			attenceDayoff.setSaveTime(new Timestamp(System.currentTimeMillis()));
			attenceDayoff.setDayoffType(workFlowDayoff.getDayoffType());
			attenceDayoff.setRemark(workFlowDayoff.getRemark());
			attenceDayOffService.save(attenceDayoff);
			
			List<AttenceBrushRecord> attenceBrushRecords= attenceBrushRecordService.
					findBrushRecord(DateUtils.truncate(attenceDayoff.getStartTime(), Calendar.DAY_OF_MONTH),
							DateUtils.truncate(attenceDayoff.getEndTime(), Calendar.DAY_OF_MONTH), workFlowDayoff.getUser().getId());
			for(AttenceBrushRecord attenceBrushRecord:attenceBrushRecords){
				attenceBrushRecord.setState("20");
				attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
			}
			
		}
		springWorkflow.doAction(wfentryId, actionId, osworkflowArgsMap);
		User currentUser=workFlowService.getCurrentstepUser(wfentry);
		/**保存审批信息**/
		workFlowService.saveApproval(approvals, step.getId(),
				springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_DAYOFF).getAction(actionId).getName());
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
		return  "redirect:/workflow/toapprove/goApprove/"+wfentryId;
	}
	
	/**报销单删除,如果已经进入流程，不允许删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{dayoffId}", method=RequestMethod.GET)
	@ResponseBody
	public Response delete(@PathVariable Long dayoffId){
		/**判断是否进入了流程，进入流程不允许删除*/
		WorkFlowDayoff workFlowDayoff = workFlowDayoffService.find(dayoffId);
		if(workFlowDayoff==null){
			return new FailedResponse("400","该请假单不存在");
		}
	
		Wfentry wfentry= workFlowDayoff.getWfentry();
		/**只能删除自己创建的工作流*/
		if(wfentry.getWfentryExtend()!=null&&
				!wfentry.getWfentryExtend().getCreater().equals(userService.findOne(RightUtil.getCurrentUserId()))){
			return new FailedResponse("400","只能删除自己创建的流程！");
		}
		
		if(wfentry!=null&&wfentry.getState()==WorkflowEntry.COMPLETED){
			return new FailedResponse("400","此请假单已经结束不允许删除！");
		}
		
		if(wfentry!=null&&wfentry.getCurrentStep().getStepId()>3){
			return new FailedResponse("400","此请假单据已进入流程，不允许删除");
		}
		/**如果进入了工作流状态，kill it*/
		if(wfentry!=null){
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				return new FailedResponse("400","工作流异常！"+e.getMessage());
			}
		}
		workFlowDayoffService.delete(workFlowDayoff);
		return new SuccessResponse("操作成功！");
	}
	/***
	 * 面包屑
	 * @param url
	 * @param name
	 * @return
	 */
	private Map<String,String> getBreadCrumbMap(String url,String name){
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", name);
		breadCrumbMap.put("url", url);
		return breadCrumbMap;
	}
}
