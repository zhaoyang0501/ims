package com.hsae.ims.entity.osworkflow;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "os_wfentry")
public class Wfentry {
	@Id
	private Long id;
	private Integer state;
	private String name;
	@JsonIgnore
	@OneToOne(optional = false, mappedBy = "wfentry",fetch=FetchType.EAGER)
	private CurrentStep currentStep;

    @OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy="wfentry",fetch=FetchType.EAGER)
    private WfentryExtend wfentryExtend;
	
	@Fetch(FetchMode.JOIN)
	public CurrentStep getCurrentStep() {
		return currentStep;
	}
	public void setCurrentStep(CurrentStep currentStep) {
		this.currentStep = currentStep;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public WfentryExtend getWfentryExtend() {
		
		return wfentryExtend==null? new WfentryExtend(): wfentryExtend;
	}
	public void setWfentryExtend(WfentryExtend wfentryExtend) {
		this.wfentryExtend = wfentryExtend;
	}
	
}
