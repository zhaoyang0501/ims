package com.hsae.ims.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the role_rights database table.
 * 
 */
@Entity
@Table(name="ims_system_role_rights")

public class RoleRights implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="rights_id")
	private long rightsId;

	private long roleId;
	@Transient
	private String rolename;
	@Transient
	private String rightdesc;
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
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getRightdesc() {
		return rightdesc;
	}
	public void setRightdesc(String rightdesc) {
		this.rightdesc = rightdesc;
	}

}