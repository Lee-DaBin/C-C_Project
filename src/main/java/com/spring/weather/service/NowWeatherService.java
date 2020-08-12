package com.spring.weather.service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.weather.Dto.District;
import com.spring.weather.Dto.NowWeather;

public interface NowWeatherService {
	
	public ArrayList<NowWeather> execute(Model model, String now, ArrayList<District> ddtosList);

	public void NWjsonObject_return(ArrayList<NowWeather> nwList, Model model);

}
