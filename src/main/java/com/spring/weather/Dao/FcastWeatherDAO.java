package com.spring.weather.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;

import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;

public class FcastWeatherDAO {
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Weather";

	static final String USERNAME = "dd";
	static final String PASSWORD = "123456789";

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	static String District_name_step1 = null;

	public static ArrayList<FcastWeather> fcWeatherList(String now_day, String areaID) {
		// 예보날씨 가져오기
		//System.out.println("====fcWeatherList 예보날씨 가져오기 DB연결 ======= ");
		
		ArrayList<District> DdtosList = DistrictDAO.select_gridxy(); //지역테이블리스트
		ArrayList<FcastWeather> fcDtos =  new ArrayList<FcastWeather>();
		
		for(int i=0; i<DdtosList.size(); i++) {
			
			if(areaID.equals(DdtosList.get(i).getDistrict_ID())) {
				District_name_step1 = DdtosList.get(i).getDistrict_name_step1();
			}
			
		}
		
		int nowday3 = Integer.parseInt(now_day)+2;
		String areaid = areaID;

		//System.out.println("fcast_weather Database 접속 : ");

		try {
			// 데이터베이스에 접속합니다.
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver 연결성공");

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// 데이터베이스 접속 결과를 출력합니다.
			if (conn != null) {
				System.out.println("MS-SQL 연결성공");
			} else {
				System.out.println("MS-SQ 연결실패");
			}

			String sql = "select * from fcast_weather where fcastDate >=(" + now_day + ") AND fcastDate <=("
					+ nowday3 + ") AND district_ID like('" + areaID
					+ "')order by district_ID, fcastDate, fcastTime;";
			System.out.println("예보날씨 table 데이터 가져오는 쿼리문 확인하기: " + sql);
			pstmt = conn.prepareStatement("select * from fcast_weather where fcastDate >=(" + now_day
					+ ") AND fcastDate <=(" + nowday3+ ") AND district_ID like('" + areaID
					+ "')order by district_ID, fcastDate, fcastTime;");

			rs = pstmt.executeQuery();

			// System.out.println("rs값 확인: "+rs.next());
			while (rs.next()) {

				String district_ID = rs.getString("district_ID");
				String fcastime = rs.getString("fcastTime");
				String fcasDate = rs.getString("fcastDate");
				String SKY = rs.getString("SKY");
				String FTH = rs.getString("T3H");
				
				
				/*

				System.out.println("<<<<<<<<<<<<<<<<<< 예보날씨 테이블에서 데이터 잘 가져와졌나 확인 >>>>>>>>>>>>>>>>>");
				System.out.println("district_ID =" + district_ID);
				System.out.println("fcastime ="+fcastime);
				System.out.println("fcasDate ="+fcasDate);
				System.out.println("SKY =" + SKY);
				System.out.println("FTH =" + FTH);
				System.out.println("값가져오기 완료"); 
				
				*/
				FcastWeather fcdto = new FcastWeather(district_ID,District_name_step1,fcastime,SKY,FTH,fcasDate);

				fcDtos.add(fcdto);
				
				//System.out.println("(fcDtos)예보 날씨 리스트에  fcdto 넣기 완료");

			}

			pstmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		//System.out.println("예보날씨 데이터 리스트(fcdto) 생성!!");
		//System.out.println("=========================================================================================");
		
		
		return fcDtos;

	}
	public void insertFcastWeather(String district_ID, FcastWeather fcast) {

		//System.out.println("====insertFcastWeather DB에 저장하기 실행 ======= ");

		String query = "IF EXISTS ( SELECT 1 FROM fcast_weather WHERE district_ID =" + "'" + district_ID
				+ "' AND fcastTime=" + "'" + fcast.getFcastTime() + "' AND fcastDate=" + "'" + fcast.getFcastDate()
				+ "')" + "\tBEGIN " + "\tUPDATE fcast_weather" + "\tSET SKY =" + fcast.getSKY() + "," + "\tT3H = "
				+ fcast.getFTH() + "\tWHERE district_ID =" + "'" + district_ID + "' AND fcastTime=" + "'"
				+ fcast.getFcastTime() + "'AND fcastDate=" + "'" + fcast.getFcastDate() + "';" + "\tEND" + "\tELSE"
				+ "\tBEGIN" + "\tINSERT INTO fcast_weather (district_ID, fcastTime, fcastDate, SKY,T3H) " + " \tVALUES("
				+ "'" + district_ID + "'," + "'" + fcast.getFcastTime() + "'," + "'" + fcast.getFcastDate() + "',"
				+ fcast.getSKY() + "," + fcast.getFTH() + ");"

				+ "\tEND;";

		//System.out.println("예보날씨 INSERT 쿼리문 확인하기: " + query);

		//System.out.println("Weather Database 접속 : ");

		try {
			// db접속
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			// 데이터베이스 접속 결과를 출력합니다.
			if (conn != null) {
				//System.out.println("MS-SQL 연결성공");
			} else {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!MS-SQ 연결실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			}

			stmt = conn.createStatement();

			try {
				stmt.executeUpdate(query);
				//System.out.println("DB저장완료");
				stmt.close();
				conn.close();

			} catch (Exception e) {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DB저장실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("SQL Exception : " + e.getMessage());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
