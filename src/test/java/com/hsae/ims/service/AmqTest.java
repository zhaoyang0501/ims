package com.hsae.ims.service;

import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import com.hsae.ims.entity.Code;
import com.hsae.ims.task.AttenceDataBaseRefreshJob;
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class AmqTest {
	  static {  
	        try { 
	            Log4jConfigurer.initLogging("file:src//main//resources//log4j.xml");  
	        } catch (FileNotFoundException ex) {  
	            System.err.println("Cannot Initialize log4j");  
	        }  
	    }  
	@Autowired
	private AttenceDataBaseRefreshJob attenceDataBaseRefreshJob;
	
	private static final Logger log = LoggerFactory.getLogger(AmqTest.class);
	
	@Autowired
	private Destination destination;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Test
	public void send() throws ParseException{
		
		jmsTemplate.setDefaultDestination(new ActiveMQQueue("1406054"));
		jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	                return session.createTextMessage("消息A");

	            }
	        });
		jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
            	 return session.createTextMessage("消息B");

            }
        });
        jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
	            public Message createMessage(Session session) throws JMSException {
	            	 return session.createTextMessage("消息C");
	            }
	        });
        jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
            	 return session.createTextMessage("消息D");
            }
        });
        jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
            	 return session.createTextMessage("消息E");
            }
        });
        jmsTemplate.send(new ActiveMQQueue("1406054"), new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
            	 return session.createTextMessage("消息F");
            }
        });
	        System.out.println("成功发送了一条JMS消息");

	    }
	

	@Test
	public void recieved(){
		
	  /*  while (true) {
	        try {
	            ObjectMessage txtmsg = (ObjectMessage) jmsTemplate
	                    .receive(new org.apache.activemq.command.ActiveMQQueue("1406054")));
	            if (null != txtmsg) {
	                System.out.println("[DB Proxy] " + txtmsg);
	                System.out.println("[DB Proxy] 收到消息内容为: "
	                        + txtmsg.getObject().getClass());
	            } else
	                break;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	    }*/
}
	
	@Test
	public void testgetMsg() throws JMSException{
		
		TextMessage objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
        	System.out.println("收到消息内容为: "+ objectMessage.getText());
        	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
        	System.out.println("收到消息内容为: "+ objectMessage.getText());
        	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
         	 objectMessage = (TextMessage) jmsTemplate.receive(new ActiveMQQueue("1406054"));
         	System.out.println("收到消息内容为: "+ objectMessage.getText());
      
	}
	
	
}
