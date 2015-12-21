package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.DailyReportWorkStage;

public interface DailyReportWorkStageRepository extends PagingAndSortingRepository<DailyReportWorkStage, Long>,JpaSpecificationExecutor<DailyReportWorkStage>{

	public List<DailyReportWorkStage> findByDeptId(Long deptId);
	
	public List<DailyReportWorkStage> findByType(int type);
}
