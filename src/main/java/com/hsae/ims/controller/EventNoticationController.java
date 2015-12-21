package com.hsae.ims.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hsae.ims.entity.EventNotication;
import com.hsae.ims.service.EventNoticationService;
import com.hsae.ims.utils.RightUtil;

@Controller
@RequestMapping("/notice")
public class EventNoticationController extends BaseController{
	
	@Autowired
	private EventNoticationService eventNoticationService; 

	@RequestMapping(value = "/toast")
	@ResponseBody
	public EventNotication findEventNotice(){
		Long cuid = RightUtil.getCurrentUserId();
		EventNotication en = null;
		List<EventNotication> list = eventNoticationService.findUser4UnreadNotices(cuid);
		if (list != null && list.size() > 0) {
			en = list.get(0);
			en.setState("1");
			eventNoticationService.save(en);
		}
		return en;
	}
}
