package com.ordereqpt.model;

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

public class OrderEqptDAO implements OrderEqptDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
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
	
	private static final String updata_gettime= 
			"UPDATE ORDER_EQPT SET OE_GET = SYSTIMESTAMP "+
			"WHERE OE_NO = ? " ;
	
	private static final String updata_backtime=
			"UPDATE ORDER_EQPT SET OE_BACK = SYSTIMESTAMP, OE_REQTY = ? "+
			"WHERE OE_NO = ? " ;
	@Override // 代表就是要新增的資料
	// Edit by Yen-Fu Chen
	public void add(OrderEqptVO orderEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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
			con = ds.getConnection();
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

	@Override
	public List<OrderEqptVO> getOrderEqptsByOmno(String om_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderEqptVO> orderEqptVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
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
	
	@Override
	public List<OrderEqptVO> getActiveOrderEqptsByOmno(String om_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OrderEqptVO> orderEqptVOList = new ArrayList<>();

		try {
			con = ds.getConnection();
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
	
	@Override
	public void updatagettime(OrderEqptVO orderEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata_gettime);

			pstmt.setString(1, orderEqptVO.getOe_no());
			
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
	public void updatabacktime(OrderEqptVO orderEqptVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(updata_backtime);

			pstmt.setInt(1, orderEqptVO.getOe_reqty());
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
	
}