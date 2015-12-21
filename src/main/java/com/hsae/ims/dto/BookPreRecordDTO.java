package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  图书预约记录。
 *
 */

public class BookPreRecordDTO {
	
	private String preId;
	
	private String index;
	
	private String preBookName;
	
	private String preUserName;
	
	//预约时间。
	private String preTime;

	public String getPreId() {
		return preId;
	}

	public void setPreId(String preId) {
		this.preId = preId;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getPreBookName() {
		return preBookName;
	}

	public void setPreBookName(String preBookName) {
		this.preBookName = preBookName;
	}

	public String getPreUserName() {
		return preUserName;
	}

	public void setPreUserName(String preUserName) {
		this.preUserName = preUserName;
	}

	public String getPreTime() {
		return preTime;
	}

	public void setPreTime(String preTime) {
		this.preTime = preTime;
	}
	
}