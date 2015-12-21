package com.hsae.ims.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceDayoff;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceDayoffRepository;
@Service
public class AttenceDayOffService {
	@Autowired
	private AttenceDayoffRepository attenceDayoffRepository;
	
	public void save(AttenceDayoff  attenceDayoff){
		attenceDayoffRepository.save(attenceDayoff);
	}
	/***
	 * 找某人某日是不是已经有加班记录
	 * @param user
	 * @param date
	 * @return
	 */
	public List<AttenceDayoff> findAttenceDayoff(User user, Date date){
		return attenceDayoffRepository.findByUserAndDate(user.getId(), date);
	}
	
	public AttenceDayoff getAttenceDayoff(Long id){
		return attenceDayoffRepository.findOne(id);
	}
	/***
	 * 删除默认某天的记录
	 * @param userId
	 * @param date
	 */
	public void deleteByUserAndDate(Long userId, Date date ){
		this.attenceDayoffRepository.deleteByUserAndDate(userId, date);
	}
}
