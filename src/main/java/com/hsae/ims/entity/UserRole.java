package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the user2role database table.
 * 
 */
@Entity
@Table(name = "ims_system_user_role")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long roleid;

	private long userId;

	@Transient
	private String rolename;

	@Transient
	private String userchinesename;

	@Transient
	private String userempnumber;

	@Transient
	private boolean checked;
	
	@Transient
	private int operate;

	public UserRole() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRoleid() {
		return this.roleid;
	}

	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUserchinesename() {
		return userchinesename;
	}

	public void setUserchinesename(String userchinesename) {
		this.userchinesename = userchinesename;
	}

	public String getUserempnumber() {
		return userempnumber;
	}

	public void setUserempnumber(String userempnumber) {
		this.userempnumber = userempnumber;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

}