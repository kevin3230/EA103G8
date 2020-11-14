package com.equipment.model;

import java.sql.*;
import java.util.*;

public class EquipmentJDBCDAO implements EquipmentDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO EQUIPMENT (EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC) VALUES ('E' || LPAD(SEQ_EQPTNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC FROM EQUIPMENT ORDER BY EQPT_NO";
	private static final String GET_ONE_STMT = "SELECT EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC FROM EQUIPMENT WHERE EQPT_NO = ?";
//	private static final String REAL_DELETE_STMT = "DELETE FROM EQUIPMENT WHERE EQPT_NO = ?";
	private static final String UPDATE_STMT = "UPDATE EQUIPMENT SET EQPT_VDNO=?, EQPT_NAME=?, EQPT_ETNO=?, EQPT_QTY=?, EQPT_PRICE=?, EQPT_STAT=?, EQPT_PIC=? WHERE EQPT_NO = ?";
	private static final String DELETE_STMT = "UPDATE EQUIPMENT SET EQPT_STAT=? WHERE EQPT_NO = ?"; // 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	private static final String UPDATE_STAT_STMT = "UPDATE EQUIPMENT SET EQPT_STAT=? WHERE EQPT_NO = ?";
	private static final String GET_MULTI_BYVDNO_STMT = "SELECT * FROM EQUIPMENT WHERE EQPT_VDNO = ? ORDER BY EQPT_NO";
	
	private static final String GET_ALLCT_BYVDNO = "SELECT EQPT_ETNO FROM EQUIPMENT WHERE EQPT_VDNO = ? AND EQPT_STAT >= 0 ORDER BY EQPT_NO"; 
	private static final String GET_MULTI_BYVDNOETNO_STMT = "SELECT * FROM EQUIPMENT WHERE EQPT_VDNO = ? AND EQPT_ETNO = ? AND EQPT_STAT >= 0 ORDER BY EQPT_NO";

	@Override
	public void insert(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, equipmentVO.getEqpt_vdno());
			pstmt.setString(2, equipmentVO.getEqpt_name());
			pstmt.setString(3,equipmentVO.getEqpt_etno());
			pstmt.setInt(4, equipmentVO.getEqpt_qty());
			pstmt.setInt(5, equipmentVO.getEqpt_price());
			pstmt.setInt(6, equipmentVO.getEqpt_stat());
			pstmt.setBytes(7, equipmentVO.getEqpt_pic());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(EquipmentVO equipmentVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, equipmentVO.getEqpt_vdno());
			pstmt.setString(2, equipmentVO.getEqpt_name());
			pstmt.setString(3, equipmentVO.getEqpt_etno());
			pstmt.setInt(4, equipmentVO.getEqpt_qty());
			pstmt.setInt(5, equipmentVO.getEqpt_price());
			pstmt.setInt(6, equipmentVO.getEqpt_stat());
			pstmt.setBytes(7, equipmentVO.getEqpt_pic());
			pstmt.setString(8, equipmentVO.getEqpt_no());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

//	@Override
//	public void delete(String eqpt_no) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(REAL_DELETE_STMT);
//
//			pstmt.setString(1, eqpt_no);
//			pstmt.executeUpdate();
//
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//		}
//	}
	
	// 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	@Override
	public void delete(String eqpt_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);

			pstmt.setInt(1, -1);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public EquipmentVO findByPrimaryKey(String eqpt_no) {

		EquipmentVO equipmentVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, eqpt_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_no(rs.getString("EQPT_NO"));
				equipmentVO.setEqpt_vdno(rs.getString("EQPT_VDNO"));
				equipmentVO.setEqpt_name(rs.getString("EQPT_NAME"));
				equipmentVO.setEqpt_etno(rs.getString("EQPT_ETNO"));
				equipmentVO.setEqpt_qty(rs.getInt("EQPT_QTY"));
				equipmentVO.setEqpt_price(rs.getInt("EQPT_PRICE"));
				equipmentVO.setEqpt_stat(rs.getInt("EQPT_STAT"));
				equipmentVO.setEqpt_pic(rs.getBytes("EQPT_PIC"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return equipmentVO;
	}

	@Override
	public List<EquipmentVO> getAll() {

		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO equipmentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_no(rs.getString("EQPT_NO"));
				equipmentVO.setEqpt_vdno(rs.getString("EQPT_VDNO"));
				equipmentVO.setEqpt_name(rs.getString("EQPT_NAME"));
				equipmentVO.setEqpt_etno(rs.getString("EQPT_ETNO"));
				equipmentVO.setEqpt_qty(rs.getInt("EQPT_QTY"));
				equipmentVO.setEqpt_price(rs.getInt("EQPT_PRICE"));
				equipmentVO.setEqpt_stat(rs.getInt("EQPT_STAT"));
				equipmentVO.setEqpt_pic(rs.getBytes("EQPT_PIC"));

				list.add(equipmentVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public void updateEqptStatTo2(String eqpt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 2);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void updateEqptStatTo1(String eqpt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<EquipmentVO> getEquipmentsByVdno(String vd_no) {
		
		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		EquipmentVO equipmentVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MULTI_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_no(rs.getString("EQPT_NO"));
				equipmentVO.setEqpt_vdno(rs.getString("EQPT_VDNO"));
				equipmentVO.setEqpt_name(rs.getString("EQPT_NAME"));
				equipmentVO.setEqpt_etno(rs.getString("EQPT_ETNO"));
				equipmentVO.setEqpt_qty(rs.getInt("EQPT_QTY"));
				equipmentVO.setEqpt_price(rs.getInt("EQPT_PRICE"));
				equipmentVO.setEqpt_stat(rs.getInt("EQPT_STAT"));
				equipmentVO.setEqpt_pic(rs.getBytes("EQPT_PIC"));

				list.add(equipmentVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void updatEqptQty(EquipmentVO equipmentVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> getAllEt_no(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<String> set = new LinkedHashSet<String>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALLCT_BYVDNO);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String eqpt_etno = rs.getString("EQUIPMENT_ETNO");
				set.add(eqpt_etno);;
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return set;
	}

	@Override
	public List<EquipmentVO> getEquipmentsByVdnoEtno(String vd_no, String et_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		EquipmentVO equipmentVO = null;
		List<EquipmentVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_MULTI_BYVDNOETNO_STMT);
			
			pstmt.setString(1, vd_no);
			pstmt.setString(2, et_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_no(rs.getString("EQPT_NO"));
				equipmentVO.setEqpt_vdno(rs.getString("EQPT_VDNO"));
				equipmentVO.setEqpt_name(rs.getString("EQPT_NAME"));
				equipmentVO.setEqpt_etno(rs.getString("EQPT_ETNO"));
				equipmentVO.setEqpt_qty(rs.getInt("EQPT_QTY"));
				equipmentVO.setEqpt_price(rs.getInt("EQPT_PRICE"));
				equipmentVO.setEqpt_stat(rs.getInt("EQPT_STAT"));
				equipmentVO.setEqpt_pic(rs.getBytes("EQPT_PIC"));

				list.add(equipmentVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}		
		return list;
	}

//	public static void main(String[] args) {
//		EquipmentJDBCDAO dao = new EquipmentJDBCDAO();
//
//		EquipmentVO equipmentVO1 = new EquipmentVO();
//		equipmentVO1.setEqpt_vdno("V000000001");
//		equipmentVO1.setEqpt_name("手電筒");
//		equipmentVO1.setEqpt_etno("ET00000004");
//		equipmentVO1.setEqpt_qty(20);
//		equipmentVO1.setEqpt_price(50);
//		equipmentVO1.setEqpt_stat(0);
////		equipmentVO1.setEqpt_pic();
//		dao.insert(equipmentVO1);
//		
//		EquipmentVO equipmentVO2 = new EquipmentVO();
//		equipmentVO2.setEqpt_no("E000000022");
//		equipmentVO2.setEqpt_vdno("V000000001");
//		equipmentVO2.setEqpt_name("頭燈");
//		equipmentVO2.setEqup_etno("ET00000004");
//		equipmentVO2.setEqpt_qty(20);
//		equipmentVO2.setEqpt_price(50);
//		equipmentVO2.setEqpt_stat(0);
////		equipmentVO2.setEqpt_pic();
//		dao.update(equipmentVO2);
//
//		EquipmentVO equipmentVO3 = dao.findByPrimaryKey("E000000022");
//		System.out.print(equipmentVO3.getEqpt_no() + ",");
//		System.out.print(equipmentVO3.getEqpt_vdno() + ",");
//		System.out.print(equipmentVO3.getEqpt_name() + ",");
//		System.out.print(equipmentVO3.getEqpt_etno() + ",");
//		System.out.print(equipmentVO3.getEqpt_qty() + ",");
//		System.out.print(equipmentVO3.getEqpt_price() + ",");
//		System.out.print(equipmentVO3.getEqpt_stat() + ",");
//		System.out.println(equipmentVO3.getEqpt_pic());
//		System.out.println("---------------------");
//
//		List<EquipmentVO> list = dao.getAll();
//		for (EquipmentVO aEqpt : list) {
//			System.out.print(aEqpt.getEqpt_no() + ",");
//			System.out.print(aEqpt.getEqpt_vdno() + ",");
//			System.out.print(aEqpt.getEqpt_name() + ",");
//			System.out.print(aEqpt.getEqpt_etno() + ",");
//			System.out.print(aEqpt.getEqpt_qty() + ",");
//			System.out.print(aEqpt.getEqpt_price() + ",");
//			System.out.print(aEqpt.getEqpt_stat() + ",");
//			System.out.println(aEqpt.getEqpt_pic());
//			System.out.println();
//		}
//
//		dao.delete("E000000022");
//		
//	}

}
