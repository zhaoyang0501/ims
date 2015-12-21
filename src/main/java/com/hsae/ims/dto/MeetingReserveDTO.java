package com.hsae.ims.dto;



import java.util.Date;

/**
 * 
 * @author zhaozhou
 *  预约信息。
 *
 */

public class MeetingReserveDTO  {
	
	private Long id;
	
	private Long roomId;


	//预约是否开会过（过期前是否审核通过）0：未生效。 1：已生效。
	private int flag = 0;
	
	//是否通知参会人员。 0：未设提醒。 1：通知开会。2：会议取消
	private int inform = 0;
	
	//允许编辑会议纪要标识位。 0:不允许。 1：允许编辑。
	private int identifying = 0;
	
	private String meetingName;
	
	private int meetingType = 0;
	
	private String roomName;
	
	private String meetingServeName;
	
	public String getMeetingServeName() {
		return meetingServeName;
	}

	public void setMeetingServeName(String meetingServeName) {
		this.meetingServeName = meetingServeName;
	}

	private String meetingDescription;
	
	private String typeValue;
	private String compereName;
	private String initiatorName;
	private String registrarName;
	private String attendeeName;
	private Date startTime;
	private Date endTime;
	private int sum;
	//会议周期性。0：日预约。1：周预约。
	private int meetingPeriodic = 0;
	
	//会议开始日期。
	private String meetingStartDate;
	
	//会议开始时间。
	private String meetingStartTime;
	
	//会议结束时间。
	private String meetingEndTime;
	
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
	
	//会议文档。
	private String meetingFile;
	
	//纪要文档。
	private String meetingSummaryFile;
	
	//会议纪要。
	private String meetingSummary;
	
	//会议纪要创建时间。
	private String summaryCreateTime;
	
	//会议状态 。0:未预约。 1：已提交。 2：已审核。 3：已开始。 4：已结束。
	private int meetingStatus = 0;
	
	//会议创建时间。
	private String meetingCreateTime;

	public int getInform() {
		return inform;
	}

	public void setInform(int inform) {
		this.inform = inform;
	}
	
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	public int getIdentifying() {
		return identifying;
	}

	public void setIdentifying(int identifying) {
		this.identifying = identifying;
	}

	public String getSummaryCreateTime() {
		return summaryCreateTime;
	}

	public void setSummaryCreateTime(String summaryCreateTime) {
		this.summaryCreateTime = summaryCreateTime;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getInitiatorName() {
		return initiatorName;
	}

	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}

	public String getRegistrarName() {
		return registrarName;
	}

	public void setRegistrarName(String registrarName) {
		this.registrarName = registrarName;
	}

	public String getAttendeeName() {
		return attendeeName;
	}

	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
	}

	public String getCompereName() {
		return compereName;
	}

	public void setCompereName(String compereName) {
		this.compereName = compereName;
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

	public void setMeetingType(int meetingType) {
		this.meetingType = meetingType;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getMeetingDescription() {
		return meetingDescription;
	}

	public void setMeetingDescription(String meetingDescription) {
		this.meetingDescription = meetingDescription;
	}

	public int getMeetingPeriodic() {
		return meetingPeriodic;
	}

	public void setMeetingPeriodic(int meetingPeriodic) {
		this.meetingPeriodic = meetingPeriodic;
	}

	public String getMeetingStartTime() {
		return meetingStartTime;
	}

	public void setMeetingStartTime(String meetingStartTime) {
		this.meetingStartTime = meetingStartTime;
	}

	public String getMeetingEndTime() {
		return meetingEndTime;
	}

	public void setMeetingEndTime(String meetingEndTime) {
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

	public String getAttendee() {
		return attendee;
	}

	public void setAttendee(String attendee) {
		this.attendee = attendee;
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

	public int getMeetingStatus() {
		return meetingStatus;
	}

	public void setMeetingStatus(int meetingStatus) {
		this.meetingStatus = meetingStatus;
	}

	public String getMeetingCreateTime() {
		return meetingCreateTime;
	}

	public void setMeetingCreateTime(String meetingCreateTime) {
		this.meetingCreateTime = meetingCreateTime;
	}

	public String getMeetingStartDate() {
		return meetingStartDate;
	}

	public void setMeetingStartDate(String meetingStartDate) {
		this.meetingStartDate = meetingStartDate;
	}
}
