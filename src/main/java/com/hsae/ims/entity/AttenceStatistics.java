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
@Table(name = "ims_attence_statistics", uniqueConstraints = { @UniqueConstraint(columnNames = {"month", "user"})})
public class AttenceStatistics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**所属月份 yyyymm*/
	private String month;
	
	/**人员*/
	@ManyToOne
	private User user;
	
	/**上月结余*/
	private Double lastRest;
	
	/**本月新增*/
	private Double currentIncrease;
	
	/**本月减少*/
	private Double currentReduce;
	
	/**本月剩余*/
	private Double currentRest;
	
	/**统计日期起*/
	private Date startDate;
	
	/**统计日期止*/
	private Date endDate;
	
	/**各种假别的统计*/
	private Double dayoff10;
	private Double dayoff20;
	private Double dayoff30;
	private Double dayoff40;
	private Double dayoff50;
	private Double dayoff60;
	private Double dayoff70;
	private Double dayoff80;
	private Double dayoff90;
	private Double dayoff100;
	private Double dayoff110;
	private Double dayoff120;
	private Double dayoff130;
	private Double dayoff10LastRest;
	private Double dayoff10CurrentIncrease;
	private Double dayoff10CurrentReduce;
	private Double dayoff10CurrentRest;
	private Double overTime;
	
	public Double getOverTime() {
		return overTime;
	}
	public void setOverTime(Double overTime) {
		this.overTime = overTime;
	}
	/**生成日期*/
	private Timestamp createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Double getDayoff10() {
		return dayoff10;
	}
	public void setDayoff10(Double dayoff10) {
		this.dayoff10 = dayoff10;
	}
	public Double getDayoff20() {
		return dayoff20;
	}
	public void setDayoff20(Double dayoff20) {
		this.dayoff20 = dayoff20;
	}
	public Double getDayoff30() {
		return dayoff30;
	}
	public void setDayoff30(Double dayoff30) {
		this.dayoff30 = dayoff30;
	}
	public Double getDayoff40() {
		return dayoff40;
	}
	public void setDayoff40(Double dayoff40) {
		this.dayoff40 = dayoff40;
	}
	public Double getDayoff50() {
		return dayoff50;
	}
	public void setDayoff50(Double dayoff50) {
		this.dayoff50 = dayoff50;
	}
	public Double getDayoff60() {
		return dayoff60;
	}
	public void setDayoff60(Double dayoff60) {
		this.dayoff60 = dayoff60;
	}
	public Double getDayoff70() {
		return dayoff70;
	}
	public void setDayoff70(Double dayoff70) {
		this.dayoff70 = dayoff70;
	}
	public Double getDayoff80() {
		return dayoff80;
	}
	public void setDayoff80(Double dayoff80) {
		this.dayoff80 = dayoff80;
	}
	public Double getDayoff90() {
		return dayoff90;
	}
	public void setDayoff90(Double dayoff90) {
		this.dayoff90 = dayoff90;
	}
	public Double getDayoff100() {
		return dayoff100;
	}
	public void setDayoff100(Double dayoff100) {
		this.dayoff100 = dayoff100;
	}
	public Double getDayoff110() {
		return dayoff110;
	}
	public void setDayoff110(Double dayoff110) {
		this.dayoff110 = dayoff110;
	}
	public Double getDayoff120() {
		return dayoff120;
	}
	public void setDayoff120(Double dayoff120) {
		this.dayoff120 = dayoff120;
	}
	public Double getDayoff130() {
		return dayoff130;
	}
	public void setDayoff130(Double dayoff130) {
		this.dayoff130 = dayoff130;
	}
	public Double getDayoff10LastRest() {
		return dayoff10LastRest;
	}
	public void setDayoff10LastRest(Double dayoff10LastRest) {
		this.dayoff10LastRest = dayoff10LastRest;
	}
	public Double getDayoff10CurrentIncrease() {
		return dayoff10CurrentIncrease;
	}
	public void setDayoff10CurrentIncrease(Double dayoff10CurrentIncrease) {
		this.dayoff10CurrentIncrease = dayoff10CurrentIncrease;
	}
	public Double getDayoff10CurrentReduce() {
		return dayoff10CurrentReduce;
	}
	public void setDayoff10CurrentReduce(Double dayoff10CurrentReduce) {
		this.dayoff10CurrentReduce = dayoff10CurrentReduce;
	}
	public Double getDayoff10CurrentRest() {
		return dayoff10CurrentRest;
	}
	public void setDayoff10CurrentRest(Double dayoff10CurrentRest) {
		this.dayoff10CurrentRest = dayoff10CurrentRest;
	}
}
