package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the user_rights database table.
 * 
 */
@Entity
@Table(name = "ims_system_user_rights")
public class UserRights implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "rights_id")
	private long rightsId;

	private long userId;
	@Transient
	private String userchinesename;

	@Transient
	private boolean fromrole;

	@Transient
	private String rightdesc;
	
	@Transient
	private int operate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRightsId() {
		return rightsId;
	}

	public void setRightsId(long rightsId) {
		this.rightsId = rightsId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserchinesename() {
		return userchinesename;
	}

	public void setUserchinesename(String userchinesename) {
		this.userchinesename = userchinesename;
	}

	public boolean isFromrole() {
		return fromrole;
	}

	public void setFromrole(boolean fromrole) {
		this.fromrole = fromrole;
	}

	public String getRightdesc() {
		return rightdesc;
	}

	public void setRightdesc(String rightdesc) {
		this.rightdesc = rightdesc;
	}
	
	

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public boolean equals(Object r) {
		if (this == r)
			return true;

		if (r instanceof UserRights) {
			return this.id == ((UserRights) r).id || (this.userId == ((UserRights) r).userId && this.rightsId == ((UserRights) r).rightsId);
		}

		return false;
	}
}