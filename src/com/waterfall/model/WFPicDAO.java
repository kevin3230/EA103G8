package com.waterfall.model;
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



public class WFPicDAO implements WFPicDAO_interface {


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
		"INSERT INTO wf_pic (wfp_no, wfp_wfno, wfp_pic) VALUES ('WP' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT wfp_no, wfp_wfno, wfp_pic FROM wf_pic where wfp_wfno = ? order by wfp_no";
	private static final String GET_ONE_STMT = 
		"SELECT wfp_no, wfp_wfno, wfp_pic FROM wf_pic where wfp_no = ?";
	private static final String DELETE = 
		"DELETE FROM wf_pic where wfp_no = ?";

	@Override
	public void insert(WFPicVO wfpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, wfpicVO.getWfp_wfno());
			pstmt.setBytes(2, wfpicVO.getWfp_pic());

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
	public void delete(String wfp_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, wfp_no);

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
	public WFPicVO findByPrimaryKey(String wfp_no) {
		
		WFPicVO wfpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, wfp_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfrepicVO 也稱為 Domain objects
				wfpicVO = new WFPicVO();
				wfpicVO.setWfp_no(rs.getString("wfp_no"));
				wfpicVO.setWfp_wfno(rs.getString("wfp_wfno"));
				wfpicVO.setWfp_pic(rs.getBytes("wfp_pic"));
			
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
		return wfpicVO;
	}

	@Override
	public List<WFPicVO> getAll(String wfp_no) {
		List<WFPicVO> list = new ArrayList<WFPicVO>();
		WFPicVO wfpicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			
			pstmt.setString(1, wfp_no);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfrepicVO 也稱為 Domain objects
				wfpicVO = new WFPicVO();
				wfpicVO.setWfp_no(rs.getString("wfp_no"));
				wfpicVO.setWfp_wfno(rs.getString("wfp_wfno"));
				wfpicVO.setWfp_pic(rs.getBytes("wfp_pic"));
				list.add(wfpicVO); // Store the row in the list
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
