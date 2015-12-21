package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ExamAnswerDetails;

public interface ExamAnswerDetailsRepository extends PagingAndSortingRepository<ExamAnswerDetails, Long>, JpaSpecificationExecutor<ExamAnswerDetails> {

	ExamAnswerDetails findByQuestionId(Long id);

}
