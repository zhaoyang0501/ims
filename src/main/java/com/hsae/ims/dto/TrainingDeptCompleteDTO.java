package com.hsae.ims.dto;

import java.io.Serializable;

/**
 * 部门培训数量完成率DTO
 * @author caowei
 *
 */
public class TrainingDeptCompleteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deptName;	//部门名称
	private int inplanNnum;		//计划内内训数量
	private int inplanWnum;		//计划内外训数量
	private int outplanNnum;	//计划外内训数量
	private int outplanWnum;	//计划外外训数量
	private int totalplanNnum;	//合计内训数量
	private int totalplanWnum;	//合计外训数量
	private int actualNnum;		//实际完成内训数量
	private int actualWnum;		//实际完成外训数量
	private double rate;		//完成率
	private String notComplete;	//未完成的培训
	
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getInplanNnum() {
		return inplanNnum;
	}
	public void setInplanNnum(int inplanNnum) {
		this.inplanNnum = inplanNnum;
	}
	public int getInplanWnum() {
		return inplanWnum;
	}
	public void setInplanWnum(int inplanWnum) {
		this.inplanWnum = inplanWnum;
	}
	public int getOutplanNnum() {
		return outplanNnum;
	}
	public void setOutplanNnum(int outplanNnum) {
		this.outplanNnum = outplanNnum;
	}
	public int getOutplanWnum() {
		return outplanWnum;
	}
	public void setOutplanWnum(int outplanWnum) {
		this.outplanWnum = outplanWnum;
	}
	public int getTotalplanNnum() {
		return totalplanNnum;
	}
	public void setTotalplanNnum(int totalplanNnum) {
		this.totalplanNnum = totalplanNnum;
	}
	public int getTotalplanWnum() {
		return totalplanWnum;
	}
	public void setTotalplanWnum(int totalplanWnum) {
		this.totalplanWnum = totalplanWnum;
	}
	public int getActualNnum() {
		return actualNnum;
	}
	public void setActualNnum(int actualNnum) {
		this.actualNnum = actualNnum;
	}
	public int getActualWnum() {
		return actualWnum;
	}
	public void setActualWnum(int actualWnum) {
		this.actualWnum = actualWnum;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getNotComplete() {
		return notComplete;
	}
	public void setNotComplete(String notComplete) {
		this.notComplete = notComplete;
	}
	
	
}
