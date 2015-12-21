package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  图书借阅记录。
 *
 */

public class BookLendRecordDTO {
	
	private String lendId;
	
	private String lendBookName;
	
	private String lendUserName;
	
	//借阅方式。0正常。1长期。
	private int lendType;
	
	//借出时间。
	private String lendTime;
	
	//是否超限。 0:未超限 1：超限
	private int isOverLimit;
	
	//应还时间。
	private String returnTime;
	
	//超限金额
	private String money;
	
	//续借应还时间。
	private String expectTime;
	
	//创建人。
	private String createName;
	
	//创建日期。
	private String createDate;

	//标识位 0:已归还 1：未归还
	private int identifying;


	public int getLendType() {
		return lendType;
	}

	public void setLendType(int lendType) {
		this.lendType = lendType;
	}

	public String getLendId() {
		return lendId;
	}

	public void setLendId(String lendId) {
		this.lendId = lendId;
	}


	public String getLendBookName() {
		return lendBookName;
	}

	public void setLendBookName(String lendBookName) {
		this.lendBookName = lendBookName;
	}

	public String getLendUserName() {
		return lendUserName;
	}

	public void setLendUserName(String lendUserName) {
		this.lendUserName = lendUserName;
	}

	public String getLendTime() {
		return lendTime;
	}

	public void setLendTime(String lendTime) {
		this.lendTime = lendTime;
	}

	public int getIsOverLimit() {
		return isOverLimit;
	}

	public void setIsOverLimit(int isOverLimit) {
		this.isOverLimit = isOverLimit;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(String expectTime) {
		this.expectTime = expectTime;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getIdentifying() {
		return identifying;
	}

	public void setIdentifying(int identifying) {
		this.identifying = identifying;
	}
	
	
	
	
}