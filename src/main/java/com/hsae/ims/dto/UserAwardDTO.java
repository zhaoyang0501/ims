package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  奖惩信息。
 *
 */

public class UserAwardDTO{
	
	private long id;
	
	//奖惩时间。
	private String awardDate;

	//奖惩类型: 1....
	private String awardType;
	
	//奖惩原因。
	private String awardReason;
	
	//奖惩内容。
	private String awardContent;
	
	//标识位：0：奖励 1：惩罚。
	private String identifier;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}

	public String getAwardType() {
		return awardType;
	}

	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}

	public String getAwardReason() {
		return awardReason;
	}

	public void setAwardReason(String awardReason) {
		this.awardReason = awardReason;
	}

	public String getAwardContent() {
		return awardContent;
	}

	public void setAwardContent(String awardContent) {
		this.awardContent = awardContent;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

	

}
