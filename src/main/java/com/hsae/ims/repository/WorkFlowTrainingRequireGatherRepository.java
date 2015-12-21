package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkFlowTrainingRequireGather;
import com.hsae.ims.entity.osworkflow.Wfentry;

public interface WorkFlowTrainingRequireGatherRepository extends PagingAndSortingRepository<WorkFlowTrainingRequireGather, Long>, JpaSpecificationExecutor<WorkFlowTrainingRequireGather>{

	List<WorkFlowTrainingRequireGather> findByYear(Integer year);

	List<WorkFlowTrainingRequireGather> findByWfentry(Wfentry wfentry);

	@Query(value = "select count(*) from WorkFlowTrainingRequireGather w where w.createDate >= ?1 and w.createDate < ?2")
	Integer countByMonth(Date truncate, Date addMonths);

}
