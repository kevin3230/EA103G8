package com.faqtype.model;

import java.sql.*;
import java.util.*;

public class FAQTypeJDBCDAO implements FAQTypeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
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
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, faqTypeVO.getFaqt_name());
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
	public void update(FAQTypeVO faqTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, faqTypeVO.getFaqt_name());
			pstmt.setString(2, faqTypeVO.getFaqt_no());
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
	public void delete(String faqt_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, faqt_no);
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
	public FAQTypeVO findByPrimaryKey(String faqt_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FAQTypeVO faqTypeVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, faqt_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqTypeVO = new FAQTypeVO();
				faqTypeVO.setFaqt_no(rs.getString(1));;
				faqTypeVO.setFaqt_name(rs.getString(2));
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
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqTypeVO = new FAQTypeVO();
				faqTypeVO.setFaqt_no(rs.getString(1));;
				faqTypeVO.setFaqt_name(rs.getString(2));
				list.add(faqTypeVO);
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

}
