package com.hsae.ims.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.service.CodeService;


@Controller
@RequestMapping("/sysconfig/code")
public class CodeController {
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping(value="/traingtype")
	@ResponseBody
	public Map<String, String> findTrainingType(){
		return codeService.findTrainingType();
	}
}
