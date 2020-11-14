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

public class CGIPicDAO implements CGIPicDAO_interface {
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, cgiPicVO.getCgip_cgino());
			pstmt.setBytes(2, cgiPicVO.getCgip_pic());
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
	public void update(CGIPicVO cgiPicVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setBytes(1, cgiPicVO.getCgip_pic());
			pstmt.setString(2, cgiPicVO.getCgip_no());
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
	public void delete(String cgip_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, cgip_no);
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
	public CGIPicVO findByPrimaryKey(String cgip_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIPicVO cgiPicVO = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cgip_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cgiPicVO = new CGIPicVO();
				cgiPicVO.setCgip_no(rs.getString(1));
				cgiPicVO.setCgip_cgino(rs.getString(2));
				cgiPicVO.setCgip_pic(rs.getBytes(3));
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
					
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cgiPicVO = new CGIPicVO();
				cgiPicVO.setCgip_no(rs.getString(1));
				cgiPicVO.setCgip_cgino(rs.getString(2));
				cgiPicVO.setCgip_pic(rs.getBytes(3));
				list.add(cgiPicVO);
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
	public List<CGIPicVO> getCGIPicsByCgino(String cgi_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CGIPicVO cgiPicVO = null;
		List<CGIPicVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
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