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

import com.hsae.ims.entity.UserContract;
import com.hsae.ims.repository.UserContractRepository;

@Service
public class UserContractService {

	@Autowired
	private UserContractRepository userContractRepository;
	
	public UserContract findOne(Long id){
		return userContractRepository.findOne(id);
	}
	
	public UserContract save(UserContract entity){
		return userContractRepository.save(entity);
	}
	
	public void delete(Long id){
		userContractRepository.delete(id);
	}
	
	public List<UserContract> findAll(){
		return (List<UserContract>) userContractRepository.findAll();
	}

	public Page<UserContract> findAll(int pageNumber, int pageSize, final Date signDate, final Date fromDate, final Date endDate, final Long userId) {
		Specification<UserContract> spec = new Specification<UserContract>() {
			@Override
			public Predicate toPredicate(Root<UserContract> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (signDate != null) {
					predicate.getExpressions().add(cb.equal(root.get("signDate").as(Date.class), signDate));
				}
				if (fromDate != null) {
					predicate.getExpressions().add(cb.equal(root.get("fromDate").as(Date.class), fromDate));
				}
				if (endDate != null) {
					predicate.getExpressions().add(cb.equal(root.get("endDate").as(Date.class), endDate));
				}
				if (userId != null && userId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("userId").as(Long.class), userId));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return userContractRepository.findAll(spec, pageRequest);
	}
}
