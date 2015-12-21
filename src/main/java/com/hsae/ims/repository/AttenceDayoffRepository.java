package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.User;


public interface AttenceDayoffRepository extends PagingAndSortingRepository<AttenceDayoff, Long>,JpaSpecificationExecutor<AttenceDayoff>{
	public List<AttenceDayoff> findByUserAndStartTimeLessThanAndEndTimeGreaterThan(User user,Date date1,Date date2);
	
	@Query(value="SELECT t1.* FROM ims_attence_dayoff t1 WHERE  user=?1 AND TO_DAYS(t1.start_time)<=TO_DAYS(?2) AND  TO_DAYS(t1.end_time)>=TO_DAYS(?2)",nativeQuery=true)
	public List<AttenceDayoff> findByUserAndDate(Long  userid,Date date1);

	@Query(value="SELECT SUM(ad.spend_hours) AS hours FROM ims_attence_dayoff ad WHERE ad.`user`=?1 AND ad.save_time BETWEEN ?2 AND ?3",nativeQuery=true)
	public Double findDayoffStatistics(long id, Date fromDate, Date toDate);
	
	@Modifying
	@Query("delete from AttenceDayoff m where m.user.id=?1 and m.dayoffDate = ?2 ")
	public void deleteByUserAndDate(Long uId, Date date);
}
