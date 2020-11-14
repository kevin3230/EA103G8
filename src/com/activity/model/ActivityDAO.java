package com.activity.model;

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

public class ActivityDAO implements ActivityDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO ACTIVITY(ACT_NO, ACT_TITLE, ACT_CONTENT,ACT_ADMINISNO) "
			+ "												VALUES ('ACT' || LPAD(SEQ_ADMINISNO.NEXTVAL, 7, '0'),, ?, ?, ?, ?)";// 打配PreparedStatement
	private static final String FIND_BY_ACT_NO = "SELECT * FROM ACTIVITY WHERE ACT_NO = ?";
	private static final String GET_ALL = "SELECT * FROM ACTIVITY ORDER BY ACT_NO";
	private static final String delete = "DELETE FROM ACTIVITY where act_no = ?";
	private static final String updata = "UPDATE ACTIVITY"
			+ " set act_title=?, act_content=?, act_stat=?, act_adminisno=? where act_no = ?";

//	static {
//		try {
//			Class.forName(DRIVER);
//		} catch (ClassNotFoundException ce) {
//			ce.printStackTrace();
//		}
//
//	}

	@Override // 代表就是要新增的資料
	public void add(ActivityVO activityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, activityVO.getAct_no());
			pstmt.setString(2, activityVO.getAct_title());
			pstmt.setString(3, activityVO.getAct_content());
			pstmt.setString(4, activityVO.getAct_adminisno());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}

		}

	}

	@Override
	public void updata(ActivityVO activityVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setString(1, activityVO.getAct_title());
			pstmt.setString(2, activityVO.getAct_content());
			pstmt.setInt(3, activityVO.getAct_stat());
			pstmt.setString(4, activityVO.getAct_adminisno());
			pstmt.setString(5, activityVO.getAct_no());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String act_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(delete);

			pstmt.setString(1, act_no);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public ActivityVO findByPrimaryKey(String act_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ActivityVO activityVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ACT_NO);
			pstmt.setString(1, act_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				activityVO = new ActivityVO();
				activityVO.setAct_no(act_no);
				activityVO.setAct_title(rs.getString("ACT_TITLE"));
				activityVO.setAct_content(rs.getString("ACT_CONTENT"));
				activityVO.setAct_edit(rs.getDate("ACT_EDIT"));
				activityVO.setAct_stat(rs.getInt("ACT_STAT"));
				activityVO.setAct_adminisno(rs.getString("ACT_ADMINISNO"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return activityVO;
	}

	@Override
	public List<ActivityVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ActivityVO> activityVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				ActivityVO activityVO = new ActivityVO();

				activityVO.setAct_no(rs.getString("Act_no"));
				activityVO.setAct_title(rs.getString("ACT_TITLE"));
				activityVO.setAct_content(rs.getString("ACT_CONTENT"));
				activityVO.setAct_edit(rs.getDate("ACT_EDIT"));
				activityVO.setAct_stat(rs.getInt("ACT_STAT"));
				activityVO.setAct_adminisno(rs.getString("ACT_ADMINISNO"));

				activityVOList.add(activityVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}

		return activityVOList;
	}

}
