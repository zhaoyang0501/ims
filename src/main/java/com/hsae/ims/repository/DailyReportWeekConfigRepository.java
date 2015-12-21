package com.hsae.ims.repository;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.DailyReportWeekConfig;

public interface DailyReportWeekConfigRepository extends PagingAndSortingRepository<DailyReportWeekConfig, Long>,JpaSpecificationExecutor<DailyReportWeekConfig>{

	@Query("select r from DailyReportWeekConfig r where r.startDate<=?1 and r.endDate>=?1 ")
	public DailyReportWeekConfig findWeekConfigByDate(Date date);
}
