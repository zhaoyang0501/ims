package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.TimetableSetting;

public interface TimetableSettingRepository extends JpaSpecificationExecutor<TimetableSetting>, PagingAndSortingRepository<TimetableSetting, Long>{

	public TimetableSetting findByMonth(int month);
	
}
