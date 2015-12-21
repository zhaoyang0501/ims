package com.hsae.ims.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.DailyReportWeekConfig;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWeekConfigService;
import com.hsae.ims.service.DailyReportWorkStageService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.service.WorkingHoursCheckObserver;
import com.hsae.ims.service.WorkingHoursCheckObserverComparator;
import com.hsae.ims.utils.RightUtil;
/***
 * 日报填写
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/dailyReport")
public class DailyReportController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(DailyReportController.class);
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private WeekReportService weekReportService;
	@Autowired
	private DailyReportWeekConfigService dailyReportWeekConfigService;
	@Autowired
	private DailyReportWorkStageService dailyReportWorkStageService;
	@Autowired
	private CodeService codeService;
	/**工时核对的观察者们，目前有两个，日志状态观察者，工时核对观察者*/
	@Autowired
	private   List<WorkingHoursCheckObserver> workingHoursCheckObservers;
	/***
	 调整到日历视图
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("DailyReportController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "日报填写");
		breadCrumbMap.put("url", "dailyReport/dailyReport/");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/dailyreport/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/***
	 * 日历视图查找日志
	 * @param start
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, String>> findDailyReport(Long start, Long end) throws ParseException {
		log.info("start:{} end:{}", start, end);
		List<DailyReport> queryList = dailyReportService.findDailyReportByMonth(new java.util.Date(start*1000),new java.util.Date(end*1000),RightUtil.getCurrentUserId());
		List<Map<String, String>> dailyList = new ArrayList<Map<String, String>>();
		Map<String,String> dailyType = codeService.findDailyTypeCode();
		if (queryList != null && queryList.size() > 0) {
			for (DailyReport daily : queryList) {
				Map<String, String> dailyMap = new HashMap<String, String>();
				dailyMap.put("id", daily.getId().toString());
				String title = daily.getType();
				String titleText = "";
				if (!title.equals("1")) {
					titleText += dailyType.get(daily.getType()) + "-" + daily.getSpendHours() + "Hours";
				}else{
					titleText += "项目-" + daily.getProject().getProjectName() + "-" + daily.getSpendHours() + "Hours";
				}
				if (daily.getType().equals("7")) {
					dailyMap.put("color", "#dd5600");
				}else{
					dailyMap.put("color", "#6666FF");
				}
				dailyMap.put("title", titleText);
				dailyMap.put("start", DateFormatUtils.format( daily.getReportDate(), "yyyy-MM-dd"));
				dailyMap.put("end", daily.getReportDate().toString());
				dailyMap.put("url", "dailyReport/dailyReport/create/" +DateFormatUtils.format( daily.getReportDate(), "yyyy-MM-dd"));
				dailyMap.put("allDay", "false");
				dailyList.add(dailyMap);
			}
		}
		return dailyList;
	}
	
	/***
	 * 跳转到创建日志的页面
	 * @param reportDate
	 * @return
	 */
	@RequestMapping("/create/{reportDate}")
	public ModelAndView redirectCreatePage(@PathVariable String reportDate) {
		log.info("DailyReport.dailyReportCreate()");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Long cuid = RightUtil.getCurrentUserId();
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "日报填写");
		breadCrumbMap1.put("url", "dailyReport/dailyReport/");
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "新建");
		breadCrumbMap.put("url", "dailyReport/dailyReport/create/"+reportDate);
		
		breadCrumbList.add(breadCrumbMap1);
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/dailyreport/create");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("reportDate", reportDate);
		model.addObject("dailyreportTypes", codeService.findDailyTypeCode());
		model.addObject("workSteps", dailyReportWorkStageService.findWorkStage(cuid));
		return model;
	}
	
	/***
	 * 查找某一日期下的所有日志
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/queryByCreateDate/{date}")
	@ResponseBody
	public Map<String, Object> findCreateDailyReport(@PathVariable String date) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<DailyReport> dailyReports =dailyReportService.findByReportDateAndUser(
				DateUtils.parseDate(date, "yyyy-MM-dd"),
				userService.findOne(RightUtil.getCurrentUserId()));
		Map<String,String> dailyType = codeService.findDailyTypeCode();
		for(DailyReport bean:dailyReports ){
			bean.setType(dailyType.get(bean.getType()));
		}
		map.put("aaData",dailyReports );
		return map;
	}
	/***
	 * 保存一个日报
	 * to do 人员 项目属性
	 * @param dailyReport
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/saveDailyReport", method = RequestMethod.POST)
	@ResponseBody
	public DailyReport saveDailyReport(DailyReport dailyReport,Long projectId) throws ParseException {
		dailyReport.setUser(userService.findOne(RightUtil.getCurrentUserId()));
		dailyReport.setCreateDate(new java.util.Date(System.currentTimeMillis()));
		if(projectId!=null) 
			dailyReport.setProject(projectService.getProjectById(projectId));
		/**如果不是项目以及不是加班不用填项目相关信息*/
		if(!ImsConstants.DailyReportConstants.PROJECT_TYPE.equals(dailyReport.getType())&&
				!ImsConstants.DailyReportConstants.OVERTIME_TYPE.equals(dailyReport.getType())){
			dailyReport.setProject(null);
			dailyReport.setProjectStep(null);
		}
		/**如果是请假不需要填写困难度*/
		if(ImsConstants.DailyReportConstants.DAYYOFF_TYPE.equals(dailyReport.getType())){
			dailyReport.setDifficulty(null);
		}
		dailyReport=dailyReportService.saveDailyReport(dailyReport);
		this.notifyWatchers(dailyReport);
		return dailyReport;
	}
	/***
	 * 删除一个日报
	 * @param dailyReport
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/deleteDailyReport/{id}")
	@ResponseBody
	public Map<String,String> deleteDailyReport(@PathVariable Long id) throws ParseException {
		Map<String,String> resultMap=new HashMap<String,String>();
		DailyReport dailyReport=dailyReportService.getDailyReportById(id);
		if(dailyReport.getUser().getId()!=RightUtil.getCurrentUserId()){
			resultMap.put("state", "false");
			resultMap.put("msg", "不允许删除不是由您创建的日志");
		}else{
			resultMap.put("state", "true");
			dailyReportService.deleteDailyReport(dailyReport);
		}
		return resultMap;
	}
	/***
	 * 修改一个日志
	 * @param dailyReport
	 * @return
	 */
	@RequestMapping(value = "/updateDailyReport", method = RequestMethod.POST)
	@ResponseBody
	public DailyReport updateDailyReport(DailyReport dailyReport,Long projectId){
		Assert.notNull(dailyReport);
		DailyReport dailyReportToBeUpdate=dailyReportService.getDailyReportById(dailyReport.getId());
		dailyReportToBeUpdate.setDifficulty(dailyReport.getDifficulty());
		dailyReportToBeUpdate.setProjectStep(dailyReport.getProjectStep());
		dailyReportToBeUpdate.setSpendHours(dailyReport.getSpendHours());
		dailyReportToBeUpdate.setSummary(dailyReport.getSummary());
		dailyReportToBeUpdate.setType(dailyReport.getType());
		if(projectId!=null) 
			dailyReportToBeUpdate.setProject(projectService.getProjectById(projectId));
		else
			dailyReportToBeUpdate.setProject(null);
		/**如果不是项目不用填项目相关信息*/
		if(!ImsConstants.DailyReportConstants.PROJECT_TYPE.equals(dailyReportToBeUpdate.getType())
				&&!ImsConstants.DailyReportConstants.OVERTIME_TYPE.equals(dailyReport.getType())){
			dailyReportToBeUpdate.setProject(null);
			dailyReportToBeUpdate.setProjectStep(null);
		}
		/**如果是请假不需要填写困难度*/
		if(ImsConstants.DailyReportConstants.DAYYOFF_TYPE.equals(dailyReportToBeUpdate.getType())){
			dailyReportToBeUpdate.setDifficulty(null);
		}
		dailyReportToBeUpdate=dailyReportService.saveDailyReport(dailyReportToBeUpdate);
		this.notifyWatchers(dailyReportToBeUpdate);
		return dailyReportToBeUpdate;
		
	}
	/***
	 * 根据id查询日志
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getDailyReport/{id}")
	@ResponseBody
	public DailyReport getDailyReport(@PathVariable Long id){
		return dailyReportService.getDailyReportById(id);
	}
	/***
	 * 获取用户参与的项目列表
	 * @return
	 */
	@RequestMapping(value="getProjectsUserJoined")
	@ResponseBody
	public Iterable<Project> getProjectsUserJoined(){
		return projectService.findByUserJoined(RightUtil.getCurrentUserId());
	}
	@RequestMapping(value="canEdit/{date}")
	@ResponseBody
	public Boolean canEdit(@PathVariable String date) throws ParseException{
		DailyReportWeekConfig dailyReportWeekConfig =
				dailyReportWeekConfigService.findWeekConfigByDate(DateUtils.parseDate(date, "yyyy-MM-dd"));
		WeekReport weekReport=weekReportService.findByWeekAndUser(RightUtil.getCurrentUserId(), dailyReportWeekConfig.getId());
		if(weekReport!=null&&!"提交周报".equals(weekReport.getStep())) 
			return false;
		else return true;
	}
	
	@RequestMapping(value="getProjectSteps")
	@ResponseBody
	public Response getProjectSteps() throws ParseException{
		return new  ObjectResponse<Map<Integer,String>>(dailyReportWorkStageService.findWorkStage());
	} 
	@RequestMapping(value="getDailyTypeCode")
	@ResponseBody
	public Response getDailyTypeCode() throws ParseException{
		return new  ObjectResponse<Map<String,String>>(codeService.findDailyTypeCode());
	} 
	/***
	 * 通知观察者工时核对完成
	 * @param attenceOverTime
	 */
	private void notifyWatchers(DailyReport dailyReport){
		WorkingHoursCheckObserverComparator comparator=new WorkingHoursCheckObserverComparator();
	    Collections.sort(workingHoursCheckObservers, comparator);
		if(workingHoursCheckObservers!=null){
			for(WorkingHoursCheckObserver workingHoursCheckObserver :workingHoursCheckObservers){
				workingHoursCheckObserver.update(dailyReport);
			}
		}
	}
}
