package com.hsae.ims.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WeekReport;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.service.UserService;
import com.hsae.ims.service.WeekReportService;
import com.hsae.ims.service.WorkFlowService;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;
/***
 * 	周报审批自动任务
 * @author panchaoyang
 *
 */
@Service
public class WeekReportAutoApproveJob {
	
	private static final Logger log = LoggerFactory.getLogger(WeekReportAutoApproveJob.class);
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private WorkFlowService workFlowService;
	
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private WeekReportService weekReportService;
	@Transactional
	public void execute() throws WorkflowException {
		log.info("开始执行总工自动审批 ！！！");
		User boss=userService.findByNo("1110093");
		springWorkflow.SetContext(boss.getId().toString());
		
		List<CurrentStep> currentSteps = workFlowService.findTodoList(userService.findByNo("1110093"));
		
		Map<String,Object> argMap= new HashMap<String,Object>();
		argMap.put("caller",boss);
		for(CurrentStep bean:currentSteps){
			log.info("处理工作流 编号{}任务--", bean.getWfentry().getId());
			WeekReport weekReport=weekReportService.findByOsworkflow(bean.getWfentry().getId());
			int[] actionIds = springWorkflow.getAvailableActions(bean.getWfentry().getId(), argMap);
			if(actionIds!=null&&actionIds.length!=0){
				springWorkflow.doAction(bean.getWfentry().getId(), actionIds[0], argMap);
				/**拼接审批意见*/
				String actionName=springWorkflow.getWorkflowDescriptor(WorkFlowConstants.DAILY_REPORT).getAction(actionIds[0]).getName();
				if(weekReport.getApprovals()==null)
					weekReport.setApprovals(boss.getChinesename()+
						DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+"同意");
				else
					weekReport.setApprovals(StringUtils.trimToEmpty(weekReport.getApprovals())+"<br>"+boss.getChinesename()+
						DateFormatUtils.format(new Date(), "yyyy-MM-dd")+":["+actionName+"]"+"同意");
				
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
				}
		}
		log.info("总工自动审批结束！");
	}
	
	
}
