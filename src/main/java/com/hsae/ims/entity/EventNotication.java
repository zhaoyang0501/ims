package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 系统事件通知
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_system_event_notication")
public class EventNotication implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 通知内容
	 */
	private String contents;
	
	/**
	 * 接收方
	 * USER.ID
	 */
	private Long receiver;
	/**
	 * 通知状态
	 * 1：已读
	 * 0：未读
	 */
	private String state;
	
	/**
	 * 消息源
	 */
	private String resource;
	
	/**
	 * 消息发送方
	 * 0：系统发送
	 * 其他：USER.ID
	 */
	private Long sender = 0l;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Long getSender() {
		return sender;
	}

	public void setSender(Long sender) {
		this.sender = sender;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getReceiver() {
		return receiver;
	}

	public void setReceiver(Long receiver) {
		this.receiver = receiver;
	}
	
}
