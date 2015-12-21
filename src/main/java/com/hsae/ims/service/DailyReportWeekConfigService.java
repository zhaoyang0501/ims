package com.hsae.ims.service;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hsae.ims.entity.DailyReportWeekConfig;
import com.hsae.ims.repository.DailyReportWeekConfigRepository;
	@Service
public class DailyReportWeekConfigService {

	@Autowired
	DailyReportWeekConfigRepository dailyReportWeekConfigRepository;
	public DailyReportWeekConfig findWeekConfigByDate(Date date){
		return dailyReportWeekConfigRepository.findWeekConfigByDate(date);
	}
	public DailyReportWeekConfig findWeekConfigById(Long id){
		return dailyReportWeekConfigRepository.findOne(id);
	}
	public Iterable<DailyReportWeekConfig>  findAll(){
		return dailyReportWeekConfigRepository.findAll();
	}
}
