package com.hsae.ims.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
@Entity  
@Table(name = "ims_daily_Report") 
@DynamicInsert(true)
@DynamicUpdate(true)
public class DailyReport implements Serializable{
private static final long serialVersionUID = 1L;
    @Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id;  
    /**
     * 日报日期
     */
    @Temporal(TemporalType.DATE)
    private Date reportDate;
    
    private String type;
    /***
     * 项目阶段
     */
    private Integer projectStep;

	/**
     * 日报详情
     */
    @Lob
    private String summary;
    /**
     * 消耗工时
     */
    private float spendHours;
    
    /**
     * 完成度
     */
    private double finishRate;
    
    /**
     * 难点
     */
    @Lob
    private String difficulty;
    
    /**
     * 创建人
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    
    /**
     * 创建日期
     */
    @JsonSerialize(using = Date1Serializer.class)
    private Date createDate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Project project;
   
   /***
    * 工时核对记录
    */
    @OneToOne(optional = true, mappedBy = "dailyReport",fetch=FetchType.LAZY)
    private AttenceOverTime attenceOverTime;
    
	@Transient
    private String week;
    
	public String getWeek() {
		week=DateFormatUtils.format(reportDate, "EEEE");
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public Project getProject() {
		return project==null?new Project():project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	public float getSpendHours() {
		return spendHours;
	}

	public void setSpendHours(float spendHours) {
		this.spendHours = spendHours;
	}

	public double getFinishRate() {
		return finishRate;
	}

	public void setFinishRate(double finishRate) {
		this.finishRate = finishRate;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getProjectStep() {
		return projectStep;
	}

	public void setProjectStep(Integer projectStep) {
		this.projectStep = projectStep;
	}
	public AttenceOverTime getAttenceOverTime() {
			return attenceOverTime;
	}
	public void setAttenceOverTime(AttenceOverTime attenceOverTime) {
			this.attenceOverTime = attenceOverTime;
	}
}
