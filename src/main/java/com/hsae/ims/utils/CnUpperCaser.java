package com.hsae.ims.utils;

/**
 * 将数字转换成汉字形式
 * @author caowei
 *
 */
public class CnUpperCaser {

	private static final char[] cnNumbers={'零','一','二','三','四','五','六','七','八','九'};
	
	private static final char[] series={' ','拾','百','仟','万','拾','百','仟','亿'};
	
	public static String getCnString(String num){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num.length(); i++) {
			int number = getNumber(num.charAt(i));
			sb.append(cnNumbers[number]);
			sb.append(series[num.length()-1-i]);
		}
		return sb.toString();
	}
	
	 private static int getNumber(char c){
		 String str = String.valueOf(c);
		 return Integer.parseInt(str);
	 }
}
