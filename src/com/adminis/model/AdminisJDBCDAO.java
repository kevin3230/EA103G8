package com.adminis.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminisJDBCDAO implements AdminisDAO_interface {

	// 載入驅動
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 載入資料庫
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO ADMINIS (adminis_no, adminis_name, adminis_pwd, adminis_email, adminis_dept,  adminis_pv) "
			+ "VALUES ('A' || LPAD(SEQ_ADMINISNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?)";// 打配PreparedStatement
	private static final String FIND_BY_ADMINIS_NO = "SELECT * FROM ADMINIS WHERE ADMINIS_NO = ?";
	private static final String GET_ALL = "SELECT * FROM ADMINIS ORDER BY ADMINIS_NO";
//	private static final String delete = "DELETE FROM ADMINIS where ADMINIS_NO = ?";
	private static final String delete = "UPDATE ADMINIS set ADMINIS_STAT=0 where ADMINIS_NO = ?";
	private static final String updata = "UPDATE ADMINIS SET ADMINIS_NAME=?, ADMINIS_PWD=?, ADMINIS_EMAIL = ?, ADMINIS_DEPT = ? "
			+ ", ADMINIS_PV=?, ADMINIS_STAT= ?  WHERE ADMINIS_NO = ?";

	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

	}

	@Override // 代表就是要新增的資料
	public void add(AdminisVO adminisVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, adminisVO.getAdminis_name());
			pstmt.setString(2, adminisVO.getAdminis_pwd());//1.亂數密碼2.用EMAIL寄出3.編碼存錄(先不做)
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
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(updata);

			pstmt.setString(1, adminisVO.getAdminis_name());
			pstmt.setString(2, adminisVO.getAdminis_pwd());
			pstmt.setString(3, adminisVO.getAdminis_email());
			pstmt.setString(4, adminisVO.getAdminis_dept());
			pstmt.setInt(5, adminisVO.getAdminis_pv());
			pstmt.setInt(6, adminisVO.getAdminis_stat());
			pstmt.setString(7, adminisVO.getAdminis_no());
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

			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
	public AdminisVO findByPrimaryKey(String adminis_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AdminisVO adminisVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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
	public List<AdminisVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AdminisVO> adminisVOList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
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

	public static void main(String[] args) {

//		AdminisJDBCDAO dao = new AdminisJDBCDAO();
		
		
		int i = 2;
		System.out.println(Integer.toBinaryString(i));
		String str = Integer.toBinaryString(i);
            int no = Integer.parseInt(str);
            str = String.format("%011d",no); 

            System.out.println(str);
		
		
//		//新增測試		
		
		
//		AdminisVO adminisVO1 = new AdminisVO();
//
//		
//		adminisVO1.setAdminis_name("測試人員01");
//		adminisVO1.setAdminis_pwd("123456");
//		adminisVO1.setAdminis_email("ea103_9@gmail.com");
//		adminisVO1.setAdminis_dept("測試部");
//		adminisVO1.setAdminis_pv(1245);
//		dao.add(adminisVO1);
//
//		AdminisVO adminisVO2 = new AdminisVO();
//		adminisVO2.setAdminis_no("A000000021");
//		adminisVO2.setAdminis_name("測試人員01");
//		adminisVO2.setAdminis_pwd("123456");
//		adminisVO2.setAdminis_email("ea103_9@gmail.com");
//		adminisVO2.setAdminis_dept("測試部");
//		adminisVO2.setAdminis_pv(1245);
//		adminisVO2.setAdminis_stat(0);
//		dao.updata(adminisVO2);

//		//刪除測試	
//		dao.delete("A000000021");

		// 主鍵查詢
//		AdminisVO adminisVO3 = dao.findByPrimaryKey("A000000001");
//		System.out.print(adminisVO3.getAdminis_no() + ",");
//		System.out.print(adminisVO3.getAdminis_name() + ",");
//		System.out.print(adminisVO3.getAdminis_pwd() + ",");
//		System.out.print(adminisVO3.getAdminis_email() + ",");
//		System.out.print(adminisVO3.getAdminis_dept() + ",");
//		System.out.print(adminisVO3.getAdminis_regdate() + ",");
//		System.out.print(adminisVO3.getAdminis_pv() + ",");
//		System.out.print(adminisVO3.getAdminis_stat() + ",");
//		System.out.println("---------------------");

	
//	//所有查詢
//		List<AdminisVO> list = dao.getAll();
//		for (AdminisVO adminisVO : list) {
//			System.out.print(adminisVO.getAdminis_no() + ",");
//			System.out.print(adminisVO.getAdminis_name() + ",");
//			System.out.print(adminisVO.getAdminis_pwd() + ",");
//			System.out.print(adminisVO.getAdminis_email() + ",");
//			System.out.print(adminisVO.getAdminis_dept() + ",");
//			System.out.print(adminisVO.getAdminis_regdate() + ",");
//			System.out.print(adminisVO.getAdminis_pv() + ",");
//			System.out.print(adminisVO.getAdminis_stat() + ",");
//			System.out.println("---------------------");
//		}
	}

	@Override
	public void swap(String adminis_no, Integer adminis_stat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AdminisVO> findByAdminis_stat(Integer adminis_stat) {
		// TODO Auto-generated method stub
		return null;
	}

}