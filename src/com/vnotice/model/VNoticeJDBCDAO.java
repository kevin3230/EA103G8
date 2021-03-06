package com.vnotice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VNoticeJDBCDAO implements VNoticeDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT = "INSERT INTO VNOTICE(VN_NO, VN_VDNO, VN_OMNO, VN_CONTENT, VN_TYPE, VN_STAT)"
			+ "VALUES('VN' || LPAD(SEQ_VNNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE VNOTICE SET VN_VDNO = ?, VN_OMNO = ?, VN_CONTENT = ?, VN_TYPE = ?, VN_STAT = ? WHERE VN_NO = ? ";
	private static final String UPDATESTAT_STMT = "UPDATE VNOTICE SET VN_STAT = ? WHERE VN_OMNO = ? ";
	private static final String FIND_BY_PK = "SELECT * FROM VNOTICE WHERE VN_NO = ?";
	private static final String GET_ALL = "SELECT * FROM VNOTICE";
	private static final String GET_ALLNEW = "SELECT * FROM VNOTICE WHERE VN_STAT = 1";
	
	@Override
	public void add(VNoticeVO vnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String[] cols = {"VN_NO"};
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, vnotice.getVn_vdno());
			pstmt.setString(2, vnotice.getVn_omno());
			pstmt.setString(3, vnotice.getVn_content());
			pstmt.setString(4, vnotice.getVn_type());
			pstmt.setInt(5, vnotice.getVn_stat());
			
			pstmt.executeUpdate();
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch(SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
	public void update(VNoticeVO vnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, vnotice.getVn_vdno());
			pstmt.setString(2, vnotice.getVn_omno());
			pstmt.setString(3, vnotice.getVn_content());
			pstmt.setString(4, vnotice.getVn_type());
			pstmt.setInt(5, vnotice.getVn_stat());
			pstmt.setString(6, vnotice.getVn_no());
			
			pstmt.executeUpdate();
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
	public void updateStat(VNoticeVO vnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATESTAT_STMT);

			pstmt.setInt(1, vnotice.getVn_stat());
			pstmt.setString(2, vnotice.getVn_omno());
			
			pstmt.executeUpdate();
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
	public VNoticeVO findByPK(String vn_no) {
		VNoticeVO vnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, vn_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vnotice = new VNoticeVO();
				vnotice.setVn_no(rs.getString("vn_no"));
				vnotice.setVn_vdno(rs.getString("vn_vdno"));
				vnotice.setVn_vdno(rs.getString("vn_omno"));
				vnotice.setVn_content(rs.getString("vn_content"));
				vnotice.setVn_time(rs.getDate("vn_time"));
				vnotice.setVn_type(rs.getString("vn_type"));
				vnotice.setVn_stat(rs.getInt("vn_stat"));
			}
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
		return vnotice;
	}

	@Override
	public List<VNoticeVO> getAll() {
		List<VNoticeVO> vnList = new ArrayList<>();
		VNoticeVO vnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vnotice = new VNoticeVO();
				vnotice.setVn_no(rs.getString("vn_no"));
				vnotice.setVn_vdno(rs.getString("vn_vdno"));
				vnotice.setVn_vdno(rs.getString("vn_omno"));
				vnotice.setVn_content(rs.getString("vn_content"));
				vnotice.setVn_time(rs.getDate("vn_time"));
				vnotice.setVn_type(rs.getString("vn_type"));
				vnotice.setVn_stat(rs.getInt("vn_stat"));
				vnList.add(vnotice);
			}
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
		return vnList;
	}

	@Override
	public List<VNoticeVO> getAllNew() {
		List<VNoticeVO> vnList = new ArrayList<>();
		VNoticeVO vnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALLNEW);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vnotice = new VNoticeVO();
				vnotice.setVn_no(rs.getString("vn_no"));
				vnotice.setVn_vdno(rs.getString("vn_vdno"));
				vnotice.setVn_omno(rs.getString("vn_omno"));
				vnotice.setVn_content(rs.getString("vn_content"));
				vnotice.setVn_time(rs.getDate("vn_time"));
				vnotice.setVn_type(rs.getString("vn_type"));
				vnotice.setVn_stat(rs.getInt("vn_stat"));
				vnList.add(vnotice);
			}
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
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
		return vnList;
	}
	
	public static void main(String[] args) {
		VNoticeJDBCDAO dao = new VNoticeJDBCDAO();
		VNoticeVO vnv = new VNoticeVO();
		
		vnv = dao.findByPK("VN00000001");
		System.out.print(vnv.getVn_no() + ",");
		System.out.print(vnv.getVn_vdno() + ",");
		System.out.print(vnv.getVn_content() + ",");
		System.out.print(vnv.getVn_time() + ",");
		System.out.print(vnv.getVn_stat());
		
	}
		
}

