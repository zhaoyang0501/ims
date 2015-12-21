/**
 * 
 */
package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserContract;

/**
 * @author caowei
 *
 */
public interface UserContractRepository extends JpaSpecificationExecutor<UserContract>, PagingAndSortingRepository<UserContract, Long> {

}
