package com.hsae.ims.repository;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import  com.hsae.ims.entity.osworkflow.Wfentry;
import com.hsae.ims.entity.Reimburse;
public interface ReimburseRepository extends PagingAndSortingRepository<Reimburse, Long>,JpaSpecificationExecutor<Reimburse>{
	public Reimburse findByWfentry(Wfentry wfentry);
	@Query("select count(*) from  Reimburse s where s.createTime>=?1 and s.createTime<?2")
	public Integer countByMonth(Date start,Date end );
}
