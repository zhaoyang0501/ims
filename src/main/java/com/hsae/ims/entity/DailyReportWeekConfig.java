package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;

@Entity  
@Table(name = "ims_daily_weekconfig") 
public class DailyReportWeekConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id; 
	
	/**
	 * 周
	 */
	private Integer weekNum;
	
	/**
	 * 年
	 */
	private String year;
	
	/**
	 * 开始日期
	 */
	private Date startDate;
	
	/**
	 * 开始日期
	 */
	private Date endDate;
	
	/**
	 * 日志可以提交日期
	 */
	private Date submitDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
}
