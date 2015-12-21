package com.hsae.ims.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.TrainingPlan;
public interface TrainingPlanRepository extends PagingAndSortingRepository<TrainingPlan, Long>, JpaSpecificationExecutor<TrainingPlan>{
	 @Modifying 
	 @Query("update TrainingPlan a set a.docurl1 = ?1 , a.docurl1 = ?2  ,a.docurl1 = ?3  where a.id = ?4") 
	 public int updateDocUrl(String docurl1, String docurl2,String docurl3,Long id);
}
