package com.waterfall.model;

import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;

public class WFPicJDBCDAO implements WFPicDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = 
		"INSERT INTO wf_pic (wfp_no, wfp_wfno, wfp_pic) VALUES ('WP' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT wfp_no, wfp_wfno, wfp_pic FROM wf_pic where wfp_wfno = ? order by wfp_no";
	private static final String GET_ONE_STMT = 
		"SELECT wfp_no, wfp_wfno, wfp_pic FROM wf_pic where wfp_no = ?";
	private static final String DELETE = 
		"DELETE FROM wf_pic where wfp_wfno = ?";

	@Override
	public void insert(WFPicVO wfpicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, wfpicVO.getWfp_wfno());
			pstmt.setBytes(2, wfpicVO.getWfp_pic());

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
	public void delete(String wfp_wfno) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, wfp_wfno);

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
	public WFPicVO findByPrimaryKey(String wfp_no) {

		WFPicVO wfpicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public static byte[] getPictureByteArray(String path) throws IOException {
		
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); //此資料流會把write的位元資料存到一個內建的byte=[]
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	public static void main(String[] args) throws IOException {

		WFPicJDBCDAO dao = new WFPicJDBCDAO();

		// 新增
//		byte[] pic = getPictureByteArray("/Users/doreenhsia/EE/EA103G8/WebContent/front-end/waterfall/img/pic1.jpg");
//		WFPicVO wfrpicVO1 = new WFPicVO();
//		wfrpicVO1.setWfp_wfno("W000000001");
//		wfrpicVO1.setWfp_pic(pic);
//		dao.insert(wfrpicVO1);
//		System.out.println("success");

	
		// 刪除
		dao.delete("W000000083");
		System.out.println("success");

		// 查詢
//		WFReplyVO wfreplyVO3 = dao.findByPrimaryKey("WR00000011");
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