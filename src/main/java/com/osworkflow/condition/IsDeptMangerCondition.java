package com.osworkflow.condition;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.User;
import com.hsae.ims.service.UserService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
/***
 * 判断是不是部门管理从而决定走哪个流程 
 * @author panchaoyang
 *
 */
@Service
public class IsDeptMangerCondition implements Condition{
	@Autowired
	private UserService userService;
	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps)
			throws WorkflowException {
		 Long createrId = (Long) transientVars.get("creater");
		 User user=userService.findOne(createrId);
		 return user.getId()==user.getDept().getManager();
	}

}
