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
import com.hsae.ims.entity.WorkflowAway;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.WorkflowAwayRepository;
import com.hsae.ims.utils.RightUtil;

@Service
public class WorkflowAwayService {

	@Autowired
	private WorkflowAwayRepository workflowAwayRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Page<WorkflowAway> findAll(int pageNumber, int pageSize, final Date from, final Date to, final Integer state){
		Specification<WorkflowAway> spec = new Specification<WorkflowAway>() {
			@Override
			public Predicate toPredicate(Root<WorkflowAway> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (from != null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("fillDate").as(Date.class), from));
				}
				if (to != null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("fillDate").as(Date.class), to));
				}
				if (state != null) {
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class), state));
				}
				long cuid = RightUtil.getCurrentUserId();
				predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(cuid)));
				return predicate;
			}
		};
		PageRequest pageable = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.ASC, "id"));
		return workflowAwayRepository.findAll(spec, pageable);
	}

	public void save(WorkflowAway workflowAway) {
		workflowAwayRepository.save(workflowAway);
	}

	public WorkflowAway findByWfentry(Wfentry wfentry) {
		return workflowAwayRepository.findByWfentry(wfentry);
	}

	public WorkflowAway findOne(Long awayId) {
		return workflowAwayRepository.findOne(awayId);
	}
	public void delete(Long id){
		workflowAwayRepository.delete(id);
	}
}

