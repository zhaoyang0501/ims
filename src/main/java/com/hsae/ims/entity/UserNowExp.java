package com.hsae.ims.entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;


/**
 * 
 * @author zhaozhou
 * 现在的工作经历。
 *
 */

@Entity
@Table(name="ims_user_now_exp")
public class UserNowExp {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	//开始时间。
	@Temporal(TemporalType.DATE)
	private Date nowStartTime;

	//结束时间。
	@Temporal(TemporalType.DATE)
	private Date nowEndTime;
	
	//部门。
	private String nowDept;
	
	//岗位。1...2....
	private String nowPost;
	
	//从业经历。
	@Lob
	private String nowPerience;
	
	//转岗原因。
	@Lob
	private String nowAlterReasons;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@JsonSerialize(using = Date1Serializer.class)
	public Date getNowStartTime() {
		return nowStartTime;
	}

	public void setNowStartTime(Date nowStartTime) {
		this.nowStartTime = nowStartTime;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getNowEndTime() {
		return nowEndTime;
	}

	public void setNowEndTime(Date nowEndTime) {
		this.nowEndTime = nowEndTime;
	}

	public String getNowDept() {
		return nowDept;
	}

	public void setNowDept(String nowDept) {
		this.nowDept = nowDept;
	}

	public String getNowPost() {
		return nowPost;
	}

	public void setNowPost(String nowPost) {
		this.nowPost = nowPost;
	}

	public String getNowPerience() {
		return nowPerience;
	}

	public void setNowPerience(String nowPerience) {
		this.nowPerience = nowPerience;
	}

	public String getNowAlterReasons() {
		return nowAlterReasons;
	}

	public void setNowAlterReasons(String nowAlterReasons) {
		this.nowAlterReasons = nowAlterReasons;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

}
