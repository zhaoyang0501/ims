package com.hsae.ims.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkflowAbsentee;
import com.hsae.ims.entity.osworkflow.Wfentry;

public interface WorkflowAbsenteeRepository extends JpaSpecificationExecutor<WorkflowAbsentee>, PagingAndSortingRepository<WorkflowAbsentee, Long>{

	WorkflowAbsentee findByWfentry(Wfentry wfentry);
	
	@Query("select count(*) from  WorkflowAbsentee s where s.createDate>=?1 and s.createDate<?2")
	public Integer countByMonth(Date start,Date end );

}
