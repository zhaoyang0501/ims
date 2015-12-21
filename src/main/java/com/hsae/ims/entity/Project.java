package com.hsae.ims.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ims_system_project")
public class Project implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目编码
	 */
	private String projectCode;
	/**
	 * 项目复杂度
	 * A1，A2，B1，B2，C，D
	 */
	private String complex;
	/**
	 * 客户名称
	 */
	private String customer;
	/**
	 * 项目经理
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private User pm;
	/**
	 * 项目描述
	 */
	private String description;
	
	/**
	 * 项目状态
	 * 0：创建
	 * 1：进行中
	 * 2：结束
	 * 3：关闭
	 */
	private String state;
	
	/**
	 * 是否删除
	 * 0：未删除
	 * 1：已删除
	 */
	private String isDelete;
	
	/**
	 * 创建日期
	 */
	private Timestamp createDate;
	/**
	 * 创建人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private User createUser;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getComplex() {
		return complex;
	}
	public void setComplex(String complex) {
		this.complex = complex;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public User getPm() {
		return pm;
	}
	public void setPm(User pm) {
		this.pm = pm;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public User getCreateUser() {
		return createUser;
	}
	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	public String getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
