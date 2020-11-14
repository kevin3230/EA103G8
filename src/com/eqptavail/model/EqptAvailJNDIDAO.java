package com.eqptavail.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EqptAvailJNDIDAO implements EqptAvailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO EQPT_AVAIL (EA_EQPTNO, EA_DATE, EA_QTY) VALUES (?, ?, ?)";
	private static final String FIND_BY = "SELECT * FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND EA_DATE = ?";
	private static final String GET_ALL = "SELECT * FROM EQPT_AVAIL ORDER BY EA_EQPTNO, EA_DATE";
	private static final String delete = "DELETE FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND EA_DATE = ?";
	private static final String updata = "UPDATE EQPT_AVAIL SET EA_QTY = ? WHERE EA_EQPTNO = ? AND EA_DATE = ?";

	@Override // 代表就是要新增的資料
	public void add(EqptAvailVO EqptAvailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, EqptAvailVO.getEa_eqptno());
			pstmt.setDate(2, EqptAvailVO.getEa_date());
			pstmt.setInt(3, EqptAvailVO.getEa_qty());
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
	public void updata(EqptAvailVO EqptAvailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, EqptAvailVO.getEa_qty());
			pstmt.setString(2, EqptAvailVO.getEa_eqptno());
			pstmt.setDate(3, EqptAvailVO.getEa_date());
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

			con = ds.getConnection();
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
		EqptAvailVO EqptAvailVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY);
			pstmt.setString(1, ea_eqptno);
			pstmt.setDate(2, ea_date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				EqptAvailVO = new EqptAvailVO();
				EqptAvailVO.setEa_eqptno(ea_eqptno);
				EqptAvailVO.setEa_date(ea_date);
				EqptAvailVO.setEa_qty(rs.getInt("EA_QTY"));

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

		return EqptAvailVO;
	}

	@Override
	public List<EqptAvailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EqptAvailVO> EqptAvailVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				EqptAvailVO EqptAvailVO = new EqptAvailVO();
				EqptAvailVO.setEa_eqptno(rs.getString("EA_EQPTNO"));
				EqptAvailVO.setEa_date(rs.getDate("EA_DATE"));
				EqptAvailVO.setEa_qty(rs.getInt("EA_QTY"));
				EqptAvailVOList.add(EqptAvailVO);
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

		return EqptAvailVOList;
	}

	@Override
	public List<EqptAvailVO> getAllByEqptno(String ea_eqptno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(EqptAvailVO eqptAvailVO, Connection con) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(EqptAvailVO eqptAvailVO, Connection con) {
		// TODO Auto-generated method stub
		
	}

}