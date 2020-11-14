package utilities;

import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class GenCarItems {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "PLAMPING";
	private static final String PASSWD = "PLAMPING";
	
	private static final String CAMP_QUERY_VDNO = "SELECT CAMP_NO FROM CAMP WHERE CAMP_VDNO = ?";
	private static final String EQPT_QUERY_VDNO = "SELECT EQPT_NO FROM EQUIPMENT WHERE EQPT_VDNO = ?";
	private static final String FOOD_QUERY_VDNO = "SELECT FOOD_NO FROM FOOD WHERE FOOD_VDNO = ?";

	private static final String CARCAMP_INSERT = "INSERT INTO CAR_CAMP(CC_CAMPNO, CC_MEMNO, CC_QTY, CC_START, CC_END) VALUES(?, ?, ?, ?, ?)";
	private static final String CAREQPT_INSERT = "INSERT INTO CAR_EQPT(CE_NO, CE_EQPTNO, CE_MEMNO, CE_QTY, CE_EXPGET, CE_EXPBACK) VALUES('CE' || LPAD(SEQ_CENO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";
	private static final String CARFOOD_INSERT = "INSERT INTO CAR_FOOD(CF_FOODNO, CF_MEMNO, CF_QTY) VALUES(?, ?, ?)";

	private static final String CARCAMP_DELETE = "DELETE FROM CAR_CAMP WHERE CC_MEMNO = ?";
	private static final String CAREQPT_DELETE = "DELETE FROM CAR_EQPT WHERE CE_MEMNO = ?";
	private static final String CARFOOD_DELETE = "DELETE FROM CAR_FOOD WHERE CF_MEMNO = ?";
	
	private static Connection conn = null;
	private static PreparedStatement pstmt = null;
	private static ResultSet rs = null;
	
	public static void main(String[] args) {
		
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			
			conn.setAutoCommit(false);
			
			String mem_no = "M000000002";
			String vd_no = "V000000001";
			SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
			long nowLong = new java.util.Date().getTime();
			
			// Delete all mem_no car items
			pstmt = conn.prepareStatement(CARCAMP_DELETE);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(CAREQPT_DELETE);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
			pstmt.close();
			
			pstmt = conn.prepareStatement(CARFOOD_DELETE);
			pstmt.setString(1, mem_no);
			pstmt.executeUpdate();
			pstmt.close();
			
			// Camp
			pstmt = conn.prepareStatement(CAMP_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			int campCount = 0;
			while(rs.next()) {
				campCount++;
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(CAMP_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			List<String> campNoList = new ArrayList<String>();
			for(int i = 0; i < (int)(Math.random() * campCount) + 1; i++) {
				if(rs.next()) {
					campNoList.add(rs.getString(1));
				}
			}
			rs.close();
			pstmt.close();
			
			for(String camp_no : campNoList) {
				pstmt = conn.prepareStatement(CARCAMP_INSERT);
				pstmt.setString(1, camp_no);
				pstmt.setString(2, mem_no);
				pstmt.setInt(3, (int)(Math.random() * 10) + 1);
				pstmt.setDate(4, java.sql.Date.valueOf(dateFmt.format(nowLong)));
				pstmt.setDate(5, java.sql.Date.valueOf(dateFmt.format(nowLong + (int)((Math.random() * 2 + 1) * 86400000))));
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			// Eqpt
			pstmt = conn.prepareStatement(EQPT_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			int eqptCount = 0;
			while(rs.next()) {
				eqptCount++;
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(EQPT_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			List<String> eqptNoList = new ArrayList<String>();
			for(int i = 0; i < (int)(Math.random() * eqptCount) + 1; i++) {
				if(rs.next()) {
					eqptNoList.add(rs.getString(1));
				}
			}
			rs.close();
			pstmt.close();
			
			for(String eqpt_no : eqptNoList) {
				pstmt = conn.prepareStatement(CAREQPT_INSERT);
				pstmt.setString(1, eqpt_no);
				pstmt.setString(2, mem_no);
				pstmt.setInt(3, (int)(Math.random() * 10) + 1);
				pstmt.setDate(4, java.sql.Date.valueOf(dateFmt.format(nowLong)));
				pstmt.setDate(5, java.sql.Date.valueOf(dateFmt.format(nowLong + (int)((Math.random() * 5 + 1) * 86400000))));
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			// Food
			pstmt = conn.prepareStatement(FOOD_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			int foodCount = 0;
			while(rs.next()) {
				foodCount++;
			}
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(FOOD_QUERY_VDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			List<String> foodNoList = new ArrayList<String>();
			for(int i = 0; i < (int)(Math.random() * foodCount) + 1; i++) {
				if(rs.next()) {
					foodNoList.add(rs.getString(1));
				}
			}
			rs.close();
			pstmt.close();
			
			for(String food_no : foodNoList) {
				pstmt = conn.prepareStatement(CARFOOD_INSERT);
				pstmt.setString(1, food_no);
				pstmt.setString(2, mem_no);
				pstmt.setInt(3, (int)(Math.random() * 10) + 1);
				pstmt.executeUpdate();
				pstmt.close();
			}
			
			conn.commit();
			System.out.println("GenCarItems.java transaction commit.");
			
		} catch(SQLException se) {
			try {
				conn.rollback();
				System.out.println("GenCarItems.java transaction rollback.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch(ClassNotFoundException ce) {
			ce.printStackTrace();
		}
		
	}
}
