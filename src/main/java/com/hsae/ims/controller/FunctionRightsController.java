package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 功能权限设置controller
 * @author caowei
 *
 */
@Controller
@RequestMapping("/functionrights")
public class FunctionRightsController extends BaseController{

	private static final Logger log = Logger.getLogger(FunctionRightsController.class);
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("sysconfig/functionrights/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "功能权限管理");
		breadCrumbMap.put("url", "functionrights/index");
		
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
}
