package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 培训讲师
 * @author caowei
 *
 */
@Entity
@Table(name="ims_training_teacher")
public class TrainingTeacher implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 培训讲师
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	
	/**
	 * 专业领域
	 */
	private String domain;
	
	/**
	 * 讲师级别
	 */
	public String level;
	
	/**
	 * 简介
	 */
	@Lob
	private String introduction;
	
	/**
	 * 是否被删除，0：未删除， 1：删除
	 */
	private int isdelete = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;
	}
	
	
}
