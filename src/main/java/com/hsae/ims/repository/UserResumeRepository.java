package com.hsae.ims.repository;




import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;

public interface UserResumeRepository extends PagingAndSortingRepository<UserResume, Long>,JpaSpecificationExecutor<UserResume>{
	@Query("select b from UserResume b where b.user=?1")
	public UserResume findUserResumeByUser(User user);
	
	@Query("select b from UserResume b where b.id=?1")
	public UserResume findUserResumeById(long id);
}
