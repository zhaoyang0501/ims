package com.hsae.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Code;

public interface CodeRepository extends JpaSpecificationExecutor<Code>, PagingAndSortingRepository<Code, Long>{
	public List<Code> findByIdentificationOrderBySortNumberAsc(String identification);
	public Code findByIdentificationAndCode(String identification,String code );
}
