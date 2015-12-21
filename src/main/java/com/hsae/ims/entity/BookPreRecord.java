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
 *  预约图书记录。
 *
 */

@Entity
@Table(name="ims_book_prerecord")
public class BookPreRecord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private BookInfo bookId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User userId;
	
	//预约时间。
	private Timestamp preTime;
	
	
	public Timestamp getPreTime() {
		return preTime;
	}

	public void setPreTime(Timestamp preTime) {
		this.preTime = preTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

}