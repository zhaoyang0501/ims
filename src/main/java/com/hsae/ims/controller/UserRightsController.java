package com.hsae.ims.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRights;
import com.hsae.ims.service.UserRightsService;

@Controller
@RequestMapping("/userrights")
public class UserRightsController extends BaseController{

	@Autowired
	private UserRightsService userRightsService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String, Object> queryAll(){
		Map<String, Object> map = new HashMap<String, Object>();
		List< User> list = userRightsService.findAllHaveRightsUser();
		map.put("aaData", list);
		return map;
	}
	
	@RequestMapping(value="/user/{id}/query")
	@ResponseBody
	public List< UserRights> queryUserRights(@PathVariable long id){
		
		return userRightsService.findByUserId(id);
	}
	
	@RequestMapping(value="/user/{id}/delete")
	@ResponseBody
	public Map<String, Object> deleteUserRights(@PathVariable long id){
		
		return userRightsService.deleteByUserId(id);
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createUserRights(long userId, String rightsIds){
		
		return userRightsService.saveUserRights(userId, rightsIds);
	}
	
}
