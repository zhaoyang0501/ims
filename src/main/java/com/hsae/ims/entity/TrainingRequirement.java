package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ims_training_requirement")
public class TrainingRequirement  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 年
	 */
	private int year;
	
	/**
	 * 工作流ID
	 */
	private Long osWorkflow;
	
	private String step;
	
	/**
	 * 流程状态
	 * 0：新建
	 * 1：流程审批中
	 * 2：审批完成
	 */
	private int state = 0;
	
	/**
	 * 审批意见
	 */
	@Lob
	private String approvals;
	
	/**
	 * 创建人
	 */
	@ManyToOne
	private User creater;
	
	/**
	 * 创建日期
	 */
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Lob
	private String remarks;
	
	@Transient
	private int operate = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Long getOsWorkflow() {
		return osWorkflow;
	}

	public void setOsWorkflow(Long osWorkflow) {
		this.osWorkflow = osWorkflow;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getApprovals() {
		return approvals;
	}

	public void setApprovals(String approvals) {
		this.approvals = approvals;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
