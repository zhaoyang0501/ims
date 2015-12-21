package com.osworkflow.condition;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.WorkflowException;
/***
 * 判断调用者是否是创建者
 * @author panchaoyang
 *
 */
@Service
public class IsUserCreateCondition implements Condition{
	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		  WorkflowContext context = (WorkflowContext) transientVars.get("context");
		  Long createrId = (Long) transientVars.get("creater");
		  if(context.getCaller().equals(String.valueOf(createrId)))
			  return true;
		  return false;
		
	}

}
