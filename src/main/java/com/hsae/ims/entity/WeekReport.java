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
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;

@Entity  
@Table(name = "ims_daily_weekreport") 
@DynamicInsert(true)
@DynamicUpdate(true)
public class WeekReport  implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id;  
	/**
	 * 当前考核周
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private DailyReportWeekConfig week;
	
	/**工作流ＩＤ*/
	private Long osworkflow;
    
	/**
	 * 创建人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
    private User creater;
    
    /**
	 * 创建日期
	 */
    private Date createDate;
   
    private String step;
    
 
    private int state;
    
    /**
     * 退回次数
     */
    private int rejects = 0;
    
    /**
     * 审批意见
     */
    private String approvals;
    
    /***
     *周总结
     */
    @Lob 
    private String remark;
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DailyReportWeekConfig getWeek() {
		return week;
	}

	public void setWeek(DailyReportWeekConfig week) {
		this.week = week;
	}

	public Long getOsworkflow() {
		return osworkflow;
	}

	public void setOsworkflow(Long osworkflow) {
		this.osworkflow = osworkflow;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}
	@JsonSerialize(using = Date1Serializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getRejects() {
		return rejects;
	}

	public void setRejects(int rejects) {
		this.rejects = rejects;
	}

	public String getApprovals() {
		return approvals;
	}

	public void setApprovals(String approvals) {
		this.approvals = approvals;
	}

}
