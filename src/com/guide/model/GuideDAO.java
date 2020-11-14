package com.guide.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;

public class GuideDAO implements GuideDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private static final String SQL_INSERT = "INSERT INTO GUIDE(GUIDE_NO, GUIDE_TITLE, GUIDE_CONTENT, GUIDE_EDIT, GUIDE_STAT, GUIDE_ADMINISNO) VALUES('G' || LPAD(SEQ_CGINO.NEXTVAL, 9, '0'),?,?, SYSTIMESTAMP,?,?)";
	private static final String SQL_UPDATE = "UPDATE GUIDE SET GUIDE_TITLE=?, GUIDE_CONTENT=?, GUIDE_EDIT=SYSTIMESTAMP, GUIDE_STAT=?, GUIDE_ADMINISNO=? WHERE GUIDE_NO=?";
	private static final String SQL_QUERY = "SELECT * FROM GUIDE WHERE GUIDE_NO=?";
	private static final String SQL_QUERYALL = "SELECT * FROM GUIDE";
	private static final String SQL_DELETE = "DELETE FROM GUIDE WHERE GUIDE_NO=?";

	@Override
	public String insert(GuideVO guideVO) {
		String guide_no = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO insert get connection successfully.");
			String[] seqNo = { "GUIDE_NO" };
			pstmt = con.prepareStatement(SQL_INSERT, seqNo);
			pstmt.setString(1, guideVO.getGuide_title());
			pstmt.setString(2, guideVO.getGuide_content());
			pstmt.setInt(3, guideVO.getGuide_stat());
			pstmt.setString(4, guideVO.getGuide_adminisno());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			// get generated seq pro_no
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				guide_no = rs.getString(1);
				System.out.println("GuideDAO insert pstmt.getGeneratedKeys() : " + guide_no);
			} else {
				System.out.println("GuideDAO insert pstmt.getGeneratedKeys() guide_no failed.");
			}
			con.commit();
			System.out.printf("GuideDAO: \"%s\" insert Successfully.%n", guideVO.getGuide_title());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("GuideDAO: \"%s\" insert failed.%n", guideVO.getGuide_title());
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return guide_no;
	}

	@Override
	public void update(GuideVO guideVO) {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO update get connection successfully.");
			pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, guideVO.getGuide_title());
			pstmt.setString(2, guideVO.getGuide_content());
			pstmt.setInt(3, guideVO.getGuide_stat());
			pstmt.setString(4, guideVO.getGuide_adminisno());
			pstmt.setString(5, guideVO.getGuide_no());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("GuideDAO: \"%s\" update successfully.%n", guideVO.getGuide_title());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("GuideDAO: \"%s\" update failed.%n", guideVO.getGuide_title());
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public GuideVO findByPrimaryKey(String guide_no) {
		GuideVO guideVO = new GuideVO();
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO findByPrimaryKey get connection successfully.");
			pstmt = con.prepareStatement(SQL_QUERY);
			pstmt.setString(1, guide_no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				guideVO.setGuide_no(rs.getString("GUIDE_NO"));
				guideVO.setGuide_title(rs.getString("GUIDE_TITLE"));
				guideVO.setGuide_content(rs.getString("GUIDE_CONTENT"));
				guideVO.setGuide_edit(rs.getDate("GUIDE_EDIT"));
				guideVO.setGuide_stat(rs.getInt("GUIDE_STAT"));
				guideVO.setGuide_adminisno(rs.getString("GUIDE_ADMINISNO"));
			}
			System.out.printf("GuideDAO: \"%s\" findByPrimaryKey successfully.%n", guideVO.getGuide_title());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return guideVO;
	}

	@Override
	public List<GuideVO> getAll() {
		List<GuideVO> GuideVOList = new ArrayList<GuideVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO getAll get connection successfully.");
			pstmt = con.prepareStatement(SQL_QUERYALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				GuideVO guideVO = new GuideVO();
				guideVO.setGuide_no(rs.getString("GUIDE_NO"));
				guideVO.setGuide_title(rs.getString("GUIDE_TITLE"));
				guideVO.setGuide_content(rs.getString("GUIDE_CONTENT"));
				guideVO.setGuide_edit(rs.getDate("GUIDE_EDIT"));
				guideVO.setGuide_stat(rs.getInt("GUIDE_STAT"));
				guideVO.setGuide_adminisno(rs.getString("GUIDE_ADMINISNO"));
				GuideVOList.add(guideVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return GuideVOList;
	}

	@Override
	public void delete(String guide_no) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO delete(PK) get connection successfully.");
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, guide_no);
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("GuideDAO: \"%s\" delete Successfully.%n", guide_no);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("GuideDAO: \"%s\" delete Failed.%n", guide_no);
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(GuideVO guideVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO delete(VO) get connection successfully.");
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, guideVO.getGuide_no());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("GuideDAO: \"%s\" delete Successfully.%n", guideVO.getGuide_title());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("GuideDAO: \"%s\" delete Failed.%n", guideVO.getGuide_title());
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(List<GuideVO> guideVOList) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("GuideDAO delete(List) get connection successfully.");
			pstmt = con.prepareStatement(SQL_DELETE);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(guideVOList.size());
			for (GuideVO guideVOi : guideVOList) {
				pstmt.setString(1, guideVOi.getGuide_no());
				pstmt.executeUpdate();
				System.out.printf("GuideDAO: deleting \"%s\"...%n", guideVOi.getGuide_no());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("GuideDAO: Delete total %d rows Successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("GuideDAO: Delete total %d rows Failed.%n", guideVOList.size());
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

//	public static void main(String[] args) {
//		String guide_no = "G000000002";
//		String guide_title = "Camp guide 02";
//		String guide_content = "This is some content of camp guide 02.";
//		long edit = new java.util.Date().getTime();
//		java.sql.Date guide_edit = new java.sql.Date(edit);
//		int guide_stat = 0;
//		String guide_adminisno = "A000000001";
//		GuideVO guideVO = new GuideVO(guide_no, guide_title, guide_content, guide_edit, guide_stat, guide_adminisno);
//		GuideDAO guideDAO = new GuideDAO();
////		// Test insert method.
////		guideDAO.insert(guideVO);
////		// Test insert method.
////		guideDAO.update(guideVO);
////		// Test findbyprimarykey
////		GuideVO guideVO2 = guideDAO.findByPrimaryKey(guide_no);
////		System.out.println(guideVO2.getGuide_no());
////		System.out.println(guideVO2.getGuide_title());
////		System.out.println(guideVO2.getGuide_content());
////		System.out.println(guideVO2.getGuide_edit());
////		System.out.println(guideVO2.getGuide_stat());
////		System.out.println(guideVO2.getGuide_adminisno());
////		// Test getAll method
////		List<GuideVO> guideVOList = guideDAO.getAll();
////		for(GuideVO guideVOi : guideVOList) {
////			System.out.println("=================");
////			System.out.println(guideVOi.getGuide_no());
////			System.out.println(guideVOi.getGuide_title());
////			System.out.println(guideVOi.getGuide_content());
////			System.out.println(guideVOi.getGuide_edit());
////			System.out.println(guideVOi.getGuide_stat());
////			System.out.println(guideVOi.getGuide_adminisno());
////		}
////		// Test delete(PK) method.
////		guideDAO.delete(guideVO);
////		// Test delete(VO) method.
////		guideDAO.delete(guide_no);
////		// Test delete(List) method.
////		List<GuideVO> guideList = guideDAO.getAll();
////		guideDAO.delete(guideList);
//		
//	}
}
