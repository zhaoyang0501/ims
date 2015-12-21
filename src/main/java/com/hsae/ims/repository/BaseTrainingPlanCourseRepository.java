package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BaseTrainingPlanCourse;

public interface BaseTrainingPlanCourseRepository extends PagingAndSortingRepository<BaseTrainingPlanCourse, Long>, JpaSpecificationExecutor<BaseTrainingPlanCourse>{

	List<BaseTrainingPlanCourse> findByPlan(Long plan);

	@Query(name="delete from BaseTrainingPlanCourse where plan = ?1")
	@Modifying
	void deleteByPlan(Long id);

	@Query("select s from BaseTrainingPlanCourse s where s.course.id = ?1")
	List<BaseTrainingPlanCourse> findByCourseId(Long courseId);

}
