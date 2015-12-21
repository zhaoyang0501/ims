package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Rights;
import com.hsae.ims.repository.RightsRepository;

@Service
public class RightsService {
	
	@Autowired
	private RightsRepository rightsRepository;

	public List<Rights> findAll(){
		return (List<Rights>) rightsRepository.findAll();
	}

	public Rights findOne(long rightid) {
		return rightsRepository.findOne(rightid);
	}
}
