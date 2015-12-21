package com.hsae.ims.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
public interface WorkFlowCurrentstepRepository extends PagingAndSortingRepository<CurrentStep, Long>,JpaSpecificationExecutor<CurrentStep>{
	public List<CurrentStep> findByWfentry(Wfentry wfentry);
}
