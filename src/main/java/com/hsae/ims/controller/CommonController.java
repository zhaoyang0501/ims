package com.hsae.ims.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.Materiel;
import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DeptService;
import com.hsae.ims.service.MaterielService;
import com.hsae.ims.service.RoleService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.RightUtil;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController{
	
	
	@Autowired
	private DeptService deptService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService; 
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private MaterielService materielService;
	
	@RequestMapping(value="/dept/list")
	@ResponseBody
	public List<Deptment> findAllDept(){
		return deptService.getAllDeptment();
	}
	
	@RequestMapping(value="/materiel/list")
	@ResponseBody
	public List<Materiel> findAllMateriel(){
		return materielService.getAllMateriel();
	}
	
	@RequestMapping(value="/position/list")
	@ResponseBody
	public Map<String,String> findAllPosition(){
		return codeService.positionConfig();
	}
	
	@RequestMapping(value="/user/list")
	@ResponseBody
	public List<User> findValidUser(){
		return userService.findValidUser();
	}
	
	@RequestMapping(value="/user/train/list")
	@ResponseBody
	public List<User> findAllUserTraining(){
		return userService.findAllTraining();
	}
	
	/***
	 * 查权限范围内的用户
	 * @return
	 */
	@RequestMapping(value="/userScope/list")
	@ResponseBody
	public List<User> findScopeUser(){
		User user=userService.findOne(RightUtil.getCurrentUserId());
		if(user==null||user.getAuthorityCode()==null)
			return userService.findAll();
		else
		return userService.findUserByAuthority(user.getAuthorityScope());
	}
	
	@RequestMapping(value="/role/list")
	@ResponseBody
	public List<Role> findAllRole(){
		return roleService.findAll();
	}
}
