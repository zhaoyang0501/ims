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

import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.RightUtil;
/***
 * 日报查询
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/viewDailyReport")
public class DailyReportViewController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(DailyReportViewController.class);
	@Autowired
	private  DailyReportService dailyReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private CodeService codeService;
	/***
	 	跳转到日报查询页面
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("DailyReportViewController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "日报查询");
		breadCrumbMap.put("url", "dailyReport/viewDailyReport/");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/viewDailyReport/index");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("projects",projectService.findByUserJoined(RightUtil.getCurrentUserId()));
		model.addObject("dailyreportTypes", codeService.findDailyTypeCode());
		return model;
	}
	/**
	 * ajax日志查询
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDailyReport(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String startDate, String endDate, Long project,
			Long user, Integer type) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date start=null, end=null;
		if(StringUtils.isNotBlank(startDate))
			start=DateUtils.parseDate(startDate, "yyyy-MM-dd");
		if(StringUtils.isNotBlank(endDate))
			end=DateUtils.parseDate(endDate, "yyyy-MM-dd");
		String scope=userService.findOne(RightUtil.getCurrentUserId()).getAuthorityScope();
		Page<DailyReport> dailyReportPage = dailyReportService.findDailyReport(start, end, project, user, type,scope, pageNumber, pageSize);
		Map<String,String> dailyType = codeService.findDailyTypeCode();
		for(DailyReport bean:dailyReportPage.getContent() ){
			bean.setType(dailyType.get(bean.getType()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dailyReportPage.getContent());
		map.put("iTotalRecords", dailyReportPage.getTotalElements());
		map.put("iTotalDisplayRecords", dailyReportPage.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value = "/query/owner", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findOwnerDailyReport(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, String startDate, String endDate) throws ParseException {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date start=null, end=null;
		if(StringUtils.isNotBlank(startDate))
			start=DateUtils.parseDate(startDate, "yyyy-MM-dd");
		if(StringUtils.isNotBlank(endDate))
			end=DateUtils.parseDate(endDate, "yyyy-MM-dd");
		Long cuid = RightUtil.getCurrentUserId();
		Page<DailyReport> dailyReportPage = dailyReportService.findDailyReport(start, end, cuid, pageNumber, pageSize);
		Map<String,String> dailyType = codeService.findDailyTypeCode();
		for(DailyReport bean:dailyReportPage.getContent() ){
			bean.setType(dailyType.get(bean.getType()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dailyReportPage.getContent());
		map.put("iTotalRecords", dailyReportPage.getTotalElements());
		map.put("iTotalDisplayRecords", dailyReportPage.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}
