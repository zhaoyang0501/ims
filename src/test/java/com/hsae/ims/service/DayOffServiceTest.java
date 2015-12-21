package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.entity.ReimburseDetails;
import com.hsae.ims.repository.ReimburseDetailsRepository;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class DayOffServiceTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("classpath:src/test/resources/log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired  
	private AttenceDayOffService AttenceDayOffService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReimburseDetailsRepository reimburseDetailsRepository;
	@Test
	public void findAttenceDayoff() throws ParseException{
		
		 Date date = new Date();
	        int k = 1;
	         
	        // JDK 1.5 or higher
	        String number = String.format("%tY%<tm%<td%03d", date, k);
	        System.out.println(number);
		//AttenceDayOffService.findAttenceDayoff(userService.findOne(1406054),DateUtils.parseDate("2015-03-04", "yyyy-MM-dd"));
	}
	@Test
	public void testq() throws ParseException{
		List<ReimburseDetails> list=reimburseDetailsRepository.findByUserAndReimburseReimburseDate(userService.findOne(1406054), DateUtils.parseDate("2015-02-27", "yyyy-MM-dd"));
		for(ReimburseDetails reimburseDetails:list ){
			System.out.println(reimburseDetails.getReimburse().getWfentry().getId());
		}
	}
	@Test
	public void test2() throws ParseException{
		DateTime a=new DateTime(DateUtils.parseDate("2015-01-01", "yyyy-MM-dd"));
		for(int i=0;i<3000;i++){
			a=a.plusDays(1);
			
			if(a.getDayOfWeek()==DateTimeConstants.SUNDAY||a.getDayOfWeek()==DateTimeConstants.SATURDAY)
			System.out.println("INSERT INTO ims_system_workandholiday_setting(afterresttime, afterworktime, date, foreresttime, foreworktime, lastupate, lastupdater, remark, type) VALUES"+
					"( '18:00', '14:00', '"+a.toString("yyyy-MM-dd")+"', '12:30', '8:30', now(), '', '', 0);");
			else
				System.out.println("INSERT INTO ims_system_workandholiday_setting(afterresttime, afterworktime, date, foreresttime, foreworktime, lastupate, lastupdater, remark, type) VALUES"+
						"( '18:00', '14:00', '"+a.toString("yyyy-MM-dd")+"', '12:30', '8:30', now(), '', '', 1);");
		}
	}
	
}
