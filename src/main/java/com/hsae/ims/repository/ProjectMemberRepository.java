package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ProjectMember;

public interface ProjectMemberRepository extends JpaSpecificationExecutor<ProjectMember>, 
								PagingAndSortingRepository<ProjectMember, Long>{

	@Query("from ProjectMember m where m.project.id=?1")
	public List<ProjectMember> findByProjectId(long pId);
	
	@Modifying
	@Query("delete from ProjectMember m where m.project.id=?1 and m.user.id = ?2 and m.role=?3")
	public void delete(long pId, long uId, String role);
}
