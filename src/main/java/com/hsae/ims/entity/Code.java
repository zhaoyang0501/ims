package com.hsae.ims.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ims_system_code")
	
public class Code implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**标识*/
	private String identification;

	/**
	 * 名称描述
	 */
	private String name;
	
	/**代码值*/
	private String code;
	/**
	 * 排序 值越小排在前面
	 */
	private Integer sortNumber;
	
	/**
	 * 父类ID
	 * 
	 */
	private Long pId = 0l;

	/**
	 * 是否可用 0:不可用	 1:不可用
	 */
	private Integer tag;
	
	private String remarks;
	
	private Timestamp saveTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Integer getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getSaveTime() {
		return saveTime;
	}

	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
