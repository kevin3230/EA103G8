package com.orderfood.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderFoodDAO implements OrderFoodDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = 
			"INSERT INTO order_food (of_no, of_foodno, of_omno, of_qty, of_listprice, of_price) VALUES('OF' || LPAD(SEQ_OFNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT * FROM order_food ORDER BY of_omno, of_foodno";
	private static final String GET_ONE_STMT = 
			"SELECT * FROM order_food WHERE of_no = ?";
	private static final String DELETE = 
			"DELETE FROM order_food WHERE of_no = ?";
	private static final String UPDATE = 
			"UPDATE order_food SET of_stat=?, of_change=SYSTIMESTAMP WHERE of_no = ?";
	
	// Author Jeff
	private static final String SQL_INSERT = 
			"INSERT INTO order_food(of_no, of_foodno, of_omno, of_qty, of_listprice, of_price) VALUES('OF' || LPAD(SEQ_OCNO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?)";

	private static final String GET_BYOMNO = "SELECT * FROM ORDER_FOOD WHERE OF_OMNO = ?";
	//新增by李承璋
	private static final String GET_FOOD_STATIC = 
			"SELECT TO_CHAR(oc.oc_start, 'yyyy-mm-dd') AS startdate, food.food_name AS foodname , SUM(order_food.of_qty) AS qty FROM order_food " + 
			"JOIN order_master om on order_food.of_omno = om.om_no " + 
			"JOIN food on order_food.of_foodno = food.food_no " + 
			"JOIN (SELECT oc_omno , oc_start FROM order_camp GROUP BY oc_omno , oc_start) oc ON order_food.of_omno = oc.oc_omno " + 
			"WHERE om_vdno = ? AND order_food.of_stat = 1 " + 
			"AND oc.oc_start BETWEEN ? AND ? " + 
			"GROUP BY oc.oc_start, food.food_name " + 
			"ORDER BY oc.oc_start";
	//新增by李承璋
	@Override
	public void insert(OrderFoodVO orderfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, orderfoodVO.getOf_foodno());
			pstmt.setString(2, orderfoodVO.getOf_omno());
			pstmt.setInt(3, orderfoodVO.getOf_qty());
			pstmt.setInt(4, orderfoodVO.getOf_listprice());
			pstmt.setInt(5, orderfoodVO.getOf_price());
			
			pstmt.executeUpdate();
			
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
	public void update(OrderFoodVO orderfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, orderfoodVO.getOf_stat());
			pstmt.setString(2, orderfoodVO.getOf_no());
			pstmt.executeUpdate();
			
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
	public void delete(String of_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, of_no);
			pstmt.executeUpdate();
			
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
	public OrderFoodVO findByPrimaryKey(String of_no) {
		
		OrderFoodVO orderfoodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, of_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				orderfoodVO = new OrderFoodVO();
				orderfoodVO.setOf_no(rs.getString("of_no"));
				orderfoodVO.setOf_foodno(rs.getString("of_foodno"));
				orderfoodVO.setOf_omno(rs.getString("of_omno"));
				orderfoodVO.setOf_qty(rs.getInt("of_qty"));
				orderfoodVO.setOf_listprice(rs.getInt("of_listprice"));
				orderfoodVO.setOf_price(rs.getInt("of_price"));
				orderfoodVO.setOf_stat(rs.getInt("of_stat"));
				orderfoodVO.setOf_change(rs.getTimestamp("of_change"));
				orderfoodVO.setOf_estbl(rs.getTimestamp("of_estbl"));
			}
			
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
		return orderfoodVO;
	}
	
	@Override
	public List<OrderFoodVO> getAll() {
		
		List<OrderFoodVO> list = new ArrayList<OrderFoodVO>();
		OrderFoodVO orderfoodVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderfoodVO = new OrderFoodVO();
				orderfoodVO.setOf_no(rs.getString("of_no"));
				orderfoodVO.setOf_foodno(rs.getString("of_foodno"));
				orderfoodVO.setOf_omno(rs.getString("of_omno"));
				orderfoodVO.setOf_qty(rs.getInt("of_qty"));
				orderfoodVO.setOf_listprice(rs.getInt("of_listprice"));
				orderfoodVO.setOf_price(rs.getInt("of_price"));
				orderfoodVO.setOf_stat(rs.getInt("of_stat"));
				orderfoodVO.setOf_change(rs.getTimestamp("of_change"));
				orderfoodVO.setOf_estbl(rs.getTimestamp("of_estbl"));
				list.add(orderfoodVO);
				
			}
			
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
	public void insert(OrderFoodVO orderFoodVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setString(1, orderFoodVO.getOf_foodno());
			pstmt.setString(2, orderFoodVO.getOf_omno());
			pstmt.setInt(3, orderFoodVO.getOf_qty());
			pstmt.setInt(4, orderFoodVO.getOf_listprice());
			pstmt.setInt(5, orderFoodVO.getOf_price());
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.printf("OrderFoodDAO insert with OrderMaster_%s failed. \n", orderFoodVO.getOf_omno());
				throw new RuntimeException("OrderFoodDAO insertWithDetails SQLException " + se.getMessage());
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
		}
	}

	// Author Jeff
	@Override
	public void update(OrderFoodVO orderFoodVO, Connection con) {

		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, orderFoodVO.getOf_stat());
			pstmt.setString(2, orderFoodVO.getOf_no());
			pstmt.executeUpdate();
			
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.printf("OrderFoodDAO update with OrderMaster_%s failed. \n", orderFoodVO.getOf_omno());
				throw new RuntimeException("OrderFoodDAO updateWithDetails SQLException " + se.getMessage());
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
		}
	}
	
	@Override
	public List<OrderFoodVO> getOrderFoodsByOmno(String om_no) {
		List<OrderFoodVO> list = new ArrayList<OrderFoodVO>();
		OrderFoodVO orderfoodVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYOMNO);
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderfoodVO = new OrderFoodVO();
				orderfoodVO.setOf_no(rs.getString("of_no"));
				orderfoodVO.setOf_foodno(rs.getString("of_foodno"));
				orderfoodVO.setOf_omno(rs.getString("of_omno"));
				orderfoodVO.setOf_qty(rs.getInt("of_qty"));
				orderfoodVO.setOf_listprice(rs.getInt("of_listprice"));
				orderfoodVO.setOf_price(rs.getInt("of_price"));
				orderfoodVO.setOf_stat(rs.getInt("of_stat"));
				orderfoodVO.setOf_change(rs.getTimestamp("of_change"));
				orderfoodVO.setOf_estbl(rs.getTimestamp("of_estbl"));
				list.add(orderfoodVO);
				
			}
			
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
	//新增by李承璋
	@Override
	public List<Map<String, String>> getFoodStatic(String vd_no, java.sql.Timestamp oc_start, java.sql.Timestamp oc_end) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FOOD_STATIC);
			pstmt.setString(1, vd_no);
			pstmt.setTimestamp(2, oc_start);
			pstmt.setTimestamp(3, oc_end);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				map = new HashMap<String, String>();
				map.put("startDate", rs.getString("startdate"));
				map.put("foodName", rs.getString("foodname"));
				map.put("foodQty", rs.getString("qty"));
				list.add(map);
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
	//新增by李承璋

	@Override
	public void delete(OrderFoodVO orderFoodVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(DELETE);
			pstmt.setString(1, orderFoodVO.getOf_no());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("orderFoodDAO delete with OrderMaster_%s failed. \n", orderFoodVO.getOf_omno());
				throw new RuntimeException("orderFoodDAO updateWithDetails SQLException " + e.getMessage());
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
}
