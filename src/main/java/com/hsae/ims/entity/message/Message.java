package com.hsae.ims.entity.message;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.entity.User;
@Entity
@Table(name = "ims_system_message")
public  class Message {
	protected Long id;
	protected String title;
	protected String context;
	protected String link;
	@JsonSerialize(using = TimeSerializer.class)
	protected Date createDate;
	protected String type;
	/***
	 * 1:未读取 默认
	 * 2：已读取
	 */
	protected String state="1";
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	protected User user;
	public Message(){
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContext(String context) {
		this.context = context;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContext() {
		return context;
	}

	public String getLink() {
		return link;
	}

	public Date getCreateDate() {
		return createDate;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	public User getUser() {
		return user;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
