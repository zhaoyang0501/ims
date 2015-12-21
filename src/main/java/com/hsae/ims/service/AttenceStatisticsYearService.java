package com.hsae.ims.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceStatisticsYear;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceStatisticsYearRepository;
/***
 * 年假
 * @author panchaoyang
 *
 */
@Service
public class AttenceStatisticsYearService {

	@Autowired
	private AttenceStatisticsYearRepository attenceStatisticsYearRepository;

	public void save(AttenceStatisticsYear attenceStatisticsYear){
		attenceStatisticsYearRepository.save(attenceStatisticsYear);
	}
	
	public AttenceStatisticsYear find(Long id){
		return attenceStatisticsYearRepository.findOne(id);
	}
	
	public List<AttenceStatisticsYear> findAll(final String year,final User user){
		Specification<AttenceStatisticsYear> spec = new Specification<AttenceStatisticsYear>() {
			@Override
			public Predicate toPredicate(Root<AttenceStatisticsYear> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotBlank(year)) {
					predicate.getExpressions().add(cb.equal(root.get("year").as(String.class),year));
				}
				if (user!=null) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class),user));
				}
				return predicate;
			}
		};
		return attenceStatisticsYearRepository.findAll(spec,new Sort(Direction.DESC, "id"));
	}
}
