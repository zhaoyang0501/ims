package com.hsae.ims.task;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsae.ims.entity.BookLendRecord;
import com.hsae.ims.repository.BookLendRecordRepository;
import com.hsae.ims.service.BookManageService;
import com.hsae.ims.utils.DateTimeUtil;
import com.hsae.ims.utils.MailUtil;

/**
 * 图书借阅定时任务执行
 * @author zhaozhou
 *
 */
@Service
public class bookAutoSendEmailJob {
	@Autowired
	private BookManageService bookManageService;
	@Autowired
	private BookLendRecordRepository lendRecordRepository;
	
	/***
	 * job 入口点
	 */
	public void execute() {
		autoEmail();
	}
	
	
	/****
	 * 自动邮件提醒归还时间。
	 * 到期前3天、7天。
	 */
	public void autoEmail(){
		List<BookLendRecord> list = (List<BookLendRecord>) lendRecordRepository.findLendRecordByAutoEmail();
		MailUtil mu = new MailUtil();
	    // 设置收件人
		String recipient = null;
	    // 设置邮件主题。
	    String subject = "图书归还时间提醒";
	    // 设置邮件的内容体
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String content = null;
		// 系统当前时间		
		String  currentDate = new Timestamp(System.currentTimeMillis()).toString();
		
		for (BookLendRecord lr: list){
			if (DateTimeUtil.getDaysBetweenDates(currentDate, sdf.format(lr.getExpectTime())) == 7
				|| DateTimeUtil.getDaysBetweenDates(currentDate, sdf.format(lr.getExpectTime())) == 3){
				try {
					if (StringUtils.isNotBlank(lr.getUserId().getEmail())){
			    		recipient = lr.getUserId().getEmail();
			    		content = "<br>用户"+lr.getUserId().getChinesename()+"你好！<br>你在"+sdf.format(lr.getLendTime())+
								" 借阅的图书："+lr.getBookId().getCode()+" 《"+lr.getBookId().getBookName()+"》 应还时间是"+
								sdf.format(lr.getExpectTime())+" ，请及时归还 。 以免产生罚款！<br>非常感谢！";
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
