package com.hsae.ims.dto;


/**
 * 
 * @author zhaozhou
 *  图书信息。
 *
 */

public class BookInfoDTO {
	
	
	private String id;
	
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
	private String description;
	
	//状态。 0:正常  1：借出。
	private int status;
	
	//创建人。
	private String createName;
	
	//创建日期。
	private String createDate;
	
	//借书人。
	private String lendUser;
	
	
	public String getLendUser() {
		return lendUser;
	}

	public void setLendUser(String lendUser) {
		this.lendUser = lendUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getPrice() {
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

}
