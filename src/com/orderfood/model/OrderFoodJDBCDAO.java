package com.orderfood.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class OrderFoodJDBCDAO implements OrderFoodDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
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
	
	@Override
	public void insert(OrderFoodVO orderfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, orderfoodVO.getOf_foodno());
			pstmt.setString(2, orderfoodVO.getOf_omno());
			pstmt.setInt(3, orderfoodVO.getOf_qty());
			pstmt.setInt(4, orderfoodVO.getOf_listprice());
			pstmt.setInt(5, orderfoodVO.getOf_price());
			
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
	public void update(OrderFoodVO orderfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, orderfoodVO.getOf_stat());
			pstmt.setString(2, orderfoodVO.getOf_no());
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
	public void delete(String of_no) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, of_no);
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
	public OrderFoodVO findByPrimaryKey(String of_no) {
		
		OrderFoodVO orderfoodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	
	public static void main(String[] args) {
		OrderFoodJDBCDAO ofDAO = new OrderFoodJDBCDAO(); 
//		// insert()
//		OrderFoodVO ofVO1 = new OrderFoodVO();
//		ofVO1.setOf_omno("O000000011");
//		ofVO1.setOf_foodno("F000000005");
//		ofVO1.setOf_qty(10);
//		ofVO1.setOf_listprice(1000);
//		ofVO1.setOf_price(800);
//		ofDAO.insert(ofVO1);
		
//		// update()
//		OrderFoodVO ofVO2 = new OrderFoodVO();
//		ofVO2.setOf_no("OF00000010");
//		ofVO2.setOf_stat(0);
//		ofDAO.update(ofVO2);
		
//		// delete()
//		OrderFoodVO ofVO3 = new OrderFoodVO();
//		ofDAO.delete("OF00000010");
		
//		// findByPrimaryKey()
//		OrderFoodVO ofVO4 = ofDAO.findByPrimaryKey("OF00000002");
//		System.out.println("OF_NO : " + ofVO4.getOf_no());
//		System.out.println("OF_OMNO : " + ofVO4.getOf_omno());
//		System.out.println("OF_FOODNO : " + ofVO4.getOf_foodno());
//		System.out.println("OF_QTY : " + ofVO4.getOf_qty());
//		System.out.println("OF_LISTPRICE : " + ofVO4.getOf_listprice());
//		System.out.println("OF_PRICE : " + ofVO4.getOf_price());
//		System.out.println("OF_STAT : " + ofVO4.getOf_stat());
//		System.out.println("OF_CHANGE : " + ofVO4.getOf_change());
//		System.out.println("OF_ESTBL : " + ofVO4.getOf_estbl());
		
//		// getAll()
//		List<OrderFoodVO> ofVOList = ofDAO.getAll();
//		for(OrderFoodVO ofVOi : ofVOList) {
//			System.out.println("====================================");
//			System.out.println("OF_NO : " + ofVOi.getOf_no());
//			System.out.println("OF_OMNO : " + ofVOi.getOf_omno());
//			System.out.println("OF_FOODNO : " + ofVOi.getOf_foodno());
//			System.out.println("OF_QTY : " + ofVOi.getOf_qty());
//			System.out.println("OF_LISTPRICE : " + ofVOi.getOf_listprice());
//			System.out.println("OF_PRICE : " + ofVOi.getOf_price());
//			System.out.println("OF_STAT : " + ofVOi.getOf_stat());
//			System.out.println("OF_CHANGE : " + ofVOi.getOf_change());
//			System.out.println("OF_ESTBL : " + ofVOi.getOf_estbl());
//		}
		
		// getOrderFoodsByOmno()
		String om_no = "O000000001";
		List<OrderFoodVO> ofVOList = ofDAO.getOrderFoodsByOmno(om_no);
		for(OrderFoodVO ofVOi : ofVOList) {
			System.out.println("====================================");
			System.out.println("OF_NO : " + ofVOi.getOf_no());
			System.out.println("OF_OMNO : " + ofVOi.getOf_omno());
			System.out.println("OF_FOODNO : " + ofVOi.getOf_foodno());
			System.out.println("OF_QTY : " + ofVOi.getOf_qty());
			System.out.println("OF_LISTPRICE : " + ofVOi.getOf_listprice());
			System.out.println("OF_PRICE : " + ofVOi.getOf_price());
			System.out.println("OF_STAT : " + ofVOi.getOf_stat());
			System.out.println("OF_CHANGE : " + ofVOi.getOf_change());
			System.out.println("OF_ESTBL : " + ofVOi.getOf_estbl());
		}
		
	}

	@Override
	public List<Map<String, String>> getFoodStatic(String vd_no, Timestamp oc_start, Timestamp oc_end) {
		// TODO Auto-generated method stub
		return null;
	}


}
