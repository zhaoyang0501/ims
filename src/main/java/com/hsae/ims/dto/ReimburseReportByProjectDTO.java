package com.hsae.ims.dto;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
public class ReimburseReportByProjectDTO {
	String projectName;
	@JsonSerialize(using = Date1Serializer.class)
	Date  reimburseDate;
	String users;
	BigDecimal reimburseMoney;
	BigInteger userNumber;
	String reimburseType;
	BigDecimal standard;
	public BigDecimal getStandard() {
		return standard;
	}
	public void setStandard(BigDecimal standard) {
		this.standard = standard;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Date getReimburseDate() {
		return reimburseDate;
	}
	public void setReimburseDate(Date reimburseDate) {
		this.reimburseDate = reimburseDate;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public BigDecimal getReimburseMoney() {
		return reimburseMoney;
	}
	public void setReimburseMoney(BigDecimal reimburseMoney) {
		this.reimburseMoney = reimburseMoney;
	}
	public BigInteger getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(BigInteger userNumber) {
		this.userNumber = userNumber;
	}
	public String getReimburseType() {
		return reimburseType;
	}
	public void setReimburseType(String reimburseType) {
		this.reimburseType = reimburseType;
	}
	
}
