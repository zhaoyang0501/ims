package com.hsae.ims.dto;


/**
 * 
 * @author zhaozhou
 *  教育信息。
 *
 */

public class UserEducationDTO {
	
	private long id;
	
	//开始时间。
	private String eduStartTime;

	//结束时间。
	private String eduEndTime ;
	
	//学校。
	private String eduSchool;
	
	//主修专业。
	private String eduMajor;
	
	//获得证书。
	private String eduCertificate;
	
	//教育方式： 1.全日制。。。。
	private String eduWay;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getEduStartTime() {
		return eduStartTime;
	}

	public void setEduStartTime(String eduStartTime) {
		this.eduStartTime = eduStartTime;
	}

	public String getEduEndTime() {
		return eduEndTime;
	}

	public void setEduEndTime(String eduEndTime) {
		this.eduEndTime = eduEndTime;
	}

	public String getEduSchool() {
		return eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}

	public String getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(String eduMajor) {
		this.eduMajor = eduMajor;
	}

	public String getEduCertificate() {
		return eduCertificate;
	}

	public void setEduCertificate(String eduCertificate) {
		this.eduCertificate = eduCertificate;
	}

	public String getEduWay() {
		return eduWay;
	}

	public void setEduWay(String eduWay) {
		this.eduWay = eduWay;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

}
