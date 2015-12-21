package com.hsae.ims.entity;


import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
/***
 * 考勤统计
 * 该表的数据通过job生成
 * @author panchaoyang
 *
 */
@Entity
@Table(name = "ims_attence_statisticsyear", uniqueConstraints = { @UniqueConstraint(columnNames = {"year", "user"})})
public class AttenceStatisticsYear {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**所属年份 yyyy*/
	private String year;
	
	/**人员*/
	@ManyToOne
	private User user;
	
	/**去年结余*/
	private Double lastRest;
	
	/**今年年新增*/
	private Double currentIncrease;
	
	/**本月减少*/
	private Double currentReduce;
	
	/**今年剩余*/
	private Double currentRest;
	
	/**统计日期起*/
	private Date startDate;
	
	/**统计日期止*/
	private Date endDate;
	/**生成日期*/
	private Timestamp createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Double getLastRest() {
		return lastRest;
	}
	public void setLastRest(Double lastRest) {
		this.lastRest = lastRest;
	}
	public Double getCurrentIncrease() {
		return currentIncrease;
	}
	public void setCurrentIncrease(Double currentIncrease) {
		this.currentIncrease = currentIncrease;
	}
	public Double getCurrentReduce() {
		return currentReduce;
	}
	public void setCurrentReduce(Double currentReduce) {
		this.currentReduce = currentReduce;
	}
	public Double getCurrentRest() {
		return currentRest;
	}
	public void setCurrentRest(Double currentRest) {
		this.currentRest = currentRest;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
