package com.cgintro.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CGIntroJDBCDAO implements CGIntroDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT =
			"INSERT INTO CG_INTRO(CGI_NO, CGI_VDNO, CGI_CONTENT)"
			+ " VALUES ('I' || LPAD(SEQ_CGINO.NEXTVAL, 9, '0'), ?, ?)";
	
	private static final String UPDATE_CONTENT_STMT =
			"UPDATE CG_INTRO SET CGI_CONTENT = ?, CGI_EDIT = SYSTIMESTAMP"
			+ " WHERE CGI_NO = ?";
	
	private static final String UPDATE_STAT_STMT =
			"UPDATE CG_INTRO SET CGI_STAT = ?"
			+ " WHERE CGI_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM CG_INTRO WHERE CGI_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT CGI_NO, CGI_VDNO, CGI_CONTENT, CGI_EDIT, CGI_STAT"
			+ " FROM CG_INTRO WHERE CGI_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT CGI_NO, CGI_VDNO, CGI_CONTENT, CGI_EDIT, CGI_STAT"
			+ " FROM CG_INTRO ORDER BY CGI_NO";
	
	private static final String GET_ONE_BYVDNO_STMT =
			"SELECT CGI_NO, CGI_VDNO, CGI_CONTENT, CGI_EDIT, CGI_STAT"
			+ " FROM CG_INTRO WHERE CGI_VDNO = ? AND CGI_STAT = 1";
	
	private static final String GET_MULTI_BYVDNO_STMT =
			"SELECT CGI_NO, CGI_VDNO, CGI_CONTENT, CGI_EDIT, CGI_STAT"
			+ " FROM CG_INTRO WHERE CGI_VDNO = ? ORDER BY CGI_NO";
	
	@Override
	public void insert(CGIntroVO cgIntroVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, cgIntroVO.getCgi_vdno());
			pstmt.setString(2, cgIntroVO.getCgi_content());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void updateContent(CGIntroVO cgIntroVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_CONTENT_STMT);
			
			pstmt.setString(1, cgIntroVO.getCgi_content());
			pstmt.setString(2, cgIntroVO.getCgi_no());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void updateStat(CGIntroVO cgIntroVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, cgIntroVO.getCgi_stat());
			pstmt.setString(2, cgIntroVO.getCgi_no());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
//	刪除前須先確認CGI_PIC的項目
	@Override
	public void delete(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, cgi_no);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public CGIntroVO findByPrimaryKey(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIntroVO cgIntroVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cgi_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cgIntroVO = new CGIntroVO();
				cgIntroVO.setCgi_no(rs.getString(1));
				cgIntroVO.setCgi_vdno(rs.getString(2));
				cgIntroVO.setCgi_content(rs.getString(3));
				cgIntroVO.setCgi_edit(rs.getTimestamp(4));
				cgIntroVO.setCgi_stat(rs.getInt(5));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cgIntroVO;
	}
	
	@Override
	public List<CGIntroVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIntroVO cgIntroVO = null;
		List<CGIntroVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
					
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgIntroVO = new CGIntroVO();
				cgIntroVO.setCgi_no(rs.getString(1));
				cgIntroVO.setCgi_vdno(rs.getString(2));
				cgIntroVO.setCgi_content(rs.getString(3));
				cgIntroVO.setCgi_edit(rs.getTimestamp(4));
				cgIntroVO.setCgi_stat(rs.getInt(5));
				list.add(cgIntroVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Override
	public CGIntroVO getCGIntroByVdno(String vd_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIntroVO cgIntroVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgIntroVO = new CGIntroVO();
				cgIntroVO.setCgi_no(rs.getString(1));
				cgIntroVO.setCgi_vdno(rs.getString(2));
				cgIntroVO.setCgi_content(rs.getString(3));
				cgIntroVO.setCgi_edit(rs.getTimestamp(4));
				cgIntroVO.setCgi_stat(rs.getInt(5));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cgIntroVO;
	}
	
	@Override
	public List<CGIntroVO> getCGIntrosByVdno(String vd_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIntroVO cgIntroVO = null;
		List<CGIntroVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_MULTI_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgIntroVO = new CGIntroVO();
				cgIntroVO.setCgi_no(rs.getString(1));
				cgIntroVO.setCgi_vdno(rs.getString(2));
				cgIntroVO.setCgi_content(rs.getString(3));
				cgIntroVO.setCgi_edit(rs.getTimestamp(4));
				cgIntroVO.setCgi_stat(rs.getInt(5));
				list.add(cgIntroVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. "
					+ e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
//	public static void main(String[] args) {
//		CGIntroJDBCDAO dao = new CGIntroJDBCDAO();
//		
//		// 新增
//		CGIntroVO cgIntroVO1 = new CGIntroVO();
//		cgIntroVO1.setCgi_vdno("V000000001");
//		cgIntroVO1.setCgi_content("HelloWorld!");
//		dao.insert(cgIntroVO1);
//		
//		// 修改內容
//		CGIntroVO cgIntroVO2 = new CGIntroVO();
//		cgIntroVO2.setCgi_no("I000000002");
//		cgIntroVO2.setCgi_content("How old are you?");
//		dao.updateContent(cgIntroVO2);
//		
//		// 修改狀態
//		CGIntroVO cgIntroVO3 = new CGIntroVO();
//		cgIntroVO3.setCgi_no("I000000003");
//		cgIntroVO3.setCgi_stat(1);
//		dao.updateStat(cgIntroVO3);
//		
//		// 刪除
//		dao.delete("I000000002");
//
//		// 查詢特定露營區介紹(cgi_no)
//		CGIntroVO cgIntroVO4 = dao.findByPrimaryKey("I000000001");
//		System.out.println(cgIntroVO4.getCgi_no());
//		System.out.println(cgIntroVO4.getCgi_vdno());
//		System.out.println(cgIntroVO4.getCgi_content());
//		System.out.println(cgIntroVO4.getCgi_edit());
//		System.out.println(cgIntroVO4.getCgi_stat());
//		System.out.println("====================");
//		
//		// 查詢全部
//		List<CGIntroVO> list = dao.getAll();
//		for (CGIntroVO cgIntroVO : list) {
//			System.out.println(cgIntroVO.getCgi_no());
//			System.out.println(cgIntroVO.getCgi_vdno());
//			System.out.println(cgIntroVO.getCgi_content());
//			System.out.println(cgIntroVO.getCgi_edit());
//			System.out.println(cgIntroVO.getCgi_stat());
//			System.out.println();
//		}
//		System.out.println("====================");
//		
//		// 查詢特定業者(vd_no)的上線露營區介紹
//		CGIntroVO cgIntroVO5 = dao.getCGIntroByVdno("V000000001");
//		System.out.println(cgIntroVO5.getCgi_no());
//		System.out.println(cgIntroVO5.getCgi_vdno());
//		System.out.println(cgIntroVO5.getCgi_content());
//		System.out.println(cgIntroVO5.getCgi_edit());
//		System.out.println(cgIntroVO5.getCgi_stat());
//		System.out.println("====================");
//		
//		// 查詢特定業者(vd_no)的所有露營區介紹
//		List<CGIntroVO> list2 = dao.getCGIntrosByVdno("V000000002");
//		for (CGIntroVO cgIntroVO : list2) {
//			System.out.println(cgIntroVO.getCgi_no());
//			System.out.println(cgIntroVO.getCgi_vdno());
//			System.out.println(cgIntroVO.getCgi_content());
//			System.out.println(cgIntroVO.getCgi_edit());
//			System.out.println(cgIntroVO.getCgi_stat());
//			System.out.println();
//		}
//	}
}