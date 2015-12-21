package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  档案信息。
 *
 */

public class UserResumeDTO {
	
	private long id;
	
	private String userId;
	private String userName;
	private String empnumber;
	private String sex;
	private long sexId;
	private long deptId;
	private int marrigeId;
	private String dept;
	private String email;
	//离职在职状态。0.在职。1.离职。
	private Integer state;

	
	//籍贯。
	private String place;

	//民族。
	private String nation;
	
	//婚否  0:未婚 1:已婚。
	private String marrige ;
	
	//照片。
	private String picture;
	
	//政治面貌。
	private String politicsStatus;
	
	//户口所在地。
	private String domicilePlace;
	
	//身份证号码。
	private String idNumber ;
	
	//身份证地址。
	private String idAddress;
	
	//学历。
	private String education;
	
	//学位。
	private String degree;
	
	//专业 。
	private String major;
	
	//毕业学校 。
	private String school;
	
	//地址。
	private String address;
	
	//手机。
	private String mobile;
	
	//语言
	private String language;
	
	//生日。
	private String birthday;
	
	//毕业时间。
	private String graduateTime;
	
	//入职时间。
	private String joinTime;
	
	//转证时间。
	private String conversionTime;
	
	//岗位。
	private String post;
	
	//职等。
	private String grade;
	
	//职位。
	private String position;
	
	//职称。
	private String title;
	
	//合同开始时间。
	private String contractStartDate;
	
	//合同结束时间。
	private String contractEndDate;
	
	//社保金额
	private double socialMoney;
	
	//社保类型
	private String socialSecurityType; 
	
	//公积金金额
	private double publicMoney;
	
	//公积金类型
	private String publicMoneyType; 
	
	//工作年限
	private String workYear;
	
	//兴趣爱好
	private String hobbies;
	 
	//技能擅长。
	private String skill;
	
	//离职时间。
	private String departureTime;
	
	//离职原因。
	private String leavingReasons;
	

	
	public String getSchool() {
		return school;
	}


	public void setSchool(String school) {
		this.school = school;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public Long getSexId() {
		return sexId;
	}


	public void setSexId(Long sexId) {
		this.sexId = sexId;
	}


	public Long getDeptId() {
		return deptId;
	}


	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}


	public int getMarrigeId() {
		return marrigeId;
	}


	public void setMarrigeId(int marrigeId) {
		this.marrigeId = marrigeId;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmpnumber() {
		return empnumber;
	}


	public void setEmpnumber(String empnumber) {
		this.empnumber = empnumber;
	}


	public String getSex() {
		return sex;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
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


	public String getMarrige() {
		return marrige;
	}


	public void setMarrige(String marrige) {
		this.marrige = marrige;
	}


	public String getPoliticsStatus() {
		return politicsStatus;
	}


	public void setPoliticsStatus(String politicsStatus) {
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


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
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

	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGraduateTime() {
		return graduateTime;
	}

	public void setGraduateTime(String graduateTime) {
		this.graduateTime = graduateTime;
	}

	public String getJoinTime() {
		return joinTime;
	}


	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public String getConversionTime() {
		return conversionTime;
	}


	public void setConversionTime(String conversionTime) {
		this.conversionTime = conversionTime;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}


	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}


	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}


	public double getSocialMoney() {
		return socialMoney;
	}


	public void setSocialMoney(double socialMoney) {
		this.socialMoney = socialMoney;
	}


	public String getSocialSecurityType() {
		return socialSecurityType;
	}


	public void setSocialSecurityType(String socialSecurityType) {
		this.socialSecurityType = socialSecurityType;
	}


	public double getPublicMoney() {
		return publicMoney;
	}


	public void setPublicMoney(double publicMoney) {
		this.publicMoney = publicMoney;
	}


	public String getPublicMoneyType() {
		return publicMoneyType;
	}


	public void setPublicMoneyType(String publicMoneyType) {
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

	public String getDepartureTime() {
		return departureTime;
	}


	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}


	public String getLeavingReasons() {
		return leavingReasons;
	}


	public void setLeavingReasons(String leavingReasons) {
		this.leavingReasons = leavingReasons;
	}
	
}
