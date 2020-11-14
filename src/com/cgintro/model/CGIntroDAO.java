package com.cgintro.model;

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

public class CGIntroDAO implements CGIntroDAO_interface {
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, cgIntroVO.getCgi_vdno());
			pstmt.setString(2, cgIntroVO.getCgi_content());
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
	public void updateContent(CGIntroVO cgIntroVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_CONTENT_STMT);
			
			pstmt.setString(1, cgIntroVO.getCgi_content());
			pstmt.setString(2, cgIntroVO.getCgi_no());
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
	public void updateStat(CGIntroVO cgIntroVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, cgIntroVO.getCgi_stat());
			pstmt.setString(2, cgIntroVO.getCgi_no());
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
	
//	刪除前須先確認CGI_PIC的項目
	@Override
	public void delete(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, cgi_no);
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
	public CGIntroVO findByPrimaryKey(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIntroVO cgIntroVO = null;
		
		try {
			conn = ds.getConnection();
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
			conn = ds.getConnection();
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
			conn = ds.getConnection();
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
			conn = ds.getConnection();
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
}