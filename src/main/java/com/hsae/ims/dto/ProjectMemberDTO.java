package com.hsae.ims.dto;

import java.io.Serializable;

/**
 * 项目成员
 * @author caowei
 *
 */
public class ProjectMemberDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	private String index;
	private String chinesename;
	private String role;
	private String operate = "0";
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
}
