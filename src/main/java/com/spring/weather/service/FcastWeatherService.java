package com.spring.weather.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.spring.weather.Dto.FcastWeather;
import com.spring.weather.Dto.NowWeather;

public interface FcastWeatherService {
	
	public void execute(Model model,String area);

	public ArrayList<FcastWeather> selectFc(Model model, String areaID);

	public String FCjsonObject_return(ArrayList<FcastWeather> sfcList, Model model, String now);
	
}
