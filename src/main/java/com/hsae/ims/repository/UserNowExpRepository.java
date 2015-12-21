package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserNowExp;

public interface UserNowExpRepository extends PagingAndSortingRepository<UserNowExp, Long>,JpaSpecificationExecutor<UserNowExp>{
	@Modifying
	@Query("delete from UserNowExp where resume = ?1")
	public void delByResumeId(long resumeId);
	
	@Query("select b from UserNowExp b where b.resume=?1")
	public List<UserNowExp> findUserNowExpByResumeId(long resume);
	
	@Modifying
	@Query("delete from UserNowExp where id = ?1")
	public void delNowExpById(long id);
}
