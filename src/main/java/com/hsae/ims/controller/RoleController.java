package com.hsae.ims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.Role;
import com.hsae.ims.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	@Autowired
	private RoleService roleService;

	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String, Object> queryAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Role> list = roleService.findAll();
		map.put("aaData", list);
		return map;
	}
	
	@RequestMapping(value="/create")
	@ResponseBody
	public Map<String, Object> createRole(@ModelAttribute("role") Role role, String rightsIds){
		return roleService.saveRole(role, rightsIds);
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Map<String, Object> updateRole(@ModelAttribute("role") Role role, String rightsIds){
		return roleService.updateRole(role, rightsIds);
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public Map<String, Object> deleteRole(long id){
		return roleService.deleteRole(id);
	}
}
