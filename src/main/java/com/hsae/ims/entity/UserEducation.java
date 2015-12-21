package com.hsae.ims.entity;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;


/**
 * 
 * @author zhaozhou
 *  教育信息。
 *
 */

@Entity
@Table(name="ims_user_education")
public class UserEducation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	//开始时间。
	@Temporal(TemporalType.DATE)
	private Date eduStartTime;

	//结束时间。
	@Temporal(TemporalType.DATE)
	private Date eduEndTime ;
	
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
	
	@JsonSerialize(using = Date1Serializer.class)
	public Date getEduStartTime() {
		return eduStartTime;
	}

	public void setEduStartTime(Date eduStartTime) {
		this.eduStartTime = eduStartTime;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getEduEndTime() {
		return eduEndTime;
	}

	public void setEduEndTime(Date eduEndTime) {
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
