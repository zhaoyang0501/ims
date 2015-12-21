package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ExamAnswer;

public interface ExamAnswerRepository extends PagingAndSortingRepository<ExamAnswer, Long>, JpaSpecificationExecutor<ExamAnswer> {

}
