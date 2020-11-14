package utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ordermaster.model.OrderMasterVO;

public class GenOrderMaster_10 {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "PLAMPING";
	private static final String PASSWD = "PLAMPING";

	private static final String MEMBERS_SELECT = "SELECT MEM_NO FROM MEMBERS";
	private static final String VENDOR_SELECT = "SELECT VD_NO FROM VENDOR ORDER BY VD_NO";
	private static final String INSERT_STMT = "INSERT INTO order_master (om_no, om_memno, om_vdno, om_txnamt, om_txntime, om_cardno) VALUES('O'|| LPAD(SEQ_OMNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?)";

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			List<String> memNoList = new ArrayList<String>();
			String vd_no = null;
			
			// Delete all OrderMaster with OrderItems
			deleteOrderMasterWithDetails(conn);
			
			// Get all members PK
			pstmt = conn.prepareStatement(MEMBERS_SELECT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				memNoList.add(rs.getString(1));
			}
			pstmt.close();
			
			// Get one vendor PK
			pstmt = conn.prepareStatement(VENDOR_SELECT);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				vd_no = rs.getString(1);
				break;
			}
			
			// Insert fake order master rows with existing members.
			pstmt = conn.prepareStatement(INSERT_STMT);
			SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
			long nowLong = new java.util.Date().getTime();
			for (String mem_no : memNoList) {				
				OrderMasterVO omVO = new OrderMasterVO();
				omVO.setOm_memno(mem_no);
				omVO.setOm_vdno(vd_no);
				int randAmt = (int)(Math.random() * 10000);
				omVO.setOm_txnamt(randAmt);
				String memLastChar = mem_no.substring(mem_no.length() - 1);
				Long txnDatei = nowLong + (Long.parseLong(memLastChar) * 864000000);
				Date txnDate = Date.valueOf(dateFmt.format(new java.util.Date(txnDatei)));
				omVO.setOm_txntime(txnDate);
				omVO.setOm_cardno("111122223333000" + memLastChar);
				pstmt.setString(1, omVO.getOm_memno());
				pstmt.setString(2, omVO.getOm_vdno());
				pstmt.setInt(3, omVO.getOm_txnamt());
				pstmt.setDate(4, omVO.getOm_txntime());
				pstmt.setString(5, omVO.getOm_cardno());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("主訂單假資料新增完成！");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void deleteOrderMasterWithDetails(Connection conn) {
		try {
			conn.setAutoCommit(false);
			// SQL
			String SQL_SELECT_OM = "SELECT OM_NO FROM ORDER_MASTER";
			String SQL_DELETE_OC = "DELETE FROM ORDER_CAMP WHERE OC_OMNO = ?";
			String SQL_DELETE_OE = "DELETE FROM ORDER_EQPT WHERE OE_OMNO = ?";
			String SQL_DELETE_OF = "DELETE FROM ORDER_FOOD WHERE OF_OMNO = ?";
			String SQL_DELETE_OM = "DELETE FROM ORDER_MASTER";
			
			// Get all existing OM_NO
			List<String> omNoList = new ArrayList<String>();
			PreparedStatement pstmt = conn.prepareStatement(SQL_SELECT_OM);
			ResultSet rs = pstmt.executeQuery();
			int omQueryRows = 0;
			while(rs.next()) {
				omNoList.add(rs.getString(1));
				omQueryRows ++;
			}
			rs.close();
			pstmt.close();
			System.out.printf("Query ORDER_MASTER : %d rows. \n", omQueryRows);
			
			// Delete all ORDER_CAMP rows
			pstmt = conn.prepareStatement(SQL_DELETE_OC);
			for(String om_no : omNoList) {
				pstmt.setString(1, om_no);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("Delete ORDER_CAMP complete.");
			
			// Delete all ORDER_EQPT rows
			pstmt = conn.prepareStatement(SQL_DELETE_OE);
			for(String om_no : omNoList) {
				pstmt.setString(1, om_no);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("Delete ORDER_EQPT complete.");
			
			// Delete all ORDER_FOOD rows
			pstmt = conn.prepareStatement(SQL_DELETE_OF);
			for(String om_no : omNoList) {
				pstmt.setString(1, om_no);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("Delete ORDER_FOOD complete.");
			
			// Delete all ORDER_MASTER rows
			pstmt = conn.prepareStatement(SQL_DELETE_OM);
			int omDeleteRows = pstmt.executeUpdate();
			pstmt.close();
			System.out.printf("Delete ORDER_MASTER : %d rows. \n", omDeleteRows);
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}