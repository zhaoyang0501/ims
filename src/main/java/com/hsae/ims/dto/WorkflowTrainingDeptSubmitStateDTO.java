package com.hsae.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class WorkflowTrainingDeptSubmitStateDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String dept;
	
	private Long deptid;
	
	private Integer totalno;
	
	private Integer totalneino;
	
	private Integer totalwaino;
	
	private Double totalmoney;
	
	private Double totalhours;
	
	private Date sbdate;
	
	private String deitals = "";

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Integer getTotalno() {
		return totalno;
	}

	public void setTotalno(Integer totalno) {
		this.totalno = totalno;
	}

	public Integer getTotalneino() {
		return totalneino;
	}

	public void setTotalneino(Integer totalneino) {
		this.totalneino = totalneino;
	}

	public Integer getTotalwaino() {
		return totalwaino;
	}

	public void setTotalwaino(Integer totalwaino) {
		this.totalwaino = totalwaino;
	}

	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}

	public Double getTotalhours() {
		return totalhours;
	}

	public void setTotalhours(Double totalhours) {
		this.totalhours = totalhours;
	}

	public Date getSbdate() {
		return sbdate;
	}

	public void setSbdate(Date sbdate) {
		this.sbdate = sbdate;
	}

	public String getDeitals() {
		return deitals;
	}

	public void setDeitals(String deitals) {
		this.deitals = deitals;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	
}
