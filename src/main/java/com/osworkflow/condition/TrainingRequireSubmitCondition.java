package com.osworkflow.condition;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.WorkFlowTrainingRequireGatherDetails;
import com.hsae.ims.service.TrainingRequirementDetailService;
import com.hsae.ims.service.WorkFlowTrainingRequireGatherService;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;

@Service
public class TrainingRequireSubmitCondition implements Condition{
	
	@Autowired
	private WorkFlowTrainingRequireGatherService workFlowTrainingRequireGatherService;
	
	@Autowired
	private TrainingRequirementDetailService trainingRequirementDetailService;

	@Override
	public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws WorkflowException {

		Long requireId = (Long) transientVars.get("requireId");
		WorkFlowTrainingRequireGather require = workFlowTrainingRequireGatherService.findOne(requireId);
		/** 需要提交的部门 **/
		List<Deptment> deptList = null;		//TODO
		for(Deptment dept : deptList){
			List<WorkFlowTrainingRequireGatherDetails> detailList = trainingRequirementDetailService.findByDeptAndPlanYear(dept.getId(), require.getYear());
			if (detailList == null || detailList.size() <= 0) {
				return false;
			}
		}
		return true;
	}

}
