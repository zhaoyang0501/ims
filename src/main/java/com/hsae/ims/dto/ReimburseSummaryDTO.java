package com.hsae.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class ReimburseSummaryDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int index;
	private String reimburser;	//报销人
	private int number;		//报销人数
	private String reimburseDate;	//报销日期
	private Double reimburseMoney;	//报销金额
	private Double actualMoney;	//实报金额
	private String reiburseUsers;	//就餐人员
	private int type;	//就餐类型
	private int payed;	//是否付款
	private String payDate;		//付款日期
	private String step;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getReimburser() {
		return reimburser;
	}
	public void setReimburser(String reimburser) {
		this.reimburser = reimburser;
	}
	public String getReimburseDate() {
		return reimburseDate;
	}
	public void setReimburseDate(String reimburseDate) {
		this.reimburseDate = reimburseDate;
	}
	public Double getReimburseMoney() {
		return reimburseMoney;
	}
	public void setReimburseMoney(Double reimburseMoney) {
		this.reimburseMoney = reimburseMoney;
	}
	public Double getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}
	public String getReiburseUsers() {
		return reiburseUsers;
	}
	public void setReiburseUsers(String reiburseUsers) {
		this.reiburseUsers = reiburseUsers;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPayed() {
		return payed;
	}
	public void setPayed(int payed) {
		this.payed = payed;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}
	
}
