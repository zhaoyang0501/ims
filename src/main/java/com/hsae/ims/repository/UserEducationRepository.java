package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserEducation;

public interface UserEducationRepository extends PagingAndSortingRepository<UserEducation, Long>,JpaSpecificationExecutor<UserEducation>{
	@Modifying
	@Query("delete from UserEducation where resume = ?1")
	public void delByResumeId(long resumeId);

	@Query("select b from UserEducation b where b.resume=?1")
	public List<UserEducation> findUserEducationByResumeId(long resume);
	
	@Modifying
	@Query("delete from UserEducation where id = ?1")
	public void delEduById(long id);
}
