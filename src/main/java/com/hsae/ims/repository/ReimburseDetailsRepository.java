package com.hsae.ims.repository;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.entity.User;
public interface ReimburseDetailsRepository extends PagingAndSortingRepository<ReimburseDetails, Long>,JpaSpecificationExecutor<ReimburseDetails>{
	public List<ReimburseDetails> findByReimburse(Reimburse reimburse);
	@Modifying 
	@Query("delete from ReimburseDetails t1 where t1.reimburse=?1")
	public void deleteByReimburse(Reimburse reimburse);
	public List<ReimburseDetails> findByUserAndReimburseReimburseDate(User user,Date date);
}
