package com.hsae.ims.service;

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

import com.hsae.ims.entity.ProjectWbs;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.ProjectWbsRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class ProjectWbsService {
	@Autowired
	private ProjectWbsRepository projectWbsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Iterable<ProjectWbs> findByUserJoined(User user){
		return projectWbsRepository.findAll();
	}
	public ProjectWbs getProjectWbsById(Long id){
		return projectWbsRepository.findOne(id);
	}
	
	private PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		Sort sort = new Sort(Direction.ASC, "id");
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 查询所有项目
	 * @param projectName 
	 * @param pmId 
	 * @param pageSize 
	 * @param pageNumber 
	 * @return
	 */
	public Page<ProjectWbs> findProjectWbsList(int pageNumber, int pageSize, long pmId, String wbsName) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
		Specification<ProjectWbs> spec = buildSpecification(pmId, wbsName);
		return (Page<ProjectWbs>)projectWbsRepository.findAll(spec, pageRequest);
		
	}
	
	private Specification<ProjectWbs> buildSpecification(final Long pmId, final String wbsName) {
		return new Specification<ProjectWbs>() {
			@Override
			public Predicate toPredicate(Root<ProjectWbs> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (pmId != null && pmId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(pmId)));
				}
				if (wbsName != null) {
					predicate.getExpressions().add(cb.like(root.get("wbsName").as(String.class), "%"+wbsName+"%"));
				}
				return predicate;
			}
		};
	}
	
	
	public ProjectWbs findOne(long id) {
		return projectWbsRepository.findOne(id);
	}
	
}
