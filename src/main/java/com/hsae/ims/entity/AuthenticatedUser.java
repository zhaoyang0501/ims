package com.hsae.ims.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class AuthenticatedUser  extends User{
	private static final long serialVersionUID = 1L;
	private List<Rights> allrights;
	private long userid;
	private String chinesename;
	
	public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
	        super(username, password, authorities);
	 }
	
	public List<Rights> getAllrights() {
		return allrights;
	}
	public void setAllrights(List<Rights> allrights) {
		this.allrights = allrights;
	}
	
	public boolean haveRight(Rights r){
		if (allrights == null) return false;
		
		return allrights.contains(r);
	}
	
	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
}