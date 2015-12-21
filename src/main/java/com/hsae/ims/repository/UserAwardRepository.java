package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserAward;

public interface UserAwardRepository extends PagingAndSortingRepository<UserAward, Long>,JpaSpecificationExecutor<UserAward>{
	@Modifying
	@Query("delete from UserAward where resume = ?1")
	public void delByResumeId(long resumeId);
	
	@Query("select b from UserAward b where b.resume=?1")
	public List<UserAward> findUserAwardByResumeId(long resume);
	
	@Modifying
	@Query("delete from UserAward where id = ?1")
	public void delAwardById(long id);
}
