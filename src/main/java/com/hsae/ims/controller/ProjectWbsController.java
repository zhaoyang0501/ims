package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.dto.ProjectWbsDTO;
import com.hsae.ims.entity.ProjectWbs;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.ProjectWbsService;

@Controller       
@RequestMapping("/project/wbs")
public class ProjectWbsController extends BaseController{
	
	@Autowired
	private ProjectWbsService projectWbsService;
	
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("project/wbs/index");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "project/wbs/index");
		breakcumbMap.put("name", "WBS查询");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		return mav;
	}
	
	
	/**
	 * 查询WBS列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> queryProjectWbsList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long pmId, String wbsName){
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		Page<ProjectWbs> page = projectWbsService.findProjectWbsList(pageNumber, pageSize, pmId, wbsName);
		List<ProjectWbsDTO>  dtoList = new ArrayList<ProjectWbsDTO>();
		if (page != null && page.getTotalElements() >0) {
			ProjectWbsDTO dto = null;
			int index = 1;
			for(ProjectWbs p : page){
				dto = new ProjectWbsDTO();
				dto.setIndex(String.valueOf(index));
				dto.setWbsName(p.getWbsName());
				User pm = p.getUser();
				dto.setPm(pm == null? "": pm.getChinesename());
				dto.setPhaseDR(p.getPhaseDR());
				dto.setPhaseNode(p.getPhaseNode());
				dto.setPlanStart(p.getPlanStart());
				dto.setPlanEnd(p.getPlanEnd());
				dto.setPlanHours(p.getPlanHours());
				dto.setActualStart(p.getActualStart());
				dto.setActualEnd(p.getActualEnd());
				dto.setActualHours(p.getActualHours());
				dto.setComplex(p.getComplex());
				dto.setState(p.getState());
				dto.setDelayReason(p.getDelayReason());
				dto.setDescription(p.getDescription());
				index++;
				dtoList.add(dto);
			}
		}
		map.put("aaData", dtoList);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ProjectWbs queryProjectWbs(@PathVariable("id")long id){
		return projectWbsService.findOne(id);
	        	
	}
}
