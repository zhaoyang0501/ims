package com.hsae.ims.dto;

public class WeekReportStateDto {
	/***
	 * 0未提交
	 * 1已经提交
	 */
	private Integer state;
	private String empnumber;
	private String name;
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getEmpnumber() {
		return empnumber;
	}
	public void setEmpnumber(String empnumber) {
		this.empnumber = empnumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
