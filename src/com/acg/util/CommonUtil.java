package com.acg.util;

import java.util.Random;

public class CommonUtil {
	/**
	 * Ê××ÖÄ¸´óÐ´
	 * @param name
	 * @return
	 */
	public static String parseFirstUpper(String name){
		return name.substring(0,1).toUpperCase().concat(name.substring(1));
	}
	
	public static String generateCode(int length){
		String base = "1234567890qwertyuioplkjhgfdsazxcvbnm";
		char[] chars = base.toCharArray();
		int len = chars.length;
		Random r = new Random();
		StringBuffer sb = new StringBuffer("");
		if(length>=1){
			for(int i=0;i<length;i++){
				char c = chars[r.nextInt(len)];
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	
	public static String getKeywordInSql(String s){
		char[] chars = s.toCharArray();
		String x = "'%'";
		if(chars!=null){
			for(int i =0;i<chars.length;i++){
				x+="||?||'%'";
			}
		}
		return x;
	}
}
