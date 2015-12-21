package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.UserRole;

public interface UserRoleRepository extends PagingAndSortingRepository<UserRole, Long>,JpaSpecificationExecutor<UserRole>{

	public List<UserRole> findByUserId(long userId);

	@Query("select r from Role r where r.id  in (select roleid from UserRole )")
	public List<Role> findAllHaveUserRoles();

	public List<UserRole> findByRoleid(long roleId);

	@Modifying
	@Query("delete from UserRole ur where ur.roleid=?1")
	public int deleteByRoleId(long id);

	@Modifying
	@Query("delete from UserRole ur where ur.roleid=?1 and ur.userId=?2")
	public void deleteByRoleIdAndUserId(long roleId, long uId);
}
