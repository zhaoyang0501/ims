package com.hsae.ims.service;

import java.util.Date;
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

import com.hsae.ims.entity.TrainingPlan;
import com.hsae.ims.repository.TrainingPlanRepository;

@Service
public class TrainingPlanService {

	@Autowired
	private TrainingPlanRepository trainingPlanRepository;
	
	public List<TrainingPlan> findAll(){
		return (List<TrainingPlan>) trainingPlanRepository.findAll();
	}
	
	public TrainingPlan findOne(Long id){
		return trainingPlanRepository.findOne(id);
	}
	
	public void save(TrainingPlan trainingPlan){
		 trainingPlanRepository.save(trainingPlan);
	}
	
	public void updateDocUrl(TrainingPlan trainingPlan){
		trainingPlanRepository.updateDocUrl(trainingPlan.getDocurl1(), trainingPlan.getDocurl2(), trainingPlan.getDocurl3(), trainingPlan.getId());
	}
	public Page<TrainingPlan> findAll(int pageNumber, int pageSize, final Date startDate,final Date endDate){
		Specification<TrainingPlan> spec = new Specification<TrainingPlan>() {
			@Override
			public Predicate toPredicate(Root<TrainingPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (startDate != null ) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("planStartTime").as(Date.class),startDate));
				}
				if (endDate != null ) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("planEndTime").as(Date.class),endDate));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return trainingPlanRepository.findAll(spec, pageRequest);
	}
}
	
