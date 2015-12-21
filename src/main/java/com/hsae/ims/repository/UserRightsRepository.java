package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRights;

public interface UserRightsRepository  extends PagingAndSortingRepository<UserRights, Long>,JpaSpecificationExecutor<UserRights>{

	public List<UserRights> findByUserId(long userId);

	@Modifying 
	@Query("delete from UserRights ur where ur.userId=?1")
	public int deleteByUserId(long userId);

	@Query("select u from User u where u.id  in (select userId from UserRights )")
	public List<User> findAllHaveRightsUser();

}
