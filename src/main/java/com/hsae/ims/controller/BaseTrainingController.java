package com.hsae.ims.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.BaseTrainingPlanMyDTO;
import com.hsae.ims.entity.BaseTrainingPlan;
import com.hsae.ims.entity.BaseTrainingPlanCourse;
import com.hsae.ims.entity.ExamQuestion;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.BaseTrainingCourse;
import com.hsae.ims.service.BaseTrainingCourseService;
import com.hsae.ims.service.BaseTrainingPlanCourseService;
import com.hsae.ims.service.BaseTrainingPlanService;
import com.hsae.ims.service.ExamPaperService;
import com.hsae.ims.service.ExamQuestionService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.utils.CnUpperCaser;
import com.hsae.ims.utils.RightUtil;

@Controller
@RequestMapping("/basetraining")
public class BaseTrainingController extends BaseController{
	
	@Autowired
	private BaseTrainingCourseService baseTrainingCourseService;
	@Autowired
	private BaseTrainingPlanService baseTrainingPlanService;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseTrainingPlanCourseService baseTrainingPlanCourseService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private ExamQuestionService examQuestionService;
	
	/**
	 * 跳转到基础培训培训计划页面
	 * @return
	 */
	@RequestMapping(value="/plan")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("training/basetraining/plan");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "基础培训计划");
		breadCrumbMap.put("url", "training/basetraining/plan/index");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("list", baseTrainingCourseService.findAll());
		return mav;
	}
	
	/**
	 * 跳转到基础培训培训计划页面
	 * @return
	 */
	@RequestMapping(value="/planinfo")
	public ModelAndView planinfo(@RequestParam Long id){
		ModelAndView mav = new ModelAndView("training/basetraining/planinfo");
		BaseTrainingPlan plan = baseTrainingPlanService.findOne(id);
		mav.addObject("plan", plan == null ? new BaseTrainingPlan() : plan);
		return mav;
	}
	
	/**
	 * 基础培训培训计划page
	 * @return
	 */
	@RequestMapping(value="/plan/list")
	@ResponseBody
	public Map<String, Object> findAllPlan(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String plan){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<BaseTrainingPlan> page = baseTrainingPlanService.findAll(pageNumber, pageSize, plan);
		if (page != null && page.getSize() > 0) {
			for(BaseTrainingPlan p : page){
				Set<String> courseName = new HashSet<String>();
				List<String> user = new ArrayList<String>();
				List<BaseTrainingCourse> courseList = p.getCourses();
				
				if (courseList != null && courseList.size() > 0) {
					for(BaseTrainingCourse c : courseList){
						courseName.add(c.getCourseName());
					}
				}
				List<User> userList = p.getUsers();
				if (userList != null && userList.size() > 0) {
					for(User u : userList){
						user.add(u.getChinesename());
					}
				}
				p.setCourseName(courseName);
				p.setUser(user);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping(value="/plan/save")
	@ResponseBody
	@Transactional
	public Response save(@ModelAttribute("plan") BaseTrainingPlan plan, String employeeIds, String courseIds, Long examId){
		
		if(StringUtils.isNotBlank(employeeIds)){
			String[] employeeIdArray = employeeIds.split(",");
			List<User> userList = new ArrayList<User>();
			for(String emp : employeeIdArray){
				User u = userService.findOne(Long.parseLong(emp));
				userList.add(u);
			}
			plan.setUsers(userList);
		}
		if(StringUtils.isNotBlank(courseIds)){
			String[] courseArray = courseIds.split(",");
			List<BaseTrainingCourse> courseList = new ArrayList<BaseTrainingCourse>();
			for(String c : courseArray){
				BaseTrainingCourse course = baseTrainingCourseService.findOne(Long.parseLong(c));
				courseList.add(course);
			}
			plan.setCourses(courseList);
		}
		baseTrainingPlanService.save(plan);
		return new SuccessResponse("操作成功");
	}
	
	@RequestMapping(value="/plan/edit")
	@ResponseBody
	@Transactional
	public Response edit(@ModelAttribute("plan") BaseTrainingPlan plan, String employeeIds, String courseIds, Long examId){
		if (plan != null && plan.getId() > 0) {
			BaseTrainingPlan entity = baseTrainingPlanService.findOne(plan.getId());
			if(StringUtils.isNotBlank(employeeIds)){
				String[] employeeIdArray = employeeIds.split(",");
				List<User> userList = new ArrayList<User>();
				for(String emp : employeeIdArray){
					User u = userService.findOne(Long.parseLong(emp));
					userList.add(u);
				}
				entity.setUsers(userList);
			}
			if(StringUtils.isNotBlank(courseIds)){
				String[] courseArray = courseIds.split(",");
				List<BaseTrainingCourse> courseList = new ArrayList<BaseTrainingCourse>();
				for(String c : courseArray){
					BaseTrainingCourse course = baseTrainingCourseService.findOne(Long.parseLong(c));
					courseList.add(course);
				}
				entity.setCourses(courseList);
			}
			baseTrainingPlanService.save(entity);
			entity.setTitle(plan.getTitle());
			entity.setDescription(plan.getDescription());
			entity.setTargets(plan.getTargets());
			entity.setStart(plan.getStart());
			entity.setEnd(plan.getEnd());
			entity.setRemarks(plan.getRemarks());
		}
		return new SuccessResponse("操作成功");
	}
	
	@RequestMapping(value="/plan/delete")
	@ResponseBody
	@Transactional
	public Response delete(Long id){
		baseTrainingPlanService.delete(id);
		baseTrainingPlanCourseService.delete(id);
		return new SuccessResponse("操作成功");
	}
	
	@RequestMapping(value="/plan/query/{id}")
	@ResponseBody
	public Object queryOne(@PathVariable Long id){
		BaseTrainingPlan plan = baseTrainingPlanService.findOne(id);
		List<BaseTrainingPlanCourse> list = baseTrainingPlanCourseService.findByPlan(id);
		List<Long> user = new ArrayList<Long>();
		List<Long> course = new ArrayList<Long>();
		if (list != null && list.size() > 0) {
			for(BaseTrainingPlanCourse c : list){
				user.add(c.getUser().getId());
				course.add(c.getCourse().getId());
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("plan", plan);
		map.put("user", user);
		map.put("course", course);
		return map;
	}
	
	@RequestMapping("/my")
	public ModelAndView myIndex(){
		ModelAndView mav = new ModelAndView("training/basetraining/myindex");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "我的基础培训");
		breadCrumbMap.put("url", "training/basetraining/my");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping(value = "/my/list")
	@ResponseBody
	public Map<String, Object> myList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Long cuid = RightUtil.getCurrentUserId();
		
		Page<BaseTrainingPlan> page = baseTrainingPlanService.findMyBaseTraining(pageNumber, pageSize, cuid);
		List<BaseTrainingPlanMyDTO> list = new ArrayList<BaseTrainingPlanMyDTO>();
		if (page != null && page.getSize() > 0) {
			for(BaseTrainingPlan plan : page){
				BaseTrainingPlanMyDTO dto = new BaseTrainingPlanMyDTO();
				dto.setTitle(plan.getTitle());
				dto.setDescription(plan.getDescription());
				dto.setTargets(plan.getTargets());
				dto.setStart(plan.getStart());
				dto.setEnd(plan.getEnd());
				List<String> courseNames = new ArrayList<String>();
				List<BaseTrainingCourse> courses = plan.getCourses();
				if (courses != null && courses.size() > 0) {
					for(BaseTrainingCourse c : courses){
						courseNames.add(c.getCourseName());
					}
				}
				dto.setCourseNames(courseNames);
				
				list.add(dto);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	@RequestMapping("/studyonline")
	public ModelAndView studyonline(ModelMap model, HttpServletRequest request, @RequestParam Long id, @RequestParam String state) throws Exception{
		BaseTrainingPlanCourse planCourse = baseTrainingPlanCourseService.findOne(id);
		ModelAndView mav = new ModelAndView("training/basetraining/studyonline");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "在线学习");
		breadCrumbMap.put("url", "training/basetraining/studyonline");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		String fileName = planCourse == null ? "" : planCourse.getCourse().getMaterials();
		model.put("pdfFile", fileName.substring(0, 13)+fileName.substring(fileName.lastIndexOf(".")));
		model.put("id", id);
		model.put("state", state);
		return mav;
	}
	
	/***
	 * 学习完成时的状态更新
	 * @param state
	 * @return
	 */
	@RequestMapping("/studyonline/state")
	@ResponseBody
	public Response studyOK(Integer state, Long planCourseId){
		BaseTrainingPlanCourse planCourse = baseTrainingPlanCourseService.findOne(planCourseId);
		if (planCourse != null && planCourse.getId() > 0) {
			planCourse.setState(state);
			baseTrainingPlanCourseService.save(planCourse);
			return new SuccessResponse("学习完毕。");
		}else{
			return new FailedResponse();
		}
	}
	
	/***
	 * 跳转到答题页面
	 */
	@RequestMapping(value = "/exam/answer")
	public ModelAndView answer(@RequestParam Long planCourseId) {
		ModelAndView mav = new ModelAndView("exam/answer");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "答题");
		breadCrumbMap.put("url", "exam/answer");

		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		//答案列表
		BaseTrainingPlanCourse planCourse = baseTrainingPlanCourseService.findOne(planCourseId);
		
		Long paperId = planCourse.getCourse().getExam().getId();
		char[] answers={'A','B','C','D','E','F','G','H','I'};
		List<ExamQuestion> list = examQuestionService.findByPaper(paperId);
		String qIds = "";
		if (list != null && list.size() > 0) {
			Integer num = 1;
			for(ExamQuestion exam : list){
				exam.setQnum(CnUpperCaser.getCnString(num.toString()));
				num ++;
				String qId = "question_" + exam.getId() + "_";
				Integer options = exam.getOptions();
				switch (exam.getType()) {
				case 1:
					//设置答案列表
					exam.setQoptions(Arrays.copyOfRange(answers, 0, options));
					//设置前台试题ID
					qId += "radio";
					exam.setqId(qId);//单选
					break;
				case 2:
					exam.setQoptions(Arrays.copyOfRange(answers, 0, options));
					qId += "check";
					exam.setqId(qId);//多选
					break;
				case 3:
					qId += "tfng";
					exam.setqId(qId);//判断
					break;
				case 4:
					qId += "essay";
					exam.setqId(qId);//主观
					break;
				default:
					break;
				}
				qIds += qId + ",";
			}
		}
		mav.addObject("subject", planCourse.getCourse().getExam().getSubject());
		mav.addObject("planCourseId", planCourseId);
		mav.addObject("paperId", paperId);	//试卷ID
		//试题ID 集合
		mav.addObject("qIds", StringUtils.isNotBlank(qIds) ? qIds.substring(0, qIds.length() - 1) : "");
		mav.addObject("list", list);//试题 列表
		return mav;
	}
	
	@RequestMapping("/course")
	public ModelAndView course(ModelMap model, HttpServletRequest request){
		ModelAndView mav = new ModelAndView("training/basetraining/course");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "在线学习课程管理");
		breadCrumbMap.put("url", "training/basetraining/course");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("examList", examPaperService.findAll());
		return mav;
	}
	
	@RequestMapping(value="/course/save", method = RequestMethod.POST)
	@ResponseBody
	public Response courseSave(@ModelAttribute("entity") BaseTrainingCourse entity, Long examId){
		if (examId != null) {
			entity.setExam(examPaperService.findOne(examId));
		}
		baseTrainingCourseService.save(entity);
		return new SuccessResponse("操作成功！");
	}
	
	/**
	 * 培训课程资料上传
	 * @param multifile
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/course/material/upload", method = RequestMethod.POST)
	public String courseMaterialUpload(Model model, @RequestParam(value = "multifile[]", required = false) MultipartFile multifile[], 
			HttpServletRequest request, Long courseId, RedirectAttributes redirectAttributes){
		String path = request.getSession().getServletContext().getRealPath("/") + "upload/basetraining";
		String filesName = "";
		if (courseId > 0) {
			BaseTrainingCourse course = baseTrainingCourseService.findOne(courseId);
			if (course != null && course.getId() > 0) {
				//TODO 删除已存在的文件
				if(multifile != null && multifile.length > 1){
					for(int i = 1; i< multifile.length; i++)
					{
						//TODO 文件类型验证，暂时只支持pdf文件
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
					course.setMaterials(filesName.substring(0, filesName.length() - 1));
				}
				baseTrainingCourseService.save(course);
			}
		}
		
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "在线学习课程管理");
		breadCrumbMap.put("url", "training/basetraining/course");
		breadCrumbList.add(breadCrumbMap);
		model.addAttribute("breadcrumb", breadCrumbList);
		model.addAttribute("examList", examPaperService.findAll());
		redirectAttributes.addFlashAttribute("msg", "上传成功");
		return "redirect:/basetraining/course";
	}
	
	@RequestMapping("/course/query/{id}")
	@ResponseBody
	public BaseTrainingCourse findOneCourse(@PathVariable Long id){
		if (id != null) {
			return baseTrainingCourseService.findOne(id);
		}
		return null;
	}
	
	@RequestMapping("/course/delete")
	@ResponseBody
	public Response deleteCourse(Long id){
		if (baseTrainingCourseService.delete(id)) {
			return new SuccessResponse("操作成功");
		}else{
			return new FailedResponse();
		}
	}
	
	/**
	 * course online page
	 * @return
	 */
	@RequestMapping(value="/course/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllCourse(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart, 
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			@RequestParam(value = "sSortDir_0", defaultValue = "asc") String sSortDir_0,
			String courseName, String courseType){
		int pageNumber = (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Page<BaseTrainingCourse> page = baseTrainingCourseService.getAllCourse(pageNumber, pageSize, courseName, courseType);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
}
