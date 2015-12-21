package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkFlowTrainingRequireGatherDetails;

public interface TrainingRequirementDetailRepository extends PagingAndSortingRepository<WorkFlowTrainingRequireGatherDetails, Long>, JpaSpecificationExecutor<WorkFlowTrainingRequireGatherDetails>{

	List<WorkFlowTrainingRequireGatherDetails> findByDeptAndYear(long deptId, int year);

}
