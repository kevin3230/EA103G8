package com.foodtype.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FoodTypeDAO implements FoodTypeDAO_interface {
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, foodTypeVO.getFt_name());
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
	public void update(FoodTypeVO foodTypeVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1, foodTypeVO.getFt_name());
			pstmt.setString(2, foodTypeVO.getFt_no());
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
	public void delete(String ft_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1, ft_no);
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
	public FoodTypeVO findByPrimaryKey(String ft_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FoodTypeVO foodTypeVO = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			
			pstmt.setString(1, ft_no);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodTypeVO = new FoodTypeVO();
				foodTypeVO.setFt_no(rs.getString(1));;
				foodTypeVO.setFt_name(rs.getString(2));
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
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				foodTypeVO = new FoodTypeVO();
				foodTypeVO.setFt_no(rs.getString(1));;
				foodTypeVO.setFt_name(rs.getString(2));
				list.add(foodTypeVO);
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

}
