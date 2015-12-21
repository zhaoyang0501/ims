package com.hsae.ims.controller;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.ReimburseSummaryDTO;
import com.hsae.ims.entity.Project;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseCustomerDetail;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.MySQLTemplateWorkflowStore;
import com.osworkflow.SpringWorkflow;
/***
 * 餐费报销
 * @author panchaoyang
 * @param <A>
 *
 */
@Controller
@RequestMapping("/reimburse/reimburse")
public class ReimburseController {
	@Autowired
	private ReimburseService reimburseService;
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private ProjectService projectService;
	
	@InitBinder  
	protected void initBinder(HttpServletRequest request,  
	            ServletRequestDataBinder binder) throws Exception {   
	      DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");  
	      CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);  
	      binder.registerCustomEditor(Date.class, dateEditor);  
	}  
	@RequestMapping("")
	public ModelAndView index(){
		ModelAndView mav = new ModelAndView("reimburse/reimburse/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销");
		breadCrumbMap.put("url", "reimburse/reimburse/");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	/***
	 * 跳转到修改报销单页面
	 * @return
	 */
	@RequestMapping("update/{id}")
	public ModelAndView update( @PathVariable Long id){
		ModelAndView mav = new ModelAndView("reimburse/reimburse/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销");
		breadCrumbMap.put("url", "reimburse/reimburse");
		
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "填写报销单");
		breadCrumbMap1.put("url", "reimburse/reimburse/create");
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		Reimburse reimburse =reimburseService.findOne(id);
		if(reimburse.getWfentry()!=null&&reimburse.getWfentry().getCurrentStep()!=null&&
				reimburse.getWfentry().getCurrentStep().getStepId()>2){
			mav=new ModelAndView("error");
			mav.addObject("tip", "非法操作，已经进入流程不允许编辑");
		}
		mav.addObject("reimburse",reimburse);
		mav.addObject("reimburseDetails",reimburseService.findReimburseDetails(reimburse));
		mav.addObject("reimburseCustomerDetail",reimburseService.findReimburseCustomerDetails(reimburse));
		mav.addObject("breadcrumb", breadCrumbList);
	
		return mav;
	}
	@RequestMapping("create")
	public ModelAndView create( ){
		ModelAndView mav = new ModelAndView("reimburse/reimburse/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销");
		breadCrumbMap.put("url", "reimburse/reimburse");
		
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "填写报销单");
		breadCrumbMap1.put("url", "reimburse/reimburse/create");
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	/***
	 * 跳转到报销汇总页面
	 * @return
	 */
	@RequestMapping("/summary")
	public ModelAndView summary(){
		ModelAndView mav = new ModelAndView("reimburse/reimburse/summary");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费汇总查询");
		breadCrumbMap.put("url", "reimburse/reimburse/summary");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("begin", DateTimeUtil.getFirstDayOfMonth());
		mav.addObject("end", DateTimeUtil.getLastDayOfMonth());
		mav.addObject("steps", springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getSteps());
		return mav;
	}
	
	/**
	 * 查询报销汇总数据
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/summary/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryReimburseSummaryList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin, String end, Long user,Integer type,Integer step) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.parseDate(end, "yyyy-MM-dd");
		Page<Reimburse> page = reimburseService.findAll(pageNumber, pageSize, beginDate, endDate, user,type,step);
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 提交表单
	 * @param reimburse
	 * @param isSend
	 * @param userIds
	 * @return
	 * @throws ParseException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public ModelAndView save(Reimburse reimburse,Boolean isSend, String userIds) throws ParseException, UnsupportedEncodingException{
		Reimburse reimburseToSave=reimburse;
		/***修改逻辑*/
		if(reimburseToSave.getId()!=null){
			reimburseToSave=reimburseService.findOne(reimburse.getId());
			reimburseToSave.setType(reimburse.getType());
			reimburseToSave.setRemark(reimburse.getRemark());
			reimburseToSave.setReimburseDate(reimburse.getReimburseDate());
			reimburseToSave.setReimburseCustomerDetails(reimburse.getReimburseCustomerDetails());
			reimburseService.deleteReimburseDetails(reimburseToSave);
			reimburseService.deleteCustomerReimburseDetails(reimburseToSave);
		}else{
			reimburseToSave.setStep("未提交");
			reimburseToSave.setCreateTime(new java.sql.Timestamp(System.currentTimeMillis()));
		}
			
		
		/***设置外部人员报销 目前已经去除 */
		if(reimburseToSave.getReimburseCustomerDetails()!=null){
			for(ReimburseCustomerDetail reimburseCustomerDetail :reimburseToSave.getReimburseCustomerDetails()){
				if(reimburseCustomerDetail.getProject().getId()!=null)
					reimburseCustomerDetail.setProject(projectService.findOne(reimburseCustomerDetail.getProject().getId()));
				else
					reimburseCustomerDetail.setProject(null);
			}
		}
		
		User user=userService.findOne(RightUtil.getCurrentUserId());
		reimburseToSave.setReimburser(user);
		reimburseService.save(reimburseToSave,userIds);
		
		/**保存并提交*/
		if(isSend&&reimburseToSave.getId()!=null){
			/**初始化一个工作流实例**/
			Long workFlowid=null;
			Map<String,Object> argMap= new HashMap<String,Object>();
			argMap.put("creater",user.getId());
			argMap.put("caller",userService.findOne(user.getId()));
			argMap.put("reimburseId", reimburseToSave.getId());
			try {
				springWorkflow.SetContext(String.valueOf( user.getId()));
				workFlowid=springWorkflow.initialize(WorkFlowConstants.REIMBURSE, 10, argMap);
				/**提交到日报填写**/
				MySQLTemplateWorkflowStore story=(MySQLTemplateWorkflowStore)springWorkflow.getConfiguration().getWorkflowStore();
				story.getPropertySetDelegate().getPropertySet(workFlowid);
				springWorkflow.getPropertySet(workFlowid).setLong("creater", user.getId());
				springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
			} catch (WorkflowException e) {
				e.printStackTrace();
			}
			/**将工作流属性关联到报销*/
			@SuppressWarnings("unchecked")
			List<Step> steps = springWorkflow.getCurrentSteps(workFlowid);
			if(steps!=null&steps.size()!=0){
				reimburseToSave.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(steps.get(0).getStepId()).getName());
				workFlowService.saveApproval("同意", steps.get(0).getId());
			}
			reimburseToSave.setState(springWorkflow.getEntryState(workFlowid));
			reimburseToSave.setWfentry(workFlowService.findWfentry(workFlowid));
		
			reimburseService.save(reimburseToSave);
		}
		
		ModelAndView mav = new ModelAndView("reimburse/reimburse/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "餐费报销");
		breadCrumbMap.put("url", "reimburse/reimburse");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("response",new SuccessResponse());
		mav.addObject("breadcrumb", breadCrumbList);
		return mav;
	}
	
	/***
	 * 查询我的报销记录
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @param reimburseDate
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryList(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin,String end,Integer state ) throws ParseException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.parseDate(end, "yyyy-MM-dd");
		
		Page<Reimburse> list = reimburseService.findAll(pageNumber, pageSize, 
				beginDate, endDate,state,RightUtil.getCurrentUserId());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", list.getContent());
		map.put("iTotalRecords", list.getTotalElements());
		map.put("iTotalDisplayRecords", list.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 报销单发送
	 * @param reimburseId
	 * @return
	 * @throws WorkflowException
	 * @throws InvalidInputException
	 * @throws WorkflowException
	 */
	@RequestMapping(value = "/sendReimburse/{reimburseId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> sendReimburse( @PathVariable Long reimburseId) throws WorkflowException, InvalidInputException, WorkflowException{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", "success");
		map.put("msg", "发送成功！");
		Reimburse reimburse = reimburseService.findOne(reimburseId);
		if(reimburse.getWfentry()!=null){
			map.put("state", "error");
			map.put("msg", "该报销单已经发送，不可重复发送");
			return map;
		}
		/**初始化一个工作流实例**/
		Long workFlowid=null;
		User user=userService.findOne(RightUtil.getCurrentUserId());
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("creater",user.getId());
		argMap.put("caller",userService.findOne(user.getId()));
		argMap.put("reimburseId", reimburse.getId());
		springWorkflow.SetContext(String.valueOf( user.getId()));
		workFlowid=springWorkflow.initialize(WorkFlowConstants.REIMBURSE, 10, argMap);
		/**提交到日报填写**/
		springWorkflow.doAction(workFlowid,springWorkflow.getAvailableActions(workFlowid,argMap)[0], argMap);
		springWorkflow.getPropertySet(workFlowid).setLong("creater", user.getId());
		/**将工作流属性关联到报销*/
		@SuppressWarnings("unchecked")
		List<Step> steps = springWorkflow.getCurrentSteps(workFlowid);
		if(steps!=null&steps.size()!=0){
			reimburse.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(steps.get(0).getStepId()).getName());
			workFlowService.saveApproval("同意", steps.get(0).getId());
		}
		reimburse.setState(springWorkflow.getEntryState(workFlowid));
		reimburse.setWfentry(workFlowService.findWfentry(workFlowid));
		reimburseService.save(reimburse);
		
		return map;
	}
	
	/**报销单删除,如果已经进入流程，不允许删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{reimburseId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delete(@PathVariable Long reimburseId){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", "success");
		map.put("msg", "删除成功！");
		/**判断是否进入了流程，进入流程不允许删除*/
		Reimburse reimburse = reimburseService.findOne(reimburseId);
		if(reimburse==null){
			map.put("state", "error");
			map.put("msg", "不存在此报销单据！");
			return map;
		}
		/** 如果报销处于工时核对或者财务审核状态不允许删除*/
		Wfentry wfentry= reimburse.getWfentry();
		if(wfentry!=null&&wfentry.getCurrentStep().getStepId()>2){
			map.put("state", "error");
			map.put("msg", "此报销单据已进入流程，不允许删除！");
			return map;
		}
		
		/**如果进入了工作流状态，kill it*/
		if(wfentry!=null){
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				map.put("state", "error");
				map.put("msg", "工作流异常！"+e.getMessage());
				e.printStackTrace();
				return map;
			}
		}
		reimburseService.delete(reimburseId);
		return map;
	}
	
	@RequestMapping(value = "/allProject", method=RequestMethod.GET)
	@ResponseBody
	public Map<Long, String> allProject() {
		Iterable<Project> list = projectService.findAll();
		Map<Long, String> maps = new HashMap<Long, String>();
		for (Project project : list) {
			maps.put(project.getId(), project.getProjectName());
		}
		return maps;
	}
}
