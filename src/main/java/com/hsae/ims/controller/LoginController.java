package com.hsae.ims.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.entity.AuthenticatedUser;

@Controller
public class LoginController {
	/***
	 * IMS 首页，如果登录了显示首页，否则显示登录页面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String  index(HttpSession session,Model model) {
		Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		principal.getClass().isInstance(String.class);
		if(principal instanceof AuthenticatedUser){
			return "index";
		}else{
			return "login";
		}
	}
	/***
	 * ERROR视图
	 * @return
	 */
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public ModelAndView error() {
		ModelAndView mv = new ModelAndView("error");
		return mv;
	}
	/***
	 * 点击退出按钮
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logoutsuccess", method = RequestMethod.GET)
	public String logoutsuccess(Model model) {
		return "login";
	}
	/***
	 * 登录失败
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/loginerror")
	public String loginerror(Model model) {
		model.addAttribute("tip", "用户名密码不正确");
		return "login";
	}
}
