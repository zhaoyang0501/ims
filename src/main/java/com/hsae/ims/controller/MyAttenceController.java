package com.hsae.ims.controller;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.AttenceStatistics;
import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.AttenceAbsenteeService;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.AttenceDayOffService;
import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.AttenceTravelService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.HomeService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.RightUtil;
/***
 * 我的考勤
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/home/myattence")
public class MyAttenceController {
	
	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private AttenceOverTimeService attenceOverTimeService;
	
	@Autowired
	private AttenceTravelService attenceTravelService;
	
	@Autowired
	private AttenceDayOffService attenceDayOffService;
	
	@Autowired
	private AttenceAbsenteeService attenceAbsenteeService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("home/myattence");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的考勤");
		breadCrumbMap.put("url", "home/myattence/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		Map<String,String> monthMap=new LinkedHashMap<String,String>();
		Date now=new Date();
		monthMap.put(DateFormatUtils.format(DateUtils.addMonths(now, -5) , "yyyy-MM"), DateFormatUtils.format(DateUtils.addMonths(now, -5) , "MM"));
		monthMap.put(DateFormatUtils.format(DateUtils.addMonths(now, -4) , "yyyy-MM"), DateFormatUtils.format(DateUtils.addMonths(now, -4) , "MM"));
		monthMap.put(DateFormatUtils.format(DateUtils.addMonths(now, -3) , "yyyy-MM"), DateFormatUtils.format(DateUtils.addMonths(now, -3) , "MM"));
		monthMap.put(DateFormatUtils.format(DateUtils.addMonths(now, -2) , "yyyy-MM"), DateFormatUtils.format(DateUtils.addMonths(now, -2) , "MM"));
		monthMap.put(DateFormatUtils.format(DateUtils.addMonths(now, -1) , "yyyy-MM"), DateFormatUtils.format(DateUtils.addMonths(now, -1) , "MM"));
		monthMap.put(DateFormatUtils.format(now, "yyyy-MM"), "当月");
		mav.addObject("monthMap", monthMap);
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
			String date) throws ParseException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		Page<AttenceBrushRecord> list = attenceBrushRecordService.findBrushRecord(date,RightUtil.getCurrentUserId(),pageNumber, 100);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		/***获取某人某月的考勤*/
		AttenceStatistics attenceStatistics=homeService.findAttenceStatic(RightUtil.getCurrentUserId(), date);
		map.put("attence" ,attenceStatistics);
		map.put("attenceCount" ,homeService.findUserAttenceCount(RightUtil.getCurrentUserId(), date));
		map.put("rank" ,attenceOverTimeService.getRank(date, attenceStatistics!=null?attenceStatistics.getCurrentIncrease():0));
		return map;
	}
	
	/***
	 * 获取某人某天的考勤详细，加班、请假等
	 * @param attenceId
	 * @return
	 */
	@RequestMapping(value = "/queryAttenceDetail/{attenceId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryAttenceDetail(@PathVariable Long attenceId){
		Map<String, Object> map = new HashMap<String, Object>();
		AttenceBrushRecord attenceBrushRecord=attenceBrushRecordService.findById(attenceId);
		User user=userService.findOne(attenceBrushRecord.getPersonId());
		List<AttenceOverTime> attenceOverTimes=attenceOverTimeService.findByUserAndOvertimeDate(user, attenceBrushRecord.getBrushDate());
		List<AttenceDayoff> attenceDayoffs=attenceDayOffService.findAttenceDayoff(user, attenceBrushRecord.getBrushDate());
		AttenceAbsentee attenceAbsentee=attenceAbsenteeService.findAbsenteee(user, attenceBrushRecord.getBrushDate());
		AttenceTravel attenceTravel=attenceTravelService.findAttenceTravel(user, attenceBrushRecord.getBrushDate());
		map.put("attenceOverTimes", attenceOverTimes);
		map.put("attenceDayoffs", attenceDayoffs);
		map.put("attenceAbsentee", attenceAbsentee);
		map.put("attenceTravel", attenceTravel);
		return map;
	}
	
	/***
	 * 获取漏打卡类型、请假类型的码表
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/getAttenceCode")
	@ResponseBody
	public Map<String,Map<String,String>> getAttenceCode() throws ParseException{
		Map<String, Map<String,String>> map = new HashMap<String, Map<String,String>>();
		map.put("absentee",  codeService.findAbsenteeTypeForMap());
		map.put("dayoff",  codeService.findDayOffTypeForMap());
		return map;
	} 
}
