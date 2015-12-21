package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Position;
import com.hsae.ims.repository.PositionRepository;

@Service
public class PositionService {

	@Autowired
	private PositionRepository positionRepository;
	
	public List<Position> findAll(){
		return (List<Position>) positionRepository.findAll();
	}
}
