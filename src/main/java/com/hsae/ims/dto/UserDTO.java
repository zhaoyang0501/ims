package com.hsae.ims.dto;

/**
 * 
 * @author zhaozhou
 *  用户信息。
 *
 */

public class UserDTO {
	
	
	private String id;
	private String username;
	private String chinesename;
	private String empnumber;
	private String dept;
	private Long deptid;
	private Integer positionid;
	//职位。
	private String position;
	private String sex;
	private String email;
	//离职在职状态。0.正常。1.冻结。
	private Integer freeze;
	
	/**
	 * 是否需要提交周报
	 */
	private int weekreport = 1;
	/**
	 * 是否参加培训
	 */
	private int train = 1;
	/**
	 * 是否参加考勤
	 */
	private int attendance = 1;
	
	private String authorityScope;
	
	private String authorityCode;
	
	
	
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public Integer getPositionid() {
		return positionid;
	}
	public void setPositionid(Integer positionid) {
		this.positionid = positionid;
	}
	public String getAuthorityCode() {
		return authorityCode;
	}
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}
	public int getWeekreport() {
		return weekreport;
	}
	public void setWeekreport(int weekreport) {
		this.weekreport = weekreport;
	}
	public int getTrain() {
		return train;
	}
	public void setTrain(int train) {
		this.train = train;
	}
	public int getAttendance() {
		return attendance;
	}
	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}
	public String getAuthorityScope() {
		return authorityScope;
	}
	public void setAuthorityScope(String authorityScope) {
		this.authorityScope = authorityScope;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getChinesename() {
		return chinesename;
	}
	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}
	public String getEmpnumber() {
		return empnumber;
	}
	public void setEmpnumber(String empnumber) {
		this.empnumber = empnumber;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getFreeze() {
		return freeze;
	}
	public void setFreeze(Integer freeze) {
		this.freeze = freeze;
	}

}