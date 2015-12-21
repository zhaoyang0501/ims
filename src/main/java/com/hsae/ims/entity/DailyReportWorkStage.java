package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 工作阶段表
 * 
 * @author caowei
 */
@Entity
@Table(name = "ims_daily_Report_work_stage")
public class DailyReportWorkStage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/**
	 * 取值方法
	 * 0：默认，即按照部门用户部门来取工作阶段
	 * N：特殊类型中的一种，直接取这个type值
	 */
	private int type = 0;
	
	private Long deptId;
	
	private String stageName;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
