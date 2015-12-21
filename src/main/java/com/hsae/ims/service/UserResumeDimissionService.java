package com.hsae.ims.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.UserResumeDimission;
import com.hsae.ims.repository.UserResumeDimissionRepository;

@Service
public class UserResumeDimissionService {

	@Autowired
	private UserResumeDimissionRepository userResumeDimissionRepository;

	public UserResumeDimission save(UserResumeDimission entity) {
		return userResumeDimissionRepository.save(entity);
	}
	
	public UserResumeDimission findOne(Long id){
		return userResumeDimissionRepository.findOne(id);
	}
	
	public void delete(Long id){
		userResumeDimissionRepository.delete(id);
	}

	public UserResumeDimission findByResumeId(Long resumeId) {
		return userResumeDimissionRepository.findByResumeId(resumeId);
	}

	public List<UserResumeDimission> findAll() {
		return (List<UserResumeDimission>) userResumeDimissionRepository.findAll();
	}

	public Page<UserResumeDimission> findAll(int pageNumber, int pageSize, final Long userId, final Date planDate_s, final Date planDate_e, 
			final Date actualDate_s, final Date actualDate_e, final Integer dismission_type) {
		Specification<UserResumeDimission> spec = new Specification<UserResumeDimission>() {
			@Override
			public Predicate toPredicate(Root<UserResumeDimission> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (planDate_s != null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("plandate").as(Date.class), planDate_s));
				}
				if (planDate_e != null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("plandate").as(Date.class), planDate_e));
				}
				if (actualDate_s != null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("actualdate").as(Date.class), actualDate_s));
				}
				if (actualDate_e != null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("actualdate").as(Date.class), actualDate_e));
				}
				if (dismission_type != null) {
					predicate.getExpressions().add(cb.equal(root.get("type").as(String.class), dismission_type));
				}
				if (userId != null && userId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("userId").as(Long.class), userId));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
		return userResumeDimissionRepository.findAll(spec, pageRequest);
	}
}
