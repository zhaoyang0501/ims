package com.hsae.ims.dto;

import java.util.Date;

import com.hsae.ims.entity.User;

public class ActivitiHistoryTaskDTO {
	private String name;
	private User user;
	private Date startTime;
	private Date endTime;
	private String approves;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getApproves() {
		return approves;
	}
	public void setApproves(String approves) {
		this.approves = approves;
	}
}
