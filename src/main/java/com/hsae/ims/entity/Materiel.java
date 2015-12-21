package com.hsae.ims.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * 
 * @author zhaozhou
 *  物料表。
 *
 */

@Entity
@Table(name="ims_materiel")
public class Materiel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//物料id
	private Long materielId;
	
	//物料部门
	@ManyToOne(fetch = FetchType.EAGER)
	private Deptment dept;
	
	//物料名称
	private String materielName;
	
	//物料类型
	private Long materielType;
	
	//物料价格
	private String materielPrice;
	
	//创建时间
	@Temporal(TemporalType.DATE)
	private Date materielCreateDate;
	
	//物料状态
	private int materielStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Deptment getDept() {
		return dept;
	}

	public void setDept(Deptment dept) {
		this.dept = dept;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public Long getMaterielType() {
		return materielType;
	}

	public void setMaterielType(Long materielType) {
		this.materielType = materielType;
	}

	public String getMaterielPrice() {
		return materielPrice;
	}

	public void setMaterielPrice(String materielPrice) {
		this.materielPrice = materielPrice;
	}

	public Date getMaterielCreateDate() {
		return materielCreateDate;
	}

	public void setMaterielCreateDate(Date materielCreateDate) {
		this.materielCreateDate = materielCreateDate;
	}

	public int getMaterielStatus() {
		return materielStatus;
	}

	public void setMaterielStatus(int materielStatus) {
		this.materielStatus = materielStatus;
	}
	
	
}
