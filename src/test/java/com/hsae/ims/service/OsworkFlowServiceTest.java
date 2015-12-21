package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.entity.Reimburse;
import com.hsae.ims.entity.osworkflow.CurrentStep;
import com.hsae.ims.entity.osworkflow.HistoryStep;
import com.hsae.ims.repository.WorkFlowWfentryRepository;
import com.opensymphony.workflow.WorkflowException;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class OsworkFlowServiceTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("classpath:src/test/resources/log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private WorkFlowService workFlowService;
	@Autowired
	private UserService userService;
	@Autowired
	private WorkFlowWfentryRepository workFlowWfentryRepository;
	@Autowired
	private ReimburseService reimburseService;
	@Autowired
	WorkFlowIdGeneratorService workFlowIdGeneratorService;
	
	@Test
	public void findReimburseReportByUser() throws ParseException{
		Date date=DateUtils.parseDate("2014-04", "yyyy-MM");
		DateTime dateTime =new DateTime(date);
		reimburseService.findReimburseReportByUser(dateTime.getYear(),dateTime.getMonthOfYear());
	}
	@Test
	public void getReimburseRepositoryWorkFlowId() throws ParseException{
		System.out.print(  DateFormatUtils.format(DateUtils.truncate(new Date(),  Calendar.MONTH), "yyyy-mm-dd")) ;
		String str=workFlowIdGeneratorService.getReimburseRepositoryWorkFlowId();
		System.out.println(str);
	}
	
	@Test
	public void testsaveR() {
		workFlowService.save(null);
	}
	
}
