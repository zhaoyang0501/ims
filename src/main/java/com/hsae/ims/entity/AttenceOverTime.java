package com.hsae.ims.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.ShortTimeSerializer;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.config.Date1Serializer;

/**
 * 加班时间表
 * @author pzy
 *
 */
@Entity
@Table(name = "ims_attence_overtime")
public class AttenceOverTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**加班日期*/
	@JsonSerialize(using = Date1Serializer.class)
	private Date overtimeDate;
	
	/**加班开始时间*/
	@JsonSerialize(using = ShortTimeSerializer.class)
	private Date startTime;
	
	/**加班结束时间*/
	@JsonSerialize(using = ShortTimeSerializer.class)
	private Date endTime;
	
	/**加班人*/
	 @ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	/**核对工时*/
	private Float checkHours;
	
	/**加班类型
	 * 1：平时	2：周末       3: 假日
	 * */
	private String overtimeType;
	
	/**刷卡记录*/
	private String brushRecord;
	/**oa状态  1已提交、未提交*/
	private String oaState;
	

	@JsonIgnore
	@OneToOne(optional = true, cascade = CascadeType.ALL)  
    @JoinColumn(name = "dailyReport_id",referencedColumnName="id")  
	private DailyReport dailyReport;
	
	/**备注*/
	private String remark;
	
	/**录入时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private Timestamp saveTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Float getCheckHours() {
		return checkHours;
	}
	public void setCheckHours(Float checkHours) {
		this.checkHours = checkHours;
	}
	public String getOvertimeType() {
		return overtimeType;
	}
	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
	public String getBrushRecord() {
		return brushRecord;
	}
	public void setBrushRecord(String brushRecord) {
		this.brushRecord = brushRecord;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}
	public DailyReport getDailyReport() {
		return dailyReport;
	}
	public void setDailyReport(DailyReport dailyReport) {
		this.dailyReport = dailyReport;
	}
	public String getOaState() {
		return oaState;
	}
	public void setOaState(String oaState) {
		this.oaState = oaState;
	}
}

























