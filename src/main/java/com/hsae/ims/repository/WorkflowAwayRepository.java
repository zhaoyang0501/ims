package com.hsae.ims.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkflowAway;
import com.hsae.ims.entity.osworkflow.Wfentry;

public interface WorkflowAwayRepository extends PagingAndSortingRepository<WorkflowAway, Long>, JpaSpecificationExecutor<WorkflowAway>{

	WorkflowAway findByWfentry(Wfentry wfentry);
	@Query("select count(*) from  WorkflowAway s where s.saveTime>=?1 and s.saveTime<?2")
	public Integer countByMonth(Date start,Date end );
}
