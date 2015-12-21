package com.hsae.ims.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name="ims_ot_reimbursedetails")
public class ReimburseDetails implements Serializable{
	private static final long serialVersionUID = 4009963607643626222L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**
	 * 就餐人员
	 */
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	/***
	 * 报销单主表
	 */
	@ManyToOne( fetch=FetchType.LAZY)
	@JoinColumn(name = "ReimburseId", nullable = false,  updatable = false)
	@JsonIgnore
	private Reimburse reimburse;
	@Transient
	private List<DailyReport> dailyReports;
	@Transient
	private List<AttenceOverTime> attenceOverTimes;
	
	public List<DailyReport> getDailyReports() {
		return dailyReports;
	}
	public void setDailyReports(List<DailyReport> dailyReports) {
		this.dailyReports = dailyReports;
	}
	public List<AttenceOverTime> getAttenceOverTimes() {
		return attenceOverTimes;
	}
	public void setAttenceOverTimes(List<AttenceOverTime> attenceOverTimes) {
		this.attenceOverTimes = attenceOverTimes;
	}
	public Reimburse getReimburse() {
		return reimburse;
	}
	public void setReimburse(Reimburse reimburse) {
		this.reimburse = reimburse;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
