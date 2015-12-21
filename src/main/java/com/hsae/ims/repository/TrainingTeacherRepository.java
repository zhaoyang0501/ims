package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.TrainingTeacher;

public interface TrainingTeacherRepository extends PagingAndSortingRepository<TrainingTeacher, Long>, JpaSpecificationExecutor<TrainingTeacher>{

	@Query("select t from TrainingTeacher t where t.user.id = ?1")
	public TrainingTeacher findByUserId(long userId);
	

}
