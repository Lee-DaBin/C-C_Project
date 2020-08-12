package com.spring.weather.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;


public class DateLoader {
	
	
	public String DateLader() {
		
		Date date = new Date();
		SimpleDateFormat sformat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.HOUR, 0);
		String now = sformat.format(calendar.getTime());
		
		return now ;
		
	}
	
	public static String[] fcDay(String formattedDate) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		
		String now = formattedDate;
		
		//view에 뿌릴 오늘 날짜  -----------------------------------------------------------------------
		String now_day = formattedDate.substring(0,8);
		String now_time = formattedDate.substring(8);
		
		System.out.println("현재 날짜 = " + now_day);
		System.out.println("현재 시간 = " + now_time);
		
		String y = now_day.substring(0,4);
		String M = now_day.substring(4,6);
		String d = now_day.substring(6,8);
		// 시간
		String nowTime = now_time.substring(0,4);
		
		// 오늘, 내일, 모레 
		String fcday = null;
		
		try {
			date = dateFormat.parse(now_day.substring(0,8));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		cal.add(Calendar.DATE,+1);
		fcday = dateFormat.format(cal.getTime());
		String d2 = fcday.substring(6,8);
		
		cal.add(Calendar.DATE,+1);
		fcday = dateFormat.format(cal.getTime());
		String d3 = fcday.substring(6,8);
		
		String[] fcDayList = {d,d2,d3};
		
		
		return fcDayList;
		
	}
	
	

}
