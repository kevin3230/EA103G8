package com.careqpt.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;




public class CarEqptDAO2 implements CarEqptDAO_interface2 {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String GET_JOIN_STMT =
			"SELECT distinct cc_memno, camp.camp_vdno, eqpt.eqpt_no, cc.cc_start, cc.cc_end, " + 
			"eqpt.eqpt_name, eqpt.eqpt_etno, eqpt.eqpt_qty, eqpt.eqpt_price, eqpt.eqpt_stat " + 
			"FROM CAR_CAMP cc " + 
			"JOIN camp ON( cc.cc_campno = camp.camp_no ) " + 
			"JOIN EQUIPMENT eqpt ON(camp.camp_vdno = eqpt.eqpt_vdno) "+
			"WHERE cc_memno = ? AND EQPT_STAT=2 " +
			"ORDER BY EQPT_NO";
	
	private static final String FIND_BY = 
			"SELECT eqpt.eqpt_no, eqpt.eqpt_qty, EA_QTY "+
			"FROM CAR_CAMP cc "+
			"JOIN camp ON( cc.cc_campno = camp.camp_no ) " + 
			"JOIN EQUIPMENT eqpt ON(camp.camp_vdno = eqpt.eqpt_vdno) "+
			"join EQPT_AVAIL ea on(ea.ea_eqptno= eqpt.eqpt_no) "+
			"WHERE EA_EQPTNO = ? AND trunc(EA_DATE) = ? AND EQPT_STAT=2 ";
	
	private static final String GET_MULTI_BYEADATE_STMT =
			"SELECT EA_EQPTNO, EA_DATE, EA_QTY "+ 
			"FROM EQPT_AVAIL WHERE EA_DATE = ? "+ 
			"ORDER BY EA_DATE";
	
	private static final String GET_ONE_PIC =
			"SELECT EQPT_NO, EQPT_PIC "+
			"FROM EQUIPMENT "+
			"WHERE EQPT_NO = ? AND EQPT_STAT=2";
	private static final String GET_EQPTNO_MIN_PRICE =
			"SELECT p.pro_no, p.pro_start, p.pro_end, p.pro_vdno, pe.pe_eqptno, pe.pe_price, p.pro_start " +
			"from promotion p "+
			"left join promo_eqpt pe on(p.pro_no=pe.pe_prono) "+
			"WHERE   PRO_STAT = 1 AND CURRENT_DATE BETWEEN PRO_START AND PRO_END and pe.pe_eqptno=? "+
			"ORDER BY  pe.pe_price";
	
	@Override
	public List<CarEqptVO2> getAllBymem_no(String cc_memno) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarEqptVO2> list = new ArrayList<CarEqptVO2>();
		CarEqptVO2 carEqptVO2 = null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_JOIN_STMT);
			pstmt.setString(1, cc_memno);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				carEqptVO2 = new CarEqptVO2();
				carEqptVO2.setCamp_vdno(rs.getString("camp_vdno"));
				carEqptVO2.setEqpt_no(rs.getString("eqpt_no"));
				carEqptVO2.setCc_start(rs.getDate("cc_start"));
				carEqptVO2.setCc_end(rs.getDate("cc_end"));
				carEqptVO2.setEqpt_name(rs.getString("eqpt_name"));
				carEqptVO2.setEqpt_etno(rs.getString("eqpt_etno"));
				carEqptVO2.setEqpt_qty(rs.getInt("eqpt_qty"));
				carEqptVO2.setEqpt_price(rs.getInt("eqpt_price"));
				carEqptVO2.setEqpt_stat(rs.getInt("eqpt_stat"));
				carEqptVO2.setCc_memno(cc_memno);
				list.add(carEqptVO2);
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
	public CarEqptVO2 getOneEqptAvail(String ea_eqptno, Date ea_date) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			CarEqptVO2 carEqptVO2 = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_BY);
				pstmt.setString(1, ea_eqptno);
				pstmt.setDate(2, ea_date);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					carEqptVO2 = new CarEqptVO2();
					carEqptVO2.setEa_eqptno(ea_eqptno);
					carEqptVO2.setEa_date(ea_date);
					carEqptVO2.setEa_qty(rs.getInt("EA_QTY"));
					carEqptVO2.setEqpt_qty(rs.getInt("EQPT_QTY"));
				}

			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						pstmt.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
			return carEqptVO2;
	}
	
	@Override
	public List<CarEqptVO2> getEqptAvailsByeadate(Date start) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarEqptVO2 carEqptVO2 = null;
		List<CarEqptVO2> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_MULTI_BYEADATE_STMT);
			
			pstmt.setDate(1, start);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				carEqptVO2 = new CarEqptVO2();
				carEqptVO2.setEa_eqptno(rs.getString(1));;
				carEqptVO2.setEa_date(rs.getDate(2));
				carEqptVO2.setEa_qty(rs.getInt(3));
				list.add(carEqptVO2);
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
	public CarEqptVO2 getOnePic(String eqpt_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarEqptVO2 carEqptVO2 = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_PIC);
			pstmt.setString(1, eqpt_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				carEqptVO2 = new CarEqptVO2();
				carEqptVO2.setEqpt_no(eqpt_no);
				carEqptVO2.setEqpt_pic(rs.getBytes("EQPT_PIC"));
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();// 關閉連線
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return carEqptVO2;
	}

	@Override
	public List<CarEqptVO2> getMinEqptPrice(String ea_eqptno) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			CarEqptVO2 carEqptVO2 = null;
			List<CarEqptVO2> list = new ArrayList<>();
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_EQPTNO_MIN_PRICE);
				pstmt.setString(1, ea_eqptno);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					carEqptVO2 = new CarEqptVO2();
					carEqptVO2.setPe_eqptno(ea_eqptno);
					carEqptVO2.setPe_price(rs.getInt("PE_PRICE"));
					list.add(carEqptVO2);
				}

			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						pstmt.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();// 關閉連線
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}
			return list;
	}

}

