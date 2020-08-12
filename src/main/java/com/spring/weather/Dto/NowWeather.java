package com.spring.weather.Dto;

import java.awt.List;

public class NowWeather {
	public String nowDate; // 현재날짜
	public String nowTime; // 현재시간
	public String SKY;
	public String NTH;
	
	public String district_ID;
	
	public String district_name_step1;

	public double X;
	public double Y;
	
	
	public NowWeather() {
	}

	
	public NowWeather(String district_ID, String nowTime, String SKY, String T1H) {
		super();
		this.nowTime = nowTime;
		this.SKY = SKY;
		this.NTH = T1H;
		this.district_ID = district_ID;
	}


	public NowWeather(String district_ID2, String district_name_step12, String nowTime2, String sKY2, String t1h2,double x, double y) {
		super();
		this.district_ID = district_ID2;
		this.district_name_step1 = district_name_step12;
		this.nowTime = nowTime2;
		this.SKY = sKY2;
		this.NTH = t1h2;
		this.X = x;
		this.Y = y;
	}


	public String getDistrict_name_step1() {
		return district_name_step1;
	}
	public void setDistrict_name_step1(String district_name_step1) {
		this.district_name_step1 = district_name_step1;
	}
	public String getnowDate() {
		return nowDate;
	}
	public void setnowDate(String baseDate) {
		this.nowDate = baseDate;
	}
	public String getnowTime() {
		return nowTime;
	}
	public void setnowTime(String baseTime) {
		this.nowTime = baseTime;
	}
	public String getSKY() {
		return SKY;
	}
	public void setSKY(String sky) {
		this.SKY = sky;
	}
	public String getNTH() {
		return NTH;
	}
	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getNowTime() {
		return nowTime;
	}

	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}

	public void setT1H(String t1h) {
		NTH = t1h;
	}
	public String getDistrict_ID() {
		return district_ID;
	}
	public void setDistrict_ID(String district_ID) {
		this.district_ID = district_ID;
	}


	public double getX() {
		return X;
	}


	public void setX(double x) {
		X = x;
	}


	public double getY() {
		return Y;
	}


	public void setY(double y) {
		Y = y;
	}
	
	@Override 
	public String toString() {
		return "NowWeather"
				+ "[nowTime=]"+nowTime+
				",district_ID="+district_ID+
				",district_name_step1="+district_name_step1+
				",SKY="+SKY+
				",NTH="+NTH+
				",X="+X+
				",Y="+Y+"]"; 
	}

	
	
}
