package com.hsae.ims.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.ReimburseMyapproveDTO;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseCustomerDetail;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.HistoryStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWorkStageService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.WorkFlowTrainingRequireGatherService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;

@Controller
@RequestMapping("/approve/myapprove")
public class MyApproveController {
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
	
	public String dailyReportWorkflowXMlAsString;
	
	public String reimburseWorkflowXMlAsString;
	
	@Autowired
	private AttenceOverTimeService attenceOverTimeService;
	
	@Autowired
	AttenceBrushRecordService attenceBrushRecordService;
	
	@Autowired
	private WorkFlowTrainingRequireGatherService workFlowTrainingRequireGatherService;
	
	/***
	 * 设置工作流XML文件的字符串表现形式
	 */
	@PostConstruct
	public void setWorkflowXMlAsString(){
		try {
			dailyReportWorkflowXMlAsString = workFlowService.getWorkflowXMlAsString(WorkFlowConstants.DAILY_REPORT);
			reimburseWorkflowXMlAsString = workFlowService.getWorkflowXMlAsString(WorkFlowConstants.REIMBURSE);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   
	}
	/***
	 * 首页 
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("approve/myapprove","我的待办"));
		ModelAndView model = new ModelAndView("approve/myapprove/index");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		return model;
	}
	/***
	 * 待办事项表格数据
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
			String endDate
			) throws WorkflowException, ParseException{
		Date start = startDate==null?null:DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end= endDate==null?null:DateUtils.parseDate(endDate, "yyyy-MM-dd");
		Page<CurrentStep> currentSteps = 
				workFlowService.findTodoList(workFlowName, start, end,
							userService.findOne(RightUtil.getCurrentUserId()),  null,null,(iDisplayStart / iDisplayLength) + 1, iDisplayLength);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", currentSteps.getContent());
		map.put("iTotalRecords", currentSteps.getTotalElements());
		map.put("iTotalDisplayRecords", currentSteps.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 已办事项表格数据
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
			Integer workflowState
			) throws WorkflowException, ParseException{
		Date start = startDate==null?null:DateUtils.parseDate(startDate, "yyyy-MM-dd");
		Date end= endDate==null?null:DateUtils.parseDate(endDate, "yyyy-MM-dd");
		Page<HistoryStep> historySteps = 
				workFlowService.findDoneList(workFlowName, start, end,workflowState,
							userService.findOne(RightUtil.getCurrentUserId()),  null,null,(iDisplayStart / iDisplayLength) + 1, iDisplayLength);
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
	@RequestMapping(value="/goApprove/{wfentryId}")
	@SuppressWarnings("unchecked")
	public  ModelAndView goApprove(@PathVariable Long wfentryId,@ModelAttribute("tip") String tip){
		ModelAndView modelAndView =new ModelAndView();
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("approve/myapprove", "我的待办"));
		
		springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
		Wfentry wfentry = workFlowService.findWfentry(wfentryId);
		/**走日志审批流程*/
		if(WorkFlowConstants.DAILY_REPORT.equals(wfentry.getName())){
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
			modelAndView.addObject("tip", tip);
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
			breadCrumbList.add(getBreadCrumbMap("approve/myapprove/goApprove/"+wfentryId, "周报审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
			
			return modelAndView;
		}
		/**走报销报销流程*/
		else if(WorkFlowConstants.REIMBURSE.equals(wfentry.getName())){
			Reimburse  reimburse = reimburseService.findByWfentry(workFlowService.findWfentry(wfentryId));
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
			int[] actionIds = springWorkflow.getAvailableActions(wfentryId, osworkflowArgsMap);
			Map<Integer,String>	aciton=new HashMap<Integer,String>();
			for(int i=0;i<actionIds.length;i++){
				aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getAction(actionIds[i]).getId(), 
						springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getAction(actionIds[i]).getName());
			}
			modelAndView.setViewName("approve/myapprove/reimburse");
			breadCrumbList.add(getBreadCrumbMap("approve/myapprove/goApprove/"+wfentryId, "餐费报销审批"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
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
//			modelAndView.addObject("approvals",workFlowService.findApprovalByWfentry(wfentryId));
			List<Step> steps = springWorkflow.getCurrentSteps(wfentryId);
			if(steps.size()>0)
				modelAndView.addObject("step",steps.get(0));
		}
		/** 培训需求收集流程  **/
		else if(ImsConstants.WorkFlowConstants.TRAINING_REQUIRE_GATHER.equals(wfentry.getName())){
			WorkFlowTrainingRequireGather tr = workFlowTrainingRequireGatherService.findByWfentry(wfentry);
			int year = tr.getYear();
			/** 可操作action **/
			Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
			osworkflowArgsMap.put("requireId", tr.getId());
			int[] actionIds = springWorkflow.getAvailableActions(wfentryId, osworkflowArgsMap);
			Map<Integer,String>	aciton=new HashMap<Integer,String>();
			for(int i=0;i<actionIds.length;i++){
				aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.TRAINING_REQUIRE_GATHER).getAction(actionIds[i]).getId(), 
						springWorkflow.getWorkflowDescriptor(WorkFlowConstants.TRAINING_REQUIRE_GATHER).getAction(actionIds[i]).getName());
			}
			
			modelAndView.addObject("actionId", actionIds[0]);
			modelAndView.addObject("requireId", tr == null ? "" : tr.getId());
			
			List<Step> steps = springWorkflow.getCurrentSteps(wfentryId);
			if(steps.size() > 0)
			{
				Step step = steps.get(0);
				if (step.getStepId() == 2) {	//部门主管收集
					modelAndView.setViewName("approve/myapprove/trainingrequire/traingrequiregather");
				}else if(step.getStepId() == 3){	//初步审核
					modelAndView.addObject("year", year);
					modelAndView.setViewName("approve/myapprove/trainingrequire/traingrequireprimary");
				}else if(step.getStepId() == 4){	//中心领导审核
					modelAndView.addObject("year", year);
					modelAndView.setViewName("approve/myapprove/trainingrequire/traingrequireleader");
				}
			}
			breadCrumbList.add(getBreadCrumbMap("approve/myapprove/goApprove/"+wfentryId, "培训需求收集"));
			modelAndView.addObject("breadcrumb", breadCrumbList);
		}
		
		return modelAndView;
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
	@RequestMapping(value="/doApproveReimburse/{wfentryId}", method = RequestMethod.POST)
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
		workFlowService.saveApproval(approvals, step.getId());
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
		if(springWorkflow.getEntryState(wfentryId)==4){
			reimburse.setPayMoneyDate(new Date(System.currentTimeMillis()));
			reimburse.setActualMoney(cash);
		}
		reimburseService.save(reimburse);
		redirectAttributes.addFlashAttribute("tip", "操作成功！");
		return  "redirect:/approve/myapprove/goApprove/"+wfentryId;
	}
	
	/***
	 * 周报审批动作
	 * @param weekreportid
	 * @param actionId
	 * @param approvals
	 * @return
	 * @throws WorkflowException
	 */
	@RequestMapping(value="/doApproveWeekReport/{weekreportid}", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	public ModelAndView doApproveWeekReport(@PathVariable Long weekreportid, Integer  actionId,String approvals) throws WorkflowException{
		/**执行成功，调整到我的审批页面**/
		ModelAndView model = new ModelAndView("approve/myapprove/index");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("approve/myapprove","我的待办"));
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		model.addObject("response",new SuccessResponse());
		
		Long userid= RightUtil.getCurrentUserId();
		WeekReport weekReport = weekReportService.getById(weekreportid);
		/**工作流流转*/
		springWorkflow.SetContext(userid.toString());
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("caller",userService.findOne(userid));
		argMap.put("creater",springWorkflow.getPropertySet(weekReport.getOsworkflow()).getLong("creater") );
		springWorkflow.doAction(weekReport.getOsworkflow(), actionId, argMap);
		
		/**拼接审批意见*/
		String actionName=springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionId).getName();
		if(weekReport.getApprovals()==null)
			weekReport.setApprovals(RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+StringUtils.trimToEmpty(approvals));
		else
			weekReport.setApprovals(StringUtils.trimToEmpty(weekReport.getApprovals())+"<br>"+RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+approvals);
		/**修改周报的状态步骤*/	
		List<Step> steps = springWorkflow.getCurrentSteps(weekReport.getOsworkflow());
		if(steps!=null&steps.size()!=0)
			weekReport.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getStep(steps.get(0).getStepId()).getName());
		else{
			List<Step> historySteps = springWorkflow.getHistorySteps(weekReport.getOsworkflow());
			if(historySteps!=null&historySteps.size()!=0)
				weekReport.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getStep(historySteps.get(0).getStepId()).getName());
		}
		weekReport.setState(springWorkflow.getEntryState(weekReport.getOsworkflow()));
		weekReportService.saveWeekReport(weekReport);
		return model;
	}
	/***
	 * 查看某个流程下的流程图
	 * @param workflowId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/workflowgraph/{workflowId}")
	public ModelAndView workflowGraph(@PathVariable Long workflowId){
		ModelAndView modelAndView = new ModelAndView("approve/myapprove/workflowgraph");
		springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
		List<Step> steps = springWorkflow.getCurrentSteps(workflowId);
		String workflowName=springWorkflow.getWorkflowName(workflowId);
		if(steps!=null&&steps.size()!=0){
			modelAndView.addObject("stepid",steps.get(0).getStepId());
		}else{
			List<Step> historySteps = springWorkflow.getHistorySteps(workflowId);
			if(historySteps!=null&historySteps.size()!=0)
				modelAndView.addObject("stepid",historySteps.get(0).getStepId());
		}
		if(WorkFlowConstants.DAILY_REPORT.equals(workflowName))
			modelAndView.addObject("workflow", dailyReportWorkflowXMlAsString);
		else if(WorkFlowConstants.REIMBURSE.equals(workflowName))
			modelAndView.addObject("workflow", reimburseWorkflowXMlAsString);
		modelAndView.addObject("steps",springWorkflow.getWorkflowDescriptor(workflowName).getSteps()) ;
		return modelAndView;
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
