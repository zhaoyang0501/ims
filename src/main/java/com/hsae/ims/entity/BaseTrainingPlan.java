package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/***
 * 基础培训计划表
 * 
 * @author caowei
 *
 */
@Entity
@Table(name = "ims_basetraining_plan")
public class BaseTrainingPlan implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/* 计划标题 */
	private String title;
	/* 计划描述 */
	@Lob
	private String description;
	/* 培训目标 */
	private String targets;
	/* 培训课程 */
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<BaseTrainingCourse> courses; 
	/* 参训人员 */
	@ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<User> users;
	/* 开始日期 */
	@Temporal(TemporalType.DATE)
	private Date start;
	/* 结束日期 */
	@Temporal(TemporalType.DATE)
	private Date end;
	/* 创建日期 */
	@Temporal(TemporalType.DATE)
	private Date createDate;
	/* 最近更新日期 */
	@Temporal(TemporalType.DATE)
	private Date lastupdate;
	/* 最近更新人 */
	private Long lastupdater;
	@Lob
	private String remarks;
	@Transient
	private String state;
	@Transient
	private Set<String> courseName;
	@Transient
	private List<String> user;

	public BaseTrainingPlan() {
		lastupdate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public Long getLastupdater() {
		return lastupdater;
	}

	public void setLastupdater(Long lastupdater) {
		this.lastupdater = lastupdater;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTargets() {
		return targets;
	}

	public void setTargets(String targets) {
		this.targets = targets;
	}

	public Set<String> getCourseName() {
		return courseName;
	}

	public void setCourseName(Set<String> courseName) {
		this.courseName = courseName;
	}

	public List<String> getUser() {
		return user;
	}

	public void setUser(List<String> user) {
		this.user = user;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<BaseTrainingCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<BaseTrainingCourse> courses) {
		this.courses = courses;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
