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


@Entity
@Table(name = "ims_system_project_wbs")
public class ProjectWbs implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String wbsName;
	
	/**
	 * wbs责任人
	 */
	@ManyToOne
	private User user;
	
	/**
	 * 阶段节点
	 */
	private String phaseNode;
	
	private String complex;
	
	/**
	 * DR阶段
	 */
	private String phaseDR;
	
	@Temporal(TemporalType.DATE)
	private Date planStart;
	
	@Temporal(TemporalType.DATE)
	private Date planEnd;
	
	@Temporal(TemporalType.DATE)
	private Date actualStart;
	
	@Temporal(TemporalType.DATE)
	private Date actualEnd;
	
	/**
	 * 计划工时
	 */
	private float planHours;
	
	/**
	 * 实际工时
	 */
	private float actualHours;
	
	@Lob
	private String delayReason;
	
	@Lob
	private String description;
	
	/**
	 * wbs状态:
	 */
	private String state;
	
	/**
	 * 同步编号
	 */
	private long synId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getWbsName() {
		return wbsName;
	}

	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPhaseNode() {
		return phaseNode;
	}

	public void setPhaseNode(String phaseNode) {
		this.phaseNode = phaseNode;
	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getPhaseDR() {
		return phaseDR;
	}

	public void setPhaseDR(String phaseDR) {
		this.phaseDR = phaseDR;
	}

	public Date getPlanStart() {
		return planStart;
	}

	public void setPlanStart(Date planStart) {
		this.planStart = planStart;
	}

	public Date getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(Date planEnd) {
		this.planEnd = planEnd;
	}

	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	public Date getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(Date actualEnd) {
		this.actualEnd = actualEnd;
	}

	public float getPlanHours() {
		return planHours;
	}

	public void setPlanHours(float planHours) {
		this.planHours = planHours;
	}

	public float getActualHours() {
		return actualHours;
	}

	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
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

	public long getSynId() {
		return synId;
	}

	public void setSynId(long synId) {
		this.synId = synId;
	}
	
	
}
