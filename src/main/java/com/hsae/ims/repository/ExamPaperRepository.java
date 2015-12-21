package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ExamPaper;

public interface ExamPaperRepository extends PagingAndSortingRepository<ExamPaper, Long>, JpaSpecificationExecutor<ExamPaper>{

}
