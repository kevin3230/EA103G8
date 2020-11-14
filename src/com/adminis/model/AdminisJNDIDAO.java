package com.adminis.model;

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

public class AdminisJNDIDAO implements AdminisDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlaningDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO ADMINIS (adminis_no, adminis_name, adminis_pwd, adminis_email, adminis_dept,  adminis_pv) "
			+ "VALUES ('A' || LPAD(SEQ_ADMINISNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?)";// 打配PreparedStatement
	private static final String FIND_BY_ADMINIS_NO = "SELECT * FROM ADMINIS WHERE ADMINIS_NO = ?";
	private static final String GET_ALL = "SELECT * FROM ADMINIS ORDER BY ADMINIS_NO";
	private static final String GET_Adminis_stat = "SELECT * FROM ADMINIS WHERE ADMINIS_STAT=? ";
	private static final String delete = "DELETE FROM ADMINIS where ADMINIS_NO = ?";
	private static final String swap = "UPDATE ADMINIS SET ADMINIS_STAT=? where ADMINIS_NO = ?";
	private static final String updata = "UPDATE ADMINIS SET ADMINIS_NAME=?, ADMINIS_PWD=?, ADMINIS_EMAIL = ?, ADMINIS_DEPT = ? "
			+ ",  ADMINIS_PV=?   WHERE ADMINIS_NO = ?";

	@Override // 代表就是要新增的資料
	public void add(AdminisVO adminisVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminisVO.getAdminis_name());
			pstmt.setString(2, adminisVO.getAdminis_pwd());
			pstmt.setString(3, adminisVO.getAdminis_email());
			pstmt.setString(4, adminisVO.getAdminis_dept());
			pstmt.setInt(5, adminisVO.getAdminis_pv());
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
	public void updata(AdminisVO adminisVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setString(1, adminisVO.getAdminis_name());
			pstmt.setString(2, adminisVO.getAdminis_pwd());
			pstmt.setString(3, adminisVO.getAdminis_email());
			pstmt.setString(4, adminisVO.getAdminis_dept());
			pstmt.setInt(5, adminisVO.getAdminis_pv());
			pstmt.setString(6, adminisVO.getAdminis_no());
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
	public void delete(String adminis_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(delete);
			
			pstmt.setString(1, adminis_no);

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
	public void swap(String adminis_no, Integer adminis_stat) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(swap);
			pstmt.setInt(1, adminis_stat);
			pstmt.setString(2, adminis_no);

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
	public AdminisVO findByPrimaryKey(String adminis_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminisVO adminisVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ADMINIS_NO);
			pstmt.setString(1, adminis_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				adminisVO = new AdminisVO();
				adminisVO.setAdminis_no(adminis_no);
				adminisVO.setAdminis_name(rs.getString("ADMINIS_NAME"));
				adminisVO.setAdminis_pwd(rs.getString("ADMINIS_PWD"));
				adminisVO.setAdminis_email(rs.getString("ADMINIS_EMAIL"));
				adminisVO.setAdminis_dept(rs.getString("ADMINIS_DEPT"));
				adminisVO.setAdminis_regdate(rs.getDate("ADMINIS_REGDATE"));
				adminisVO.setAdminis_pv(rs.getInt("ADMINIS_PV"));
				adminisVO.setAdminis_stat(rs.getInt("ADMINIS_STAT"));

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

		return adminisVO;
	}

	@Override
	public List<AdminisVO> findByAdminis_stat(Integer adminis_stat) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminisVO> adminisstat = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_Adminis_stat);
			pstmt.setInt(1, adminis_stat);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				AdminisVO adminisVO = new AdminisVO();
				adminisVO.setAdminis_no(rs.getString("ADMINIS_NO"));
				adminisVO.setAdminis_name(rs.getString("ADMINIS_NAME"));
				adminisVO.setAdminis_pwd(rs.getString("ADMINIS_PWD"));
				adminisVO.setAdminis_email(rs.getString("ADMINIS_EMAIL"));
				adminisVO.setAdminis_dept(rs.getString("ADMINIS_DEPT"));
				adminisVO.setAdminis_regdate(rs.getDate("ADMINIS_REGDATE"));
				adminisVO.setAdminis_pv(rs.getInt("ADMINIS_PV"));
				adminisVO.setAdminis_stat(adminis_stat);
				adminisstat.add(adminisVO);
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

		return adminisstat;
	}	

		
	@Override
	public List<AdminisVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminisVO> adminisVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AdminisVO adminisVO = new AdminisVO();
				adminisVO.setAdminis_no(rs.getString("ADMINIS_NO"));
				adminisVO.setAdminis_name(rs.getString("ADMINIS_NAME"));
				adminisVO.setAdminis_pwd(rs.getString("ADMINIS_PWD"));
				adminisVO.setAdminis_email(rs.getString("ADMINIS_EMAIL"));
				adminisVO.setAdminis_dept(rs.getString("ADMINIS_DEPT"));
				adminisVO.setAdminis_regdate(rs.getDate("ADMINIS_REGDATE"));
				adminisVO.setAdminis_pv(rs.getInt("ADMINIS_PV"));
				adminisVO.setAdminis_stat(rs.getInt("ADMINIS_STAT"));
				adminisVOList.add(adminisVO);
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

		return adminisVOList;
	}
}
