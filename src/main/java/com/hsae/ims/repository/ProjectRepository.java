package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Project;

public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {

	@Query(value = "SELECT DISTINCT p.* FROM ims_system_project_member m  right JOIN ims_system_project p ON m.project = p.id WHERE (m.`user`=?1  and p.state='1') or p.id=0 ", nativeQuery = true)
	Iterable<Project> findByUserJoined(Long user);
	
	@Query(value = "SELECT  p.* from ims_system_project p where   p.state='1' or p.id=0 ", nativeQuery = true)
	List<Project> findAllvalidProjects();

}
