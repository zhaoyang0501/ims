package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.TrainingRequirement;

public interface TrainingRequirementRepository extends PagingAndSortingRepository<TrainingRequirement, Long>, JpaSpecificationExecutor<TrainingRequirement>{

	List<TrainingRequirement> findByYear(Integer year);

	List<TrainingRequirement> findByOsWorkflow(Long wfentryId);

}
