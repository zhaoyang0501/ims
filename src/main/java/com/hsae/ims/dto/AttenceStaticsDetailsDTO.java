package com.hsae.ims.dto;

import java.io.Serializable;

public class AttenceStaticsDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer index;
	
	private String type;
	
	private String date;
	
	private String details;
	
	private String description;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
