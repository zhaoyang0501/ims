package com.hsae.ims.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.DailyReportWeekConfig;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWeekConfigService;
import com.hsae.ims.service.DailyReportWorkStageService;
import com.hsae.ims.service.DeptService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.view.WeekReportExportXlsView;
/***
 * 周报导出
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/weekReportExport")
public class WeekReportExportController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(WeekReportExportController.class);
	@Autowired
	private  WeekReportService weekReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private DailyReportWeekConfigService dailyReportWeekConfigService;
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private DailyReportWorkStageService dailyReportWorkStageService;
	/***
	 	首页
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("WeekReportStateController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报导出");
		breadCrumbMap.put("url", "dailyReport/weekReportExport/");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/weekReportExport/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/**
	 * ajax日志查询
	 */
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDailyReport(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength, Long weekId) {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Assert.notNull(weekId);
		DailyReportWeekConfig  dailyReportWeekConfig = dailyReportWeekConfigService.findWeekConfigById(weekId);
		Page<DailyReport> dailyReportPage = dailyReportService.findDailyReport(dailyReportWeekConfig.getStartDate(), dailyReportWeekConfig.getEndDate(),
				null, null, null,null, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dailyReportPage.getContent());
		map.put("iTotalRecords", dailyReportPage.getTotalElements());
		map.put("iTotalDisplayRecords", dailyReportPage.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	@RequestMapping(value="/export/{weekId}")
	public ModelAndView viewExcel(@PathVariable Long weekId){
		log.debug("ViewController.viewExcel is started......");
		DailyReportWeekConfig week=dailyReportWeekConfigService.findWeekConfigById(weekId);
		Map<String,Object> model = new HashMap<String,Object>();
		List<Deptment> deptments=deptService.getAllChildDept();
		Map<Long,List<DailyReport>> dailys=new HashMap<Long,List<DailyReport>>();
		for(Deptment deptment:deptments){
			dailys.put(deptment.getId(), dailyReportService.findDailyReportByDeptAndWeek(deptment.getId(),week));
		}
		log.info("周报数量-"+dailys.size());
		model.put("dailys", dailys);
		model.put("codeMap", codeService.findDailyTypeCode());
		model.put("workStep", dailyReportWorkStageService.findWorkStage());
		model.put("deptments", deptments);
		model.put("fileName", week.getYear()+"年第"+week.getWeekNum()+"周.xls");
		log.debug("ViewController.viewExcel is ended......");
		return new ModelAndView(new WeekReportExportXlsView(), model); 
	}
}
