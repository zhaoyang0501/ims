package com.osworkflow.condition;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.WorkFlowService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
@Service
public class WorkinghoursCheckCondition implements Condition {
	@Autowired
	private ReimburseService reimburseService;
	
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private  AttenceOverTimeService attenceOverTimeService;
	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		 Long reimburseId = (Long) transientVars.get("reimburseId");
		 Reimburse  reimburse = reimburseService.findOne(reimburseId);
		 List<ReimburseDetails> list = reimburseService.findReimburseDetails(reimburse);
		 Assert.notNull(list);
		 for(ReimburseDetails bean:list){
			 List<DailyReport> dailyReports= bean.getDailyReports();
			 if(dailyReports==null||dailyReports.size()==0) 
				 return false;
			 else {
				 for(DailyReport dailyReport:dailyReports){
					 List<AttenceOverTime> attenceOverTimes=attenceOverTimeService.findByDailyReportId(dailyReport.getId());
					 if(attenceOverTimes==null||attenceOverTimes.size()==0) 
						 return false;
				 }
			 }
		 }
		 return true;
	}
}
