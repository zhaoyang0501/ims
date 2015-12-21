package com.hsae.ims.repository;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.MeetingRoom;

public interface MeetingRoomRepository extends PagingAndSortingRepository<MeetingRoom, Long>,JpaSpecificationExecutor<MeetingRoom>{
	@Query("select b from MeetingRoom b where b.roomName=?1")
	public MeetingRoom findRoomByName(String name);
	
	@Modifying
	@Query(value="update MeetingRoom set roomStatus = ?2 where id =?1")
	public void updateState(Long id, int state);
}
