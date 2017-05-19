package com.acg.test;

import java.util.Date;

import com.acg.util.CommonUtil;
import com.acg.util.DateUtil;



public class UtilTest {
	public static void main(String[] args) {
		Date d = new Date();
		//Date c = DateUtil.getNDaysLater(d, 122);
		//System.out.println(DateUtil.formatDate(d));
		//System.out.println(DateUtil.formatDate(c));
		//String s = CommonUtil.generateCode(7);
		//System.out.println(s);
		String x = "moxing.jsp";
		String s = x.substring(x.lastIndexOf(".")+1);
		System.out.println(s);
	}
	
	
}
