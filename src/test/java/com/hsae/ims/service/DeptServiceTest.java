package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.entity.Deptment;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.message.AttenceMessage;
import com.hsae.ims.entity.message.WorkFlowMessage;
import com.hsae.ims.repository.AttenceBrushRdcordRepository;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class DeptServiceTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("classpath:src/test/resources/log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private DeptService deptService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private AttenceBrushRdcordRepository attenceBrushRdcordRepository;
	@Test
	public void getAllDeptment(){
		 List<Deptment> list=deptService.getAllDeptment();
		 Assert.notEmpty(list);
	}
	@Test
	public void findUserByAuthority(){
		 List<User> list=userService.findUserByAuthority("105654");
		 System.out.println(list.size());
		 Assert.notEmpty(list);
	}
	@Test
	public void fuckyou(){
		attenceBrushRdcordRepository.findUserAttenceCount( 
				new PageRequest(1, 23,  new Sort(Direction.ASC, "state")));
	}
	
	@Test
	public void saveMessage(){
		
		//this.messageService.save(new  AttenceMessage("333", 32L));
	//this.messageService.save(new  WorkFlowMessage("3333", "333"));
	//	this.messageService.findAll();
	//	this.messageService.findUnReaded(new User(32L));
	}
	
}
