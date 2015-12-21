package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Transient;

/***
 * 合同
 * 
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_user_contract")
public class UserContract implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long resumeId;
	
	private Long userId;

	/** 合同编号 **/
	private String contractNo;

	/** 合同类型 **/
	private String contractType;

	/** 合同签订日期 **/
	@Temporal(TemporalType.DATE)
	private Date signDate;

	/** 合同生效日期 **/
	@Temporal(TemporalType.DATE)
	private Date fromDate;

	/** 合同终止日期 **/
	@Temporal(TemporalType.DATE)
	private Date endDate;

	/** 是否含试用期 **/
	private Integer isProbation;

	/** 合同是否已解除 **/
	private Integer isRelieve;

	/** 合同是否续签 **/
	private Integer isRenewal;

	@Lob
	private String remarks;

	private Long creater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	
	@Transient
	private String operate;
	@Transient
	private String chinesename;
	@Transient
	private String deptname;
	@Transient
	private String contractTypeName;
	
	public UserContract(){
		this.createDate = new Date();
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getResumeId() {
		return resumeId;
	}

	public void setResumeId(Long resumeId) {
		this.resumeId = resumeId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getIsProbation() {
		return isProbation;
	}

	public void setIsProbation(Integer isProbation) {
		this.isProbation = isProbation;
	}

	public Integer getIsRelieve() {
		return isRelieve;
	}

	public void setIsRelieve(Integer isRelieve) {
		this.isRelieve = isRelieve;
	}

	public Integer getIsRenewal() {
		return isRenewal;
	}

	public void setIsRenewal(Integer isRenewal) {
		this.isRenewal = isRenewal;
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

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
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

	public String getContractTypeName() {
		return contractTypeName;
	}

	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}


	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}


	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

}
