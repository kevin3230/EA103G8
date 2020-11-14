package com.eqptintro.model;
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



public class EqptIntroDAO implements EqptIntroDAO_interface {


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
			"INSERT INTO EQPT_INTRO (ei_no, ei_adminisno, ei_title, ei_content) VALUES ('EI' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT ei_no, ei_adminisno, ei_title, ei_content, to_char(ei_edit,'yyyy-mm-dd') ei_edit, ei_stat FROM EQPT_INTRO order by ei_no";
	private static final String GET_ONE_STMT = 
			"SELECT ei_no, ei_adminisno, ei_title, ei_content, to_char(ei_edit,'yyyy-mm-dd') ei_edit, ei_stat FROM EQPT_INTRO where ei_no = ?";
	private static final String DELETE = 
			"DELETE FROM EQPT_INTRO where ei_no = ?";
	private static final String UPDATE = 
			"UPDATE EQPT_INTRO set ei_adminisno=?, ei_title=?, ei_content=?, ei_stat=?, ei_edit=systimestamp where ei_no = ?";

	@Override
	public void insert(EqptIntroVO eqptintroVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, eqptintroVO.getEi_adminisno());
			pstmt.setString(2, eqptintroVO.getEi_title());
			pstmt.setString(3, eqptintroVO.getEi_content());

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
	public void update(EqptIntroVO eqptintroVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, eqptintroVO.getEi_adminisno());
			pstmt.setString(2, eqptintroVO.getEi_title());
			pstmt.setString(3, eqptintroVO.getEi_content());
			pstmt.setInt(4, eqptintroVO.getEi_stat());
			pstmt.setString(5, eqptintroVO.getEi_no());

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
	public void delete(String ei_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ei_no);

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
	public EqptIntroVO findByPrimaryKey(String ei_no) {
		
		EqptIntroVO eqptintroVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, ei_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// eqptintroVO 也稱為 Domain objects
				eqptintroVO = new EqptIntroVO();
				eqptintroVO.setEi_no(rs.getString("ei_no"));
				eqptintroVO.setEi_adminisno(rs.getString("ei_adminisno"));
				eqptintroVO.setEi_title(rs.getString("ei_title"));
				eqptintroVO.setEi_content(rs.getString("ei_content"));
				eqptintroVO.setEi_edit(rs.getDate("ei_edit"));
				eqptintroVO.setEi_stat(rs.getInt("ei_stat"));
			
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
		return eqptintroVO;
	}

	@Override
	public List<EqptIntroVO> getAll() {
		List<EqptIntroVO> list = new ArrayList<EqptIntroVO>();
		EqptIntroVO eqptintroVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// eqptintroVO 也稱為 Domain objects
				eqptintroVO = new EqptIntroVO();
				eqptintroVO.setEi_no(rs.getString("ei_no"));
				eqptintroVO.setEi_adminisno(rs.getString("ei_adminisno"));
				eqptintroVO.setEi_title(rs.getString("ei_title"));
				eqptintroVO.setEi_content(rs.getString("ei_content"));
				eqptintroVO.setEi_edit(rs.getDate("ei_edit"));
				eqptintroVO.setEi_stat(rs.getInt("ei_stat"));
				list.add(eqptintroVO); // Store the row in the list
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
