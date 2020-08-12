package com.spring.weather.Dto;

import java.awt.List;

public class FcastWeather {
	
	public String fcastDate; // 예보날짜
	public String fcastTime; // 예보시간
	
	public String district_ID;
	public String district_name_step1;
	public String SKY;
	public String FTH;
	
	public int ny;
	public int nx;
	

	public FcastWeather() {
		
	}
	

	public FcastWeather(String district_ID,String fcastime,String SKY, String FTH) {
		super();
		this.district_ID = district_ID;
		this.fcastTime = fcastime;
		this.SKY = SKY;
		this.FTH = FTH;
	}


	public FcastWeather(String district_ID, String district_name_step1, String fcastime, String sKY, String FTH, String fcasDate) {
		super();
		this.district_ID = district_ID;
		this.district_name_step1 = district_name_step1;
		this.fcastTime = fcastime;
		this.fcastDate = fcasDate;
		this.SKY = sKY;
		this.FTH = FTH;
		
	}

	public String getFcastDate() {
		return fcastDate;
	}

	public void setFcastDate(String fcastDate) {
		this.fcastDate = fcastDate;
	}

	public String getFcastTime() {
		return fcastTime;
	}

	public void setFcastTime(String fcastTime) {
		this.fcastTime = fcastTime;
	}

	public String getDistrict_ID() {
		return district_ID;
	}

	public void setDistrict_ID(String district_ID) {
		this.district_ID = district_ID;
	}

	public String getDistrict_name_step1() {
		return district_name_step1;
	}

	public void setDistrict_name_step1(String district_name_step1) {
		this.district_name_step1 = district_name_step1;
	}

	public String getSKY() {
		return SKY;
	}

	public void setSKY(String sKY) {
		SKY = sKY;
	}

	public String getFTH() {
		return FTH;
	}

	public void setFTH(String FTH) {
		FTH = FTH;
	}

	public int getNy() {
		return ny;
	}

	public void setNy(int ny) {
		this.ny = ny;
	}

	public int getNx() {
		return nx;
	}

	public void setNx(int nx) {
		this.nx = nx;
	}

	@Override 
	public String toString() {
		return "FcastWeather"
				+ "[fcastTime=]"+fcastTime+
				",district_ID="+district_ID+
				",district_name_step1="+district_name_step1+
				",SKY="+SKY+
				",FTH="+FTH+"]"; 
	}


	
	
}
