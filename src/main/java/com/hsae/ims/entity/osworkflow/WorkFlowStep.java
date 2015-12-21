package com.hsae.ims.entity.osworkflow;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
import com.hsae.ims.entity.User;
@MappedSuperclass
public class WorkFlowStep {
	private Wfentry wfentry;
	private User owner;
	private User caller;
	private Integer stepId;
	private Integer action_id;
	private Timestamp startDate;
	private Timestamp finishDate;
	private Timestamp dueDate;
	private String status;
	private String name;
	private Long id;
	private User creater;
	@Id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(optional = true, cascade = CascadeType.ALL)  
    @JoinColumn(name = "entry_id",referencedColumnName="id")  
	public Wfentry getWfentry() {
		return wfentry;
	}
	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	public User getCaller() {
		return caller;
	}
	public void setCaller(User caller) {
		this.caller = caller;
	}
	public Integer getStepId() {
		return stepId;
	}
	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}
	public Integer getAction_id() {
		return action_id;
	}
	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
	}
	@JsonSerialize(using = Date1Serializer.class)
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	@JsonSerialize(using = Date1Serializer.class)
	public Timestamp getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public User getCreater() {
		return creater;
	}
	public void setCreater(User create) {
		this.creater = create;
	}
}
