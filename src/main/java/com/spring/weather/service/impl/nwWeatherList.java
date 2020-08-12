package com.spring.weather.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import com.spring.weather.service.NowWeatherService;

public class nwWeatherList implements NowWeatherService {

	@Override
	public ArrayList<NowWeather> execute(Model model,String now, ArrayList<District> ddtosList) {
		// 현재날씨 요쳥하기
		//System.out.println("@@@@@@@@@@@@@ 현재 날씨 API 요청 시작 @@@@@@@@@@@@@");
		
		String now_day = now.substring(0,8);
		String now_time = now.substring(8);
		
		for (int i = 0; i < ddtosList.size(); i++) {
			
			District Ddto = ddtosList.get(i);
			String district_ID = ddtosList.get(i).getDistrict_ID();
			
			System.out.println(ddtosList.get(i).getDistrict_ID() + " 지역 API요청 시작");
			
			int nx = ddtosList.get(i).getNX();
			//System.out.println("****** 지역 nx = " + nx);
			
			int ny = ddtosList.get(i).getNY();
			//System.out.println("****** 지역 ny = " + ny);
			
	        // 기상 데이터를 얻어오는 객체 생성
	        //System.out.println("@@@@@@@@@@@@@ JSON 객체 생성");
	     	NowWeatherJSON nwJson = new NowWeatherJSON();
	     	NowWeather nw = new NowWeather();
			try {
				nw = nwJson.getNowWeather(now_day, now_time, nx, ny); // 초단기 실황
				NowWeatherJSON_SKY nwJson_SKY = new NowWeatherJSON_SKY(); 
		     	nw.SKY = nwJson_SKY.getSKY(now_day, now_time, nx, ny); // 초단기 예보
		     	//System.out.println("@@@@@@@@@@@@@ NowWeather에 저장완료");
		     	
		     	// 데이터베이스에 접속에 관련하는객체를 만들고 데이터베이스에 입력
		     	//System.out.println("@@@@@@@@@@@@@ DB 연결 ,select, insert 시작");
		     	NowWeatherDAO nwDao = new NowWeatherDAO();

		     	nwDao.insertNowWeather(district_ID, nw);
		     	//System.out.println("@@@@@@@@@@@@@ DB 연결 ,select, insert 완료");
				
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//System.out.println("초단기 실황 API요청 실패!");
			} 
	     	
		}
		
		// 현재 날씨 리스트 가져오기
		NowWeatherDAO nwDao = new NowWeatherDAO();
		ArrayList<NowWeather> nwDtos = nwDao.getNowWeatherList(now_day,now_time);
		
		//System.out.println("[[[[[[  현재날씨 데이터 리스트 값 확인    ]]]]]]");
		/*
		for(int i=0; i<nwDtos.size();i++) {
			System.out.println(" ==================="+nwDtos.get(i).getDistrict_name_step1()+"======================");
			System.out.println(nwDtos.get(i).getDistrict_ID() + "/지역코드 "); 
			System.out.println(nwDtos.get(i).getDistrict_name_step1() + "/지역명 "); 
			System.out.println(nwDtos.get(i).getnowTime() + "/현재시간 ");
			System.out.println(nwDtos.get(i).getSKY() + " /하늘상태");
			System.out.println(nwDtos.get(i).getNTH() + " /현재온도");
			System.out.println(nwDtos.get(i).getX()+ " /위도" );
			System.out.println(nwDtos.get(i).getY()+ " /경도" );
			System.out.println(" =========================================");
		}
		*/
	
		return nwDtos;
		
	}
	@Override
	public void NWjsonObject_return(ArrayList<NowWeather> nwList,Model model) {
		
		JSONObject NWjsonObject = new JSONObject();// 최종 완성될 JSonObject

		// 지역정보입력
		for (int i = 0; i < nwList.size(); i++) {

			int num = nwList.size();

			String areaname = nwList.get(i).getDistrict_name_step1();

			System.out.println("===========================" + areaname + "===========================");

			JSONArray areaArray = new JSONArray(); // 지역이름 json정보 array
			JSONObject areaNWInfo = new JSONObject(); // 지역정보및 현재날씨 정보가 들어갈 Jsonobject

			areaNWInfo.put("District_ID", nwList.get(i).getDistrict_ID());
			areaNWInfo.put("district_name_step1", nwList.get(i).getDistrict_name_step1());
			areaNWInfo.put("X", nwList.get(i).getX());
			areaNWInfo.put("Y", nwList.get(i).getY());
			areaNWInfo.put("NTH", nwList.get(i).getNTH());
			areaNWInfo.put("SKY", nwList.get(i).getSKY());
			areaNWInfo.put("X", nwList.get(i).getX());
			areaNWInfo.put("Y", nwList.get(i).getY());
			//System.out.println("확인 areaArray리스트 들어가기전에 확인 ==== " + areaNWInfo);
			areaArray.add(areaNWInfo);
			//System.out.println("확인 NWjsonObject 들어가기전에 확인 ==== " + areaArray);

			NWjsonObject.put(areaname, areaArray);
			//System.out.println("NWjsonObject에  잘 들어갔는지 확인 ==== " + areaArray);
		}

		String nw = NWjsonObject.toJSONString();
		System.out.println("자바스크립트 보낼꺼 확인" + nw);

		model.addAttribute("nwList", nw);
	}

}
