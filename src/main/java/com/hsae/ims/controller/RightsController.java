package com.hsae.ims.controller;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.Rights;
import com.hsae.ims.service.RightsService;

@Controller
@RequestMapping("/rights")
public class RightsController extends BaseController{

	@Autowired
	private RightsService rightsService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public List<Rights> getAllRights() {	
		List<Rights> rights = (List<Rights>)rightsService.findAll();		
		return rights;
	}
}
