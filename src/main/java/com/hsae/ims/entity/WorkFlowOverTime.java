package com.hsae.ims.entity;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hsae.ims.config.Date1Serializer;
import com.hsae.ims.config.TimeSerializer;
import com.hsae.ims.entity.osworkflow.Wfentry;
@Entity
@Table(name = "ims_workflow_overtime")
public class WorkFlowOverTime {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@OneToMany( mappedBy = "workFlowOverTime",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private List<WorkFlowOverTimeDetail> workFlowOverTimeDetail;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Wfentry wfentry;
	/**录入时间*/
	@JsonSerialize(using = TimeSerializer.class)
	private  Timestamp saveTime;
	@JsonSerialize(using = Date1Serializer.class)
	private Date applyDate;
	
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	@Transient
	private String step;
	
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
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
	public List<WorkFlowOverTimeDetail> getWorkFlowOverTimeDetail() {
		return workFlowOverTimeDetail;
	}
	public void setWorkFlowOverTimeDetail(List<WorkFlowOverTimeDetail> workFlowOverTimeDetail) {
		this.workFlowOverTimeDetail = workFlowOverTimeDetail;
		for(int i=0;i<workFlowOverTimeDetail.size();i++){
			workFlowOverTimeDetail.get(i).setWorkFlowOverTime(this);
		}
	}
	public Wfentry getWfentry() {
		return wfentry;
	}
	public void setWfentry(Wfentry wfentry) {
		this.wfentry = wfentry;
	}
	public Timestamp getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(Timestamp saveTime) {
		this.saveTime = saveTime;
	}
	
}
