package com.hsae.ims.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
import com.hsae.ims.config.DateJsonDeserializer;
@Entity
@Table(name = "ims_workflow_overtimedetail")
public class WorkFlowOverTimeDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**加班日期*/
	@JsonSerialize(using = Date1Serializer.class)
	private Date overtimeDate;
	
	/**加班开始时间*/
	 @JsonDeserialize(using=DateJsonDeserializer.class)  
	private Date startTime;
	
	/**加班结束时间*/
	@JsonDeserialize(using=DateJsonDeserializer.class)  
	private Date endTime;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)  
    @JoinColumn(name="work_flow_over_time") 
	private WorkFlowOverTime workFlowOverTime;
	
	/**加班人*/
	 @ManyToOne(fetch = FetchType.EAGER)
	private User user;
	/**加班原因*/
	private String remark; 
	/**脚注*/
	private String footnote;
	
	@Persistent
	private String position;
	
	public WorkFlowOverTime getWorkFlowOverTime() {
		return workFlowOverTime;
	}

	public void setWorkFlowOverTime(WorkFlowOverTime workFlowOverTime) {
		this.workFlowOverTime = workFlowOverTime;
	}

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

	public Date getOvertimeDate() {
		return overtimeDate;
	}

	public void setOvertimeDate(Date overtimeDate) {
		this.overtimeDate = overtimeDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	/**核对工时*/
	private Double hours;
	
	public String getFootnote() {
		return footnote;
	}

	public void setFootnote(String footnote) {
		this.footnote = footnote;
	}
}
