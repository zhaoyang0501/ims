package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.WorkAndHolidaySetting;

public interface WorkAndHolidaySettingRepository extends JpaSpecificationExecutor<WorkAndHolidaySetting>, PagingAndSortingRepository<WorkAndHolidaySetting, Long>{

	public List<WorkAndHolidaySetting> findByDate(Date date);

	@Modifying
	@Query(value = "delete from ims_system_workandholiday_setting where date = ?1",nativeQuery = true)
	public void deleteByDate(Date date);

	@Query("from WorkAndHolidaySetting s where s.date>=?1 and s.date<=?2")
	public List<WorkAndHolidaySetting> findHolidayByMonth(Date start, Date end);
}
