package com.myfav.model;

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

public class MyfavDAO implements MyfavDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, myfavVO.getMyfav_memno());
			pstmt.setString(2, myfavVO.getMyfav_vdno());
			pstmt.executeUpdate();

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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, myfav_memno);
			pstmt.setString(2, myfav_vdno);
			pstmt.executeUpdate();

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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				myfavVO = new MyfavVO();
				myfavVO.setMyfav_memno(rs.getString(1));;
				myfavVO.setMyfav_vdno(rs.getString(2));
				list.add(myfavVO);
			}

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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_MULTI_BYMEMNO_STMT);

			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				myfavVO = new MyfavVO();
				myfavVO.setMyfav_memno(rs.getString(1));;
				myfavVO.setMyfav_vdno(rs.getString(2));
				list.add(myfavVO);
			}

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
}