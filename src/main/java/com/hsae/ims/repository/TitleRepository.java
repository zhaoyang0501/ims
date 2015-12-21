package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Title;

public interface TitleRepository extends PagingAndSortingRepository<Title, Long>,JpaSpecificationExecutor<Title>{

}
