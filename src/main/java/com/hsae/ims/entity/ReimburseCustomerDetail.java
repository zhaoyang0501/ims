package com.hsae.ims.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="ims_ot_reimbursecustomerdetail")
public class ReimburseCustomerDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String userName;
	private String company;
	
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name = "ReimburseId", nullable = false,  updatable = false)
	@JsonIgnore
	private Reimburse reimburse;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = true,  updatable = false)
	@JsonIgnore
	private Project project;
	public Reimburse getReimburse() {
		return reimburse;
	}
	public void setReimburse(Reimburse reimburse) {
		this.reimburse = reimburse;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
}
