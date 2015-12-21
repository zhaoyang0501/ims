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

import com.hsae.ims.entity.WorkflowAbsentee;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.repository.WorkflowAbsenteeRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.RightUtil;

@Service
public class WorkflowAbsenteeService {
	
	@Autowired
	private WorkflowAbsenteeRepository workflowAbsenteeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void save(WorkflowAbsentee workflow){
		workflowAbsenteeRepository.save(workflow);
	}

	public WorkflowAbsentee findByWfentry(Wfentry wfentry) {
		return workflowAbsenteeRepository.findByWfentry(wfentry);
	}

	public WorkflowAbsentee findOne(Long absenteeId) {
		return workflowAbsenteeRepository.findOne(absenteeId);
	}

	public void delete(Long absenteeId) {
		workflowAbsenteeRepository.delete(absenteeId);
	}

	/**
	 * 所有漏打卡申请分页查询
	 */
	public Page<WorkflowAbsentee> findAll(int pageNumber, int pageSize, final Date from, final Date to, final Integer state) {
		return (Page<WorkflowAbsentee>)workflowAbsenteeRepository.findAll(new Specification<WorkflowAbsentee>() {
			@Override
			public Predicate toPredicate(Root<WorkflowAbsentee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (from != null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createDate").as(Date.class), from));
				}
				if (to != null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createDate").as(Date.class), to));
				}
				if (state != null) {
					predicate.getExpressions().add(cb.equal(root.get("wfentry").get("state").as(Integer.class), state));
				}
				long cuid = RightUtil.getCurrentUserId();
				predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(cuid)));
				return predicate;
			}
		}, new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id")));
	}


}
