package com.hsae.ims.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.DailyReport;
import com.hsae.ims.entity.User;
public interface DailyReportRepository  extends PagingAndSortingRepository<DailyReport, Long>,JpaSpecificationExecutor<DailyReport>{
	@Query("select report from DailyReport report where reportDate>=?1 and reportDate <=?2 and user.id=?3 order by createDate")
	public List<DailyReport> findDailyReportByMonth(Date start, Date end, long userId);
	@Query("select report from DailyReport report where  user.dept.id=?1 and reportDate>=?2 and reportDate <=?3")
	public List<DailyReport> findDailyReportByDept(Long deptId,Date start, Date end);
	
	@Query("select report from DailyReport report where  user.dept.id=?1 and reportDate>=?2 and reportDate <=?3 "
			+ "and user in ( select weekreport.creater from  WeekReport weekreport where  weekreport.week.id=?4)")
	public List<DailyReport> findDailyReportByDept(Long deptId,Date start, Date end,Long weekid);
	
	public List<DailyReport> findByReportDateAndUser(Date date, User user);
	public List<DailyReport> findByTypeAndReportDate(String type, Date date);
	public List<DailyReport> findByReportDateAndUserAndType(Date date, User user, String type);
	
}
