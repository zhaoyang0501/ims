package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 作息时间表
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_system_timetable")
public class TimetableSetting implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int month;
	
	/** 上午上班时间 **/
	private String foreworktime;
	
	/** 上午休息时间 **/
	private String foreresttime;
	
	/** 下午上班时间  **/
	private String afterworktime;
	
	/** 下午休息时间 **/
	private String afterresttime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public String getForeworktime() {
		return foreworktime;
	}

	public void setForeworktime(String foreworktime) {
		this.foreworktime = foreworktime;
	}

	public String getForeresttime() {
		return foreresttime;
	}

	public void setForeresttime(String foreresttime) {
		this.foreresttime = foreresttime;
	}

	public String getAfterworktime() {
		return afterworktime;
	}

	public void setAfterworktime(String afterworktime) {
		this.afterworktime = afterworktime;
	}

	public String getAfterresttime() {
		return afterresttime;
	}

	public void setAfterresttime(String afterresttime) {
		this.afterresttime = afterresttime;
	}
	
}
