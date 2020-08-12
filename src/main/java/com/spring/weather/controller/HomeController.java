package com.spring.weather.controller;

import java.util.List;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.weather.Dao.DistrictDAO;
import com.spring.weather.Dao.NowWeatherJSON;
import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;
import com.spring.weather.Dto.NowWeather;
import com.spring.weather.Util.DateLoader;
import com.spring.weather.service.FcastWeatherService;
import com.spring.weather.service.MainpageService;
import com.spring.weather.service.NowWeatherService;
import com.spring.weather.service.impl.fcWeatherList;
import com.spring.weather.service.impl.mainimpl;
import com.spring.weather.service.impl.nwWeatherList;

import net.sf.json.JSONException;

/**
 * Handles requests for the application home page.
 */

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	ArrayList<District> DdtosList = DistrictDAO.select_gridxy(); 

	NowWeatherService nwService;
	FcastWeatherService fcService;
	MainpageService mainService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String now = new DateLoader().DateLader();
		logger.info("접속!! The client locale is {}.", locale);
		
		// 메인화면 구성 
		System.out.println("=== 1) 메인화면 구성");
		mainService = new mainimpl();
		mainService.execute(model,now);

		// 현재날씨 요청 및 출력
		System.out.println("=== 2) 현재날씨 시작");
		nwService = new nwWeatherList();
		ArrayList<NowWeather> nwList = nwService.execute(model, now, DdtosList);
		nwService.NWjsonObject_return(nwList,model);

		// 예보날씨 요청 및 출력
		System.out.println("=== 3) 예보날씨 시작");
		String areaID = DdtosList.get(0).getDistrict_ID(); // 디폴트 지역 (서울)
		fcService = new fcWeatherList();
		fcService.execute(model, areaID);

		return "home";
	}

	// 선택한 지역 예보날씨 ajax
	@RequestMapping(value = "/fcWeatherList", method = RequestMethod.POST, produces = "application/text; charset=utf8") // 한글인코딩해서넘기기
	public @ResponseBody String ajax_fcWeatherbtn(@RequestBody String ID, Model model) {
		String now = new DateLoader().DateLader();
		
		//선택한 지역 ID,지역이름 
		String areaID = ID.replaceAll("[^0-9]", ""); // 숫자만가져오기
		String areaName = new DistrictDAO().select_areaID(areaID);
	
		//선택한 지역이름,예보날씨 보내기
		fcService = new fcWeatherList();
		ArrayList<FcastWeather> SfcList = fcService.selectFc(model, areaID);
		String Sfw = fcService.FCjsonObject_return(SfcList,model, now);
		
		return Sfw;
	}

}
