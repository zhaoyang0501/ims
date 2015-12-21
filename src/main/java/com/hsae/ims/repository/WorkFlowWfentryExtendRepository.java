package com.hsae.ims.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.osworkflow.WfentryExtend;
public interface WorkFlowWfentryExtendRepository extends PagingAndSortingRepository<WfentryExtend, Long>,JpaSpecificationExecutor<WfentryExtend>{
}
