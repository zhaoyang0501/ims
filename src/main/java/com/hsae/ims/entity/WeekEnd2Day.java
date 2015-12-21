package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 调休工作表
 * @author wushang
 *
 */
@Entity
@Table(name = "ims_weekend2day")
public class WeekEnd2Day implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 时间(年月日)
	 */
	private String date;
}
