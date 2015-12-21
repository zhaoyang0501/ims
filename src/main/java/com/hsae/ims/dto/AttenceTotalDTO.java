package com.hsae.ims.dto;


public class AttenceTotalDTO {
	
	private double overtime;
	
	private double dayoff;
	
	private double total;
	
	private String brushDate;

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public double getDayoff() {
		return dayoff;
	}

	public void setDayoff(double dayoff) {
		this.dayoff = dayoff;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getBrushDate() {
		return brushDate;
	}

	public void setBrushDate(String brushDate) {
		this.brushDate = brushDate;
	}
	
	

}
