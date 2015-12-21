package com.hsae.ims.entity.osworkflow;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hsae.ims.entity.User;
@Entity
@Table(name = "os_wfentry_extend")
public class WfentryExtend {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	/**创建人*/
	@ManyToOne(fetch = FetchType.EAGER)
	private User creater;
	
	/**文件名称*/
	private String title;
	
	/**紧急程度*/
	private Integer degree;
	
	/**序号*/
	private String sn;
	
	@JsonIgnore
	@ManyToOne(optional = true, cascade = CascadeType.ALL)  
    @JoinColumn(name = "entry_id",referencedColumnName="id")  
	private Wfentry wfentry;
	
	public Wfentry getWfentry() {
		return wfentry;
	}
	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCreater() {
		return creater==null?new User():creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getDegree() {
		return degree;
	}
	public void setDegree(Integer degree) {
		this.degree = degree;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
}
