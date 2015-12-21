package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.entity.DailyReport;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class DailyRepoertServiceTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("classpath:src/test/resources/log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private DailyReportService dailyReportService;
	@Autowired
	private UserService userService;
	
	@Test
	public void getDailyReportByMonth() throws ParseException{
		List<DailyReport> list=dailyReportService.findDailyReportByMonth(DateUtils.parseDate("2011-01-14", "yyyy-MM-dd") ,DateUtils.parseDate("2019-01-01", "yyyy-MM-dd"), 1);
		Assert.notEmpty(list);
	}
	@Test
	public void findByReportDateAndUser() throws ParseException{
		List<DailyReport> list=dailyReportService.findByReportDateAndUser(DateUtils.parseDate("2014-11-14", "yyyy-MM-dd")  ,userService.findOne(1));
		Assert.notEmpty(list);
	}
	@Test
	public void findDailyReportPage(){
		
		Page<DailyReport> page=dailyReportService.findDailyReport(null, null, null, 1l, null,null, 1, 100);
		Assert.notEmpty(page.getContent());
	}
	@Test
	public void findOverTimeDailyReport() throws ParseException{
//		Page<DailyReport> page=dailyReportService.findOverTimeDailyReport(DateUtils.parseDate("2014-11-14", "yyyy-MM-dd"), 
//				DateUtils.parseDate("2015-11-14", "yyyy-MM-dd"), null,"1", 1, 100);
//		Assert.notEmpty(page.getContent());
	}
}
