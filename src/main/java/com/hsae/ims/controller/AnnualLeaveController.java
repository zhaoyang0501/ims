package com.hsae.ims.controller;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.hsae.ims.entity.AttenceStatisticsYear;
import com.hsae.ims.service.AttenceStatisticsYearService;
/***
 * 年假设置
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/sysconfig/annualleave")
public class AnnualLeaveController {

	@Autowired
	private AttenceStatisticsYearService attenceStatisticsYearService;
	
	@RequestMapping(value = "/index")
	public ModelAndView absenteeIndex() {
		ModelAndView mav = new ModelAndView("sysconfig/annualleave/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "年假设置");
		breadCrumbMap.put("url", "sysconfig/annualleave/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("nowYear", DateFormatUtils.format(new Date(), "yyyy"));
		return mav;
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> list(String year, Long userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<AttenceStatisticsYear> attenceStatisticsYears = attenceStatisticsYearService.findAll(year, null);
		map.put("aaData", attenceStatisticsYears);
		return map;
	}
	@RequestMapping(value = "/update")
	@ResponseBody
	public Map<String, Object> update(Long pk, Double value) {
		Map<String, Object> map = new HashMap<String, Object>();
		AttenceStatisticsYear attenceStatisticsYear=attenceStatisticsYearService.find(pk);
		attenceStatisticsYear.setCurrentIncrease(value);
		attenceStatisticsYearService.save(attenceStatisticsYear);
		return map;
	}
}
