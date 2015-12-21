package com.hsae.ims.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * 
 * @author zhaozhou
 *  图书信息。
 *
 */

@Entity
@Table(name="ims_book_info")
public class BookInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//图书编号。
	private String code;

	//图书名称。
	private String bookName;
	
	//图书价格。
	private String price;
	
	//作者。
	private String author;
	
	//出版社。
	private String publish;
	
	//描述。
	@Lob
	private String description;
	
	//状态。 0:正常  1：借出 2:长期。
	private int status;
	
	//创建人。
	@ManyToOne(fetch = FetchType.EAGER)
	private User createName;
	
	//创建日期。
	private Timestamp createDate;

	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	
	public String getPrice(){
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
