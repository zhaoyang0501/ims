package com.hsae.ims.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.DailyReportWeekConfig;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWeekConfigService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;
/***
 * 周报提交功能模块
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/weekReport")
public class WeekReportController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(WeekReportController.class);
	@Autowired
	private  DailyReportService dailyReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DailyReportWeekConfigService dailyReportWeekConfigService;
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private WeekReportService weekReportService;
	@Autowired
	private CodeService codeService;
	/***
	 	跳转到周报提交
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("WeekReportController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报提交");
		breadCrumbMap.put("url", "dailyreport/weekReport");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/weekReport/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/***
	 * 获取当前周weekid
	 * @return
	 */
	@RequestMapping(value="/getWeekId")
	@ResponseBody
	public Map<String,Object> getWeekId(){
		Map<String, Object> map = new HashMap<String, Object>();
		DailyReportWeekConfig dailyReportWeekConfig = dailyReportWeekConfigService.
				findWeekConfigByDate(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH) );
		if(dailyReportWeekConfig==null){
			map.put("status","error");
			map.put("msg", "周次表中没有配置周次信息");
		}else{
			map.put("status","success");
			map.put("weekid",dailyReportWeekConfig.getId());
		}
		return map;
	}
	/**
	 * 根据weekId获取某一周的日报
	 * @param weekNum
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getweekReport")
	@ResponseBody
	public Map<String, Object> getweekReport(Long weekId) {
		Assert.notNull(weekId);
		Map<String, Object> map = new HashMap<String, Object>();
		DailyReportWeekConfig dailyReportWeekConfig = dailyReportWeekConfigService.findWeekConfigById(weekId);
		if (dailyReportWeekConfig != null) {
			map.put("weekConfig", dailyReportWeekConfig);
			/**查周报表，获取周报信息**/
			WeekReport weekReport = weekReportService.findByWeekAndUser(RightUtil.getCurrentUserId(),weekId);
			map.put("weekReport", weekReport);
			/**查周报下日志**/
			List<DailyReport> dailyReports= dailyReportService.findDailyReport(dailyReportWeekConfig.getStartDate(),
					dailyReportWeekConfig.getEndDate(), userService.findOne(RightUtil.getCurrentUserId()));
										   
			Map<String,String> dailyType = codeService.findDailyTypeCode();
			for( DailyReport dailyReport:dailyReports){
				dailyReport.setType(dailyType.get(dailyReport.getType()));
			}
			map.put("aaData",dailyReports);
		} else {
			map.put("weekReport", null);
			map.put("weekConfig", null);
			map.put("aaData", null);
		}
		return map;
	}
	/***
	 * 提交某一周周报
	 * @param weekid
	 * @return
	 */
	@RequestMapping(value="/submitWeekReport/{weekid}")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,Object> submitWeekReport(@PathVariable Long weekid,String remark){
		Assert.notNull(weekid);
		Map<String,Object> resultMap = new HashMap<String,Object>(); 
		resultMap.put("status", "success");
		DailyReportWeekConfig dailyReportWeekConfig=dailyReportWeekConfigService.findWeekConfigById(weekid);
		User user=userService.findOne(RightUtil.getCurrentUserId());
		/** 验证是否在提交时间段，验证该周下面是否有日报等**/
		if(dailyReportWeekConfig==null){
			resultMap.put("status", "error");
			resultMap.put("msg", "没有设置该考核周！");
			return resultMap;
		}
		if(dailyReportWeekConfig.getSubmitDate().after(new Date())){
			resultMap.put("status", "error");
			resultMap.put("msg", "未到流程发起日期！");
			return resultMap;
		}
		List<DailyReport> dailyReports=dailyReportService.findDailyReport(dailyReportWeekConfig.getStartDate(), 
				dailyReportWeekConfig.getEndDate(), userService.findOne(RightUtil.getCurrentUserId()));
		if(dailyReports==null||dailyReports.size()==0){
			resultMap.put("status", "error");
			resultMap.put("msg", "该周次下没有日报！");
			return resultMap;
		}
		WeekReport weekReport = weekReportService.findByWeekAndUser(RightUtil.getCurrentUserId(),weekid);
		if(weekReport!=null&&!"提交周报".equals(weekReport.getStep())){
			resultMap.put("status", "error");
			resultMap.put("msg", "已经提交过，不允许重复提交！");
			return resultMap;
		}
		/**工作流逻辑**/
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("creater",user.getId());
		argMap.put("caller",userService.findOne(user.getId()));
		
		/**初始化一个工作流实例**/
		springWorkflow.SetContext(String.valueOf( user.getId()));
		Long workFlowid = weekReport==null?null:weekReport.getOsworkflow();
		try {
		/**如果新的工作流需要初始化，因为退回的不需要初始化**/
			if(weekReport==null){
				weekReport=new WeekReport();
				workFlowid=springWorkflow.initialize(WorkFlowConstants.DAILY_REPORT, 10, argMap);
				springWorkflow.getPropertySet(workFlowid).setLong("creater", user.getId());
			}
				
		}  catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("msg", "初始化流程实例失败"+e.getMessage());
			return resultMap;
		} 
		/**提交到部门经理审核**/
		Integer actionId=springWorkflow.getAvailableActions(workFlowid,argMap)[0];
		String actionName=springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionId).getName();
		try {
			springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", "error");
			resultMap.put("msg", "提交到部门经理失败"+e.getMessage());
			return resultMap;
		}
		/**保存周报，设置周报的状态步骤等信息*/
		if(weekReport.getApprovals()==null)
			weekReport.setApprovals(RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]");
		else
			weekReport.setApprovals(StringUtils.trimToEmpty(weekReport.getApprovals())+"<br>"+RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]");
		
		weekReport.setCreateDate(new Date());
		weekReport.setCreater(userService.findOne(RightUtil.getCurrentUserId()));
		weekReport.setOsworkflow(workFlowid);
		List<Step> steps=springWorkflow.getCurrentSteps(workFlowid);
		if(steps!=null&steps.size()!=0)
			weekReport.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getStep(steps.get(0).getStepId()).getName());
		weekReport.setState(springWorkflow.getEntryState(workFlowid));
		weekReport.setWeek(dailyReportWeekConfig);
		weekReport.setRemark(remark);
		weekReportService.saveWeekReport(weekReport);
		resultMap.put("weekReport",weekReport);
		resultMap.put("weekConfig",dailyReportWeekConfig);
		return resultMap;
	}
}
