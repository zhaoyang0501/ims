package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Rights;

public interface RightsRepository extends PagingAndSortingRepository<Rights, Long>,JpaSpecificationExecutor<Rights>{

}
