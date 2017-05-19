package com.acg.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddhhmmss");
	public static String formatDate(Date date){
		return format.format(date);
	}
	public static String formatDate2(Date date){
		return format2.format(date);
	}
	/**
	 * 判断日期d1,d2是否是同一天
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean sameDay(Date d1,Date d2){
		boolean sameDay = false;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		if(c2.get(Calendar.YEAR)==c1.get(Calendar.YEAR)){
			if(c2.get(Calendar.DAY_OF_YEAR)==c1.get(Calendar.DAY_OF_YEAR)){
				sameDay = true;
			}
		}
		return sameDay;
	}
	
	public static Date getNDaysLater(Date d,int n){

		long secs = n*1000*60*60*24l;
		long days = d.getTime();
		return new Date(secs+days);
	}

}
