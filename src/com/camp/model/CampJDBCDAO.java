package com.camp.model;

import java.sql.*;
import java.util.*;

public class CampJDBCDAO implements CampDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO CAMP (CAMP_NO, CAMP_VDNO, CAMP_NAME, CAMP_CTNO, CAMP_QTY, CAMP_PRICE, CAMP_STAT, CAMP_PIC) VALUES ('C' || LPAD(SEQ_CAMPNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT CAMP_NO, CAMP_VDNO, CAMP_NAME, CAMP_CTNO, CAMP_QTY, CAMP_PRICE, CAMP_STAT, CAMP_PIC FROM CAMP ORDER BY CAMP_NO";
	private static final String GET_ONE_STMT = "SELECT CAMP_NO, CAMP_VDNO, CAMP_NAME, CAMP_CTNO, CAMP_QTY, CAMP_PRICE, CAMP_STAT, CAMP_PIC FROM CAMP WHERE CAMP_NO = ?";
//	private static final String REAL_DELETE_STMT = "DELETE FROM CAMP WHERE CAMP_NO = ?";	
	private static final String UPDATE_STMT = "UPDATE CAMP SET CAMP_VDNO=?, CAMP_NAME=?, CAMP_CTNO=?, CAMP_QTY=?, CAMP_PRICE=?, CAMP_STAT=?, CAMP_PIC=? WHERE CAMP_NO = ?";
	private static final String DELETE_STMT = "UPDATE CAMP SET CAMP_STAT=? WHERE CAMP_NO = ?"; // 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	private static final String UPDATE_STAT_STMT = "UPDATE CAMP SET CAMP_STAT=? WHERE CAMP_NO = ?";

	// Add by Yen-Fu Chen
	private static final String GET_MULTI_BYVDNO_STMT =
			"SELECT CAMP_NO, CAMP_VDNO, CAMP_NAME, CAMP_CTNO, CAMP_QTY, CAMP_PRICE, CAMP_STAT, CAMP_PIC"
			+ " FROM CAMP WHERE CAMP_VDNO = ? ORDER BY CAMP_NO";
	
	private static final String GET_ALLCT_BYVDNO = "SELECT CAMP_CTNO FROM CAMP WHERE CAMP_VDNO = ? AND CAMP_STAT >= 0 ORDER BY CAMP_NO"; 
	private static final String GET_MULTI_BYVDNOCTNO_STMT = "SELECT * FROM CAMP WHERE CAMP_VDNO = ? AND CAMP_CTNO = ? AND CAMP_STAT >= 0 ORDER BY CAMP_NO";
	
	// Author Jeff
	private static final String GET_EXIST_BYVDNO = "SELECT * FROM CAMP WHERE CAMP_VDNO = ? AND CAMP_STAT >= 0  ORDER BY CAMP_NO";
	
	@Override
	public void insert(CampVO campVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, campVO.getCamp_vdno());
			pstmt.setString(2, campVO.getCamp_name());
			pstmt.setString(3, campVO.getCamp_ctno());
			pstmt.setInt(4, campVO.getCamp_qty());
			pstmt.setInt(5, campVO.getCamp_price());
			pstmt.setInt(6, campVO.getCamp_stat());
			pstmt.setBytes(7, campVO.getCamp_pic());

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(CampVO campVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, campVO.getCamp_vdno());
			pstmt.setString(2, campVO.getCamp_name());
			pstmt.setString(3, campVO.getCamp_ctno());
			pstmt.setInt(4, campVO.getCamp_qty());
			pstmt.setInt(5, campVO.getCamp_price());
			pstmt.setInt(6, campVO.getCamp_stat());
			pstmt.setBytes(7, campVO.getCamp_pic());
			pstmt.setString(8, campVO.getCamp_no());

			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

//	@Override
//	public void delete(String camp_no) {
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(REAL_DELETE_STMT);
//			
//			pstmt.setString(1, camp_no);
//			pstmt.executeUpdate();
//			
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//		}
//	}
	
	// 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	@Override
	public void delete(String camp_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, -1);
			pstmt.setString(2, camp_no);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public CampVO findByPrimaryKey(String camp_no) {
		
		CampVO campVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, camp_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCamp_no(rs.getString("CAMP_NO"));
				campVO.setCamp_vdno(rs.getString("CAMP_VDNO"));
				campVO.setCamp_name(rs.getString("CAMP_NAME"));
				campVO.setCamp_ctno(rs.getString("CAMP_CTNO"));
				campVO.setCamp_qty(rs.getInt("CAMP_QTY"));
				campVO.setCamp_price(rs.getInt("CAMP_PRICE"));
				campVO.setCamp_stat(rs.getInt("CAMP_STAT"));			
				campVO.setCamp_pic(rs.getBytes("CAMP_PIC"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return campVO;
	}

	@Override
	public List<CampVO> getAll() {
		
		List<CampVO> list = new ArrayList<CampVO>();
		CampVO campVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCamp_no(rs.getString("CAMP_NO"));
				campVO.setCamp_vdno(rs.getString("CAMP_VDNO"));
				campVO.setCamp_name(rs.getString("CAMP_NAME"));
				campVO.setCamp_ctno(rs.getString("CAMP_CTNO"));
				campVO.setCamp_qty(rs.getInt("CAMP_QTY"));
				campVO.setCamp_price(rs.getInt("CAMP_PRICE"));
				campVO.setCamp_stat(rs.getInt("CAMP_STAT"));			
				campVO.setCamp_pic(rs.getBytes("CAMP_PIC"));
				
				list.add(campVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return list;
	}
	
	@Override
	public List<CampVO> getCampsByVdno(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampVO campVO = null;
		List<CampVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MULTI_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCamp_no(rs.getString("CAMP_NO"));
				campVO.setCamp_vdno(rs.getString("CAMP_VDNO"));
				campVO.setCamp_name(rs.getString("CAMP_NAME"));
				campVO.setCamp_ctno(rs.getString("CAMP_CTNO"));
				campVO.setCamp_qty(rs.getInt("CAMP_QTY"));
				campVO.setCamp_price(rs.getInt("CAMP_PRICE"));
				campVO.setCamp_stat(rs.getInt("CAMP_STAT"));			
				campVO.setCamp_pic(rs.getBytes("CAMP_PIC"));
				list.add(campVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return list;
	}

	@Override
	public void updateCampStatTo2(String camp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 2);
			pstmt.setString(2, camp_no);
			
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void updateCampStatTo1(String camp_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, camp_no);
			
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public Set<String> getAllCt_no(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<String> set = new LinkedHashSet<String>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALLCT_BYVDNO);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String camp_ctno = rs.getString("CAMP_CTNO");
				set.add(camp_ctno);;
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return set;
	}

	@Override
	public List<CampVO> getCampsByVdnoCtno(String vd_no, String ct_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampVO campVO = null;
		List<CampVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MULTI_BYVDNOCTNO_STMT);
			
			pstmt.setString(1, vd_no);
			pstmt.setString(2, ct_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCamp_no(rs.getString("CAMP_NO"));
				campVO.setCamp_vdno(rs.getString("CAMP_VDNO"));
				campVO.setCamp_name(rs.getString("CAMP_NAME"));
				campVO.setCamp_ctno(rs.getString("CAMP_CTNO"));
				campVO.setCamp_qty(rs.getInt("CAMP_QTY"));
				campVO.setCamp_price(rs.getInt("CAMP_PRICE"));
				campVO.setCamp_stat(rs.getInt("CAMP_STAT"));			
				campVO.setCamp_pic(rs.getBytes("CAMP_PIC"));
				list.add(campVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return list;
	}

	@Override
	public List<CampVO> getExistCampsByVdno(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampVO campVO = null;
		List<CampVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_EXIST_BYVDNO);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campVO = new CampVO();
				campVO.setCamp_no(rs.getString("CAMP_NO"));
				campVO.setCamp_vdno(rs.getString("CAMP_VDNO"));
				campVO.setCamp_name(rs.getString("CAMP_NAME"));
				campVO.setCamp_ctno(rs.getString("CAMP_CTNO"));
				campVO.setCamp_qty(rs.getInt("CAMP_QTY"));
				campVO.setCamp_price(rs.getInt("CAMP_PRICE"));
				campVO.setCamp_stat(rs.getInt("CAMP_STAT"));			
//				campVO.setCamp_pic(rs.getBytes("CAMP_PIC"));
				list.add(campVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return list;
	}
	
//	public static void main(String[] args) {
//		CampJDBCDAO dao = new CampJDBCDAO(); 
//		
//		CampVO campVO1 = new CampVO();
//		campVO1.setCamp_vdno("V000000001");
//		campVO1.setCamp_name("親子區");
//		campVO1.setCamp_ctno("CT00000004");
//		campVO1.setCamp_qty(20);
//		campVO1.setCamp_price(2000);
//		campVO1.setCamp_stat(0);
////		campVO1.setCamp_pic();
//		dao.insert(campVO1);
//		
//		CampVO campVO2 = new CampVO();
//		campVO2.setCamp_no("C000000004");
//		campVO2.setCamp_vdno("V000000001");
//		campVO2.setCamp_name("親子山屋");
//		campVO2.setCamp_ctno("CT00000003");
//		campVO2.setCamp_qty(10);
//		campVO2.setCamp_price(4000);
//		campVO2.setCamp_stat(0);
////		campVO2.setCamp_pic();
//		dao.update(campVO2);
//		
//		CampVO campVO3 = dao.findByPrimaryKey("C000000004");
//		System.out.print(campVO3.getCamp_no() + ",");
//		System.out.print(campVO3.getCamp_vdno() + ",");
//		System.out.print(campVO3.getCamp_name() + ",");
//		System.out.print(campVO3.getCamp_ctno() + ",");
//		System.out.print(campVO3.getCamp_qty() + ",");
//		System.out.print(campVO3.getCamp_price() + ",");
//		System.out.print(campVO3.getCamp_stat() + ",");
//		System.out.println(campVO3.getCamp_pic());
//		System.out.println("---------------------");
//		
//		List<CampVO> list = dao.getAll();
//		for (CampVO aCamp : list) {
//			System.out.print(aCamp.getCamp_no() + ",");
//			System.out.print(aCamp.getCamp_vdno() + ",");
//			System.out.print(aCamp.getCamp_name() + ",");
//			System.out.print(aCamp.getCamp_ctno() + ",");
//			System.out.print(aCamp.getCamp_qty() + ",");
//			System.out.print(aCamp.getCamp_price() + ",");
//			System.out.print(aCamp.getCamp_stat() + ",");
//			System.out.println(aCamp.getCamp_pic());
//			System.out.println();			
//		}
//		
//		dao.delete("C000000001");
//		
//		// Add by Yen-Fu Chen
//		// 查詢特定業者(vd_no)的營位
//		List<CampVO> list = dao.getCampsByVdno("V000000001");
//		for (CampVO campVO : list) {
//			System.out.print(campVO.getCamp_no() + ", ");
//			System.out.print(campVO.getCamp_vdno() + ", ");
//			System.out.print(campVO.getCamp_name() + ", ");
//			System.out.print(campVO.getCamp_ctno() + ", ");
//			System.out.print(campVO.getCamp_qty() + ", ");
//			System.out.print(campVO.getCamp_price() + ", ");
//			System.out.print(campVO.getCamp_stat() + ", ");
//			System.out.println(campVO.getCamp_pic());
//			System.out.println();
//		}
//		
//		dao.updateStat("C000000018");
//	}
	
}
