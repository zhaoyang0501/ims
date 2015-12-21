package com.hsae.ims.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
/***
 * 周报查询查询
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/viewWeekReport")
public class WeekReportViewController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(WeekReportViewController.class);
	@Autowired
	private  WeekReportService weekReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private ProjectService projectService;
	/***
	 	跳转到日报查询页面
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("ViewWeekReportController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报查询");
		breadCrumbMap.put("url", "dailyReport/viewWeekReport/");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/viewWeekReport/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/**
	 * ajax日志查询
	 * @throws ParseException 
	 * @throws WorkflowException 
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findWeekReport(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String startDate, String endDate,
			Long user, Integer state) throws WorkflowException, ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1, pageSize = iDisplayLength;
		Date start=null, end=null;
		if(StringUtils.isNotBlank(startDate))
			start=DateUtils.parseDate(startDate, "yyyy-MM-dd");
		if(StringUtils.isNotBlank(endDate))
			end=DateUtils.parseDate(endDate, "yyyy-MM-dd");
		String scope=userService.findOne(RightUtil.getCurrentUserId()).getAuthorityScope();
		Page<WeekReport> weekReportPage = weekReportService.findWeekReport(start,end,user, state,scope, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", weekReportPage.getContent());
		map.put("iTotalRecords", weekReportPage.getTotalElements());
		map.put("iTotalDisplayRecords", weekReportPage.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}
