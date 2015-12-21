package com.hsae.ims.dto;

import java.io.Serializable;

/**
 * 个人主页的培训明细DTO
 * @author caowei
 *
 */
public class TrainingDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String index;
	private String trainingName;		//培训课题
	private String trainingDate;		//培训日期
	private String trainingTime;		//培训时间
	private String hours;				//培训课时
	private String user;				//培训讲师
	private String remarks;				//备注
	
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getTrainingName() {
		return trainingName;
	}
	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	public String getTrainingDate() {
		return trainingDate;
	}
	public void setTrainingDate(String trainingDate) {
		this.trainingDate = trainingDate;
	}
	public String getTrainingTime() {
		return trainingTime;
	}
	public void setTrainingTime(String trainingTime) {
		this.trainingTime = trainingTime;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
