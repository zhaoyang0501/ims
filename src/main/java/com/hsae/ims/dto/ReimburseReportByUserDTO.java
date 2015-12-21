package com.hsae.ims.dto;

import com.hsae.ims.entity.User;

public class ReimburseReportByUserDTO {
	public User user;
	public Double reimburseMoney;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getReimburseMoney() {
		return reimburseMoney;
	}
	public void setReimburseMoney(Double reimburseMoney) {
		this.reimburseMoney = reimburseMoney;
	}
	
}
