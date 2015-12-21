package com.hsae.ims.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.EventNotication;
import com.hsae.ims.repository.EventNoticationRepository;

@Service
public class EventNoticationService {

	@Autowired
	private EventNoticationRepository eventNoticationRepository;
	
	/**
	 * 获取用户的系统消息
	 * @param userId
	 * @return
	 */
	public List<EventNotication> findUser4UnreadNotices(Long userId){
		
		return eventNoticationRepository.findUser4UnreadNotices(userId);
	}
	
	public boolean save(EventNotication entity){
		try {
			EventNotication temp = eventNoticationRepository.save(entity);
			if (temp != null && temp.getId() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 创建系统消息
	 * @param contents	消息内容
	 * @param receiver	消息接收人
	 * @param resource	消息源
	 * @param sender	消息发送人
	 */
	public boolean createEventNotice(String contents, Long receiver, String resource, Long sender){
		EventNotication entity = new EventNotication();
		entity.setContents(contents);
		entity.setCreateDate(new Date());
		entity.setReceiver(receiver);
		entity.setResource(resource);
		entity.setSender(sender);
		entity.setState("0");
		try {
			EventNotication temp = eventNoticationRepository.save(entity);
			if (temp != null && temp.getId() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
