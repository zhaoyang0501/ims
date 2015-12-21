package com.hsae.ims.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="ims_system_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column()
	private long id;

	private String name;

	private String roledesc;
	
	@Transient
	private int operate;

	public Role() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoledesc() {
		return this.roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

}