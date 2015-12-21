package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.dto.ProjectDTO;
import com.hsae.ims.dto.ProjectMemberDTO;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.ProjectService;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("project/index");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "project/index");
		breakcumbMap.put("name", "项目管理");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		return mav;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam("id") long id){
		ModelAndView mav = new ModelAndView("project/edit");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "project/index");
		breakcumbMap.put("name", "项目管理");
		breakcumbList.add(breakcumbMap);
		Map<String, String> breakcumbMap1 = new HashMap<String, String>();
		breakcumbMap1.put("url", "project/edit");
		breakcumbMap1.put("name", "项目编辑");
		breakcumbList.add(breakcumbMap1);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("id", id);
		Map<String, String> roles = codeService.findProjectMemberRole();
		mav.addObject("roles", roles);
		return mav;
	}
	
	/**
	 * 查询项目列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> queryProjectList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long pmId, String projectName){
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		Page<Project> page = projectService.findProjectList(pageNumber, pageSize, pmId, projectName);
		List<ProjectDTO>  dtoList = new ArrayList<ProjectDTO>();
		if (page != null && page.getTotalElements() >0) {
			ProjectDTO dto = null;
			int index = 1;
			for(Project p : page){
				dto = new ProjectDTO();
				dto.setId(p.getId());
				dto.setIndex(String.valueOf(index));
				dto.setProjectName(p.getProjectName());
				dto.setProjectCode(p.getProjectCode());
				dto.setComplex(p.getComplex());
				User pm = p.getPm();
				dto.setPm(pm == null? "": pm.getChinesename());
				dto.setCustomer(p.getCustomer());
				dto.setState(p.getState());
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
	public Project queryProject(@PathVariable("id")long id){
		return projectService.findOne(id);
	}
	
	/**
	 * 创建项目
	 * @return
	 */
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createProject(@ModelAttribute("project") Project project, long pmId){
		Map<String, Object> map = projectService.createProject(project, pmId);
		return map;
	}
	
	/**
	 * 更新项目
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateProject(@ModelAttribute("project") Project project, long pmId){
		Map<String, Object> map = projectService.updateProject(project, pmId);
		return map;
	}
	
	/**
	 * 删除项目
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProject(long id){
		Map<String, Object> map = projectService.deleteProject(id);
		return map;
	}
	
	
	/**
	 * 创建项目成员配置。
	 * @return
	 */
	@RequestMapping(value="/member/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createProjectMember(long id, String users, String role){
		return projectService.createProjectMember(id, users, role);
	}
	
	/**
	 * 删除项目成员配置。
	 * @return
	 */
	@RequestMapping(value="/member/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteProjectMember(long id){
		return projectService.deleteProjectMember(id);
	}
	
	
	/**
	 * 项目成员列表
	 * @return
	 */
	@RequestMapping(value="/member/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findProjectMemberList(@RequestParam("pId") long pId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectMemberDTO> list = projectService.findProjectMemberList(pId);
		map.put("aaData", list);
		map.put("iTotalRecords", list.size());
		map.put("iTotalDisplayRecords", list.size());
		return map;
	}
}
