package utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenOrderItems {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "PLAMPING";
	private static final String PASSWD = "PLAMPING";
	
	private static final String OM_GETALL = "SELECT OM_NO FROM ORDER_MASTER WHERE OM_VDNO = ? AND OM_MEMNO = ?";
	private static final String CAMP_GETALL = "SELECT CAMP_NO, CAMP_PRICE FROM CAMP WHERE CAMP_VDNO = ?";
	private static final String OC_INSERT = "INSERT INTO ORDER_CAMP(OC_NO, OC_CAMPNO, OC_OMNO, OC_QTY, OC_LISTPRICE, OC_PRICE, OC_START, OC_END)"
			+ " VALUES ('OC' || LPAD(SEQ_OCNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String EQPT_GETALL = "SELECT EQPT_NO, EQPT_PRICE FROM EQUIPMENT WHERE EQPT_VDNO = ?";
	private static final String OE_INSERT = "INSERT INTO ORDER_EQPT(OE_NO, OE_EQPTNO, OE_OMNO, OE_QTY, OE_LISTPRICE,"
			+ " OE_PRICE, OE_EXPGET, OE_EXPBACK)"
			+ " VALUES ('OE' || LPAD(SEQ_OENO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String FOOD_GETALL = "SELECT FOOD_NO, FOOD_PRICE FROM FOOD WHERE FOOD_VDNO = ?";
	private static final String OF_INSERT = "INSERT INTO order_food (of_no, of_foodno, of_omno, of_qty, of_listprice, of_price) "
			+ "VALUES('OF' || LPAD(SEQ_OFNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";

	public static void main(String[] args) {
		String vd_no = "V000000001";
		String mem_no = "M000000001";
		GenOrderItems obj = new GenOrderItems();
		obj.genOrderCamp(mem_no, vd_no, 1, 4);
		obj.genOrderEqpt(mem_no, vd_no, 1, 4);
		obj.genOrderFood(mem_no, vd_no, 1, 4);
		
	}
	
	public void genOrderCamp(String mem_no, String vd_no, int omNum, int campNum) {
		/*
		 * omNum : how many OrderMaster record to be used as OC_OMNO?
		 * campNum : how many Camp to be used as OrderCamp OC_CAMPNO?
		 * 
		 */
		// Current date format
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		long now = new java.util.Date().getTime();
		// JDBC Settings
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			
			// Get all om_no
			List<String> omNoList = new ArrayList<String>();
			pstmt = conn.prepareStatement(OM_GETALL);
			pstmt.setString(1, vd_no);
			pstmt.setString(2, mem_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				omNoList.add(rs.getString(1));
			}
			rs.close();
			pstmt.close();
			
			// Get all camp_no
			List<String> campNoList = new ArrayList<String>();
			List<Integer> campPriceList = new ArrayList<Integer>();
			pstmt = conn.prepareStatement(CAMP_GETALL);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				campNoList.add(rs.getString(1));
				campPriceList.add(rs.getInt(2));
			}
			rs.close();
			pstmt.close();
			
			// OrderCamp
			for (String om_no : omNoList.subList(0, omNum)) {
				String oc_omno = om_no;
				String oc_end_str = dateFmt.format(now + (86400000 * (int)(Math.random()*10 + 1)));
				for (int i = 0; i < campNum; i++) {
					// attribute settings
					String oc_campno = campNoList.get(i);
					int oc_qty = (int) (Math.random() * 10) + 1;
					int oc_listprice = campPriceList.get(i);
					int oc_price = oc_listprice - 10;
					Date oc_start = Date.valueOf(dateFmt.format(now));
					Date oc_end = Date.valueOf(oc_end_str);
					// OrderCampService addOrderCamp method
					pstmt = conn.prepareStatement(OC_INSERT);
					pstmt.setString(1, oc_campno);
					pstmt.setString(2, oc_omno);
					pstmt.setInt(3, oc_qty);
					pstmt.setInt(4, oc_listprice);
					pstmt.setInt(5, oc_price);
					pstmt.setDate(6, oc_start);
					pstmt.setDate(7, oc_end);
					pstmt.executeUpdate();
					System.out.printf("GenOrderItems %s - %s inserted.\n", oc_omno, oc_campno);
					pstmt.close();
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void genOrderEqpt(String mem_no, String vd_no, int omNum, int eqptNum) {
		/*
		 * omNum : how many OrderMaster record to be used as OC_OMNO?
		 * campNum : how many Camp to be used as OrderCamp OC_CAMPNO?
		 * 
		 */
		// Current date format
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
		long now = new java.util.Date().getTime();
		// JDBC Settings
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			
			// Get all om_no
			List<String> omNoList = new ArrayList<String>();
			pstmt = conn.prepareStatement(OM_GETALL);
			pstmt.setString(1, vd_no);
			pstmt.setString(2, mem_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				omNoList.add(rs.getString(1));
			}
			rs.close();
			pstmt.close();
			
			// Get all camp_no
			List<String> eqptNoList = new ArrayList<String>();
			List<Integer> eqptPriceList = new ArrayList<Integer>();
			pstmt = conn.prepareStatement(EQPT_GETALL);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				eqptNoList.add(rs.getString(1));
				eqptPriceList.add(rs.getInt(2));
			}
			rs.close();
			pstmt.close();
			
			// OrderEqpt
			for (String om_no : omNoList.subList(0, omNum)) {
				String oe_omno = om_no;
				for (int i = 0; i < eqptNum; i++) {
					// attribute settings
					String oe_eqptno = eqptNoList.get(i);
					int oe_qty = (int) (Math.random() * 10) + 1;
					int oe_listprice = eqptPriceList.get(i);
					int oe_price = oe_listprice - 10;
					Date oe_expget = Date.valueOf(dateFmt.format(now));
					Date oe_expback = Date.valueOf(dateFmt.format(now + (86400000 * (int)(Math.random()*10 + 1))));
					// OrderCampService addOrderCamp method
					pstmt = conn.prepareStatement(OE_INSERT);
					pstmt.setString(1, oe_eqptno);
					pstmt.setString(2, oe_omno);
					pstmt.setInt(3, oe_qty);
					pstmt.setInt(4, oe_listprice);
					pstmt.setInt(5, oe_price);
					pstmt.setDate(6, oe_expget);
					pstmt.setDate(7, oe_expback);
					pstmt.executeUpdate();
					System.out.printf("GenOrderItems %s - %s inserted.\n", oe_omno, oe_eqptno);
					pstmt.close();
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void genOrderFood(String mem_no, String vd_no, int omNum, int foodNum) {
		/*
		 * omNum : how many OrderMaster record to be used as OC_OMNO?
		 * campNum : how many Camp to be used as OrderCamp OC_CAMPNO?
		 * 
		 */
		// JDBC Settings
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			
			// Get all om_no
			List<String> omNoList = new ArrayList<String>();
			pstmt = conn.prepareStatement(OM_GETALL);
			pstmt.setString(1, vd_no);
			pstmt.setString(2, mem_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				omNoList.add(rs.getString(1));
			}
			rs.close();
			pstmt.close();
			
			// Get all camp_no
			List<String> foodNoList = new ArrayList<String>();
			List<Integer> foodPriceList = new ArrayList<Integer>();
			pstmt = conn.prepareStatement(FOOD_GETALL);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				foodNoList.add(rs.getString(1));
				foodPriceList.add(rs.getInt(2));
			}
			rs.close();
			pstmt.close();
			
			// OrderEqpt
			for (String om_no : omNoList.subList(0, omNum)) {
				String of_omno = om_no;
				for (int i = 0; i < foodNum; i++) {
					// attribute settings
					String of_foodno = foodNoList.get(i);
					int of_qty = (int) (Math.random() * 10) + 1;
					int of_listprice = foodPriceList.get(i);
					int of_price = of_listprice - 10;
					// OrderCampService addOrderCamp method
					pstmt = conn.prepareStatement(OF_INSERT);
					pstmt.setString(1, of_foodno);
					pstmt.setString(2, of_omno);
					pstmt.setInt(3, of_qty);
					pstmt.setInt(4, of_listprice);
					pstmt.setInt(5, of_price);
					pstmt.executeUpdate();
					System.out.printf("GenOrderItems %s - %s inserted.\n", of_omno, of_foodno);
					pstmt.close();
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
