package com.hsae.ims.dto;

/**
 * @author zhaozhou
 */
public class AttenceAbsenteeDTO {
	

	private String index;
	
	private String userName;
	
	/** 漏打卡人部门 */
	private String deptName;

	/**漏打卡日期*/
	private String absenteeDate;
	
	/**漏打卡时间 */
	private String absenteeTime;
	
	/**漏打卡类型
	 * 1 没带卡
	 * 2 刷卡无记录
	 * 3 忘记刷卡
	 * 4 其他
	 * */
	private String absenteeType;
	
	/**漏打卡原因*/
	private String remark;
	
	/**保存时间*/
	private String saveTime;
	

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
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

	public String getAbsenteeDate() {
		return absenteeDate;
	}

	public void setAbsenteeDate(String absenteeDate) {
		this.absenteeDate = absenteeDate;
	}

	public String getAbsenteeTime() {
		return absenteeTime;
	}

	public void setAbsenteeTime(String absenteeTime) {
		this.absenteeTime = absenteeTime;
	}

	public String getAbsenteeType() {
		return absenteeType;
	}

	public void setAbsenteeType(String absenteeType) {
		this.absenteeType = absenteeType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
}
