package com.hsae.ims.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.dto.ReimburseMyapproveDTO;
import com.hsae.ims.dto.StepDTO;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseCustomerDetail;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.entity.WorkFlowDayoff;
import com.hsae.ims.entity.WorkFlowOverTime;
import com.hsae.ims.entity.WorkFlowOverTimeDetail;
import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.WorkflowAbsentee;
import com.hsae.ims.entity.WorkflowAway;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.HistoryStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.UserResumeService;
import com.hsae.ims.service.WorkFlowTrainingRequireGatherService;
import com.hsae.ims.service.WorkflowAbsenteeService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWorkStageService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.service.WorkFlowDayoffService;
import com.hsae.ims.service.WorkFlowOverTimeService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.service.WorkflowAwayService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;
/***
 * 工作流审批
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/workflow")
public class WorkFlowApproveController {
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private UserService userService;
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private	ReimburseService  reimburseService;
	@Autowired
	private WeekReportService weekReportService;
	@Autowired
	private DailyReportService dailyReportService;
	
	@Autowired
	private CodeService codeService;
	@Autowired
	private DailyReportWorkStageService dailyReportWorkStageService;
	
	@Autowired
	private WorkFlowDayoffService workFlowDayoffService;
	
	@Autowired
	private WorkflowAbsenteeService workflowAbsenteeService;
	
	@Autowired
	private WorkFlowOverTimeService workFlowOverTimeService;
	
	@Autowired
	private WorkflowAwayService workflowAwayService;
	
	@Autowired
	private WorkFlowTrainingRequireGatherService workFlowTrainingRequireGatherService;
	
	@Autowired
	private AttenceOverTimeService attenceOverTimeService;
	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	@Autowired
	private UserResumeService userResumeService;
	
	/**工作流XML文件*/
	private Map<String,String> workflowXmlMap=new HashMap<String,String>();
	/**工作流布局文件文件*/
	private Map<String,String> workflowLayOutXmlMap=new HashMap<String,String>();
	
	/***
	 * 设置工作流XML文件的字符串表现形式
	 * 读文件比较耗资源，只读取一次
	 */
	@PostConstruct
	public void readWorkFlowXMLFile(){
		try {
			workflowXmlMap.put(WorkFlowConstants.WORLFLOW_DAYOFF,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.WORLFLOW_DAYOFF));
			workflowXmlMap.put(WorkFlowConstants.WORLFLOW_AWAY,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.WORLFLOW_AWAY));
			workflowXmlMap.put(WorkFlowConstants.WORLFLOW_OVERTIME,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.WORLFLOW_OVERTIME));			
			workflowXmlMap.put(WorkFlowConstants.ABSENTEE,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.ABSENTEE));			
			workflowXmlMap.put(WorkFlowConstants.REIMBURSE,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.REIMBURSE));			
			workflowXmlMap.put(WorkFlowConstants.DAILY_REPORT,workFlowService.getWorkflowXMlAsString(WorkFlowConstants.DAILY_REPORT));			
			workflowLayOutXmlMap.put(WorkFlowConstants.WORLFLOW_DAYOFF,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.WORLFLOW_DAYOFF));
			workflowLayOutXmlMap.put(WorkFlowConstants.ABSENTEE,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.ABSENTEE));
			workflowLayOutXmlMap.put(WorkFlowConstants.WORLFLOW_OVERTIME,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.WORLFLOW_OVERTIME));
			workflowLayOutXmlMap.put(WorkFlowConstants.WORLFLOW_AWAY,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.WORLFLOW_AWAY));
			workflowLayOutXmlMap.put(WorkFlowConstants.REIMBURSE,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.REIMBURSE));
			workflowLayOutXmlMap.put(WorkFlowConstants.DAILY_REPORT,workFlowService.getWorkflowXMlLayOutAsString(WorkFlowConstants.DAILY_REPORT));
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
	}
	/***
	 * 跳转到已办事项
	 * @return
	 */
	@RequestMapping("/approved")
	public ModelAndView approved() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("workflow/approved","已办事项"));
		ModelAndView model = new ModelAndView("workflow/approved/index");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		return model;
	}
	/***
	 * 跳转到待办事项
	 * @return
	 */
	@RequestMapping("/toapprove")
	public ModelAndView toapprove() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("workflow/toapprove","待办事项"));
		ModelAndView model = new ModelAndView("workflow/toapprove/index");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		return model;
	}
	/***
	 * 待办事项列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param workFlowName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws WorkflowException
	 * @throws ParseException
	 */
	@RequestMapping(value="/toApproveList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toApproveList(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String workFlowName,
			String startDate,
			String endDate,
			Long craterId,
			String sn
			) throws WorkflowException, ParseException{
		Date start = startDate==null?null:DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end= endDate==null?null:DateUtils.parseDate(endDate, "yyyy-MM-dd");
		User creater = craterId==null?null:userService.findOne(craterId);
		Page<CurrentStep> currentSteps = 
				workFlowService.findTodoList(workFlowName, start, end,
							userService.findOne(RightUtil.getCurrentUserId()),creater, sn, (iDisplayStart / iDisplayLength) + 1, iDisplayLength);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", currentSteps.getContent());
		map.put("iTotalRecords", currentSteps.getTotalElements());
		map.put("iTotalDisplayRecords", currentSteps.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 已办事项列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param workFlowName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws WorkflowException
	 * @throws ParseException
	 */
	@RequestMapping(value="/approvedList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> approvedList(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String workFlowName,
			String startDate,
			String endDate,
			Integer workflowState,
			Long craterId,
			String sn
			) throws WorkflowException, ParseException{
		Date start = startDate==null?null:DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end= endDate==null?null:DateUtils.parseDate(endDate, "yyyy-MM-dd");
		User creater = craterId==null?null:userService.findOne(craterId);
		Page<HistoryStep> historySteps = 
				workFlowService.findDoneList(workFlowName, start, end,workflowState,
							userService.findOne(RightUtil.getCurrentUserId()), sn,creater,(iDisplayStart / iDisplayLength) + 1, iDisplayLength);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", historySteps.getContent());
		map.put("iTotalRecords", historySteps.getTotalElements());
		map.put("iTotalDisplayRecords", historySteps.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 跳转到单个审批页面
	 * @param weekreportid
	 * @return
	 */
	@RequestMapping(value={"/toapprove/goApprove/{wfentryId}","/approved/goApprove/{wfentryId}"})
	public  ModelAndView goApprove(@PathVariable Long wfentryId,@ModelAttribute("tip") String tip,HttpServletRequest request){
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();

		ModelAndView modelAndView= new ModelAndView();
		modelAndView.addObject("tip", tip);
		
		Wfentry wfentry = workFlowService.findWfentry(wfentryId);
		/**面包屑链接，是待办还是已办*/
		String url=request.getRequestURI();
		String workFlowTypeUrl="";
		if(url.indexOf("approved")!=-1){
			workFlowTypeUrl="approved";
			breadCrumbList.add(this.getBreadCrumbMap("workflow/approved", "我的已办"));
		}
		if(url.indexOf("toapprove")!=-1){
			workFlowTypeUrl="toapprove";
			breadCrumbList.add(this.getBreadCrumbMap("workflow/toapprove", "我的待办"));
		}
		
		if(wfentry.getState()==3)
			throw new RuntimeException("该流程已经被作废或者不存在！");
		
		springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
		if(WorkFlowConstants.DAILY_REPORT.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "周报审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveWeekReport(wfentryId,modelAndView);
		}
		
		else if(WorkFlowConstants.REIMBURSE.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "餐费报销审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveReimburse(wfentry,modelAndView);
		}
		
		else if(ImsConstants.WorkFlowConstants.WORLFLOW_DAYOFF.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "请假单审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveDayoff(wfentry,modelAndView);
		}
		else if(ImsConstants.WorkFlowConstants.WORLFLOW_OVERTIME.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "加班审批流程"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveOverTime(wfentry,modelAndView);
		}
		
		else if(ImsConstants.WorkFlowConstants.ABSENTEE.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "补打卡申请流程审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveAbsentee(wfentry,modelAndView);
		}
		
		else if(ImsConstants.WorkFlowConstants.WORLFLOW_AWAY.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/"+workFlowTypeUrl+"/goApprove/"+wfentryId, "外出申请流程审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveAway(wfentry,modelAndView);
		}
		
		else if(ImsConstants.WorkFlowConstants.TRAINING_REQUIRE_GATHER.equals(wfentry.getName())){
			breadCrumbList.add(getBreadCrumbMap("workflow/toApprove/"+wfentryId, "培训需求收集流程审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			this.approveTrainingRequireGather(wfentry,modelAndView);
		}
		return modelAndView;
	}
	
	
	/***
	 * 查看某个流程下的流程图
	 * @param workflowId
	 * @return
	 */
	@RequestMapping(value="/workflowgraph/{workflowId}")
	public ModelAndView workflowGraph(@PathVariable Long workflowId){
		ModelAndView modelAndView = new ModelAndView("approve/myapprove/workflowgraph");
		springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
		Wfentry wfentry=workFlowService.findWfentry(workflowId);
		Assert.notNull(wfentry);
		/**取流程文件以及流程布局文件*/
		modelAndView.addObject("workflowXml",this.workflowXmlMap.get(wfentry.getName()));
		modelAndView.addObject("workflowLayOutXml",this.workflowLayOutXmlMap.get(wfentry.getName()));
		List<StepDTO> historyStep=this.workFlowService.findHistoryStepsDTO(workFlowService.findWfentry(workflowId));
		List<StepDTO> currentStep=this.workFlowService.findCurrentStepsDTO(workFlowService.findWfentry(workflowId));
		modelAndView.addObject("historyStep",historyStep);
		modelAndView.addObject("currentStep",currentStep);
		/**计算历史步骤以及当前步骤字符串**/
		String historystepStr="",currentStepStr="";
		for(int i=0;i<historyStep.size();i++){
			if(i==historyStep.size()-1)
				 historystepStr+=historyStep.get(i).getStepid();
			else 
				historystepStr+=historyStep.get(i).getStepid()+",";
		}
		for(int i=0;i<currentStep.size();i++){
			if(i==currentStep.size()-1)
				currentStepStr+=currentStep.get(i).getStepid();
			else 
				currentStepStr+=currentStep.get(i).getStepid()+",";
		}
		modelAndView.addObject("currentStepStr",currentStepStr);
		modelAndView.addObject("historyStepStr",historystepStr);
		
		return modelAndView;
	}
	//~~~~~~~~~~~private~~~~~~~~~~~~~~~~~~~~~~~~~
	/***
	 * 调转到周报审批页面
	 * @param wfentryId
	 * @param modelAndView
	 * @return
	 */
	private ModelAndView approveWeekReport(Long wfentryId, ModelAndView  modelAndView){
		
		WeekReport weekReport = weekReportService.findByOsworkflow(wfentryId);
		Assert.notNull(weekReport);
		List<DailyReport> dailyReports = dailyReportService.findDailyReport(weekReport.getWeek().getStartDate(),
				weekReport.getWeek().getEndDate(), weekReport.getCreater());
		Map<String,String> dailyType = codeService.findDailyTypeCode();
		for(DailyReport bean:dailyReports ){
			bean.setType(dailyType.get(bean.getType()));
		}
		modelAndView.setViewName("approve/myapprove/weekreport");
		/**算总工时*/
		float totalHours=0;
		for(DailyReport bean :dailyReports){
			totalHours+=bean.getSpendHours();
		}
		//modelAndView.addObject("tip", tip);
		modelAndView.addObject("totalHours", totalHours);
		modelAndView.addObject("dailyReports", dailyReports);
		modelAndView.addObject("weekReport", weekReport);
		modelAndView.addObject("workSteps", dailyReportWorkStageService.findWorkStage());
		
		/***查可执行动作 TODO 可以抽象成方法*/
		int[] actionIds = springWorkflow.getAvailableActions(weekReport.getOsworkflow(), null);
		Map<Integer,String>	aciton=new HashMap<Integer,String>();
		for(int i=0;i<actionIds.length;i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionIds[i]).getName());
		}
		modelAndView.addObject("acitons",aciton);
		
		return modelAndView;
	}
	/***
	 * 调转到请假审批页面
	 * @param wfentryId
	 * @param modelAndView
	 * @return
	 */
	private void approveDayoff(Wfentry wfentry, ModelAndView  modelAndView){
		WorkFlowDayoff workFlowDayoff=workFlowDayoffService.findByWfentry(wfentry);
		modelAndView.setViewName("workflow/dayoff/goapprove");
		
		/***查可执行动作 TODO 可以抽象成方法*/
		int[] actionIds = springWorkflow.getAvailableActions(wfentry.getId(), null);
		Map<Integer,String>	aciton=new LinkedHashMap<Integer,String>();
		for(int i=0;i<actionIds.length;i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_DAYOFF).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_DAYOFF).getAction(actionIds[i]).getName());
		}
		modelAndView.addObject("dayyOffTypes", codeService.findDayOffCode());
		modelAndView.addObject("acitons",aciton);
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
		
		modelAndView.addObject("approval1",workFlowService.findSingleApproval(wfentry.getId(), 2));
		modelAndView.addObject("approval2",workFlowService.findSingleApproval(wfentry.getId(), 3));
		modelAndView.addObject("approval3",workFlowService.findSingleApproval(wfentry.getId(), 4));
		modelAndView.addObject("approval4",workFlowService.findSingleApproval(wfentry.getId(), 6));
		Long leadderid=springWorkflow.getPropertySet(wfentry.getId()).getLong("leader");
		modelAndView.addObject("leader",userService.findOne(leadderid));
		modelAndView.addObject("workFlowDayoff",workFlowDayoff);
	}
	/***
	 * 调转到请假审批页面
	 * @param wfentryId
	 * @param modelAndView
	 * @return
	 */
	private void approveOverTime(Wfentry wfentry, ModelAndView  modelAndView){
		WorkFlowOverTime workFlowOverTime=workFlowOverTimeService.findByWfentry(wfentry);
		for(WorkFlowOverTimeDetail bean:workFlowOverTime.getWorkFlowOverTimeDetail()){
			UserResume userResume=userResumeService.findByUser(bean.getUser());
			if(userResume!=null)
				bean.setPosition(codeService.findPositionName(userResume.getPosition().toString()));
		}
		modelAndView.setViewName("workflow/overtime/goapprove");
		
		/***查可执行动作 TODO 可以抽象成方法*/
		int[] actionIds = springWorkflow.getAvailableActions(wfentry.getId(), null);
		Map<Integer,String>	aciton=new LinkedHashMap<Integer,String>();
		for(int i=0;i<actionIds.length;i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_OVERTIME).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_OVERTIME).getAction(actionIds[i]).getName());
		}
		modelAndView.addObject("dayyOffTypes", codeService.findDayOffCode());
		modelAndView.addObject("acitons",aciton);
		modelAndView.addObject("leader",springWorkflow.getPropertySet(wfentry.getId()).getLong("leader"));
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
		modelAndView.addObject("workFlowOverTime",workFlowOverTime);
		
		modelAndView.addObject("approval2",workFlowService.findSingleApproval(wfentry.getId(), 2));
		modelAndView.addObject("approval3",workFlowService.findSingleApproval(wfentry.getId(), 3));
		modelAndView.addObject("approval6",workFlowService.findSingleApproval(wfentry.getId(), 6));

	}
	/***
	 * 跳转到漏打卡审批页面
	 * @param wfentry
	 * @param modelAndView
	 */
	@SuppressWarnings("unchecked")
	private void approveAbsentee(Wfentry wfentry, ModelAndView modelAndView) {
		
		Long osworkflow = wfentry.getId();
		WorkflowAbsentee absenteeWorkflow = workflowAbsenteeService.findByWfentry(wfentry);
		modelAndView.setViewName("workflow/absentee/goapprove");
		
		int[] actionIds = springWorkflow.getAvailableActions(osworkflow, null);
		Map<Integer,String>	aciton = new HashMap<Integer,String>();
		for(int i=0; i<actionIds.length; i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.ABSENTEE).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.ABSENTEE).getAction(actionIds[i]).getName());
		}
		modelAndView.addObject("acitons", aciton);
		modelAndView.addObject("absenteeWorkflow", absenteeWorkflow);
		modelAndView.addObject("osworkflow", osworkflow);
		List<Step> steps = springWorkflow.getCurrentSteps(osworkflow);
		//判断是否是人事审核这一步，如果是operate = 1 显示“操作”这一列
		int operate = 0;
		if (steps != null && steps.size() > 0) {
			operate = steps.get(0).getStepId() >= 3 ? 1 : 0;
		}else{
			operate = 1;
		}
		modelAndView.addObject("operate", operate);
		modelAndView.addObject("approval2",workFlowService.findSingleApproval(wfentry.getId(), 2));
		modelAndView.addObject("approval3",workFlowService.findSingleApproval(wfentry.getId(), 3));
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
	}
	
	/***
	 * 跳转到外出申请审批页面
	 * @param wfentry
	 * @param modelAndView
	 */
	@SuppressWarnings("unchecked")
	private void approveAway(Wfentry wfentry, ModelAndView modelAndView) {
		Long osworkflow = wfentry.getId();
		WorkflowAway workflowAway = workflowAwayService.findByWfentry(wfentry);
		for(User user:workflowAway.getAwayUser()){
			UserResume userResume=userResumeService.findByUser(user);
			if(userResume!=null)
			user.setPosition(codeService.findPositionName(userResume.getPosition().toString()));
		}
		
		modelAndView.setViewName("workflow/away/goapprove");
		
		int[] actionIds = springWorkflow.getAvailableActions(osworkflow, null);
		Map<Integer,String>	aciton = new HashMap<Integer,String>();
		for(int i=0; i<actionIds.length; i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_AWAY).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_AWAY).getAction(actionIds[i]).getName());
		}
		User manager = userService.findOne(workflowAway.getManager());
		modelAndView.addObject("acitons", aciton);
		modelAndView.addObject("workflowAway", workflowAway);
		modelAndView.addObject("osworkflow", osworkflow);
		modelAndView.addObject("manager", manager.getChinesename());
		List<Step> steps = springWorkflow.getCurrentSteps(osworkflow);
		int guider = 1;
		int over = 0;	//流程结束
		if (steps != null && steps.size() > 0) {
			guider = steps.get(0).getStepId() >= 3 ? 1 : 0;
			over = steps.get(0).getStepId() != 3 ? 1 : 0;
		}else{
			over = 1;
		}
		modelAndView.addObject("over", over);
		modelAndView.addObject("guider", guider);
		modelAndView.addObject("approval2",workFlowService.findSingleApproval(wfentry.getId(), 2));
		modelAndView.addObject("approval3",workFlowService.findSingleApproval(wfentry.getId(), 3));
		modelAndView.addObject("approval4",workFlowService.findSingleApproval(wfentry.getId(), 4));
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
	}

	/***
	 * 跳转到报销页面
	 * @param wfentry
	 * @param modelAndView
	 */
	@SuppressWarnings("unchecked")
	private void approveReimburse(Wfentry wfentry, ModelAndView modelAndView) {
		modelAndView.setViewName("workflow/reimburse/goapprove");
		Reimburse  reimburse = reimburseService.findByWfentry(workFlowService.findWfentry(wfentry.getId()));
		Assert.notNull(reimburse);
		/**找外部人员信息*/
		List<ReimburseCustomerDetail> customerDetails = reimburseService.findReimburseCustomerDetails(reimburse);
		StringBuilder sbCustomer = new StringBuilder();
		if(customerDetails != null && customerDetails.size() >0){
			for(ReimburseCustomerDetail customer : customerDetails){
				if(StringUtils.isBlank(sbCustomer))
					sbCustomer.append(customer.getUserName() + "(" + customer.getCompany() + ") ");
				else
					sbCustomer.append(","+customer.getUserName() + "(" + customer.getCompany() + ") ");
			}
		}
		
		Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
		osworkflowArgsMap.put("reimburseId", reimburse.getId());
		/**查可以执行的动作*/
		int[] actionIds = springWorkflow.getAvailableActions(wfentry.getId(), osworkflowArgsMap);
		Map<Integer,String>	aciton=new HashMap<Integer,String>();
		for(int i=0;i<actionIds.length;i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getAction(actionIds[i]).getName());
		}
		
		modelAndView.addObject("reimburse", reimburse);
		modelAndView.addObject("sbCustomer", sbCustomer.toString());
		/**组装报销 详情DTO*/
		List<ReimburseDetails> details=reimburseService.findReimburseDetails(reimburse);
		List<ReimburseMyapproveDTO> dtos=new ArrayList<ReimburseMyapproveDTO>();
		for(ReimburseDetails detail: details){
			if(detail.getDailyReports()!=null&&detail.getDailyReports().size()!=0){
				for(DailyReport dailyReport:detail.getDailyReports()){
					ReimburseMyapproveDTO dto=new ReimburseMyapproveDTO();
					dto.setUser(detail.getUser());
					dto.setDailyReport(dailyReport);
					/**取刷卡记录*/
					dto.setAttenceBrushRecord(attenceBrushRecordService.findBrushRecord(dailyReport.getUser().getId(),
							dailyReport.getReportDate()));
					dtos.add(dto);
				}
			}
			else{
				ReimburseMyapproveDTO dto=new ReimburseMyapproveDTO();
				dto.setUser(detail.getUser());
				dtos.add(dto);
			}
		}
		/*找加班报告**/
		for(ReimburseMyapproveDTO dto: dtos){
			if(dto.getDailyReport()!=null){
				List<AttenceOverTime> attenceOverTimes=attenceOverTimeService.findByDailyReportId(dto.getDailyReport().getId());
				if(attenceOverTimes!=null&&attenceOverTimes.size()>=1)
				dto.setAttenceOverTime(attenceOverTimes.get(0));
			}
			
		}
		
		modelAndView.addObject("dtos",dtos);
		modelAndView.addObject("acitons",aciton);
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
		List<Step> steps = springWorkflow.getCurrentSteps(wfentry.getId());
		if(steps.size()>0)
			modelAndView.addObject("step",steps.get(0));
	}
	
	/**
	 * 培训需求收集审批页面
	 * @param wfentry
	 * @param modelAndView
	 */
	@SuppressWarnings("unchecked")
	private void approveTrainingRequireGather(Wfentry wfentry, ModelAndView modelAndView) {
		Long wfentryId = wfentry.getId();
		WorkFlowTrainingRequireGather workflow = workFlowTrainingRequireGatherService.findByWfentry(wfentry);
		List<Step> steps = springWorkflow.getCurrentSteps(wfentry.getId());
		if(steps.size() > 0)
		{
			Step step = steps.get(0);
			if (step.getStepId() == 2) {	//部门主管收集
				modelAndView.setViewName("workflow/training/requiregather/goapprove");
			}else if(step.getStepId() == 3){	//初步审核
				modelAndView.setViewName("workflow/training/requiregather/hr");
			}else if(step.getStepId() == 4){	//中心领导审核
				modelAndView.setViewName("workflow/training/requiregather/boss");
			}
		}
		//获取有效action
		int[] actionIds = springWorkflow.getAvailableActions(wfentryId, null);
		Map<Integer,String>	aciton = new HashMap<Integer,String>();
		for(int i=0; i<actionIds.length; i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.TRAINING_REQUIRE_GATHER).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.TRAINING_REQUIRE_GATHER).getAction(actionIds[i]).getName());
		}
		
		modelAndView.addObject("sn", workflow.getWfentry().getWfentryExtend().getSn());
		modelAndView.addObject("year", workflow.getYear());
		modelAndView.addObject("requireId", workflow.getId());
		modelAndView.addObject("acitons", aciton);
		modelAndView.addObject("wfentryId", wfentryId);
		modelAndView.addObject("approvals",workFlowService.findApproval(wfentry.getId()));
		
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
