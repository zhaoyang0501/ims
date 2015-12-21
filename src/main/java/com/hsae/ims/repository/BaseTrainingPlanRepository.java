package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.BaseTrainingPlan;

public interface BaseTrainingPlanRepository extends PagingAndSortingRepository<BaseTrainingPlan, Long>, JpaSpecificationExecutor<BaseTrainingPlan>{

}
