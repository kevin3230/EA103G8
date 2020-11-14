package com.faqtype.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FAQTypeDAO implements FAQTypeDAO_interface {
	
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
			"INSERT INTO FAQ_TYPE(FAQT_NO, FAQT_NAME) VALUES ('FAQT' || LPAD(SEQ_CTNO.NEXTVAL, 6, '0'), ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE FAQ_TYPE SET FAQT_NAME = ? WHERE FAQT_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM FAQ_TYPE WHERE FAQT_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT FAQT_NO, FAQT_NAME FROM FAQ_TYPE WHERE FAQT_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT FAQT_NO, FAQT_NAME FROM FAQ_TYPE ORDER BY FAQT_NO";
	
	@Override
	public void insert(FAQTypeVO faqTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, faqTypeVO.getFaqt_name());
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
	public void update(FAQTypeVO faqTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, faqTypeVO.getFaqt_name());
			pstmt.setString(2, faqTypeVO.getFaqt_no());
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
	public void delete(String faqt_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, faqt_no);
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
	public FAQTypeVO findByPrimaryKey(String faqt_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FAQTypeVO faqTypeVO = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, faqt_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqTypeVO = new FAQTypeVO();
				faqTypeVO.setFaqt_no(rs.getString(1));;
				faqTypeVO.setFaqt_name(rs.getString(2));
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
		return faqTypeVO;
	}

	@Override
	public List<FAQTypeVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FAQTypeVO faqTypeVO = null;
		List<FAQTypeVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqTypeVO = new FAQTypeVO();
				faqTypeVO.setFaqt_no(rs.getString(1));;
				faqTypeVO.setFaqt_name(rs.getString(2));
				list.add(faqTypeVO);
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
