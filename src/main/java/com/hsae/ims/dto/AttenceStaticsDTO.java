package com.hsae.ims.dto;

import java.io.Serializable;

public class AttenceStaticsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 上月结余
	 */
	private String lastmonthRemain;
	
	/**
	 * 本月新增
	 */
	private String currentmonthIncrease;
	
	/**
	 * 本月减少
	 */
	private String currentmonthMiuns;
	
	/**
	 * 当前剩余
	 */
	private String hours;
	
	private String startDate;
	
	private String endDate;

	public String getLastmonthRemain() {
		return lastmonthRemain;
	}

	public void setLastmonthRemain(String lastmonthRemain) {
		this.lastmonthRemain = lastmonthRemain;
	}

	public String getCurrentmonthIncrease() {
		return currentmonthIncrease;
	}

	public void setCurrentmonthIncrease(String currentmonthIncrease) {
		this.currentmonthIncrease = currentmonthIncrease;
	}

	public String getCurrentmonthMiuns() {
		return currentmonthMiuns;
	}

	public void setCurrentmonthMiuns(String currentmonthMiuns) {
		this.currentmonthMiuns = currentmonthMiuns;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
 