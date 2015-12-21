package com.hsae.ims.dto;


/**
 * 
 * @author zhaozhou
 * 现在的工作经历。
 *
 */

public class UserNowExpDTO {
	
	private long id;
	
	//开始时间。
	private String nowStartTime;

	//结束时间。
	private String nowEndTime;
	
	//部门。
	private String nowDept;
	
	//岗位。1...2....
	private String nowPost;
	
	//从业经历。
	private String nowPerience;
	
	//转岗原因。
	private String nowAlterReasons;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNowStartTime() {
		return nowStartTime;
	}

	public void setNowStartTime(String nowStartTime) {
		this.nowStartTime = nowStartTime;
	}

	public String getNowEndTime() {
		return nowEndTime;
	}

	public void setNowEndTime(String nowEndTime) {
		this.nowEndTime = nowEndTime;
	}

	public String getNowDept() {
		return nowDept;
	}

	public void setNowDept(String nowDept) {
		this.nowDept = nowDept;
	}

	public String getNowPost() {
		return nowPost;
	}

	public void setNowPost(String nowPost) {
		this.nowPost = nowPost;
	}

	public String getNowPerience() {
		return nowPerience;
	}

	public void setNowPerience(String nowPerience) {
		this.nowPerience = nowPerience;
	}

	public String getNowAlterReasons() {
		return nowAlterReasons;
	}

	public void setNowAlterReasons(String nowAlterReasons) {
		this.nowAlterReasons = nowAlterReasons;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

}
