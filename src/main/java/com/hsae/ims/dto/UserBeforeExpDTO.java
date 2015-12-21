package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  之前的工作经历。
 *
 */

public class UserBeforeExpDTO {
	
	private long id;
	
	//开始时间。
	private String beforeStartTime;

	//结束时间。
	private String beforeEndTime;
	
	//原公司名称。
	private String beforeCompany;
	
	//原部门。
	private String beforeDept;
	
	//原职位。
	private String beforePosition;
	
	//原从业经历。
	private String beforePerience;
	
	//原离职原因。
	private String beforeLeavingReasons;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBeforeStartTime() {
		return beforeStartTime;
	}

	public void setBeforeStartTime(String beforeStartTime) {
		this.beforeStartTime = beforeStartTime;
	}

	public String getBeforeEndTime() {
		return beforeEndTime;
	}

	public void setBeforeEndTime(String beforeEndTime) {
		this.beforeEndTime = beforeEndTime;
	}

	public String getBeforeCompany() {
		return beforeCompany;
	}

	public void setBeforeCompany(String beforeCompany) {
		this.beforeCompany = beforeCompany;
	}

	public String getBeforeDept() {
		return beforeDept;
	}

	public void setBeforeDept(String beforeDept) {
		this.beforeDept = beforeDept;
	}

	public String getBeforePosition() {
		return beforePosition;
	}

	public void setBeforePosition(String beforePosition) {
		this.beforePosition = beforePosition;
	}

	public String getBeforePerience() {
		return beforePerience;
	}

	public void setBeforePerience(String beforePerience) {
		this.beforePerience = beforePerience;
	}

	public String getBeforeLeavingReasons() {
		return beforeLeavingReasons;
	}

	public void setBeforeLeavingReasons(String beforeLeavingReasons) {
		this.beforeLeavingReasons = beforeLeavingReasons;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}
	
}
