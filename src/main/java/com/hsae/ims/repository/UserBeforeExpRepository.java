package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserBeforeExp;

public interface UserBeforeExpRepository extends PagingAndSortingRepository<UserBeforeExp, Long>,JpaSpecificationExecutor<UserBeforeExp>{
	@Modifying
	@Query("delete from UserBeforeExp where resume = ?1")
	public void delByResumeId(long resumeId);
	
	@Query("select b from UserBeforeExp b where b.resume=?1")
	public List<UserBeforeExp> findUserBeforeExpByResumeId(long resume);
	
	@Modifying
	@Query("delete from UserBeforeExp where id = ?1")
	public void delBeforeExpById(long id);
}
