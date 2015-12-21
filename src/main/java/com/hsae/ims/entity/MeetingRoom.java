package com.hsae.ims.entity;



import java.io.Serializable;
import java.util.Date;
import java.util.List;

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



/**
 * 
 * @author zhaozhou
 *  会议室表。
 *
 */

@Entity
@Table(name="ims_meeting_room")
public class MeetingRoom implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String roomName;
	
	//会议室设备。
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Materiel> materiel;
	
	//会议室负责人。
//	@OneToOne(fetch = FetchType.EAGER)
//	private User user;
	
	//席位。
	private int	roomSeat = 0;

	//地址。
	private String roomAddress;

	//描述。
	@Lob
	private String roomDescription;
	
	//创建时间。
	@Temporal(TemporalType.DATE)
	private Date roomCreateTime;
	  
	//状态。0：空闲中。1：预约中。
	private int roomStatus = 0;

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


	public List<Materiel> getMateriel() {
		return materiel;
	}

	public void setMateriel(List<Materiel> materiel) {
		this.materiel = materiel;
	}

	public Date getRoomCreateTime() {
		return roomCreateTime;
	}

	public void setRoomCreateTime(Date roomCreateTime) {
		this.roomCreateTime = roomCreateTime;
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

	public int getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(int roomStatus) {
		this.roomStatus = roomStatus;
	}
	
	
}
