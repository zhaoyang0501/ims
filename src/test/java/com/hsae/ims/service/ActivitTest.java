package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.tcp.TcpConnection;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.dto.ActivitiHistoryTaskDTO;
import com.hsae.ims.task.AttenceDataBaseRefreshJob;
import com.hsae.ims.task.AttenceStatisticsJob;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivitTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("file:src//main//resources//log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	ActivitiService activitiService;
	private static final Logger log = LoggerFactory.getLogger(ActivitTest.class);
	@Test
	public void deploy() throws ParseException{
		Deployment deployment=processEngine.getRepositoryService().createDeployment().name("培训计划流程")
				.addClasspathResource("activiti/trainingplan.bpmn").addClasspathResource("activiti/trainingplan.png").deploy();
		System.out.println("fuck-->"+deployment.getId());
	}

	@Test
	public void start() throws ParseException{
		/*UserManager d;
		org.activiti.engine.impl.persistence.entity.UserEntityManagerImpl
		UserEntity   */
		
		ProcessInstance processInstance=processEngine.getRuntimeService().startProcessInstanceByKey("trainingplan");
		System.out.println("fuck-->"+processInstance.getId());
		//List<Task> tasks = processEngine.getTaskService().createTaskQuery().
	//	processEngine.getTaskService().complete(taskId);
		//System.out.println("fuck-->"+tasks.size());
	}
	@Test
	public void list(){
		/*List<Task> task=processEngine.getTaskService().createTaskQuery().processInstanceId("42501").list();
		task.get(0).getAssignee();
		processEngine.getTaskService().complete(task.get(0).getId());*/
		/*List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskCandidateUser("4").list();
		for (IdentityLink il : processEngine.getTaskService().getIdentityLinksForTask(tasks.get(0).getId())){
			System.out.print("nofuck--?"+il.getUserId());
			processEngine.getTaskService().claim(tasks.get(0).getId(), "4");
		}*/
		
		List<HistoricActivityInstance> list = processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId("57501")
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
	
		
		List<Task> tasks1 = processEngine.getTaskService().createTaskQuery().taskAssignee("79").list();
		List<Task> tasks2 = processEngine.getTaskService().createTaskQuery().taskVariableValueLike("assignee", "48").list();
		System.out.println("fuck-3->"+tasks1.size());
		
		
		List<HistoricActivityInstance> list3 = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.processInstanceId("57501") 
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		
		List<ActivitiHistoryTaskDTO> list2= activitiService.findHistoryTask("75001");
		
		System.out.println("fuck2-->"+list2.size());
		
	}
	

}
