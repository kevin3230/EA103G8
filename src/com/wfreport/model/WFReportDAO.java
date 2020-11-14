package com.wfreport.model;
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



public class WFReportDAO implements WFReportDAO_interface {


	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
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
		"INSERT INTO wf_report (rep_no, rep_wfno, rep_wfrno, rep_memno, rep_content) VALUES ('R' || LPAD(SEQ_WFNO.NEXTVAL, 9, '0'), ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT rep_no, rep_wfno, rep_wfrno, rep_memno, rep_adminisno, rep_content, rep_stat FROM wf_report order by rep_no DESC";
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, wfreportVO.getRep_wfno());
			pstmt.setString(2, wfreportVO.getRep_wfrno());
			pstmt.setString(3, wfreportVO.getRep_memno());
			pstmt.setString(4, wfreportVO.getRep_content());

			pstmt.executeUpdate();

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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PASS);

			pstmt.setString(1, wfreportVO.getRep_adminisno());
			pstmt.setString(2, wfreportVO.getRep_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_UNPASS);

			pstmt.setString(1, wfreportVO.getRep_adminisno());
			pstmt.setString(2, wfreportVO.getRep_no());

			pstmt.executeUpdate();

			// Handle any driver errors
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

			con = ds.getConnection();
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

			con = ds.getConnection();
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
}
