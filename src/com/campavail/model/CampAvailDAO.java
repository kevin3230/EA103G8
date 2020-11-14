package com.campavail.model;

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

public class CampAvailDAO implements CampAvailDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, campAvailVO.getCa_campno());
			pstmt.setDate(2, campAvailVO.getCa_date());
			pstmt.setInt(3, campAvailVO.getCa_qty());
			pstmt.executeUpdate();

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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, campAvailVO.getCa_qty());
			pstmt.setString(2, campAvailVO.getCa_campno());
			pstmt.setDate(3, campAvailVO.getCa_date());
			pstmt.executeUpdate();

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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, ca_campno);
			pstmt.setDate(2, ca_date);
			pstmt.executeUpdate();

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
			conn = ds.getConnection();
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campAvailVO = new CampAvailVO();
				campAvailVO.setCa_campno(rs.getString(1));;
				campAvailVO.setCa_date(rs.getDate(2));
				campAvailVO.setCa_qty(rs.getInt(3));
				list.add(campAvailVO);
			}

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
			conn = ds.getConnection();
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
				throw new RuntimeException("CampAvailDAO insertWithDetails SQLException " + e.getMessage());
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
}