package com.hsae.ims.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseCustomerDetail;
public interface ReimburseCustomerDetailRepository extends PagingAndSortingRepository<ReimburseCustomerDetail, Long>,JpaSpecificationExecutor<ReimburseCustomerDetail>{
	public List<ReimburseCustomerDetail> findByReimburse(Reimburse reimburse);
	@Modifying 
	@Query("delete from ReimburseCustomerDetail t1 where t1.reimburse=?1")
	public void deleteByReimburse(Reimburse reimburse);
}
