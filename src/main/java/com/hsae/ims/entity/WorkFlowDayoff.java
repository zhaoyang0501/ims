package com.hsae.ims.entity;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.entity.osworkflow.Wfentry;
/**
 * 请假表
 * @author panchaoyang
 */
@Entity
@Table(name = "ims_workflow_dayoff")
public class WorkFlowDayoff{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**请假日期*/
	@JsonSerialize(using =Date1Serializer.class)
	private Date dayoffDate;
	
	/**请假人*/
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	/**请假开始日期*/
	@JsonSerialize(using = TimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Timestamp startTime;
	
	/**请假结束日期*/
	@JsonSerialize(using = TimeSerializer.class)
	 @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")  
	private Timestamp endTime;
	
	/**总工时*/
	private Float spendHours;
	/**假别
	 * 30调休假 40事假 80产假  100哺乳假  110陪产假 。
	 * */
	private String dayoffType;
	
	private String tel;
	
	/**请假原因*/
	private String remark;
	
	/**工作接替人*/
	/**请假人*/
	@ManyToOne(fetch = FetchType.EAGER)
	private User proxy;
	
	/**请假天数*/
	private Integer days;
	
	/**请假小时*/
	private Double hours;
	/**工作流Id*/
	@OneToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	
	/**录入时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private  Timestamp saveTime;
	
	@Transient
	private String step;
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public User getProxy() {
		return proxy;
	}
	public void setProxy(User proxy) {
		this.proxy = proxy;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Double getHours() {
		return hours;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	public Wfentry getWfentry() {
		return wfentry;
	}
	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDayoffDate() {
		return dayoffDate;
	}
	public void setDayoffDate(Date dayoffDate) {
		this.dayoffDate = dayoffDate;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public Float getSpendHours() {
		return spendHours;
	}
	public void setSpendHours(Float spendHours) {
		this.spendHours = spendHours;
	}
	public String getDayoffType() {
		return dayoffType;
	}
	public void setDayoffType(String dayoffType) {
		this.dayoffType = dayoffType;
	}
	public Timestamp getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}











