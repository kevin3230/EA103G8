package com.authority.model;

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

public class AuthorityDAO implements AuthorityDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO AUTHORITY (AUTH_NO, AUTH_NAME) VALUES (SEQ_AUTHNO.NEXTVAL, ?)";
	private static final String FIND_BY_AUTH_NO = "SELECT * FROM AUTHORITY WHERE AUTH_NO = ?";
	private static final String GET_ALL = "SELECT * FROM AUTHORITY ORDER BY AUTH_NO";
	private static final String delete = "DELETE FROM AUTHORITY WHERE AUTH_NO = ?";
	private static final String updata = "UPDATE AUTHORITY SET AUTH_NAME = ? WHERE AUTH_NO = ?";


	@Override // 代表就是要新增的資料
	public void add(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, authorityVO.getAuth_name());
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
	public void updata(AuthorityVO authorityVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata);

			pstmt.setString(1, authorityVO.getAuth_name());
			pstmt.setInt(2, authorityVO.getAuth_no());
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
	public void delete(Integer auth_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(delete);

			pstmt.setInt(1, auth_no);

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
	public AuthorityVO findByPrimaryKey(Integer auth_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AuthorityVO authorityVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_AUTH_NO);
			pstmt.setInt(1, auth_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				authorityVO = new AuthorityVO();
				authorityVO.setAuth_no(auth_no);
				authorityVO.setAuth_name(rs.getString("AUTH_NAME"));

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

		return authorityVO;
	}

	@Override
	public List<AuthorityVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AuthorityVO> authorityVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				AuthorityVO authorityVO = new AuthorityVO();
				authorityVO.setAuth_no(rs.getInt("AUTH_NO"));
				authorityVO.setAuth_name(rs.getString("AUTH_NAME"));

				authorityVOList.add(authorityVO);
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

		return authorityVOList;
	}

}
