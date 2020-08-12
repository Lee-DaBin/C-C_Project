package com.spring.weather.scheduler;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import com.spring.weather.Dao.DistrictDAO;
import com.spring.weather.Dao.FcastWeatherJSON;
import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;
import com.spring.weather.Util.DateLoader;
import com.spring.weather.controller.HomeController;

public class SchedulerFcW {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerFcW.class);

	@Scheduled(fixedRate = 10800000) //5분마다 실행(300000),1시간 간격=(3600000) -> [10000*60*60], 3시간 간격=(10800000)
	public void scheduleRun() {
		System.out.println("★★★★★★★★★★★★★★★★★  예보날씨 요청  Schedulerfc 실행 ★★★★★★★★★★★★★★★★★");
		//System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ ★★★★★★★★★★★★★★★★★★★");

		logger.info("scheduler시작 = " );
		String now = new DateLoader().DateLader();
		String now_day = now.substring(0, 8);
		String now_time = now.substring(8);

		//System.out.println("현재 날짜 = " + now_day);
		//System.out.println("현재 시간 = " + now_time);

		// 동네에보 JSON 데이터를 얻기위해 필요한 요청변수 입력

		// 동네에보 JSON 데이터를 얻기위해 필요한 요청변수 입력
		// DB에서 격자 정보 가져오기
		// 데이터베이스에 접속에 관련하는객체를 만들고 데이터베이스에 입력
		//System.out.println("@@@@@@@@@@@@@ DB 연결 ,지역테이블에서 격자 정보 가져오기 시작");

		DistrictDAO disdoa = new DistrictDAO();

		ArrayList<District> DdtosList = DistrictDAO.select_gridxy();

		for (int i = 0; i < DdtosList.size(); i++) {
			District Ddto = DdtosList.get(i);
			//System.out.println("============================================================================");
			System.out.println(DdtosList.get(i).getDistrict_ID() + " 지역 API요청 시작");
			//System.out.println("============================================================================");
			int nx = DdtosList.get(i).getNX();
			//System.out.println("****** 지역 nx = " + nx);
			int ny = DdtosList.get(i).getNY();
			//System.out.println("****** 지역 ny = " + ny);

			// 현재시간 계산하기
			// 서버 현재 날짜 시간 가져오기
		
			int nowtime = Integer.parseInt(now_time); // 시간변경 가능하게 int형으로 바꾸기
			int nowday = Integer.parseInt(now_day); // 시간변경 가능하게 int형으로 바꾸기

			//System.out.println("날짜/" + now_day + "시간/" + now_time);

			String districtID = DdtosList.get(i).getDistrict_ID();

			// 기상 데이터를 얻어오는 객체 생성
			//System.out.println("@@@@@@@@@@@@@ JSON 객체 생성 시작");
			FcastWeatherJSON fcJson = new FcastWeatherJSON();

			// 기상데이터를 JSON 형태로 받아 FcastWeather에 저장 //baseTime 이랑 baseDate는 Spring scheduler로
			// 생성한걸로 수정 필요
			//System.out.println("@@@@@@@@@@@@@ FcastWeather에 저장 시작");
			try {
				FcastWeather fcDto = fcJson.getFcastWeather(districtID, nowday, nowtime, nx, ny);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // 동네예보
			System.out.println("@@@@@@@@@@@@@ FcastWeather에 저장완료"+"현재날짜"+nowday+"현재시간 ="+nowtime);
			//System.out.println("★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ ★★★★★★★★★★★★★★★★★★★");
		}

	}
}
