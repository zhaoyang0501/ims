package com.hsae.ims.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.RoleRights;
import com.hsae.ims.service.RoleRightsService;

@Controller
@RequestMapping("/rolerights")
public class RoleRightsController extends BaseController{

	@Autowired
	private RoleRightsService roleRightsService;
	
	@RequestMapping(value = "/list/role/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<RoleRights> queryRoleRightsByRole(@PathVariable long id){
		return roleRightsService.queryByRole(id);
	}
}
