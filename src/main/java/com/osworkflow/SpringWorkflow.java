package com.osworkflow;

import com.opensymphony.workflow.AbstractWorkflow;
import com.opensymphony.workflow.basic.BasicWorkflowContext;

public class SpringWorkflow extends AbstractWorkflow {

	public SpringWorkflow(){
	}
	public void SetContext(String caller){
		super.context = new BasicWorkflowContext(caller);
	}
	
}
