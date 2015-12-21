package com.hsae.ims.dto;



import java.util.Date;



/**
 * 
 * @author zhaozhou
 *  会议室表。
 *
 */

public class MeetingRoomDTO {
	
	private Long id;
	
	private String roomName;
	
	//会议室设备。
	private String materiel;
	
	//席位。
	private int	roomSeat;

	//地址。
	private String roomAddress;

	//描述。
	private String roomDescription;
	
	//创建时间。
	private Date roomCreateTime;
	  
	//状态。0：空闲中。1：预约中。
	private int roomStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getMateriel() {
		return materiel;
	}

	public void setMateriel(String materiel) {
		this.materiel = materiel;
	}

	public int getRoomSeat() {
		return roomSeat;
	}

	public void setRoomSeat(int roomSeat) {
		this.roomSeat = roomSeat;
	}

	public String getRoomAddress() {
		return roomAddress;
	}

	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}

	public Date getRoomCreateTime() {
		return roomCreateTime;
	}

	public void setRoomCreateTime(Date roomCreateTime) {
		this.roomCreateTime = roomCreateTime;
	}

	public int getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(int roomStatus) {
		this.roomStatus = roomStatus;
	}
	
}
