package com.hsae.ims.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.hsae.ims.entity.ExamAnswerDetails;
import com.hsae.ims.repository.ExamAnswerDetailsRepository;

import org.springframework.stereotype.Service;

@Service
public class ExamAnswerDetailsService {

	@Autowired
	private ExamAnswerDetailsRepository examAnswerDetailsRepository;

	public void save(ExamAnswerDetails details) {
		examAnswerDetailsRepository.save(details);	
	}

	public ExamAnswerDetails findByQuestionId(Long id) {
		return examAnswerDetailsRepository.findByQuestionId(id);
	}
	
}
