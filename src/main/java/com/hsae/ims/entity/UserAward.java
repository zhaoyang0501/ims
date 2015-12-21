package com.hsae.ims.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;


/**
 * 
 * @author zhaozhou
 *  奖惩信息。
 *
 */

@Entity
@Table(name="ims_user_award")
public class UserAward implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	//奖惩时间。
	@Temporal(TemporalType.DATE)
	private Date awardDate;

	//奖惩类型: 1....
	private String awardType;
	
	//奖惩原因。
	@Lob
	private String awardReason;
	
	//奖惩内容。
	@Lob
	private String awardContent;
	
	//标识位：0：奖励 1：惩罚。
	private int identifier;
	
	//简历编号。
	private long resume;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(Date awardDate) {
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

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}

	public Long getResume() {
		return resume;
	}

	public void setResume(Long resume) {
		this.resume = resume;
	}

}
