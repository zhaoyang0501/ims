package com.hsae.ims.utils;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hsae.ims.constants.ImsConstants;
import com.hsae.ims.entity.MailAuthenticator;

public class MailUtil {
	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();

	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	private transient Session session;

	public MailUtil() {
		this.init(ImsConstants.BookConfig.EMAIL_ID, ImsConstants.BookConfig.EMAIL_PWD, ImsConstants.BookConfig.SEND_SMTP);
	}

	/**
	 * 邮件发送
	 * @param recipient
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, String subject, Object content) throws AddressException, MessagingException {
		final MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		message.setSubject(subject);
		message.setContent(content.toString(), "text/html;charset=utf-8");
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * @param recipients
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content) throws AddressException, MessagingException {
		final MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		message.setSubject(subject);
		message.setContent(content.toString(), "text/html;charset=utf-8");
		Transport.send(message);
	}

	private void init(String username, String password, String smtpHostName) {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		authenticator = new MailAuthenticator(username, password);
		session = Session.getInstance(props, authenticator);
	}
}
