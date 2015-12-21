package com.hsae.ims.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserLanguage;

public interface UserLanguageRepository extends PagingAndSortingRepository<UserLanguage, Long>,JpaSpecificationExecutor<UserLanguage>{
//	@Modifying
//	@Query("update BookInfo a set a.status=?2 where a.id=?1")
//	public int bookStatus(long id, int status);
	
	@Query("select b from UserLanguage b where b.resume=?1")
	public List<UserLanguage> findUserLanguageByResumeId(long resume);
}
