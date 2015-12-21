package com.hsae.ims.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.service.DailyReportService;
import com.hsae.ims.service.DailyReportWeekConfigService;
import com.hsae.ims.service.ProjectService;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.utils.RightUtil;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;
/***
 * 废弃
 * @see com.hsae.ims.controller.MyApproveController 
 * 周报审批
 * @author panchaoyang
 *
 */
@Deprecated
@Controller
@RequestMapping("/approve/weekReport")
public class WeekReportApproveController extends BaseController {
	@Autowired
	private  DailyReportService dailyReportService;
	@Autowired
	private  UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DailyReportWeekConfigService dailyReportWeekConfigService;
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private WeekReportService weekReportService;
	/**dailyreport.xml的字符串 表现形式*/
	private String workflowXMlAsString;
	@PostConstruct
	private void setWorkflowXMlAsString(){
		if(workflowXMlAsString==null){
			Resource resource = new ClassPathResource("osworkflow/dailyReport.xml");
			SAXReader reader = new SAXReader();
		    Document document=null;
			try {
				reader.setValidation(false);
				reader.setEntityResolver(new EntityResolver () {  
				     public InputSource resolveEntity(String publicId, String systemId)  
				       throws SAXException, IOException {  
				            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='utf-8'?>".getBytes()));  
				     }  
				});  
				document = reader.read(resource.getFile());
			} catch (Exception e) {
				e.printStackTrace();
			}
		    Element root = document.getRootElement();
		    workflowXMlAsString=root.asXML();
		}
	}
	/***
	 	跳转到周报提交
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView index() {
		List<Map<String,String>> breadCrumbList = new ArrayList<Map<String,String>>();
		Map<String,String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报审批");
		breadCrumbMap.put("url", "approve/weekReport");
		breadCrumbList.add(breadCrumbMap);
		ModelAndView model = new ModelAndView("approve/weekReport/index");
		model.addObject("breadcrumb", breadCrumbList);
		return model;
	}
	/***
	 * 周报待审批列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 * @throws WorkflowException
	 */
	@RequestMapping(value="/weekReportToApproveList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> weekReportToApproveList(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength
			) throws WorkflowException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Long userid = RightUtil.getCurrentUserId();
		Page<WeekReport> weekReports = weekReportService.findWeekReportToApprove(userid, pageNumber, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", weekReports.getContent());
		map.put("iTotalRecords", weekReports.getTotalElements());
		map.put("iTotalDisplayRecords", weekReports.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	/***
	 * 周报已审批列表
	 * @param sEcho
	 * @param iDisplayStart
	 * @param iDisplayLength
	 * @return
	 * @throws WorkflowException
	 */
	@RequestMapping(value="/weekReportApprovedList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> weekReportApprovedList(
			@RequestParam(value = "sEcho", defaultValue = "1") int sEcho,
			@RequestParam(value = "iDisplayStart", defaultValue = "0") int iDisplayStart,
			@RequestParam(value = "iDisplayLength", defaultValue = "10") int iDisplayLength
			) throws WorkflowException{
		int pageNumber = (int) (iDisplayStart / iDisplayLength) + 1;
		int pageSize = iDisplayLength;
		Long userid= RightUtil.getCurrentUserId();
		Page<WeekReport>  weekReports=weekReportService.findWeekReportApproved(userid,pageNumber,pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaData", weekReports.getContent());
		map.put("iTotalRecords",weekReports.getTotalElements());
		map.put("iTotalDisplayRecords", weekReports.getTotalElements());
		map.put("sEcho", sEcho);
		return map;
	}
	/***
	 * 审批单个周报页面
	 * @param weekreportid
	 * @return
	 */
	@RequestMapping(value="/weekReportToApprove/{weekreportid}")
	public  ModelAndView toApprove(@PathVariable Long weekreportid){
		Long userid = RightUtil.getCurrentUserId();
		WeekReport weekReport = weekReportService.getById(weekreportid);
		Assert.notNull(weekReport);
		List<DailyReport> dailyReports = dailyReportService.findDailyReport(weekReport.getWeek().getStartDate(),
				weekReport.getWeek().getEndDate(), weekReport.getCreater());
		ModelAndView modelAndView = new ModelAndView("approve/weekReport/toapprove");
		modelAndView.addObject("dailyReports", dailyReports);
		modelAndView.addObject("weekReport", weekReport);
		
		springWorkflow.SetContext(userid.toString());
		int[] actionIds = springWorkflow.getAvailableActions(weekReport.getOsworkflow(), null);
		Map<Integer,String>	aciton=new HashMap<Integer,String>();
		for(int i=0;i<actionIds.length;i++){
			aciton.put(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionIds[i]).getId(), 
					springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionIds[i]).getName());
		}
		modelAndView.addObject("acitons",aciton);
	
		List<Map<String, String>> breadCrumbList = new ArrayList<Map<String, String>>();
		Map<String, String> breadCrumbMap = new HashMap<String, String>();
		breadCrumbMap.put("name", "周报审批");
		breadCrumbMap.put("url", "approve/weekReport");
		
		Map<String, String> breadCrumbMap1 = new HashMap<String, String>();
		breadCrumbMap1.put("name", "审批");
		breadCrumbMap1.put("url", "approve/weekReportToApprove/"+weekreportid);
		
		breadCrumbList.add(breadCrumbMap);
		breadCrumbList.add(breadCrumbMap1);
		modelAndView.addObject("breadcrumb", breadCrumbList);
		
		return modelAndView;
	}
	/***
	 * 周报审批动作
	 * weekreportid 周报ID
	 * actionId 工作流某STEP的action id
	 * @return
	 */
	
	@RequestMapping(value="/doApprove/{weekreportid}/{actionId}", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String,Object> doApprove(@PathVariable Long weekreportid,@PathVariable  Integer  actionId,String approvals){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("status", "success");
		Long userid= RightUtil.getCurrentUserId();
		WeekReport weekReport = weekReportService.getById(weekreportid);
		/**工作流流转*/
		springWorkflow.SetContext(userid.toString());
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("caller",userService.findOne(userid));
		try {
			springWorkflow.doAction(weekReport.getOsworkflow(), actionId, argMap);
		} catch (WorkflowException e) {
			resultMap.put("status", "error");
			resultMap.put("msg", "审批失败"+e.getMessage());
		}
		/**拼接审批意见*/
		String actionName=springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionId).getName();
		if(weekReport.getApprovals()==null)
			weekReport.setApprovals(RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+approvals);
		else
			weekReport.setApprovals(StringUtils.trimToEmpty(weekReport.getApprovals())+"<br>"+RightUtil.getCurrentChinesename()+
				DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+approvals);
		/**修改周报的状态步骤*/	
		List<Step> steps = springWorkflow.getCurrentSteps(weekReport.getOsworkflow());
		if(steps!=null&steps.size()!=0)
			weekReport.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getStep(steps.get(0).getStepId()).getName());
		else{
			List<Step> historySteps = springWorkflow.getHistorySteps(weekReport.getOsworkflow());
			if(historySteps!=null&historySteps.size()!=0)
				weekReport.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getStep(historySteps.get(0).getStepId()).getName());
		}
		weekReport.setState(springWorkflow.getEntryState(weekReport.getOsworkflow()));
		weekReportService.saveWeekReport(weekReport);
		return resultMap;
	}
	/***
	 * 查看某个流程下的流程图
	 * @param workflowId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/workflowgraph/{workflowId}")
	public ModelAndView workflowGraph(@PathVariable Long workflowId){
		ModelAndView modelAndView = new ModelAndView("approve/weekReport/workflowgraph");
		springWorkflow.SetContext(String.valueOf(RightUtil.getCurrentUserId()));
		List<Step> steps = springWorkflow.getCurrentSteps(workflowId);
		if(steps!=null&&steps.size()!=0){
			modelAndView.addObject("stepid",steps.get(0).getStepId());
		}else{
			List<Step> historySteps = springWorkflow.getHistorySteps(workflowId);
			if(historySteps!=null&historySteps.size()!=0)
				modelAndView.addObject("stepid",historySteps.get(0).getStepId());
		}
		modelAndView.addObject("workflow", workflowXMlAsString);
		return modelAndView;
	}
}
