package com.hsae.ims.dto;

import java.io.Serializable;

public class TrainingDeptSummaryDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String deptName;		//部门名称
	private int usernum;			//人员数量
	private float everyUsernum;		//人均课时
	private float yearTotalnum;		//全年总课时
	private float actualYearTotalnum;	//实际全年总课时
	private float remainnum;		//剩余课时
	private double rate;			//完成率
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getUsernum() {
		return usernum;
	}
	public void setUsernum(int usernum) {
		this.usernum = usernum;
	}
	public float getEveryUsernum() {
		return everyUsernum;
	}
	public void setEveryUsernum(float everyUsernum) {
		this.everyUsernum = everyUsernum;
	}
	public float getYearTotalnum() {
		return yearTotalnum;
	}
	public void setYearTotalnum(float yearTotalnum) {
		this.yearTotalnum = yearTotalnum;
	}
	public float getActualYearTotalnum() {
		return actualYearTotalnum;
	}
	public void setActualYearTotalnum(float actualYearTotalnum) {
		this.actualYearTotalnum = actualYearTotalnum;
	}
	public float getRemainnum() {
		return remainnum;
	}
	public void setRemainnum(float remainnum) {
		this.remainnum = remainnum;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
