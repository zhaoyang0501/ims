package com.hsae.ims.dto;

import java.sql.Timestamp;
/***
 * 用于工作流程图显示
 * @author panchaoyang
 *
 */
public class StepDTO {
	private Integer stepid;
	
	private String userName;
	private String action;
	private Timestamp startDate;
	private Timestamp finishDate;
	private String approve;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Timestamp finishDate) {
		this.finishDate = finishDate;
	}
	public String getApprove() {
		return approve;
	}
	public void setApprove(String approve) {
		this.approve = approve;
	}
	public Integer getStepid() {
		return stepid;
	}
	public void setStepid(Integer stepid) {
		this.stepid = stepid;
	}
}
