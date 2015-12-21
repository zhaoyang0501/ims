package com.hsae.ims.controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.interceptor.AvoidDuplicateSubmission;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.SpringWorkflow;
@Controller
@RequestMapping("/workflow/reimburse")
public class WorkFlowReimburseController {
	@Autowired
	private UserService userService;

	@Autowired
	private ReimburseService reimburseService;
	
	@Autowired
	private SpringWorkflow springWorkflow;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
	      CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);  
	      binder.registerCustomEditor(Date.class, dateEditor);  
	}  
	
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("workflow/reimburse/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销");
		breadCrumbMap.put("url", "workflow/reimburse/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	@AvoidDuplicateSubmission(needSaveToken = true)
	@RequestMapping("create")
	public ModelAndView create( ){
		ModelAndView mav = new ModelAndView("workflow/reimburse/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(getBreadCrumbMap("workflow/reimburse","餐费报销流程"));
		breadCrumbList.add(getBreadCrumbMap("workflow/reimburse/create","填写报销单"));
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("user", userService.findOne(RightUtil.getCurrentUserId()));
		return mav;
	}
	/***
	 * 提交表单
	 * @param reimburse
	 * @param isSend
	 * @param userIds
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	@AvoidDuplicateSubmission(needRemoveToken = true)
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@Transactional(propagation=Propagation.REQUIRED)
	public ModelAndView save(Reimburse reimburse,String userIds) throws ParseException, UnsupportedEncodingException{
		Reimburse reimburseToSave=reimburse;
		reimburseToSave.setStep("未提交");
		reimburseToSave.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		User user=userService.findOne(RightUtil.getCurrentUserId());
		reimburseToSave.setReimburser(user);
		reimburseService.save(reimburseToSave,userIds);
		/**初始化一个工作流实例**/
		Long workFlowid=null;
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("creater",user.getId());
		argMap.put("caller",userService.findOne(user.getId()));
		argMap.put("reimburseId", reimburseToSave.getId());
		try {
			springWorkflow.SetContext(String.valueOf( user.getId()));
			workFlowid=springWorkflow.initialize(WorkFlowConstants.REIMBURSE, 10, argMap);
			WfentryExtend wfentryExtend=new WfentryExtend();
			wfentryExtend.setCreater(user);
			wfentryExtend.setTitle(user.getChinesename()+"的报销单");
			wfentryExtend.setWfentry(workFlowService.findWfentry(workFlowid));
			wfentryExtend.setSn(workFlowIdGeneratorService.getReimburseRepositoryWorkFlowId());
			workFlowService.saveWfentryExtend(wfentryExtend);
			springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		/**将工作流属性关联到报销*/
		@SuppressWarnings("unchecked")
		List<Step> steps = springWorkflow.getCurrentSteps(workFlowid);
		if(steps!=null&steps.size()!=0){
			reimburseToSave.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(steps.get(0).getStepId()).getName());
			workFlowService.saveApproval("同意", steps.get(0).getId());
		}
		reimburseToSave.setState(springWorkflow.getEntryState(workFlowid));
		reimburseToSave.setWfentry(workFlowService.findWfentry(workFlowid));
		reimburseService.save(reimburseToSave);
		ModelAndView mav = new ModelAndView("workflow/reimburse/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(getBreadCrumbMap("workflow/overtime","餐费报销流程"));
		breadCrumbList.add(getBreadCrumbMap("workflow/overtime/create","填写报销单"));
		mav.addObject("breadcrumb", breadCrumbList);
		
		User currentUser=workFlowService.getCurrentstepUser(reimburseToSave.getWfentry());
		mav.addObject("response",new ObjectResponse<User>(currentUser));
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
	public Map<String, Object> queryList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin,String end,Integer state ) throws ParseException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.addDays( DateUtils.parseDate(end, "yyyy-MM-dd"), 1);
		
		Page<Reimburse> list = reimburseService.findAll(pageNumber, pageSize, 
				beginDate, endDate,state,RightUtil.getCurrentUserId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	/**报销单删除,如果已经进入流程，不允许删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{reimburseId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long reimburseId){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", "success");
		map.put("msg", "删除成功！");
		/**判断是否进入了流程，进入流程不允许删除*/
		Reimburse reimburse = reimburseService.findOne(reimburseId);
		if(reimburse==null){
			map.put("state", "error");
			map.put("msg", "不存在此报销单据！");
			return map;
		}
		/** 如果报销处于工时核对或者财务审核状态不允许删除*/
		Wfentry wfentry= reimburse.getWfentry();
		/**只能删除自己创建的工作流*/
		if(wfentry.getWfentryExtend()!=null&&
				!wfentry.getWfentryExtend().getCreater().equals(userService.findOne(RightUtil.getCurrentUserId()))){
			map.put("state", "error");
			map.put("msg", "只能删除自己创建的流程！");
			return map;
		}
		if(wfentry!=null&&wfentry.getState()==WorkflowEntry.COMPLETED){
			map.put("state", "error");
			map.put("msg", "此报销单已经结束不允许删除！");
			return map;
		}
		
		if(wfentry!=null&&wfentry.getCurrentStep().getStepId()>2){
			map.put("state", "error");
			map.put("msg", "此报销单据已进入流程，不允许删除！");
			return map;
		}
		
		/**如果进入了工作流状态，kill it*/
		if(wfentry!=null){
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				map.put("state", "error");
				map.put("msg", "工作流异常！"+e.getMessage());
				e.printStackTrace();
				return map;
			}
		}
		reimburseService.delete(reimburseId);
		return map;
	}
	/***
	 * 报销单审批动作
	 * @param wfentryId
	 * @param actionId
	 * @param approvals
	 * @param cash
	 * @param redirectAttributes
	 * @return
	 * @throws WorkflowException
	 */
	@RequestMapping(value="/doApprove/{wfentryId}", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public String doApproveReimburse(@PathVariable Long wfentryId, Integer  actionId,String approvals,Double cash,RedirectAttributes redirectAttributes) throws WorkflowException{
		/**TODO 检查工作流id是否本人合法*/
		/**保存审批意见*/
		/**执行成功，调整到我的审批页面**/
		ModelAndView model = new ModelAndView("/goApprove/");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("approve/myapprove","我的待办"));
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		model.addObject("response",new SuccessResponse());
		/**工作流引擎开始工作*/
		Long userid= RightUtil.getCurrentUserId();
		springWorkflow.SetContext(String.valueOf(userid));
		Reimburse reimburse = reimburseService.findByWfentry(workFlowService.findWfentry(wfentryId));
		Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
		osworkflowArgsMap.put("creater",userid);
		osworkflowArgsMap.put("reimburseUser",reimburse.getReimburser().getId());
		osworkflowArgsMap.put("reimburseId", reimburse.getId());
		Step step=(Step)springWorkflow.getCurrentSteps(wfentryId).get(0);
		Assert.notNull(step);
		springWorkflow.doAction(wfentryId, actionId, osworkflowArgsMap);
		workFlowService.saveApproval(approvals, step.getId(),
				springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getAction(actionId).getName());
		/**执行完成并回写状态step等*/
		
		List<Step> steps = springWorkflow.getCurrentSteps(wfentryId);
		if(steps!=null&steps.size()!=0)
			reimburse.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(steps.get(0).getStepId()).getName());
		else{
			List<Step> historySteps = springWorkflow.getHistorySteps(wfentryId);
			if(historySteps!=null&historySteps.size()!=0)
				reimburse.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(historySteps.get(0).getStepId()).getName());
		}
			
		reimburse.setState(springWorkflow.getEntryState(wfentryId));
		reimburse.setWfentry(workFlowService.findWfentry(wfentryId));
		/**第四部付款保存付款金额**/
		if(springWorkflow.getEntryState(wfentryId)==4&&step.getStepId()==4){
			reimburse.setPayMoneyDate(new Date(System.currentTimeMillis()));
			reimburse.setActualMoney(cash);
		}
		reimburseService.save(reimburse);
		
		User currentUser=workFlowService.getCurrentstepUser(reimburse.getWfentry());
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
		return  "redirect:/workflow/toapprove/goApprove/"+wfentryId;
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
