package com.hsae.ims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.constants.ImsConstants.DailyReportConstants;
import com.hsae.ims.constants.ImsConstants.WorkFlowConstants;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.spi.Step;
import com.osworkflow.SpringWorkflow;
/***
 * 工时核对的观察者
 * @author panchaoyang
 *
 */
@Service
public class DailyReportStateObserverImpl implements  WorkingHoursCheckObserver{
	
	@Autowired
	private SpringWorkflow springWorkflow;
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private ReimburseService  reimburseService;
	@Override
	public void update(DailyReport dailyReport)   {
		if(!DailyReportConstants.OVERTIME_TYPE.equals(dailyReport.getType()))
			return ;
		Long userid= dailyReport.getUser().getId();
		springWorkflow.SetContext(String.valueOf(userid));
		/**找出对应的加班报销工作流，并终结他 ，如果条件允许*/
		List<ReimburseDetails> list = reimburseService.findByUserAndDate(dailyReport.getUser(), dailyReport.getReportDate());
		for(ReimburseDetails reimburseDetails :list){
			Reimburse reimburse=reimburseDetails.getReimburse();
			Wfentry wfentry =reimburseDetails.getReimburse().getWfentry();
			/**工作流引擎开始工作*/
		
			Map<String,Object> osworkflowArgsMap= new HashMap<String,Object>();
			osworkflowArgsMap.put("creater",userid);
			osworkflowArgsMap.put("reimburseId", reimburse.getId());
			osworkflowArgsMap.put("caller",dailyReport.getUser());
			
			
			if(wfentry==null||springWorkflow.getCurrentSteps(wfentry.getId()).size()==0)
				return ;
			Step step=(Step)springWorkflow.getCurrentSteps(wfentry.getId()).get(0);
			/**当前步骤不是加班报告填写*/
			if(step.getStepId()!=2) return ;
			int[] actionIds = springWorkflow.getAvailableActions(wfentry.getId(), osworkflowArgsMap);
			if(actionIds!=null){
				for(int actionId:actionIds){
					try {
						springWorkflow.doAction(wfentry.getId(),actionId, osworkflowArgsMap);
					} catch (WorkflowException e) {
						throw new RuntimeException(e.getMessage());
					}catch (com.opensymphony.workflow.InvalidActionException e1){
						e1.printStackTrace();
					}
				}
			}
			workFlowService.saveApproval("同意", step.getId());
			/**执行完成并回写状态step等*/
			@SuppressWarnings("unchecked")
			List<Step> steps = springWorkflow.getCurrentSteps(wfentry.getId());
			if(steps!=null&steps.size()!=0)
				reimburse.setStep(springWorkflow.getWorkflowDescriptor(WorkFlowConstants.REIMBURSE).getStep(steps.get(0).getStepId()).getName());
			reimburse.setState(springWorkflow.getEntryState(wfentry.getId()));
			reimburse.setWfentry(workFlowService.findWfentry(wfentry.getId()));
			reimburseService.save(reimburse);
		}
	}
	@Override
	public int getPriority() {
		return 100;
	}

}
