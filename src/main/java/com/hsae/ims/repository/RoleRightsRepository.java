package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.RoleRights;

public interface RoleRightsRepository extends PagingAndSortingRepository<RoleRights, Long>,JpaSpecificationExecutor<RoleRights>{

	public List<RoleRights> findByRoleId(long roleId);
	
	@Modifying
	@Query("delete from RoleRights rr where rr.roleId=?1")
	public void deleteByRoleId(long roleId);
}
