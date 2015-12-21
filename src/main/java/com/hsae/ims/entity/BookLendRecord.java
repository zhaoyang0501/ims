package com.hsae.ims.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author zhaozhou
 *  图书借阅记录。
 *
 */

@Entity
@Table(name="ims_book_lendrecord")
public class BookLendRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private BookInfo bookId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User userId;
	
	//借出时间。
	private Timestamp lendTime;
	
	//是否超限。 0:未超限 1：超限
	private int isOverLimit;
	
	//归还时间。
	private Timestamp returnTime;
	
	//超限金额
	private String money;
	
	//续借应还时间。
	private Timestamp expectTime;
	
	//借出类型。 0：正常。1：长期。
	private int lendType;
	
	//创建人。
	@ManyToOne(fetch = FetchType.EAGER)
	private User createName;
	
	//创建日期。
	private Timestamp createDate;
	
	//标识位 0:已归还 1：未归还
	private int identifying;
	
	//是否续借  0:未续借 1：已续借。
	private int renew;
	
	
	public int getLendType() {
		return lendType;
	}

	public void setLandType(int lendType) {
		this.lendType = lendType;
	}

	public int getRenew() {
		return renew;
	}

	public void setRenew(int renew) {
		this.renew = renew;
	}

	public int getIdentifying() {
		return identifying;
	}

	public void setIdentifying(int identifying) {
		this.identifying = identifying;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BookInfo getBookId() {
		return bookId;
	}

	public void setBookId(BookInfo bookId) {
		this.bookId = bookId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Timestamp getLendTime() {
		return lendTime;
	}

	public void setLendTime(Timestamp lendTime) {
		this.lendTime = lendTime;
	}

	public int getIsOverLimit() {
		return isOverLimit;
	}

	public void setIsOverLimit(int isOverLimit) {
		this.isOverLimit = isOverLimit;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public Timestamp getExpectTime() {
		return expectTime;
	}

	public void setExpectTime(Timestamp expectTime) {
		this.expectTime = expectTime;
	}

	public User getCreateName() {
		return createName;
	}

	public void setCreateName(User createName) {
		this.createName = createName;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

}