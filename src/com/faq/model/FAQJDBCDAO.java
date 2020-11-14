package com.faq.model;

import java.sql.*;
import java.util.*;

public class FAQJDBCDAO implements FAQDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, faqVO.getFaq_faqtno());
			pstmt.setString(2, faqVO.getFaq_title());
			pstmt.setString(3, faqVO.getFaq_content());
			pstmt.setInt(4, faqVO.getFaq_stat());
			pstmt.setString(5, faqVO.getFaq_adminisno());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, faqVO.getFaq_faqtno());
			pstmt.setString(2, faqVO.getFaq_title());
			pstmt.setString(3, faqVO.getFaq_content());
			pstmt.setInt(4, faqVO.getFaq_stat());
			pstmt.setString(5, faqVO.getFaq_adminisno());
			pstmt.setString(6, faqVO.getFaq_no());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, faq_no);			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
	
//	public static void main(String[] args) {
//		FAQJDBCDAO dao = new FAQJDBCDAO();
//		
//		FAQVO faqVO1 = new FAQVO();
//		faqVO1.setFaq_title("如何搭帳棚？");
//		faqVO1.setFaq_content("給我一個支點，一條夠長的繩子，我將撐起整個帳篷");
//		faqVO1.setFaq_stat(0);
//		faqVO1.setFaq_adminisno("A000000001");
//		dao.insert(faqVO1);
//
//		FAQVO faqVO2 = new FAQVO();
//		faqVO2.setFaq_no("FAQ0000001");
//		faqVO2.setFaq_title("如何搭帳棚？");
//		faqVO2.setFaq_content("用手");
//		faqVO2.setFaq_stat(0);
//		faqVO2.setFaq_adminisno("A000000002");
//		dao.update(faqVO2);
//		
//		FAQVO faqVO3 = dao.findByPrimaryKey("FAQ0000001");
//		System.out.print(faqVO3.getFaq_no() + ",");
//		System.out.print(faqVO3.getFaq_title() + ",");
//		System.out.print(faqVO3.getFaq_content() + ",");
//		System.out.print(faqVO3.getFaq_edit() + ",");
//		System.out.print(faqVO3.getFaq_stat() + ",");
//		System.out.println(faqVO3.getFaq_adminisno());
//		System.out.println("---------------------");
//		
//		List<FAQVO> list = dao.getAll();
//		for (FAQVO aFAQ : list) {
//			System.out.print(aFAQ.getFaq_no() + ",");
//			System.out.print(aFAQ.getFaq_title() + ",");
//			System.out.print(aFAQ.getFaq_content() + ",");
//			System.out.print(aFAQ.getFaq_edit() + ",");
//			System.out.print(aFAQ.getFaq_stat() + ",");
//			System.out.println(aFAQ.getFaq_adminisno());
//			System.out.println();
//		}
//		
//		dao.delete("FAQ0000001");
//		
//	}

}
