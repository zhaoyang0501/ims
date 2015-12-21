package com.hsae.ims.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.osworkflow.Wfentry;
public interface WorkFlowWfentryRepository extends PagingAndSortingRepository<Wfentry, Long>,JpaSpecificationExecutor<Wfentry>{
}
