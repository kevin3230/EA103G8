package com.food.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FoodDAO implements FoodDAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FOOD (FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC) VALUES ('F' || LPAD(SEQ_FOODNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD ORDER BY FOOD_NO";
	private static final String GET_ONE_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD WHERE FOOD_NO = ?";
//	private static final String REAL_DELETE_STMT = "DELETE FROM FOOD WHERE FOOD_NO = ?";	
	private static final String UPDATE_STMT = "UPDATE FOOD SET FOOD_VDNO=?, FOOD_NAME=?, FOOD_FTNO=?, FOOD_PRICE=?, FOOD_INTRO=?, FOOD_STAT=?, FOOD_PIC=? WHERE FOOD_NO = ?";
	private static final String DELETE_STMT = "UPDATE FOOD SET FOOD_STAT=? WHERE FOOD_NO = ?"; // 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	private static final String UPDATE_STAT_STMT = "UPDATE FOOD SET FOOD_STAT=? WHERE FOOD_NO = ?";
	//新增by李承璋
	private static final String GET_ONEVENDOR_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD WHERE FOOD_VDNO = ? AND FOOD_STAT = ? AND FOOD_FTNO = ? ORDER BY FOOD_NO";
	private static final String GET_VENDORFOODTYPE_STMT = "SELECT DISTINCT FOOD_FTNO FROM (SELECT FOOD_FTNO FROM FOOD WHERE FOOD_VDNO = ? AND FOOD_STAT = ?) ORDER BY FOOD_FTNO";
	//新增by李承璋
	private static final String GET_MULTI_BYVDNO_STMT = "SELECT * FROM FOOD WHERE FOOD_VDNO = ? ORDER BY FOOD_NO";
	
	private static final String GET_ALLCT_BYVDNO = "SELECT FOOD_FTNO FROM FOOD WHERE FOOD_VDNO = ? AND FOOD_STAT >= 0 ORDER BY FOOD_NO"; 
	private static final String GET_MULTI_BYVDNOFTNO_STMT = "SELECT * FROM FOOD WHERE FOOD_VDNO = ? AND FOOD_FTNO = ? AND FOOD_STAT >= 0 ORDER BY FOOD_NO";
	
	@Override
	public void insert(FoodVO foodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, foodVO.getFood_vdno());
			pstmt.setString(2, foodVO.getFood_name());
			pstmt.setString(3, foodVO.getFood_ftno());
			pstmt.setInt(4, foodVO.getFood_price());
			pstmt.setString(5, foodVO.getFood_intro());
			pstmt.setInt(6, foodVO.getFood_stat());
			pstmt.setBytes(7, foodVO.getFood_pic());

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
	public void update(FoodVO foodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, foodVO.getFood_vdno());
			pstmt.setString(2, foodVO.getFood_name());
			pstmt.setString(3,foodVO.getFood_ftno());
			pstmt.setInt(4, foodVO.getFood_price());
			pstmt.setString(5, foodVO.getFood_intro());
			pstmt.setInt(6, foodVO.getFood_stat());
			pstmt.setBytes(7, foodVO.getFood_pic());
			pstmt.setString(8, foodVO.getFood_no());

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
//	public void delete(String food_no) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(REAL_DELETE_STMT);
//			
//			pstmt.setString(1, food_no);			
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
	public void delete(String food_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, -1);
			pstmt.setString(2, food_no);
			
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
	public FoodVO findByPrimaryKey(String food_no) {

		FoodVO foodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, food_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFood_no(rs.getString("FOOD_NO"));
				foodVO.setFood_vdno(rs.getString("FOOD_VDNO"));
				foodVO.setFood_name(rs.getString("FOOD_NAME"));
				foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
				foodVO.setFood_price(rs.getInt("FOOD_PRICE"));
				foodVO.setFood_intro(rs.getString("FOOD_INTRO"));
				foodVO.setFood_stat(rs.getInt("FOOD_STAT"));			
				foodVO.setFood_pic(rs.getBytes("FOOD_PIC"));
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
		return foodVO;
	}

	@Override
	public List<FoodVO> getAll() {
		
		List<FoodVO> list = new ArrayList<FoodVO>();
		FoodVO foodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFood_no(rs.getString("FOOD_NO"));
				foodVO.setFood_vdno(rs.getString("FOOD_VDNO"));
				foodVO.setFood_name(rs.getString("FOOD_NAME"));
				foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
				foodVO.setFood_price(rs.getInt("FOOD_PRICE"));
				foodVO.setFood_intro(rs.getString("FOOD_INTRO"));
				foodVO.setFood_stat(rs.getInt("FOOD_STAT"));			
				foodVO.setFood_pic(rs.getBytes("FOOD_PIC"));
				
				list.add(foodVO);
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

	//新增by李承璋
		@Override
		public List<FoodVO> getOneVendor(String food_vdno, Integer food_stat, String food_ftno) {
			
			List<FoodVO> list = new ArrayList<FoodVO>();
			FoodVO foodVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONEVENDOR_STMT);
				pstmt.setString(1, food_vdno);
				pstmt.setInt(2, food_stat);
				pstmt.setString(3, food_ftno);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					foodVO = new FoodVO();
					foodVO.setFood_no(rs.getString("FOOD_NO"));
					foodVO.setFood_vdno(rs.getString("FOOD_VDNO"));
					foodVO.setFood_name(rs.getString("FOOD_NAME"));
					foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
					foodVO.setFood_price(rs.getInt("FOOD_PRICE"));
					foodVO.setFood_intro(rs.getString("FOOD_INTRO"));
					foodVO.setFood_stat(rs.getInt("FOOD_STAT"));			
					foodVO.setFood_pic(rs.getBytes("FOOD_PIC"));
					
					list.add(foodVO);
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
		public List<FoodVO> getVendorFoodType(String food_vdno, Integer food_stat) {
			
			List<FoodVO> list = new ArrayList<FoodVO>();
			FoodVO foodVO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_VENDORFOODTYPE_STMT);
				pstmt.setString(1, food_vdno);
				pstmt.setInt(2, food_stat);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					foodVO = new FoodVO();
					foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
					list.add(foodVO);
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
		//新增by李承璋
		
	@Override
	public void updateFoodStatTo2(String food_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 2);
			pstmt.setString(2, food_no);
			
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
	public void updateFoodStatTo1(String food_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STAT_STMT);
			
			pstmt.setInt(1, 1);
			pstmt.setString(2, food_no);
			
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
	public List<FoodVO> getFoodsByVdno(String vd_no) {

		List<FoodVO> list = new ArrayList<FoodVO>();
		FoodVO foodVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MULTI_BYVDNO_STMT);
			
			pstmt.setString(1, vd_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFood_no(rs.getString("FOOD_NO"));
				foodVO.setFood_vdno(rs.getString("FOOD_VDNO"));
				foodVO.setFood_name(rs.getString("FOOD_NAME"));
				foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
				foodVO.setFood_price(rs.getInt("FOOD_PRICE"));
				foodVO.setFood_intro(rs.getString("FOOD_INTRO"));
				foodVO.setFood_stat(rs.getInt("FOOD_STAT"));			
				foodVO.setFood_pic(rs.getBytes("FOOD_PIC"));
				
				list.add(foodVO);
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
	public Set<String> getAllFt_no(String vd_no) {
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
				String food_ftno = rs.getString("FOOD_FTNO");
				set.add(food_ftno);
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
	public List<FoodVO> getFoodsByVdnoFtno(String vd_no, String ft_no) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FoodVO foodVO = null;
		List<FoodVO> list = new ArrayList<FoodVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_MULTI_BYVDNOFTNO_STMT);
			
			pstmt.setString(1, vd_no);
			pstmt.setString(2, ft_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodVO = new FoodVO();
				foodVO.setFood_no(rs.getString("FOOD_NO"));
				foodVO.setFood_vdno(rs.getString("FOOD_VDNO"));
				foodVO.setFood_name(rs.getString("FOOD_NAME"));
				foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
				foodVO.setFood_price(rs.getInt("FOOD_PRICE"));
				foodVO.setFood_intro(rs.getString("FOOD_INTRO"));
				foodVO.setFood_stat(rs.getInt("FOOD_STAT"));			
				foodVO.setFood_pic(rs.getBytes("FOOD_PIC"));
				list.add(foodVO);
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
