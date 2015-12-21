package com.hsae.ims.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * 
 * @author zhaozhou
 *  预约信息。
 *
 */

@Entity
@Table(name="ims_meeting_reserve")
public class MeetingReserve implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String meetingName;
	
	private Integer meetingType = 0;
	
	//预约是否开会过（过期前是否审核通过）0：未生效。 1：已生效。
	private Integer flag = 0;
	
	//是否通知参会人员。 0：未设提醒。 1：通知开会。2：会议取消
	private Integer inform = 0;
	
	public Integer getInform() {
		return inform;
	}

	public void setInform(Integer inform) {
		this.inform = inform;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	//预约会议室。
	@ManyToOne(fetch = FetchType.EAGER)
	private MeetingRoom room;
	
	//会议描述。
	@Lob
	private String meetingDescription;
	
	//会议周期性。0：日预约。1：周预约。
	private Integer meetingPeriodic = 0;
	
//	//会议开始日期。
//	@Temporal(TemporalType.DATE)
//	private Date meetingStartDate;
//	
//	//会议结束日期。
//	@Temporal(TemporalType.DATE)
//	private Date meetingEndDate;

	//会议开始时间。
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingStartTime;
	
	//会议结束时间。
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingEndTime;
	
	//会议服务。(矿泉水、水果)
	private String meetingServe;
	
	//会议召集人。
	private Long initiator;
	
	//会议主持人。
	private Long compere;
	
	//会议纪要人。
	private Long registrar;
	
	//参会人员。
	private String attendee;
	
	public String getAttendee() {
		return attendee;
	}

	public void setAttendee(String attendee) {
		this.attendee = attendee;
	}

	//会议文档。
	private String meetingFile;
	
	//纪要文档。
	private String meetingSummaryFile;
	
	//会议纪要。
	@Lob
	private String meetingSummary;
	
	//会议纪要创建时间。
	@Temporal(TemporalType.TIMESTAMP)
	private Date summaryCreateTime;
	
	//会议状态 。0:未预约。 1：已提交。 2：已审核。 3：已开始。 4：已结束。5:已作废。
	private Integer meetingStatus = 0;
	
	//会议创建时间。
	@Temporal(TemporalType.TIMESTAMP)
	private Date meetingCreateTime;

	public Date getSummaryCreateTime() {
		return summaryCreateTime;
	}

	public void setSummaryCreateTime(Date summaryCreateTime) {
		this.summaryCreateTime = summaryCreateTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public int getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(Integer meetingType) {
		this.meetingType = meetingType;
	}

	public MeetingRoom getRoom() {
		return room;
	}

	public void setRoom(MeetingRoom room) {
		this.room = room;
	}

	public String getMeetingDescription() {
		return meetingDescription;
	}

	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}

	public Integer getMeetingPeriodic() {
		return meetingPeriodic;
	}

	public void setMeetingPeriodic(Integer meetingPeriodic) {
		this.meetingPeriodic = meetingPeriodic;
	}

	public Date getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(Date meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	public Date getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(Date meetingEndTime) {
		this.meetingEndTime = meetingEndTime;
	}

	public String getMeetingServe() {
		return meetingServe;
	}

	public void setMeetingServe(String meetingServe) {
		this.meetingServe = meetingServe;
	}

	public Long getInitiator() {
		return initiator;
	}

	public void setInitiator(Long initiator) {
		this.initiator = initiator;
	}

	public Long getCompere() {
		return compere;
	}

	public void setCompere(Long compere) {
		this.compere = compere;
	}

	public Long getRegistrar() {
		return registrar;
	}

	public void setRegistrar(Long registrar) {
		this.registrar = registrar;
	}

	public String getMeetingFile() {
		return meetingFile;
	}

	public void setMeetingFile(String meetingFile) {
		this.meetingFile = meetingFile;
	}

	public String getMeetingSummaryFile() {
		return meetingSummaryFile;
	}

	public void setMeetingSummaryFile(String meetingSummaryFile) {
		this.meetingSummaryFile = meetingSummaryFile;
	}

	public String getMeetingSummary() {
		return meetingSummary;
	}

	public void setMeetingSummary(String meetingSummary) {
		this.meetingSummary = meetingSummary;
	}

	public Integer getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(Integer meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public Date getMeetingCreateTime() {
		return meetingCreateTime;
	}

	public void setMeetingCreateTime(Date meetingCreateTime) {
		this.meetingCreateTime = meetingCreateTime;
	}
	
	
}
