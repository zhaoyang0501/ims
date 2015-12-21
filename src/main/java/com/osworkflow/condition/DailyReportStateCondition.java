package com.osworkflow.condition;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.WorkFlowService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
/***
 * 判断加班的日志有没有提交 
 * @author panchaoyang
 *
 */
@Service
public class DailyReportStateCondition implements Condition{
	
	@Autowired
	private ReimburseService reimburseService;
	
	@Autowired
	private WorkFlowService workFlowService;
	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		 Long reimburseId = (Long) transientVars.get("reimburseId");
		 Reimburse  reimburse = reimburseService.findOne(reimburseId);
		 List<ReimburseDetails> list = reimburseService.findReimburseDetails(reimburse);
		 Assert.notNull(list);
		 for(ReimburseDetails bean:list){
			if(bean.getDailyReports()==null||bean.getDailyReports().size()==0)  return false;
		 }
		 return true;
	}

}
