package com.hsae.ims.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.MeetingReserve;
import com.hsae.ims.entity.MeetingRoom;

public interface MeetingReserveRepository extends PagingAndSortingRepository<MeetingReserve, Long>,JpaSpecificationExecutor<MeetingReserve>{
	@Query("select b from MeetingReserve b where b.meetingStartTime>=?1 and b.meetingEndTime <=?2 order by b.meetingCreateTime")
	public List<MeetingReserve> findReserveReportByMonth(Date start , Date end);
	
	@Query("select b from MeetingReserve b  order by b.meetingStartTime DESC")
	public List<MeetingReserve> findReserveReport();
	
	@Modifying
	@Query(value="update MeetingReserve set meetingStatus = ?2 where id =?1")
	public void updateState(Long id, int state);
	
	@Modifying
	@Query(value="update MeetingReserve set flag = ?2 where id =?1")
	public void updateFlag(Long id, int flag);
	
	@Modifying
	@Query(value="update MeetingReserve set inform = ?2 where id =?1")
	public void updateInform(Long id, int inform);
	
	@Modifying
	@Query(value="update MeetingReserve set meetingStatus = ?2,flag = ?3,inform = ?4 where id =?1")
	public void updateParameter(Long id, int meetingStatus, int flag, int inform);
	
	@Query("select b from MeetingReserve b where b.room=?1")
	public List<MeetingReserve> findReserveByRoom(MeetingRoom room);
	
}
