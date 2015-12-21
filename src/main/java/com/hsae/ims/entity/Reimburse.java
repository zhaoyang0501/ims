package com.hsae.ims.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.entity.osworkflow.Wfentry;
@Entity
@Table(name = "ims_ot_reimburse")
public class Reimburse implements Serializable {
	/**
	 * 加班报销表
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	private User reimburser;
	/**
	 * 报销日期
	 */
	private Date reimburseDate;
	/**
	 * 餐费类别 1、午餐 2、晚餐
	 */
	private Integer type;
	/**
	 * 用餐人数
	 */
	@Column(nullable = true)    
	private Integer number;
	/**
	 * 餐费标准
	 */
	private Double standard = 12d;
	/**
	 * 报销金额
	 */
	private Double reimburseMoney;
	/**
	 * 状态 0：未报销 1：已报销  4已经结束
	 */
	private int state;
	/**
	 * 实际报销金额
	 */
	private Double actualMoney;
	/**
	 * 付款日期
	 */
	private Date payMoneyDate;
	@Transient
	private List<ReimburseDetails> details;
	@Transient
	private List<ReimburseCustomerDetail> reimburseCustomerDetails;

	/**
	 * 报销备注
	 */
	@Lob
	private String remark;
	/**
	 * 付款备注
	 */
	@Lob
	private String payRemark;
	/**工作流Id*/
	@OneToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	
	private String step;
	
	
	private int operate;
	private Timestamp createTime;
	@JsonSerialize(using = TimeSerializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	public List<ReimburseDetails> getDetails() {
		return details;
	}

	public void setDetails(List<ReimburseDetails> details) {
		this.details = details;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getReimburseDate() {
		return reimburseDate;
	}

	public void setReimburseDate(Date reimburseDate) {
		this.reimburseDate = reimburseDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getStandard() {
		return standard;
	}

	public void setStandard(Double standard) {
		this.standard = standard;
	}

	public List<ReimburseDetails> getOtrDetails() {
		return details;
	}

	public void setOtrDetails(List<ReimburseDetails> details) {
		this.details = details;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getPayMoneyDate() {
		return payMoneyDate;
	}

	public void setPayMoneyDate(Date payMoneyDate) {
		this.payMoneyDate = payMoneyDate;
	}

	public User getReimburser() {
		return reimburser;
	}

	public void setReimburser(User reimburser) {
		this.reimburser = reimburser;
	}

	public Double getReimburseMoney() {
		return reimburseMoney;
	}

	public void setReimburseMoney(Double reimburseMoney) {
		this.reimburseMoney = reimburseMoney;
	}

	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public Wfentry getWfentry() {
		return wfentry;
	}

	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	public List<ReimburseCustomerDetail> getReimburseCustomerDetails() {
		return reimburseCustomerDetails;
	}

	public void setReimburseCustomerDetails(
			List<ReimburseCustomerDetail> reimburseCustomerDetails) {
		this.reimburseCustomerDetails = reimburseCustomerDetails;
	}
}
