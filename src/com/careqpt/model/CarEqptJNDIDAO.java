package com.careqpt.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class CarEqptJNDIDAO implements CarEqptDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO CAR_EQPT (CE_EQPTNO, CE_MEMNO, CE_QTY) VALUES (?, ?, ?)";
	private static final String FIND_BY_AUTH_NO = "SELECT * FROM CAR_EQPT WHERE CE_EQPTNO = ? AND CE_MEMNO = ?";
	private static final String GET_ALL = "SELECT * FROM CAR_EQPT ORDER BY CE_EQPTNO, CE_MEMNO";
	private static final String delete = "DELETE FROM CAR_EQPT WHERE CE_EQPTNO = ? AND CE_MEMNO = ?";
	private static final String updata = "UPDATE CAR_EQPT SET CE_QTY = ? WHERE CE_EQPTNO = ? AND CE_MEMNO = ?";
	private static final String GET_BYMEMNO = "SELECT * FROM CAR_EQPT WHERE CE_MEMNO = ?"; // Author: Jeff

	@Override // 代表就是要新增的資料
	public void add(CarEqptVO carEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, carEqptVO.getCe_eqptno());
			pstmt.setString(2, carEqptVO.getCe_memno());
			pstmt.setInt(3, carEqptVO.getCe_qty());
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
	public void updata(CarEqptVO carEqptVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, carEqptVO.getCe_qty());
			pstmt.setString(2, carEqptVO.getCe_eqptno());
			pstmt.setString(3, carEqptVO.getCe_memno());
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

	public void delete(String ce_memno) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(delete);

			pstmt.setString(1, ce_memno);

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
	public CarEqptVO findByPrimaryKey(String ce_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarEqptVO carEqptVO = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				CarEqptVO carEqptVO = new CarEqptVO();
				carEqptVO.setCe_eqptno(rs.getString("CE_EQPTNO"));
				carEqptVO.setCe_memno(rs.getString("CE_MEMNO"));
				carEqptVO.setCe_qty(rs.getInt("CE_QTY"));
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
	
	// Author: Jeff
	@Override
	public List<CarEqptVO> getCarEqptsByMemno(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarEqptVO> carEqptVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYMEMNO);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				CarEqptVO carEqptVO = new CarEqptVO();
				carEqptVO.setCe_eqptno(rs.getString("CE_EQPTNO"));
				carEqptVO.setCe_memno(rs.getString("CE_MEMNO"));
				carEqptVO.setCe_qty(rs.getInt("CE_QTY"));
				carEqptVO.setCe_expget(rs.getDate("CE_EXPGET"));
				carEqptVO.setCe_expback(rs.getDate("CE_EXPBACK"));
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



	@Override
	public void delete(CarEqptVO car_eqptVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CarEqptVO car_eqptVO, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String insert(CarEqptVO car_eqptVO) {
		// TODO Auto-generated method stub
		return null;
	}
}