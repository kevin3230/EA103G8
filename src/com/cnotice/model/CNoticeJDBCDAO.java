package com.cnotice.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CNoticeJDBCDAO implements CNoticeDAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT = "INSERT INTO CNOTICE(CN_NO, CN_MEMNO, CN_OMNO, CN_CONTENT, CN_TYPE, CN_STAT)"
			+ "VALUES('CN' || LPAD(SEQ_CNNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE CNOTICE SET CN_MEMNO = ?, CN_OMNO = ?, CN_OMNO = ?, CN_TYPE = ?, CN_STAT = ? WHERE CN_NO = ? ";
	private static final String UPDATESTAT_STMT = "UPDATE CNOTICE SET CN_STAT = ? WHERE CN_OMNO = ? ";
	private static final String FIND_BY_PK = "SELECT * FROM CNOTICE WHERE CN_NO = ?";
	private static final String GET_ALL = "SELECT * FROM CNOTICE";
	private static final String GET_ALLNEW = "SELECT * FROM CNOTICE WHERE CN_STAT = 1";

	@Override
	public void add(CNoticeVO cnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			String[] cols = {"CN_NO"};
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, cnotice.getCn_memno());
			pstmt.setString(2, cnotice.getCn_omno());
			pstmt.setString(3, cnotice.getCn_content());
			pstmt.setString(4, cnotice.getCn_type());
			pstmt.setInt(5, cnotice.getCn_stat());
			
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
	public void update(CNoticeVO cnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, cnotice.getCn_memno());
			pstmt.setString(2, cnotice.getCn_omno());
			pstmt.setString(3, cnotice.getCn_content());
			pstmt.setString(4, cnotice.getCn_type());
			pstmt.setInt(5, cnotice.getCn_stat());
			pstmt.setString(6, cnotice.getCn_no());
			
			pstmt.executeUpdate();
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());	
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
	public void updateStat(CNoticeVO cnotice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATESTAT_STMT);

			pstmt.setInt(1, cnotice.getCn_stat());
			pstmt.setString(2, cnotice.getCn_omno());
			
			pstmt.executeUpdate();
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());	
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
	public CNoticeVO findByPK(String cn_no) {
		CNoticeVO cnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, cn_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cnotice = new CNoticeVO();
				cnotice.setCn_no(rs.getString("cn_no"));
				cnotice.setCn_memno(rs.getString("cn_memno"));
				cnotice.setCn_omno(rs.getString("cn_omno"));
				cnotice.setCn_content(rs.getString("cn_content"));
				cnotice.setCn_time(rs.getDate("cn_time"));
				cnotice.setCn_type(rs.getString("cn_type"));
				cnotice.setCn_stat(rs.getInt("cn_stat"));
			}
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());	
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
		return cnotice;
	}

	@Override
	public List<CNoticeVO> getAll() {
		List<CNoticeVO> cnList = new ArrayList<>();
		CNoticeVO cnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cnotice = new CNoticeVO();
				cnotice.setCn_no(rs.getString("cn_no"));
				cnotice.setCn_memno(rs.getString("cn_memno"));
				cnotice.setCn_omno(rs.getString("cn_omno"));
				cnotice.setCn_content(rs.getString("cn_content"));
				cnotice.setCn_time(rs.getDate("cn_time"));
				cnotice.setCn_type(rs.getString("cn_type"));
				cnotice.setCn_stat(rs.getInt("cn_stat"));
				cnList.add(cnotice);
			}
			
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());	
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
		return cnList;
	}

	@Override
	public List<CNoticeVO> getAllNew() {
		List<CNoticeVO> cnList = new ArrayList<>();
		CNoticeVO cnotice = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALLNEW);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cnotice = new CNoticeVO();
				cnotice.setCn_no(rs.getString("cn_no"));
				cnotice.setCn_memno(rs.getString("cn_memno"));
				cnotice.setCn_omno(rs.getString("cn_omno"));
				cnotice.setCn_content(rs.getString("cn_content"));
				cnotice.setCn_time(rs.getDate("cn_time"));
				cnotice.setCn_type(rs.getString("cn_type"));
				cnotice.setCn_stat(rs.getInt("cn_stat"));
				cnList.add(cnotice);
			}
			
		
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());	
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
		return cnList;
	}
	
}