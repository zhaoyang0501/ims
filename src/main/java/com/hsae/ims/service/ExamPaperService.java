package com.hsae.ims.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.ExamPaper;
import com.hsae.ims.repository.ExamPaperRepository;

@Service
public class ExamPaperService {

	@Autowired
	private ExamPaperRepository examPaperRepository;
	
	public ExamPaper findOne(Long id){
		return examPaperRepository.findOne(id);
	}

	public List<ExamPaper> findAll() {
		return (List<ExamPaper>) examPaperRepository.findAll();
	}

	public Page<ExamPaper> findAll(int pageNumber, int pageSize, final String subject) {
		Specification<ExamPaper> spec = new Specification<ExamPaper>() {
			@Override
			public Predicate toPredicate(Root<ExamPaper> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNotBlank(subject)) {
					predicate.getExpressions().add(cb.like(root.get("subject").as(String.class), "%"+ subject +"%"));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return examPaperRepository.findAll(spec, pageRequest);
	}

	public void save(ExamPaper entity) {
		examPaperRepository.save(entity);
	}

	public void delete(Long id) {
		examPaperRepository.delete(id);
	}
}
