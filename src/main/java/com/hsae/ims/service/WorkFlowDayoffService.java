package com.hsae.ims.service;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.WorkFlowDayoff;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.WorkFlowDayoffRepository;
import com.osworkflow.SpringWorkflow;
@Service
public class WorkFlowDayoffService {
	
	@Autowired
	private WorkFlowDayoffRepository workFlowDayoffRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  SpringWorkflow springWorkflow;
	
	public void save(WorkFlowDayoff workFlowDay){
		workFlowDayoffRepository.save(workFlowDay);
	}
	public void delete(WorkFlowDayoff workFlowDay){
		workFlowDayoffRepository.delete(workFlowDay);
	}
	public WorkFlowDayoff find(Long  id){
		return workFlowDayoffRepository.findOne(id);
	}
	/***
	 * 根据流程ID查找
	 * @param workFlowId
	 * @return
	 */
	public WorkFlowDayoff findByWfentry(Wfentry wfentry){
		return workFlowDayoffRepository.findByWfentry(wfentry);
	}
	
	/**
	 * 查询请假
	 * @return
	 */
	public Page<WorkFlowDayoff> findAll(final int pageNumber, final int pageSize,
									final Date begin,final Date end,final Integer state, final Long user){
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "dayoffDate"));
		Specification<WorkFlowDayoff> spec = new Specification<WorkFlowDayoff>() {
				@Override
				public Predicate toPredicate(Root<WorkFlowDayoff> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate predicate = cb.conjunction();
					if (begin != null) {
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("dayoffDate").as(Date.class),begin));
					}
					if (end != null) {
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("dayoffDate").as(Date.class),end));
					}
					if (state != null) {
						predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class),state));
					}
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(user)));
					return predicate;
				}
			};
		Page<WorkFlowDayoff> result = (Page<WorkFlowDayoff>) workFlowDayoffRepository.findAll(spec, pageRequest);
		wrapStepName(result);
		return result;
	}
	
	/***
	 * 包装stepname
	 * @param workFlowSteps
	 */
	private void wrapStepName(Page<WorkFlowDayoff>  result){
		for(WorkFlowDayoff workFlowDayoff:result.getContent()){
			if(workFlowDayoff.getWfentry()!=null&&workFlowDayoff.getWfentry().getCurrentStep()!=null)
			workFlowDayoff.setStep(springWorkflow.getWorkflowDescriptor(workFlowDayoff.getWfentry().getName()).
					getStep(workFlowDayoff.getWfentry().getCurrentStep().getStepId()).getName());
		}
	}
}
