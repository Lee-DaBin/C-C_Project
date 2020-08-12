package com.spring.weather.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.ui.Model;

import com.spring.weather.Dao.DistrictDAO;
import com.spring.weather.Dto.District;
import com.spring.weather.Util.DateLoader;
import com.spring.weather.service.MainpageService;

public class mainimpl implements MainpageService {

	@Override
	public void execute(Model model,String now) {
		// 첫화면 구성
		String now_day = now.substring(0, 8);
		String now_time = now.substring(8);

		System.out.println("현재 날짜 = " + now_day);
		System.out.println("현재 시간 = " + now_time);

		model.addAttribute("y", now_day.substring(0, 4));
		model.addAttribute("M", now_day.substring(4, 6));
		model.addAttribute("d", now_day.substring(6, 8));
		// 시간
		model.addAttribute("nowTime", now_time.substring(0, 4));

		// 오늘, 내일, 모레
		Date date = null;
		String fcday = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		try {
			date = dateFormat.parse(now_day.substring(0, 8));
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.add(Calendar.DATE, +1);
		fcday = dateFormat.format(cal.getTime());
		model.addAttribute("d2", fcday.substring(6, 8));

		cal.add(Calendar.DATE, +1);
		fcday = dateFormat.format(cal.getTime());
		model.addAttribute("d3", fcday.substring(6, 8));

		//// 요일 구하기
		cal = Calendar.getInstance();
		System.out.println("cal:" + cal);
		// 1 2 3 4 5 6 7
		final String[] week = { "일", "월", "화", "수", "목", "금", "토" };

		String W = week[cal.get(Calendar.DAY_OF_WEEK) - 1];
		model.addAttribute("w", W);
	
		ArrayList<District> DdtosList = DistrictDAO.select_gridxy(); // 지역테이블리스트
		String areaName = DdtosList.get(0).getDistrict_name_step1(); 
		model.addAttribute("mainAreaName", areaName); // 초기 지역이름 

	}

}
