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

import com.hsae.ims.entity.ExamAnswer;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.ExamAnswerRepository;
import com.hsae.ims.repository.ExamPaperRepository;
import com.hsae.ims.repository.UserRepository;

@Service
public class ExamAnswerService {

	@Autowired
	private ExamAnswerRepository examAnswerRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ExamPaperRepository examPaperRepository;
	
	public ExamAnswer save(ExamAnswer answer){
		return examAnswerRepository.save(answer);
	}

	public ExamAnswer findOne(Long answerId) {
		return examAnswerRepository.findOne(answerId);
	}

	public Page<ExamAnswer> findAll(final Long userId, final Long paperId, final Integer state, int pageNumber, int pageSize) {
		Specification<ExamAnswer> spec = new Specification<ExamAnswer>() {
			@Override
			public Predicate toPredicate(Root<ExamAnswer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (userId != null && userId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("user").as(User.class), userRepository.findOne(userId)));
				}
				if (paperId != null && paperId > 0) {
					predicate.getExpressions().add(cb.equal(root.get("paper").as(User.class), examPaperRepository.findOne(paperId)));
				}
				if (state != null && state > 0) {
					predicate.getExpressions().add(cb.equal(root.get("ifgovoer").as(Integer.class), state));
				}
				return predicate;
			}
		};
		PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize,  new Sort(Direction.DESC, "id"));
		return examAnswerRepository.findAll(spec, pageRequest);
	}
}
