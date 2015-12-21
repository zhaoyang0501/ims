package com.hsae.ims.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.BaseTrainingPlan;
import com.hsae.ims.repository.BaseTrainingPlanRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class BaseTrainingPlanService {

	@Autowired
	private BaseTrainingPlanRepository baseTrainingPlanRepository;
	@Autowired
	private UserRepository userRepository;
	
	public BaseTrainingPlan findOne(Long id){
		return baseTrainingPlanRepository.findOne(id);
	}
	
	public List<BaseTrainingPlan> findAll(){
		return (List<BaseTrainingPlan>) baseTrainingPlanRepository.findAll();
	}

	public Page<BaseTrainingPlan> findAll(int pageNumber, int pageSize, final String plan) {
		Specification<BaseTrainingPlan> spec = new Specification<BaseTrainingPlan>() {
			@Override
			public Predicate toPredicate(Root<BaseTrainingPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotBlank(plan)) {
					predicate.getExpressions().add(cb.like(root.get("title").as(String.class), "%"+ plan +"%"));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return baseTrainingPlanRepository.findAll(spec, pageRequest);
	}

	public BaseTrainingPlan save(BaseTrainingPlan entity) {
		return baseTrainingPlanRepository.save(entity);
	}

	public void delete(Long id) {
		baseTrainingPlanRepository.delete(id);
	}

	public Page<BaseTrainingPlan> findMyBaseTraining(int pageNumber, int pageSize, final Long cuid){
		Specification<BaseTrainingPlan> spec = new Specification<BaseTrainingPlan>() {
			@Override
			public Predicate toPredicate(Root<BaseTrainingPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (cuid != null && cuid > 0) {
					predicate.getExpressions().add(cb.isMember(userRepository.findOne(cuid), root.get("users").as(List.class)));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return baseTrainingPlanRepository.findAll(spec, pageRequest);
	}
	
}
