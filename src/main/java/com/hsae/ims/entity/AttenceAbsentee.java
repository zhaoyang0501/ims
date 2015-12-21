package com.hsae.ims.entity;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.TimeSerializer;
/**
 * 漏打卡表
 * @author panchaoyang
 *
 */
@Entity
@Table(name = "ims_attence_absentee")
public class AttenceAbsentee{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long user;
	
	/**漏打卡日期*/
	@Temporal(TemporalType.DATE)
	private Date absenteeDate;
	
	/**漏打卡时间 */
	private String absenteeTime;
	
	/**漏打卡类型*/
	private String absenteeType;
	
	/**漏打卡原因*/
	private String reason;
	
	/**备注*/
	private String remark;
	
	/**保存时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private Timestamp saveTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUser() {
		return user;
	}
	public void setUser(Long user) {
		this.user = user;
	}
	public Date getAbsenteeDate() {
		return absenteeDate;
	}
	public void setAbsenteeDate(Date absenteeDate) {
		this.absenteeDate = absenteeDate;
	}
	public String getAbsenteeTime() {
		return absenteeTime;
	}
	public void setAbsenteeTime(String absenteeTime) {
		this.absenteeTime = absenteeTime;
	}
	public String getAbsenteeType() {
		return absenteeType;
	}
	public void setAbsenteeType(String absenteeType) {
		this.absenteeType = absenteeType;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
