package com.eqpttype.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EqptTypeDAO implements EqptTypeDAO_interface {
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
			"INSERT INTO EQPT_TYPE(ET_NO, ET_NAME) VALUES ('ET' || LPAD(SEQ_CTNO.NEXTVAL, 8, '0'), ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE EQPT_TYPE SET ET_NAME = ? WHERE ET_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM EQPT_TYPE WHERE ET_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT ET_NO, ET_NAME FROM EQPT_TYPE WHERE ET_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT ET_NO, ET_NAME FROM EQPT_TYPE ORDER BY ET_NO";

	@Override
	public void insert(EqptTypeVO eqptTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, eqptTypeVO.getEt_name());
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
	public void update(EqptTypeVO eqptTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, eqptTypeVO.getEt_name());
			pstmt.setString(2, eqptTypeVO.getEt_no());
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
	public void delete(String et_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, et_no);
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
	public EqptTypeVO findByPrimaryKey(String et_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EqptTypeVO eqptTypeVO = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, et_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				eqptTypeVO = new EqptTypeVO();
				eqptTypeVO.setEt_no(rs.getString(1));;
				eqptTypeVO.setEt_name(rs.getString(2));
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
		return eqptTypeVO;
	}

	@Override
	public List<EqptTypeVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EqptTypeVO eqptTypeVO = null;
		List<EqptTypeVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				eqptTypeVO = new EqptTypeVO();
				eqptTypeVO.setEt_no(rs.getString(1));;
				eqptTypeVO.setEt_name(rs.getString(2));
				list.add(eqptTypeVO);
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
	
}
