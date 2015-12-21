package com.hsae.ims.controller;

import java.beans.PropertyEditor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.TrainingPlan;
import com.hsae.ims.service.ActivitiService;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.TrainingPlanService;
import com.hsae.ims.service.UserService;

@Controller
@RequestMapping("/workflow/plan")
public class WorkflowPlanController{
	
	@Autowired
	private TrainingPlanService trainingPlanService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProcessEngine processEngine;
	
	@Autowired
	private ActivitiService activitiService;
	
	private static final Logger log = LoggerFactory.getLogger(WorkflowPlanController.class);
	@InitBinder
    public void dataBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        PropertyEditor propertyEditor = new CustomDateEditor(dateFormat, true );
        binder.registerCustomEditor(Date.class , propertyEditor);
    }
	
	@RequestMapping("")
	public ModelAndView index() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "培训计划流程");
		breadCrumbMap.put("url", "workflow/plan");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("workflow/plan/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	
	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("workflow/plan/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan", "培训计划流程"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan/create", "创建详细计划"));
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping("/view/{id}")
	public ModelAndView view(@PathVariable Long id) {
		TrainingPlan plan=trainingPlanService.findOne(id);
		ModelAndView mav = new ModelAndView("workflow/plan/approve");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan", "培训计划"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan/create", "培训计划"));
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("plan", plan);
		
		
		return mav;
	}
	@RequestMapping("/save")
	public ModelAndView save(TrainingPlan trainingPlan,String planUserIds) {
		trainingPlanService.save(trainingPlan);
		
		ModelAndView mav = new ModelAndView("workflow/training/view");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/training", "培训计划"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/training/create", "培训计划"));
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	@RequestMapping("/send/{planid}")
	@ResponseBody
	public Response send(@PathVariable Long planid) {
		/** 检查是否已经发起*/
		TrainingPlan trainingPlan = trainingPlanService.findOne(planid);
		if(trainingPlan.getProcessInstanceId()!=null){
			return new FailedResponse("400","已经在流转中");
		}else{
			Map<String,Object> activtiMap=new HashMap<String,Object>();
			
			activtiMap.put("teacher", trainingPlan.getPlanTeacher().getId().toString());
			
			ProcessInstance processInstance=processEngine.getRuntimeService().
				startProcessInstanceByKey("trainingplan", trainingPlan.getId().toString(),activtiMap);
			
			trainingPlan.setProcessInstanceId(processInstance.getProcessInstanceId());
			trainingPlanService.save(trainingPlan);
			/***/
			List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee(trainingPlan.getPlanTeacher().getId().toString()).list();
			log.info("流程实例Id->"+processInstance.getId());
			log.info("当前待办事项->"+tasks.size());
			return new  SuccessResponse("流程发起成功，已发送给讲师"+trainingPlan.getPlanTeacher().getChinesename());
		}
			
	}
	/***
	 * 跳转到表单详情
	 * @param taskid
	 * @param prcessInstanceid
	 * @return
	 */
	@RequestMapping("/viewapprive/{taskid}/{prcessInstanceid}")
	public ModelAndView viewapprive(@PathVariable String taskid,@PathVariable String prcessInstanceid) {
		
		ProcessInstance processInstance=processEngine.getRuntimeService().
				createProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult();
		
		String planid ="";
		if(processInstance==null) 
			planid=processEngine.getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceid).singleResult().getBusinessKey();
		else
			planid = processInstance.getBusinessKey();
		
		TrainingPlan trainingPlan = trainingPlanService.findOne(Long.parseLong(planid));
		
		ModelAndView mav = new ModelAndView("workflow/plan/approve");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan", "培训计划"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan/create", "培训计划"));
		
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("plan", trainingPlan);
		mav.addObject("task", processEngine.getTaskService().createTaskQuery().taskId(taskid).singleResult());
		mav.addObject("taskhistory", activitiService.findHistoryTask(prcessInstanceid));
		mav.addObject("prcessInstanceid", prcessInstanceid);
		return mav;
	}
	
	/***
	 * 跳转到表单详情
	 * @param taskid
	 * @param prcessInstanceid
	 * @return
	 */
	@RequestMapping("/doapprove/{taskid}/{prcessInstanceid}")
	public ModelAndView doapprove(@PathVariable String taskid,@PathVariable String prcessInstanceid,
			TrainingPlan trainingPlan,String planUserIds,Boolean pass,String approvals ) {
		ProcessInstance processInstance=processEngine.getRuntimeService().createProcessInstanceQuery().
				processInstanceId(prcessInstanceid).singleResult();
		
		Task task=processEngine.getTaskService().createTaskQuery().taskId(taskid).singleResult();
		
		Map<String,Object> activtiMap=new HashMap<String,Object>();
		activtiMap.put("pass", pass);
		activtiMap.put("experts",  Arrays.asList("32", "79", "48"));
		String planid = processInstance.getBusinessKey();
		/**TODO 判断当前登录人是不是任务的拥有者***/
		if("teacherSubmit".equals(task.getTaskDefinitionKey())){
			trainingPlanService.updateDocUrl(trainingPlan);
			processEngine.getTaskService().addComment(task.getId(), processInstance.getId(), (pass?"[同意]":"[驳回]")+approvals);
			processEngine.getTaskService().complete(task.getId(), activtiMap);
		}
		
		if("expertSubmit".equals(task.getTaskDefinitionKey())){
			trainingPlanService.updateDocUrl(trainingPlan);
			processEngine.getTaskService().addComment(task.getId(), processInstance.getId(), (pass?"[同意]":"[驳回]")+approvals);
			processEngine.getTaskService().complete(task.getId(), activtiMap);
		}
		
		ModelAndView mav = new ModelAndView("workflow/plan/approve");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan", "培训计划"));
		breadCrumbList.add(this.getBreadCrumbMap("workflow/plan/create", "培训计划"));
		
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("plan", trainingPlan);
		mav.addObject("task", task);
		mav.addObject("processInstance", processInstance);
		return mav;
	}	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAllPage(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			Date startDate,Date endDate) {
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Map<String, Object> map = new HashMap<String, Object>();
		Page<TrainingPlan> list = trainingPlanService.findAll(pageNumber, pageSize, startDate,endDate);
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 面包屑
	 * @param url
	 * @param name
	 * @return
	 */
	private Map<String,String> getBreadCrumbMap(String url,String name){
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", name);
		breadCrumbMap.put("url", url);
		return breadCrumbMap;
	}
}
