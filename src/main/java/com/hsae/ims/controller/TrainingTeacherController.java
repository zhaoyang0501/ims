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

import com.hsae.ims.entity.TrainingTeacher;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.TrainingTeacherService;
import com.hsae.ims.service.UserService;

@Controller
@RequestMapping("/training/teacher")
public class TrainingTeacherController extends BaseController{
	
	@Autowired
	private TrainingTeacherService trainingTeacherService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private UserService userService;

	@RequestMapping("/index")
	public ModelAndView index() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "讲师库管理");
		breadCrumbMap.put("url", "training/teacher/index");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("training/teacher");
		model.addObject("breadcrumb", breadCrumbList);
		model.addObject("levels", codeService.findTrainingTeacherLevel());
		return model;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllPage(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Long userId) {
		
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Page<TrainingTeacher> list = trainingTeacherService.findAll(pageNumber, pageSize, userId);
		if(list != null && list.getSize() > 0){
			for(TrainingTeacher tt : list.getContent()){
				tt.setLevel(codeService.findTrainingTeacherLevelName(tt.getLevel()));
			}
		}
		
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value = "/all")
	@ResponseBody
	public Map<String, Object> findAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<TrainingTeacher> teacherList = trainingTeacherService.findAll();
		if (teacherList != null && teacherList.size() > 0) {
			for(TrainingTeacher teacher : teacherList){
				map.put(teacher.getId().toString(), teacher.getUser() == null ? "" : teacher.getUser().getChinesename());
			}
		}
		return map;
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TrainingTeacher findOne(@PathVariable Long id){
		return trainingTeacherService.findOne(id);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> create(@ModelAttribute TrainingTeacher tt, Long userId) {
		User u = userService.findOne(userId);
		tt.setUser(u);
		return trainingTeacherService.save(tt);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(@ModelAttribute TrainingTeacher tt, Long userId) {
		User u = userService.findOne(userId);
		tt.setUser(u);
		return trainingTeacherService.save(tt);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Long id) {
		return trainingTeacherService.delete(id);
	}
}
