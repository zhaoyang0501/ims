package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/survey")
public class SurveyController extends BaseController{

	@RequestMapping(value="/training")
	public ModelAndView tr(){
		ModelAndView mav = new ModelAndView("training/survey/trsurvey");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "survey/training");
		breakcumbMap.put("name", "培训问卷调查");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		return mav;
	}
}
