package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hsae.ims.entity.AttenceStatistics;
import com.hsae.ims.entity.User;

@Repository
public interface AttenceStatisticsRepository extends PagingAndSortingRepository<AttenceStatistics, Long>, JpaSpecificationExecutor<AttenceStatistics> {

	@Query(value = "SELECT * FROM (SELECT * FROM ims_attence_statistics s WHERE s.create_time < ?1 ORDER BY s.create_time DESC) a GROUP BY a.user", nativeQuery=true)
	public List<AttenceStatistics> findLatestAttenceStatictcs(Date date);

	@Query(value = "SELECT max(create_time) FROM ims_attence_statistics", nativeQuery=true)
	public Date  findLastUpdate();
	
	public List<AttenceStatistics> findByMonthAndUser(String yyyyMM,User user);
	
}