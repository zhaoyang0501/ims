package com.hsae.ims.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
@Entity
@Table(name = "ims_training_plan")
public class TrainingPlan {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String sn;
	
	private String source;
	
	private String trainingType;
	
	private String contents;
	
	private Double planHours;
	
	private Double planCost;
	
	@JsonSerialize(using = Date1Serializer.class)
	private Date planStartTime;
	
	@JsonSerialize(using = Date1Serializer.class)
	private Date planEndTime;
	
	private String planTime;
	
	private String planAddr;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User planTeacher;
	
	@OneToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<User> planStudents;
	/**设备器材*/
	private String planEquipment;
	/**考核方式**/
	private String planCheckType;
	
	private Double realHours;
	
	private Double realCost;
	
	@JsonSerialize(using = Date1Serializer.class)
	private Date realStartTime;
	
	@JsonSerialize(using = Date1Serializer.class)
	private Date realEndTime;
	
	private String realTime;
	
	private String realAddr;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User realTeacher;
	
	@OneToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<User> realStudents;
	/**设备器材*/
	private String realEquipment;
	/**考核方式**/
	private String realCheckType;
	
	private String remark;
	
	private String processInstanceId;
	
	private String docurl1;
	
	private String docurl2;
	
	private String docurl3;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Double getPlanHours() {
		return planHours;
	}

	public void setPlanHours(Double planHours) {
		this.planHours = planHours;
	}

	public Double getPlanCost() {
		return planCost;
	}

	public void setPlanCost(Double planCost) {
		this.planCost = planCost;
	}

	public Date getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Date planStartTime) {
		this.planStartTime = planStartTime;
	}

	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}

	public String getPlanAddr() {
		return planAddr;
	}

	public void setPlanAddr(String planAddr) {
		this.planAddr = planAddr;
	}

	public User getPlanTeacher() {
		return planTeacher;
	}

	public void setPlanTeacher(User planTeacher) {
		this.planTeacher = planTeacher;
	}

	public List<User> getPlanStudents() {
		return planStudents;
	}

	public void setPlanStudents(List<User> planStudents) {
		this.planStudents = planStudents;
	}

	public String getPlanEquipment() {
		return planEquipment;
	}

	public void setPlanEquipment(String planEquipment) {
		this.planEquipment = planEquipment;
	}

	public String getPlanCheckType() {
		return planCheckType;
	}

	public void setPlanCheckType(String planCheckType) {
		this.planCheckType = planCheckType;
	}

	public Double getRealHours() {
		return realHours;
	}

	public void setRealHours(Double realHours) {
		this.realHours = realHours;
	}

	public Double getRealCost() {
		return realCost;
	}

	public void setRealCost(Double realCost) {
		this.realCost = realCost;
	}

	public Date getRealStartTime() {
		return realStartTime;
	}

	public void setRealStartTime(Date realStartTime) {
		this.realStartTime = realStartTime;
	}

	public Date getRealEndTime() {
		return realEndTime;
	}

	public void setRealEndTime(Date realEndTime) {
		this.realEndTime = realEndTime;
	}

	public String getRealAddr() {
		return realAddr;
	}

	public void setRealAddr(String realAddr) {
		this.realAddr = realAddr;
	}

	public User getRealTeacher() {
		return realTeacher;
	}

	public void setRealTeacher(User realTeacher) {
		this.realTeacher = realTeacher;
	}

	public List<User> getRealStudents() {
		return realStudents;
	}

	public void setRealStudents(List<User> realStudents) {
		this.realStudents = realStudents;
	}

	public String getRealEquipment() {
		return realEquipment;
	}

	public void setRealEquipment(String realEquipment) {
		this.realEquipment = realEquipment;
	}

	public String getRealCheckType() {
		return realCheckType;
	}

	public String getPlanTime() {
		return planTime;
	}

	public void setPlanTime(String planTime) {
		this.planTime = planTime;
	}

	public String getRealTime() {
		return realTime;
	}

	public void setRealTime(String realTime) {
		this.realTime = realTime;
	}

	public void setRealCheckType(String realCheckType) {
		this.realCheckType = realCheckType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getDocurl1() {
		return docurl1;
	}

	public void setDocurl1(String docurl1) {
		this.docurl1 = docurl1;
	}

	public String getDocurl2() {
		return docurl2;
	}

	public void setDocurl2(String docurl2) {
		this.docurl2 = docurl2;
	}

	public String getDocurl3() {
		return docurl3;
	}

	public void setDocurl3(String docurl3) {
		this.docurl3 = docurl3;
	}
}
