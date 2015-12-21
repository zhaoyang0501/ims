package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.WorkAndHolidaySetting;
import com.hsae.ims.service.WorkAndHolidaySettingService;

@Controller
@RequestMapping("/sysconfig/workandholiday")
public class WorkAndHolidaySettingController extends BaseController{
	
	@Autowired
	private WorkAndHolidaySettingService workAndHolidaySettingService;
	

	@RequestMapping(value = "")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("sysconfig/workandholiday");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "节假日设置");
		breadCrumbMap.put("url", "sysconfig/workandholiday");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(@ModelAttribute WorkAndHolidaySetting setting){
		Map<String, Object> map = new HashMap<String, Object>();
		WorkAndHolidaySetting holiday = workAndHolidaySettingService.findWorkAndHolidaySetting(setting.getDate());
		if (holiday != null) {
			holiday.setForeworktime(setting.getForeworktime());
			holiday.setForeresttime(setting.getForeresttime());
			holiday.setAfterworktime(setting.getAfterworktime());
			holiday.setAfterresttime(setting.getAfterresttime());
			holiday.setType(setting.getType());
			holiday.setRemark(setting.getRemark());
			workAndHolidaySettingService.save(holiday);
		}else{
			workAndHolidaySettingService.save(setting);
		}
		map.put("success", 1);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/cancle/{date}")
	@ResponseBody
	public Map<String, Object> cancle(@PathVariable Date date){
		Map<String, Object> map = new HashMap<String, Object>();
		workAndHolidaySettingService.cancle(date);
		map.put("success", 1);
		map.put("msg", "操作成功");
		return map;
	}
	
	@RequestMapping("/query/{date}")
	@ResponseBody
	public WorkAndHolidaySetting save(@PathVariable Date date){
		return workAndHolidaySettingService.findWorkAndHolidaySetting(date);
	}
	
	@RequestMapping("/calendar")
	@ResponseBody
	public List<Map<String, String>> calendar(Long start, Long end){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<WorkAndHolidaySetting> holidayList = workAndHolidaySettingService.findHolidayByMonth(new java.util.Date(start*1000), new java.util.Date(end*1000));
		if (holidayList != null && holidayList.size() > 0) {
			 for(WorkAndHolidaySetting setting : holidayList){
				 Map<String, String> map = new HashMap<String, String>();
				 String titleText = "";
				 if(setting.getType() == 0){
					 titleText = "节假日";
					 map.put("color", "#999999");
				 }else{
					 titleText = "上班";
					 map.put("color", "#3a87ad");
				 }
				 map.put("title", titleText);
				 map.put("start", setting.getDate().toString());
				 map.put("end", setting.getDate().toString());
				 map.put("allDay", "false");
				 list.add(map);
			 }
		}
		return list;
	}
}
