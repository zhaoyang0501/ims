package com.hsae.ims.dto;
/**
 * 考勤情况总统计报表。
 * @author zhaozhou
 *
 */
public class AttenceCountReportDTO {
	
	private String index;
	
	private String deptName;
	
	/**平时加班*/
	private String dayOvertime;
	
	/**周末加班*/
	private String weekOvertime;
	
	/**假日加班*/
	private String holidayOvertime;

	/**调休假*/
	private String dayoffFlirt;

	/**事假*/
	private String privateLeave;
	
	/**产假*/
	private String maternityLeave;
	
	/**哺乳假*/
	private String feedingOff;
	
	/**陪产假*/
	private String paternityLeave;
	
	/**漏签卡*/
	private String absenteeCount;
	
	public String getAbsenteeCount() {
		return absenteeCount;
	}

	public void setAbsenteeCount(String absenteeCount) {
		this.absenteeCount = absenteeCount;
	}

	public String getIndex() {
		return index;
	}

	public String getHolidayOvertime() {
		return holidayOvertime;
	}

	public void setHolidayOvertime(String holidayOvertime) {
		this.holidayOvertime = holidayOvertime;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDayOvertime() {
		return dayOvertime;
	}

	public void setDayOvertime(String dayOvertime) {
		this.dayOvertime = dayOvertime;
	}

	public String getWeekOvertime() {
		return weekOvertime;
	}

	public void setWeekOvertime(String weekOvertime) {
		this.weekOvertime = weekOvertime;
	}

	public String getDayoffFlirt() {
		return dayoffFlirt;
	}

	public void setDayoffFlirt(String dayoffFlirt) {
		this.dayoffFlirt = dayoffFlirt;
	}

	public String getPrivateLeave() {
		return privateLeave;
	}

	public void setPrivateLeave(String privateLeave) {
		this.privateLeave = privateLeave;
	}

	public String getMaternityLeave() {
		return maternityLeave;
	}

	public void setMaternityLeave(String maternityLeave) {
		this.maternityLeave = maternityLeave;
	}

	public String getFeedingOff() {
		return feedingOff;
	}

	public void setFeedingOff(String feedingOff) {
		this.feedingOff = feedingOff;
	}

	public String getPaternityLeave() {
		return paternityLeave;
	}

	public void setPaternityLeave(String paternityLeave) {
		this.paternityLeave = paternityLeave;
	}

}
