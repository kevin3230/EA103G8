package com.carfood.model;

import java.sql.*;
import java.util.*;

public class CarFoodJDBCDAO implements CarFoodDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT = 
			"INSERT INTO car_food (cf_foodno, cf_memno, cf_qty) VALUES( ?, ?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT cf_foodno, cf_memno, cf_qty FROM car_food ORDER BY cf_memno";
	private static final String GET_ONE_STMT = 
			"SELECT cf_foodno, cf_memno, cf_qty FROM car_food WHERE cf_foodno = ? AND cf_memno = ?";
	private static final String DELETE = 
			"DELETE FROM car_food WHERE cf_foodno = ? AND cf_memno = ?";
	private static final String UPDATE = 
			"UPDATE car_food SET cf_qty=? WHERE cf_foodno=? AND cf_memno=?";
	//新增by李承璋
	private static final String GET_ONECAR_STMT = 
			"SELECT cf_foodno, cf_memno, cf_qty FROM car_food WHERE cf_memno = ?";
	private static final String DELETEALL = 
			"DELETE FROM car_food WHERE cf_memno = ?";
	//新增by李承璋
	
	@Override
	public void insert(CarFoodVO carfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, carfoodVO.getCf_foodno());
			pstmt.setString(2, carfoodVO.getCf_memno());
			pstmt.setInt(3, carfoodVO.getCf_qty());
			
			
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
	public void update(CarFoodVO carfoodVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setInt(1, carfoodVO.getCf_qty());
			pstmt.setString(2, carfoodVO.getCf_foodno());
			pstmt.setString(3, carfoodVO.getCf_memno());
			
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
	public void delete(String cf_foodno, String cf_memno) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			
			pstmt.setString(1, cf_foodno);
			pstmt.setString(2, cf_memno);
			
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
	public CarFoodVO findByPrimaryKey(String cf_foodno, String cf_memno) {
		
		CarFoodVO carfoodVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, cf_foodno);
			pstmt.setString(2, cf_memno);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				carfoodVO = new CarFoodVO();
				carfoodVO.setCf_foodno(rs.getString("cf_foodno"));
				carfoodVO.setCf_memno(rs.getString("cf_memno"));
				carfoodVO.setCf_qty(rs.getInt("cf_qty"));
				
				
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
		return carfoodVO;
	}
	
	@Override
	public List<CarFoodVO> getAll() {
		
		List<CarFoodVO> list = new ArrayList<CarFoodVO>();
		CarFoodVO carfoodVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				carfoodVO = new CarFoodVO();
				carfoodVO.setCf_foodno(rs.getString("cf_foodno"));
				carfoodVO.setCf_memno(rs.getString("cf_memno"));
				carfoodVO.setCf_qty(rs.getInt("cf_qty"));
				list.add(carfoodVO);
				
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
	
	//新增by李承璋
			@Override
			public List<CarFoodVO> getOneCar(String cf_memno) {
				
				List<CarFoodVO> list = new ArrayList<CarFoodVO>();
				CarFoodVO carfoodVO = null;
				
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				try {
					
					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					pstmt = con.prepareStatement(GET_ONECAR_STMT);
					
					pstmt.setString(1, cf_memno);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						carfoodVO = new CarFoodVO();
						carfoodVO.setCf_foodno(rs.getString("cf_foodno"));
						carfoodVO.setCf_memno(rs.getString("cf_memno"));
						carfoodVO.setCf_qty(rs.getInt("cf_qty"));
						list.add(carfoodVO);
						
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
			public void updateTransaction(List<CarFoodVO> carFoodVOList) {
				
				Connection con = null;
				PreparedStatement pstmt = null;
				
				try {
					
					Class.forName(driver);
					con = DriverManager.getConnection(url, userid, passwd);
					
					// 1.設定於pstmt().executeUpdate()之前
					con.setAutoCommit(false);
					
					// 先刪除訂單
					pstmt = con.prepareStatement(DELETEALL);
					
					pstmt.setString(1, carFoodVOList.get(0).getCf_memno());
					pstmt.executeUpdate();
					
					//再同時新增購物車食材
					for(int i = 0; i < carFoodVOList.size(); i++) {
						insertTransaction(con, carFoodVOList.get(i));
					}
					
					// 2.設定於pstmt().executeUpdate()之後
					con.commit();
					con.setAutoCommit(true);
					
				} catch (ClassNotFoundException e) {
					throw new RuntimeException("Couldn't load database driver. "
							+ e.getMessage());
				} catch (SQLException se){
					try{
						//發生例外即進行rollback動作
						con.rollback();
					}catch (SQLException e){
						throw new RuntimeException("A database rollback error occured. "
								+ e.getMessage());
					}
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
			
			public void insertTransaction(Connection con,CarFoodVO carFoodVO) {
				
				PreparedStatement pstmt = null;
				
				try {
					
					pstmt = con.prepareStatement(INSERT_STMT);
					
					pstmt.setString(1, carFoodVO.getCf_foodno());
					pstmt.setString(2, carFoodVO.getCf_memno());
					pstmt.setInt(3, carFoodVO.getCf_qty());
					
					pstmt.executeUpdate();
					
				} catch (SQLException se){
					try{
						//發生例外即進行rollback動作
						con.rollback();
					}catch (SQLException e){
						throw new RuntimeException("A database rollback error occured. "
								+ e.getMessage());
					}
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
				}
			}
		//新增by李承璋

		@Override // Author Jeff
		public void delete(CarFoodVO CarFoodVO, Connection con) {
			PreparedStatement pstmt = null;
			
			try {
				pstmt = con.prepareStatement(DELETE);
				
				pstmt.setString(1, CarFoodVO.getCf_foodno());
				pstmt.setString(2, CarFoodVO.getCf_memno());
				
				pstmt.executeUpdate();
				
			} catch (SQLException se) {
				try {
					con.rollback();
					System.out.println("CarFoodDAO delete with OrderMaster failed.");
					throw new RuntimeException("CarFoodDAO insertWithDetails SQLException " + se.getMessage());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException se) {
						se.printStackTrace(System.err);
					}
				}
			}
		}
		
}
