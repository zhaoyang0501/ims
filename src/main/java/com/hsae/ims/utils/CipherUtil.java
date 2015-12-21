package com.hsae.ims.utils;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;


/**
 * 对密码进行加密和验证的类
 */
public class CipherUtil {
	
	/** 对字符串进行MD5加密 */
	public static String encodeByMD5(String originString) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		return md5.encodePassword(originString, null);
	}
	
}
