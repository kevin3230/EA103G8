package com.cgintro.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CGIPicJDBCDAO implements CGIPicDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO CGI_PIC(CGIP_NO, CGIP_CGINO, CGIP_PIC)"
			+ " VALUES ('IP' || LPAD(SEQ_CGIPNO.NEXTVAL, 8, '0'), ?, ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE CGI_PIC SET CGIP_PIC = ?"
			+ " WHERE CGIP_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM CGI_PIC WHERE CGIP_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT CGIP_NO, CGIP_CGINO, CGIP_PIC"
			+ " FROM CGI_PIC WHERE CGIP_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT CGIP_NO, CGIP_CGINO, CGIP_PIC"
			+ " FROM CGI_PIC ORDER BY CGIP_NO";
	
	private static final String GET_MULTI_BYCGIPNO_STMT =
			"SELECT CGIP_NO, CGIP_CGINO, CGIP_PIC"
			+ " FROM CGI_PIC WHERE CGIP_CGINO = ? ORDER BY CGIP_NO";

	@Override
	public void insert(CGIPicVO cgiPicVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, cgiPicVO.getCgip_cgino());
			pstmt.setBytes(2, cgiPicVO.getCgip_pic());
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
	public void update(CGIPicVO cgiPicVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setBytes(1, cgiPicVO.getCgip_pic());
			pstmt.setString(2, cgiPicVO.getCgip_no());
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
	public void delete(String cgip_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, cgip_no);
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
	public CGIPicVO findByPrimaryKey(String cgip_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIPicVO cgiPicVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cgip_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cgiPicVO = new CGIPicVO();
				cgiPicVO.setCgip_no(rs.getString(1));
				cgiPicVO.setCgip_cgino(rs.getString(2));
				cgiPicVO.setCgip_pic(rs.getBytes(3));
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
		return cgiPicVO;
	}

	@Override
	public List<CGIPicVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIPicVO cgiPicVO = null;
		List<CGIPicVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
					
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgiPicVO = new CGIPicVO();
				cgiPicVO.setCgip_no(rs.getString(1));
				cgiPicVO.setCgip_cgino(rs.getString(2));
				cgiPicVO.setCgip_pic(rs.getBytes(3));
				list.add(cgiPicVO);
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
	public List<CGIPicVO> getCGIPicsByCgino(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIPicVO cgiPicVO = null;
		List<CGIPicVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_MULTI_BYCGIPNO_STMT);
			
			pstmt.setString(1, cgi_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgiPicVO = new CGIPicVO();
				cgiPicVO.setCgip_no(rs.getString(1));
				cgiPicVO.setCgip_cgino(rs.getString(2));
				cgiPicVO.setCgip_pic(rs.getBytes(3));
				list.add(cgiPicVO);
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

	public static void main(String[] args) {
		CGIPicJDBCDAO dao = new CGIPicJDBCDAO();
		
		// 新增
		CGIPicVO cgiPicVO1 = new CGIPicVO();
		cgiPicVO1.setCgip_cgino("I000000001");
		byte[] b = {0, 0, 0};
		cgiPicVO1.setCgip_pic(b);
		dao.insert(cgiPicVO1);
		
		// 修改
		CGIPicVO cgiPicVO2 = new CGIPicVO();
		byte[] b2 = {0, 1, 2, 3, 4, 5};
		cgiPicVO2.setCgip_pic(b2);
		cgiPicVO2.setCgip_no("IP00000001");
		dao.update(cgiPicVO2);
		
		// 刪除
		dao.delete("IP00000002");

		// 查詢特定露營區介紹圖片(cgip_no)
		CGIPicVO cgiPicVO3 = dao.findByPrimaryKey("IP00000001");
		System.out.println(cgiPicVO3.getCgip_no());
		System.out.println(cgiPicVO3.getCgip_cgino());
		for (byte i : cgiPicVO3.getCgip_pic())
			System.out.print(i + "\t");
		System.out.println();
		System.out.println("====================");
		
		// 查詢全部
		List<CGIPicVO> list = dao.getAll();
		for (CGIPicVO cgiPicVO : list) {
			System.out.println(cgiPicVO.getCgip_no());
			System.out.println(cgiPicVO.getCgip_cgino());
			for (byte i : cgiPicVO.getCgip_pic())
				System.out.print(i + "\t");
			System.out.println("\n");
		}
		System.out.println("====================");
		
		// 查詢特定露營區介紹(cgi_no)的圖片
		List<CGIPicVO> list2 = dao.getCGIPicsByCgino("I000000001");
		for (CGIPicVO cgiPicVO : list2) {
			System.out.println(cgiPicVO.getCgip_no());
			System.out.println(cgiPicVO.getCgip_cgino());
			for (byte i : cgiPicVO.getCgip_pic())
				System.out.print(i + "\t");
			System.out.println("\n");
		}
	}
}