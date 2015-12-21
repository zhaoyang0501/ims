package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.task.AttenceDataBaseRefreshJob;
import com.hsae.ims.task.AttenceStatisticsJob;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringJobServiceTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("file:src//main//resources//log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private AttenceDataBaseRefreshJob attenceDataBaseRefreshJob;
	@Autowired
	private  AttenceRefleshLogService attenceRefleshLogService;
	@Autowired
	private WorkFlowIdGeneratorService workFlowIdGeneratorService;
	@Autowired
	private AttenceStatisticsJob attenceStatisticsJob;
	private static final Logger log = LoggerFactory.getLogger(SpringJobServiceTest.class);
	@Test
	public void execute() throws ParseException{
		;
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-10-8", "yyyy-MM-dd")));
		/*attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-11", "yyyy-MM-dd")));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-10", "yyyy-MM-dd")));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-9", "yyyy-MM-dd")));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-17", "yyyy-MM-dd")));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-16", "yyyy-MM-dd")));
		attenceRefleshLogService.saveAttenceRefleshLog(attenceDataBaseRefreshJob.reflashAttenceData(DateUtils.parseDate("2015-08-15", "yyyy-MM-dd")));
*/	}
	@Test
	public void execute1() throws ParseException{
		System.out.println("fuck"+workFlowIdGeneratorService.getDayOffWorkFlowId());
	}
	@Test
	public void exe2() throws ParseException{
		attenceStatisticsJob.execute();
	}
	
}
