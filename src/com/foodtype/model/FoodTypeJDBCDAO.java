package com.foodtype.model;

import java.sql.*;
import java.util.*;

public class FoodTypeJDBCDAO implements FoodTypeDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String userid = "PLAMPING";
	String passwd = "PLAMPING";
	
	private static final String INSERT_STMT =
			"INSERT INTO FOOD_TYPE(FT_NO, FT_NAME) VALUES ('FT' || LPAD(SEQ_CTNO.NEXTVAL, 8, '0'), ?)";
	
	private static final String UPDATE_STMT =
			"UPDATE FOOD_TYPE SET FT_NAME = ? WHERE FT_NO = ?";
	
	private static final String DELETE_STMT =
			"DELETE FROM FOOD_TYPE WHERE FT_NO = ?";
	
	private static final String GET_ONE_STMT =
			"SELECT FT_NO, FT_NAME FROM FOOD_TYPE WHERE FT_NO = ?";
	
	private static final String GET_ALL_STMT =
			"SELECT FT_NO, FT_NAME FROM FOOD_TYPE ORDER BY FT_NO";
	
	@Override
	public void insert(FoodTypeVO foodTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, foodTypeVO.getFt_name());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void update(FoodTypeVO foodTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, foodTypeVO.getFt_name());
			pstmt.setString(2, foodTypeVO.getFt_no());
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void delete(String ft_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, ft_no);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public FoodTypeVO findByPrimaryKey(String ft_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FoodTypeVO foodTypeVO = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ft_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodTypeVO = new FoodTypeVO();
				foodTypeVO.setFt_no(rs.getString(1));;
				foodTypeVO.setFt_name(rs.getString(2));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return foodTypeVO;
	}

	@Override
	public List<FoodTypeVO> getAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FoodTypeVO foodTypeVO = null;
		List<FoodTypeVO> list = new ArrayList<>();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userid, passwd);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodTypeVO = new FoodTypeVO();
				foodTypeVO.setFt_no(rs.getString(1));;
				foodTypeVO.setFt_name(rs.getString(2));
				list.add(foodTypeVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

//	public static void main(String arg[]) {
//		FoodTypeJDBCDAO dao = new FoodTypeJDBCDAO();
//	
//		FoodTypeVO foodTypeVO1 = new FoodTypeVO();
//		foodTypeVO1.setFt_name("昆蟲");
//		dao.insert(foodTypeVO1);
//	
//		FoodTypeVO foodTypeVO2 = new FoodTypeVO();
//		foodTypeVO2.setFt_no("FT00000008");
//		foodTypeVO2.setFt_name("兩棲類");
//		dao.update(foodTypeVO2);
//	
//		FoodTypeVO foodTypeVO3 = dao.findByPrimaryKey("FT00000008");
//		System.out.print(foodTypeVO3.getFt_no() + ",");
//		System.out.println(foodTypeVO3.getFt_name());
//		System.out.println("---------------------");
//	
//		List<FoodTypeVO> list = dao.getAll();
//		for (FoodTypeVO foodTypeVO : list) {
//			System.out.print(foodTypeVO.getFt_no() + ",");
//			System.out.println(foodTypeVO.getFt_name());
//			System.out.println();
//		}
//	
//		dao.delete("FT00000008");
//	
//	}
	
}
