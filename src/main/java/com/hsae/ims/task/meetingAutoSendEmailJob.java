package com.hsae.ims.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.MeetingReserve;
import com.hsae.ims.entity.User;
import com.hsae.ims.repository.MeetingReserveRepository;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.MailUtil;

/**
 * 会议预定定时任务执行
 * @author pzy
 *
 */
@Service
public class meetingAutoSendEmailJob {
	@Autowired
	private MeetingReserveRepository meetingReserveRepository;
	@Autowired
	private UserRepository userRepository;

	/***
	 * job 入口点
	 */
	public void execute() {
		updateState ();
		autoEmail();
	}
	
	/**
	 * 更新过期会议状态。
	 */
	private void updateState (){
		Date nowTime = new Timestamp(System.currentTimeMillis());
		for (MeetingReserve mr : meetingReserveRepository.findAll()){
			if (DateTimeUtil.getDaysBetweenMils(nowTime, mr.getMeetingEndTime()) <0 && mr.getMeetingStatus() != 4 && mr.getFlag() == 1){
				meetingReserveRepository.updateState(mr.getId(), 4);
			}
			if (DateTimeUtil.getDaysBetweenMils(nowTime, mr.getMeetingStartTime()) <0 && mr.getMeetingStatus() == 1){
				meetingReserveRepository.updateState(mr.getId(), 4);
			}
			if (DateTimeUtil.getDaysBetweenMils(nowTime, mr.getMeetingStartTime()) <0 && mr.getMeetingStatus() == 2){
				meetingReserveRepository.updateState(mr.getId(), 3);
			}
			if (DateTimeUtil.getDaysBetweenMils(nowTime, mr.getMeetingStartTime()) <0 && mr.getMeetingStatus() != 4 && mr.getFlag() == 0){
				meetingReserveRepository.updateState(mr.getId(), 5);
			}
		}
	}
	/****
	 * 自动邮件提醒开会。
	 * 
	 */
	public void autoEmail(){
		List<MeetingReserve> list = (List<MeetingReserve>) meetingReserveRepository.findAll();
		
		MailUtil mu = new MailUtil();
		// 设置收件人
		String recipient = null;
		// 设置邮件主题。
		String subject = "开会时间提醒";
		// 设置邮件的内容体
		String content = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		// 系统当前时间		
		Date  currentDate = new Timestamp(System.currentTimeMillis());

		for (MeetingReserve  mr :list){
			
			if (mr.getFlag() == 1 && mr.getMeetingStatus()==2){
				if (DateTimeUtil.getDaysBetweenMils(currentDate , DateTimeUtil.getSpecifiedDateTimeBySeconds(mr.getMeetingStartTime(), -600)) == 0 && mr.getAttendee()!=null){
					StringBuffer sb = new StringBuffer();
					for (String ad : mr.getAttendee().split(",")){
						sb.append(userRepository.findOne(Long.valueOf(ad)).getChinesename()+",");
					}
					String attendeeNames = sb.toString().substring(0,sb.toString().length()-1);
					
					for (String ad : mr.getAttendee().split(",")){
						User user = userRepository.findOne(Long.valueOf(ad));
					   
					    if (StringUtils.isNotBlank(user.getEmail())){
				    		recipient = user.getEmail();
			    		    content = "<br>员工:" + user.getChinesename() + "你好！<br>你在" + sf.format(mr.getMeetingStartTime()) + ", 被邀请参加会议: " + mr.getMeetingName() + " 。<br>"
							   		+ "参会人员 :" + attendeeNames +" 。 <br>"
							   		+"会议室 :"+ mr.getRoom().getRoomName() +" 。<br>"
							   		+"会议地点 :"+mr.getRoom().getRoomAddress()+" 。<br>"
					   				+ "请务必准时参加！";
						    try {
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
			
		}
	}
}
