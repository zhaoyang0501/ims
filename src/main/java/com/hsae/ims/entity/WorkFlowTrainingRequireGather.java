package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hsae.ims.entity.osworkflow.Wfentry;

@Entity
@Table(name = "ims_workflow_training_require_gather")
public class WorkFlowTrainingRequireGather  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 年
	 */
	private int year;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	
	@Transient
	private String step;
	
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

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
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

	public Wfentry getWfentry() {
		return wfentry;
	}

	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	
}
