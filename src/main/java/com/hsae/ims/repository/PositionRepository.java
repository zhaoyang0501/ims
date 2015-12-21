package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Position;

public interface PositionRepository extends PagingAndSortingRepository<Position, Long>,JpaSpecificationExecutor<Position>{

}
