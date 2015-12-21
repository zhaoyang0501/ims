package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BaseTrainingCourse;

public interface BaseTrainingCourseRepository extends PagingAndSortingRepository<BaseTrainingCourse, Long>, JpaSpecificationExecutor<BaseTrainingCourse>{

}
