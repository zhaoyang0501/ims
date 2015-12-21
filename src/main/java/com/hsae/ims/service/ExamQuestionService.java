package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.controller.response.FailedResponse;
import com.hsae.ims.controller.response.Response;
import com.hsae.ims.controller.response.SuccessResponse;
import com.hsae.ims.entity.ExamQuestion;
import com.hsae.ims.repository.ExamQuestionRepository;

@Service
public class ExamQuestionService {
	
	@Autowired
	private ExamQuestionRepository examQuestionRepository;

	public Response save(ExamQuestion question) {
		ExamQuestion entity = examQuestionRepository.save(question);
		if(entity != null && entity.getId() > 0){
			return new SuccessResponse("保存成功");
		}else{
			return new FailedResponse();
		}
	}

	public List<ExamQuestion> findAll(Long id) {
		return (List<ExamQuestion>) examQuestionRepository.findByPaperId(id);
	}

	/** 根据paper查找所有试题 **/
	public List<ExamQuestion> findByPaper(Long i) {
		return examQuestionRepository.findByPaperId(i);
	}

	public Response delete(Long id) {
		examQuestionRepository.delete(id);
		return new SuccessResponse("操作成功");
	}

	public ExamQuestion findOne(Long questionId) {
		return examQuestionRepository.findOne(questionId);
	}


}
