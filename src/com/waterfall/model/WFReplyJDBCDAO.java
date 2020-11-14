package com.waterfall.model;

import java.util.*;

import java.sql.*;
import java.sql.Date;

public class WFReplyJDBCDAO implements WFReplyDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = 
		"INSERT INTO wf_reply (wfr_no, wfr_wfno, wfr_memno, wfr_content) VALUES ('WR' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT wfr_no, wfr_wfno, wfr_memno, wfr_content,to_char(wfr_edit,'yyyy-mm-dd') wfr_edit, wfr_stat FROM wf_reply order by wfr_no";
	private static final String GET_ONE_STMT = 
		"SELECT wfr_no, wfr_wfno, wfr_memno, wfr_content,to_char(wfr_edit,'yyyy-mm-dd hh24:mi') wfr_edit, wfr_stat FROM wf_reply where wfr_no = ?";
	private static final String DELETE = 
		"DELETE FROM wf_reply where wfr_no = ?";
	private static final String UPDATE = 
		"UPDATE wf_reply set wfr_content=?, wfr_stat=? where wfr_wfno = ?";
	private static final String FAKE_DELETE = 
		"UPDATE WF_REPLY SET wfr_stat = 0 WHERE wfr_no = ?";

	@Override
	public void insert(WFReplyVO wfreplyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, wfreplyVO.getWfr_wfno());
			pstmt.setString(2, wfreplyVO.getWfr_memno());
			pstmt.setString(3, wfreplyVO.getWfr_content());

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
	public void update(WFReplyVO wfreplyVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, wfreplyVO.getWfr_content());
			pstmt.setInt(2, wfreplyVO.getWfr_stat());
			pstmt.setString(3, wfreplyVO.getWfr_no());

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
	public void delete(String wfr_wfno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, wfr_wfno);

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
	public WFReplyVO findByPrimaryKey(String wfr_no) {

		WFReplyVO wfreplyVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	@Override
	public void fake_delete(String wfr_wfno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FAKE_DELETE);

			pstmt.setString(1, wfr_wfno);

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

	public static void main(String[] args) {

		WFReplyJDBCDAO dao = new WFReplyJDBCDAO();

//		// 新增
//		WFReplyVO wfreplyVO1 = new WFReplyVO();
//		wfreplyVO1.setWfr_wfno("W000000010");
//		wfreplyVO1.setWfr_memno("M000000006");
//		wfreplyVO1.setWfr_content("TEST1");
//		dao.insert(wfreplyVO1);
//		System.out.println("success");

		// 修改
//		WFReplyVO wfreplyVO2 = new WFReplyVO();
//		wfreplyVO2.setWfr_no("WR00000011");
//		wfreplyVO2.setWfr_content("HELLO2");
//		wfreplyVO2.setWfr_stat(1);		
//		dao.update(wfreplyVO2);
//		System.out.println("success");
//		
		// 刪除
//		dao.delete("WR00000125");
//		System.out.println("success");
		
		// fake_delete
		dao.fake_delete("WR00000071");
		System.out.println("success");
		
		// 查詢
//		WFReplyVO wfreplyVO3 = dao.findByPrimaryKey("WR00000132");
//		System.out.print(wfreplyVO3.getWfr_memno() + ",");
//		System.out.print(wfreplyVO3.getWfr_content() + ",");
//		System.out.println(wfreplyVO3.getWfr_edit() );
//		System.out.println("---------------------");

		// 查詢
//		List<WFReplyVO> list = dao.getAll();
//		for (WFReplyVO aEmp : list) {
//			System.out.print(aEmp.getWfr_no() + ",");
//			System.out.print(aEmp.getWfr_memno() + ",");
//			System.out.print(aEmp.getWfr_content() + ",");
//			System.out.print(aEmp.getWfr_edit());
//			System.out.println();
//		}
	}
}