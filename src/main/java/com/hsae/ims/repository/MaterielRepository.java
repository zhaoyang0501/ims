package com.hsae.ims.repository;



import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.Materiel;

public interface MaterielRepository extends PagingAndSortingRepository<Materiel, Long>,JpaSpecificationExecutor<Materiel>{
}
