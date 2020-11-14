package com.myfav.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyfavJDBCDAO implements MyfavDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "123456";
	
	private static final String INSERT_STMT =
			"INSERT INTO MYFAV(MYFAV_MEMNO, MYFAV_VDNO)"
			+ " VALUES (?, ?)";
	
	private static final String DELETE_STMT =
			"DELETE FROM MYFAV WHERE MYFAV_MEMNO = ? AND MYFAV_VDNO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT MYFAV_MEMNO, MYFAV_VDNO"
			+ " FROM MYFAV ORDER BY MYFAV_MEMNO, MYFAV_VDNO";
	
	private static final String GET_MULTI_BYMEMNO_STMT =
			"SELECT MYFAV_MEMNO, MYFAV_VDNO"
			+ " FROM MYFAV WHERE MYFAV_MEMNO = ? ORDER BY MYFAV_VDNO";

	@Override
	public void insert(MyfavVO myfavVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, myfavVO.getMyfav_memno());
			pstmt.setString(2, myfavVO.getMyfav_vdno());
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
	public void delete(String myfav_memno, String myfav_vdno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, myfav_memno);
			pstmt.setString(2, myfav_vdno);
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
	public List<MyfavVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MyfavVO myfavVO = null;
		List<MyfavVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				myfavVO = new MyfavVO();
				myfavVO.setMyfav_memno(rs.getString(1));;
				myfavVO.setMyfav_vdno(rs.getString(2));
				list.add(myfavVO);
			}
			
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
		return list;
	}

	@Override
	public List<MyfavVO> getMyfavsByMemno(String mem_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MyfavVO myfavVO = null;
		List<MyfavVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_MULTI_BYMEMNO_STMT);

			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				myfavVO = new MyfavVO();
				myfavVO.setMyfav_memno(rs.getString(1));;
				myfavVO.setMyfav_vdno(rs.getString(2));
				list.add(myfavVO);
			}
			
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
		return list;
	}
	
	public static void main(String[] args) {
		MyfavJDBCDAO dao = new MyfavJDBCDAO();
		
//		// 新增
//		MyfavVO myfavVO1 = new MyfavVO();
//		myfavVO1.setMyfav_memno("M000000001");
//		myfavVO1.setMyfav_vdno("V000000002");
//		dao.insert(myfavVO1);
		
		// 刪除
		dao.delete("M000000001", "V000000001");
		
		// 查詢全部
		List<MyfavVO> list = dao.getAll();
		for (MyfavVO myfavVO : list) {
			System.out.println(myfavVO.getMyfav_memno());
			System.out.println(myfavVO.getMyfav_vdno());
			System.out.println();
		}
		System.out.println("====================");
		
		// 查詢特定營位(camp_no)的剩餘數量
		List<MyfavVO> list2 = dao.getMyfavsByMemno("M000000001");
		for (MyfavVO myfavVO : list2) {
			System.out.println(myfavVO.getMyfav_memno());
			System.out.println(myfavVO.getMyfav_vdno());
			System.out.println();
		}
	}
}