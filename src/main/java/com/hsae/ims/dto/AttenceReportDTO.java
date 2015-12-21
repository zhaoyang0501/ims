package com.hsae.ims.dto;

public class AttenceReportDTO {
	
	private String id;
	
	private String dept; //部门
	
	private String ot_day;//平时加班
	
	private String ot_week;//周末加班
	
	private String df_sj;//事假
	
	private String df_cj;//产假
	
	private String df_prj;//哺乳假
	
	private String df_khj;//看护假
	
	private String df_tx;//调休
	
	private String	leak;//漏打卡人次

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getOt_day() {
		return ot_day;
	}

	public void setOt_day(String ot_day) {
		this.ot_day = ot_day;
	}

	public String getOt_week() {
		return ot_week;
	}

	public void setOt_week(String ot_week) {
		this.ot_week = ot_week;
	}

	public String getDf_sj() {
		return df_sj;
	}

	public void setDf_sj(String df_sj) {
		this.df_sj = df_sj;
	}

	public String getDf_cj() {
		return df_cj;
	}

	public void setDf_cj(String df_cj) {
		this.df_cj = df_cj;
	}

	public String getDf_prj() {
		return df_prj;
	}

	public void setDf_prj(String df_prj) {
		this.df_prj = df_prj;
	}

	public String getDf_khj() {
		return df_khj;
	}

	public void setDf_khj(String df_khj) {
		this.df_khj = df_khj;
	}

	public String getDf_tx() {
		return df_tx;
	}

	public void setDf_tx(String df_tx) {
		this.df_tx = df_tx;
	}

	public String getLeak() {
		return leak;
	}

	public void setLeak(String leak) {
		this.leak = leak;
	}
	
	

}
