package com.vendor.model;

import java.sql.*;
import java.util.*;

import com.vendor.model.VendorJDBCDAO;
import com.vendor.model.VendorVO;

public class VendorJDBCDAO implements VendorDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT = 
			"INSERT INTO vendor (vd_no, vd_email, vd_pwd, vd_name, vd_id, vd_birth, vd_mobile, vd_cgname, vd_cgtel, vd_cgaddr, vd_taxid, vd_acc, vd_stat, vd_cgstat, vd_lat, vd_lon, vd_brc, vd_map) VALUES('V'|| LPAD(SEQ_VDNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT vd_no, vd_email, vd_pwd, vd_name, vd_id, to_char(vd_birth, 'YYYY-MM-DD') vd_birth, vd_mobile, vd_cgname, vd_cgtel, vd_cgaddr, vd_taxid, vd_acc, to_char(vd_regdate, 'YYYY-MM-DD') vd_regdate, vd_stat, vd_cgstat, vd_lat, vd_lon, vd_brc, vd_map FROM vendor ORDER BY vd_no";
	private static final String GET_ONE_STMT = 
			"SELECT vd_no, vd_email, vd_pwd, vd_name, vd_id, to_char(vd_birth, 'YYYY-MM-DD') vd_birth, vd_mobile, vd_cgname, vd_cgtel, vd_cgaddr, vd_taxid, vd_acc, to_char(vd_regdate, 'YYYY-MM-DD') vd_regdate, vd_stat, vd_cgstat, vd_lat, vd_lon, vd_brc, vd_map FROM vendor WHERE vd_no = ?";
	private static final String GET_ALL_STMTNOPIC = 
			"SELECT vd_no, vd_email, vd_pwd, vd_name, vd_id, to_char(vd_birth, 'YYYY-MM-DD') vd_birth, vd_mobile, vd_cgname, vd_cgtel, vd_cgaddr, vd_taxid, vd_acc, to_char(vd_regdate, 'YYYY-MM-DD') vd_regdate, vd_stat, vd_cgstat, vd_lat, vd_lon FROM vendor ORDER BY vd_no";
	private static final String GET_ONE_STMTNOPIC = 
			"SELECT vd_no, vd_email, vd_pwd, vd_name, vd_id, to_char(vd_birth, 'YYYY-MM-DD') vd_birth, vd_mobile, vd_cgname, vd_cgtel, vd_cgaddr, vd_taxid, vd_acc, to_char(vd_regdate, 'YYYY-MM-DD') vd_regdate, vd_stat, vd_cgstat, vd_lat, vd_lon FROM vendor WHERE vd_no = ?";
	private static final String DELETE = 
			"DELETE FROM vendor WHERE vd_no = ?";
	private static final String UPDATE = 
			"UPDATE vendor SET vd_email =?, vd_pwd =?, vd_name =?, vd_id =?, vd_birth =?, vd_mobile =?, vd_cgname =?, vd_cgtel =?, vd_cgaddr =?, vd_taxid =?, vd_acc =?, vd_stat =?, vd_cgstat =?, vd_lat =?, vd_lon =?, vd_brc=? WHERE vd_no = ?";
	private static final String PSWUPDATE = "UPDATE vendor SET vd_pwd = ? WHERE vd_no = ?";	
	private static final String UPDATENOPIC = 
			"UPDATE vendor SET vd_email =?, vd_pwd =?, vd_name =?, vd_id =?, vd_birth =?, vd_mobile =?, vd_cgname =?, vd_cgtel =?, vd_cgaddr =?, vd_taxid =?, vd_acc =?, vd_stat =?, vd_cgstat =?, vd_lat =?, vd_lon =? WHERE vd_no = ?";
	private static final String CHECK_BY_EMAIL = "SELECT * FROM vendor WHERE vd_email = ?";
	private static final String GET_VDPIC = "SELECT vd_no, vd_brc FROM vendor WHERE vd_no = ?";
	
	@Override
	public void insert(VendorVO vendorVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, vendorVO.getVd_email());
			pstmt.setString(2, vendorVO.getVd_pwd());
			pstmt.setString(3, vendorVO.getVd_name());
			pstmt.setString(4, vendorVO.getVd_id());
			pstmt.setDate(5, vendorVO.getVd_birth());
			pstmt.setString(6, vendorVO.getVd_mobile());
			pstmt.setString(7, vendorVO.getVd_cgname());
			pstmt.setString(8, vendorVO.getVd_cgtel());
			pstmt.setString(9, vendorVO.getVd_cgaddr());
			pstmt.setString(10, vendorVO.getVd_taxid());
			pstmt.setString(11, vendorVO.getVd_acc());
			pstmt.setInt(12, vendorVO.getVd_stat());
			pstmt.setInt(13, vendorVO.getVd_cgstat());
			pstmt.setDouble(14, vendorVO.getVd_lat());
			pstmt.setDouble(15, vendorVO.getVd_lon());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch(SQLException se) {
			throw new RuntimeException("A database error occured"
					+ se.getMessage());
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void update(VendorVO vendorVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, vendorVO.getVd_email());
			pstmt.setString(2, vendorVO.getVd_pwd());
			pstmt.setString(3, vendorVO.getVd_name());
			pstmt.setString(4, vendorVO.getVd_id());
			pstmt.setDate(5, vendorVO.getVd_birth());
			pstmt.setString(6, vendorVO.getVd_mobile());
			pstmt.setString(7, vendorVO.getVd_cgname());
			pstmt.setString(8, vendorVO.getVd_cgtel());
			pstmt.setString(9, vendorVO.getVd_cgaddr());
			pstmt.setString(10, vendorVO.getVd_taxid());
			pstmt.setString(11, vendorVO.getVd_acc());
			pstmt.setInt(12, vendorVO.getVd_stat());
			pstmt.setInt(13, vendorVO.getVd_cgstat());
			pstmt.setDouble(14, vendorVO.getVd_lat());
			pstmt.setDouble(15, vendorVO.getVd_lon());
			pstmt.setString(16, vendorVO.getVd_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se){
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void pswupdate(VendorVO vendorVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(PSWUPDATE);
			
			pstmt.setString(1, vendorVO.getVd_pwd());
			pstmt.setString(2, vendorVO.getVd_no());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se){
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void updatenopic(VendorVO vendorVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATENOPIC);
			
			pstmt.setString(1, vendorVO.getVd_email());
			pstmt.setString(2, vendorVO.getVd_pwd());
			pstmt.setString(3, vendorVO.getVd_name());
			pstmt.setString(4, vendorVO.getVd_id());
			pstmt.setDate(5, vendorVO.getVd_birth());
			pstmt.setString(6, vendorVO.getVd_mobile());
			pstmt.setString(7, vendorVO.getVd_cgname());
			pstmt.setString(8, vendorVO.getVd_cgtel());
			pstmt.setString(9, vendorVO.getVd_cgaddr());
			pstmt.setString(10, vendorVO.getVd_taxid());
			pstmt.setString(11, vendorVO.getVd_acc());
			pstmt.setInt(12, vendorVO.getVd_stat());
			pstmt.setInt(13, vendorVO.getVd_cgstat());
			pstmt.setDouble(14, vendorVO.getVd_lat());
			pstmt.setDouble(15, vendorVO.getVd_lon());
			pstmt.setBytes(16, vendorVO.getVd_brc());
			
			pstmt.executeUpdate();
			
		}catch (SQLException se){
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(String vd_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, vd_no);
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public VendorVO findByPrimaryKey(String vd_no) {
		
		VendorVO vendorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, vd_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVd_no(rs.getString("vd_no"));
				vendorVO.setVd_email(rs.getString("vd_email"));
				vendorVO.setVd_pwd(rs.getString("vd_pwd"));
				vendorVO.setVd_name(rs.getString("vd_name"));
				vendorVO.setVd_id(rs.getString("vd_id"));
				vendorVO.setVd_birth(rs.getDate("vd_birth"));
				vendorVO.setVd_mobile(rs.getString("vd_mobile"));
				vendorVO.setVd_cgname(rs.getString("vd_cgname"));
				vendorVO.setVd_cgtel(rs.getString("vd_cgtel"));
				vendorVO.setVd_cgaddr(rs.getString("vd_cgaddr"));
				vendorVO.setVd_taxid(rs.getString("vd_taxid"));
				vendorVO.setVd_acc(rs.getString("vd_acc"));
				vendorVO.setVd_regdate(rs.getDate("vd_regdate"));
				vendorVO.setVd_stat(rs.getInt("vd_stat"));
				vendorVO.setVd_cgstat(rs.getInt("vd_cgstat"));
				vendorVO.setVd_lat(rs.getDouble("vd_lat"));
				vendorVO.setVd_lon(rs.getDouble("vd_lon"));
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
		return vendorVO;
	}
	
	@Override
	public List<VendorVO> getAll() {
		
		List<VendorVO> list = new ArrayList<VendorVO>();
		VendorVO vendorVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVd_no(rs.getString("vd_no"));
				vendorVO.setVd_email(rs.getString("vd_email"));
				vendorVO.setVd_pwd(rs.getString("vd_pwd"));
				vendorVO.setVd_name(rs.getString("vd_name"));
				vendorVO.setVd_id(rs.getString("vd_id"));
				vendorVO.setVd_birth(rs.getDate("vd_birth"));
				vendorVO.setVd_mobile(rs.getString("vd_mobile"));
				vendorVO.setVd_cgname(rs.getString("vd_cgname"));
				vendorVO.setVd_cgtel(rs.getString("vd_cgtel"));
				vendorVO.setVd_cgaddr(rs.getString("vd_cgaddr"));
				vendorVO.setVd_taxid(rs.getString("vd_taxid"));
				vendorVO.setVd_acc(rs.getString("vd_acc"));
				vendorVO.setVd_regdate(rs.getDate("vd_regdate"));
				vendorVO.setVd_stat(rs.getInt("vd_stat"));
				vendorVO.setVd_cgstat(rs.getInt("vd_cgstat"));
				vendorVO.setVd_lat(rs.getDouble("vd_lat"));
				vendorVO.setVd_lon(rs.getDouble("vd_lon"));
				list.add(vendorVO);
				
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
		return list;
	}

	@Override
	public VendorVO checkByEmail(String vd_email) {
		VendorVO vendorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
			pstmt = con.prepareStatement(CHECK_BY_EMAIL);
			pstmt.setString(1, vd_email);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				vendorVO = new VendorVO();
				vendorVO.setVd_no(rs.getString("vd_no"));
				vendorVO.setVd_email(rs.getString("vd_email"));
				vendorVO.setVd_pwd(rs.getString("vd_pwd"));
				vendorVO.setVd_name(rs.getString("vd_name"));
				vendorVO.setVd_id(rs.getString("vd_id"));
				vendorVO.setVd_birth(rs.getDate("vd_birth"));
				vendorVO.setVd_mobile(rs.getString("vd_mobile"));
				vendorVO.setVd_cgname(rs.getString("vd_cgname"));
				vendorVO.setVd_cgtel(rs.getString("vd_cgtel"));
				vendorVO.setVd_cgaddr(rs.getString("vd_cgaddr"));
				vendorVO.setVd_taxid(rs.getString("vd_taxid"));
				vendorVO.setVd_acc(rs.getString("vd_acc"));
				vendorVO.setVd_regdate(rs.getDate("vd_regdate"));
				vendorVO.setVd_stat(rs.getInt("vd_stat"));
				vendorVO.setVd_cgstat(rs.getInt("vd_cgstat"));
				vendorVO.setVd_lat(rs.getDouble("vd_lat"));
				vendorVO.setVd_lon(rs.getDouble("vd_lon"));			
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return vendorVO;
	}
	
	@Override
	public VendorVO findVdpicByPrimaryKey(String vd_no) {
		
		VendorVO vendorVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_VDPIC);
				pstmt.setString(1, vd_no);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					vendorVO.setVd_no(rs.getString("vd_no"));
					vendorVO.setVd_brc(rs.getBytes("vd_brc"));
				}
				
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
			return vendorVO;
	}
	
	//--------------------------------------------

	public static void main(String[] args) {

		VendorJDBCDAO dao = new VendorJDBCDAO();

		// �d��
		VendorVO vendorVO3 = dao.findByPrimaryKey("V000000002");
		System.out.print(vendorVO3.getVd_no() + ",");
		System.out.print(vendorVO3.getVd_email() + ",");
		System.out.print(vendorVO3.getVd_pwd() + ",");
		System.out.print(vendorVO3.getVd_name() + ",");
		System.out.print(vendorVO3.getVd_id() + ",");
		System.out.print(vendorVO3.getVd_birth() + ",");
		System.out.print(vendorVO3.getVd_mobile() + ",");
		System.out.print(vendorVO3.getVd_cgname() + ",");
		System.out.print(vendorVO3.getVd_cgtel() + ",");
		System.out.print(vendorVO3.getVd_cgaddr() + ",");
		System.out.print(vendorVO3.getVd_taxid() + ",");
		System.out.print(vendorVO3.getVd_acc() + ",");
		System.out.print(vendorVO3.getVd_regdate() + ",");
		System.out.print(vendorVO3.getVd_stat() + ",");
		System.out.print(vendorVO3.getVd_cgstat() + ",");
		System.out.print(vendorVO3.getVd_lat());
		System.out.println(vendorVO3.getVd_lon() + ",");
		System.out.println("---------------------");

		// �d��
		List<VendorVO> list = dao.getAll();
		for (VendorVO aVendor : list) {
			System.out.print(aVendor.getVd_no() + ",");
			System.out.print(aVendor.getVd_email() + ",");
			System.out.print(aVendor.getVd_pwd() + ",");
			System.out.print(aVendor.getVd_name() + ",");
			System.out.print(aVendor.getVd_id() + ",");
			System.out.print(aVendor.getVd_birth() + ",");
			System.out.print(aVendor.getVd_mobile() + ",");
			System.out.print(aVendor.getVd_cgname() + ",");
			System.out.print(aVendor.getVd_cgtel() + ",");
			System.out.print(aVendor.getVd_cgaddr() + ",");
			System.out.print(aVendor.getVd_taxid() + ",");
			System.out.print(aVendor.getVd_acc() + ",");
			System.out.print(aVendor.getVd_regdate() + ",");
			System.out.print(aVendor.getVd_stat() + ",");
			System.out.print(aVendor.getVd_cgstat() + ",");
			System.out.print(aVendor.getVd_lat());
			System.out.println(aVendor.getVd_lon() + ",");
			System.out.println("---------------------");
		}
		
	}

	@Override
	public void fakeInformationinsert(VendorVO VendorVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<VendorVO> getAllnopic() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VendorVO findByPrimaryKeynopic(String vd_no) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void fakeInformationinsert(VendorVO vendorVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//			
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//			
//			pstmt.setString(1, vendorVO.getVd_email());
//			pstmt.setString(2, vendorVO.getVd_pwd());
//			pstmt.setString(3, vendorVO.getVd_name());
//			pstmt.setString(4, vendorVO.getVd_id());
//			pstmt.setDate(5, vendorVO.getVd_birth());
//			pstmt.setString(6, vendorVO.getVd_mobile());
//			pstmt.setString(7, vendorVO.getVd_cgname());
//			pstmt.setString(8, vendorVO.getVd_cgtel());
//			pstmt.setString(9, vendorVO.getVd_cgaddr());
//			pstmt.setString(10, vendorVO.getVd_taxid());
//			pstmt.setString(11, vendorVO.getVd_acc());
//			pstmt.setInt(12, vendorVO.getVd_stat());
//			pstmt.setInt(13, vendorVO.getVd_cgstat());
//			pstmt.setDouble(14, vendorVO.getVd_lat());
//			pstmt.setDouble(15, vendorVO.getVd_lon());
//			
//			pstmt.executeUpdate();
//			
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//		} catch(SQLException se) {
//			throw new RuntimeException("A database error occured"
//					+ se.getMessage());
//		}finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				}catch(SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				}catch(Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		
//	}

}
