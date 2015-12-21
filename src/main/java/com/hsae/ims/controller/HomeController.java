package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.dto.AttenceStaticsDTO;
import com.hsae.ims.dto.AttenceStaticsDetailsDTO;
import com.hsae.ims.dto.UserAwardDTO;
import com.hsae.ims.dto.UserBeforeExpDTO;
import com.hsae.ims.dto.UserEducationDTO;
import com.hsae.ims.dto.UserFamilyDTO;
import com.hsae.ims.dto.UserNowExpDTO;
import com.hsae.ims.dto.UserResumeDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.service.HomeService;
import com.hsae.ims.service.UserResumeService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserResumeService userResumeService;
	
	@Autowired
	private UserResumeRepository userResumeRepository;
	
	@RequestMapping(value = "/profile")
	public ModelAndView profile() {
		ModelAndView mav = new ModelAndView("home/profile");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "个人资料");
		breadCrumbMap.put("url", "home/profile");
		
		long userId = RightUtil.getCurrentUserId();
		User u = userService.findOne(userId);
		mav.addObject("user", u);
		
		UserResume ur = userResumeRepository.findUserResumeByUser(u);
		
		long resumeId = 0;
		
		if (ur !=null){
			resumeId = ur.getId();
		}
		
		UserResumeDTO resume = userResumeService.findResumeById(resumeId);;
		List<UserAwardDTO> award = userResumeService.findUserAwardByResumeId(resumeId);
		List<UserEducationDTO> education = userResumeService.findUserEducationByResumeId(resumeId);
		List<UserFamilyDTO> family = userResumeService.findUserFamilyByResumeId(resumeId);
		List<UserBeforeExpDTO> beforeExp = userResumeService.findUserBeforeExpByResumeId(resumeId);
		List<UserNowExpDTO> nowExp = userResumeService.findUserNowExpByResumeId(resumeId);

		mav.addObject("resume", resume);
		mav.addObject("award", award);
		mav.addObject("education", education);
		mav.addObject("family", family);
		mav.addObject("beforeExp", beforeExp);
		mav.addObject("nowExp", nowExp);
		
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}

	@RequestMapping("/attence")
	public ModelAndView attendance(){
		ModelAndView mav = new ModelAndView("home/attence");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的考勤");
		breadCrumbMap.put("url", "/home/attence");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		Long cuid = RightUtil.getCurrentUserId();
		AttenceStaticsDTO attence = homeService.queryAttenceStatics(cuid);
		mav.addObject("attence", attence);
		return mav;
	}
	
	@RequestMapping(value="/attence/list",method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String,Object> queryAttenceStaticsDetails(@RequestParam(value="sEcho",defaultValue="1") int sEcho,
			@RequestParam(value="iDisplayStart",defaultValue="0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "fromDate", defaultValue = "") String fromDate,
			@RequestParam(value = "toDate", defaultValue = "") String toDate,
			@RequestParam(value = "type", defaultValue = "") String type){
		int pageNumber = (iDisplayStart/iDisplayLength) + 1;
		int pageSize =  iDisplayLength;
		Map<String,Object> map = new HashMap<String,Object>();
		Long cuid = RightUtil.getCurrentUserId();
		List<AttenceStaticsDetailsDTO> list = homeService.queryAttenceStaticsDetails(pageNumber, pageSize, fromDate, toDate, type, cuid);
		
		map.put("aaData", list);
		map.put("iTotalRecords", list.size());
		map.put("iTotalDisplayRecords", list.size());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping("/buglist")
	public ModelAndView buglist(){
		ModelAndView mav = new ModelAndView("home/buglist");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的buglist");
		breadCrumbMap.put("url", "/home/buglist");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping("/dailyreport")
	public ModelAndView dailyreport(){
		ModelAndView mav = new ModelAndView("home/dailyreport");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的日报");
		breadCrumbMap.put("url", "/home/dailyreport");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("fromDate", DateTimeUtil.getFirstDayOfMonth());
		mav.addObject("toDate", DateTimeUtil.getLastDayOfMonth());
		return mav;
	}
	
	/**
	 * 日报类型分布图
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/dailyreport/pie")
	@ResponseBody
	public Map<String, Object> queryDailyReportPie(String startDate, String endDate){
		Map<String, Object> map = new HashMap<String, Object>();
		Long cuid =RightUtil.getCurrentUserId();
		List<Map<String, Object>> list = homeService.queryDailyReportTypePieList(startDate, endDate, cuid);
		map.put("data", list);
		//pie汇总数据计算
		Double totalHours = 0d;
		for(Map<String, Object> m : list){
			if (!m.isEmpty()) {
				totalHours += (Double) m.get("value");
			}
		}
		List<Map<String, Object>> sumList = list;
		for(Map<String, Object> m : sumList){
			if (!m.isEmpty()) {
				m.put("percent", (int)((Double) m.get("value") / totalHours * 100) + "%");
			}
		}
		map.put("staticsData", sumList);
		return map;
	}
	
	@RequestMapping("/project")
	public ModelAndView project(){
		ModelAndView mav = new ModelAndView("home/project");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的项目");
		breadCrumbMap.put("url", "/home/project");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping("/task")
	public ModelAndView task(){
		ModelAndView mav = new ModelAndView("home/task");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的Task");
		breadCrumbMap.put("url", "home/task");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	/***
	 * 跳转到待办事项
	 * @return
	 */
	@RequestMapping("/toapprove")
	public ModelAndView toapprove() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		breadCrumbList.add(getBreadCrumbMap("workflow/toapprove","待办事项"));
		ModelAndView model = new ModelAndView("workflow/toapprove/index");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("workflowNames", WorkFlowConstants.WORKFLOWNAME_MAP);
		return model;
	}
	private Map<String,String> getBreadCrumbMap(String url,String name){
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", name);
		breadCrumbMap.put("url", url);
		return breadCrumbMap;
	}
}
