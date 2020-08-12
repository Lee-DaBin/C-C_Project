package com.spring.weather.Dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;
import com.spring.weather.Dto.NowWeather;

//데이터 베이스에 접속하여 조작에 관한 기능의 정의된 클래스
public class NowWeatherDAO {
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Weather";

	static final String USERNAME = "dd";
	static final String PASSWORD = "123456789";

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	static String District_name_step1 = null;
	static double X ;
	static double Y ;

	public void insertNowWeather(String district_ID, NowWeather nw) {
		/*
		System.out.println("====insertNowWeather DB에 저장하기 실행 ======= ");
		System.out.println();
		System.out.println("district_ID값= " + district_ID);
		System.out.println("초단기실황API에서 받아온 T1H기온 값 확인: " + nw.getNTH());
		*/
		
		String query = "INSERT INTO now_weather " + " VALUES(" + "'" + district_ID + "'," + "'" + nw.getnowTime() + "',"
				+ "'" + nw.getnowDate() + "'," + nw.getSKY() + "," + nw.getNTH() + ");";
		
		//System.out.println("현재날씨 INSERT 쿼리문 확인하기: " + query);
		//System.out.println("Weather Database 접속 : ");

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			if (conn != null) {
				System.out.println("MS-SQL 연결성공");
			} else {
				System.out.println("MS-SQ 연결실패");
			}

			stmt = conn.createStatement();

			try {
				stmt.executeUpdate(query);
				System.out.println("DB저장완료");
				stmt.close();
				conn.close();

			} catch (Exception e) {
				System.out.println("DB저장실패");
				System.out.println("SQL Exception : " + e.getMessage());
			}

		} catch (Exception e) {
		}

	}

	public static ArrayList<NowWeather> getNowWeatherList(String now_day, String now_time) {
		// 현재날씨 가져오기
		ArrayList<NowWeather> nwDtos =  new ArrayList<NowWeather>();
		
		// top ? 개 뽑을찌 지역 정하는거 
		ArrayList<District> DdtosList = DistrictDAO.select_gridxy();
		int num = DdtosList.size();
		String areaSize = Integer.toString(num);
		String nowday = now_day;
		
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver 연결성공");

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			if (conn != null) {
				System.out.println("MS-SQL 연결성공");
			} else {
				System.out.println("MS-SQ 연결실패");
			}

			
			String sql = "select top\n"+areaSize+" \n[district_ID], MAX(nowTime) as NT,SKY,T1H from now_weather where nowDate like(" + nowday + ") group by district_ID,nowTime,SKY,T1H order by MAX(nowTime) DESC;";
			//System.out.println("예보날씨 table 데이터 가져오는 쿼리문 확인하기: " + sql);
			pstmt = conn.prepareStatement("select top\n"+areaSize+" \n[district_ID], MAX(nowTime) as NT,SKY,T1H from now_weather where nowDate like(" + nowday + ") group by district_ID,nowTime,SKY,T1H order by MAX(nowTime) DESC;");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				String district_ID = rs.getString("district_ID");
				String nowTime = rs.getString("NT");
				String SKY = rs.getString("SKY");
				String T1H = rs.getString("T1H");
				
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" +district_ID +"정보 넣기  <<<<<<<<<<<<<<<<<<<<<");
				for(int a = 0; a<DdtosList.size(); a++) {
					if(district_ID.equals(DdtosList.get(a).getDistrict_ID())) {
						District_name_step1  = DdtosList.get(a).getDistrict_name_step1(); 
					    X = (double) DdtosList.get(a).getX();
					    Y = (double) DdtosList.get(a).getY();
					}
				}
				
				/*
				System.out.println("district_ID =" + district_ID);
				System.out.println("District_name_step1 =" + District_name_step1);
				System.out.println("nowTime =" + nowTime);
				System.out.println("SKY =" + SKY);
				System.out.println("T1H =" + T1H);
				System.out.println("X =" + X);
				System.out.println("Y =" + Y);
				System.out.println("값가져오기 완료"); 
				*/
	
				NowWeather nwdto = new NowWeather(district_ID,District_name_step1,nowTime,SKY, T1H,X,Y);
				
				nwDtos.add(nwdto);
			}
			pstmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		return nwDtos;
	}
}