package com.faq.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FAQDAO implements FAQDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FAQ (FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_STAT, FAQ_ADMINISNO) VALUES ('FAQ' || LPAD(SEQ_FAQNO.NEXTVAL, 7, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_EDIT, FAQ_STAT, FAQ_ADMINISNO FROM FAQ ORDER BY FAQ_NO";
	private static final String GET_ONE_STMT = "SELECT FAQ_NO, FAQ_FAQTNO, FAQ_TITLE, FAQ_CONTENT, FAQ_EDIT, FAQ_STAT, FAQ_ADMINISNO FROM FAQ WHERE FAQ_NO = ?";
	private static final String DELETE_STMT = "DELETE FROM FAQ WHERE FAQ_NO = ?";	
	private static final String UPDATE_STMT = "UPDATE FAQ SET FAQ_FAQTNO=?, FAQ_TITLE=?, FAQ_CONTENT=?, FAQ_EDIT=CURRENT_TIMESTAMP, FAQ_STAT=?, FAQ_ADMINISNO=? WHERE FAQ_NO = ?";
	private static final String GET_MULTI_BYFAQTNO_STMT = "SELECT * FROM FAQ WHERE FAQ_FAQTNO = ?";
	private static final String GET_MULTI_BYKEYWORD_STMT="SELECT * FROM FAQ WHERE UPPER(FAQ_TITLE) LIKE UPPER(?) OR UPPER(FAQ_CONTENT) LIKE UPPER(?)";
	
	@Override
	public void insert(FAQVO faqVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, faqVO.getFaq_faqtno());
			pstmt.setString(2, faqVO.getFaq_title());
			pstmt.setString(3, faqVO.getFaq_content());
			pstmt.setInt(4, faqVO.getFaq_stat());
			pstmt.setString(5, faqVO.getFaq_adminisno());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(FAQVO faqVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, faqVO.getFaq_faqtno());
			pstmt.setString(2, faqVO.getFaq_title());
			pstmt.setString(3, faqVO.getFaq_content());
			pstmt.setInt(4, faqVO.getFaq_stat());
			pstmt.setString(5, faqVO.getFaq_adminisno());
			pstmt.setString(6, faqVO.getFaq_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void delete(String faq_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, faq_no);			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public FAQVO findByPrimaryKey(String faq_no) {

		FAQVO faqVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, faq_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqVO = new FAQVO();
				faqVO.setFaq_no(rs.getString("FAQ_NO"));
				faqVO.setFaq_faqtno(rs.getString("FAQ_FAQTNO"));
				faqVO.setFaq_title(rs.getString("FAQ_TITLE"));
				faqVO.setFaq_content(rs.getString("FAQ_CONTENT"));
				faqVO.setFaq_edit(rs.getDate("FAQ_EDIT"));
				faqVO.setFaq_stat(rs.getInt("FAQ_STAT"));			
				faqVO.setFaq_adminisno(rs.getString("FAQ_ADMINISNO"));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return faqVO;
	}

	@Override
	public List<FAQVO> getAll() {
		
		List<FAQVO> list = new ArrayList<FAQVO>();
		FAQVO faqVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqVO = new FAQVO();
				faqVO.setFaq_no(rs.getString("FAQ_NO"));
				faqVO.setFaq_faqtno(rs.getString("FAQ_FAQTNO"));
				faqVO.setFaq_title(rs.getString("FAQ_TITLE"));
				faqVO.setFaq_content(rs.getString("FAQ_CONTENT"));
				faqVO.setFaq_edit(rs.getDate("FAQ_EDIT"));
				faqVO.setFaq_stat(rs.getInt("FAQ_STAT"));			
				faqVO.setFaq_adminisno(rs.getString("FAQ_ADMINISNO"));
				
				list.add(faqVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<FAQVO> getFAQsByFaqtno(String faqt_no) {
		
		List<FAQVO> list = new ArrayList<FAQVO>();
		FAQVO faqVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MULTI_BYFAQTNO_STMT);
			
			pstmt.setString(1, faqt_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqVO = new FAQVO();
				faqVO.setFaq_no(rs.getString("FAQ_NO"));
				faqVO.setFaq_faqtno(rs.getString("FAQ_FAQTNO"));
				faqVO.setFaq_title(rs.getString("FAQ_TITLE"));
				faqVO.setFaq_content(rs.getString("FAQ_CONTENT"));
				faqVO.setFaq_edit(rs.getDate("FAQ_EDIT"));
				faqVO.setFaq_stat(rs.getInt("FAQ_STAT"));			
				faqVO.setFaq_adminisno(rs.getString("FAQ_ADMINISNO"));
				
				list.add(faqVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;		
	}

	@Override
	public List<FAQVO> getFAQsByKeyword(String keyword) {
		
		List<FAQVO> list = new ArrayList<FAQVO>();
		FAQVO faqVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MULTI_BYKEYWORD_STMT);
			
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				faqVO = new FAQVO();
				faqVO.setFaq_no(rs.getString("FAQ_NO"));
				faqVO.setFaq_faqtno(rs.getString("FAQ_FAQTNO"));
				faqVO.setFaq_title(rs.getString("FAQ_TITLE"));
				faqVO.setFaq_content(rs.getString("FAQ_CONTENT"));
				faqVO.setFaq_edit(rs.getDate("FAQ_EDIT"));
				faqVO.setFaq_stat(rs.getInt("FAQ_STAT"));			
				faqVO.setFaq_adminisno(rs.getString("FAQ_ADMINISNO"));
				
				list.add(faqVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
}
