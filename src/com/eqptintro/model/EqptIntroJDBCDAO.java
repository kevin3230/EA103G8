package com.eqptintro.model;

import java.util.*;

import java.sql.*;

public class EqptIntroJDBCDAO implements EqptIntroDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, eqptintroVO.getEi_adminisno());
			pstmt.setString(2, eqptintroVO.getEi_title());
			pstmt.setString(3, eqptintroVO.getEi_content());

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
	public void update(EqptIntroVO eqptintroVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, eqptintroVO.getEi_adminisno());
			pstmt.setString(2, eqptintroVO.getEi_title());
			pstmt.setString(3, eqptintroVO.getEi_content());
			pstmt.setInt(4, eqptintroVO.getEi_stat());
			pstmt.setString(5, eqptintroVO.getEi_no());

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
	public void delete(String ei_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, ei_no);

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
	public EqptIntroVO findByPrimaryKey(String ei_no) {

		EqptIntroVO eqptintroVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

	public static void main(String[] args) {

		EqptIntroJDBCDAO dao = new EqptIntroJDBCDAO();

		// 新增
		EqptIntroVO eqptintroVO1 = new EqptIntroVO();
		eqptintroVO1.setEi_adminisno("A000000009");
		eqptintroVO1.setEi_title("HelloWorld10");
		eqptintroVO1.setEi_content("test");
		dao.insert(eqptintroVO1);
		System.out.println("success");

		// 修改
//		EqptIntroVO eqptintroVO2 = new EqptIntroVO();
//		eqptintroVO2.setEi_no("EI00000021");
//		eqptintroVO2.setEi_adminisno("A000000009");
//		eqptintroVO2.setEi_title("HEEEE");
//		eqptintroVO2.setEi_content("HELLO2");
//		eqptintroVO2.setEi_stat(1);		
//		dao.update(eqptintroVO2);
//		System.out.println("success");
		
		// 刪除
//		dao.delete("EI00000008");
//		System.out.println("success");

		// 查詢
//		EqptIntroVO eqptintroVO3 = dao.findByPrimaryKey("EI00000009");
//		System.out.print(eqptintroVO3.getEi_adminisno() + ",");
//		System.out.print(eqptintroVO3.getEi_content() + ",");
//		System.out.println(eqptintroVO3.getEi_edit() );
//		System.out.println("---------------------");

//		// 查詢
//		List<EqptIntroVO> list = dao.getAll();
//		for (EqptIntroVO aEmp : list) {
//			System.out.print(aEmp.getEi_no() + ",");
//			System.out.print(aEmp.getEi_adminisno() + ",");
//			System.out.print(aEmp.getEi_title() + ",");
//			System.out.print(aEmp.getEi_content() + ",");
//			System.out.print(aEmp.getEi_edit() );
//			System.out.println();
//		}
	}
}