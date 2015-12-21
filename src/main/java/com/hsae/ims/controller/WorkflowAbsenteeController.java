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
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.hsae.ims.dto.WorkflowAbsenteeApproveDetailsDTO;
import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.WorkflowAbsentee;
import com.hsae.ims.entity.AttenceBrushRecord;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkflowAbsenteeDetails;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.service.AttenceAbsenteeService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkflowAbsenteeService;
import com.hsae.ims.service.AttenceBrushRecordService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.opensymphony.workflow.spi.WorkflowEntry;
import com.osworkflow.SpringWorkflow;

@Controller
@RequestMapping("/workflow/absentee")
public class WorkflowAbsenteeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	SpringWorkflow springWorkflow;
	
	@Autowired
	WorkFlowService workFlowService;
	
	@Autowired
	private AttenceAbsenteeService attenceAbsenteeService;
	
	@Autowired
	private WorkflowAbsenteeService workflowAbsenteeService;
	
	@Autowired
	private AttenceBrushRecordService attenceBrushRecordService;
	
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@InitBinder
	 public void dataBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
   }

	@RequestMapping(value = "")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("workflow/absentee/index");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "补打卡流程");
		breadCrumbMap.put("url", "workflow/absentee");
		breadCrumbList.add(breadCrumbMap);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("cuid", RightUtil.getCurrentUserId());
		return mav;
	}
	
	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("workflow/absentee/create");
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "补打卡流程");
		breadCrumbMap.put("url", "workflow/absentee");
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "补打卡申请单");
		breadCrumbMap1.put("url", "workflow/absentee/create");
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		mav.addObject("breadcrumb", breadCrumbList);
		mav.addObject("cuid", RightUtil.getCurrentUserId());
		mav.addObject("cname", RightUtil.getCurrentChinesename());
		mav.addObject("user", userService.findOne(RightUtil.getCurrentUserId()));
		mav.addObject("formDate", new Date());
		return mav;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryAbsenteePage(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,
			String  begin, String end, Integer state) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		
		Date begainDate =begin==null?null:DateUtils.parseDate(begin, "yyyy-MM-dd");
		Date endDate =end==null?null:DateUtils.addDays( DateUtils.parseDate(end, "yyyy-MM-dd"), 1);
		Page<WorkflowAbsentee> page = workflowAbsenteeService.findAll(pageNumber, pageSize, begainDate, endDate, state);
		if (page != null && page.getNumberOfElements() > 0) {
			for(WorkflowAbsentee absentee : page){
				CurrentStep cstep = absentee.getWfentry().getCurrentStep();
				if (cstep != null) {
					absentee.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.ABSENTEE).getStep(cstep.getStepId()).getName());
				}
			}
		}
		
		map.put("aaData", page.getContent());
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	
	/***
	 * 审批页面漏打卡明细
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/goapprove/details/list/{osworkflow}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> goapproveDetailList(@PathVariable Long osworkflow) throws ParseException{
		Map<String, Object> map = new HashMap<String, Object>();
		Wfentry wfentry = workFlowService.findWfentry(osworkflow);
		WorkflowAbsentee workflow = workflowAbsenteeService.findByWfentry(wfentry);
		List<WorkflowAbsenteeDetails> list = workflow.getAbsentee();
		List<WorkflowAbsenteeApproveDetailsDTO> dtoList = new ArrayList<WorkflowAbsenteeApproveDetailsDTO>();
		WorkflowAbsenteeApproveDetailsDTO dto = null;
		for(WorkflowAbsenteeDetails absentee : list){
			dto = new WorkflowAbsenteeApproveDetailsDTO();
			Long userId = absentee.getUser();
			User u = userService.findOne(userId);
			dto.setId(absentee.getId());
			dto.setUsername(u.getChinesename());
			dto.setEmpnumber(u.getEmpnumber());
			dto.setDeptname(u.getDept() == null? "" : u.getDept().getName());
			dto.setAbsenteetime(absentee.getAbsenteeDate() + " " +absentee.getAbsenteeTime());
			AttenceBrushRecord brushRecord = attenceBrushRecordService.findBrushRecord(userId, absentee.getAbsenteeDate());
			dto.setRecord(brushRecord == null? "" : brushRecord.getBrushData());
			dto.setReason(absentee.getReason());
			/** 本月漏打卡次数 **/
			Integer frequency = attenceAbsenteeService.findAbsenteeFrequency(userId, absentee.getAbsenteeDate());
			dto.setFrequency(frequency.toString());	
			dto.setRemark(absentee.getRemark());
			dto.setState(absentee.getState());
			dtoList.add(dto);
		}
		map.put("aaData", dtoList);
		return map;
	}
	
	/**
	 * 流程提交
	 * @param absentee
	 * @return
	 */
	@RequestMapping("/submit")
	@ResponseBody
	public Map<String, Object> submit(@RequestBody List<WorkflowAbsenteeDetails> absentee){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Long workFlowid = null;
		Map<String, Object> argMap = new HashMap<String, Object>();
		Long cuid = RightUtil.getCurrentUserId();
		User u = userService.findOne(cuid);
		argMap.put("creater", cuid);
		argMap.put("caller",userService.findOne(cuid));
		Deptment dept = u.getDept();
		if (dept == null) {
			map.put("success", "0");
			map.put("msg", "未设置用户所在部门，请联系管理员！");
			return map;
		}
		Long managerId = dept.getManager();
		if (managerId == null || managerId <= 0) {
			map.put("success", "0");
			map.put("msg", "用户所在部门未设置部门主管，请联系管理员！");
			return map;
		}
		/**如果部门主管就是自己，提交给朱新军审核**/
		if(u.getId()== managerId)
			argMap.put("manager",32);
		else
			argMap.put("manager", managerId);
		//流程发起
		try {
			springWorkflow.SetContext(String.valueOf(cuid));
			workFlowid = springWorkflow.initialize(WorkFlowConstants.ABSENTEE, 10, argMap);
			/** 工作流扩展信息 **/
			Wfentry wfentry = workFlowService.findWfentry(workFlowid);
			WfentryExtend extend = new WfentryExtend();
			extend.setCreater(u);
			extend.setTitle(u.getChinesename() + " [补打卡申请流程]");
			extend.setSn(workFlowIdGeneratorService.getAbsenteeWorkFlowId());
			extend.setWfentry(wfentry);
			workFlowService.saveWfentryExtend(extend);
			
			springWorkflow.doAction(workFlowid, springWorkflow.getAvailableActions(workFlowid, argMap)[0], argMap);
			
			/** 业务表保存  **/
			WorkflowAbsentee workflow = new WorkflowAbsentee();
			workflow.setWfentry(wfentry);
			workflow.setUser(u);
			workflow.setAbsentee(absentee);
			workflow.setCreateDate(new Date());
			workflowAbsenteeService.save(workflow);
			User currentUser=workFlowService.getCurrentstepUser(workflow.getWfentry());
			map.put("success", "1");
			map.put("msg", "操作成功，流程已提交给"+currentUser.getChinesename());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/approve/{absenteeId}", method = RequestMethod.POST)
	public String approve(@PathVariable Long absenteeId, Integer actionId, String approvals, String operate, RedirectAttributes redirectAttributes) throws Exception{
		springWorkflow.SetContext(String.valueOf( RightUtil.getCurrentUserId()));
		WorkflowAbsentee workflow = workflowAbsenteeService.findOne(absenteeId);
		Long osworkflow = workflow.getWfentry().getId();
		Map<String, Object> argMap = new HashMap<String, Object>();
		User u = workflow.getUser();
		
		
		argMap.put("creater", u.getId());
		/**如果部门主管就是自己，提交给朱新军审核**/
		if(u.getId()== u.getDept().getManager())
			argMap.put("manager",32);
			else
		argMap.put("manager", u.getDept().getManager());
		
		List<Step> steps = springWorkflow.getCurrentSteps(osworkflow);
		Assert.notNull(steps);
		/** 流程流转 **/
		springWorkflow.doAction(osworkflow, actionId, argMap);
		
		workFlowService.saveApproval(approvals, steps.get(0).getId(),
				springWorkflow.getWorkflowDescriptor(WorkFlowConstants.ABSENTEE).getAction(actionId).getName());
		
		if(steps != null && steps.size() > 0){
			/** 当为人事审核节点 **/
			if (steps.get(0).getStepId() == 3) {
				if (StringUtils.isNotBlank(operate)) {
					String[] operates = operate.split(",");
					List<WorkflowAbsenteeDetails> details = workflow.getAbsentee();
					for(String op : operates){
						if (StringUtils.isNotBlank(op)) {
							String[] ops = op.split("-");
							Long id = Long.parseLong(ops[0]);
							String state = ops[1];
							for(WorkflowAbsenteeDetails d : details){
								if (id == d.getId()) {
									d.setState(state);
									if (state.equals("1")) {	//有效地漏打卡记录
										AttenceAbsentee absentee=attenceAbsenteeService.findAbsenteee(userService.findOne(d.getUser()), d.getAbsenteeDate());
										if(absentee==null)
											 absentee = new AttenceAbsentee();
										absentee.setAbsenteeDate(d.getAbsenteeDate());
										absentee.setAbsenteeTime((absentee.getAbsenteeTime()==null?"":absentee.getAbsenteeTime())+" "+d.getAbsenteeTime());
										absentee.setReason(d.getReason());
										absentee.setRemark(d.getRemark());
										absentee.setUser(d.getUser());
										absentee.setSaveTime(new Timestamp(System.currentTimeMillis()));
										absentee.setAbsenteeType("40");
										attenceAbsenteeService.saveAttenceAbsentee(absentee);
										AttenceBrushRecord attenceBrushRecord = attenceBrushRecordService.findBrushRecord(d.getUser(), d.getAbsenteeDate());
										attenceBrushRecord.setState("30");
										attenceBrushRecord.setTransfertime(new Timestamp(System.currentTimeMillis()));
										attenceBrushRecordService.saveAttenceBrushRdcord(attenceBrushRecord);
									}
								}
							}
						}
					}
					workflow.setAbsentee(details);
					workflowAbsenteeService.save(workflow);
				}
			}
		}
		User currentUser=workFlowService.getCurrentstepUser(workflow.getWfentry());
		redirectAttributes.addFlashAttribute("response",new ObjectResponse<User>(currentUser));
		return "redirect:/workflow/toapprove/goApprove/"+ osworkflow;
	}
	
	/**
	 * 删除不用流程
	 * @return
	 */
	@RequestMapping(value = "/delete/{absenteeId}", method=RequestMethod.POST)
	@ResponseBody
	public Response delete(@PathVariable Long absenteeId){
		if (absenteeId == null || absenteeId <= 0) {
			return new FailedResponse("400","该单据不存在");
		}
		WorkflowAbsentee absentee = workflowAbsenteeService.findOne(absenteeId);
		if(absentee == null){
			return new FailedResponse("400","该单据不存在");
		}
		Wfentry wfentry = absentee.getWfentry();
		if(wfentry.getWfentryExtend()!=null&&
				!wfentry.getWfentryExtend().getCreater().equals(userService.findOne(RightUtil.getCurrentUserId()))){
			return new FailedResponse("400","只能删除自己创建的流程！");
		}
		
		if(wfentry!=null&&wfentry.getState()==WorkflowEntry.COMPLETED){
			return new FailedResponse("400","此补卡单已经结束不允许删除！");
		}
		
		if(wfentry != null && wfentry.getCurrentStep().getStepId() >= 2){
			return new FailedResponse("400","此单据已进入流程，不允许删除");
		}
		/** 删除工作流 和 业务表 **/
		if (wfentry != null) {
			springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
			try {
				springWorkflow.changeEntryState(wfentry.getId(), WorkflowEntry.KILLED);
			} catch (WorkflowException e) {
				return new FailedResponse("400", "工作流异常！" + e.getMessage());
			}
		}
		workflowAbsenteeService.delete(absenteeId);
		return new SuccessResponse();
	}
 	
}
