package com.carcamp.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarCampJDBCDAO implements CarCampDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT =
			"INSERT INTO CAR_CAMP(CC_CAMPNO, CC_MEMNO, CC_QTY, CC_START, CC_END)"
			+ " VALUES (?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE CAR_CAMP SET CC_QTY = ?, CC_START = ?, CC_END = ?"
			+ " WHERE CC_MEMNO = ? AND CC_CAMPNO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM CAR_CAMP WHERE CC_MEMNO = ? AND CC_CAMPNO = ?";

	private static final String DELETE_BYMEMNO_STMT =
			"DELETE FROM CAR_CAMP WHERE CC_MEMNO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT CC_CAMPNO, CC_MEMNO, CC_QTY, CC_START, CC_END"
			+ " FROM CAR_CAMP WHERE CC_MEMNO = ? AND CC_CAMPNO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT CC_CAMPNO, CC_MEMNO, CC_QTY, CC_START, CC_END"
			+ " FROM CAR_CAMP ORDER BY CC_MEMNO, CC_CAMPNO";
	
	private static final String GET_MULTI_BYMEMNO_STMT =
			"SELECT CC_CAMPNO, CC_MEMNO, CC_QTY, CC_START, CC_END"
			+ " FROM CAR_CAMP WHERE CC_MEMNO = ? ORDER BY CC_MEMNO, CC_CAMPNO";
	
	@Override
	public void insert(CarCampVO carCampVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, carCampVO.getCc_campno());
			pstmt.setString(2, carCampVO.getCc_memno());
			pstmt.setInt(3, carCampVO.getCc_qty());
			pstmt.setDate(4, carCampVO.getCc_start());
			pstmt.setDate(5, carCampVO.getCc_end());
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
	public void update(CarCampVO carCampVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, carCampVO.getCc_qty());
			pstmt.setDate(2, carCampVO.getCc_start());
			pstmt.setDate(3, carCampVO.getCc_end());
			pstmt.setString(4, carCampVO.getCc_memno());
			pstmt.setString(5, carCampVO.getCc_campno());
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
	public void delete(String cc_memno, String cc_campno) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, cc_memno);
			pstmt.setString(2, cc_campno);
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
	public void delete(String cc_memno) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_BYMEMNO_STMT);
			
			pstmt.setString(1, cc_memno);
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
	public CarCampVO findByPrimaryKey(String cc_memno, String cc_campno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarCampVO carCampVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cc_memno);
			pstmt.setString(2, cc_campno);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				carCampVO = new CarCampVO();
				carCampVO.setCc_campno(rs.getString(1));
				carCampVO.setCc_memno(rs.getString(2));
				carCampVO.setCc_qty(rs.getInt(3));
				carCampVO.setCc_start(rs.getDate(4));
				carCampVO.setCc_end(rs.getDate(5));
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
		return carCampVO;
	}

	@Override
	public List<CarCampVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarCampVO carCampVO = null;
		List<CarCampVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				carCampVO = new CarCampVO();
				carCampVO.setCc_campno(rs.getString(1));
				carCampVO.setCc_memno(rs.getString(2));
				carCampVO.setCc_qty(rs.getInt(3));
				carCampVO.setCc_start(rs.getDate(4));
				carCampVO.setCc_end(rs.getDate(5));
				list.add(carCampVO);
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
	public List<CarCampVO> getCarCampsByMemno(String mem_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarCampVO carCampVO = null;
		List<CarCampVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_MULTI_BYMEMNO_STMT);
			
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				carCampVO = new CarCampVO();
				carCampVO.setCc_campno(rs.getString(1));
				carCampVO.setCc_memno(rs.getString(2));
				carCampVO.setCc_qty(rs.getInt(3));
				carCampVO.setCc_start(rs.getDate(4));
				carCampVO.setCc_end(rs.getDate(5));
				list.add(carCampVO);
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
	
	@Override // Author: Jeff
	public void delete(CarCampVO carCampVO, Connection conn) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, carCampVO.getCc_memno());
			pstmt.setString(2, carCampVO.getCc_campno());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				System.out.println("CarCampDAO delete with OrderMaster failed.");
				throw new RuntimeException("CarCampDAO insertWithDetails SQLException " + e.getMessage());
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
//		CarCampJDBCDAO dao = new CarCampJDBCDAO();
//		
//		// 新增
////		CarCampVO carCampVO1 = new CarCampVO();
////		carCampVO1.setCc_campno("C000000001");
////		carCampVO1.setCc_memno("M000000001");
////		carCampVO1.setCc_qty(3);
////		carCampVO1.setCc_start(Date.valueOf("2020-10-18"));
////		carCampVO1.setCc_end(Date.valueOf("2020-10-21"));
////		dao.insert(carCampVO1);
//		
//		// 修改
////		CarCampVO carCampVO2 = new CarCampVO();
////		carCampVO2.setCc_qty(5);
////		carCampVO2.setCc_start(Date.valueOf("2020-08-26"));
////		carCampVO2.setCc_end(Date.valueOf("2020-08-28"));
////		carCampVO2.setCc_campno("C000000003");
////		carCampVO2.setCc_memno("M000000003");
////		dao.update(carCampVO2);
//		
//		// 刪除
////		dao.delete("M000000003", "C000000003");
//
//		// 查詢特定營位購物車(cc_memno, cc_campno)
////		CarCampVO carCampVO3 = dao.findByPrimaryKey("M000000001", "C000000001");
////		System.out.println(carCampVO3.getCc_campno());
////		System.out.println(carCampVO3.getCc_memno());
////		System.out.println(carCampVO3.getCc_qty());
////		System.out.println(carCampVO3.getCc_start());
////		System.out.println(carCampVO3.getCc_end());
////		System.out.println("====================");
//		
//		// 查詢全部
////		List<CarCampVO> list = dao.getAll();
////		for (CarCampVO carCampVO : list) {
////			System.out.println(carCampVO.getCc_campno());
////			System.out.println(carCampVO.getCc_memno());
////			System.out.println(carCampVO.getCc_qty());
////			System.out.println(carCampVO.getCc_start());
////			System.out.println(carCampVO.getCc_end());
////			System.out.println();
////		}
////		System.out.println("====================");
//		
//		// 查詢特定會員(mem_no)的營位購物車
//		List<CarCampVO> list2 = dao.getCarCampsByMemno("M000000001");
//		for (CarCampVO carCampVO : list2) {
//			System.out.println(carCampVO.getCc_campno());
//			System.out.println(carCampVO.getCc_memno());
//			System.out.println(carCampVO.getCc_qty());
//			System.out.println(carCampVO.getCc_start());
//			System.out.println(carCampVO.getCc_end());
//			System.out.println();
//		}
//	}

}