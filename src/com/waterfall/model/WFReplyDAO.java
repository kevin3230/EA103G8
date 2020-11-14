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



public class WFReplyDAO implements WFReplyDAO_interface {


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
			"INSERT INTO wf_reply (wfr_no, wfr_wfno, wfr_memno, wfr_content) VALUES ('WR' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT wfr_no, wfr_wfno, wfr_memno, wfr_content,to_char(wfr_edit,'yyyy-mm-dd hh24:mi') wfr_edit, wfr_stat FROM wf_reply order by wfr_no";
	private static final String GET_ONE_STMT = 
			"SELECT wfr_no, wfr_wfno, wfr_memno, wfr_content,to_char(wfr_edit,'yyyy-mm-dd hh24:mi') wfr_edit, wfr_stat FROM wf_reply where wfr_no = ?";
	private static final String DELETE = 
			"DELETE FROM wf_reply where wfr_wfno = ?";
	private static final String UPDATE = 
			"UPDATE wf_reply set wfr_content=?, wfr_edit=systimestamp  where wfr_no = ?";
	private static final String FAKE_DELETE = 
			"UPDATE WF_REPLY SET wfr_stat = 0 WHERE wfr_no = ?";
	
	@Override
	public void insert(WFReplyVO wfreplyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, wfreplyVO.getWfr_wfno());
			pstmt.setString(2, wfreplyVO.getWfr_memno());
			pstmt.setString(3, wfreplyVO.getWfr_content());

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
	public void update(WFReplyVO wfreplyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, wfreplyVO.getWfr_content());
			pstmt.setString(2, wfreplyVO.getWfr_no());

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
	public void delete(String wfr_wfno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, wfr_wfno);

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
	public WFReplyVO findByPrimaryKey(String wfr_no) {
		
		WFReplyVO wfreplyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, wfr_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfreplyVO 也稱為 Domain objects
				wfreplyVO = new WFReplyVO();
				wfreplyVO.setWfr_no(rs.getString("wfr_no"));
				wfreplyVO.setWfr_wfno(rs.getString("wfr_wfno"));
				wfreplyVO.setWfr_memno(rs.getString("wfr_memno"));
				wfreplyVO.setWfr_content(rs.getString("wfr_content"));
				wfreplyVO.setWfr_edit(rs.getString("wfr_edit"));
				wfreplyVO.setWfr_stat(rs.getInt("wfr_stat"));
			
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
		return wfreplyVO;
	}

	@Override
	public List<WFReplyVO> getAll() {
		List<WFReplyVO> list = new ArrayList<WFReplyVO>();
		WFReplyVO wfreplyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfreplyVO 也稱為 Domain objects
				wfreplyVO = new WFReplyVO();
				wfreplyVO.setWfr_no(rs.getString("wfr_no"));
				wfreplyVO.setWfr_wfno(rs.getString("wfr_wfno"));
				wfreplyVO.setWfr_memno(rs.getString("wfr_memno"));
				wfreplyVO.setWfr_content(rs.getString("wfr_content"));
				wfreplyVO.setWfr_edit(rs.getString("wfr_edit"));
				wfreplyVO.setWfr_stat(rs.getInt("wfr_stat"));
				list.add(wfreplyVO); // Store the row in the list
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
	@Override
	public void fake_delete(String wfr_wfno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FAKE_DELETE);

			pstmt.setString(1, wfr_wfno);

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
}
