package com.hsae.ims.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.User;
public interface AttenceOverTimeRepository extends PagingAndSortingRepository<AttenceOverTime, Long>,JpaSpecificationExecutor<AttenceOverTime>{
	public List<AttenceOverTime> findByUserAndOvertimeDate(User user,Date overtimeDate);
	
	/** 查询用户加班汇总 **/
	@Query(value="SELECT SUM(ao.check_hours)AS hours FROM ims_attence_overtime ao WHERE `user`=?1 AND ao.save_time BETWEEN ?2 AND ?3", nativeQuery=true)
	public Double findOverTimeStatistics(Long uid, Date startDate, Date endDate);
	
	public List<AttenceOverTime> findByDailyReportId(Long dailyReportId);
	
	@Query(value=" SELECT COUNT(1) FROM ( SELECT user FROM ims_attence_overtime t1 WHERE t1.overtime_date>=?1 AND t1.overtime_date<?2 GROUP BY t1.user ) t1", nativeQuery=true)
	public Integer findOverTimeUserCount( Date startDate, Date endDate);
	
	@Query(value=" SELECT COUNT(1) FROM ( SELECT user FROM ims_attence_overtime t1 WHERE t1.overtime_date>=?1 AND t1.overtime_date<?2 GROUP BY t1.user  HAVING  SUM(t1.check_hours)<?3 ) t1", nativeQuery=true)
    public Integer findOverTimeUserCount( Date startDate, Date endDate,Double spendhours);
	

	@Query("select count(*) from  AttenceOverTime s where s.saveTime>=?1 and s.saveTime<?2")
	public Integer countByMonth(Date start,Date end );
}
