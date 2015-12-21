package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 节假日表
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_system_workandholiday_setting")
public class WorkAndHolidaySetting implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	
	/**0:休息   1:上班（注：只要今天上一小时班都属于上班）**/
	private int type;
	
	/** 上午上班时间 **/
	private String foreworktime;
	
	/** 上午休息时间 **/
	private String foreresttime;
	
	/** 下午上班时间  **/
	private String afterworktime;
	
	/** 下午休息时间 **/
	private String afterresttime;
	
	private Date lastupate;
	
	private Long lastupdater;
	
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public Date getLastupate() {
		return lastupate;
	}

	public void setLastupate(Date lastupate) {
		this.lastupate = lastupate;
	}

	public Long getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(Long lastupdater) {
		this.lastupdater = lastupdater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
