package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.entity.User;

public interface AttenceTravelRepository extends PagingAndSortingRepository<AttenceTravel, Long>,JpaSpecificationExecutor<AttenceTravel>{
	@Query(value="SELECT t1.* FROM ims_attence_travel t1 WHERE  user=?1 AND TO_DAYS(t1.start_time)<=TO_DAYS(?2) AND  TO_DAYS(t1.end_time)>=TO_DAYS(?2)",nativeQuery=true)
	public List<AttenceTravel> findByUserAndDate(Long  userid,Date date1);

	public List<AttenceTravel> findByUserAndStartTimeLessThanAndEndTimeGreaterThan(User user,Date date1,Date date2);
	@Modifying
	@Query("delete from AttenceTravel m where m.user.id=?1 and m.travelDate = ?2 ")
	public void deleteByUserAndDate(Long uId, Date date);
}
