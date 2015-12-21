package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Transient;

/***
 * 答题主表
 * 
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_exam_answer")
public class ExamAnswer implements Serializable {

	private static final long serialVersionUID = 1L;

	public ExamAnswer() {
		this.saveDate = new Date();
		this.startDate = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 试题 **/
	@ManyToOne(fetch = FetchType.EAGER)
	private ExamPaper paper;

	/** 答题人 **/
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	/** 答分 **/
	private Double score = 0d;

	/** 是否已阅卷0：未阅卷 1： 已阅卷 **/
	private Integer ifgovoer = 0;

	/** 开始答题 **/
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date startDate;

	/** 提交答题 **/
	@javax.persistence.Temporal(TemporalType.DATE)
	private Date saveDate;

	@Transient
	private Integer operate = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ExamPaper getPaper() {
		return paper;
	}

	public void setPaper(ExamPaper paper) {
		this.paper = paper;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getIfgovoer() {
		return ifgovoer;
	}

	public void setIfgovoer(Integer ifgovoer) {
		this.ifgovoer = ifgovoer;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

}
