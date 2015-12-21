package com.hsae.ims.controller;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.OverTimeMaxHour;
import com.hsae.ims.dto.WorkflowOverTimeDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkFlowOverTime;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkFlowOverTimeService;
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
@RequestMapping("/workflow/overtime")
public class WorkFlowOverTimeController {
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
	private WorkFlowOverTimeService workFlowOverTimeService;
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}  
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("workflow/overtime/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "加班流程");
		breadCrumbMap.put("url", "workflow/overtime/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	@RequestMapping("create")
	public ModelAndView create( ){
		ModelAndView mav = new ModelAndView("workflow/overtime/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "加班流程");
		breadCrumbMap.put("url", "workflow/overtime");
		
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "填写加班申请单");
		breadCrumbMap1.put("url", "workflow/overtime/create");
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("user", userService.findOne(RightUtil.getCurrentUserId()));
		return mav;
	}
	/***
	 * 查询加班申请列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param begin
	 * @param end
	 * @param state
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin, String end, Integer state) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.addDays( DateUtils.parseDate(end, "yyyy-MM-dd"), 1);
		Page<WorkFlowOverTime> page = workFlowOverTimeService.findAll(pageNumber, pageSize, beginDate, endDate, state,RightUtil.getCurrentUserId());
		
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/**
	 * 流程提交
	 * @param absentee
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public Response submit(@RequestBody WorkflowOverTimeDTO workflowOverTimeDTO,RedirectAttributes redirectAttributes){
		User user=userService.findOne(RightUtil.getCurrentUserId());
		User manager=userService.findManagerByDept(user.getDept().getId());
		if(manager==null)
			return new FailedResponse("400","找不到部门经理 流程无法继续");
		if(manager.getId().equals(user.getId()))
			/**朱工*/
			manager=userService.findOne(32);
		/**组装请假单bean*/
		WorkFlowOverTime workFlowOverTime =new WorkFlowOverTime();
		workFlowOverTime.setUser(user);
		workFlowOverTime.setSaveTime(new Timestamp(System.currentTimeMillis()));
		workFlowOverTime.setWorkFlowOverTimeDetail(workflowOverTimeDTO.getWorkFlowOverTimeDetails());
		workFlowOverTime.setApplyDate(new Date());
		workFlowOverTimeService.save(workFlowOverTime);
		/**初始化一个工作流实例**/
		Long workFlowid=null;
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("creater",user.getId());
		argMap.put("nextowner", manager.getId());
		argMap.put("leader",workflowOverTimeDTO.getLeader());
		try {
			springWorkflow.SetContext(String.valueOf( user.getId()));
			workFlowid=springWorkflow.initialize(WorkFlowConstants.WORLFLOW_OVERTIME, 10, argMap);
			/**保存工作流相关信息*/
			if(workflowOverTimeDTO.getLeader()!=null)
				springWorkflow.getPropertySet(workFlowid).setLong("leader", workflowOverTimeDTO.getLeader());
			Wfentry wfentry=workFlowService.findWfentry(workFlowid);
			WfentryExtend wfentryExtend=new WfentryExtend();
			wfentryExtend.setCreater(user);
			wfentryExtend.setTitle(user.getChinesename()+"加班申请单");
			wfentryExtend.setSn(workFlowIdGeneratorService.getOverTimeWorkFlowId());
			wfentryExtend.setWfentry(wfentry);
			workFlowService.saveWfentryExtend(wfentryExtend);
			/**提交工作流*/
			springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		/**将工作流属性关联到报销*/
		workFlowOverTime.setWfentry(workFlowService.findWfentry(workFlowid));
		workFlowOverTimeService.save(workFlowOverTime);
		
		ModelAndView mav = new ModelAndView("workflow/overtime/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "加班流程");
		breadCrumbMap.put("url", "workflow/overtime");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("response",new SuccessResponse());
		mav.addObject("breadcrumb", breadCrumbList);
		User currentUser=workFlowService.getCurrentstepUser(workFlowOverTime.getWfentry());
		return new SuccessResponse("提交成功！流程已经提交给"+currentUser.getChinesename());
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
	public String doApprove(@PathVariable Long wfentryId, Integer  actionId,String approvals,RedirectAttributes redirectAttributes) throws WorkflowException{
		/**工作流引擎开始工作*/
		springWorkflow.SetContext(String.valueOf( RightUtil.getCurrentUserId()));
		Wfentry wfentry=workFlowService.findWfentry(wfentryId);
		WorkFlowOverTime workFlowOverTime = workFlowOverTimeService.findByWfentry(wfentry);
		Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
		osworkflowArgsMap.put("creater", wfentry.getWfentryExtend().getCreater().getId());
		Step step=(Step)springWorkflow.getCurrentSteps(wfentryId).get(0);
		osworkflowArgsMap.put("leader",springWorkflow.getPropertySet(wfentryId).getLong("leader"));
		/**产品经理审核*/
		if(step.getStepId()<=2){
			User user=workFlowOverTime.getUser();
			User manager=userService.findManagerByDept(user.getDept().getId());
			if(manager==null){
				redirectAttributes.addFlashAttribute("response",new FailedResponse("400","找不到部门经理，流程无法继续"));
				return  "redirect:/workflow/toapprove/goApprove/"+wfentryId;
			}
			/*如果自己就是部门主管设置他的部门主管为朱新军**/
			if(user.getId().equals(manager.getId()))
				osworkflowArgsMap.put("nextowner",32);
			else
				osworkflowArgsMap.put("nextowner",manager.getId());
		}
		springWorkflow.doAction(wfentryId, actionId, osworkflowArgsMap);
		/**保存审批信息**/
		workFlowService.saveApproval(approvals, step.getId(),
				springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_OVERTIME).getAction(actionId).getName());
		User currentUser=workFlowService.getCurrentstepUser(wfentry);
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
		
		return  "redirect:/workflow/toapprove/goApprove/"+wfentryId;
	}
	/**报销单删除,如果已经进入流程，不允许删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{overtimeId}", method=RequestMethod.GET)
	@ResponseBody
	public Response delete(@PathVariable Long overtimeId){
		/**判断是否进入了流程，进入流程不允许删除*/
		WorkFlowOverTime workFlowOverTime = workFlowOverTimeService.findOne(overtimeId);
		if(workFlowOverTime==null){
			return new FailedResponse("400","该加班申请单不存在");
		}
		
		/** 如果报销处于工时核对或者财务审核状态不允许删除*/
		Wfentry wfentry= workFlowOverTime.getWfentry();
		/**只能删除自己创建的工作流*/
		if(wfentry.getWfentryExtend()!=null&&
				!wfentry.getWfentryExtend().getCreater().equals(userService.findOne(RightUtil.getCurrentUserId()))){
			return new FailedResponse("400","只能删除自己创建的流程！");
		}
		if(wfentry!=null&&wfentry.getState()==WorkflowEntry.COMPLETED){
			return new FailedResponse("400","此加班单已经结束不允许删除！");
		}
		
		if(wfentry!=null&&wfentry.getCurrentStep().getStepId()>=2){
			return new FailedResponse("400", "此报销单据已进入流程，不允许删除！");
		}
		/**如果进入了工作流状态，kill it*/
		if(wfentry!=null){
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				e.printStackTrace();
				return new FailedResponse("400", "工作流异常！"+e.getMessage());
			}
		}
		workFlowOverTimeService.delete(overtimeId);
		return new SuccessResponse("删除成功！");
	}
	@RequestMapping(value = "/getOverTimeMaxHour", method=RequestMethod.POST)
	@ResponseBody
	public Response getOverTimeMaxHour(Long userid,Date curDate){
		return new  ObjectResponse<OverTimeMaxHour>(workFlowOverTimeService.findMaxOverTime(userid, curDate));
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
