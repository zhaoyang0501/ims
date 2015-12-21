package com.osworkflow.function;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsae.ims.entity.User;
import com.hsae.ims.service.UserService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
@Service
public class GetNextOwnerFunction implements FunctionProvider {
	@Autowired
	UserService userService;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void execute(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		User user=(User) transientVars.get("caller");
		if(user==null)
			 throw new RuntimeException("找不到接手人");
		//找部门经理
		User manager=userService.findManagerByDept(user.getDept().getId());
		if(manager==null)
			throw new RuntimeException("找不到接手人");
		transientVars.put("nextowner", manager.getId());
		
	}

}
