package com.ordermaster.model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import com.eqptavail.model.EqptAvailJDBCDAO;
import com.eqptavail.model.EqptAvailVO;
import com.equipment.model.EquipmentJDBCDAO;
import com.camp.model.CampJDBCDAO;
import com.campavail.model.CampAvailJDBCDAO;
import com.campavail.model.CampAvailVO;
import com.carcamp.model.CarCampJDBCDAO;
import com.carcamp.model.CarCampVO;
import com.careqpt.model.CarEqptJDBCDAO;
import com.careqpt.model.CarEqptVO;
import com.carfood.model.CarFoodJDBCDAO;
import com.carfood.model.CarFoodVO;
import com.ordercamp.model.OrderCampJDBCDAO;
import com.ordercamp.model.OrderCampVO;
import com.ordereqpt.model.OrderEqptJDBCDAO;
import com.ordereqpt.model.OrderEqptVO;
import com.orderfood.model.OrderFoodJDBCDAO;
import com.orderfood.model.OrderFoodVO;

import utilities.OrderMaster_CompositeQuery;

public class OrderMasterJDBCDAO implements OrderMasterDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT = 
			"INSERT INTO order_master (om_no, om_memno, om_vdno, om_stat, om_note, om_txnamt, om_txntime, om_cardno) VALUES('O'|| LPAD(SEQ_OMNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT om_no, om_memno, om_vdno, om_stat, om_note, to_char(om_estbl, 'YYYY-MM-DD HH:MM:SS') om_estbl, om_txnamt, to_char(om_txntime, 'YYYY-MM-DD HH:MM:SS') om_txntime, om_cardno FROM order_master ORDER BY om_no";
	private static final String GET_ONE_STMT = 
			"SELECT om_no, om_memno, om_vdno, om_stat, om_note, to_char(om_estbl, 'YYYY-MM-DD HH:MM:SS') om_estbl, om_txnamt, to_char(om_txntime, 'YYYY-MM-DD HH:MM:SS') om_txntime, om_cardno FROM order_master WHERE om_no = ?";
	private static final String DELETE = 
			"DELETE FROM order_master WHERE om_no = ?";
	private static final String UPDATE = 
			"UPDATE order_master SET om_stat=?, om_note=?, om_txntime=? WHERE om_no = ?";
	// Author Jeff
	private static final String SQL_INSERT = 
			"INSERT INTO order_master (om_no, om_memno, om_vdno, om_txnamt, om_cardno, om_stat) VALUES('O'|| LPAD(SEQ_OMNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = 
			"UPDATE order_master SET om_txnamt=?, om_txntime=? WHERE om_no=?";
	//新增by李承璋
	private static final String GET_MEMNO_STMT =
			"SELECT * FROM order_master WHERE om_memno = ?";
	private static final String CANCELORDER_BYOMNO = 
			"UPDATE order_master SET om_stat=? WHERE om_no = ?";
	//新增by李承璋
	@Override
	public void insert(OrderMasterVO ordermasterVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, ordermasterVO.getOm_memno());
			pstmt.setString(2, ordermasterVO.getOm_vdno());
			pstmt.setInt(3, ordermasterVO.getOm_stat());
			pstmt.setString(4, ordermasterVO.getOm_note());
			pstmt.setInt(5, ordermasterVO.getOm_txnamt());
			pstmt.setDate(6, ordermasterVO.getOm_txntime());
			pstmt.setString(7, ordermasterVO.getOm_cardno());
			
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
	public void update(OrderMasterVO ordermasterVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, ordermasterVO.getOm_stat());
			pstmt.setString(2, ordermasterVO.getOm_note());
			pstmt.setDate(3, ordermasterVO.getOm_txntime());
			pstmt.setString(4, ordermasterVO.getOm_no());
			
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
	public void delete(String om_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, om_no);
			
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
	public OrderMasterVO findByPrimaryKey(String om_no) {
		
		OrderMasterVO ordermasterVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, om_no);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				ordermasterVO = new OrderMasterVO();
				ordermasterVO.setOm_no(rs.getString("om_no"));
				ordermasterVO.setOm_memno(rs.getString("om_memno"));
				ordermasterVO.setOm_vdno(rs.getString("om_vdno"));
				ordermasterVO.setOm_stat(rs.getInt("om_stat"));
				ordermasterVO.setOm_note(rs.getString("om_note"));
				ordermasterVO.setOm_estbl(rs.getTimestamp("om_estbl"));
				ordermasterVO.setOm_txnamt(rs.getInt("om_txnamt"));
				ordermasterVO.setOm_txntime(rs.getDate("om_txntime"));
				ordermasterVO.setOm_cardno(rs.getString("om_cardno"));
				
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
		return ordermasterVO;
	}
	
	@Override
	public List<OrderMasterVO> getAll() {
		
		List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		OrderMasterVO ordermasterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ordermasterVO = new OrderMasterVO();
				ordermasterVO.setOm_no(rs.getString("om_no"));
				ordermasterVO.setOm_memno(rs.getString("om_memno"));
				ordermasterVO.setOm_vdno(rs.getString("om_vdno"));
				ordermasterVO.setOm_stat(rs.getInt("om_stat"));
				ordermasterVO.setOm_note(rs.getString("om_note"));
				ordermasterVO.setOm_estbl(rs.getTimestamp("om_estbl"));
				ordermasterVO.setOm_txnamt(rs.getInt("om_txnamt"));
				ordermasterVO.setOm_txntime(rs.getDate("om_txntime"));
				ordermasterVO.setOm_cardno(rs.getString("om_cardno"));
				list.add(ordermasterVO);
				
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

	// Author Jeff
	@Override
	public String insertWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList, List<CarCampVO> ccVOList, List<CarEqptVO> ceVOList, List<CarFoodVO> cfVOList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String om_no = null;
		try {
			// Start up DB driver.
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			conn.setAutoCommit(false);
			// Insert order master with details transaction start.
			String[] seqNo = { "OM_NO" };
			pstmt = conn.prepareStatement(SQL_INSERT, seqNo);
			pstmt.setString(1, omVO.getOm_memno());
			pstmt.setString(2, omVO.getOm_vdno());
			pstmt.setInt(3, omVO.getOm_txnamt());
			pstmt.setString(4, omVO.getOm_cardno());
			pstmt.setInt(5, omVO.getOm_stat());
			pstmt.executeUpdate();
			// 1. Get generated OrderMaster PK
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				om_no = rs.getString(1);
				System.out.println("OrderMasterDAO insert pstmt.getGeneratedKeys() : " + om_no);
			} else {
				System.out.println("OrderMasterDAO insert pstmt.getGeneratedKeys() OM_NO failed.");
			}
			// 2. Set PK to details VO
			for(OrderCampVO ocVO : ocVOList) {
				ocVO.setOc_omno(om_no);
			}
			for(OrderEqptVO oeVO : oeVOList) {
				oeVO.setOe_omno(om_no);
			}
			for(OrderFoodVO ofVO : ofVOList) {
				ofVO.setOf_omno(om_no);
			}
			// 3. Call details DAO & set the same connection to insert details with the same transaction.
			OrderCampJDBCDAO ocDAO = new OrderCampJDBCDAO();
			OrderEqptJDBCDAO oeDAO = new OrderEqptJDBCDAO();
			OrderFoodJDBCDAO ofDAO = new OrderFoodJDBCDAO();
			for(OrderCampVO ocVO : ocVOList) {
				ocDAO.insert(ocVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderCamp with OrderMaster execute %d rows complete. \n", ocVOList.size());
			for(OrderEqptVO oeVO : oeVOList) {
				oeDAO.insert(oeVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderEqpt with OrderMaster execute %d rows complete. \n", oeVOList.size());
			for(OrderFoodVO ofVO : ofVOList) {
				ofDAO.insert(ofVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderFood with OrderMaster execute %d rows complete. \n", ofVOList.size());
			// 4. Call details available DAO & set the same connection to update available quantity with the same transaction.
			CampJDBCDAO campDAO = new CampJDBCDAO();
			EquipmentJDBCDAO eqptDAO = new EquipmentJDBCDAO();
			CampAvailJDBCDAO caDAO = new CampAvailJDBCDAO();
			EqptAvailJDBCDAO eaDAO = new EqptAvailJDBCDAO();
			// 4-1 CampAvail
			for(OrderCampVO ocVOi : ocVOList) {
				String oc_campno = ocVOi.getOc_campno();
				int oc_qty = ocVOi.getOc_qty();
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd(ocVOi.getOc_start(), ocVOi.getOc_end());
				for(java.sql.Date ca_date : dateSeq) {
					CampAvailVO caVO0 = caDAO.findByPrimaryKey(ocVOi.getOc_campno(), ca_date);
					// 4-1-1 CampAvail DB didn't exist CA_CAMPNO-CA_DATE key pair row data, need to insert.
					if(caVO0 == null) {
						CampAvailVO caVO = new CampAvailVO();
						int campMaxQty = campDAO.findByPrimaryKey(oc_campno).getCamp_qty();
						caVO.setCa_campno(oc_campno);
						caVO.setCa_date(ca_date);
						caVO.setCa_qty(campMaxQty - oc_qty);
						caDAO.insert(caVO, conn);
						System.out.printf("OrderMasterDAO inserting CAMP_AVAIL : %s-%s with %d remain camps...... \n",
								oc_campno, ca_date, campMaxQty - oc_qty);
					// 4-1-2 CampAvail DB exist CA_CAMPNO-CA_DATE key pair row data, need to update.
					}else {
						int campRemainQty = caVO0.getCa_qty();
						CampAvailVO caVO = new CampAvailVO();
						caVO.setCa_campno(ocVOi.getOc_campno());
						caVO.setCa_date(ca_date);
						caVO.setCa_qty(campRemainQty - oc_qty);
						caDAO.update(caVO, conn);
						System.out.printf("OrderMasterDAO updating CAMP_AVAIL : %s-%s with %d remain camps...... \n", 
								oc_campno, ca_date, campRemainQty - oc_qty);
					}
					
				}
			}
			// 4-2 EqptAvail
			for(OrderEqptVO oeVOi : oeVOList) {
				String oe_eqptno = oeVOi.getOe_eqptno();
				int oe_qty = oeVOi.getOe_qty();
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd(oeVOi.getOe_expget(), oeVOi.getOe_expback());
				for(java.sql.Date ea_date : dateSeq) {
					EqptAvailVO eaVO0 = eaDAO.findByPrimaryKey(oe_eqptno, ea_date);
					// 4-2-1 EqptAvail DB didn't exist EA_EQPTNO-EA_DATE key pair row data, need to insert.
					if(eaVO0 == null) {
						EqptAvailVO eaVO = new EqptAvailVO();
						int eqptMaxQty = eqptDAO.findByPrimaryKey(oe_eqptno).getEqpt_qty();
						eaVO.setEa_eqptno(oe_eqptno);
						eaVO.setEa_date(ea_date);
						eaVO.setEa_qty(eqptMaxQty - oe_qty);
						eaDAO.insert(eaVO, conn);
						System.out.printf("OrderMasterDAO inserting EQPT_AVAIL : %s-%s with %d remain eqpts...... \n",
								oe_eqptno, ea_date, eqptMaxQty - oe_qty);
					// 4-2-2 EqptAvail DB exist EA_EQPTNO-EA_DATE key pair row data, need to update.
					}else {
						int eqptRemainQty = eaVO0.getEa_qty();
						EqptAvailVO eaVO = new EqptAvailVO();
						eaVO.setEa_eqptno(oeVOi.getOe_eqptno());
						eaVO.setEa_date(ea_date);
						eaVO.setEa_qty(eqptRemainQty - oe_qty);
						eaDAO.update(eaVO, conn);
						System.out.printf("OrderMasterDAO updating EQPT_AVAIL : %s-%s with %d remain eqpts...... \n", 
								oe_eqptno, ea_date, eqptRemainQty - oe_qty);
					}
				}
			}
			// 5. Delete existing items in ShoppingCart Tables.
			CarCampJDBCDAO ccDAO = new CarCampJDBCDAO();
			CarEqptJDBCDAO ceDAO = new CarEqptJDBCDAO();
			CarFoodJDBCDAO cfDAO = new CarFoodJDBCDAO();
			for(CarCampVO ccVOi : ccVOList) {
				ccDAO.delete(ccVOi, conn);
				System.out.printf("OrderMasterDAO deleting CAR_CAMP : %s-%s...... \n", ccVOi.getCc_memno(), ccVOi.getCc_campno());
			}
			for(CarEqptVO ceVOi : ceVOList) {
				ceDAO.delete(ceVOi, conn);
				System.out.printf("OrderMasterDAO deleting CAR_EQPT : %s...... \n", ceVOi.getCe_no());
			}
			for(CarFoodVO cfVOi : cfVOList) {
				cfDAO.delete(cfVOi, conn);
				System.out.printf("OrderMasterDAO deleting CAR_FOOD : %s-%s...... \n", cfVOi.getCf_memno(), cfVOi.getCf_foodno());
			}
			// 6. Commit all SQL execute.
			conn.commit();
			System.out.println("OrderMasterDAO insertWithDetails commit transaction.");
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch(SQLException se) {
			try {
				conn.rollback();
				System.out.println("OrderMasterDAO insertWithDetails rollback transaction.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch(RuntimeException re){
			try {
				conn.rollback();
				System.out.println("OrderMasterDAO insertWithDetails rollback from other DAO trancsaction.");
				re.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return om_no;
	}
	
	// Author Jeff
	@Override
	public String updateWithDetails(OrderMasterVO omVO, List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList,
			List<OrderFoodVO> ofVOList) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// Get om_no from omVO
		String om_no = omVO.getOm_no();
		
		try {
			// Start up DB driver.
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			conn.setAutoCommit(false);
			
			/***** Recover CampAvail & EqptAvail first. *****/
			// Recover CampAvail, EqptAvail by ocVOListOld, oeVOListOld
			OrderMasterJDBCDAO thisSvc = new OrderMasterJDBCDAO();
			// if exception occur, will throw RuntimeException.
			Map<String, Integer> oldOrderCampQtyMap = thisSvc.recoverCampAvailFromOrderMaster(om_no, conn);
			Map<String, Integer> oldOrderEqptQtyMap = thisSvc.recoverEqptAvailFromOrderMaster(om_no, conn);
			/***** Update OrderMaster; Update old OrderItems; Insert new OrderItems; Update CampAvail, EqptAvail. *****/
			// 1. Update order master with omVO
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setInt(1, omVO.getOm_txnamt());
			pstmt.setDate(2, omVO.getOm_txntime());
			pstmt.setString(3, om_no);
			pstmt.executeUpdate();
			
			// 2. Update origin order items stat to 0.
			OrderCampJDBCDAO ocDAO = new OrderCampJDBCDAO();
			OrderEqptJDBCDAO oeDAO = new OrderEqptJDBCDAO();
			OrderFoodJDBCDAO ofDAO = new OrderFoodJDBCDAO();
			List<OrderCampVO> ocVOListOld = ocDAO.getOrderCampsByOmno(om_no);
			List<OrderEqptVO> oeVOListOld = oeDAO.getOrderEqptsByOmno(om_no);
			List<OrderFoodVO> ofVOListOld = ofDAO.getOrderFoodsByOmno(om_no);
			for(OrderCampVO ocVO : ocVOListOld) {
				ocVO.setOc_stat(0);
				ocDAO.update(ocVO, conn);
			}
			System.out.printf("OrderMasterDAO udpate OrderCamp with OrderMaster execute %d rows complete. \n", ocVOListOld.size());
			for(OrderEqptVO oeVO : oeVOListOld) {
				oeVO.setOe_stat(0);
				oeDAO.update(oeVO, conn);
			}
			System.out.printf("OrderMasterDAO update OrderEqpt with OrderMaster execute %d rows complete. \n", oeVOListOld.size());
			for(OrderFoodVO ofVO : ofVOListOld) {
				ofVO.setOf_stat(0);
				ofDAO.update(ofVO, conn);
			}
			System.out.printf("OrderMasterDAO update OrderFood with OrderMaster execute %d rows complete. \n", ofVOListOld.size());
			
			// 3. Insert new order items.
			// Set om_no to new order items VO.
			for(OrderCampVO ocVO : ocVOList) {
				ocVO.setOc_omno(om_no);
			}
			for(OrderEqptVO oeVO : oeVOList) {
				oeVO.setOe_omno(om_no);
			}
			for(OrderFoodVO ofVO : ofVOList) {
				ofVO.setOf_omno(om_no);
			}
			// call DAO to insert new order items VO.
			for(OrderCampVO ocVO : ocVOList) {
				ocDAO.insert(ocVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderCamp with OrderMaster execute %d rows complete. \n", ocVOList.size());
			for(OrderEqptVO oeVO : oeVOList) {
				oeDAO.insert(oeVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderEqpt with OrderMaster execute %d rows complete. \n", oeVOList.size());
			for(OrderFoodVO ofVO : ofVOList) {
				ofDAO.insert(ofVO, conn);
			}
			System.out.printf("OrderMasterDAO insert OrderFood with OrderMaster execute %d rows complete. \n", ofVOList.size());
			
			// 4. Update or Insert CampAvail, EqptAvail by ocVOList, OeVOList
			CampJDBCDAO campDAO = new CampJDBCDAO();
			EquipmentJDBCDAO eqptDAO = new EquipmentJDBCDAO();
			CampAvailJDBCDAO caDAO = new CampAvailJDBCDAO();
			EqptAvailJDBCDAO eaDAO = new EqptAvailJDBCDAO();
			// 4-1 CampAvail
			for(OrderCampVO ocVOi : ocVOList) {
				String oc_campno = ocVOi.getOc_campno();
				int oc_qty = ocVOi.getOc_qty();
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd(ocVOi.getOc_start(), ocVOi.getOc_end());
				for(java.sql.Date ca_date : dateSeq) {
					CampAvailVO caVO0 = caDAO.findByPrimaryKey(ocVOi.getOc_campno(), ca_date);
					// 4-1-1 CampAvail DB didn't exist CA_CAMPNO-CA_DATE key pair row data, need to insert.
					if(caVO0 == null) {
						CampAvailVO caVO = new CampAvailVO();
						int campMaxQty = campDAO.findByPrimaryKey(oc_campno).getCamp_qty() + oldOrderCampQtyMap.get(oc_campno);
						caVO.setCa_campno(oc_campno);
						caVO.setCa_date(ca_date);
						caVO.setCa_qty(campMaxQty - oc_qty);
						caDAO.insert(caVO, conn);
						System.out.printf("OrderMasterDAO inserting CAMP_AVAIL : %s-%s minus %d in %d remain %d camps...... \n",
								oc_campno, ca_date, oc_qty, campMaxQty, campMaxQty - oc_qty);
					// 4-1-2 CampAvail DB exist CA_CAMPNO-CA_DATE key pair row data, need to update.
					}else {
						int campRemainQty = caVO0.getCa_qty();
						CampAvailVO caVO = new CampAvailVO();
						caVO.setCa_campno(ocVOi.getOc_campno());
						caVO.setCa_date(ca_date);
						caVO.setCa_qty(campRemainQty - oc_qty);
						caDAO.update(caVO, conn);
						System.out.printf("OrderMasterDAO updating CAMP_AVAIL : %s-%s minus %d in %d remain %d camps...... \n", 
								oc_campno, ca_date, oc_qty, campRemainQty, campRemainQty - oc_qty);
					}
					
				}
			}
			// 4-2 EqptAvail
			for(OrderEqptVO oeVOi : oeVOList) {
				String oe_eqptno = oeVOi.getOe_eqptno();
				int oe_qty = oeVOi.getOe_qty();
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd_Eqpt(oeVOi.getOe_expget(), oeVOi.getOe_expback());
				for(java.sql.Date ea_date : dateSeq) {
					EqptAvailVO eaVO0 = eaDAO.findByPrimaryKey(oe_eqptno, ea_date);
					// 4-2-1 EqptAvail DB didn't exist EA_EQPTNO-EA_DATE key pair row data, need to insert.
					if(eaVO0 == null) {
						EqptAvailVO eaVO = new EqptAvailVO();
						int eqptMaxQty = eqptDAO.findByPrimaryKey(oe_eqptno).getEqpt_qty() + oldOrderEqptQtyMap.get(oe_eqptno);
						eaVO.setEa_eqptno(oe_eqptno);
						eaVO.setEa_date(ea_date);
						eaVO.setEa_qty(eqptMaxQty - oe_qty);
						eaDAO.insert(eaVO, conn);
						System.out.printf("OrderMasterDAO inserting EQPT_AVAIL : %s-%s minus %d in %d remain %d eqpts...... \n",
								oe_eqptno, ea_date, oe_qty, eqptMaxQty, eqptMaxQty - oe_qty);
					// 4-2-2 EqptAvail DB exist EA_EQPTNO-EA_DATE key pair row data, need to update.
					}else {
						int eqptRemainQty = eaVO0.getEa_qty();
						EqptAvailVO eaVO = new EqptAvailVO();
						eaVO.setEa_eqptno(oeVOi.getOe_eqptno());
						eaVO.setEa_date(ea_date);
						eaVO.setEa_qty(eqptRemainQty - oe_qty);
						eaDAO.update(eaVO, conn);
						System.out.printf("OrderMasterDAO updating EQPT_AVAIL : %s-%s minus %d in %d remain %d eqpts...... \n", 
								oe_eqptno, ea_date, oe_qty, eqptRemainQty, eqptRemainQty - oe_qty);
					}
				}
			}
			// 5. Commit all SQL execute.
			conn.commit();
			System.out.printf("OrderMasterDAO %s updateWithDetails commit transaction. \n", om_no);
			
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		} catch(SQLException se) {
			try {
				conn.rollback();
				System.out.println("OrderMasterDAO updateWithDetails rollback transaction.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			se.printStackTrace();
		} catch(RuntimeException re){
			try {
				conn.rollback();
				System.out.println("OrderMasterDAO updateWithDetails rollback from other DAO trancsaction.");
				re.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				}catch(Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return om_no;
	}
	
	@Override
	public Map<String, Integer> recoverEqptAvailFromOrderMaster(String om_no, Connection conn) {
		Map<String, Integer> prevQtyMap = new HashMap<String, Integer>();
		try {
			// Get OrderItems from om_no
			OrderEqptJDBCDAO oeDAO = new OrderEqptJDBCDAO();
			List<OrderEqptVO> oeVOList = oeDAO.getActiveOrderEqptsByOmno(om_no);
			
			// Recover EqptAvail
			EqptAvailJDBCDAO eaDAO = new EqptAvailJDBCDAO();
			for(OrderEqptVO oeVOi : oeVOList) {
				String oe_eqptno = oeVOi.getOe_eqptno();
				int oe_qty = oeVOi.getOe_qty();
				prevQtyMap.put(oe_eqptno, oe_qty);
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd_Eqpt(oeVOi.getOe_expget(), oeVOi.getOe_expback());
				for(java.sql.Date ea_date : dateSeq) {
					EqptAvailVO eaVO0 = eaDAO.findByPrimaryKey(oe_eqptno, ea_date);
					// check if EqptAvail DB exist EA_EQPTNO-EA_DATE key pair row data.
					if(eaVO0 != null) {
						int eqptRemainQty = eaVO0.getEa_qty();
						EqptAvailVO eaVO = new EqptAvailVO();
						eaVO.setEa_eqptno(oeVOi.getOe_eqptno());
						eaVO.setEa_date(ea_date);
						eaVO.setEa_qty(eqptRemainQty + oe_qty); // add back avail qty.
						eaDAO.update(eaVO, conn);
						System.out.printf("OrderMasterDAO recovering EQPT_AVAIL : %s-%s plus %d in %d total %d eqpts...... \n", 
								oe_eqptno, ea_date, oe_qty, eqptRemainQty, eqptRemainQty + oe_qty);
					}else {
						System.out.printf("OrderMasterDAO recoverAvailFromOrderMaster method %s-%s null in DB. \n", oe_eqptno, ea_date);
					}
				}
			}
			
		} catch(RuntimeException re){
			try {
				conn.rollback();
				re.printStackTrace();
				throw new RuntimeException("OrderMasterDAO recoverAvailFromOrderMaster failed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prevQtyMap;
	}
	
	@Override
	public Map<String, Integer> recoverCampAvailFromOrderMaster(String om_no, Connection conn) {
		Map<String, Integer> prevQtyMap = new HashMap<String, Integer>();
		try {
			// Get OrderItems from om_no
			OrderCampJDBCDAO ocDAO = new OrderCampJDBCDAO();
			List<OrderCampVO> ocVOList = ocDAO.getActiveOrderCampsByOmno(om_no);
			
			// Recover CampAvail
			CampAvailJDBCDAO caDAO = new CampAvailJDBCDAO();
			for(OrderCampVO ocVOi : ocVOList) {
				String oc_campno = ocVOi.getOc_campno();
				int oc_qty = ocVOi.getOc_qty();
				prevQtyMap.put(oc_campno, oc_qty);
				List<java.sql.Date> dateSeq = getDateSeqByStartEnd(ocVOi.getOc_start(), ocVOi.getOc_end());
				for(java.sql.Date ca_date : dateSeq) {
					CampAvailVO caVO0 = caDAO.findByPrimaryKey(ocVOi.getOc_campno(), ca_date);
					// check if CampAvail DB exist CA_CAMPNO-CA_DATE key pair row data.
					if(caVO0 != null) {
						int campRemainQty = caVO0.getCa_qty();
						CampAvailVO caVO = new CampAvailVO();
						caVO.setCa_campno(ocVOi.getOc_campno());
						caVO.setCa_date(ca_date);
						caVO.setCa_qty(campRemainQty + oc_qty); // add back avail qty.
						caDAO.update(caVO, conn);
						System.out.printf("OrderMasterDAO recovering CAMP_AVAIL : %s-%s plus %d in %d total %d camps...... \n", 
								oc_campno, ca_date, oc_qty, campRemainQty, campRemainQty + oc_qty);
					}else {
						System.out.printf("OrderMasterDAO recoverAvailFromOrderMaster method %s-%s null in DB. \n", oc_campno, ca_date);
					}
				}
			}
			
		} catch(RuntimeException re){
			try {
				conn.rollback();
				re.printStackTrace();
				throw new RuntimeException("OrderMasterDAO recoverAvailFromOrderMaster failed.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return prevQtyMap;
	}
	
	// Author Jeff
	private List<java.sql.Date> getDateSeqByStartEnd(java.sql.Date start_date, java.sql.Date end_date){
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = start_date.getTime();
		long endLong = end_date.getTime();
		long seqLong = startLong;
		long dayLong = 24 * 60 * 60 * 1000;
		while(seqLong < endLong) {
			String seqDateFmt = fmt.format(seqLong);
			result.add(java.sql.Date.valueOf(seqDateFmt));
			seqLong += dayLong;
		}
		return result;
	}
	
	private List<java.sql.Date> getDateSeqByStartEnd_Eqpt(java.sql.Date start_date, java.sql.Date end_date){
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = start_date.getTime();
		long endLong = end_date.getTime();
		long seqLong = startLong;
		long dayLong = 24 * 60 * 60 * 1000;
		while(seqLong <= endLong) {
			String seqDateFmt = fmt.format(seqLong);
			result.add(java.sql.Date.valueOf(seqDateFmt));
			seqLong += dayLong;
		}
		return result;
	}
	
	//新增by李承璋
	@Override
	public List<OrderMasterVO> getByMemno(String om_memno) {
		
		List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		OrderMasterVO ordermasterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, om_memno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ordermasterVO = new OrderMasterVO();
				ordermasterVO.setOm_no(rs.getString("om_no"));
				ordermasterVO.setOm_memno(rs.getString("om_memno"));
				ordermasterVO.setOm_vdno(rs.getString("om_vdno"));
				ordermasterVO.setOm_stat(rs.getInt("om_stat"));
				ordermasterVO.setOm_note(rs.getString("om_note"));
				ordermasterVO.setOm_estbl(rs.getTimestamp("om_estbl"));
				ordermasterVO.setOm_txnamt(rs.getInt("om_txnamt"));
				ordermasterVO.setOm_txntime(rs.getDate("om_txntime"));
				ordermasterVO.setOm_cardno(rs.getString("om_cardno"));
				list.add(ordermasterVO);
				
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
	public List<OrderMasterVO> getByMemno(Map<String, String[]> map) {
		
		List<OrderMasterVO> list = new ArrayList<OrderMasterVO>();
		OrderMasterVO ordermasterVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String finalSQL = "select * from Order_Master om "
				  + "join order_camp oc on om.om_no = oc.oc_omno "
		          + OrderMaster_CompositeQuery.get_WhereCondition(map)
		          + "order by om_no";
			pstmt = con.prepareStatement(finalSQL);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ordermasterVO = new OrderMasterVO();
				ordermasterVO.setOm_no(rs.getString("om_no"));
				ordermasterVO.setOm_memno(rs.getString("om_memno"));
				ordermasterVO.setOm_vdno(rs.getString("om_vdno"));
				ordermasterVO.setOm_stat(rs.getInt("om_stat"));
				ordermasterVO.setOm_note(rs.getString("om_note"));
				ordermasterVO.setOm_estbl(rs.getTimestamp("om_estbl"));
				ordermasterVO.setOm_txnamt(rs.getInt("om_txnamt"));
				ordermasterVO.setOm_txntime(rs.getDate("om_txntime"));
				ordermasterVO.setOm_cardno(rs.getString("om_cardno"));
				list.add(ordermasterVO);
				
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
	public void cancelOrder(String om_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(CANCELORDER_BYOMNO);
			
			pstmt.setInt(1, 0);
			pstmt.setString(2, om_no);
			
			pstmt.executeUpdate();
			
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		}catch (SQLException se){
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
	//新增by李承璋

	public static void main(String[] args) {
//		/*** Static Test insertWithDetails method ***/
//		OrderMasterJDBCDAO omDAO = new OrderMasterJDBCDAO();
//		CarCampJDBCDAO ccDAO = new CarCampJDBCDAO();
//		CarEqptJDBCDAO ceDAO = new CarEqptJDBCDAO();
//		CarFoodJDBCDAO cfDAO = new CarFoodJDBCDAO();
//		// General fake data
//		int listprice = 1000;
//		int price = 800;
//		java.sql.Date start = java.sql.Date.valueOf("2020-10-01");
//		java.sql.Date end = java.sql.Date.valueOf("2020-10-03");
//		// OrderMaster fake data
//		OrderMasterVO omVO = new OrderMasterVO("M000000001", "V000000001", 10800, "1234567812345678");
//		// OrderCamp fake data
//		List<OrderCampVO> ocVOList = new ArrayList<OrderCampVO>();
//		ocVOList.add(new OrderCampVO("C000000001", 2, listprice, price, start, end));
//		ocVOList.add(new OrderCampVO("C000000002", 3, listprice, price, start, end));
//		// OrderEqpt fake data
//		List<OrderEqptVO> oeVOList = new ArrayList<OrderEqptVO>();
//		oeVOList.add(new OrderEqptVO("E000000001", 2, listprice, price, start, end));
//		oeVOList.add(new OrderEqptVO("E000000002", 3, listprice, price, start, end));
//		// OrderFood fake data
//		List<OrderFoodVO> ofVOList = new ArrayList<OrderFoodVO>();
//		ofVOList.add(new OrderFoodVO("F000000004", 2, listprice, price));
//		ofVOList.add(new OrderFoodVO("F000000005", 3, listprice, price));
//		
//		// CarCamp fake data
//		List<CarCampVO> ccVOList = new ArrayList<CarCampVO>();
//		ccVOList.add(new CarCampVO("C000000001", "M000000001", 4, start, end));
//		ccVOList.add(new CarCampVO("C000000002", "M000000001", 5, start, end));
//		for(CarCampVO ccVOi : ccVOList) {
//			ccDAO.insert(ccVOi);
//		}
//		// CarEqpt fake data
//		List<CarEqptVO> ceVOList = new ArrayList<CarEqptVO>();
//		ceVOList.add(new CarEqptVO("E000000001", "M000000001", 4, start, end));
//		ceVOList.add(new CarEqptVO("E000000002", "M000000001", 5, start, end));
//		for(CarEqptVO ceVOi : ceVOList) {
//			String ceVOi_ceno = ceDAO.insert(ceVOi);
//			ceVOi.setCe_no(ceVOi_ceno);
//		}
//		// CarFood fake data
//		List<CarFoodVO> cfVOList = new ArrayList<CarFoodVO>();
//		cfVOList.add(new CarFoodVO("F000000004", "M000000001", 4));
//		cfVOList.add(new CarFoodVO("F000000005", "M000000001", 5));
//		for(CarFoodVO cfVOi : cfVOList) {
//			cfDAO.insert(cfVOi);
//		}
//		// Execute
//		String om_no = null;
//		om_no = omDAO.insertWithDetails(omVO, ocVOList, oeVOList, ofVOList, ccVOList, ceVOList, cfVOList);
//		System.out.println("OrderMasterDAO main : Success execute insertWithDetails method, om_no : " + om_no);
		
		/*** Static Test updateWithDetails method ***/
		OrderMasterJDBCDAO omDAO = new OrderMasterJDBCDAO();
		OrderCampJDBCDAO ocDAO = new OrderCampJDBCDAO();
		OrderEqptJDBCDAO oeDAO = new OrderEqptJDBCDAO();
		OrderFoodJDBCDAO ofDAO = new OrderFoodJDBCDAO();
		CarCampJDBCDAO ccDAO = new CarCampJDBCDAO();
		CarEqptJDBCDAO ceDAO = new CarEqptJDBCDAO();
		CarFoodJDBCDAO cfDAO = new CarFoodJDBCDAO();
		// General fake data
		int listprice = 1000;
		int price = 800;
		java.sql.Date orderStart = java.sql.Date.valueOf("2020-10-01");
		java.sql.Date orderEnd = java.sql.Date.valueOf("2020-10-03");
		java.sql.Date carStart = java.sql.Date.valueOf("2020-10-04");
		java.sql.Date carEnd = java.sql.Date.valueOf("2020-10-06");
		String om_no = "O000000001";
		// OrderMaster fake data
		OrderMasterVO omVO = omDAO.findByPrimaryKey(om_no);
		
		// OrderCamp existing to test update fake data
		List<OrderCampVO> ocVOList = new ArrayList<OrderCampVO>();
		ocVOList.add(new OrderCampVO(om_no, "C000000001", 2, listprice, price, orderStart, orderEnd));
		ocVOList.add(new OrderCampVO(om_no, "C000000002", 3, listprice, price, orderStart, orderEnd));
		for(OrderCampVO ocVOi : ocVOList) {
			ocDAO.insert(ocVOi);
		}
		// OrderEqpt existing to test update fake data
		List<OrderEqptVO> oeVOList = new ArrayList<OrderEqptVO>();
		oeVOList.add(new OrderEqptVO(om_no, "E000000001", 2, listprice, price, orderStart, orderEnd));
		oeVOList.add(new OrderEqptVO(om_no, "E000000002", 3, listprice, price, orderStart, orderEnd));
		for(OrderEqptVO oeVOi : oeVOList) {
			oeDAO.add(oeVOi);
		}
		// OrderFood existing to test update fake data
		List<OrderFoodVO> ofVOList = new ArrayList<OrderFoodVO>();
		ofVOList.add(new OrderFoodVO(om_no, "F000000004", 2, listprice, price));
		ofVOList.add(new OrderFoodVO(om_no, "F000000005", 3, listprice, price));
		for(OrderFoodVO ofVOi : ofVOList) {
			ofDAO.insert(ofVOi);
		}
		
		// OrderCamp new to insert new fake data
		List<OrderCampVO> ocVOList2 = new ArrayList<OrderCampVO>();
		ocVOList2.add(new OrderCampVO(om_no, "C000000001", 4, listprice-100, price-100, orderStart, orderEnd));
		ocVOList2.add(new OrderCampVO(om_no, "C000000002", 5, listprice-100, price-100, orderStart, orderEnd));

		// OrderEqpt new to insert new fake data
		List<OrderEqptVO> oeVOList2 = new ArrayList<OrderEqptVO>();
		oeVOList2.add(new OrderEqptVO(om_no, "E000000001", 4, listprice-100, price-100, orderStart, orderEnd));
		oeVOList2.add(new OrderEqptVO(om_no, "E000000002", 5, listprice-100, price-100, orderStart, orderEnd));

		// OrderFood new to insert new fake data
		List<OrderFoodVO> ofVOList2 = new ArrayList<OrderFoodVO>();
		ofVOList2.add(new OrderFoodVO(om_no, "F000000004", 4, listprice-100, price-100));
		ofVOList2.add(new OrderFoodVO(om_no, "F000000005", 5, listprice-100, price-100));
//		
//		// Execute
//		omDAO.updateWithDetails(omVO, ocVOList2, oeVOList2, ofVOList2);
//		System.out.println("Static updateWithDetails test end.");
	}
	
}
