/**
 * 
 */
package com.hsae.ims.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.hsae.ims.entity.UserResumeDimission;

/**
 * @author caowei
 *
 */
public interface UserResumeDimissionRepository extends PagingAndSortingRepository<UserResumeDimission, Long>, JpaSpecificationExecutor<UserResumeDimission> {

	UserResumeDimission findByResumeId(Long resumeId);

}
