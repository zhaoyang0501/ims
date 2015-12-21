package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "ims_system_user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String chinesename;

	@ManyToOne(fetch = FetchType.EAGER)
	private Deptment dept;

	private String email;

	private String empnumber;
	@JsonIgnore
	private String password;


	/**
	 * 1：男，2：女
	 */
	private String sex;

	private String username;

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
	
	/**
	 * 工作阶段是否按照部门来取
	 * 0：默认，按照部门去
	 * N：特殊取法,用workstage = DailyReportWorkStage.type方式来取
	 */
	private int workStage = 0;

	/**
	 * 0：未冻结 1：冻结
	 */
	private int freeze;
	
	private long creater;
	
	private Date createDate;
	
	@Transient
	private int operate = 0;
	/**权限范围*/
	private String authorityScope;
	/**权限代码*/
	private String authorityCode;
	@Persistent
	private String position;
	public User(){
	}
	public User(Long id){
		this.id=id;
	}
	
	public String getAuthorityScope() {
		return authorityScope;
	}

	public void setAuthorityScope(String authorityScope) {
		this.authorityScope = authorityScope;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChinesename() {
		return chinesename;
	}

	public void setChinesename(String chinesename) {
		this.chinesename = chinesename;
	}

	public Deptment getDept() {
		return dept;
	}

	public void setDept(Deptment dept) {
		this.dept = dept;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmpnumber() {
		return empnumber;
	}

	public void setEmpnumber(String empnumber) {
		this.empnumber = empnumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getFreeze() {
		return freeze;
	}

	public void setFreeze(int freeze) {
		this.freeze = freeze;
	}

	public int getOperate() {
		return operate;
	}

	public void setOperate(int operate) {
		this.operate = operate;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getWorkStage() {
		return workStage;
	}

	public void setWorkStage(int workStage) {
		this.workStage = workStage;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
