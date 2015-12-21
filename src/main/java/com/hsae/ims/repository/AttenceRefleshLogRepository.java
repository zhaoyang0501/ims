package com.hsae.ims.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.AttenceRefleshLog;
public interface AttenceRefleshLogRepository extends PagingAndSortingRepository<AttenceRefleshLog, Long>,
														JpaSpecificationExecutor<AttenceRefleshLog>{
	public  List<AttenceRefleshLog> findByAttenceDate(Date date);
}



