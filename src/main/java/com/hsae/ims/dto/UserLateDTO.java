package com.hsae.ims.dto;

public class UserLateDTO {
	public final static Integer YEAR_MAX=6;
	public final static  Integer MONTH_MAX=1;
	private String personNo;
	private Integer lateMonth;
	private Integer lateYear;
	public String getPersonNo() {
		return personNo;
	}
	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}
	public Integer getLateMonth() {
		return lateMonth;
	}
	public void setLateMonth(Integer lateMonth) {
		this.lateMonth = lateMonth;
	}
	public Integer getLateYear() {
		return lateYear;
	}
	public void setLateYear(Integer lateYear) {
		this.lateYear = lateYear;
	}
}
