package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ims_system_project_buglist")
public class ProjectBuglist implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String bugName;
	
	/**
	 * 发行版
	 */
	private String version;
	
	/**
	 * 输入源
	 * 设计调试、设计验证、试装反馈、外部测试、生产反馈、客户反馈、其他
	 */
	private String source;
	
	/**
	 * 频度
	 */
	private String frequency;
	
	private String complex;
	
	/**
	 * 等级：A+,A,B,C
	 */
	private String grade;
	
	/**
	 * 测试人员
	 */
	private String tester;
	
	@Temporal(TemporalType.DATE)
	private Date testDate;
	
	private String creater;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@Lob
	private String description;
}
