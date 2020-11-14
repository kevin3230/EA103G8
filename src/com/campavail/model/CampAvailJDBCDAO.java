package com.campavail.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampAvailJDBCDAO implements CampAvailDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT =
			"INSERT INTO CAMP_AVAIL(CA_CAMPNO, CA_DATE, CA_QTY)"
			+ " VALUES (?, ?, ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE CAMP_AVAIL SET CA_QTY = ?"
			+ " WHERE CA_CAMPNO = ? AND CA_DATE = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM CAMP_AVAIL WHERE CA_CAMPNO = ? AND CA_DATE = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT CA_CAMPNO, CA_DATE, CA_QTY"
			+ " FROM CAMP_AVAIL WHERE CA_CAMPNO = ? AND CA_DATE = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT CA_CAMPNO, CA_DATE, CA_QTY"
			+ " FROM CAMP_AVAIL ORDER BY CA_CAMPNO, CA_DATE";
	
	private static final String GET_MULTI_BYCAMPNO_STMT =
			"SELECT CA_CAMPNO, CA_DATE, CA_QTY"
			+ " FROM CAMP_AVAIL WHERE CA_CAMPNO = ? AND CA_DATE BETWEEN ? AND ?"
			+ " ORDER BY CA_DATE";	
	
	@Override
	public void insert(CampAvailVO campAvailVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, campAvailVO.getCa_campno());
			pstmt.setDate(2, campAvailVO.getCa_date());
			pstmt.setInt(3, campAvailVO.getCa_qty());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
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

	@Override
	public void update(CampAvailVO campAvailVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, campAvailVO.getCa_qty());
			pstmt.setString(2, campAvailVO.getCa_campno());
			pstmt.setDate(3, campAvailVO.getCa_date());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
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

	@Override
	public void delete(String ca_campno, Date ca_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, ca_campno);
			pstmt.setDate(2, ca_date);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
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

	@Override
	public CampAvailVO findByPrimaryKey(String ca_campno, Date ca_date) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampAvailVO campAvailVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ca_campno);
			pstmt.setDate(2, ca_date);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campAvailVO = new CampAvailVO();
				campAvailVO.setCa_campno(rs.getString(1));;
				campAvailVO.setCa_date(rs.getDate(2));
				campAvailVO.setCa_qty(rs.getInt(3));
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
		return campAvailVO;
	}

	@Override
	public List<CampAvailVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampAvailVO campAvailVO = null;
		List<CampAvailVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campAvailVO = new CampAvailVO();
				campAvailVO.setCa_campno(rs.getString(1));;
				campAvailVO.setCa_date(rs.getDate(2));
				campAvailVO.setCa_qty(rs.getInt(3));
				list.add(campAvailVO);
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
	public List<CampAvailVO> getCampAvailsByCampno(String camp_no, Date start, Date end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampAvailVO campAvailVO = null;
		List<CampAvailVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_MULTI_BYCAMPNO_STMT);
			
			pstmt.setString(1, camp_no);
			pstmt.setDate(2, start);
			pstmt.setDate(3, end);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				campAvailVO = new CampAvailVO();
				campAvailVO.setCa_campno(rs.getString(1));;
				campAvailVO.setCa_date(rs.getDate(2));
				campAvailVO.setCa_qty(rs.getInt(3));
				list.add(campAvailVO);
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
	
	// Author Jeff
	@Override
	public void update(CampAvailVO campAvailVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			// Set campAvailVO
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, campAvailVO.getCa_qty());
			pstmt.setString(2, campAvailVO.getCa_campno());
			pstmt.setDate(3, campAvailVO.getCa_date());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.println("CampAvail update with OrderMaster failed.");
				throw new RuntimeException("CampAvailDAO update with one conn SQLException " + e.getMessage());
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
		}
	}

	@Override
	public void insert(CampAvailVO campAvailVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			// Set campAvailVO
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(3, campAvailVO.getCa_qty());
			pstmt.setDate(2, campAvailVO.getCa_date());
			pstmt.setString(1, campAvailVO.getCa_campno());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.println("CampAvail update with OrderMaster failed.");
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
		}
		
	}
	
//	public static void main(String[] args) {
//		CampAvailJDBCDAO dao = new CampAvailJDBCDAO();
//		
////		// 新增
////		CampAvailVO campAvailVO1 = new CampAvailVO();
////		campAvailVO1.setCa_campno("C000000002");
////		campAvailVO1.setCa_date(Date.valueOf("2020-08-05"));
////		campAvailVO1.setCa_qty(19);
////		dao.insert(campAvailVO1);
////		
////		// 修改
////		CampAvailVO campAvailVO2 = new CampAvailVO();
////		campAvailVO2.setCa_campno("C000000001");
////		campAvailVO2.setCa_date(Date.valueOf("2020-08-02"));
////		campAvailVO2.setCa_qty(15);;
////		dao.update(campAvailVO2);
////		
////		// 刪除
////		dao.delete("C000000001", Date.valueOf("2020-08-03"));
////
////		// 查詢特定營位及日期的剩餘數量(ca_campno, ca_date) 
////		CampAvailVO campAvailVO3 = dao.findByPrimaryKey("C000000001", Date.valueOf("2020-10-27"));		
////		System.out.println(campAvailVO3.getCa_campno());
////		System.out.println(campAvailVO3.getCa_date());
////		System.out.println(campAvailVO3.getCa_qty());
////		System.out.println("====================");
////		
////		// 查詢全部
////		List<CampAvailVO> list = dao.getAll();
////		for (CampAvailVO campAvailVO : list) {
////			System.out.println(campAvailVO.getCa_campno());
////			System.out.println(campAvailVO.getCa_date());
////			System.out.println(campAvailVO.getCa_qty());
////			System.out.println();
////		}
////		System.out.println("====================");
//		
////		// 查詢特定營位(camp_no)的剩餘數量
////		List<CampAvailVO> list2 = dao.getCampAvailsByCampno("C000000001",
////				Date.valueOf("2021-1-1"), Date.valueOf("2021-1-31"));
////		for (CampAvailVO campAvailVO : list2) {
////			System.out.println(campAvailVO.getCa_campno());
////			System.out.println(campAvailVO.getCa_date());
////			System.out.println(campAvailVO.getCa_qty());
////			System.out.println();
////		}
//	}

}