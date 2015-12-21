package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 离职信息表
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_user_resume_dimission")
public class UserResumeDimission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long resumeId;
	
	private Long userId;
	
	/** 离职类型 code  **/
	private String type;
	
	/** 申请日期  **/
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date applydate;

	/** 计划离职日期  **/
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date plandate;
	
	/** 实际离职日期 **/
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date actualdate;
	
	/** 是否加入黑名单 1:是 0：否 **/
	private Integer blacklist = 0;
	
	/** 离职原因 **/
	@Lob
	private String reason;
	
	@Lob
	private String remarks;
	
	private Long creater;
	
	private Date createDate;
	
	private Date lastupdateDate;
	
	@Transient
	private String chinesename;
	
	@Transient
	private String deptname;
	
	@Transient
	private String dimissionTypeName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserResumeDimission(){
		this.createDate = new Date();
	}

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getApplydate() {
		return applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

	public Date getPlandate() {
		return plandate;
	}

	public void setPlandate(Date plandate) {
		this.plandate = plandate;
	}

	public Date getActualdate() {
		return actualdate;
	}

	public void setActualdate(Date actualdate) {
		this.actualdate = actualdate;
	}

	public Integer getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Integer blacklist) {
		this.blacklist = blacklist;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getDimissionTypeName() {
		return dimissionTypeName;
	}

	public void setDimissionTypeName(String dimissionTypeName) {
		this.dimissionTypeName = dimissionTypeName;
	}

	public Date getLastupdateDate() {
		return lastupdateDate;
	}

	public void setLastupdateDate(Date lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}
	
}
