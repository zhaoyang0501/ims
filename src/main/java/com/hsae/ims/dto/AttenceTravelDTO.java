package com.hsae.ims.dto;


/**
 * 出差表
 * @author zhaozhou
 */
public class AttenceTravelDTO {
	
	private String  index;

	private String userName;
	
	/**
	 * 出差日期
	 */
	private String travelDate;
	
	/**
	 * 出差开始时间
	 */
	private String startTime;
	
	/**
	 * 出差结束时间
	 */
	private String endTime;
	
	/**
	 * 开始时间状态
	 * 1.12点(含)前
	 * 2.12点后
	 */
	private String startTimeType;
	
	/**
	 * 结束时间状态
	 * 1.12点(含)前
	 * 2.12点后
	 */
	private String endTimeType;
	
	/**
	 * 出差地址
	 */
	private String address;
	
	/**
	 * 出差原因
	 */
	private String reason;
	
	/**保存时间*/
	private String saveTime;


	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
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

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	
	public String getStartTimeType() {
		return startTimeType;
	}

	public void setStartTimeType(String startTimeType) {
		this.startTimeType = startTimeType;
	}

	public String getEndTimeType() {
		return endTimeType;
	}

	public void setEndTimeType(String endTimeType) {
		this.endTimeType = endTimeType;
	}

}























