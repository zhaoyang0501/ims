package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ExamQuestion;

public interface ExamQuestionRepository extends PagingAndSortingRepository<ExamQuestion, Long>, JpaSpecificationExecutor<ExamQuestion>{

	@Query("from ExamQuestion t where t.paper.id = ?1")
	List<ExamQuestion> findByPaperId(Long i);
	
}
