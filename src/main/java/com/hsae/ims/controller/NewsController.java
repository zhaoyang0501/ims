package com.hsae.ims.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.controller.response.Response;
import com.hsae.ims.entity.News;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.NewsService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("sysconfig/news")
public class NewsController extends BaseController {

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("news/index");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/news/index");
		breakcumbMap.put("name", "通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("news", codeService.NewsTypeConfig());
		return mav;
	}
	
	@RequestMapping(value="/more")
	public ModelAndView more(){
		ModelAndView mav = new ModelAndView("news/more");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "news/more");
		breakcumbMap.put("name", "通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		return mav;
	}
	
	@RequestMapping(value="/info/{id}")
	public ModelAndView more(@PathVariable Long id){
		ModelAndView mav = new ModelAndView("news/info");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/news/info");
		breakcumbMap.put("name", "通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		return mav;
	}
	
	@RequestMapping(value="/create")
	public ModelAndView create(){
		ModelAndView mav = new ModelAndView("news/create");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/news/create");
		breakcumbMap.put("name", "新建通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("current", DateTimeUtil.getCurrDateStr());
		mav.addObject("types", codeService.NewsTypeConfig());
		return mav;
	}
	
	@RequestMapping(value="/edit")
	public ModelAndView edit(@RequestParam Long id){
		ModelAndView mav = new ModelAndView("news/edit");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/news/edit");
		breakcumbMap.put("name", "编辑通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("types", codeService.NewsTypeConfig());
		mav.addObject("id", id);
		return mav;
	}
	
	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, Object> page(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "25") int iDisplayLength,
			String type, String title){
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		Page<News> page = newsService.findAll(pageNumber, pageSize, type, title);
		
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value = "/home/list")
	@ResponseBody
	public List<News> list(){
		List<News> list = newsService.findLatestNews(5);
		Date d = DateTimeUtil.getFormatDateToDate(DateTimeUtil.getCurrDate());
		for(News news : list){
			if (news.getDate().equals(d)) {
				news.setNewnews(1);
			}
		}
		return list;
	}
	
	@RequestMapping(value = "/query/{id}")
	@ResponseBody
	public News queryOne(@PathVariable Long id){
		return newsService.findOne(id);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam(value = "multifile[]", required = false) MultipartFile multifile[], HttpServletRequest request, @ModelAttribute("news") News news, String typeCode, boolean topValue){
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/news";
		String filesName = "";
		if(multifile != null && multifile.length > 1){
			for(int i = 1; i< multifile.length; i++)
			{
				MultipartFile file = multifile[i];
				Long time = new Date().getTime();
				String originalFilename = file.getOriginalFilename();	//原文件名次
				String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());	//获取后缀名
				
				File targetFile = new File(path, time + "." + suffix);
				if(!targetFile.exists()){
					targetFile.mkdirs();
				}
				try {
					file.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				String fileName = time + originalFilename;
				filesName += fileName + ",";
			}
			news.setAttachment(filesName.substring(0, filesName.length() - 1));
		}
		news.setTop(topValue == true ? 1 : 0);
		Long cuid = RightUtil.getCurrentUserId();
		User user = userService.findOne(cuid);
		news.setType(codeService.findNewsCode(typeCode));
		news.setUser(user);
		newsService.save(news);
		ModelAndView mav = new ModelAndView("news/create");
		List<Map<String, String>> breakcumbList = new ArrayList<Map<String,String>>();
		Map<String, String> breakcumbMap = new HashMap<String, String>();
		breakcumbMap.put("url", "sysconfig/news/create");
		breakcumbMap.put("name", "新建通知公告");
		breakcumbList.add(breakcumbMap);
		mav.addObject("breadcrumb", breakcumbList);
		mav.addObject("current", DateTimeUtil.getCurrDateStr());
		mav.addObject("types", codeService.NewsTypeConfig());
		mav.addObject("msg", "保存成功！");
		return mav;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Response delete(Long id){
		return newsService.delete(id);
	}
}
