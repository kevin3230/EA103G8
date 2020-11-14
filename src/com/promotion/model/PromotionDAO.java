package com.promotion.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.carcamp.model.CarCampVO;
import com.promocamp.model.PromoCampDAO;
import com.promocamp.model.PromoCampVO;
import com.promoeqpt.model.PromoEqptDAO;
import com.promoeqpt.model.PromoEqptVO;
import com.promofood.model.PromoFoodDAO;
import com.promofood.model.PromoFoodVO;

import oracle.jdbc.OraclePreparedStatement;

public class PromotionDAO implements PromotionDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	private static final String SQL_INSERT = "INSERT INTO PROMOTION(PRO_NO,PRO_NAME,PRO_START,PRO_END,PRO_VDNO,PRO_STAT) VALUES('P' || LPAD(SEQ_PRONO.NEXTVAL, 9, '0'),?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE PROMOTION SET PRO_NAME=?, PRO_START=?, PRO_END=?, PRO_VDNO=?, PRO_STAT=? WHERE PRO_NO=?";
	private static final String SQL_QUERYNO = "SELECT * FROM PROMOTION WHERE PRO_NO=?";
	private static final String SQL_QUERYALL = "SELECT * FROM PROMOTION";
	private static final String SQL_QUERYACTIVEALL = "SELECT * FROM PROMOTION WHERE PRO_STAT = 1";
	private static final String SQL_DELETE = "DELETE FROM PROMOTION WHERE PRO_NO=?";
	
	// Add by Yen-Fu Chen
	private static final String GET_ALIVE_BYVDNO_STMT =
			"SELECT PRO_NO, PRO_NAME, PRO_START, PRO_END, PRO_VDNO, PRO_STAT"
			+ " FROM PROMOTION WHERE PRO_STAT = 1 AND CURRENT_DATE BETWEEN PRO_START AND PRO_END AND PRO_VDNO = ? "
			+ " ORDER BY PRO_NO";
	
	private static final String SQL_QUERYALLBYVDNO = "SELECT * FROM PROMOTION WHERE PRO_VDNO = ?";

	@Override
	public String insert(PromotionVO proVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pro_no = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] seqNo = { "PRO_NO" };
			pstmt = con.prepareStatement(SQL_INSERT, seqNo);
			pstmt.setString(1, proVO.getPro_name());
			pstmt.setDate(2, proVO.getPro_start());
			pstmt.setDate(3, proVO.getPro_end());
			pstmt.setString(4, proVO.getPro_vdno());
			pstmt.setInt(5, proVO.getPro_stat());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			// get generated seq pro_no
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				pro_no = rs.getString(1);
				System.out.println("PromotionDAO insert pstmt.getGeneratedKeys() : " + pro_no);
			} else {
				System.out.println("PromotionDAO insert pstmt.getGeneratedKeys() pro_no failed.");
			}
			con.commit();
			System.out.printf("PromotionDAO: \"%s\" insert successfully.%n", proVO.getPro_name());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: \"%s\" insert failed.%n", proVO.getPro_name());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pro_no;
	}

	@Override
	public void update(PromotionVO proVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setString(6, proVO.getPro_no());
			pstmt.setString(1, proVO.getPro_name());
			pstmt.setDate(2, proVO.getPro_start());
			pstmt.setDate(3, proVO.getPro_end());
			pstmt.setString(4, proVO.getPro_vdno());
			pstmt.setInt(5, proVO.getPro_stat());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromotionDAO: \"%s\" update Successfully.%n", proVO.getPro_name());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: \"%s\" update Failed.%n", proVO.getPro_name());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public PromotionVO queryByNo(String PRO_NO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PromotionVO proVO = new PromotionVO();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYNO);
			pstmt.setString(1, PRO_NO);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				proVO.setPro_no(rs.getString("PRO_NO"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_start(rs.getDate("PRO_START"));
				proVO.setPro_end(rs.getDate("PRO_END"));
				proVO.setPro_vdno(rs.getNString("PRO_VDNO"));
				proVO.setPro_stat(rs.getInt("PRO_STAT"));
			}

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return proVO;
	}

	@Override
	public List<PromotionVO> queryAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PromotionVO> proVOList = new ArrayList<PromotionVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromotionVO proVO = new PromotionVO();
				proVO.setPro_no(rs.getString("PRO_NO"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_start(rs.getDate("PRO_START"));
				proVO.setPro_end(rs.getDate("PRO_END"));
				proVO.setPro_vdno(rs.getNString("PRO_VDNO"));
				proVO.setPro_stat(rs.getInt("PRO_STAT"));
				proVOList.add(proVO);
			}

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return proVOList;
	}

	@Override
	public void delete(String pro_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, pro_no);
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromotionDAO: \"%s\" delete successfully.%n", pro_no);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: \"%s\" delete failed.%n", pro_no);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String[] pro_noArr) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pro_noArr.length);
			for (String pro_no : pro_noArr) {
				pstmt.setString(1, pro_no);
				pstmt.executeUpdate();
				System.out.printf("PromotionDAO: deleting(String[]) \"%s\"...%n", pro_no);
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromotionDAO: delete(String[]) total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: delete(String[]) total %d rows failed.%n", ((OraclePreparedStatement) pstmt).getExecuteBatch());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void delete(PromotionVO proVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, proVO.getPro_no());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromotionDAO: \"%s\" delete successfully.%n", proVO.getPro_name());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: \"%s\" delete failed.%n", proVO.getPro_name());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(List<PromotionVO> proVOList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(proVOList.size());
			for (PromotionVO proVOi : proVOList) {
				pstmt.setString(1, proVOi.getPro_no());
				pstmt.executeUpdate();
				System.out.printf("PromotionDAO: deleting \"%s\"...%n", proVOi.getPro_no());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromotionDAO: delete total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: delete total %d rows failed.%n", proVOList.size());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String insert(PromotionVO proVO, List<PromoCampVO> pcVOList, List<PromoEqptVO> peVOList,
			List<PromoFoodVO> pfVOList) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pro_no = null;
		try {
			// JDBC general settings
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			// Promotion insert
			String[] seqNo = { "PRO_NO" };
			pstmt = con.prepareStatement(SQL_INSERT, seqNo);
			pstmt.setString(1, proVO.getPro_name());
			pstmt.setDate(2, proVO.getPro_start());
			pstmt.setDate(3, proVO.getPro_end());
			pstmt.setString(4, proVO.getPro_vdno());
			pstmt.setInt(5, proVO.getPro_stat());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			// PromoCamp, PromoEqpt, PromoFood insert
			// 1. get auto-generated pro_no
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				pro_no = rs.getString(1);
				System.out.println("PromotionDAO insert pstmt.getGeneratedKeys() : " + pro_no);
			} else {
				System.out.println("PromotionDAO insert pstmt.getGeneratedKeys() pro_no failed.");
			}
			// 2. set pro_no to items VO.
			for(PromoCampVO pcVO : pcVOList) {
				pcVO.setPc_prono(pro_no);
			}
			for(PromoEqptVO peVO : peVOList) {
				peVO.setPe_prono(pro_no);
			}
			for(PromoFoodVO pfVO : pfVOList) {
				pfVO.setPf_prono(pro_no);
			}
			// 3. transfer pro_no to items' DAO method.
			PromoCampDAO pcDAO = new PromoCampDAO();
			PromoEqptDAO peDAO = new PromoEqptDAO();
			PromoFoodDAO pfDAO = new PromoFoodDAO();
			if(pcVOList.size() != 0) {
				pcDAO.insert(pcVOList, con);
			}
			if(peVOList.size() != 0) {
				peDAO.insert(peVOList, con);
			}
			if(pfVOList.size() != 0) {
				pfDAO.insert(pfVOList, con);
			}
			// Commit Promotion, PromoCamp, PromoEqpt, PromoFood all transactions.
			con.commit();
			System.out.printf("PromotionDAO: \"%s\" insert(items) successfully.%n", proVO.getPro_name());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromotionDAO: \"%s\" insert(items) failed.%n", proVO.getPro_name());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pro_no;
	}
	
	// Add by Yen-Fu Chen
	@Override
	public List<PromotionVO> getAlivePromosByVdno(String vd_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PromotionVO promoVO = null;
		List<PromotionVO> list = new ArrayList<>();
		
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = conn.prepareStatement(GET_ALIVE_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {				
				promoVO = new PromotionVO();
				promoVO.setPro_no(rs.getString(1));
				promoVO.setPro_name(rs.getString(2));
				promoVO.setPro_start(rs.getDate(3));
				promoVO.setPro_end(rs.getDate(4));
				promoVO.setPro_vdno(rs.getNString(5));
				promoVO.setPro_stat(rs.getInt(6));
				list.add(promoVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
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
		return list;
	}

	@Override
	public List<PromotionVO> getAllByVdno(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PromotionVO> proVOList = new ArrayList<PromotionVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYALLBYVDNO);
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromotionVO proVO = new PromotionVO();
				proVO.setPro_no(rs.getString("PRO_NO"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_start(rs.getDate("PRO_START"));
				proVO.setPro_end(rs.getDate("PRO_END"));
				proVO.setPro_vdno(rs.getNString("PRO_VDNO"));
				proVO.setPro_stat(rs.getInt("PRO_STAT"));
				proVOList.add(proVO);
			}

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return proVOList;
	}

//	public static void main(String[] args) {
//		String pro_no = "P000000001";
//		String pro_name = "JavaDAO第一波促銷";
//		long start = new java.util.Date().getTime();
//		java.sql.Date pro_start = new java.sql.Date(start);
//		long end = new java.util.Date().getTime() + (10 * 24 * 60 * 60 * 1000); // 10 days later
//		java.sql.Date pro_end = new java.sql.Date(end);
//		String pro_vdno = "V000000001";
//		int pro_stat = 0;
//		PromotionVO proVO = new PromotionVO(pro_no, pro_name, pro_start, pro_end, pro_vdno, pro_stat);
//		PromotionDAO proDAO = new PromotionDAO();
//////		// Test Promotion Insert DAO
////		proDAO.insert(proVO);
////		// Test Promotion Update DAO
////		proDAO.update(proVO);
////		// Test Promotion Query By PRO_NO
////		PromotionVO testQueryNo = proDAO.queryByNo(pro_no);
////		System.out.printf("%s PRO_NO is : %s%n", pro_no, testQueryNo.getPro_no());
////		System.out.printf("%s PRO_NAME is : %s%n", pro_no, testQueryNo.getPro_name());
////		System.out.printf("%s PRO_START is : %s%n", pro_no, testQueryNo.getPro_start());
////		System.out.printf("%s PRO_END is : %s%n", pro_no, testQueryNo.getPro_end(), pro_no);
////		System.out.printf("%s PRO_VDNO is : %s%n", pro_no, testQueryNo.getPro_vdno(), pro_no);
////		System.out.printf("%s PRO_STAT is : %d%n", pro_no, testQueryNo.getPro_stat(), pro_no);
////		List<PromotionVO> proVOList = proDAO.queryAll();
////		for (PromotionVO proVOi : proVOList) {
////			System.out.println("===============================");
////			System.out.println("PRO_NO : " + proVOi.getPro_no());
////			System.out.println("PRO_NAME : " + proVOi.getPro_name());
////			System.out.println("PRO_START : " + proVOi.getPro_start());
////			System.out.println("PRO_END : " + proVOi.getPro_end());
////			System.out.println("PRO_VDNO : " + proVOi.getPro_vdno());
////			System.out.println("PRO_STAT : " + proVOi.getPro_stat());
////		}
//////		// Test Promotion Delete DAO
//////		proDAO.delete(proVO);
//////		// Test Promotion List Delete DAO
//////		List<PromotionVO> proVOList = proDAO.queryAll();
//////		proVOList.remove(0);
//////		proDAO.delete(proVOList);
//////		// Test Promotion String[] Delete DAO
//////		String[] pro_noList = {"P000000007", "P000000008"};
//////		proDAO.delete(pro_noList);
//////		// Test Promotion insert with PromoCamp, PromoEqpt, PromoFood items.
//////		List<PromoCampVO> pcVOList = new ArrayList<PromoCampVO>();
//////		PromoCampVO pcVO1 = new PromoCampVO("", "C000000001", 1000);
//////		PromoCampVO pcVO2 = new PromoCampVO("", "C000000002", 1000);
//////		pcVOList.add(pcVO1);
//////		pcVOList.add(pcVO2);
//////		List<PromoEqptVO> peVOList = new ArrayList<PromoEqptVO>();
//////		PromoEqptVO peVO1 = new PromoEqptVO("", "E000000001", 500);
//////		PromoEqptVO peVO2 = new PromoEqptVO("", "E000000002", 500);
//////		peVOList.add(peVO1);
//////		peVOList.add(peVO2);
//////		List<PromoFoodVO> pfVOList = new ArrayList<PromoFoodVO>();
//////		PromoFoodVO pfVO1 = new PromoFoodVO("", "F000000004", 100);
//////		PromoFoodVO pfVO2 = new PromoFoodVO("", "F000000005", 100);
//////		pfVOList.add(pfVO1);
//////		pfVOList.add(pfVO2);
//////		proDAO.insert(proVO, pcVOList, peVOList, pfVOList);
////		
////		// Add by Yen-Fu Chen
////		PromotionDAO dao = new PromotionDAO();
////		List<PromotionVO> list = dao.getAlivePromosByVdno("V000000001");
////		for (PromotionVO promoVO : list) {
////			System.out.println(promoVO.getPro_no());
////			System.out.println(promoVO.getPro_name());
////			System.out.println(promoVO.getPro_start());
////			System.out.println(promoVO.getPro_end());
////			System.out.println(promoVO.getPro_vdno());
////			System.out.println(promoVO.getPro_stat());
////			System.out.println();
////		}
//		
////		// Test getAllByVdno(String vd_no)
////		for(PromotionVO proVOi : proDAO.getAllByVdno(pro_vdno)) {
////			System.out.println(proVOi.getPro_no());
////			System.out.println(proVOi.getPro_name());
////			System.out.println(proVOi.getPro_start());
////			System.out.println(proVOi.getPro_end());
////			System.out.println(proVOi.getPro_vdno());
////			System.out.println(proVOi.getPro_stat());
////		}
//	}

}