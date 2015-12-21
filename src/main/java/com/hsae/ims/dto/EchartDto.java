package com.hsae.ims.dto;

public class EchartDto {
	private String name;
	private Long value;
	public EchartDto(Object name,Object count){
		this.name=(String) name;
		this.value=(Long) count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	
}
