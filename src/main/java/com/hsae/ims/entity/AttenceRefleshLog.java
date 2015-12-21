package com.hsae.ims.entity;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.TimeSerializer;
@Entity
@Table(name = "ims_attence_refleshlog")
public class AttenceRefleshLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  Long id;
	
	@JsonSerialize(using = TimeSerializer.class)
	private  Timestamp beginTime;
	
	@JsonSerialize(using = TimeSerializer.class)
	private  Timestamp endTime;
	
	private  String log;
	
	private  Integer recordNum;
	
	private  Long totalTime;
	
	@Temporal(TemporalType.DATE)
	private Date attenceDate;
	
	private String state;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Timestamp getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public Integer getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	public Date getAttenceDate() {
		return attenceDate;
	}
	public void setAttenceDate(Date attenceDate) {
		this.attenceDate = attenceDate;
	}
}
