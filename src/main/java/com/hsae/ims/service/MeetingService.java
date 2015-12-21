package com.hsae.ims.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hsae.ims.dto.MeetingReserveDTO;
import com.hsae.ims.entity.Materiel;
import com.hsae.ims.entity.MeetingReserve;
import com.hsae.ims.entity.MeetingRoom;
import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.repository.MaterielRepository;
import com.hsae.ims.repository.MeetingRoomRepository;
import com.hsae.ims.repository.MeetingReserveRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.MailUtil;
import com.hsae.ims.utils.RightUtil;


@Service
public class MeetingService {

	@Autowired
	private MeetingRoomRepository meetingRoomRepository;
	
	@Autowired
	private MaterielRepository materielRepository;
	
	@Autowired
	private MeetingReserveRepository meetingReserveRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private EventNoticationService eventNoticationService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleUserService roleUserService;
	
	
	private PageRequest buildPageRequest(String sSortDir_0, int pageNumber, int pagzSize) {
		List<String> list = new ArrayList<String>();
		list.add("id");
		Direction direction = Direction.ASC;
		if (StringUtils.isNotBlank(sSortDir_0)) { 
			list.add("roomName");
			if (sSortDir_0.toUpperCase().equals("DESC")) {
				direction = Direction.DESC;	
			}
		}
		Sort sort = new Sort(direction, list);
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 会议室列表。
	 * 
	 */
	public Page<MeetingRoom> getRoomInfoDate(String sSortDir_0 ,int pageNumber, int pageSize, String roomname, 
			String roomseat, String roomstatus ){
		PageRequest pageRequest = buildPageRequest(sSortDir_0, pageNumber, pageSize);
		Specification<MeetingRoom> spec = roomBuildSpecification(roomname, roomseat, roomstatus);
		this.updateRoomStatus();
		return (Page<MeetingRoom>) meetingRoomRepository.findAll(spec, pageRequest);
	}
	
	
	private Specification<MeetingRoom> roomBuildSpecification(final String roomname,final String roomseat,final String roomstatus){
		return new Specification<MeetingRoom>() {
			@Override
			public Predicate toPredicate(Root<MeetingRoom> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if (StringUtils.isNoneEmpty(roomname)) {
					predicate.getExpressions().add(cb.like(root.get("roomName").as(String.class), "%" + roomname + "%"));
				}
				if (StringUtils.isNoneEmpty(roomseat)) {
					predicate.getExpressions().add(cb.equal(root.get("roomSeat").as(String.class), roomseat));
				}
				if (StringUtils.isNoneEmpty(roomstatus)) {
					predicate.getExpressions().add(cb.equal(root.get("roomStatus").as(String.class), roomstatus));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 设置会议室状态空闲。
	 */
	public void updateRoomStatus (){
		int count = 0;
		List<MeetingRoom> rm = (List<MeetingRoom>) meetingRoomRepository.findAll();
		if (rm != null){
			for (MeetingRoom room : rm){
				List<MeetingReserve> rs = meetingReserveRepository.findReserveByRoom(room);
				if (rs != null){
					for (MeetingReserve reserve :rs){
						if (reserve.getMeetingStatus() != 4){
							continue;
						}else{
							count++;
						}
					}
					if (count == rs.size()){
						room.setRoomStatus(0);//如果预约均为已结束，那么会议室为空闲。
						meetingRoomRepository.updateState(room.getId(), room.getRoomStatus());
					}
					
				}
			}
		}
	}

	/**
	 * 保存会议室。
	 */
	public Integer saveRoom (MeetingRoom meetingroom, String materiel){
		if (meetingroom.getRoomStatus() == 1){
			return 3;
		}
		
		List<Materiel> list = new ArrayList<Materiel>();
		if (StringUtils.isNoneBlank(materiel)){
			for (String m : materiel.split(",")){
				list.add(materielRepository.findOne(Long.valueOf(m)));
			}
		}
		
		if (StringUtils.isNotBlank(meetingroom.getRoomName()) && meetingroom.getId() == null){
			for (MeetingRoom room : meetingRoomRepository.findAll()){
				if (room.getRoomName().equals(meetingroom.getRoomName())){
					return 2;
				}
			}
		}
		
		if (meetingroom.getId() == null){
			meetingroom.setRoomStatus(0);
		}
		
		meetingroom.setMateriel(list);
		meetingroom.setRoomCreateTime(new Timestamp(System.currentTimeMillis()));
		MeetingRoom mr = meetingRoomRepository.save(meetingroom);
		if (mr != null){
			return 1;
		}
		return 0;
	}
	
	/**
	 * 查找会议室。
	 */
	public MeetingRoom findOneRoom (long id){
		return meetingRoomRepository.findOne(id);
	}
	
	/**
	 * 删除会议室。
	 */
	public boolean deleteRoom (long id){
		if (meetingRoomRepository.exists(id)){
			MeetingRoom room = meetingRoomRepository.findOne(id);
			if (room.getRoomStatus()!= 0){
				return false;
			}
			
			List<MeetingReserve> mr = meetingReserveRepository.findReserveByRoom(room);
			for (MeetingReserve reserve : mr){
				if (reserve.getMeetingStatus() != 0){
					return false;
				}
			}
			meetingReserveRepository.delete(mr);
			meetingRoomRepository.delete(id);

			return true;
		}
		return false;
	}
	
	/**
	 * 日程显示绑定。
	 */
	public List<MeetingReserve> findReserveReportByMonth (Date start, Date end){
		return meetingReserveRepository.findReserveReportByMonth(start , end);
	}
	
	
	/**
	 * 保存预约。
	 */
	public boolean saveReserve (MeetingReserve meetingreserve, Long roomId, String startTime ,String endTime) {
		Date meetingStartTime = DateTimeUtil.getFormatDateTime(startTime);
		Date meetingendTime = DateTimeUtil.getFormatDateTime(endTime);
		MeetingRoom room = meetingRoomRepository.findOne(roomId);
		Date nowTime = new Timestamp(System.currentTimeMillis());
		
		if (DateTimeUtil.getDaysBetweenMils(nowTime, meetingStartTime) <0){
			return false;
		}
		
		if (meetingreserve.getId() != null){
			MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
			Date reserveStart = mr.getMeetingStartTime();
			Date reserveEnd = mr.getMeetingEndTime();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String cs = "0000-00-00";
			try {
				mr.setMeetingStartTime(sf.parse(cs));
				mr.setMeetingEndTime(sf.parse(cs));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			meetingReserveRepository.save(mr);
			
			for (MeetingReserve reserve : meetingReserveRepository.findAll()){
				if (reserve.getRoom().getId() == room.getId()){
					if (meetingendTime.compareTo(reserve.getMeetingStartTime()) == 0 || 
							meetingStartTime.compareTo(reserve.getMeetingEndTime()) == 0){
						continue;
					}else{
						if (DateTimeUtil.isConfilct(reserve.getMeetingStartTime(), reserve.getMeetingEndTime(),meetingStartTime,
								meetingendTime)){
								mr.setMeetingStartTime(reserveStart);
								mr.setMeetingEndTime(reserveEnd);
								meetingReserveRepository.save(mr);
								return false;
						}
					}
				}
			}
		}else{
			for (MeetingReserve reserve : meetingReserveRepository.findAll()){
				if ((reserve.getRoom().getId() == room.getId())){
					if (meetingendTime.compareTo(reserve.getMeetingStartTime()) == 0 || 
							meetingStartTime.compareTo(reserve.getMeetingEndTime()) == 0){
						continue;
					}else{
						if (DateTimeUtil.isConfilct(reserve.getMeetingStartTime(), reserve.getMeetingEndTime(),meetingStartTime,
								meetingendTime)){
								return false;
						}
					}
				}
			}
		}
		
		room.setRoomStatus(1);//会议室为预约中。不可被删除。
		meetingreserve.setRoom(room);
		meetingreserve.setMeetingStartTime(meetingStartTime);
		meetingreserve.setMeetingEndTime(meetingendTime);
		
		if (meetingreserve.getAttendee()!=null){
			HashSet<String>  set = new HashSet<String>();//去除重复字符。
			for (String ad : meetingreserve.getAttendee().split(",")){
				set.add(ad);
			}
			set.add(meetingreserve.getInitiator().toString());
			set.add(meetingreserve.getCompere().toString());
			set.add(meetingreserve.getRegistrar().toString());
			String attendee = set.toString();
			attendee = attendee.replace(" ","");
			attendee = attendee.replace("[", "");
			attendee = attendee.replace("]", "");

			meetingreserve.setAttendee(attendee);
		}
		meetingreserve.setMeetingStatus(1);//预约为 已提交 状态。
		meetingreserve.setMeetingCreateTime(new Timestamp(System.currentTimeMillis()));
		MeetingReserve mr = meetingReserveRepository.save(meetingreserve);
		if (mr != null && remindCheck(mr)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 提醒管理员审核会议。
	 * 
	 */
	public boolean remindCheck(MeetingReserve mr) {
		MailUtil mu = new MailUtil();
		// 设置收件人
		List<String> recipients = new ArrayList<String>();
		// 设置邮件主题。
		String subject = "会议预约申请提醒";
		// 设置邮件的内容体
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		User user = userRepository.findOne(mr.getInitiator());
		String content = "<br>管理员你好！<br>用户" + user.getChinesename() + "在" + sf.format(mr.getMeetingCreateTime()) + "申请了会议：" + mr.getMeetingName() + " ，请尽快审核！<br>非常感谢！";
		// 会议管理员role。
		List<Role> rsList = roleService.findAll();
		long meetingmanage = 0;
		for (Role rs : rsList) {
			if (rs.getName().equals("meetingmanage"))
				meetingmanage = rs.getId();
		}
		
		long currentUser = RightUtil.getCurrentUserId();
		User current = userRepository.getUserById(currentUser);
		
		if (meetingmanage == 0) {
			eventNoticationService.createEventNotice("系统未设置会议管理员！", current.getId(), "会议管理", Long.valueOf(0));
			return false;
		}
		List<UserRole> usrole = roleUserService.findByRoleid(meetingmanage);

		User us = null;
		for (UserRole rl : usrole) {
			us = userRepository.findOne(rl.getUserId());
			if (StringUtils.isNotBlank(us.getEmail())) {
				recipients.add(us.getEmail());
			}
		}
		
		if (mr.getInitiator()!=null){
		    try {
			mu.send(recipients, subject, content);
			return true;
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	/**
	 * 查找指定预约。
	 */
	public MeetingReserveDTO findOneReserve(long id ){
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		MeetingReserveDTO dto = new MeetingReserveDTO();
		DateFormat df = new SimpleDateFormat("HH:mm");
		
		dto.setId(mr.getId());
		dto.setMeetingName(mr.getMeetingName()==null?"":mr.getMeetingName());
		dto.setMeetingStartDate(DateTimeUtil.getFormatDate(mr.getMeetingStartTime()));
		dto.setMeetingStartTime(df.format(mr.getMeetingStartTime()));
		dto.setMeetingEndTime(df.format(mr.getMeetingEndTime()));
		dto.setRoomName(mr.getRoom().getRoomName()==null?"":mr.getRoom().getRoomName());
		dto.setRoomId(mr.getRoom()==null?null:mr.getRoom().getId());
		dto.setMeetingDescription(mr.getMeetingDescription()==null?"":mr.getMeetingDescription());
		dto.setMeetingCreateTime(mr.getMeetingCreateTime()==null?"":DateTimeUtil.getFormatDate(mr.getMeetingCreateTime()));
		dto.setMeetingFile(mr.getMeetingFile()==null?"":mr.getMeetingFile());
		dto.setMeetingSummaryFile(mr.getMeetingSummaryFile()==null?"":mr.getMeetingSummaryFile());
		dto.setMeetingType(mr.getMeetingType());
		dto.setMeetingStatus(mr.getMeetingStatus());
		dto.setMeetingSummary(mr.getMeetingSummary()==null?"":mr.getMeetingSummary());
		dto.setSummaryCreateTime(mr.getSummaryCreateTime()==null?"":DateTimeUtil.getFormatDate(mr.getSummaryCreateTime()));
		dto.setInitiator(mr.getInitiator());
		dto.setRegistrar(mr.getRegistrar());
		dto.setCompere(mr.getCompere());
		dto.setAttendee(mr.getAttendee());
		dto.setMeetingServe(mr.getMeetingServe()==null?"":mr.getMeetingServe());
		dto.setMeetingPeriodic(mr.getMeetingPeriodic());
		
		dto.setTypeValue(codeService.findMeetingType(String.valueOf(mr.getMeetingType())));
		dto.setCompereName(userRepository.findOne(mr.getCompere()).getChinesename());
		dto.setInitiatorName(userRepository.findOne(mr.getInitiator()).getChinesename());
		dto.setRegistrarName(userRepository.findOne(mr.getRegistrar()).getChinesename());

		int sum = 0;
		if (mr.getAttendee()!=null){
			StringBuffer attendeeNames = new StringBuffer();
			for (String ad : mr.getAttendee().split(",")){
				attendeeNames.append(userRepository.findOne(Long.valueOf(ad)).getChinesename()+",");
				sum++;
			}
			dto.setAttendeeName(attendeeNames.toString().substring(0,attendeeNames.toString().length()-1));
		}else {dto.setAttendeeName("");}
		dto.setSum(sum);
		dto.setStartTime(mr.getMeetingStartTime());
		dto.setEndTime(mr.getMeetingEndTime());

		if(mr.getMeetingServe() != null){
			String[] serve = mr.getMeetingServe().split(",");
			StringBuffer serveNames = new StringBuffer();
			for (int i = 0; i < serve.length; i++) {
				if (serve[i] == serve[0]){
					serveNames.append(serve[0].equals("1")?"矿泉水,":"");
					continue;
				}
				if (serve[i] == serve[1]){
					serveNames.append(serve[1].equals("1")?"水果,":"");
					continue;
				}
			}
			if (serveNames.length() > 0){
				dto.setMeetingServeName(serveNames.toString().substring(0,serveNames.toString().length()-1));
			}else{
				dto.setMeetingServeName("");
			}
		}
		dto.setFlag(mr.getFlag());
		dto.setInform(mr.getInform());
		return dto;
	}
	
	
	
	private PageRequest buildReserveRequest(String sSortDir_0, int pageNumber, int pagzSize) {
		List<String> list = new ArrayList<String>();
		list.add("meetingStartTime");
		Direction direction = Direction.DESC;
		if (StringUtils.isNotBlank(sSortDir_0)) {
			list.add("meetingStartTime");
			if (sSortDir_0.toUpperCase().equals("DESC")) {
				direction = Direction.ASC;	
			}
		}
		 Sort sort = new Sort(direction, list);
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 我召集的预约会议。
	 */
	public Page<MeetingReserve> getReserveList(String sSortDir_0 ,int pageNumber, int pageSize, String reserveName,
			Long compere, Date startDate,Date overDate, Integer meetingStatus, int pageType){
		PageRequest pageRequest = buildReserveRequest(sSortDir_0, pageNumber, pageSize);
		Specification<MeetingReserve> spec = reserveBuildSpecification( reserveName, compere, startDate, overDate, meetingStatus, pageType);
		return (Page<MeetingReserve>) meetingReserveRepository.findAll(spec, pageRequest);
	}
	
	private Specification<MeetingReserve> reserveBuildSpecification(final String reserveName,
			final Long compere, final Date startDate,final Date overDate, final Integer meetingStatus, final Integer pageType){
		return new Specification<MeetingReserve>() {
			@Override
			public Predicate toPredicate(Root<MeetingReserve> root,CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<Predicate>();//0:我召集的会议 1：预约管理 2：已参加会议 3 ：会议纪要 4:待参加的会议。5：所有的历史会议
				if (pageType == 0){
					predicate.add(cb.equal(root.get("initiator").as(Long.class), RightUtil.getCurrentUserId()));
				}
				if (pageType == 1){
					Predicate p = cb.and(cb.equal(root.get("meetingStatus").as(Integer.class), 4),cb.equal(root.get("flag").as(Integer.class), 1));
					p =cb.or(cb.isTrue(root.get("meetingStatus").in( 1,2,3)),p);
					predicate.add(p);
				}
				if (pageType == 2){
//					predicate.add(cb.equal(root.get("initiator").as(Long.class), RightUtil.getCurrentUserId()));
					predicate.add(cb.like(root.get("attendee").as(String.class), "%"+RightUtil.getCurrentUserId()+"%"));
					predicate.add(cb.equal(root.get("meetingStatus").as(Integer.class), 4));
					predicate.add(cb.equal(root.get("flag").as(Integer.class), 1));

				}
				if (pageType == 3){
					predicate.add(cb.equal(root.get("registrar").as(Long.class), RightUtil.getCurrentUserId()));
					predicate.add(cb.equal(root.get("meetingStatus").as(Integer.class), 4));
					predicate.add(cb.equal(root.get("flag").as(Integer.class), 1));
				}
				if (pageType == 4){
					predicate.add(cb.like(root.get("attendee").as(String.class), "%"+RightUtil.getCurrentUserId()+"%"));
					predicate.add(cb.isTrue(root.get("meetingStatus").in( 2,3)));
				}
				if (pageType == 5){
					predicate.add(cb.equal(root.get("meetingStatus").as(Integer.class), 4));
					predicate.add(cb.equal(root.get("flag").as(Integer.class), 1));
				}
				

				if (StringUtils.isNotBlank(reserveName)) {
					predicate.add(cb.like(root.get("meetingName").as(String.class), "%" + reserveName + "%"));
				}
				if (compere != null && compere > 0) {
					predicate.add(cb.equal(root.get("compere").as(Long.class), compere));
				}
				if (pageType == 3){
					if (startDate!=null){
						predicate.add(cb.greaterThanOrEqualTo(root.get("summaryCreateTime").as(Date.class), startDate));
					}
					if (overDate!=null){	
						predicate.add(cb.lessThanOrEqualTo(root.get("summaryCreateTime").as(Date.class), overDate));
					}
				}else{
					if (startDate!=null){
						predicate.add(cb.greaterThanOrEqualTo(root.get("meetingStartTime").as(Date.class), startDate));
					}
					if (overDate!=null){	
						predicate.add(cb.lessThanOrEqualTo(root.get("meetingStartTime").as(Date.class), overDate));
					}
				}
				
				if (meetingStatus != null) {
					predicate.add(cb.equal(root.get("meetingStatus").as(Integer.class), meetingStatus));
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
				
			}
		};
	}
	
	/**
	 * 提交预约审核。
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean saveReserveSubmit (long id){
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		if (mr != null && mr.getMeetingStatus() == 1){
				mr.setMeetingStatus(2);
				meetingReserveRepository.save(mr);
				return true;
		}
		return false;
	}
	
	/**
	 * 删除预约。
	 */
	public boolean deleteReserve (long id ,String path){
		if (meetingReserveRepository.exists(id)){
			MeetingReserve mr = meetingReserveRepository.findOne(id);
			if (StringUtils.isNotBlank(mr.getMeetingFile())){
				for (String fileName :mr.getMeetingFile().split(",")){
					File targetFile = new File(path, fileName.substring(0,13) + fileName.substring(fileName.lastIndexOf(".")));
					targetFile.delete();
				}
			}
			if (StringUtils.isNotBlank(mr.getMeetingSummaryFile())){
				for (String fileName :mr.getMeetingSummaryFile().split(",")){
					File targetFile = new File(path, fileName.substring(0,13) + fileName.substring(fileName.lastIndexOf(".")));
					targetFile.delete();
				}
			}
			meetingReserveRepository.delete(id);
			return true;
		}
		return false;
	}
	
	/**
	 * 预约审核。
	 */
	public boolean reserveCheck (long id , long op){
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		if (op == 1 ){
			if (mr != null && mr.getMeetingStatus() == 1){
				meetingReserveRepository.updateParameter(id, 2, 1, 1);
				this.submitAutoEmail(id);
				return true;
			}
		}else {
			if (mr != null && mr.getMeetingStatus() == 2){
				meetingReserveRepository.updateParameter(id, 1, 0, 2);
				this.returnAotoEmail(id);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据审核发动邮件提醒开会时间。
	 * 
	 */
	public void submitAutoEmail(long id) {
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		MailUtil mu = new MailUtil();
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "开会时间提醒";
		// 设置邮件的内容体
		String content = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (mr.getAttendee()!=null){
			StringBuffer sb = new StringBuffer();
			for (String ad : mr.getAttendee().split(",")){
				sb.append(userRepository.findOne(Long.valueOf(ad)).getChinesename()+",");
			}
			String attendeeNames = sb.toString().substring(0,sb.toString().length()-1);
			
			for (String ad : mr.getAttendee().split(",")){
				User user = userRepository.findOne(Long.valueOf(ad));
		    	if (StringUtils.isNotBlank(user.getEmail())){
				    try {
			    		recipient = user.getEmail();
			    		content = "<br>员工:" + user.getChinesename() + "你好！<br>你在" + sf.format(mr.getMeetingStartTime()) + ", 被邀请参加会议: " + mr.getMeetingName() + " 。<br>"
						   		+ "参会人员 :" + attendeeNames +" 。 <br>"
						   		+"会议室 :"+ mr.getRoom().getRoomName() +" 。<br>"
						   		+"会议地点 :"+mr.getRoom().getRoomAddress()+" 。<br>"
				   				+ "请务必准时参加！";
						mu.send(recipient, subject, content);
	
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
		    	}
			
			}
		}
	}
	
	/**
	 * 根据审核发动邮件提醒取消会议。
	 * 
	 */
	public void returnAotoEmail(long id) {
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		MailUtil mu = new MailUtil();
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "取消会议提醒";
		// 设置邮件的内容体
		String content = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if (mr.getAttendee()!=null){
			for (String ad : mr.getAttendee().split(",")){
				User user = userRepository.findOne(Long.valueOf(ad));
				
				if (StringUtils.isNotBlank(user.getEmail())){
				    try {
			    		recipient = user.getEmail();
			    		content = "<br>员工:" + user.getChinesename() + "你好！<br>你在" + sf.format(mr.getMeetingStartTime()) + ", 被邀请参加的会议: " + mr.getMeetingName() + " 。<br>"
						   		+ "已取消！请悉知！<br>";
						mu.send(recipient, subject, content);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			
			}
		}
	}
	
	/**
	 * 保存纪要。
	 */
	public boolean saveSummary (MeetingReserve meetingreserve){
		MeetingReserve mr = meetingReserveRepository.findOne(meetingreserve.getId());
		mr.setMeetingSummary(meetingreserve.getMeetingSummary());
		mr.setMeetingSummaryFile(meetingreserve.getMeetingSummaryFile());
//		mr.setAttendee(meetingreserve.getAttendee());
		mr.setSummaryCreateTime(new Timestamp(System.currentTimeMillis()));
		MeetingReserve summary = meetingReserveRepository.save(mr);
		if (summary != null){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 手动发送邮件提醒开会时间。
	 * 
	 */
	public boolean sendEmail(long id) {
		MailUtil mu = new MailUtil();
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "开会时间提醒";
		// 设置邮件的内容体
		String content = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		
		StringBuffer sb = new StringBuffer();
		for (String ad : mr.getAttendee().split(",")){
			sb.append(userRepository.findOne(Long.valueOf(ad)).getChinesename()+",");
		}
		String attendeeNames = sb.toString().substring(0,sb.toString().length()-1);
		
		if (mr.getAttendee()!=null){
			for (String ad : mr.getAttendee().split(",")){
				User user = userRepository.findOne(Long.valueOf(ad));
			   
			    if (StringUtils.isNotBlank(user.getEmail())){
				    try {
		    		recipient = user.getEmail();
		    		content = "<br>员工:" + user.getChinesename() + "你好！<br>你在" + sf.format(mr.getMeetingStartTime()) + ", 被邀请参加会议: " + mr.getMeetingName() + " 。<br>"
		 			   		+ "参会人员 :" + attendeeNames +" 。 <br>"
		 			   		+"会议室 :"+ mr.getRoom().getRoomName() +" 。<br>"
		 			   		+"会议地点 :"+mr.getRoom().getRoomAddress()+" 。<br>";
					mu.send(recipient, subject, content);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
			    }
			
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * 手动发送邮件提醒写会议纪要。
	 * 
	 */
	public boolean remindSummary(long id) {
		MailUtil mu = new MailUtil();
		MeetingReserve mr = meetingReserveRepository.findOne(id);
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "会议纪要提醒";
		// 设置邮件的内容体
		String content = null;
		
		if (mr.getRegistrar()!=null){
			User user = userRepository.findOne(mr.getRegistrar());
		    if (StringUtils.isNotBlank(user.getEmail())){
			    try {
	    		recipient = user.getEmail();
	    		content = "<br>员工:" + user.getChinesename() + "你好！你被邀请参加的会议: " + mr.getMeetingName() + " 已结束。请尽快填写会议纪要！<br>";
				mu.send(recipient, subject, content);
				} catch (AddressException e) {
					e.printStackTrace();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
		    }
			return true;

		}
		return false;
	}
	
	
	/**
	 * 我召集的会议列表首页使用。
	 */
	public LinkedList<MeetingReserveDTO> queryPersonal (){
		LinkedList<MeetingReserveDTO> list = new LinkedList<MeetingReserveDTO>();
		List<MeetingReserve> mr = meetingReserveRepository.findReserveReport();
		for (MeetingReserve personal : mr){
			MeetingReserveDTO dto = new MeetingReserveDTO();
			if (personal.getInitiator() == RightUtil.getCurrentUserId()){
				dto.setId(personal.getId());
				dto.setMeetingName(personal.getMeetingName());
				dto.setCompereName(userRepository.findOne(personal.getCompere()).getChinesename());
				dto.setMeetingStartDate(DateTimeUtil.getFormatDate(personal.getMeetingStartTime()));
				list.add(dto);
			}
		}
		return list;
	}
	
}
