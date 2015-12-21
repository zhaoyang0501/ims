package com.hsae.ims.dto;

import java.io.Serializable;

public class WorkflowAbsenteeApproveDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String username;
	
	private String empnumber;
	
	private String deptname;
	
	private String absenteetime;
	
	private String record;
	
	private String reason;
	
	private String frequency;
	
	private String remark;
	
	private String state;
	
	private String operate = "";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmpnumber() {
		return empnumber;
	}

	public void setEmpnumber(String empnumber) {
		this.empnumber = empnumber;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getAbsenteetime() {
		return absenteetime;
	}

	public void setAbsenteetime(String absenteetime) {
		this.absenteetime = absenteetime;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
