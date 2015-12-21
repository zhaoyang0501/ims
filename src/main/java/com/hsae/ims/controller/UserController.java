package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
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

import com.hsae.ims.dto.UserDTO;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserResumeRepository userResumeRepository;
	@Autowired
	private CodeService codeService;

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("sysconfig/user/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "用户管理");
		breadCrumbMap.put("url", "user/index");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryUserList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String chinesename, String empnumber, Long deptId, Integer freeze) {
		
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		
		Page<User> list = userService.pageAllUser(sSortDir_0, chinesename, empnumber, deptId, freeze, pageNumber, pageSize);
		
		List<UserDTO> dtoList = new ArrayList<UserDTO>();
		
		if (list != null && list.getTotalElements() > 0) {
			UserDTO dto = null;
			UserResume  ur = null;
			for (User  p : list){
				dto = new UserDTO();
				dto.setId(p.getId().toString());
				dto.setUsername(p.getUsername()==null?"":p.getUsername());
				dto.setChinesename(p.getChinesename()==null?"":p.getChinesename());
				dto.setDept(p.getDept()==null?"":p.getDept().getName()==null?"":p.getDept().getName());
				dto.setSex(p.getSex());
				dto.setEmpnumber(p.getEmpnumber()==null?"":p.getEmpnumber());
				dto.setEmail(p.getEmail()==null?"":p.getEmail());
				dto.setFreeze(p.getFreeze());
				ur = userResumeRepository.findUserResumeByUser(p);
				if (ur != null){
					dto.setPosition(ur.getPosition()==0?"":codeService.findPositionName(ur.getPosition().toString()));
				}else{
					dto.setPosition("");
				}
				dtoList.add(dto);
			}
			
		}
		map.put("aaData", dtoList);
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value = "/queryuser/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public UserDTO queryResume(@PathVariable long id) {
		return userService.findUserResume(id);
	}
	
	
	@RequestMapping(value = "/query/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public User query(@PathVariable long id) {
		User user=userService.findOne(id);
		return user==null?new User():user;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean create(@ModelAttribute("user") User user, String deptId, Integer position) {
		return userService.save(deptId,position, user);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean update(@ModelAttribute("user") User user, String deptId, Integer position) {
		return userService.save(deptId,position, user);
	}
	
	/**
	 * 用户冻结
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/freeze/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean freezeUser(@PathVariable long id) {
		return userService.freezeUser(id);
	}
	
	/**
	 * 用户解冻
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/unfreeze/{id}", method = RequestMethod.GET)
	@ResponseBody
	public boolean unFreezeUser(@PathVariable long id) {
		return userService.unFreezeUser(id);
	}
	
	/**
	 * 密码重置
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/pwd/reset", method = RequestMethod.POST)
	@ResponseBody
	public boolean resetpwd(@PathVariable long id) {
		String defaultpwd = "e10adc3949ba59abbe56e057f20f883e";	//123456 md5加密之后
		return userService.resetpwd(id, defaultpwd);
	}
	
	/**
	 * 密码修改
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/pwd/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> udpatepwd(String pwd_old, String pwd) {
		return userService.updatepwd(pwd_old, pwd);
	}
	
	/**
	 * 个人档案修改
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/profile/update", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> udpateprofile(@ModelAttribute("user") User user) {
		return userService.updateProfile(user);
	}
}
