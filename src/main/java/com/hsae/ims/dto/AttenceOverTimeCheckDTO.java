package com.hsae.ims.dto;

public class AttenceOverTimeCheckDTO {
	
	private Long overTimeId;
	private Long reportId;
	private String startTime;
	private String endTime;
	private String overtimeType;
	private Float checkHours;
	private String brushRecord;
	private String oaState;
	public String getOaState() {
		return oaState;
	}
	public void setOaState(String oaState) {
		this.oaState = oaState;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
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
	public String getOvertimeType() {
		return overtimeType;
	}
	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
	public Float getCheckHours() {
		return checkHours;
	}
	public void setCheckHours(Float checkHours) {
		this.checkHours = checkHours;
	}
	public String getBrushRecord() {
		return brushRecord;
	}
	public void setBrushRecord(String brushRecord) {
		this.brushRecord = brushRecord;
	}
	public Long getOverTimeId() {
		return overTimeId;
	}
	public void setOverTimeId(Long overTimeId) {
		this.overTimeId = overTimeId;
	}
}
