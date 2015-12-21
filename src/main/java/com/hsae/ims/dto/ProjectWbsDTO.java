package com.hsae.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class ProjectWbsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String index;

	/**
	 * 项目名称。
	 */
	private String wbsName;
	/**
	 * 责任人。
	 */
	private String pm;
	/**
	 * 项目复杂度。 A1，A2，B1，B2，C，D
	 */
	private String complex;
	/**
	 * 项目描述。
	 */
	private String description;
	/**
	 * 项目节点。
	 */
	private String phaseNode;
	/**
	 * DR阶段.
	 */
	private String phaseDR;

	/**
	 * 项目状态。
	 */
	private String state;

	/**
	 * 实际工时。
	 */
	private float actualHours;
	/**
	 * 实际开始时间。
	 */
	private Date actualStart;
	/**
	 * 实际结束时间。
	 */
	private Date actualEnd;
	/**
	 * 计划开始时间。
	 */
	private Date planStart;

	/**
	 * 计划结束时间。
	 */
	private Date planEnd;
	/**
	 * 计划工时。
	 */
	private float planHours;

	/**
	 * 延期原因
	 */
	private String delayReason;
	
	
	
	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getWbsName() {
		return wbsName;
	}

	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getComplex() {
		return complex;
	}

	public void setComplex(String complex) {
		this.complex = complex;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhaseNode() {
		return phaseNode;
	}

	public void setPhaseNode(String phaseNode) {
		this.phaseNode = phaseNode;
	}

	public Date getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(Date actualEnd) {
		this.actualEnd = actualEnd;
	}

	public float getActualHours() {
		return actualHours;
	}

	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}

	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public float getPlanHours() {
		return planHours;
	}

	public void setPlanHours(float planHours) {
		this.planHours = planHours;
	}

	public String getPhaseDR() {
		return phaseDR;
	}

	public void setPhaseDR(String phaseDR) {
		this.phaseDR = phaseDR;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
