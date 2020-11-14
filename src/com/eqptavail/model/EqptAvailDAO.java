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

public class EqptAvailDAO implements EqptAvailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO EQPT_AVAIL (EA_EQPTNO, EA_DATE, EA_QTY) VALUES (?, ?, ?)";
	private static final String FIND_BY = "SELECT * FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND trunc(EA_DATE) = ?";
	private static final String GET_ALL = "SELECT * FROM EQPT_AVAIL ORDER BY EA_EQPTNO";
	private static final String GET_ALL_BY_EQPTNO = "SELECT * FROM EQPT_AVAIL WHERE EA_EQPTNO=?";
	private static final String delete = "DELETE FROM EQPT_AVAIL WHERE EA_EQPTNO = ? AND EA_DATE = ?";
	private static final String updata = "UPDATE EQPT_AVAIL SET EA_QTY = ? WHERE EA_EQPTNO = ? AND EA_DATE = ?";

	@Override // 代表就是要新增的資料
	public void add(EqptAvailVO eqptAvailVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eqptAvailVO.getEa_eqptno());
			pstmt.setDate(2, eqptAvailVO.getEa_date());
			pstmt.setInt(3, eqptAvailVO.getEa_qty());
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
	public void updata(EqptAvailVO eqptAvailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, eqptAvailVO.getEa_qty());
			pstmt.setString(2, eqptAvailVO.getEa_eqptno());
			pstmt.setDate(3, eqptAvailVO.getEa_date());
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
		EqptAvailVO eqptAvailVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY);
			pstmt.setString(1, ea_eqptno);
			pstmt.setDate(2, ea_date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				eqptAvailVO = new EqptAvailVO();
				eqptAvailVO.setEa_eqptno(ea_eqptno);
				eqptAvailVO.setEa_date(ea_date);
				eqptAvailVO.setEa_qty(rs.getInt("EA_QTY"));

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

		return eqptAvailVO;
	}

	@Override
	public List<EqptAvailVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<EqptAvailVO> eqptAvailVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {

				EqptAvailVO eqptAvailVO = new EqptAvailVO();
				eqptAvailVO.setEa_eqptno(rs.getString("EA_EQPTNO"));
				eqptAvailVO.setEa_date(rs.getDate("EA_DATE"));
				eqptAvailVO.setEa_qty(rs.getInt("EA_QTY"));
				eqptAvailVOList.add(eqptAvailVO);
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

		return eqptAvailVOList;
	}
	@Override
	public List<EqptAvailVO> getAllByEqptno(String ea_eqptno) {
		
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<EqptAvailVO> List = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_EQPTNO);
			pstmt.setString(1, ea_eqptno);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				EqptAvailVO eqptAvailVO = new EqptAvailVO();
				eqptAvailVO.setEa_date(rs.getDate("EA_DATE"));
				eqptAvailVO.setEa_qty(rs.getInt("EA_QTY"));
				eqptAvailVO.setEa_eqptno(ea_eqptno);
				List.add(eqptAvailVO);
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

		return List;
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

}