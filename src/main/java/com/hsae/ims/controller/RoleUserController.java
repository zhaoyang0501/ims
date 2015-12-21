package com.hsae.ims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.service.RoleService;
import com.hsae.ims.service.RoleUserService;
import com.hsae.ims.service.UserService;

@Controller
@RequestMapping("/roleuser")
public class RoleUserController extends BaseController {

	@Autowired
	private RoleUserService roleUserService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> queryAllHaveUserRoles() {

		Map<String, Object> map = new HashMap<String, Object>();
		List<Role> list = roleUserService.findAllHaveUserRoles();
		map.put("aaData", list);
		return map;
	}

	@RequestMapping(value = "/user4role/query", method = RequestMethod.GET)
	@ResponseBody
	public List<UserRole> queryUser4role(@RequestParam(value = "roleId", required = false) Long roleId) {
		return roleUserService.queryUser4role(roleId);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRoleUser(long roleId, String userIds) {
		return roleUserService.saveRoleUser(roleId, userIds);
	}
	
	@RequestMapping(value = "/role/{id}/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleUser(@PathVariable("id") long id) {
		return roleUserService.deleteByRoleId(id);
	}
}
