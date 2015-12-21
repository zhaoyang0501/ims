package com.hsae.ims.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.hsae.ims.entity.osworkflow.Wfentry;

@Entity
@Table(name = "ims_workflow_absentee")
public class WorkflowAbsentee implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<WorkflowAbsenteeDetails> absentee;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	
	@Transient
	private String step;
	
	@Temporal(TemporalType.DATE)
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wfentry getWfentry() {
		return wfentry;
	}

	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<WorkflowAbsenteeDetails> getAbsentee() {
		return absentee;
	}

	public void setAbsentee(List<WorkflowAbsenteeDetails> absentee) {
		this.absentee = absentee;
	}

}
