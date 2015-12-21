package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "ims_training_requirement_detail")
public class TrainingRequirementDetail implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long requireId;
	
	private Long dept;
	
	/**
	 * 培训类型
	 * 1、	入职/转岗培训
	 * 2、	企业文化制度培训
	 * 3、	体系培训
	 * 4、	管理者培训
	 * 5、	任职能力/岗位技能培训
	 * 6、	专业提升培训
	 * 7、	战略培训
	 * 8、	客户要求培训
	 */
	private String trainingType;
	
	@Transient
	private String trainees;
	
	/**
	 * 培训课题
	 */
	private String subject;
	
	/**
	 * 计划年份
	 */
	private int year;
	
	/**
	 * 计划月份
	 */
	private int month;
	
	/**
	 * 预计费用
	 */
	private double cost;
	
	/**
	 * 预计课时
	 */
	private double hours;
	
	/**
	 * 培训方式
	 * 1：内训
	 * 2：外训
	 */
	private int method;
	
	/**
	 * 当为外训时必须填写
	 * 培训机构
	 */
	private String orgnization;
	
	/**
	 * 当为内训时必须填写
	 * 讲师
	 */
	private Long teacher;
	
	/**
	 * 培训目标
	 */
	private String goals;
	
	/**
	 * 状态
	 * 1：部门新增
	 * 2：中心领导新增
	 * 3：中心领导废弃
	 */
	private int state = 1;
	
	private String remark;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRequireId() {
		return requireId;
	}

	public void setRequireId(Long requireId) {
		this.requireId = requireId;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public String getOrgnization() {
		return orgnization;
	}

	public void setOrgnization(String orgnization) {
		this.orgnization = orgnization;
	}

	public Long getTeacher() {
		return teacher;
	}

	public void setTeacher(Long teacher) {
		this.teacher = teacher;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getTrainees() {
		return trainees;
	}

	public void setTrainees(String trainees) {
		this.trainees = trainees;
	}

	public Long getDept() {
		return dept;
	}

	public void setDept(Long dept) {
		this.dept = dept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
