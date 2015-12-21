package com.hsae.ims.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.AttenceOverTime;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.AttenceOverTimeRepository;
import com.hsae.ims.repository.DailyReportRepository;
@Service
public class AttenceOverTimeService {
	@Autowired
	private AttenceOverTimeRepository attenceOverTimeRepository;
	@Autowired
	private DailyReportRepository dailyReportRepository;
	
	public void save(AttenceOverTime attenceOverTime){
		attenceOverTimeRepository.save(attenceOverTime);
	}
	
	public void save(AttenceOverTime attenceOverTime,Long dailyReportId){
		attenceOverTime.setDailyReport(dailyReportRepository.findOne(dailyReportId));
		attenceOverTimeRepository.save(attenceOverTime);
	}
	
	public	List<AttenceOverTime>  findByDailyReportId(Long dailyReportId){
		return attenceOverTimeRepository.findByDailyReportId(dailyReportId);
	}
	public AttenceOverTime findById(Long id){
		return attenceOverTimeRepository.findOne(id);
	}
	
	public List<AttenceOverTime> findByUserAndOvertimeDate(User user,Date overtimeDate){
		return attenceOverTimeRepository.findByUserAndOvertimeDate(user, overtimeDate);
	}
	
	
	public Integer getRank(String yyyyMM,Double spendHour) throws ParseException{
		Date startDate=DateUtils.parseDate(yyyyMM, "yyyy-MM");
		Date endDate= DateUtils.addMonths(startDate, 1);
		Integer countAll=attenceOverTimeRepository.findOverTimeUserCount(startDate, endDate);
		Integer countMy=attenceOverTimeRepository.findOverTimeUserCount(startDate, endDate,spendHour);
		if(countAll==null||countAll==0)
			return 0;
		else
			return (countMy*100)/countAll;
	}
}
