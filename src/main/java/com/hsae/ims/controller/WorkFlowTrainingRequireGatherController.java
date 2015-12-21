package com.hsae.ims.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.dto.TrainingRequireGatherDetailsDTO;
import com.hsae.ims.dto.WorkflowTrainingDeptSubmitStateDTO;
import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.WorkFlowTrainingRequireGatherDetails;
import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.osworkflow.WfentryExtend;
import com.hsae.ims.service.CodeService;
import com.hsae.ims.service.DeptService;
import com.hsae.ims.service.TrainingRequirementDetailService;
import com.hsae.ims.service.WorkFlowIdGeneratorService;
import com.hsae.ims.service.WorkFlowTrainingRequireGatherService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WorkFlowService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;

@Controller
@RequestMapping("/training/workflow/require/gather")
public class WorkFlowTrainingRequireGatherController extends BaseController {

	@Autowired
	private WorkFlowTrainingRequireGatherService workFlowTrainingRequireGatherService;
	@Autowired
	private TrainingRequirementDetailService trainingRequirementDetailService;
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeptService deptService;
	@Autowired
	private CodeService codeService;
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@RequestMapping("/index")
	public ModelAndView index() {
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "培训需求收集");
		breadCrumbMap.put("url", "/training/workflow/require/gather/index");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("training/requirement/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	
	@RequestMapping("/hr/details/{deptid}")
	public ModelAndView hrdetails(@PathVariable Long deptid) {
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "培训需求收集");
		breadCrumbMap.put("url", "/training/workflow/require/gather/hr/details/"+deptid);
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("workflow/training/requiregather/hrdetails");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findRequirementList() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkFlowTrainingRequireGather> list = workFlowTrainingRequireGatherService.findAll();

		map.put("aaData", list);
		return map;
	}

	/**
	 * 培训需求收集流程发起
	 * 
	 * @param year
	 * @param remarks
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Response submit(Integer year, String remarks) {
		List<WorkFlowTrainingRequireGather> list = workFlowTrainingRequireGatherService.findByYear(year);
		if (list != null && list.size() > 0) {
			return new FailedResponse("0", "已经提交，不能重复提交");
		}
		WorkFlowTrainingRequireGather require = new WorkFlowTrainingRequireGather();
		/** 提交流程 **/
		Long workFlowid = null;
		Map<String, Object> argMap = new HashMap<String, Object>();
		Long cuid = RightUtil.getCurrentUserId();
		User u = userService.findOne(cuid);
		argMap.put("creater", cuid);
		argMap.put("pms", cuid);	//TODO 部门主管列表 逗号分隔

		try {
			springWorkflow.SetContext(String.valueOf(cuid));
			workFlowid = springWorkflow.initialize(WorkFlowConstants.TRAINING_REQUIRE_GATHER, 10, argMap);
			/** 工作流扩展信息 **/
			Wfentry wfentry = workFlowService.findWfentry(workFlowid);
			WfentryExtend extend = new WfentryExtend();
			extend.setCreater(u);
			extend.setTitle(u.getChinesename() + " [培训需求收集流程]");
			extend.setSn(workFlowIdGeneratorService.getRequireGatherRepositoryWorkFlowId());
			extend.setWfentry(wfentry);
			workFlowService.saveWfentryExtend(extend);

			springWorkflow.doAction(workFlowid, springWorkflow.getAvailableActions(workFlowid, argMap)[0], argMap);
			/** 审批意见 **/
			List<Step> steps = springWorkflow.getCurrentSteps(workFlowid);
			workFlowService.saveApproval("提交外出申请流程", steps.get(0).getId());
			
			/** 保存业务 **/
			require.setYear(year);
			require.setCreater(u);
			require.setCreateDate(new Date());
			require.setWfentry(wfentry);
			require.setRemarks(remarks);
			workFlowTrainingRequireGatherService.save(require);
		} catch (Exception e) {
			e.printStackTrace();
			
			return new FailedResponse("0", "操作失败！");
		}
		return new SuccessResponse("操作成功！");
	}

	/**
	 * 培训需求收集流程审批 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/approve/{requireId}/{actionId}")
	@ResponseBody
	public Response doApprove(@PathVariable Long requireId, @PathVariable Integer actionId, String approvals, 
			@RequestBody List<WorkFlowTrainingRequireGatherDetails> details) throws Exception {
		Long cuid = RightUtil.getCurrentUserId();
		WorkFlowTrainingRequireGather tr = workFlowTrainingRequireGatherService.findOne(requireId);
		User user = userService.findOne(cuid);
		Wfentry wfentry = tr.getWfentry();
		springWorkflow.SetContext(cuid.toString());
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("caller", userService.findOne(cuid));
		List<Step> steps = springWorkflow.getCurrentSteps(wfentry.getId());
		
		/** 流程流转 **/
		springWorkflow.doAction(tr.getWfentry().getId(), actionId, argMap);
		if(steps != null && steps.size() > 0){
			/** 审批意见 **/
			workFlowService.saveApproval(approvals, steps.get(0).getId());
		}

		// 保存收集的培训需求
		if (details != null && details.size() > 0) {
			for(WorkFlowTrainingRequireGatherDetails detail : details){
				detail.setRequireId(requireId);
				detail.setYear(tr.getYear());
				detail.setDept(user.getDept().getId());
				trainingRequirementDetailService.save(detail);
			}
		}
		
		return new SuccessResponse("操作成功");
	}
	
	
	/**
	 * 所有部门的年度培训需求
	 * @return
	 */
	@RequestMapping(value = "/{year}/dept/submit/state")
	@ResponseBody
	public Map<String, Object> findAllDeptSubmitState(@PathVariable Integer year){
		Map<String, Object> map = new HashMap<String, Object>();
		List<WorkflowTrainingDeptSubmitStateDTO> list = new ArrayList<WorkflowTrainingDeptSubmitStateDTO>();
		List<Deptment> deptList = deptService.getAllChildDept();
		if (deptList != null && deptList.size() > 0) {
			for(Deptment dept : deptList){
				WorkflowTrainingDeptSubmitStateDTO dto = new WorkflowTrainingDeptSubmitStateDTO();
				dto.setDept(dept.getName());
				dto.setDeptid(dept.getId());
				dto.setTotalno(100);
				dto.setTotalneino(50);
				dto.setTotalwaino(50);
				dto.setTotalmoney(5000d);
				dto.setTotalhours(40d);
				dto.setSbdate(new Date());
				list.add(dto);
			}
		}
		
		map.put("aaData", list);
		return map;
	}
	
	
	@RequestMapping(value = "/{year}/list")
	@ResponseBody
	public Map<String, Object> findYearAllRequire(@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength,@PathVariable Integer year){
		
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		
		Map<String, Object> map = new HashMap<String, Object>();
		Page<WorkFlowTrainingRequireGatherDetails> page = trainingRequirementDetailService.findByYear(pageNumber, pageSize, year);
		List<TrainingRequireGatherDetailsDTO> list = new ArrayList<TrainingRequireGatherDetailsDTO>();
		if (page != null ) {
			TrainingRequireGatherDetailsDTO dto = null;
			for(WorkFlowTrainingRequireGatherDetails detail : page){
				dto = new TrainingRequireGatherDetailsDTO();
				dto.setId(detail.getId());
				dto.setCost(detail.getCost());
				dto.setDept(deptService.findDeptById(detail.getDept()).getName());
				dto.setGoals(detail.getGoals());
				dto.setHours(detail.getHours());
				int method = detail.getMethod();
				dto.setMethod(method == 1?"内训":"外训");
				dto.setMonth(detail.getMonth());
				dto.setSubject(detail.getSubject());
				dto.setTeacher_orgnization(method == 1? userService.findOne(detail.getTeacher()).getChinesename() : detail.getOrgnization());
				dto.setTrainees(dto.getTrainees());
				dto.setTrainingType(codeService.findTrainingTypeName(detail.getTrainingType()));
				dto.setState(detail.getState());
				dto.setRemark(detail.getRemark());
				list.add(dto);
			}
		}
		map.put("aaData", list);
		map.put("iTotalRecords", page.getTotalElements());
		map.put("iTotalDisplayRecords", page.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
}
