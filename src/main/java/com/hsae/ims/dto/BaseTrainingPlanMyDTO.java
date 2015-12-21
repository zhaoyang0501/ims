package com.hsae.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.hsae.ims.entity.User;

public class BaseTrainingPlanMyDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	/* 计划标题 */
	private String title;
	/* 计划描述 */
	private String description;
	/* 培训课程 */
	private List<String> courseNames;
	/* 开始日期 */
	private Date start;
	/* 结束日期 */
	private Date end;
	/* 培训目标 */
	private String targets;
	
	private String state;
	
	private String ifscore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getCourseNames() {
		return courseNames;
	}

	public void setCourseNames(List<String> courseNames) {
		this.courseNames = courseNames;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIfscore() {
		return ifscore;
	}

	public void setIfscore(String ifscore) {
		this.ifscore = ifscore;
	}
	
	
}
