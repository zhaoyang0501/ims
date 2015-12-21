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
 *  之前的工作经历。
 *
 */

@Entity
@Table(name="ims_user_before_exp")
public class UserBeforeExp {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	//开始时间。
	@Temporal(TemporalType.DATE)
	private Date beforeStartTime;

	//结束时间。
	@Temporal(TemporalType.DATE)
	private Date beforeEndTime;
	
	//原公司名称。
	private String beforeCompany;
	
	//原部门。
	private String beforeDept;
	
	//原职位。
	private String beforePosition;
	
	//原从业经历。
	@Lob
	private String beforePerience;
	
	//原离职原因。
	@Lob
	private String beforeLeavingReasons;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getBeforeStartTime() {
		return beforeStartTime;
	}

	public void setBeforeStartTime(Date beforeStartTime) {
		this.beforeStartTime = beforeStartTime;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getBeforeEndTime() {
		return beforeEndTime;
	}

	public void setBeforeEndTime(Date beforeEndTime) {
		this.beforeEndTime = beforeEndTime;
	}

	public String getBeforeCompany() {
		return beforeCompany;
	}

	public void setBeforeCompany(String beforeCompany) {
		this.beforeCompany = beforeCompany;
	}

	public String getBeforeDept() {
		return beforeDept;
	}

	public void setBeforeDept(String beforeDept) {
		this.beforeDept = beforeDept;
	}

	public String getBeforePosition() {
		return beforePosition;
	}

	public void setBeforePosition(String beforePosition) {
		this.beforePosition = beforePosition;
	}

	public String getBeforePerience() {
		return beforePerience;
	}

	public void setBeforePerience(String beforePerience) {
		this.beforePerience = beforePerience;
	}

	public String getBeforeLeavingReasons() {
		return beforeLeavingReasons;
	}

	public void setBeforeLeavingReasons(String beforeLeavingReasons) {
		this.beforeLeavingReasons = beforeLeavingReasons;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}
	
}
