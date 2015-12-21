package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Title;
import com.hsae.ims.repository.TitleRepository;

@Service
public class TitleService {
	
	@Autowired
	private TitleRepository titleRepository;
	
	public List<Title> findAll(){
		return (List<Title>) titleRepository.findAll();
	}
}
