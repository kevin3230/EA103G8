package com.equipment.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EquipmentDAO implements EquipmentDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO EQUIPMENT (EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC) VALUES ('E' || LPAD(SEQ_EQPTNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC FROM EQUIPMENT ORDER BY EQPT_NO";
	private static final String GET_ONE_STMT = "SELECT EQPT_NO, EQPT_VDNO, EQPT_NAME, EQPT_ETNO, EQPT_QTY, EQPT_PRICE, EQPT_STAT, EQPT_PIC FROM EQUIPMENT WHERE EQPT_NO = ?";
//	private static final String REAL_DELETE_STMT = "DELETE FROM EQUIPMENT WHERE EQPT_NO = ?";
	private static final String UPDATE_STMT = "UPDATE EQUIPMENT SET EQPT_VDNO=?, EQPT_NAME=?, EQPT_ETNO=?, EQPT_QTY=?, EQPT_PRICE=?, EQPT_STAT=?, EQPT_PIC=? WHERE EQPT_NO = ?";
	private static final String DELETE_STMT = "UPDATE EQUIPMENT SET EQPT_STAT=? WHERE EQPT_NO = ?"; // 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	private static final String UPDATE_STAT_STMT = "UPDATE EQUIPMENT SET EQPT_STAT=? WHERE EQPT_NO = ?";
	private static final String GET_MULTI_BYVDNO_STMT = "SELECT * FROM EQUIPMENT WHERE EQPT_VDNO = ? ORDER BY EQPT_NO";
	//By 柏誼
	private static final String UPDATE_QTY_STMT = "UPDATE EQUIPMENT SET EQPT_QTY=? WHERE EQPT_NO = ?";
	
	private static final String GET_ALLCT_BYVDNO = "SELECT EQPT_ETNO FROM EQUIPMENT WHERE EQPT_VDNO = ? AND EQPT_STAT >= 0 ORDER BY EQPT_NO"; 
	private static final String GET_MULTI_BYVDNOETNO_STMT = "SELECT * FROM EQUIPMENT WHERE EQPT_VDNO = ? AND EQPT_ETNO = ? AND EQPT_STAT >= 0 ORDER BY EQPT_NO";
	
	@Override
	public void insert(EquipmentVO equipmentVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, equipmentVO.getEqpt_vdno());
			pstmt.setString(2, equipmentVO.getEqpt_name());
			pstmt.setString(3,equipmentVO.getEqpt_etno());
			pstmt.setInt(4, equipmentVO.getEqpt_qty());
			pstmt.setInt(5, equipmentVO.getEqpt_price());
			pstmt.setInt(6, equipmentVO.getEqpt_stat());
			pstmt.setBytes(7, equipmentVO.getEqpt_pic());

			pstmt.executeUpdate();

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

			con = ds.getConnection();
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
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(REAL_DELETE_STMT);
//			
//			pstmt.setString(1, eqpt_no);			
//			pstmt.executeUpdate();
//
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, -1);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();

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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 2);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();
			
//			System.out.println(camp_no + " 於DAO上架完成");
			
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
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, eqpt_no);
			
			pstmt.executeUpdate();
			
//			System.out.println(camp_no + " 於DAO上架完成");
			
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
			
			con = ds.getConnection();
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
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_QTY_STMT);
			
			pstmt.setInt(1, equipmentVO.getEqpt_qty());
			pstmt.setString(2, equipmentVO.getEqpt_no());
			
			pstmt.executeUpdate();
			
			
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
	public Set<String> getAllEt_no(String vd_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Set<String> set = new LinkedHashSet<String>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALLCT_BYVDNO);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String eqpt_etno = rs.getString("EQPT_ETNO");
				set.add(eqpt_etno);
			}
			
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
		List<EquipmentVO> list = new ArrayList<EquipmentVO>();
		
		try {
			con = ds.getConnection();
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
	
}
