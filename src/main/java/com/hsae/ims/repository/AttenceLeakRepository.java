package com.hsae.ims.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.hsae.ims.entity.AttenceAbsentee;
public interface AttenceLeakRepository extends PagingAndSortingRepository<AttenceAbsentee, Long>,JpaSpecificationExecutor<AttenceAbsentee>{

}
