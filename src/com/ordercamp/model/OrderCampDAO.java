package com.ordercamp.model;

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

public class OrderCampDAO implements OrderCampDAO_interface {
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
			"INSERT INTO ORDER_CAMP(OC_NO, OC_CAMPNO, OC_OMNO, OC_QTY, OC_LISTPRICE, OC_PRICE, OC_START, OC_END)"
			+ " VALUES ('OC' || LPAD(SEQ_OCNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE ORDER_CAMP SET OC_STAT = ?, OC_CHANGE = SYSTIMESTAMP"
			+ " WHERE OC_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM ORDER_CAMP WHERE OC_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT *"
			+ " FROM ORDER_CAMP WHERE OC_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT *"
			+ " FROM ORDER_CAMP ORDER BY OC_OMNO, OC_CAMPNO";
	
	private static final String GET_MULTI_BYCAMPNO_STMT =
			"SELECT *"
			+ " FROM ORDER_CAMP WHERE OC_CAMPNO = ? ORDER BY OC_OMNO, OC_CAMPNO";
	
	private static final String GET_MULTI_BYOMNO_STMT =
			"SELECT *"
			+ " FROM ORDER_CAMP WHERE OC_OMNO = ? ORDER BY OC_OMNO, OC_CAMPNO";
	
	private static final String GET_ACTIVE_MULTI_BYOMNO_STMT =
			"SELECT *"
			+ " FROM ORDER_CAMP WHERE OC_STAT = 1 AND OC_OMNO = ? ORDER BY OC_OMNO, OC_CAMPNO";
	
	@Override
	public void insert(OrderCampVO orderCampVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, orderCampVO.getOc_campno());
			pstmt.setString(2, orderCampVO.getOc_omno());
			pstmt.setInt(3, orderCampVO.getOc_qty());
			pstmt.setInt(4, orderCampVO.getOc_listprice());
			pstmt.setInt(5, orderCampVO.getOc_price());
			pstmt.setDate(6, orderCampVO.getOc_start());
			pstmt.setDate(7, orderCampVO.getOc_end());
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
	public void update(OrderCampVO orderCampVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setInt(1, orderCampVO.getOc_stat());
			pstmt.setString(2, orderCampVO.getOc_no());
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
	public OrderCampVO findByPrimaryKey(String oc_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderCampVO orderCampVO = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, oc_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				orderCampVO = new OrderCampVO();
				orderCampVO.setOc_no(rs.getString(1));
				orderCampVO.setOc_campno(rs.getString(2));
				orderCampVO.setOc_omno(rs.getString(3));
				orderCampVO.setOc_qty(rs.getInt(4));
				orderCampVO.setOc_listprice(rs.getInt(5));
				orderCampVO.setOc_price(rs.getInt(6));
				orderCampVO.setOc_stat(rs.getInt(7));
				orderCampVO.setOc_start(rs.getDate(8));
				orderCampVO.setOc_end(rs.getDate(9));
				orderCampVO.setOc_change(rs.getDate(10));
				orderCampVO.setOc_estbl(rs.getDate(11));
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
		return orderCampVO;
	}

	@Override
	public List<OrderCampVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderCampVO orderCampVO = null;
		List<OrderCampVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
					
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderCampVO = new OrderCampVO();
				orderCampVO.setOc_no(rs.getString(1));
				orderCampVO.setOc_campno(rs.getString(2));
				orderCampVO.setOc_omno(rs.getString(3));
				orderCampVO.setOc_qty(rs.getInt(4));
				orderCampVO.setOc_listprice(rs.getInt(5));
				orderCampVO.setOc_price(rs.getInt(6));
				orderCampVO.setOc_stat(rs.getInt(7));
				orderCampVO.setOc_start(rs.getDate(8));
				orderCampVO.setOc_end(rs.getDate(9));
				orderCampVO.setOc_change(rs.getDate(10));
				orderCampVO.setOc_estbl(rs.getDate(11));
				list.add(orderCampVO);
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
	public List<OrderCampVO> getOrderCampsByCampno(String camp_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderCampVO orderCampVO = null;
		List<OrderCampVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_MULTI_BYCAMPNO_STMT);
			
			pstmt.setString(1, camp_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderCampVO = new OrderCampVO();
				orderCampVO.setOc_no(rs.getString(1));
				orderCampVO.setOc_campno(rs.getString(2));
				orderCampVO.setOc_omno(rs.getString(3));
				orderCampVO.setOc_qty(rs.getInt(4));
				orderCampVO.setOc_listprice(rs.getInt(5));
				orderCampVO.setOc_price(rs.getInt(6));
				orderCampVO.setOc_stat(rs.getInt(7));
				orderCampVO.setOc_start(rs.getDate(8));
				orderCampVO.setOc_end(rs.getDate(9));
				orderCampVO.setOc_change(rs.getDate(10));
				orderCampVO.setOc_estbl(rs.getDate(11));
				list.add(orderCampVO);
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
	public List<OrderCampVO> getOrderCampsByOmno(String om_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderCampVO orderCampVO = null;
		List<OrderCampVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_MULTI_BYOMNO_STMT);
			
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderCampVO = new OrderCampVO();
				orderCampVO.setOc_no(rs.getString(1));
				orderCampVO.setOc_campno(rs.getString(2));
				orderCampVO.setOc_omno(rs.getString(3));
				orderCampVO.setOc_qty(rs.getInt(4));
				orderCampVO.setOc_listprice(rs.getInt(5));
				orderCampVO.setOc_price(rs.getInt(6));
				orderCampVO.setOc_stat(rs.getInt(7));
				orderCampVO.setOc_start(rs.getDate(8));
				orderCampVO.setOc_end(rs.getDate(9));
				orderCampVO.setOc_change(rs.getDate(10));
				orderCampVO.setOc_estbl(rs.getDate(11));
				list.add(orderCampVO);
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
	public List<OrderCampVO> getActiveOrderCampsByOmno(String om_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderCampVO orderCampVO = null;
		List<OrderCampVO> list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ACTIVE_MULTI_BYOMNO_STMT);
			
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderCampVO = new OrderCampVO();
				orderCampVO.setOc_no(rs.getString(1));
				orderCampVO.setOc_campno(rs.getString(2));
				orderCampVO.setOc_omno(rs.getString(3));
				orderCampVO.setOc_qty(rs.getInt(4));
				orderCampVO.setOc_listprice(rs.getInt(5));
				orderCampVO.setOc_price(rs.getInt(6));
				orderCampVO.setOc_stat(rs.getInt(7));
				orderCampVO.setOc_start(rs.getDate(8));
				orderCampVO.setOc_end(rs.getDate(9));
				orderCampVO.setOc_change(rs.getDate(10));
				orderCampVO.setOc_estbl(rs.getDate(11));
				list.add(orderCampVO);
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
	
	// Author Jeff
	@Override
	public void insert(OrderCampVO orderCampVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, orderCampVO.getOc_campno());
			pstmt.setString(2, orderCampVO.getOc_omno());
			pstmt.setInt(3, orderCampVO.getOc_qty());
			pstmt.setInt(4, orderCampVO.getOc_listprice());
			pstmt.setInt(5, orderCampVO.getOc_price());
			pstmt.setDate(6, orderCampVO.getOc_start());
			pstmt.setDate(7, orderCampVO.getOc_end());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("OrderCampDAO insert with OrderMaster_%s failed. \n", orderCampVO.getOc_omno());
				throw new RuntimeException("OrderCampDAO insertWithDetails SQLException " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Author Jeff
	@Override
	public void update(OrderCampVO orderCampVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, orderCampVO.getOc_stat());
			pstmt.setString(2, orderCampVO.getOc_no());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("OrderCampDAO update with OrderMaster_%s failed. \n", orderCampVO.getOc_omno());
				throw new RuntimeException("OrderCampDAO updateWithDetails SQLException " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void delete(OrderCampVO orderCampVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(DELETE_STMT);
			pstmt.setString(1, orderCampVO.getOc_no());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("OrderCampDAO delete with OrderMaster_%s failed. \n", orderCampVO.getOc_omno());
				throw new RuntimeException("OrderCampDAO updateWithDetails SQLException " + e.getMessage());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
//	public static void main(String[] args) {
//		OrderCampDAO dao = new OrderCampDAO();
//		
////		// 新增
////		OrderCampVO orderCampVO1 = new OrderCampVO();
////		orderCampVO1.setOc_campno("C000000002");
////		orderCampVO1.setOc_omno("O000000012");
////		orderCampVO1.setOc_qty(2);
////		orderCampVO1.setOc_listprice(1000);
////		orderCampVO1.setOc_price(800);
////		orderCampVO1.setOc_start(Date.valueOf("2020-08-02"));
////		orderCampVO1.setOc_end(Date.valueOf("2020-08-04"));
////		dao.insert(orderCampVO1);
//	
//		// 修改
////		OrderCampVO orderCampVO2 = new OrderCampVO();
////		orderCampVO2.setOc_stat(0);
////		orderCampVO2.setOc_no("OC00000005");
////		dao.update(orderCampVO2);
////
////		// 查詢特定營位訂單明細(oc_campno, oc_omno)
////		OrderCampVO orderCampVO3 = dao.findByPrimaryKey("OC00000005");
////		System.out.println(orderCampVO3.getOc_no());
////		System.out.println(orderCampVO3.getOc_campno());
////		System.out.println(orderCampVO3.getOc_omno());
////		System.out.println(orderCampVO3.getOc_qty());
////		System.out.println(orderCampVO3.getOc_listprice());
////		System.out.println(orderCampVO3.getOc_price());
////		System.out.println(orderCampVO3.getOc_stat());
////		System.out.println(orderCampVO3.getOc_start());
////		System.out.println(orderCampVO3.getOc_end());
////		System.out.println(orderCampVO3.getOc_change());
////		System.out.println(orderCampVO3.getOc_estbl());
////		System.out.println("====================");
////		
////		// 查詢全部
////		List<OrderCampVO> list = dao.getAll();
////		for (OrderCampVO orderCampVO : list) {
////			System.out.println(orderCampVO.getOc_no());
////			System.out.println(orderCampVO.getOc_campno());
////			System.out.println(orderCampVO.getOc_omno());
////			System.out.println(orderCampVO.getOc_qty());
////			System.out.println(orderCampVO.getOc_listprice());
////			System.out.println(orderCampVO.getOc_price());
////			System.out.println(orderCampVO.getOc_stat());
////			System.out.println(orderCampVO.getOc_start());
////			System.out.println(orderCampVO.getOc_end());
////			System.out.println(orderCampVO.getOc_change());
////			System.out.println(orderCampVO.getOc_estbl());
////			System.out.println();
////		}
////		System.out.println("====================");
////
//		// 查詢特定營位(camp_no)的營位訂單明細
////		List<OrderCampVO> list2 = dao.getOrderCampsByCampno("C000000001");
////		for (OrderCampVO orderCampVO : list2) {
////			System.out.println(orderCampVO.getOc_no());
////			System.out.println(orderCampVO.getOc_campno());
////			System.out.println(orderCampVO.getOc_omno());
////			System.out.println(orderCampVO.getOc_qty());
////			System.out.println(orderCampVO.getOc_listprice());
////			System.out.println(orderCampVO.getOc_price());
////			System.out.println(orderCampVO.getOc_stat());
////			System.out.println(orderCampVO.getOc_start());
////			System.out.println(orderCampVO.getOc_end());
////			System.out.println(orderCampVO.getOc_change());
////			System.out.println(orderCampVO.getOc_estbl());
////			System.out.println();
////		}
////		System.out.println("====================");
////		
////		// 查詢特定主訂單(om_no)的營位訂單明細
////		List<OrderCampVO> list3 = dao.getOrderCampsByOmno("O000000012");
////		for (OrderCampVO orderCampVO : list3) {
////			System.out.println(orderCampVO.getOc_campno());
////			System.out.println(orderCampVO.getOc_omno());
////			System.out.println(orderCampVO.getOc_qty());
////			System.out.println(orderCampVO.getOc_listprice());
////			System.out.println(orderCampVO.getOc_price());
////			System.out.println(orderCampVO.getOc_stat());
////			System.out.println(orderCampVO.getOc_start());
////			System.out.println(orderCampVO.getOc_end());
////			System.out.println(orderCampVO.getOc_change());
////			System.out.println(orderCampVO.getOc_estbl());
////			System.out.println();
////		}
////		System.out.println("====================");
//	}

}