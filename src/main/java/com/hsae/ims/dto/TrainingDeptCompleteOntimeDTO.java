package com.hsae.ims.dto;

import java.io.Serializable;

public class TrainingDeptCompleteOntimeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deptName;		//部门名称
	private int inplanNnum;			//计划内内训数量
	private int inplanWnum;			//计划内外训数量
	private int completeOntimeNnum;	//按期完成内训数量
	private int completeOntimeWnum;	//按期完成外训数量
	private double rate;				//完成率
	private int postponenum;			//延期次数
	private String postpones;			//延期的培训
	
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
	public int getCompleteOntimeNnum() {
		return completeOntimeNnum;
	}
	public void setCompleteOntimeNnum(int completeOntimeNnum) {
		this.completeOntimeNnum = completeOntimeNnum;
	}
	public int getCompleteOntimeWnum() {
		return completeOntimeWnum;
	}
	public void setCompleteOntimeWnum(int completeOntimeWnum) {
		this.completeOntimeWnum = completeOntimeWnum;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getPostponenum() {
		return postponenum;
	}
	public String getPostpones() {
		return postpones;
	}
	public void setPostpones(String postpones) {
		this.postpones = postpones;
	}
	public void setPostponenum(int postponenum) {
		this.postponenum = postponenum;
	}
	
	
	
}
