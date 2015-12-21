package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.ProjectWbs;

public interface ProjectWbsRepository  extends PagingAndSortingRepository<ProjectWbs, Long>,JpaSpecificationExecutor<ProjectWbs>{
	
}
