package com.spring.weather.Dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.spring.weather.Dto.District;
import com.spring.weather.Dto.FcastWeather;

public class DistrictDAO {
	static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Weather";

	static final String USERNAME = "dd";
	static final String PASSWORD = "123456789";

	static Connection conn = null;
	static PreparedStatement pstmt = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	public static ArrayList<District> select_gridxy() {

		ArrayList<District> DdtoList = new ArrayList<District>();

		//System.out.println("====select_gridxy 가져오기 실행 ======= ");

		try {
			// 데이터베이스에 접속합니다.
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver 연결성공");

			conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

			if (conn != null) {
				System.out.println("MS-SQL 연결성공");
			} else {
				System.out.println("MS-SQ 연결실패");
			}

			String sql = "select * from area;";
			//System.out.println("지역 table 데이터 가져오는 쿼리문 확인하기: " + sql);
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				String district_ID = rs.getString("district_ID");
				String district_name_step1 = rs.getString("district_name_step1");
				int NX = rs.getInt("NX");
				int NY = rs.getInt("NY");
				double X = rs.getDouble("X");
				double Y = rs.getDouble("Y");

				District Ddto = new District(district_ID, district_name_step1, NX, NY, X, Y);

				DdtoList.add(Ddto);
				
			}

			pstmt.close();
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Class Not Found Exection");
		} catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		//System.out.println("지역array에 데이터 저장 끝");
		return DdtoList;

	}

	public static String select_areaID(String areaName) {
		// 선택지역 지역ID가겨오기 
		ArrayList<District> DdtosList = DistrictDAO.select_gridxy();
		
		String areaID = null;
		for(int a=0; a<DdtosList.size(); a++) {
			
			if(areaName.equals(DdtosList.get(a).getDistrict_name_step1())) {
				
				areaID = DdtosList.get(a).getDistrict_ID();
				
				System.out.println(areaName + "지역의 지역ID =" +areaID);
			}
		}
		return areaID;
		
	}
	
	
	

}
