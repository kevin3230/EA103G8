package com.wfreport.model;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class WFReportJDBCDAO implements WFReportDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = 
		"INSERT INTO wf_report (rep_no, rep_wfno, rep_wfrno, rep_memno, rep_content) VALUES ('R' || LPAD(SEQ_WFNO.NEXTVAL, 9, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT rep_no, rep_wfno, rep_wfrno, rep_memno, rep_adminisno, rep_content, rep_stat FROM wf_report order by rep_no";
	private static final String GET_ONE_STMT = 
		"SELECT rep_no, rep_wfno, rep_wfrno, rep_memno, rep_adminisno, rep_content, rep_stat FROM wf_report where rep_no = ?";
	private static final String UPDATE_UNPASS = 
		"UPDATE wf_report set rep_stat= 0, rep_adminisno=? where rep_no = ?";
	private static final String UPDATE_PASS = 
		"UPDATE wf_report set rep_stat= 1, rep_adminisno=? where rep_no = ?";

	@Override
	public void insert(WFReportVO wfreportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, wfreportVO.getRep_wfno());
			pstmt.setString(2, wfreportVO.getRep_wfrno());
			pstmt.setString(3, wfreportVO.getRep_memno());
			pstmt.setString(4, wfreportVO.getRep_content());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update_pass(WFReportVO wfreportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_PASS);

			pstmt.setString(1, wfreportVO.getRep_adminisno());
			pstmt.setString(2, wfreportVO.getRep_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update_unpass(WFReportVO wfreportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_UNPASS);

			pstmt.setString(1, wfreportVO.getRep_adminisno());
			pstmt.setString(2, wfreportVO.getRep_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public WFReportVO findByPrimaryKey(String rep_no) {

		WFReportVO wfreportVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, rep_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfreplyVO 也稱為 Domain objects
				wfreportVO = new WFReportVO();
				wfreportVO.setRep_no(rs.getString("rep_no"));
				wfreportVO.setRep_wfno(rs.getString("rep_wfno"));
				wfreportVO.setRep_wfrno(rs.getString("rep_wfrno"));
				wfreportVO.setRep_memno(rs.getString("rep_memno"));
				wfreportVO.setRep_adminisno(rs.getString("rep_adminisno"));
				wfreportVO.setRep_content(rs.getString("rep_content"));
				wfreportVO.setRep_stat(rs.getInt("rep_stat"));
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return wfreportVO;
	}

	@Override
	public List<WFReportVO> getAll() {
		List<WFReportVO> list = new ArrayList<WFReportVO>();
		WFReportVO wfreportVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfreplyVO 也稱為 Domain objects
				wfreportVO = new WFReportVO();
				wfreportVO.setRep_no(rs.getString("rep_no"));
				wfreportVO.setRep_wfno(rs.getString("rep_wfno"));
				wfreportVO.setRep_wfrno(rs.getString("rep_wfrno"));
				wfreportVO.setRep_memno(rs.getString("rep_memno"));
				wfreportVO.setRep_adminisno(rs.getString("rep_adminisno"));
				wfreportVO.setRep_content(rs.getString("rep_content"));
				wfreportVO.setRep_stat(rs.getInt("rep_stat"));
				list.add(wfreportVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		WFReportJDBCDAO dao = new WFReportJDBCDAO();

		// 新增
		WFReportVO wfreportVO1 = new WFReportVO();
		wfreportVO1.setRep_wfno("W000000011");
		wfreportVO1.setRep_wfrno("WR00000072");
		wfreportVO1.setRep_memno("M000000006");
		wfreportVO1.setRep_content("TEST456");
		dao.insert(wfreportVO1);
		System.out.println("success");

		// 修改R000000014
//		WFReportVO wfreportVO2 = new WFReportVO();
//		wfreportVO2.setRep_no("R000000014");
//		wfreportVO2.setRep_stat(1);	
//		wfreportVO2.setRep_adminisno("A000000009");
//		dao.update(wfreportVO2);
//		System.out.println("success");
		
		// 查詢
//		WFReportVO wfreportVO3 = dao.findByPrimaryKey("R000000014");
//		System.out.print(wfreportVO3.getRep_no() + ",");
//		System.out.print(wfreportVO3.getRep_wfno() + ",");
//		System.out.print(wfreportVO3.getRep_wfrno() + ",");
//		System.out.print(wfreportVO3.getRep_memno() + ",");
//		System.out.print(wfreportVO3.getRep_adminisno() + ",");
//		System.out.print(wfreportVO3.getRep_content() + ",");
//		System.out.println(wfreportVO3.getRep_stat() );
//		System.out.println("---------------------");

		// 查詢
//		List<WFReportVO> list = dao.getAll();
//		for (WFReportVO aRep : list) {
//			System.out.print(aRep.getRep_no() + ",");
//			System.out.print(aRep.getRep_wfno() + ",");
//			System.out.print(aRep.getRep_wfrno() + ",");
//			System.out.print(aRep.getRep_memno() + ",");
//			System.out.print(aRep.getRep_adminisno() + ",");
//			System.out.print(aRep.getRep_content() + ",");
//			System.out.print(aRep.getRep_stat());
//			System.out.println();
//		}
	}
}