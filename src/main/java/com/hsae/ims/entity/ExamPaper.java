package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 在线试卷
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_exam_paper")
public class ExamPaper implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/** 试卷题目 **/
	private String subject;
	
	/** 试卷领域 **/
	private String domain;
	
	/** 试卷类型：1：在线考试；2：网上练习；3：课后作业**/
	private Integer type = 1;
	
	/** 出题方式：1：讲师出题；2：随机选题； **/
	private Integer method = 1;
	
	/** 考试时间 **/
	private Double examTime;
	
	/** 试卷说明 **/
	@Lob
	private String remarks;

	/** 创建人ID **/
	private Long userId;

	/** 创建时间 **/
	@Temporal(TemporalType.DATE)
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public Double getExamTime() {
		return examTime;
	}

	public void setExamTime(Double examTime) {
		this.examTime = examTime;
	}

}
