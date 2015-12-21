package com.hsae.ims.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.Role;
import com.hsae.ims.entity.User;
import com.hsae.ims.entity.UserResume;
import com.hsae.ims.entity.UserRole;
import com.hsae.ims.repository.UserRepository;
import com.hsae.ims.repository.UserResumeRepository;
import com.hsae.ims.service.RoleService;
import com.hsae.ims.service.RoleUserService;
import com.hsae.ims.service.UserResumeService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.MailUtil;

/**
 * 档案信息定时任务执行
 * @author pzy
 *
 */
@Service
public class resumeAutoSendEmailJob {
	@Autowired
	private UserResumeService userResumeService;
	@Autowired
	private UserResumeRepository userResumeRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleUserService roleUserService;
	@Autowired
	private UserRepository userRepository;
	

	/***
	 * job 入口点
	 */
	public void execute() {
		birthdayAutoEmail();
		conversionTimeAutoEmail();
		contractEndDateAutoEmail();
		joinTimeAutoEmail();
	}
	
	
	/****
	 * 自动邮件提醒生日祝福。
	 * 
	 */
	public void birthdayAutoEmail(){
		List<UserResume> list = (List<UserResume>) userResumeRepository.findAll();
		
		MailUtil mu = new MailUtil();
	    // 设置收件人
		String recipient = null;
	    // 设置邮件主题。
	    String subject = "生日祝福邮件！";
	    // 设置邮件的内容体
		SimpleDateFormat md = new SimpleDateFormat("MM-dd");
		String content = null;
		// 系统当前时间		
		Date  currentDate = new Timestamp(System.currentTimeMillis());
		// 生日。
		Date birthday = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (UserResume ur : list){
			if (ur.getBirthday()== null){
				continue;
			}
			birthday = ur.getBirthday();
			
			if (DateTimeUtil.getDaysBetweenDates(birthday, currentDate) > 0 &&
					md.format(birthday).equalsIgnoreCase(md.format(currentDate))){
					try {
							if (StringUtils.isNotBlank(ur.getUser().getEmail())){
					    		recipient = ur.getUser().getEmail();
					    		content = "<br>亲爱的"+ur.getUser().getChinesename()+"您好！<br>今天是 "+sf.format(currentDate)+"您的生日！航盛全体同仁祝您：生日快乐！心想事成！";
								mu.send(recipient, subject, content);
				    		}
						} catch (AddressException e) {
							e.printStackTrace();
						} catch (MessagingException e) {
							e.printStackTrace();
						}
			}
		}
	}
	
	/****
	 * 自动邮件提醒转正时间。
	 * 
	 */
	public void conversionTimeAutoEmail(){
		List<UserResume> list = (List<UserResume>) userResumeRepository.findAll();
		
		MailUtil mu = new MailUtil();
	    // 设置收件人
		List<String> recipients = new ArrayList<String>();
	    // 设置邮件主题。
	    String subject = "转正时间提醒！";
	    // 设置邮件的内容体
		String content = null;
		// 系统当前时间		
		Date  currentDate = new Timestamp(System.currentTimeMillis());
		// 转正时间。
		Date conversionTime = null;
		
		//HR管理员role。
	    List<Role> rsList = roleService.findAll();
	    long hr =0;
	    for (Role rs : rsList){
	    	if (rs.getName().equals("hr"))
	    	hr = rs.getId();
	    }
	    List<UserRole> usrole = roleUserService.findByRoleid(hr);
	    User us = null;
	    for (UserRole rl: usrole){
    		us = userRepository.findOne(rl.getUserId());
    		if (StringUtils.isNotBlank(us.getEmail())){
	    		recipients.add(us.getEmail());
    		}

		}
		for (UserResume ur : list){
			
			if (ur.getConversionTime() == null){
				continue;
			}
			conversionTime = ur.getConversionTime();
			
			if (DateTimeUtil.getDaysBetweenDates(currentDate, conversionTime) == 15){
				content = "<br>亲爱的HR您好！<br>员工"+ur.getUser().getChinesename()+"将在"+conversionTime+"转正，特此提醒！";
					try {
						mu.send(recipients, subject, content);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	/****
	 * 自动邮件提醒合同到期时间。
	 * 
	 */
	public void contractEndDateAutoEmail(){
		List<UserResume> list = (List<UserResume>) userResumeRepository.findAll();
		
		MailUtil mu = new MailUtil();
	    // 设置收件人
		List<String> recipients = new ArrayList<String>();
	    // 设置邮件主题。
	    String subject = "合同到期时间提醒！";
	    // 设置邮件的内容体
		String content = null;
		// 系统当前时间		
		Date  currentDate = new Timestamp(System.currentTimeMillis());
		// 合同到期时间。
		Date contractEndDate = null;
		
		//HR管理员role。
	    List<Role> rsList = roleService.findAll();
	    long hr =0;
	    for (Role rs : rsList){
	    	if (rs.getName().equals("hr"))
	    	hr = rs.getId();
	    }
	    List<UserRole> usrole = roleUserService.findByRoleid(hr);
	    User us = null;
	    for (UserRole rl: usrole){
    		us = userRepository.findOne(rl.getUserId());
    		if (StringUtils.isNotBlank(us.getEmail())){
	    		recipients.add(us.getEmail());
    		}

		}
		for (UserResume ur : list){
			if (ur.getContractEndDate() == null){
				continue;
			}
			contractEndDate = ur.getContractEndDate();
			
			if (DateTimeUtil.getDaysBetweenDates(currentDate, contractEndDate) == 30){
				content = "<br>亲爱的HR您好！<br>员工"+ur.getUser().getChinesename()+"的合同将在"+contractEndDate+"到期，特此提醒！";
					try {
						mu.send(recipients, subject, content);
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	
	/****
	 * 自动邮件提醒入职满周年祝福。
	 * 
	 */
	public void joinTimeAutoEmail(){
		List<UserResume> list = (List<UserResume>) userResumeRepository.findAll();
		
		MailUtil mu = new MailUtil();
	    // 设置收件人
		String recipient = null;
	    // 设置邮件主题。
	    String subject = "入职满周年祝福！";
	    // 设置邮件的内容体
		String content = null;
		// 系统当前时间		
		SimpleDateFormat md = new SimpleDateFormat("MM-dd");
		Date  currentDate = new Timestamp(System.currentTimeMillis());
		// 合同到期时间。
		Date joinTime = null;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		for (UserResume ur : list){
			if (ur.getJoinTime()==null){
				continue;
			}
			joinTime = ur.getJoinTime();
			if (DateTimeUtil.getDaysBetweenDates(joinTime, currentDate) > 0 &&
					md.format(joinTime).equalsIgnoreCase(md.format(currentDate))){
					try {
						if (StringUtils.isNotBlank(ur.getUser().getEmail())){
							recipient = ur.getUser().getEmail();
							content = "<br>亲爱的"+ur.getUser().getChinesename()+"您好！<br>今天是 "+sf.format(currentDate)+"您入职满"+ur.getWorkYear()+"年啦！航盛全体同仁祝您：工作顺利！心想事成！";
							mu.send(recipient, subject, content);
						}
					} catch (AddressException e) {
						e.printStackTrace();
					} catch (MessagingException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
}
