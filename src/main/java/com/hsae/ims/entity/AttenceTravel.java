package com.hsae.ims.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.TimeSerializer;
/**
 * 出差表
 * @author pzy
 *
 */
@Entity
@Table(name = "ims_attence_travel")
public class AttenceTravel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**出差人*/
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	/**出差日期*/
	private Date travelDate;
	
	/**开始时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private Timestamp startTime;
	
	/**结束时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private Timestamp endTime;
	
	/**地址*/
	private String address;
	
	/**出差原因*/
	private String reason;
	
	/**保存时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private Timestamp saveTime;
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

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Timestamp getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}

	
	
}
