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

import com.hsae.ims.entity.BaseTrainingCourse;
import com.hsae.ims.entity.BaseTrainingPlanCourse;
import com.hsae.ims.repository.BaseTrainingCourseRepository;

@Service
public class BaseTrainingCourseService {

	@Autowired
	private BaseTrainingCourseRepository trainingBaseOnlineCourseRepository;
	@Autowired
	private BaseTrainingPlanCourseService baseTrainingPlanCourseService;
	
	public void save(BaseTrainingCourse entity){
		trainingBaseOnlineCourseRepository.save(entity);
	}
	
	public BaseTrainingCourse findOne(Long id){
		return trainingBaseOnlineCourseRepository.findOne(id);
	}
	
	public List<BaseTrainingCourse> findAll(){
		return (List<BaseTrainingCourse>) trainingBaseOnlineCourseRepository.findAll();
	}
	
	public boolean delete(Long id){
		List<BaseTrainingPlanCourse> planCourseList = baseTrainingPlanCourseService.findByCourse(id);
		if (planCourseList != null && planCourseList.size() > 0) {
			return false;
		}else{
			trainingBaseOnlineCourseRepository.delete(id);
			return true;
		}
	}

	/***
	 * 查询在线课程
	 * @param pageNumber
	 * @param pageSize
	 * @param courseName
	 * @param courseType
	 * @return
	 */
	public Page<BaseTrainingCourse> getAllCourse(int pageNumber, int pageSize, final String courseName, final String courseType) {
		
		Specification<BaseTrainingCourse> spec = new Specification<BaseTrainingCourse>() {
			@Override
			public Predicate toPredicate(Root<BaseTrainingCourse> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotBlank(courseName)) {
					predicate.getExpressions().add(cb.equal(root.get("courseName").as(String.class), courseName));
				}
				if(StringUtils.isNotBlank(courseType) && !courseType.equals("0"))
					predicate.getExpressions().add(cb.equal(root.get("courseType").as(String.class), courseType));
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return trainingBaseOnlineCourseRepository.findAll(spec, pageRequest);
	}
}
