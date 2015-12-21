package com.hsae.ims.dto;

import java.io.Serializable;

public class TrainingRequireGatherDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String dept;
	
	private String trainingType;
	
	private String subject;
	
	private String trainees;
	
	private int month;
	
	private int year;
	
	private double cost;
	
	private double hours;
	
	private String method;
	
	private String teacher_orgnization;
	
	private String goals;
	
	private int state;
	
	private String remark;

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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

	public String getTrainees() {
		return trainees;
	}

	public void setTrainees(String trainees) {
		this.trainees = trainees;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTeacher_orgnization() {
		return teacher_orgnization;
	}

	public void setTeacher_orgnization(String teacher_orgnization) {
		this.teacher_orgnization = teacher_orgnization;
	}

	public String getGoals() {
		return goals;
	}

	public void setGoals(String goals) {
		this.goals = goals;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
}
