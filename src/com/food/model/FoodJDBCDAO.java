package com.food.model;

import java.sql.*;
import java.util.*;

public class FoodJDBCDAO implements FoodDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO FOOD (FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC) VALUES ('F' || LPAD(SEQ_FOODNO.NEXTVAL, 9, '0'), ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD ORDER BY FOOD_NO";
	private static final String GET_ONE_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD WHERE FOOD_NO = ?";
//	private static final String REAL_DELETE_STMT = "DELETE FROM FOOD WHERE FOOD_NO = ?";
	private static final String UPDATE_STMT = "UPDATE FOOD SET FOOD_VDNO=?, FOOD_NAME=?, FOOD_FTNO=?, FOOD_PRICE=?, FOOD_INTRO=?, FOOD_STAT=?, FOOD_PIC=? WHERE FOOD_NO = ?";
	private static final String DELETE_STMT = "UPDATE FOOD SET FOOD_STAT=? WHERE FOOD_NO = ?"; // 不行完全刪除資料，僅變更為狀態「-1 刪除」，隱藏起來無法看到
	private static final String UPDATE_STAT_STMT = "UPDATE FOOD SET FOOD_STAT=? WHERE FOOD_NO = ?";
	//新增by李承璋
		private static final String GET_ONEVENDOR_STMT = "SELECT FOOD_NO, FOOD_VDNO, FOOD_NAME, FOOD_FTNO, FOOD_PRICE, FOOD_INTRO, FOOD_STAT, FOOD_PIC FROM FOOD WHERE FOOD_VDNO = ? AND FOOD_STAT = ? AND FOOD_FTNO= ? ORDER BY FOOD_NO";
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, foodVO.getFood_vdno());
			pstmt.setString(2, foodVO.getFood_name());
			pstmt.setString(3, foodVO.getFood_ftno());
			pstmt.setInt(4, foodVO.getFood_price());
			pstmt.setString(5, foodVO.getFood_intro());
			pstmt.setInt(6, foodVO.getFood_stat());
			pstmt.setBytes(7, foodVO.getFood_pic());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(REAL_DELETE_STMT);
//			
//			pstmt.setString(1, food_no);			
//			pstmt.executeUpdate();
//
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_STMT);
			
			pstmt.setInt(1, -1);
			pstmt.setString(2, food_no);
			
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());	
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
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_VENDORFOODTYPE_STMT);
				pstmt.setString(1, food_vdno);
				pstmt.setInt(2, food_stat);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					foodVO = new FoodVO();
					foodVO.setFood_ftno(rs.getString("FOOD_FTNO"));
					list.add(foodVO);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE_STAT_STMT);
				
				pstmt.setInt(1, 2);
				pstmt.setString(2, food_no);
				
				pstmt.executeUpdate();
				
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE_STAT_STMT);
				
				pstmt.setInt(1, 1);
				pstmt.setString(2, food_no);
				
				pstmt.executeUpdate();
				
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
				
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());			
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
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALLCT_BYVDNO);
				
				pstmt.setString(1, vd_no);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					String food_ftno = rs.getString("FOOD_FTNO");
					set.add(food_ftno);
				}
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
			List<FoodVO> list = new ArrayList<>();
			
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
				
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	
//	public static void main(String[] args) {
//		FoodJDBCDAO dao = new FoodJDBCDAO();
////		
//		FoodVO foodVO1 = new FoodVO();
//		foodVO1.setFood_vdno("V000000001");
//		foodVO1.setFood_name("金針菇");
//		foodVO1.setFood_ftno("FT00000005");
//		foodVO1.setFood_price(100);
//		foodVO1.setFood_intro("好粗");
//		foodVO1.setFood_stat(0);
////		foodVO1.setFood_pic();
//		dao.insert(foodVO1);
//		
//		FoodVO foodVO2 = new FoodVO();
//		foodVO2.setFood_no("F000000014");
//		foodVO2.setFood_vdno("V000000001");
//		foodVO2.setFood_name("珊瑚菇");
//		foodVO1.setFood_ftno("FT00000005");
//		foodVO2.setFood_price(200);
//		foodVO2.setFood_intro("有夠好粗");
//		foodVO2.setFood_stat(0);
////		foodVO2.setFood_pic();
//		dao.update(foodVO2);
//		
//		FoodVO foodVO3 = dao.findByPrimaryKey("F000000014");
//		System.out.print(foodVO3.getFood_no() + ",");
//		System.out.print(foodVO3.getFood_vdno() + ",");
//		System.out.print(foodVO3.getFood_name() + ",");
//		System.out.print(foodVO3.getFood_ftno() + ",");
//		System.out.print(foodVO3.getFood_price() + ",");
//		System.out.print(foodVO3.getFood_intro() + ",");
//		System.out.print(foodVO3.getFood_stat() + ",");
//		System.out.println(foodVO3.getFood_pic());
//		System.out.println("---------------------");
//		
//		List<FoodVO> list = dao.getAll();
//		for (FoodVO aFood : list) {
//			System.out.print(aFood.getFood_no() + ",");
//			System.out.print(aFood.getFood_vdno() + ",");
//			System.out.print(aFood.getFood_name() + ",");
//			System.out.print(aFood.getFood_ftno() + ",");
//			System.out.print(aFood.getFood_price() + ",");
//			System.out.print(aFood.getFood_intro() + ",");
//			System.out.print(aFood.getFood_stat() + ",");
//			System.out.println(aFood.getFood_pic());
//			System.out.println();
//		}
//		
//		dao.delete("F000000014");
//		
//	}

}
