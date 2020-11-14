package utilities;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.equipment.model.EquipmentVO;

public class GenEqptAvail {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USERID = "PLAMPING";
	private static final String PASSWD = "PLAMPING";

	private static final String SELECT_STMT = "SELECT EQPT_NO, EQPT_QTY FROM EQUIPMENT";
	private static final String DELETE_STMT = "DELETE FROM EQPT_AVAIL";
	private static final String INSERT_STMT = "INSERT INTO EQPT_AVAIL(EA_EQPTNO, EA_DATE, EA_QTY) VALUES (?, ?, ?)";

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			List<EquipmentVO> list = new ArrayList<>();

			// 取得所有裝備編號
			pstmt = conn.prepareStatement(SELECT_STMT);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				EquipmentVO eqptVO = new EquipmentVO();
				eqptVO.setEqpt_no(rs.getString(1));
				eqptVO.setEqpt_qty(rs.getInt(2));
				list.add(eqptVO);
			}
			pstmt.close();

			// 刪除EqptAvail原有資料
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.executeUpdate();
			pstmt.close();

			// 插入假資料
			pstmt = conn.prepareStatement(INSERT_STMT);
			for (EquipmentVO eqptVO : list) {				
				// 取得今日0點0分0秒的time
				java.util.Date now = new java.util.Date();
				long time = now.getTime() / 86_400_000;
				
				// 插入90天的假資料
				for (int i = 1; i <= 90; i++) {
					Date date = new Date((time + i) * 86_400_000);
					int qty = (int) (Math.random() * (eqptVO.getEqpt_qty() + 1));		// 隨機取得數量
					if (qty < eqptVO.getEqpt_qty()) {		// 若為營位最大數量者，不插入
						pstmt.setString(1, eqptVO.getEqpt_no());
						pstmt.setDate(2, date);
						pstmt.setInt(3, qty);
						pstmt.addBatch();
					}
				}
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("EQPT_AVAIL 假資料新增完成！");

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
}