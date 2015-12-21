package com.hsae.ims.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.dto.WeekReportStateDto;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.DailyReportWeekConfigService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
/***
 * 周报提交状态
 * @author panchaoyang
 *
 */
@Controller
@RequestMapping("/dailyReport/weekReportState")
public class WeekReportStateController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(WeekReportStateController.class);
	@Autowired
	private  WeekReportService weekReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private DailyReportWeekConfigService dailyReportWeekConfigService;
	/***
	 	首页
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		log.info("WeekReportStateController.index()");
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报提交统计查询");
		breadCrumbMap.put("url", "dailyReport/weekReportState/");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("dailyreport/weekReportState/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/***
	 * 查询周报提交状态
	 * @param weekId
	 * @param state
	 * @return
	 */
	@RequestMapping(value="/queryAll", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAll(Long weekId,Integer state){
		 Map<String,Object> map=new HashMap<String,Object>();
		 List<User> users= userService.findNeedWeekReportUser();
		 List<WeekReport> weekReports=weekReportService.findByWeek(weekId);
		 List<WeekReportStateDto> dtos= new ArrayList<WeekReportStateDto>();
		 for(User user:users){
			 WeekReportStateDto dto=new WeekReportStateDto();
			 dto.setEmpnumber(user.getEmpnumber());
			 dto.setName(user.getChinesename());
			 dto.setState(0);
			 for(WeekReport weekReport:weekReports){
				 if(weekReport.getCreater().getId().equals(user.getId())) {
					 dto.setState(1);
					 break;
				 } 
				 dto.setState(0);
			 }
			 if(state==null||dto.getState()==state)
				 dtos.add(dto);
		 }
		 
		 /**计算提交人数、未提交人数，总人数*/
		 int totalCount=0;
		 int submitCount=0;
		 int noSubmitCount=0;
		 for(WeekReportStateDto dto:dtos){
			 totalCount++;
			 if(dto.getState()==1) submitCount++;
			 if(dto.getState()==0) noSubmitCount++;
		 }
		 map.put("totalCount", totalCount);
		 map.put("submitCount", submitCount);
		 map.put("noSubmitCount", noSubmitCount);
		 
		 map.put("aaData", dtos);
		return map;
	}
	/***
	 * 初始化周次下拉框
	 * @return
	 */
	@RequestMapping(value="/allWeekConfig")
	@ResponseBody
	public Map<String,Object> allWeekConfig(){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("map",dailyReportWeekConfigService.findAll());
		return map;
	}
}
