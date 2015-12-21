package com.hsae.ims.entity.osworkflow;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "os_approval")
public class Approval {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private long id;
	private String approvel;
	private java.sql.Timestamp createDate;
	@ManyToOne(fetch = FetchType.EAGER)
	private HistoryStep step;
	private String action;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getApprovel() {
		return approvel;
	}
	public void setApprovel(String approvel) {
		this.approvel = approvel;
	}
	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}
	public HistoryStep getStep() {
		return step;
	}
	public void setStep(HistoryStep step) {
		this.step = step;
	}
	
}
