package com.hsae.ims.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.osworkflow.HistoryStep;
import com.hsae.ims.entity.osworkflow.Wfentry;
public interface WorkFlowHistorystepRepository extends PagingAndSortingRepository<HistoryStep, Long>,JpaSpecificationExecutor<HistoryStep>{
	public List<HistoryStep> findByWfentry(Wfentry wfentry);
}
