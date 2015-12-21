package com.hsae.ims.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceTravel;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceTravelRepository;
@Service
public class AttenceTravelService {
	@Autowired
	private AttenceTravelRepository attenceTravelRepository;
	public void save(AttenceTravel  attenceTravel){
		attenceTravelRepository.save(attenceTravel);
	}
	public AttenceTravel findAttenceTravel(User user, Date date){
		List<AttenceTravel> list=attenceTravelRepository.findByUserAndDate(user.getId(), date);
		return list.size()==0?null:list.get(0);
	}
	public AttenceTravel getAttenceTravel(Long id){
		return attenceTravelRepository.findOne(id);
	}
	
	public void deleteByUserAndDate(Long userId, Date date ){
		this.attenceTravelRepository.deleteByUserAndDate(userId, date);
	}
}
