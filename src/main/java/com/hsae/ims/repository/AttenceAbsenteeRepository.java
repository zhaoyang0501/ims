package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.AttenceAbsentee;


public interface AttenceAbsenteeRepository extends PagingAndSortingRepository<AttenceAbsentee, Long>,JpaSpecificationExecutor<AttenceAbsentee>{
	public List<AttenceAbsentee> findByAbsenteeDateAndUser(Date date, Long user);

	@Query(value="select count(1) from ims_attence_absentee a where a.user=?1 and a.absentee_date between ?2 and ?3", nativeQuery = true)
	public Integer countAbsenteeFrequency(Long userId, Date firstDayOfMonth, Date lastDayOfMonth);
	
	@Modifying
	@Query("delete from AttenceAbsentee m where m.user=?1 and m.absenteeDate = ?2 ")
	public void deleteByUserAndDate(Long uId, Date date);
}



