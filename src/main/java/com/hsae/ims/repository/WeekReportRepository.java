package com.hsae.ims.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WeekReport;
public interface WeekReportRepository  extends PagingAndSortingRepository<WeekReport, Long>,JpaSpecificationExecutor<WeekReport>{
	
	@Query("select d from WeekReport d where d.creater.id=?1 and d.week.id=?2")
	public List<WeekReport> findByWeekAndUser(Long userId, Long id);
	
	@Query("select d from WeekReport d where d.week.id=?1")
	public List<WeekReport> findByWeek(Long id);
	
	@Query("select d from WeekReport d where osworkflow in (?1) ")
	public List<WeekReport> findToDoWeekReports(List<Long> workflowids);
	
	public WeekReport findByOsworkflow(Long osworkflow); 
}
