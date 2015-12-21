package com.hsae.ims.repository;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkFlowOverTime;
import com.hsae.ims.entity.osworkflow.Wfentry;
public interface WorkFlowOverTimeRepository extends PagingAndSortingRepository<WorkFlowOverTime, Long>,JpaSpecificationExecutor<WorkFlowOverTime>{
	public WorkFlowOverTime findByWfentry(Wfentry wfentry); 
	@Query(value ="SELECT IFNULL(SUM(t1.check_hours),0) FROM ims_attence_overtime t1 WHERE t1.overtime_date>=?1 AND t1.overtime_date<?2 and t1.user=?3 ", nativeQuery=true) 
	public Double findTotalHours(Date startDate,Date endDate,Long userid);
	
	@Query("select count(*) from  WorkFlowOverTime s where s.saveTime>=?1 and s.saveTime<?2")
	public Integer countByMonth(Date start,Date end );		
}



