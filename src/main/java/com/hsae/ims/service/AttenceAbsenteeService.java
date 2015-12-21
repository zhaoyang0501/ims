package com.hsae.ims.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceAbsentee;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceAbsenteeRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.DateTimeUtil;
@Service
public class AttenceAbsenteeService {
	@Autowired
	private AttenceAbsenteeRepository attenceAbsenteeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void delete(AttenceAbsentee attenceAbsentee ){
		attenceAbsenteeRepository.delete(attenceAbsentee);
	}
	public void  saveAttenceAbsentee(AttenceAbsentee attenceAbsentee){
		attenceAbsenteeRepository.save(attenceAbsentee);
	}
	
	public Boolean isAbsenteeexist(String empnumber,Date date){
		List<AttenceAbsentee> attenceAbsentees = attenceAbsenteeRepository.findByAbsenteeDateAndUser(date, userRepository.getUserByNo(empnumber).getId());
		return attenceAbsentees.size()==0?false:true;
	}
	/***
	 * 找某人某日是不是已经有漏打卡
	 * @param user
	 * @param date
	 * @return
	 */
	public AttenceAbsentee findAbsenteee(User user, Date date){
		List<AttenceAbsentee> list=attenceAbsenteeRepository.findByAbsenteeDateAndUser(date, user.getId());
		return list.size()==0?null:list.get(0);
	}
	
	public AttenceAbsentee findAbsenteee(Long id){
		return attenceAbsenteeRepository.findOne(id);
	}

	/****
	 * 获取本月漏打卡次数
	 * @param userId
	 * @param absenteeDate
	 * @return
	 * @throws ParseException 
	 */
	public Integer findAbsenteeFrequency(Long userId, Date absenteeDate) throws ParseException {
		Date firstDayOfMonth = DateUtils.parseDate(DateTimeUtil.getFirstDayOfMonth(absenteeDate), "yyyy-MM-dd");
		Date lastDayOfMonth = DateUtils.parseDate(DateTimeUtil.getLastDayOfMonth(absenteeDate), "yyyy-MM-dd");
		return attenceAbsenteeRepository.countAbsenteeFrequency(userId, firstDayOfMonth, lastDayOfMonth);
	}
	
	public void deleteByUserAndDate(Long userId, Date absenteeDate ){
		this.attenceAbsenteeRepository.deleteByUserAndDate(userId, absenteeDate);
	}
}
