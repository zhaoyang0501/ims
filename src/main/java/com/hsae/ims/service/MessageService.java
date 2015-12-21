package com.hsae.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.User;
import com.hsae.ims.entity.message.Message;
import com.hsae.ims.repository.MessageRepository;
@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;
	
	public void save(Message message){
		this.messageRepository.save(message);
	}
	public List<Message> findAll(){
		 return (List<Message>) this.messageRepository.findAll();
	}
	/***
	 * 查默认未读消息
	 * @param user
	 * @return
	 */
	public List<Message> findUnRead( User user){
		 return (List<Message>) this.messageRepository.findByUserAndState(user,"1");
	}
	/***
	 * 查默认已读消息
	 * @param user
	 * @return
	 */
	public List<Message> findReaded( User user){
		 return (List<Message>) this.messageRepository.findByUserAndState(user,"2");
	}
	/***TODO
	 * 标未已读
	 * @param id
	 */
	public void markReaded(Long id){
		
	}
}
