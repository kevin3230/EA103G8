package com.vendor.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ordereqpt.model.OrderEqptVO;

public class VendorLeaseEqptDAO implements VendorLeaseEqptDAO_interface{
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ALL_BY_TODAY = 
			"SELECT distinct " +
			"OM_NO, OM_MEMNO, OM_VDNO, OM_STAT, " +
			"OC_START, OC_END, MEM_NAME " +
			"FROM order_master om " +
			"JOIN ORDER_CAMP oe ON (OM_NO=OC_OMNO) " +
			"JOIN members m ON (om.om_memno=m.mem_no) " +
			"WHERE OM_VDNO = ? AND OM_STAT=1 " +
			"AND CURRENT_DATE BETWEEN  OC_START and OC_END " +
			"order by om_no, OC_START";
	
	private static final String GET_BYOMNO =
			"SELECT * FROM ORDER_EQPT oe " +
			"JOIN EQUIPMENT eqpt on(oe_eqptno=eqpt_no) " +
			"WHERE OE_OMNO = ? " +
			"ORDER BY EQPT_NO ";
	@Override
	public List<VendorLeaseEqptVO> getAllByToday(String om_vdno) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VendorLeaseEqptVO> list = new ArrayList<VendorLeaseEqptVO>();
		VendorLeaseEqptVO vendorLeaseEqptVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_TODAY);
			pstmt.setString(1, om_vdno);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				vendorLeaseEqptVO = new VendorLeaseEqptVO();
				vendorLeaseEqptVO.setOm_no(rs.getString("om_no"));
				vendorLeaseEqptVO.setOm_memno(rs.getString("om_memno"));
				vendorLeaseEqptVO.setOm_vdno(rs.getString("om_vdno"));
				vendorLeaseEqptVO.setMem_name(rs.getString("mem_name"));
				vendorLeaseEqptVO.setOc_start(rs.getDate("oc_start"));
				vendorLeaseEqptVO.setOc_end(rs.getDate("oc_end"));
				list.add(vendorLeaseEqptVO);
				
			}
			
		}catch (SQLException se) {
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
	public List<VendorLeaseEqptVO> getOrderEqptsByOmno(String om_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<VendorLeaseEqptVO> vendorLeaseEqptVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYOMNO);
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				VendorLeaseEqptVO vendorLeaseEqptVO = new VendorLeaseEqptVO();
				vendorLeaseEqptVO = new VendorLeaseEqptVO();
				vendorLeaseEqptVO.setOe_no(rs.getString("OE_NO"));
				vendorLeaseEqptVO.setOe_eqptno(rs.getString("OE_EQPTNO"));
				vendorLeaseEqptVO.setOm_no(om_no);
				vendorLeaseEqptVO.setOe_qty(rs.getInt("OE_QTY"));
				vendorLeaseEqptVO.setOe_stat(rs.getInt("OE_STAT"));
				vendorLeaseEqptVO.setOe_expget(rs.getDate("OE_EXPGET"));
				vendorLeaseEqptVO.setOe_expback(rs.getDate("OE_EXPBACK"));
				vendorLeaseEqptVO.setOe_get(rs.getTimestamp("OE_GET"));
				vendorLeaseEqptVO.setOe_back(rs.getTimestamp("OE_BACK"));
				vendorLeaseEqptVO.setOe_reqty(rs.getInt("OE_REQTY"));
				vendorLeaseEqptVO.setEqpt_name(rs.getString("eqpt_name"));
				vendorLeaseEqptVOList.add(vendorLeaseEqptVO);
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

		return vendorLeaseEqptVOList;
	}
	
}
