package com.hsae.ims.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkFlowDayoff;
import com.hsae.ims.entity.osworkflow.Wfentry;
public interface WorkFlowDayoffRepository extends PagingAndSortingRepository<WorkFlowDayoff, Long>,JpaSpecificationExecutor<WorkFlowDayoff>{
	public WorkFlowDayoff findByWfentry(Wfentry wfentry);
	@Query("select count(*) from  WorkFlowDayoff s where s.saveTime>=?1 and s.saveTime<?2")
	public Integer countByMonth(Date start,Date end );
	
}
