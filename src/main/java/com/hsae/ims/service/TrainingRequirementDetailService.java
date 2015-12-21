package com.hsae.ims.service;

import java.util.List;

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

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.WorkFlowTrainingRequireGatherDetails;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.TrainingRequirementDetailRepository;

@Service
public class TrainingRequirementDetailService {

	@Autowired
	private TrainingRequirementDetailRepository trainingRequirementDetailRepository;

	public List<WorkFlowTrainingRequireGatherDetails> findByDeptAndPlanYear(long deptId, int year) {
		return trainingRequirementDetailRepository.findByDeptAndYear(deptId, year);
	}
	
	public void save(WorkFlowTrainingRequireGatherDetails entity){
		trainingRequirementDetailRepository.save(entity);
	}

	public Page<WorkFlowTrainingRequireGatherDetails> findByYear(int pageNumber, int pageSize,final Integer year) {
		Sort sort = new Sort(Direction.DESC, "id");
		
		return (Page<WorkFlowTrainingRequireGatherDetails>)trainingRequirementDetailRepository.findAll(
				new Specification<WorkFlowTrainingRequireGatherDetails>() {
			@Override
			public Predicate toPredicate(Root<WorkFlowTrainingRequireGatherDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (year != null && year > 0) {
					predicate.getExpressions().add(cb.equal(root.get("year").as(Integer.class), year));
				}
				return predicate;
			}
		}, new PageRequest(pageNumber - 1, pageSize, sort)) ;
	}
	
}
