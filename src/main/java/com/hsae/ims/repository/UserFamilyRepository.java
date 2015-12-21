package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserFamily;

public interface UserFamilyRepository extends PagingAndSortingRepository<UserFamily, Long>,JpaSpecificationExecutor<UserFamily>{
	@Modifying
	@Query("delete from UserFamily where resume = ?1")
	public void delByResumeId(long resumeId);
	
	@Query("select b from UserFamily b where b.resume=?1")
	public List<UserFamily> findUserFamilyByResumeId(long resume);
	
	@Modifying
	@Query("delete from UserFamily where id = ?1")
	public void delFamilyById(long id);
}
