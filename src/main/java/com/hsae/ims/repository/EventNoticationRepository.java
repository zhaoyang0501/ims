package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.EventNotication;

public interface EventNoticationRepository extends PagingAndSortingRepository<EventNotication, Long>, JpaSpecificationExecutor<Long>{

	@Query(value = "SELECT * FROM ims_system_event_notication n WHERE n.receiver=?1 AND n.state = '0'", nativeQuery = true)
	List<EventNotication> findUser4UnreadNotices(Long userId);

}
