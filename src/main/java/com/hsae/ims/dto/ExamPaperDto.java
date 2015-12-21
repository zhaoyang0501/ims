package com.hsae.ims.dto;

import java.io.Serializable;
import java.util.Date;


public class ExamPaperDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	/** 试卷题目 **/
	private String subject;
	
	/** 试卷领域 **/
	private String domain;
	
	/** 试卷类型：1：在线考试；2：网上练习；3：课后作业**/
	private Integer type;
	
	/** 出题方式：1：讲师出题；2：随机选题； **/
	private Integer method;
	
	/** 考试时间 **/
	private Double examTime;
	
	/** 试卷说明 **/
	private String remarks;

	/** 创建人ID **/
	private String createrName;

	/** 创建时间 **/
	private Date saveDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}

	public Double getExamTime() {
		return examTime;
	}

	public void setExamTime(Double examTime) {
		this.examTime = examTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

}
