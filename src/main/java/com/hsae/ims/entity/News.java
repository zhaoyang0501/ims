package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ims_system_news")
public class News implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** 类型 **/
	@ManyToOne(fetch = FetchType.EAGER)
	private Code type;

	@Lob
	private String title;
	
	@Lob
	private String contents;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	private Integer top;
	
	private String attachment;
	
	@Transient
	private int operate = 0;
	
	@Transient
	private int newnews = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Code getType() {
		return type;
	}

	public void setType(Code type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public int getNewnews() {
		return newnews;
	}

	public void setNewnews(int newnews) {
		this.newnews = newnews;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}
	
	
}
