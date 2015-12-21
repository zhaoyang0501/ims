package com.osworkflow.condition;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.service.AttenceOverTimeService;
import com.hsae.ims.service.ReimburseService;
import com.hsae.ims.service.WorkFlowService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
@Service
public class InternalTranJoinedCondition implements Condition {
	@Autowired
	private ReimburseService reimburseService;
	
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private  AttenceOverTimeService attenceOverTimeService;
	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		 return true;
	}
}
