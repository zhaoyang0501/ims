package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/****
 * 在线学习课程
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_basetraining_course")
public class BaseTrainingCourse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/* 课程名称  */
	private String courseName;
	/* 课程类型  */
	private String courseType;
	/* 培训材料  */
	private String materials;
	/* 培训课时  */
	private Double hours;
	/* 考试试题  */
	@ManyToOne
	private ExamPaper exam;
	/* 培训目标  */
	@Lob
	private String targets;
	@Lob
	private String remarks;
	@Transient
	private int operate = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public ExamPaper getExam() {
		return exam;
	}

	public void setExam(ExamPaper exam) {
		this.exam = exam;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}
	
}
