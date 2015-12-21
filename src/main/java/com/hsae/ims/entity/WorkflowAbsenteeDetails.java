package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ims_workflow_absentee_details")
public class WorkflowAbsenteeDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long user;
	
	/**漏打卡日期*/
	@Temporal(TemporalType.DATE)
	private Date absenteeDate;
	
	/**漏打卡时间 */
	private String absenteeTime;
	
	/**漏打卡原因*/
	private String reason;
	
	private String state;
	
	/**备注*/
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Date getAbsenteeDate() {
		return absenteeDate;
	}

	public void setAbsenteeDate(Date absenteeDate) {
		this.absenteeDate = absenteeDate;
	}

	public String getAbsenteeTime() {
		return absenteeTime;
	}

	public void setAbsenteeTime(String absenteeTime) {
		this.absenteeTime = absenteeTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
