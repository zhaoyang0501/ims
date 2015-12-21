package com.hsae.ims.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bsh.This;

import com.hsae.ims.dto.AttenceAbsenteeDTO;
import com.hsae.ims.dto.AttenceCountReportDTO;
import com.hsae.ims.dto.AttenceCountStateDTO;
import com.hsae.ims.dto.AttenceDayoffDTO;
import com.hsae.ims.dto.AttenceOvertimeDTO;
import com.hsae.ims.dto.AttenceTravelDTO;
import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.AttenceStatistics;
import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.entity.Code;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.AttenceReportService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.DateTimeUtil;

@Controller
@RequestMapping("/attence/report")
public class AttenceReportController {

	@Autowired
	private AttenceReportService attenceReportService;
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private UserService userService;
	/**
	 * 漏刷卡统计表。 absentee
	 */
	@RequestMapping(value = "/absentee/index")
	public ModelAndView absenteeIndex() {
		ModelAndView mav = new ModelAndView("attence/report/absentee");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "漏刷卡统计报表");
		breadCrumbMap.put("url", "attence/report/absentee/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("startDate", DateTimeUtil.getFirstDayOfMonth(new Date()));
		mav.addObject("endDate", DateTimeUtil.getLastDayOfMonth(new Date()));
		return mav;
	}

	/**
	 * 漏刷卡统计表数据源。 absentee
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/absentee/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryAttenceAbsenteeList(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String sstartDate, String sendDate, Long pmId) throws ParseException {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date startDate= sstartDate==null?null:DateUtils.parseDate(sstartDate, "yyyy-MM-dd");
	    Date endDate= sendDate==null?null:DateUtils.parseDate(sendDate, "yyyy-MM-dd");
		Page<AttenceAbsentee> page = attenceReportService.findUserAbsentee(startDate, endDate, pmId, pageNumber, pageSize);
		List<AttenceAbsenteeDTO> dtoList = new ArrayList<AttenceAbsenteeDTO>();
		List<Code> codeList = codeService.findAbsenteeType();
		Map<String,String> codesMap=new HashMap<String,String>() ;
		for(Code code : codeList ){
			codesMap.put(code.getCode(), code.getName());
		}
		if (page != null && page.getTotalElements() > 0) {
			AttenceAbsenteeDTO dto = null;
			User user = null;
			Deptment dept = null;
			int index = 1;
			for (AttenceAbsentee p : page) {
				dto = new AttenceAbsenteeDTO();
				dto.setIndex(String.valueOf(index));
				user = userService.findOne(p.getUser());
				if (user == null) {
					dto.setUserName("");
					dto.setDeptName("");
				} else {
					dept = user.getDept();
					dto.setUserName(user.getChinesename());
					dto.setDeptName(dept == null ? "" : dept.getName());
				}
				dto.setAbsenteeDate(DateTimeUtil.getFormatDate(p.getAbsenteeDate(), "yyyy-MM-dd"));
				dto.setAbsenteeTime(p.getAbsenteeTime());
				dto.setAbsenteeType(codesMap.get(p.getAbsenteeType()));
				dto.setRemark(p.getRemark());
				index++;
				dtoList.add(dto);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 漏刷卡统计图表。（补签卡）
	 */
	@RequestMapping(value = "/Absentee/chart")
	@ResponseBody
	public Map<String, Object> queryAttenceAbsenteePie(String rsstartDate, String rsendDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = attenceReportService.queryAttenceAbsenteePieList(rsstartDate, rsendDate);
		List<String> deptList = attenceReportService.findAllDeptNames(rsstartDate, rsendDate);
		map.put("deptData", deptList);
		map.put("absenteeData", list);
		return map;
	}

	/**
	 * 请假数据统计表。 dayoff
	 */
	@RequestMapping(value = "/dayoff/index")
	public ModelAndView dayoffIndex() {
		ModelAndView mav = new ModelAndView("attence/report/dayoff");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "请假统计报表");
		breadCrumbMap.put("url", "attence/report/dayoff/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("startDate", DateTimeUtil.getFirstDayOfMonth(new Date()));
		mav.addObject("endDate", DateTimeUtil.getLastDayOfMonth(new Date()));
		return mav;
	}

	/***
	 * 请假统计表数据源。 Dayoff
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param sstartDate
	 * @param sendDate
	 * @param pmId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/dayoff/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryAttenceDayoffList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String sstartDate, String sendDate, Long pmId) throws ParseException {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date startDate= sstartDate==null?null:DateUtils.parseDate(sstartDate, "yyyy-MM-dd");
	    Date endDate= sendDate==null?null:DateUtils.parseDate(sendDate, "yyyy-MM-dd");
		Page<AttenceDayoff> page = attenceReportService.findUserDayoff(startDate, endDate, pmId, pageNumber, pageSize);
		List<AttenceDayoffDTO> dtoList = new ArrayList<AttenceDayoffDTO>();

		if (page != null && page.getTotalElements() > 0) {
			AttenceDayoffDTO dto = null;
			User user = null;
			Deptment dept = null;
			int index = 1;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (AttenceDayoff p : page) {
				dto = new AttenceDayoffDTO();
				dto.setIndex(String.valueOf(index));
				user = p.getUser();
				if (user == null) {
					dto.setUserName("");
					dto.setDeptName("");
				} else {
					dept = user.getDept();
					dto.setUserName(user.getChinesename());
					dto.setDeptName(dept == null ? "" : dept.getName());
				}
				dto.setDayoffDate(df.format(p.getDayoffDate()));
				dto.setStartTime(df.format(p.getStartTime()));
				dto.setEndTime(df.format(p.getEndTime()));
				dto.setSaveTime(df.format(p.getSaveTime()));
				dto.setSpendHours(p.getSpendHours());
				dto.setDayoffType(codeService.findDayOffName(p.getDayoffType()));
				dto.setRemark(p.getRemark());
				index++;
				dtoList.add(dto);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 请假数据图表 。
	 * 
	 */
	@RequestMapping(value = "/dayoff/chart")
	@ResponseBody
	public Map<String, Object> queryAttenceDayoffPie(String rsstartDate, String rsendDate) {
		Map<String, Object> map = attenceReportService.queryAttenceDayoffPieList(rsstartDate, rsendDate);
		return map;
	}

	/**
	 * 加班数据统计表。 overtime
	 */
	@RequestMapping(value = "/overtime/index")
	public ModelAndView overtimeIndex() {
		ModelAndView mav = new ModelAndView("attence/report/overtime");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "加班统计报表");
		breadCrumbMap.put("url", "attence/report/overtime/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("startDate", DateTimeUtil.getFirstDayOfMonth(new Date()));
		mav.addObject("endDate", DateTimeUtil.getLastDayOfMonth(new Date()));
		return mav;
	}

	/**
	 * 加班数据统计表数据源。 overtime
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/overtime/list")
	@ResponseBody
	public Map<String, Object> queryAttenceOvertimeList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String sstartDate, String sendDate, Long pmId) throws ParseException {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date startDate= sstartDate==null?null:DateUtils.parseDate(sstartDate, "yyyy-MM-dd");
	    Date endDate= sendDate==null?null:DateUtils.parseDate(sendDate, "yyyy-MM-dd");
		Page<AttenceOverTime> page = attenceReportService.findUserOvertime(startDate, endDate, pmId, pageNumber, pageSize);
		List<AttenceOvertimeDTO> dtoList = new ArrayList<AttenceOvertimeDTO>();

		if (page != null && page.getTotalElements() > 0) {
			AttenceOvertimeDTO dto = null;
			User user = null;
			Deptment dept = null;
			int index = 1;
			DateFormat tm = new SimpleDateFormat("HH:mm");
			DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

			for (AttenceOverTime p : page) {
				dto = new AttenceOvertimeDTO();
				dto.setIndex(String.valueOf(index));
				user = p.getUser();
				if (user == null) {
					dto.setUserName("");
					dto.setDeptName("");
				} else {
					dept = user.getDept();
					dto.setUserName(user.getChinesename());
					dto.setDeptName(dept == null ? "" : dept.getName());
				}
				dto.setStartTime(tm.format(p.getStartTime()));
				dto.setEndTime(tm.format(p.getEndTime()));
				dto.setOvertimeDate(p.getOvertimeDate() == null ? "" : dt.format(p.getOvertimeDate()));
				dto.setOvertimeType(p.getOvertimeType());
				dto.setCheckHours(p.getCheckHours());
				
				dto.setProjectName( p.getDailyReport().getProject()==null?"": p.getDailyReport().getProject().getProjectName());
				dto.setRemark(p.getDailyReport().getSummary());
				index++;
				dtoList.add(dto);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 加班数据图表 。
	 * 
	 */
	@RequestMapping(value = "/overtime/chart")
	@ResponseBody
	public Map<String, Object> queryAttenceOvertimePie(String rsstartDate, String rsendDate) {
		Map<String, Object> map = attenceReportService.queryAttenceOvertimePieList(rsstartDate, rsendDate);
		return map;
	}

	/**
	 * 出差记录数据统计表。 businessTrip
	 */
	@RequestMapping(value = "/travel/index")
	public ModelAndView travelIndex() {
		ModelAndView mav = new ModelAndView("attence/report/travel");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "出差统计报表");
		breadCrumbMap.put("url", "attence/report/travel/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("startDate", DateTimeUtil.getFirstDayOfMonth(new Date()));
		mav.addObject("endDate", DateTimeUtil.getLastDayOfMonth(new Date()));
		return mav;
	}

	/**
	 * 出差记录数据统计表数据源。 businessTrip
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/travel/list")
	@ResponseBody
	public Map<String, Object> queryAttenceBusinesstripList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, @RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String sstartDate, String sendDate, Long pmId) throws ParseException {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date startDate= sstartDate==null?null:DateUtils.parseDate(sstartDate, "yyyy-MM-dd");
	    Date endDate= sendDate==null?null:DateUtils.parseDate(sendDate, "yyyy-MM-dd");
		Page<AttenceTravel> page = attenceReportService.findUserTravel(startDate, endDate, pmId, pageNumber, pageSize);
		List<AttenceTravelDTO> dtoList = new ArrayList<AttenceTravelDTO>();

		if (page != null && page.getTotalElements() > 0) {
			AttenceTravelDTO dto = null;
			User user = null;
			int index = 1;
			DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Timestamp startTm = null;
			Timestamp endTm = null;
			for (AttenceTravel p : page) {
				dto = new AttenceTravelDTO();
				dto.setIndex(String.valueOf(index));
				user = p.getUser();
				if (user == null) {
					dto.setUserName("");
				} else {
					dto.setUserName(user.getChinesename());
				}
				startTm = Timestamp.valueOf(dt.format(p.getStartTime()) + " " + "12:00:00.000000000");
				endTm = Timestamp.valueOf(dt.format(p.getEndTime()) + " " + "12:00:00.000000000");
				dto.setStartTime(dt.format(p.getStartTime()));
				dto.setEndTime(dt.format(p.getEndTime()));
				dto.setStartTimeType(p.getStartTime().compareTo(startTm) > 0 ? "12点后" : "12点(含)前");
				dto.setEndTimeType(p.getEndTime().compareTo(endTm) > 0 ? "12点后" : "12点(含)前");
				dto.setAddress(p.getAddress());
				dto.setReason(p.getReason());
				index++;
				dtoList.add(dto);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 调休时数统计表。 attencestatistics
	 */
	@RequestMapping(value = "/attencestatistics/index")
	public ModelAndView workingHoursCountIndex() {
		ModelAndView mav = new ModelAndView("attence/report/attencestatistics");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "请假时数统计表");
		breadCrumbMap.put("url", "attence/report/attencestatistics/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("queryDate", DateFormatUtils.format(new Date(), "yyyy-MM"));
		mav.addObject("lastupdatedate", DateFormatUtils.format(attenceReportService.findAttenceStatisticsLastUpdate(), "yyyy-MM-dd HH:mm:ss"));
		return mav;
	}

	/**
	 * 2015/6/2修改，优化SQL语句，统计工时不正确
	 * 调休统计报表数据源。 attenceStatistics
	 * sendDate : 截止日期
	 * pmid : 查询的人员
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/attencestatistics/list")
	@ResponseBody
	public Map<String, Object> queryAttenceStatisticsList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String sendDate, Long pmId) throws ParseException {
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		User user=pmId==null||pmId==0?null:userService.findOne(pmId);
		Page<AttenceStatistics>  page = attenceReportService.findAttenceStatistics(sendDate, user, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}

	/**
	 * 考勤按月总计数据报表。 attencecount
	 */
	@RequestMapping(value = "/attencecount/index")
	public ModelAndView attenceCount() {
		ModelAndView mav = new ModelAndView("attence/report/attencecount");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "各部门每月考勤报表");
		breadCrumbMap.put("url", "attence/report/attencecount/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		StringBuffer firstDayOfMonth = new StringBuffer();
		StringBuffer lastDayOfMonth = new StringBuffer();
		firstDayOfMonth.append(DateTimeUtil.getFirstDayOfMonth());
		lastDayOfMonth.append(DateTimeUtil.getLastDayOfMonth());

		mav.addObject("firstDayOfMonth", firstDayOfMonth.toString());
		mav.addObject("lastDayOfMonth", lastDayOfMonth.toString());
		return mav;
	}

	/**
	 * 考勤按月总计数据报表数据源
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/attencecount/list")
	@ResponseBody
	public Map<String, Object> queryAttenceCountList(String startDate, String endDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer firstDayOfMonth = new StringBuffer();
		StringBuffer lastDayOfMonth = new StringBuffer();
		if (startDate == null && endDate == null) {
			firstDayOfMonth.append(DateTimeUtil.getFirstDayOfMonth());
			lastDayOfMonth.append(DateTimeUtil.getLastDayOfMonth());
		} else {
			firstDayOfMonth.append(startDate);
			lastDayOfMonth.append(endDate);
		}
		List<AttenceCountReportDTO> dtoList = attenceReportService.findAttenceCount(firstDayOfMonth.toString(), lastDayOfMonth.toString());
		map.put("aaData", dtoList);
		return map;
	}

	/**
	 * 个人请假加班次数统计表。
	 * CountState
	 */
	@RequestMapping(value = "/countstate/index")
	public ModelAndView countStateIndex() {
		ModelAndView mav = new ModelAndView("attence/report/countstate");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "个人请假加班次数统计");
		breadCrumbMap.put("url", "attence/report/countstate/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("startDate", DateTimeUtil.getFirstDayOfMonth(new Date()));
		mav.addObject("endDate", DateTimeUtil.getLastDayOfMonth(new Date()));
		return mav;
	}
	
	
	/**
	 * 个人请假加班次数报表数据源。
	 * CountState
	 */
	@RequestMapping(value="/countstate/list")
	@ResponseBody
	public Map<String,Object> queryAttenceCountStateList(@RequestParam(value="sEcho",defaultValue="1") int sEcho,
			String sstartDate,String sendDate,Long pmId){
		
		List<AttenceCountStateDTO> dtoList = attenceReportService.getUserCountState(sstartDate, sendDate, pmId);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("aaData", dtoList);
		return map;
	}
	
	
}
