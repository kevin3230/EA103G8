package com.eqptavail.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EqptAvailJDBCDAO implements EqptAvailDAO_interface {

	// 載入驅動
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 載入資料庫
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO EQPT_AVAIL (EA_EQPTNO, EA_DATE, EA_QTY) VALUES (?, ?, ?)";
	private static final String FIND_BY = "SELECT * FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND trunc(EA_DATE)=? ";
	private static final String GET_ALL = "SELECT * FROM EQPT_AVAIL ORDER BY EA_EQPTNO, EA_DATE";
	private static final String delete = "DELETE FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND EA_DATE = ?";
	private static final String updata = "UPDATE EQPT_AVAIL SET EA_QTY = ? WHERE EA_EQPTNO = ? AND EA_DATE = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

	}

	@Override // 代表就是要新增的資料
	public void add(EqptAvailVO carEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, carEqptVO.getEa_eqptno());
			pstmt.setDate(2, carEqptVO.getEa_date());
			pstmt.setInt(3, carEqptVO.getEa_qty());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
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
	public void updata(EqptAvailVO carEqptVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, carEqptVO.getEa_qty());
			pstmt.setString(2, carEqptVO.getEa_eqptno());
			pstmt.setDate(3, carEqptVO.getEa_date());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String ea_eqptno, Date ea_date) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(delete);

			pstmt.setString(1, ea_eqptno);
			pstmt.setDate(2, ea_date);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public EqptAvailVO findByPrimaryKey(String ea_eqptno, Date ea_date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EqptAvailVO carEqptVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY);
			pstmt.setString(1, ea_eqptno);
			pstmt.setDate(2, ea_date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				carEqptVO = new EqptAvailVO();
				carEqptVO.setEa_eqptno(ea_eqptno);
				carEqptVO.setEa_date(ea_date);
				carEqptVO.setEa_qty(rs.getInt("EA_QTY"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
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
	public List<EqptAvailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EqptAvailVO> carEqptVOList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				EqptAvailVO carEqptVO = new EqptAvailVO();
				carEqptVO.setEa_eqptno(rs.getString("EA_EQPTNO"));
				carEqptVO.setEa_date(rs.getDate("EA_DATE"));
				carEqptVO.setEa_qty(rs.getInt("EA_QTY"));
				carEqptVOList.add(carEqptVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
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

	// Author Jeff
	@Override
	public void update(EqptAvailVO eqptAvailVO, Connection con) {

		PreparedStatement pstmt = null;

		try {
			// Set EQPTAvailVO
			pstmt = con.prepareStatement(updata);
			pstmt.setInt(1, eqptAvailVO.getEa_qty());
			pstmt.setString(2, eqptAvailVO.getEa_eqptno());
			pstmt.setDate(3, eqptAvailVO.getEa_date());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.println("EqptAvailDAO update with OrderMaster failed.");
				throw new RuntimeException("EqptAvailDAO update with one conn SQLException " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
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
	
	@Override
	public void insert(EqptAvailVO eqptAvailVO, Connection con) {

		PreparedStatement pstmt = null;

		try {
			// Set EQPTAvailVO
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, eqptAvailVO.getEa_eqptno());
			pstmt.setDate(2, eqptAvailVO.getEa_date());
			pstmt.setInt(3, eqptAvailVO.getEa_qty());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.println("EqptAvailDAO insert with OrderMaster failed.");
				se.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
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
	
//	public static void main(String[] args) {
//
//		EqptAvailJDBCDAO dao = new EqptAvailJDBCDAO();
//
//		//新增測試		
//		EqptAvailVO carEqptVO1 = new EqptAvailVO();
//		carEqptVO1.setEa_eqptno("E000000001");
//		carEqptVO1.setEa_date(java.sql.Date.valueOf("2020-10-16"));
//		carEqptVO1.setEa_qty(1);
//		dao.add(carEqptVO1);
//
//		// 修改測試
////		EqptAvailVO carEqptVO2 = new EqptAvailVO();
////		carEqptVO2.setEa_eqptno("E000000001");
////		carEqptVO2.setEa_date(java.sql.Date.valueOf("2020-1-20"));
////		carEqptVO2.setEa_qty(100);
////		dao.updata(carEqptVO2);
//
////		//刪除測試	
////		dao.delete("E000000001", java.sql.Date.valueOf("2020-1-21"));
//
////		// 主鍵查詢
//
////		EqptAvailVO carEqptVO3 = dao.findByPrimaryKey("E000000001", java.sql.Date.valueOf("2020-10-15"));
////		System.out.println(carEqptVO3);
////		System.out.print(carEqptVO3.getEa_eqptno() + ",");
////		System.out.print(carEqptVO3.getEa_date() + ",");
////		System.out.print(carEqptVO3.getEa_qty() + ",");
////			System.out.println("---------------------");
//
//	//所有查詢
////		List<EqptAvailVO> list = dao.getAll();
////		for (EqptAvailVO carEqptVO : list) {
////			System.out.print(carEqptVO.getEa_eqptno() + ",");
////			System.out.print(carEqptVO.getEa_date() + ",");
////			System.out.print(carEqptVO.getEa_qty() + ",");
////			System.out.println("---------------------");
////		}
//	}

	@Override
	public List<EqptAvailVO> getAllByEqptno(String ea_eqptno) {
		// TODO Auto-generated method stub
		return null;
	}

}
