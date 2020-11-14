package com.camptype.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CampTypeJDBCDAO implements CampTypeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO CAMP_TYPE(CT_NO, CT_NAME)"
			+ " VALUES ('CT' || LPAD(SEQ_CTNO.NEXTVAL, 8, '0'), ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE CAMP_TYPE SET CT_NAME = ? WHERE CT_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM CAMP_TYPE WHERE CT_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT CT_NO, CT_NAME FROM CAMP_TYPE WHERE CT_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT CT_NO, CT_NAME FROM CAMP_TYPE ORDER BY CT_NO";
	
	@Override
	public void insert(CampTypeVO campTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, campTypeVO.getCt_name());
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
	public void update(CampTypeVO campTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, campTypeVO.getCt_name());
			pstmt.setString(2, campTypeVO.getCt_no());
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
	public void delete(String ct_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, ct_no);
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
	public CampTypeVO findByPrimaryKey(String ct_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampTypeVO campTypeVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ct_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campTypeVO = new CampTypeVO();
				campTypeVO.setCt_no(rs.getString(1));;
				campTypeVO.setCt_name(rs.getString(2));
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
		return campTypeVO;
	}

	@Override
	public List<CampTypeVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CampTypeVO campTypeVO = null;
		List<CampTypeVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				campTypeVO = new CampTypeVO();
				campTypeVO.setCt_no(rs.getString(1));;
				campTypeVO.setCt_name(rs.getString(2));
				list.add(campTypeVO);
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
		CampTypeJDBCDAO dao = new CampTypeJDBCDAO();
		
		// 新增
		CampTypeVO campTypeVO1 = new CampTypeVO();
		campTypeVO1.setCt_name("露天型");
		dao.insert(campTypeVO1);
		
		// 修改
		CampTypeVO campTypeVO2 = new CampTypeVO();
		campTypeVO2.setCt_no("CT00000021");
		campTypeVO2.setCt_name("山洞型");
		dao.update(campTypeVO2);
		
		// 刪除
		dao.delete("CT00000021");

		// 查詢特定營位類型(ct_no)
		CampTypeVO campTypeVO3 = dao.findByPrimaryKey("CT00000001");
		System.out.println(campTypeVO3.getCt_no());
		System.out.println(campTypeVO3.getCt_name());
		System.out.println("====================");
		
		// 查詢全部
		List<CampTypeVO> list = dao.getAll();
		for (CampTypeVO campTypeVO : list) {
			System.out.println(campTypeVO.getCt_no());
			System.out.println(campTypeVO.getCt_name());
			System.out.println();
		}
		System.out.println("====================");
	}
}