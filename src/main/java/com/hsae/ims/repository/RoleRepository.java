package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long>,JpaSpecificationExecutor<Role>{

}
