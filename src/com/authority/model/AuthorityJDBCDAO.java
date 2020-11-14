package com.authority.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorityJDBCDAO implements AuthorityDAO_interface {

	// 載入驅動
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 載入資料庫
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO AUTHORITY (AUTH_NO, AUTH_NAME) VALUES (SEQ_AUTHNO.NEXTVAL, ?)";
	private static final String FIND_BY_AUTH_NO = "SELECT * FROM AUTHORITY WHERE AUTH_NO = ?";
	private static final String GET_ALL = "SELECT * FROM AUTHORITY ORDER BY AUTH_NO";
	private static final String delete = "DELETE FROM AUTHORITY WHERE AUTH_NO = ?";
	private static final String updata = "UPDATE AUTHORITY SET AUTH_NAME = ? WHERE AUTH_NO = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

	}

	@Override // 代表就是要新增的資料
	public void add(AuthorityVO authorityVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

	public static void main(String[] args) {

		AuthorityJDBCDAO dao = new AuthorityJDBCDAO();

		//新增測試		
		AuthorityVO authorityVO1 = new AuthorityVO();
		authorityVO1.setAuth_name("測試權限");
		dao.add(authorityVO1);

		// 修改測試
//		AuthorityVO authorityVO2 = new AuthorityVO();
//		authorityVO2.setAuth_no(21);
//		authorityVO2.setAuth_name("測試權限測試全縣");
//		dao.updata(authorityVO2);

//		//刪除測試	
//		dao.delete(21);

//		// 主鍵查詢
//		AuthorityVO authorityVO3 = dao.findByPrimaryKey(5);
//		System.out.print(authorityVO3.getAuth_no() + ",");
//		System.out.print(authorityVO3.getAuth_name() + ",");
//			System.out.println("---------------------");

//	//所有查詢
//		List<AuthorityVO> list = dao.getAll();
//		for (AuthorityVO authorityVO : list) {
//			System.out.print(authorityVO.getAuth_no() + ",");
//			System.out.print(authorityVO.getAuth_name() + ",");
//
//			System.out.println("---------------------");
//		}
	}
}
