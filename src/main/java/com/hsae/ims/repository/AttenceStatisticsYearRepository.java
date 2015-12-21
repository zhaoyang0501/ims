package com.hsae.ims.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.hsae.ims.entity.AttenceStatisticsYear;
@Repository
public interface AttenceStatisticsYearRepository extends PagingAndSortingRepository<AttenceStatisticsYear, Long>, JpaSpecificationExecutor<AttenceStatisticsYear> {
}