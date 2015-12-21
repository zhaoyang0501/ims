package com.hsae.ims.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.entity.osworkflow.Wfentry;

@Entity
@Table(name="ims_workflow_away")
public class WorkflowAway implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	
	@Transient
	private String step;
	
	//填单人
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Temporal(TemporalType.DATE)
	private Date fillDate;
	
	//外出人员
	@ManyToMany(fetch = FetchType.EAGER)
	private List<User> awayUser;
	
	private Date awayFrom;
	
	private Date awayTo;
	
	private String awayAddress;
	
	private String awayReason;
	
	private String awayCar;
	
	private String awayDriver;
	
	private Date awayTime;
	
	private Long manager;
	
	//保安
	@ManyToOne(fetch = FetchType.EAGER)
	private User guider;
	
	private String remarks;
	
	@JsonSerialize(using = TimeSerializer.class)
	private  Timestamp saveTime;
	
	public Timestamp getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Wfentry getWfentry() {
		return wfentry;
	}

	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getFillDate() {
		return fillDate;
	}

	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}

	public List<User> getAwayUser() {
		return awayUser;
	}

	public void setAwayUser(List<User> awayUser) {
		this.awayUser = awayUser;
	}

	public String getAwayAddress() {
		return awayAddress;
	}

	public void setAwayAddress(String awayAddress) {
		this.awayAddress = awayAddress;
	}

	public String getAwayReason() {
		return awayReason;
	}

	public void setAwayReason(String awayReason) {
		this.awayReason = awayReason;
	}

	public String getAwayCar() {
		return awayCar;
	}

	public void setAwayCar(String awayCar) {
		this.awayCar = awayCar;
	}

	public String getAwayDriver() {
		return awayDriver;
	}

	public void setAwayDriver(String awayDriver) {
		this.awayDriver = awayDriver;
	}

	@JsonSerialize(using = TimeSerializer.class)
	public Date getAwayTime() {
		return awayTime;
	}

	public void setAwayTime(Date awayTime) {
		this.awayTime = awayTime;
	}

	public User getGuider() {
		return guider;
	}

	public void setGuider(User guider) {
		this.guider = guider;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@JsonSerialize(using = TimeSerializer.class)
	public Date getAwayFrom() {
		return awayFrom;
	}

	public void setAwayFrom(Date awayFrom) {
		this.awayFrom = awayFrom;
	}

	@JsonSerialize(using = TimeSerializer.class)
	public Date getAwayTo() {
		return awayTo;
	}

	public void setAwayTo(Date awayTo) {
		this.awayTo = awayTo;
	}

	public Long getManager() {
		return manager;
	}

	public void setManager(Long manager) {
		this.manager = manager;
	}
	
}
