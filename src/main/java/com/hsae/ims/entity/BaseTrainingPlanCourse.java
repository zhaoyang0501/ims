package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 为员工制定培训计划表
 * @author caowei
 *
 */
@Entity
@Table(name="ims_basetraining_plan_course")
public class BaseTrainingPlanCourse implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/**人员**/
	@ManyToOne
	private User user;
	/**培训计划**/
	private Long plan;
	/**课程**/
	@ManyToOne
	private BaseTrainingCourse course;
	/** 0:未学习	1:已学习**/
	private Integer state = 0;
	/** 0:未考试	1:已考试**/
	private Integer ifscore = 0;
	/**考试得分**/
	private Double score = 0d;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BaseTrainingCourse getCourse() {
		return course;
	}
	public void setCourse(BaseTrainingCourse course) {
		this.course = course;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getPlan() {
		return plan;
	}
	public void setPlan(Long plan) {
		this.plan = plan;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Integer getIfscore() {
		return ifscore;
	}
	public void setIfscore(Integer ifscore) {
		this.ifscore = ifscore;
	}
	
}
