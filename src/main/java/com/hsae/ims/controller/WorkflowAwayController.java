package com.hsae.ims.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.ObjectResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkflowAway;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.service.WorkflowAwayService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.SpringWorkflow;

@Controller
@RequestMapping("/workflow/away")
public class WorkflowAwayController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private WorkflowAwayService workflowAwayService;
	
	@Autowired
	SpringWorkflow springWorkflow;
	
	@Autowired
	WorkFlowService workFlowService;
	
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@InitBinder
	 public void dataBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
    }

	@RequestMapping(value = "")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("workflow/away/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "外出申请流程");
		breadCrumbMap.put("url", "workflow/away");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("cuid", RightUtil.getCurrentUserId());
		return mav;
	}
	
	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("workflow/away/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "外出申请流程");
		breadCrumbMap.put("url", "workflow/away");
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "补打卡申请单");
		breadCrumbMap1.put("url", "workflow/away/create");
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		mav.addObject("breadcrumb", breadCrumbList);
		long cuid = RightUtil.getCurrentUserId();
		mav.addObject("cuid", cuid);
		mav.addObject("cname", RightUtil.getCurrentChinesename());
		mav.addObject("formDate", DateTimeUtil.getCurrDateStr());
		User user = userService.findOne(cuid);
		Deptment dept = user.getDept();
		if(dept != null){
			if(cuid != dept.getManager()){
				mav.addObject("manager", dept.getManager());
			}else{
				mav.addObject("manager", 2);	//如果当前人事部门主管则直接转到管工处 TODO 添加到配置中去
			}
		}
		return mav;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findAll(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String begin, String end, Integer state) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Date beginDate = begin==null?null: DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =  end==null?null:DateUtils.addDays( DateUtils.parseDate(end, "yyyy-MM-dd"), 1);
		
		Page<WorkflowAway> page = workflowAwayService.findAll(pageNumber, pageSize, beginDate, endDate, state);
		if (page != null && page.getNumberOfElements() > 0) {
			for(WorkflowAway away : page){
				CurrentStep cstep = away.getWfentry().getCurrentStep();
				if (cstep != null) {
					away.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_AWAY).getStep(cstep.getStepId()).getName());
				}
			}
		}
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/****
	 * 外出流程提交
	 * @param workflowAway
	 * @param awayer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Response submit(@ModelAttribute WorkflowAway workflowAway, String awayer){
		
		Long workFlowid = null;
		Wfentry wfentry;
		Map<String, Object> argMap = new HashMap<String, Object>();
		Long cuid = RightUtil.getCurrentUserId();
		User u = userService.findOne(cuid);
		argMap.put("creater", cuid);
		argMap.put("caller",userService.findOne(cuid));
		Long managerId = workflowAway.getManager();
		if (managerId == null || managerId <= 0) {
			return new FailedResponse("0", "提交失败，请设置外出单下级审批人！");
		}
		argMap.put("manager", managerId);
		try {
			springWorkflow.SetContext(String.valueOf(cuid));
			workFlowid = springWorkflow.initialize(WorkFlowConstants.WORLFLOW_AWAY, 10, argMap);
			/** 工作流扩展信息 **/
			wfentry = workFlowService.findWfentry(workFlowid);
			WfentryExtend extend = new WfentryExtend();
			extend.setCreater(u);
			extend.setTitle(u.getChinesename() + " [外出申请流程]");
			extend.setSn(workFlowIdGeneratorService.getAwayWorkFlowId());
			extend.setWfentry(wfentry);
			workFlowService.saveWfentryExtend(extend);
			/** 流程提交 **/
			springWorkflow.doAction(workFlowid, springWorkflow.getAvailableActions(workFlowid, argMap)[0], argMap);
			/** 审批意见 **/
			List<Step> steps = springWorkflow.getCurrentSteps(workFlowid);
			workFlowService.saveApproval("提交外出申请流程", steps.get(0).getId());
			
			/** 业务表保存 **/
			List<User> userList = null;
			if(StringUtils.isNotBlank(awayer)){
				String[] awayers = awayer.split(",");
				userList = new ArrayList<User>();
				for(String a : awayers){
					User user = userService.findOne(Integer.parseInt(a));
					userList.add(user);
				}
			}
			workflowAway.setWfentry(wfentry);
			workflowAway.setFillDate(new Date());
			workflowAway.setUser(userService.findOne(RightUtil.getCurrentUserId()));
			workflowAway.setAwayUser(userList);
			workflowAway.setSaveTime(new Timestamp(System.currentTimeMillis()));
			workflowAwayService.save(workflowAway);
		} catch (Exception e) {
			e.printStackTrace();
			return new FailedResponse("0", "流程提交失败！");
		}
		User currentUser=workFlowService.getCurrentstepUser(wfentry);
		return  new ObjectResponse<User>(currentUser);
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/approve/{awayId}", method = RequestMethod.POST)
	public String approve(@PathVariable Long awayId, Integer actionId, String approvals, String remarks, RedirectAttributes redirectAttributes) throws Exception{
		Long cuid = RightUtil.getCurrentUserId();
		
		WorkflowAway workflow = workflowAwayService.findOne(awayId);
		Long osworkflow = workflow.getWfentry().getId();
		springWorkflow.SetContext(cuid.toString());
		Map<String, Object> argMap = new HashMap<String, Object>();
		List<Step> steps = springWorkflow.getCurrentSteps(osworkflow);
		argMap.put("creater", workflow.getUser().getId());
		argMap.put("guider", 95);	//保安 TODO
		Long managerId = workflow.getManager();
		argMap.put("manager", managerId);
		Assert.notNull(steps);
		/** 流程流转 **/
		springWorkflow.doAction(osworkflow, actionId, argMap);
		workFlowService.saveApproval(approvals, steps.get(0).getId(),
				springWorkflow.getWorkflowDescriptor(WorkFlowConstants.WORLFLOW_AWAY).getAction(actionId).getName());
		if(steps != null && steps.size() > 0){
			/** 当节点为保安放行时 **/
			if (steps.get(0).getStepId() == 3) {
				workflow.setAwayTime(new Date());
				workflow.setGuider(userService.findOne(95)); //保安 TODO
				workflow.setRemarks(remarks);
				workflowAwayService.save(workflow);
			}
		}
		
		User currentUser=workFlowService.getCurrentstepUser(workflow.getWfentry());
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
		
		redirectAttributes.addFlashAttribute("tip", "操作成功！");
		return "redirect:/workflow/toapprove/goApprove/"+ osworkflow;
	}
	/**报销单删除,如果已经进入流程，不允许删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete/{workflowAwayid}", method=RequestMethod.GET)
	@ResponseBody
	public Response delete(@PathVariable Long workflowAwayid){
		/**判断是否进入了流程，进入流程不允许删除*/
		WorkflowAway workflowAway = workflowAwayService.findOne(workflowAwayid);
		if(workflowAway==null){
			return new FailedResponse("400","该外出单不存在");
		}
		
		Wfentry wfentry= workflowAway.getWfentry();
		/**只能删除自己创建的工作流*/
		if(wfentry.getWfentryExtend()!=null&&
				!wfentry.getWfentryExtend().getCreater().equals(userService.findOne(RightUtil.getCurrentUserId()))){
			return new FailedResponse("400","只能删除自己创建的流程！");
		}
		if(wfentry!=null&&wfentry.getState()==WorkflowEntry.COMPLETED){
			return new FailedResponse("400","此外出单已经结束不允许删除！");
		}
		
		if(wfentry!=null&&wfentry.getCurrentStep().getStepId()>=2){
			return new FailedResponse("400", "此外出单据已进入流程，不允许删除！");
		}
		/**如果进入了工作流状态，kill it*/
		if(wfentry!=null){
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				e.printStackTrace();
				return new FailedResponse("400", "工作流异常！"+e.getMessage());
			}
		}
		workflowAwayService.delete(workflowAwayid);
		return new SuccessResponse("删除成功！");
	}

	/**
	 * 同部门人员
	 * @return
	 */
	@RequestMapping(value="/deptofuser", method = RequestMethod.GET)
	@ResponseBody
	public List<User> findDeptofUser(){
		long cuid = RightUtil.getCurrentUserId();
		User user = userService.findOne(cuid);
		Deptment dept = user.getDept();
		return userService.findbyDept(dept);
	}
}
