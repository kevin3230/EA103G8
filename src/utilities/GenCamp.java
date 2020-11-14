package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.camp.model.CampJDBCDAO;
import com.camp.model.CampVO;
import com.carcamp.model.CarCampVO;
import com.vendor.model.VendorJDBCDAO;
import com.vendor.model.VendorVO;

public class GenCamp {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "PLAMPING";
	private static final String PASSWD = "PLAMPING";

	private static final String SELECT_VENDOR_STMT = "SELECT VD_NO FROM VENDOR";
	private static final String SELECT_CAMPTYPE_STMT = "SELECT CT_NO FROM CAMP_TYPE";
	private static final String SELECT_CAMP_STMT = "SELECT CAMP_NO FROM CAMP WHERE CAMP_VDNO = ?";
	private static final String INSERT_CAMP_STMT =
			"INSERT INTO CAMP(CAMP_NO, CAMP_VDNO, CAMP_NAME, CAMP_CTNO, CAMP_QTY, CAMP_PRICE, CAMP_STAT)"
			+ " VALUES ('C' || LPAD(SEQ_CAMPNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, 2)";
	
	public static final int[] SELECTION_CAMP_QTY = {1, 3, 5, 10, 15, 20, 25};

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement selPstmt = null;
		PreparedStatement insPstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			List<String> vdnoList = new ArrayList<>();
			List<String> ctnoList = new ArrayList<>();
			
			// 取得所有業者編號
			selPstmt = conn.prepareStatement(SELECT_VENDOR_STMT);
			rs = selPstmt.executeQuery();
			while (rs.next()) {
				vdnoList.add(rs.getString(1));
			}
			
			// 取得所有營位類型編號
			selPstmt = conn.prepareStatement(SELECT_CAMPTYPE_STMT);
			rs = selPstmt.executeQuery();
			while (rs.next()) {
				ctnoList.add(rs.getString(1));
			}
			
			// 檢查特定業者是否有營位
			selPstmt = conn.prepareStatement(SELECT_CAMP_STMT);
			insPstmt = conn.prepareStatement(INSERT_CAMP_STMT);
			for (String vd_no : vdnoList) {
				selPstmt.setString(1, vd_no);
				rs = selPstmt.executeQuery();
				
				// 若有營位資料就跳下一個業者
				if(rs.next())
					continue;
				
				// 加入隨機3-5個營位
				int ncamp = (int)(Math.random() * 3) + 3;
				for (int i = 0; i < ncamp; i++) {
					String camp_name = (char)(65 + i) + "區";
					String camp_ctno = ctnoList.get((int)(Math.random() * ctnoList.size()));
					Integer camp_qty = SELECTION_CAMP_QTY[(int)(Math.random() * SELECTION_CAMP_QTY.length)];
					Integer camp_price = (int)(Math.random() * 41) * 100 + 1000;	// 價格位於1,000-5,000之間
					
					insPstmt.setString(1, vd_no);
					insPstmt.setString(2, camp_name);
					insPstmt.setString(3, camp_ctno);
					insPstmt.setInt(4, camp_qty);
					insPstmt.setInt(5, camp_price);
					insPstmt.addBatch();
				}
			}
			insPstmt.executeBatch();
			System.out.println("新增完成！");

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (selPstmt != null) {
				try {
					selPstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (insPstmt != null) {
				try {
					insPstmt.close();
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
}
