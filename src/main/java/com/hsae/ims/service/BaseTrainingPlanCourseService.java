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

import com.hsae.ims.entity.BaseTrainingPlanCourse;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.BaseTrainingPlanCourseRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class BaseTrainingPlanCourseService {
	
	@Autowired
	private BaseTrainingPlanCourseRepository baseTrainingPlanCourseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void save(List<BaseTrainingPlanCourse> list){
		baseTrainingPlanCourseRepository.save(list);
	}
	
	public List<BaseTrainingPlanCourse> findByPlan(Long plan){
		return baseTrainingPlanCourseRepository.findByPlan(plan);
	}

	public void delete(Long id) {
		baseTrainingPlanCourseRepository.deleteByPlan(id);
	}

	public Page<BaseTrainingPlanCourse> findMyAll(int pageNumber, int pageSize, final Long cuid) {
		Specification<BaseTrainingPlanCourse> spec = new Specification<BaseTrainingPlanCourse>() {
			@Override
			public Predicate toPredicate(Root<BaseTrainingPlanCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (cuid != null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(cuid)));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return baseTrainingPlanCourseRepository.findAll(spec, pageRequest);
	}

	public BaseTrainingPlanCourse findOne(Long planCourseId) {
		return baseTrainingPlanCourseRepository.findOne(planCourseId);
	}

	public void save(BaseTrainingPlanCourse planCourse) {
		baseTrainingPlanCourseRepository.save(planCourse);
	}

	public List<BaseTrainingPlanCourse> findByCourse(Long courseId) {
		return baseTrainingPlanCourseRepository.findByCourseId(courseId);
	}
}
