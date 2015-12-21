package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;

/**
 * 
 * @author zhaozhou 档案信息。
 *
 */

@Entity
@Table(name = "ims_user_resume")
public class UserResume implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// 创建人。
	@OneToOne(fetch = FetchType.EAGER)
	private User user;

	// 籍贯。
	private String place;

	// 民族。
	private String nation;

	// 婚否 0:未婚 1:已婚。
	private int marrige = 0;

	// 照片。
	private String picture;

	// 政治面貌。 1：党员 2：团员 3：群众。
	private Integer politicsStatus = 0;

	// 户口所在地。
	private String domicilePlace;

	// 身份证号码。
	private String idNumber;

	// 身份证地址。
	private String idAddress;

	// 学历。
	private Integer education = 0;

	// 学位。
	private Integer degree = 0;

	// 专业 。
	private String major;

	// 毕业学校。
	private String school;

	// 地址。
	private String address;

	// 手机。
	private String mobile;

	// 语言
	private String language;

	// 生日。
	@Temporal(TemporalType.DATE)
	private Date birthday;

	// 毕业时间。
	@Temporal(TemporalType.DATE)
	private Date graduateTime;

	// 入职时间。
	@Temporal(TemporalType.DATE)
	private Date joinTime;

	// 转证时间。
	@Temporal(TemporalType.DATE)
	private Date conversionTime;

	// 岗位。
	private Integer post = 0;

	// 职等。
	private Integer grade = 0;

	// 职位。
	private Integer position = 0;

	// 职称。
	private Integer title = 0;

	// 合同开始时间。
	@Temporal(TemporalType.DATE)
	private Date contractStartDate;

	// 合同结束时间。
	@Temporal(TemporalType.DATE)
	private Date contractEndDate;

	// 社保金额
	private Double socialMoney = 0.0;

	// 社保类型
	private Integer socialSecurityType = 0;

	// 公积金金额
	private Double publicMoney = 0.0;

	// 公积金类型
	private Integer publicMoneyType = 0;

	// 工作年限
	private String workYear = "0";

	// 兴趣爱好
	@Lob
	private String hobbies;

	// 技能擅长。
	@Lob
	private String skill;


	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public int getMarrige() {
		return marrige;
	}

	public void setMarrige(int marrige) {
		this.marrige = marrige;
	}

	public Integer getPoliticsStatus() {
		return politicsStatus;
	}

	public void setPoliticsStatus(Integer politicsStatus) {
		this.politicsStatus = politicsStatus;
	}

	public String getDomicilePlace() {
		return domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public Integer getEducation() {
		return education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(Date graduateTime) {
		this.graduateTime = graduateTime;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getConversionTime() {
		return conversionTime;
	}

	public void setConversionTime(Date conversionTime) {
		this.conversionTime = conversionTime;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getTitle() {
		return title;
	}

	public void setTitle(Integer title) {
		this.title = title;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	@JsonSerialize(using = Date1Serializer.class)
	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public Double getSocialMoney() {
		return socialMoney;
	}

	public void setSocialMoney(Double socialMoney) {
		this.socialMoney = socialMoney;
	}

	public Integer getSocialSecurityType() {
		return socialSecurityType;
	}

	public void setSocialSecurityType(Integer socialSecurityType) {
		this.socialSecurityType = socialSecurityType;
	}

	public Double getPublicMoney() {
		return publicMoney;
	}

	public void setPublicMoney(Double publicMoney) {
		this.publicMoney = publicMoney;
	}

	public Integer getPublicMoneyType() {
		return publicMoneyType;
	}

	public void setPublicMoneyType(Integer publicMoneyType) {
		this.publicMoneyType = publicMoneyType;
	}

	public String getWorkYear() {
		return workYear;
	}

	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

}
