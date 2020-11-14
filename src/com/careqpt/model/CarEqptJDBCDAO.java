package com.careqpt.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarEqptJDBCDAO implements CarEqptDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";
	
	private static final String INSERT_STMT = "INSERT INTO CAR_EQPT (CE_NO, CE_EQPTNO, CE_MEMNO, CE_QTY, CE_EXPGET, CE_EXPBACK) VALUES ('CE' || LPAD(SEQ_CENO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";
	private static final String FIND_BY_AUTH_NO = "SELECT * FROM CAR_EQPT WHERE CE_NO = ?";
	private static final String GET_ALL = "SELECT * FROM CAR_EQPT ORDER BY CE_MEMNO, CE_EQPTNO";
	private static final String delete = "DELETE FROM CAR_EQPT WHERE CE_MEMNO = ?";
	private static final String updata = "UPDATE CAR_EQPT SET CE_QTY = ? WHERE CE_NO = ?";
	// Author: Jeff
	private static final String GET_BYMEMNO = "SELECT * FROM CAR_EQPT WHERE CE_MEMNO = ? ORDER BY CE_MEMNO, CE_EQPTNO";
	private static final String DELETE_BYCEVO = "DELETE FROM CAR_EQPT WHERE CE_NO = ?";
	
	@Override // 代表就是要新增的資料
	public void add(CarEqptVO carEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, carEqptVO.getCe_eqptno());
			pstmt.setString(2, carEqptVO.getCe_memno());
			pstmt.setInt(3, carEqptVO.getCe_qty());
			pstmt.setDate(4,carEqptVO.getCe_expget());
			pstmt.setDate(5,carEqptVO.getCe_expback());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}

	}

	@Override
	public void updata(CarEqptVO carEqptVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, carEqptVO.getCe_qty());
			pstmt.setString(2, carEqptVO.getCe_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(String ce_memno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(delete);

			pstmt.setString(1, ce_memno);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public CarEqptVO findByPrimaryKey(String ce_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarEqptVO carEqptVO = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_AUTH_NO);
			pstmt.setString(1, ce_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				carEqptVO = new CarEqptVO();
				carEqptVO.setCe_no(ce_no);
				carEqptVO.setCe_eqptno(rs.getString("CE_EQPTNO"));
				carEqptVO.setCe_memno(rs.getString("CE_MEMNO"));
				carEqptVO.setCe_qty(rs.getInt("CE_QTY"));
				carEqptVO.setCe_expget(rs.getDate("CE_EXPGET"));
				carEqptVO.setCe_expback(rs.getDate("CE_EXPBACK"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return carEqptVO;
	}

	@Override
	public List<CarEqptVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarEqptVO> carEqptVOList = new ArrayList<>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				CarEqptVO carEqptVO = new CarEqptVO();
				carEqptVO.setCe_no(rs.getString("CE_NO"));
				carEqptVO.setCe_eqptno(rs.getString("CE_EQPTNO"));
				carEqptVO.setCe_memno(rs.getString("CE_MEMNO"));
				carEqptVO.setCe_qty(rs.getInt("CE_QTY"));
				carEqptVO.setCe_expget(rs.getDate("CE_EXPGET"));
				carEqptVO.setCe_expback(rs.getDate("CE_EXPBACK"));
				carEqptVOList.add(carEqptVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return carEqptVOList;
	}

	@Override
	public List<CarEqptVO> getCarEqptsByMemno(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarEqptVO> carEqptVOList = new ArrayList<>();

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_BYMEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				CarEqptVO carEqptVO = new CarEqptVO();
				carEqptVO.setCe_no(rs.getString("CE_NO"));
				carEqptVO.setCe_eqptno(rs.getString("CE_EQPTNO"));
				carEqptVO.setCe_memno(rs.getString("CE_MEMNO"));
				carEqptVO.setCe_qty(rs.getInt("CE_QTY"));
				carEqptVO.setCe_expget(rs.getDate("CE_EXPGET"));
				carEqptVO.setCe_expback(rs.getDate("CE_EXPBACK"));
				carEqptVOList.add(carEqptVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return carEqptVOList;
	}

	@Override // Author: Jeff
	public void delete(CarEqptVO car_eqptVO) {
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_BYCEVO);

			pstmt.setString(1, car_eqptVO.getCe_no());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.println("CarEqptDAO delete with OrderMaster failed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
	
	@Override // Author: Jeff
	public void delete(CarEqptVO car_eqptVO, Connection con) {
		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(DELETE_BYCEVO);

			pstmt.setString(1, car_eqptVO.getCe_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.println("CarEqptDAO delete with OrderMaster failed.");
				throw new RuntimeException("CarEqptDAO insertWithDetails SQLException " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override // Author Jeff
	public String insert(CarEqptVO carEqptVO) {
		String ce_no = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			String[] seqCol = {"CE_NO"};
			pstmt = con.prepareStatement(INSERT_STMT, seqCol);
			pstmt.setString(1, carEqptVO.getCe_eqptno());
			pstmt.setString(2, carEqptVO.getCe_memno());
			pstmt.setInt(3, carEqptVO.getCe_qty());
			pstmt.setDate(4,carEqptVO.getCe_expget());
			pstmt.setDate(5,carEqptVO.getCe_expback());
			pstmt.executeUpdate();
			// Get seq PK
			rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				ce_no = rs.getString(1);
				System.out.println("CarEqptJDBCDAO insert getGeneratedKeys : " + ce_no);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}
		return ce_no;
	}
	
	public static void main(String[] args) {
		CarEqptJDBCDAO ceDAO = new CarEqptJDBCDAO();
		// add()
		CarEqptVO ceVO1 = new CarEqptVO();
		ceVO1.setCe_memno("M000000002");
		ceVO1.setCe_eqptno("E000000003");
		ceVO1.setCe_qty(66);
		ceVO1.setCe_expget(Date.valueOf("2020-10-01"));
		ceVO1.setCe_expback(Date.valueOf("2020-10-05"));
		ceDAO.add(ceVO1);
		
////		// update()
////		CarEqptVO ceVO2 = new CarEqptVO();
////		ceVO2.setCe_no("CE00000001");
////		ceVO2.setCe_qty(55);
////		ceDAO.update(ceVO2);
//		
////		// delete()
////		ceDAO.delete("M000000001");
//		
////		// findByPrimaryKey()
////		CarEqptVO ceVO3 = ceDAO.findByPrimaryKey("CE00000003");
////		System.out.println("CE_NO : " + ceVO3.getCe_no());
////		System.out.println("CE_EQPTNO : " + ceVO3.getCe_eqptno());
////		System.out.println("CE_MEMNO : " + ceVO3.getCe_memno());
////		System.out.println("CE_QTY : " + ceVO3.getCe_qty());
////		System.out.println("CE_EXPGET : " + ceVO3.getCe_expget());
////		System.out.println("CE_EXPBACK : " + ceVO3.getCe_expback());
//		
////		// getAll()
////		List<CarEqptVO> ceVOList = ceDAO.getAll();
////		for(CarEqptVO ceVOi : ceVOList) {
////			System.out.println("================");
////			System.out.println("CE_NO : " + ceVOi.getCe_no());
////			System.out.println("CE_EQPTNO : " + ceVOi.getCe_eqptno());
////			System.out.println("CE_MEMNO : " + ceVOi.getCe_memno());
////			System.out.println("CE_QTY : " + ceVOi.getCe_qty());
////			System.out.println("CE_EXPGET : " + ceVOi.getCe_expget());
////			System.out.println("CE_EXPBACK : " + ceVOi.getCe_expback());
////		}
//		
////		// getCarEqptsByMemno()
////		List<CarEqptVO> ceVOList2 = ceDAO.getCarEqptsByMemno("M000000001");
////		for(CarEqptVO ceVOi : ceVOList2) {
////			System.out.println("================");
////			System.out.println("CE_NO : " + ceVOi.getCe_no());
////			System.out.println("CE_EQPTNO : " + ceVOi.getCe_eqptno());
////			System.out.println("CE_MEMNO : " + ceVOi.getCe_memno());
////			System.out.println("CE_QTY : " + ceVOi.getCe_qty());
////			System.out.println("CE_EXPGET : " + ceVOi.getCe_expget());
////			System.out.println("CE_EXPBACK : " + ceVOi.getCe_expback());
////		}
//		
////		// delete(CarEqptVO)
////		CarEqptVO ceVO4 = new CarEqptVO();
////		ceVO4.setCe_no("CE00000010");
////		ceDAO.delete(ceVO4);
	}
}