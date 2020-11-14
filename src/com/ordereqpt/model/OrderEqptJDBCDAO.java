package com.ordereqpt.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderEqptJDBCDAO implements OrderEqptDAO_interface {

	// 載入驅動
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 載入資料庫
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";

	// Edit by Yen-Fu Chen
	private static final String INSERT_STMT =
			"INSERT INTO ORDER_EQPT(OE_NO, OE_EQPTNO, OE_OMNO, OE_QTY, OE_LISTPRICE,"
			+ " OE_PRICE, OE_EXPGET, OE_EXPBACK)"
			+ " VALUES ('OE' || LPAD(SEQ_OENO.NEXTVAL, 8, '0'), ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String FIND_BY_OE_NO = "SELECT * FROM ORDER_EQPT WHERE OE_NO = ?";
	private static final String GET_ALL = "SELECT * FROM ORDER_EQPT ORDER BY OE_NO";
	private static final String delete = "DELETE FROM ORDER_EQPT WHERE OE_NO = ?";

	// Edit by Yen-Fu Chen, 需要另建兩個指令修改"領取時間"、"歸還時間及數量"
	private static final String updata =
			"UPDATE ORDER_EQPT SET OE_STAT = ?, OE_CHANGE = CURRENT_TIMESTAMP"
			+ " WHERE OE_NO = ?";
	
	// Author Jeff
	private static final String GET_BYOMNO = "SELECT * FROM ORDER_EQPT WHERE OE_OMNO = ?";
	private static final String GETACTIVE_BYOMNO = "SELECT * FROM ORDER_EQPT WHERE OE_STAT = 1 AND OE_OMNO = ?";
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}

	}

	@Override // 代表就是要新增的資料
	// Edit by Yen-Fu Chen
	public void add(OrderEqptVO orderEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, orderEqptVO.getOe_eqptno());
			pstmt.setString(2, orderEqptVO.getOe_omno());
			pstmt.setInt(3, orderEqptVO.getOe_qty());
			pstmt.setInt(4, orderEqptVO.getOe_listprice());
			pstmt.setInt(5, orderEqptVO.getOe_price());
			pstmt.setDate(6, orderEqptVO.getOe_expget());
			pstmt.setDate(7, orderEqptVO.getOe_expback());	
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
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

	}

	@Override
	// Edit by Yen-Fu Chen
	public void updata(OrderEqptVO orderEqptVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(updata);

			pstmt.setInt(1, orderEqptVO.getOe_stat());
			pstmt.setString(2, orderEqptVO.getOe_no());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public void delete(String oe_no) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(delete);

			pstmt.setString(1, oe_no);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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
	public OrderEqptVO findByPrimaryKey(String oe_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OrderEqptVO orderEqptVO = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(FIND_BY_OE_NO);
			pstmt.setString(1, oe_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				orderEqptVO = new OrderEqptVO();
				orderEqptVO.setOe_no(rs.getString("OE_NO"));
				orderEqptVO.setOe_eqptno(rs.getString("OE_EQPTNO"));
				orderEqptVO.setOe_omno(rs.getString("OE_OMNO"));
				orderEqptVO.setOe_qty(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_listprice(rs.getInt("OE_LISTPRICE"));
				orderEqptVO.setOe_price(rs.getInt("OE_PRICE"));
				orderEqptVO.setOe_stat(rs.getInt("OE_STAT"));
				orderEqptVO.setOe_expget(rs.getDate("OE_EXPGET"));
				orderEqptVO.setOe_expback(rs.getDate("OE_EXPBACK"));
				orderEqptVO.setOe_get(rs.getTimestamp("OE_GET"));
				orderEqptVO.setOe_back(rs.getTimestamp("OE_BACK"));
				orderEqptVO.setOe_reqty(rs.getInt("OE_REQTY"));
				orderEqptVO.setOe_change(rs.getTimestamp("OE_CHANGE"));
				orderEqptVO.setOe_estbl(rs.getTimestamp("OE_ESTBL"));		
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

		return orderEqptVO;
	}

	@Override
	public List<OrderEqptVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderEqptVO> orderEqptVOList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				OrderEqptVO orderEqptVO = new OrderEqptVO();
				orderEqptVO = new OrderEqptVO();
				orderEqptVO.setOe_no(rs.getString("OE_NO"));
				orderEqptVO.setOe_eqptno(rs.getString("OE_EQPTNO"));
				orderEqptVO.setOe_omno(rs.getString("OE_OMNO"));
				orderEqptVO.setOe_qty(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_listprice(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_price(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_stat(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_expget(rs.getDate("OE_EXPGET"));
				orderEqptVO.setOe_expback(rs.getDate("OE_EXPBACK"));
				orderEqptVO.setOe_get(rs.getTimestamp("OE_GET"));
				orderEqptVO.setOe_back(rs.getTimestamp("OE_BACK"));
				orderEqptVO.setOe_reqty(rs.getInt("OE_REQTY"));
				orderEqptVO.setOe_change(rs.getTimestamp("OE_CHANGE"));
				orderEqptVO.setOe_estbl(rs.getTimestamp("OE_ESTBL"));
				orderEqptVOList.add(orderEqptVO);
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

		return orderEqptVOList;
	}

	// Author Jeff, edit by yfu
	@Override
	public void insert(OrderEqptVO orderEqptVO, Connection con) {

		PreparedStatement pstmt = null;

		try {		
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, orderEqptVO.getOe_eqptno());
			pstmt.setString(2, orderEqptVO.getOe_omno());
			pstmt.setInt(3, orderEqptVO.getOe_qty());
			pstmt.setInt(4, orderEqptVO.getOe_listprice());
			pstmt.setInt(5, orderEqptVO.getOe_price());
			pstmt.setDate(6, orderEqptVO.getOe_expget());
			pstmt.setDate(7, orderEqptVO.getOe_expback());
			
			pstmt.executeUpdate();

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.printf("OrderEqptDAO insert with OrderMaster_%s failed. \n", orderEqptVO.getOe_omno());
				throw new RuntimeException("OrderEqptDAO insertWithDetails SQLException " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
	
	@Override // Author Jeff
	public List<OrderEqptVO> getOrderEqptsByOmno(String om_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderEqptVO> orderEqptVOList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GET_BYOMNO);
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderEqptVO orderEqptVO = new OrderEqptVO();
				orderEqptVO = new OrderEqptVO();
				orderEqptVO.setOe_no(rs.getString("OE_NO"));
				orderEqptVO.setOe_eqptno(rs.getString("OE_EQPTNO"));
				orderEqptVO.setOe_omno(rs.getString("OE_OMNO"));
				orderEqptVO.setOe_qty(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_listprice(rs.getInt("OE_LISTPRICE"));
				orderEqptVO.setOe_price(rs.getInt("OE_PRICE"));
				orderEqptVO.setOe_stat(rs.getInt("OE_STAT"));
				orderEqptVO.setOe_expget(rs.getDate("OE_EXPGET"));
				orderEqptVO.setOe_expback(rs.getDate("OE_EXPBACK"));
				orderEqptVO.setOe_get(rs.getTimestamp("OE_GET"));
				orderEqptVO.setOe_back(rs.getTimestamp("OE_BACK"));
				orderEqptVO.setOe_reqty(rs.getInt("OE_REQTY"));
				orderEqptVO.setOe_change(rs.getTimestamp("OE_CHANGE"));
				orderEqptVO.setOe_estbl(rs.getTimestamp("OE_ESTBL"));
				orderEqptVOList.add(orderEqptVO);
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

		return orderEqptVOList;
	}
	
	@Override // Author Jeff
	public List<OrderEqptVO> getActiveOrderEqptsByOmno(String om_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderEqptVO> orderEqptVOList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(GETACTIVE_BYOMNO);
			pstmt.setString(1, om_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				OrderEqptVO orderEqptVO = new OrderEqptVO();
				orderEqptVO = new OrderEqptVO();
				orderEqptVO.setOe_no(rs.getString("OE_NO"));
				orderEqptVO.setOe_eqptno(rs.getString("OE_EQPTNO"));
				orderEqptVO.setOe_omno(rs.getString("OE_OMNO"));
				orderEqptVO.setOe_qty(rs.getInt("OE_QTY"));
				orderEqptVO.setOe_listprice(rs.getInt("OE_LISTPRICE"));
				orderEqptVO.setOe_price(rs.getInt("OE_PRICE"));
				orderEqptVO.setOe_stat(rs.getInt("OE_STAT"));
				orderEqptVO.setOe_expget(rs.getDate("OE_EXPGET"));
				orderEqptVO.setOe_expback(rs.getDate("OE_EXPBACK"));
				orderEqptVO.setOe_get(rs.getTimestamp("OE_GET"));
				orderEqptVO.setOe_back(rs.getTimestamp("OE_BACK"));
				orderEqptVO.setOe_reqty(rs.getInt("OE_REQTY"));
				orderEqptVO.setOe_change(rs.getTimestamp("OE_CHANGE"));
				orderEqptVO.setOe_estbl(rs.getTimestamp("OE_ESTBL"));
				orderEqptVOList.add(orderEqptVO);
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

		return orderEqptVOList;
	}
	
	// Author Jeff
	@Override
	public void update(OrderEqptVO orderEqptVO, Connection con) {

		PreparedStatement pstmt = null;

		try {		
			pstmt = con.prepareStatement(updata);
			pstmt.setInt(1, orderEqptVO.getOe_stat());
			pstmt.setString(2, orderEqptVO.getOe_no());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			try {
				con.rollback();
				System.out.printf("OrderEqptDAO update with OrderMaster_%s failed. \n", orderEqptVO.getOe_omno());
				throw new RuntimeException("OrderEqptDAO updateWithDetails SQLException " + se.getMessage());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
	}
	
	// Author Jeff
	@Override
	public void delete(OrderEqptVO orderEqptVO, Connection con) {
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(delete);
			pstmt.setString(1, orderEqptVO.getOe_no());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("OrderEqptDAO delete with OrderMaster_%s failed. \n", orderEqptVO.getOe_omno());
				throw new RuntimeException("OrderEqptDAO updateWithDetails SQLException " + e.getMessage());
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
		OrderEqptJDBCDAO dao = new OrderEqptJDBCDAO();

//		//新增測試		
//		OrderEqptVO orderEqptVO1 = new OrderEqptVO();
//		orderEqptVO1.setOe_eqptno("E000000001");
//		orderEqptVO1.setOe_omno("O000000012");
//		orderEqptVO1.setOe_qty(2);
//		orderEqptVO1.setOe_listprice(2000);
//		orderEqptVO1.setOe_price(2000);
//		orderEqptVO1.setOe_expget(java.sql.Date.valueOf("2020-10-01"));
//		orderEqptVO1.setOe_expback(java.sql.Date.valueOf("2020-10-05"));
//		dao.add(orderEqptVO1);

		// 修改測試
//		OrderEqptVO orderEqptVO2 = new OrderEqptVO();
//		orderEqptVO2.setOe_stat(0);
//		orderEqptVO2.setOe_no("OE00000001");
//		dao.updata(orderEqptVO2);

		//刪除測試	
//		dao.delete("OE00000001");

		// 主鍵查詢
//		OrderEqptVO orderEqptVO3 = dao.findByPrimaryKey("OE00000002");
//		System.out.print(orderEqptVO3.getOe_no() + ", ");
//		System.out.print(orderEqptVO3.getOe_eqptno() + ", ");
//		System.out.print(orderEqptVO3.getOe_omno() + ", ");
//		System.out.print(orderEqptVO3.getOe_qty() + ", ");
//		System.out.print(orderEqptVO3.getOe_listprice() + ", ");
//		System.out.print(orderEqptVO3.getOe_price() + ", ");
//		System.out.print(orderEqptVO3.getOe_stat() + ", ");
//		System.out.print(orderEqptVO3.getOe_expget() + ", ");
//		System.out.print(orderEqptVO3.getOe_expback() + ", ");
//		System.out.print(orderEqptVO3.getOe_get() + ", ");
//		System.out.print(orderEqptVO3.getOe_back() + ", ");
//		System.out.print(orderEqptVO3.getOe_reqty() + ", ");
//		System.out.print(orderEqptVO3.getOe_change() + ", ");
//		System.out.print(orderEqptVO3.getOe_estbl()+ "\n");
//		System.out.println();
//		System.out.println("---------------------");

		// 所有查詢
//		List<OrderEqptVO> list = dao.getAll();
//		for (OrderEqptVO orderEqptVO : list) {
//			System.out.print(orderEqptVO.getOe_no() + ", ");
//			System.out.print(orderEqptVO.getOe_eqptno() + ", ");
//			System.out.print(orderEqptVO.getOe_omno() + ", ");
//			System.out.print(orderEqptVO.getOe_qty() + ", ");
//			System.out.print(orderEqptVO.getOe_listprice() + ", ");
//			System.out.print(orderEqptVO.getOe_price() + ", ");
//			System.out.print(orderEqptVO.getOe_stat() + ", ");
//			System.out.print(orderEqptVO.getOe_expget() + ", ");
//			System.out.print(orderEqptVO.getOe_expback() + ", ");
//			System.out.print(orderEqptVO.getOe_get() + ", ");
//			System.out.print(orderEqptVO.getOe_back() + ", ");
//			System.out.print(orderEqptVO.getOe_reqty() + ", ");
//			System.out.print(orderEqptVO.getOe_change() + ", ");
//			System.out.print(orderEqptVO.getOe_estbl()+ "\n");
//			System.out.println();
//			System.out.println("---------------------");
//		}
		
		// getOrderEqptsByOmno
		String om_no = "O000000001";
		List<OrderEqptVO> oeVOList = dao.getOrderEqptsByOmno(om_no);
		for (OrderEqptVO orderEqptVO : oeVOList) {
			System.out.print(orderEqptVO.getOe_no() + ", ");
			System.out.print(orderEqptVO.getOe_eqptno() + ", ");
			System.out.print(orderEqptVO.getOe_omno() + ", ");
			System.out.print(orderEqptVO.getOe_qty() + ", ");
			System.out.print(orderEqptVO.getOe_listprice() + ", ");
			System.out.print(orderEqptVO.getOe_price() + ", ");
			System.out.print(orderEqptVO.getOe_stat() + ", ");
			System.out.print(orderEqptVO.getOe_expget() + ", ");
			System.out.print(orderEqptVO.getOe_expback() + ", ");
			System.out.print(orderEqptVO.getOe_get() + ", ");
			System.out.print(orderEqptVO.getOe_back() + ", ");
			System.out.print(orderEqptVO.getOe_reqty() + ", ");
			System.out.print(orderEqptVO.getOe_change() + ", ");
			System.out.print(orderEqptVO.getOe_estbl()+ "\n");
			System.out.println();
			System.out.println("---------------------");
		}
	}

	@Override
	public void updatagettime(OrderEqptVO orderEqptVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatabacktime(OrderEqptVO orderEqptVO) {
		// TODO Auto-generated method stub
		
	}

}