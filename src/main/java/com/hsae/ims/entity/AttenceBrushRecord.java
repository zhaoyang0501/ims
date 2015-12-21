package com.hsae.ims.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.time.DateFormatUtils;
@Entity
@Table(name = "ims_attence_brushrecord")
public class AttenceBrushRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**人员ID*/
	private Long personId;
	
	/**工号*/
	private String personNo;
	
	/**姓名*/
	private String personName;
	
	/**部门工号*/
	private String deptNo;
	
	/**部门名称*/
	private String deptName;
	
	/**卡号*/
	private Long cardNo;
	
	/**刷卡日期*/
	private Date brushDate;
	
	/**班次信息*/
	private String workingTime;
	/**状态
	 * ims_system_code:	ATTENCETYPE
	 * */
	private String state;
	
	/**刷卡数据*/
	private String brushData;
	
	/**迁移日期*/
	private Timestamp transfertime;
	@Transient
    private String week;
	@Transient
	private AttenceAbsentee attenceAbsentee;
	
	public String getWeek() {
		week=DateFormatUtils.format(brushDate, "EEEE");
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	/**备注*/
	public String remark;
	public String getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(String workingTime) {
		this.workingTime = workingTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getPersonNo() {
		return personNo;
	}

	public void setPersonNo(String personNo) {
		this.personNo = personNo;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getCardNo() {
		return cardNo;
	}

	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}

	public Date getBrushDate() {
		return brushDate;
	}

	public void setBrushDate(Date brushDate) {
		this.brushDate = brushDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBrushData() {
		return brushData;
	}

	public void setBrushData(String brushData) {
		this.brushData = brushData;
	}

	public Timestamp getTransfertime() {
		return transfertime;
	}

	public void setTransfertime(Timestamp transfertime) {
		this.transfertime = transfertime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public AttenceAbsentee getAttenceAbsentee() {
		return attenceAbsentee;
	}

	public void setAttenceAbsentee(AttenceAbsentee attenceAbsentee) {
		this.attenceAbsentee = attenceAbsentee;
	}
}
