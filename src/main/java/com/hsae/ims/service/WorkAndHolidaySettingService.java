package com.hsae.ims.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.TimetableSetting;
import com.hsae.ims.entity.WorkAndHolidaySetting;
import com.hsae.ims.repository.TimetableSettingRepository;
import com.hsae.ims.repository.WorkAndHolidaySettingRepository;
import com.hsae.ims.utils.RightUtil;

@Service
public class WorkAndHolidaySettingService {
	
	@Autowired
	private WorkAndHolidaySettingRepository workAndHolidaySettingRepository;
	
	@Autowired
	private TimetableSettingRepository timetableSettingRepository;

	/***
	 * 获取节日和工作日设置
	 * @param date
	 * @return
	 */
	@SuppressWarnings("static-access")
	public WorkAndHolidaySetting findWorkAndHolidaySetting(Date date){
		//已经设置节假日和工作日
		List<WorkAndHolidaySetting> list = workAndHolidaySettingRepository.findByDate(date);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}else{
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			WorkAndHolidaySetting setting = new WorkAndHolidaySetting();
			setting.setDate(date);
			if (c.get(c.DAY_OF_WEEK) == Calendar.SATURDAY || c.get(c.DAY_OF_WEEK) == Calendar.SUNDAY) {
				setting.setType(0);
				return setting;
			}
			int month = c.get(Calendar.MONTH) + 1;
			TimetableSetting timetable = timetableSettingRepository.findByMonth(month);
			setting.setType(1);
			setting.setForeworktime(timetable.getForeworktime());
			setting.setForeresttime(timetable.getForeresttime());
			setting.setAfterworktime(timetable.getAfterworktime());
			setting.setAfterresttime(timetable.getAfterresttime());
			
			return setting;
		}
	}
	
	public TimetableSetting findTimetableSetting(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH) + 1;
		return timetableSettingRepository.findByMonth(month);
	}

	public void save(WorkAndHolidaySetting setting) {
		setting.setLastupdater(RightUtil.getCurrentUserId());
		setting.setLastupate(new Date());
		workAndHolidaySettingRepository.save(setting);
	}

	public void cancle(Date date) {
		workAndHolidaySettingRepository.deleteByDate(date);
	}

	public List<WorkAndHolidaySetting> findHolidayByMonth(Date start, Date end) {
		return workAndHolidaySettingRepository.findHolidayByMonth(start, end);
	}
}
