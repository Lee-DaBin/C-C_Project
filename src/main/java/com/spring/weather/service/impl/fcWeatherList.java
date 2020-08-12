package com.spring.weather.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;

import com.spring.weather.Dao.DistrictDAO;
import com.spring.weather.Dao.FcastWeatherDAO;
import com.spring.weather.Dao.NowWeatherDAO;
import com.spring.weather.Dao.NowWeatherJSON;
import com.spring.weather.Dao.NowWeatherJSON_SKY;
import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;
import com.spring.weather.Dto.NowWeather;
import com.spring.weather.Util.DateLoader;
import com.spring.weather.service.FcastWeatherService;

public class fcWeatherList implements FcastWeatherService {

	@Override
	public void execute(Model model, String areaID) {
		// 예보날씨리스트 가져오기
		String formattedDate = new DateLoader().DateLader();
		String nowday = formattedDate.substring(0, 8);

		FcastWeatherDAO fcDao = new FcastWeatherDAO();
		ArrayList<FcastWeather> fcDtos = fcDao.fcWeatherList(nowday, areaID);

		/*
		for (int i = 0; i < fcDtos.size(); i++) {
			System.out.println(" =========================================");
			System.out.println(i + ")번째");
			System.out.println(fcDtos.get(i).district_ID + " 지역 예보 날씨 리스트/");
			System.out.println(fcDtos.get(i).district_name_step1 + " 지역이름/");
			System.out.println(fcDtos.get(i).fcastTime + " / 예보 시간");
			System.out.println(fcDtos.get(i).fcastDate + " / 예보 날짜");
			System.out.println(fcDtos.get(i).FTH + "/예보온도 ");
			System.out.println(fcDtos.get(i).SKY + "/하늘상태 ");
			System.out.println(" =========================================");
		}
		*/
		model.addAttribute("fcDtos", fcDtos);

	}

	@Override
	public ArrayList<FcastWeather> selectFc(Model model, String areaID) {
		// 예보날씨리스트 가져오기
		String formattedDate = new DateLoader().DateLader();
		String nowday = formattedDate.substring(0, 8);

		FcastWeatherDAO fcDao = new FcastWeatherDAO();
		ArrayList<FcastWeather> fcDtos = fcDao.fcWeatherList(nowday, areaID);
		
		/*
		for (int i = 0; i < fcDtos.size(); i++) {
			System.out.println(" =========================================");
			System.out.println(i + ")번째");
			System.out.println(fcDtos.get(i).district_ID + " 지역 예보 날씨 리스트/");
			System.out.println(fcDtos.get(i).district_name_step1 + " 지역이름/");
			System.out.println(fcDtos.get(i).fcastTime + " / 예보 시간");
			System.out.println(fcDtos.get(i).FTH + "/예보온도 ");
			System.out.println(fcDtos.get(i).SKY + "/하늘상태 ");
			System.out.println(" =========================================");
		}
		*/
		model.addAttribute("fcDtos", fcDtos);

		return fcDtos;

	}
	
	@Override
	public String FCjsonObject_return(ArrayList<FcastWeather> sfc, Model model, String now) {

		// 날짜 배열 가져오기
		String[] fcDayList = DateLoader.fcDay(now);
		String fcDay = null;

		JSONObject FCObject = new JSONObject();// 최종 완성될 JSonObject
		JSONArray areaArray = new JSONArray(); // 지역이름 json정보 array

		// 지역정보입력
		for (int i = 0; i < sfc.size(); i++) {

			String FcastDate = sfc.get(i).getFcastDate();

			JSONObject areaNWInfo = new JSONObject(); // 지역정보및 현재날씨 정보가 들어갈 Jsonobject

			if (sfc.get(i).getFcastTime().equals("0000")) {
				areaArray = new JSONArray(); // 지역이름 json정보 array
			}
			areaNWInfo.put("fcTime", sfc.get(i).getFcastTime());
			areaNWInfo.put("fcDate", sfc.get(i).getFcastDate());
			areaNWInfo.put("district_name_step1", sfc.get(i).getDistrict_name_step1());
			areaNWInfo.put("FTH", sfc.get(i).getFTH());
			//System.out.println("확인 areaArray리스트 들어가기전에 확인 ==== " + areaNWInfo);
			areaArray.add(areaNWInfo);
			//System.out.println("확인 NWjsonObject 들어가기전에 확인 ==== " + areaArray);

			FCObject.put(FcastDate, areaArray);
			//System.out.println("NWjsonObject에  잘 들어갔는지 확인 ==== " + areaArray);
		}

		String Sfw = FCObject.toJSONString();
		//System.out.println("확인:" + Sfw);
		return Sfw;

	}

}
