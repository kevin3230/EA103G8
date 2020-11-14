package com.members.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MembersJDBCDAO implements MembersDAO_interface {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO MEMBERS(MEM_NO, MEM_EMAIL, MEM_PWD, MEM_NAME, "
			+ "MEM_ALIAS, MEM_GENDER, MEM_BIRTH, MEM_MOBILE, MEM_TEL, MEM_ADDR, MEM_TYPE, MEM_STAT)"
			+ "VALUES('M' || LPAD(SEQ_MEMNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_STMT = "UPDATE MEMBERS SET MEM_EMAIL = ?, MEM_PWD = ?, "
			+ "MEM_NAME = ?, MEM_ALIAS = ?, MEM_GENDER = ?, MEM_BIRTH = ?, MEM_MOBILE = ?, MEM_TEL = ?, "
			+ "MEM_ADDR= ?, MEM_TYPE = ?, MEM_STAT = ? WHERE MEM_NO = ?";
	private static final String PSWUPDATE_STMT = "UPDATE MEMBERS SET MEM_PWD = ? WHERE MEM_NO = ?";
	private static final String DELETE_STMT = "DELETE FROM MEMBERS WHERE MEM_NO = ?";
	private static final String FIND_BY_PK = "SELECT * FROM MEMBERS WHERE MEM_NO = ?";
	private static final String GET_ALL = "SELECT * FROM MEMBERS";
	private static final String CHECK_BY_EMAIL = "SELECT * FROM MEMBERS WHERE MEM_EMAIL = ?";

	@Override
	public void add(MembersVO members) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			
			String[] cols = {"MEM_NO"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			
			pstmt.setString(1, members.getMem_email());
			pstmt.setString(2, members.getMem_pwd());
			pstmt.setString(3, members.getMem_name());
			pstmt.setString(4, members.getMem_alias());
			pstmt.setString(5, members.getMem_gender());
			pstmt.setDate(6, members.getMem_birth());
			pstmt.setString(7, members.getMem_mobile());
			pstmt.setString(8, members.getMem_tel());
			pstmt.setString(9, members.getMem_addr());
			pstmt.setInt(10, members.getMem_type());
			pstmt.setInt(11, members.getMem_stat());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(MembersVO members) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, members.getMem_email());
			pstmt.setString(2, members.getMem_pwd());
			pstmt.setString(3, members.getMem_name());
			pstmt.setString(4, members.getMem_alias());
			pstmt.setString(5, members.getMem_gender());
			pstmt.setDate(6, members.getMem_birth());
			pstmt.setString(7, members.getMem_mobile());
			pstmt.setString(8, members.getMem_tel());
			pstmt.setString(9, members.getMem_addr());
			pstmt.setInt(10, members.getMem_type());
			pstmt.setInt(11, members.getMem_stat());
			pstmt.setString(12, members.getMem_no());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void pswupdate(MembersVO members) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(PSWUPDATE_STMT);

			pstmt.setString(1, members.getMem_pwd());
			pstmt.setString(2, members.getMem_no());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void delete(String mem_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setString(1, mem_no);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public MembersVO findByPK(String mem_no) {
		MembersVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_PK);
			pstmt.setString(1, mem_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MembersVO();
				mem.setMem_no(rs.getString("mem_no"));
				mem.setMem_email(rs.getString("mem_email"));
				mem.setMem_pwd(rs.getString("mem_pwd"));
				mem.setMem_name(rs.getString("mem_name"));
				mem.setMem_alias(rs.getString("mem_alias"));
				mem.setMem_gender(rs.getString("mem_gender"));
				mem.setMem_birth(rs.getDate("mem_birth"));
				mem.setMem_mobile(rs.getString("mem_mobile"));
				mem.setMem_tel(rs.getString("mem_tel"));
				mem.setMem_addr(rs.getString("mem_addr"));
				mem.setMem_type(rs.getInt("mem_type"));
				mem.setMem_regdate(rs.getDate("mem_regdate"));
				mem.setMem_stat(rs.getInt("mem_stat"));				
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return mem;
	}

	@Override
	public List<MembersVO> getAll() {
		List<MembersVO> memList = new ArrayList<>();
		MembersVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mem = new MembersVO();
				mem.setMem_no(rs.getString("mem_no"));
				mem.setMem_email(rs.getString("mem_email"));
				mem.setMem_pwd(rs.getString("mem_pwd"));
				mem.setMem_name(rs.getString("mem_name"));
				mem.setMem_alias(rs.getString("mem_alias"));
				mem.setMem_gender(rs.getString("mem_gender"));
				mem.setMem_birth(rs.getDate("mem_birth"));
				mem.setMem_mobile(rs.getString("mem_mobile"));
				mem.setMem_tel(rs.getString("mem_tel"));
				mem.setMem_addr(rs.getString("mem_addr"));
				mem.setMem_type(rs.getInt("mem_type"));
				mem.setMem_regdate(rs.getDate("mem_regdate"));
				mem.setMem_stat(rs.getInt("mem_stat"));		
				memList.add(mem);
			}
			
		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return memList;
	}

	@Override
	public MembersVO checkByEmail(String mem_email) {
		MembersVO mem = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(CHECK_BY_EMAIL);
			pstmt.setString(1, mem_email);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				mem = new MembersVO();
				mem.setMem_no(rs.getString("mem_no"));
				mem.setMem_email(rs.getString("mem_email"));
				mem.setMem_pwd(rs.getString("mem_pwd"));
				mem.setMem_name(rs.getString("mem_name"));
				mem.setMem_alias(rs.getString("mem_alias"));
				mem.setMem_gender(rs.getString("mem_gender"));
				mem.setMem_birth(rs.getDate("mem_birth"));
				mem.setMem_mobile(rs.getString("mem_mobile"));
				mem.setMem_tel(rs.getString("mem_tel"));
				mem.setMem_addr(rs.getString("mem_addr"));
				mem.setMem_type(rs.getInt("mem_type"));
				mem.setMem_regdate(rs.getDate("mem_regdate"));
				mem.setMem_stat(rs.getInt("mem_stat"));				
			}

		} catch (ClassNotFoundException ce) {
			throw new RuntimeException("Couldn't load database driver. " + ce.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return mem;
	}

}
