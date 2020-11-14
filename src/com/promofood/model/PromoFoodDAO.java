package com.promofood.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OraclePreparedStatement;

public class PromoFoodDAO implements PromoFoodDAO_interface {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private static final String SQL_INSERT = "INSERT INTO PROMO_FOOD(PF_PRONO, PF_FOODNO, PF_PRICE) VALUES(?,?,?)";
	private static final String SQL_UPDATE = "UPDATE PROMO_FOOD SET PF_PRICE=? WHERE (PF_PRONO=? AND PF_FOODNO=?)";
	private static final String SQL_QUERYBYPRONO = "SELECT * FROM PROMO_FOOD WHERE PF_PRONO=?";
	private static final String SQL_QUERYACTIVEBYFOODNO = "SELECT PF_PRONO, PF_FOODNO, PF_PRICE FROM PROMO_FOOD PF JOIN PROMOTION PRO ON PF.PF_PRONO = PRO.PRO_NO WHERE PRO.PRO_STAT = 1 AND PF_FOODNO=?";
	private static final String SQL_DELETE = "DELETE FROM PROMO_FOOD WHERE (PF_PRONO=? AND PF_FOODNO=?)";
	//新增by李承璋
	private static final String SQL_QUERYACTIVELOWPRICEBYFOODNO = "SELECT Min(PF_PRICE) FROM PROMO_FOOD PF JOIN PROMOTION PRO ON PF.PF_PRONO = PRO.PRO_NO WHERE PRO.PRO_STAT = 1 AND PF_FOODNO=?";
	//新增by李承璋
	
	@Override
	public void insert(PromoFoodVO pfVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setString(1, pfVO.getPf_prono());
			pstmt.setString(2, pfVO.getPf_foodno());
			pstmt.setInt(3, pfVO.getPf_price());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoFoodDAO: \"%s\" - \"%s\" insert successfully.%n", pfVO.getPf_prono(),
					pfVO.getPf_foodno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: \"%s\" - \"%s\" insert failed.%n", pfVO.getPf_prono(),
						pfVO.getPf_foodno());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void insert(List<PromoFoodVO> pfVOList) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_INSERT);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pfVOList.size());
			for (PromoFoodVO pfVOi : pfVOList) {
				pstmt.setString(1, pfVOi.getPf_prono());
				pstmt.setString(2, pfVOi.getPf_foodno());
				pstmt.setInt(3, pfVOi.getPf_price());
				pstmt.executeUpdate();
				System.out.printf("PromoFoodDAO: Inserting \"%s\"-\"%s\"...%n", pfVOi.getPf_prono(),
						pfVOi.getPf_foodno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromoFoodDAO: Insert total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: Insert total %d rows failed.%n", pfVOList.size());
				;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(PromoFoodVO pfVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setInt(1, pfVO.getPf_price());
			pstmt.setString(2, pfVO.getPf_prono());
			pstmt.setString(3, pfVO.getPf_foodno());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoFoodDAO: \"%s\"-\"%s\" update successfully.%n", pfVO.getPf_prono(),
					pfVO.getPf_foodno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: \"%s\"-\"%s\" update failed.%n", pfVO.getPf_prono(),
						pfVO.getPf_foodno());
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.printf("PromoFoodDAO: \"%s\"-\"%s\" update rollback failed.%n", pfVO.getPf_prono(),
						pfVO.getPf_foodno());
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public List<PromoFoodVO> queryByPro_no(String pf_prono) {
		List<PromoFoodVO> pfVOList = new ArrayList<PromoFoodVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYBYPRONO);
			pstmt.setString(1, pf_prono);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromoFoodVO pfVO = new PromoFoodVO();
				pfVO.setPf_prono(rs.getString("PF_PRONO"));
				pfVO.setPf_foodno(rs.getString("PF_FOODNO"));
				pfVO.setPf_price(rs.getInt("PF_PRICE"));
				pfVOList.add(pfVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pfVOList;
	}

	@Override
	public void delete(PromoFoodVO pfVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, pfVO.getPf_prono());
			pstmt.setString(2, pfVO.getPf_foodno());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoFoodDAO: \"%s\" - \"%s\" delete successfully.%n", pfVO.getPf_prono(),
					pfVO.getPf_foodno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: \"%s\" - \"%s\" delete failed.%n", pfVO.getPf_prono(),
						pfVO.getPf_foodno());
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.printf("PromoFoodDAO: \"%s\" - \"%s\" delete Rollback failed.%n", pfVO.getPf_prono(),
						pfVO.getPf_foodno());
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(List<PromoFoodVO> pfVOList) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pfVOList.size());
			for (PromoFoodVO pfVOi : pfVOList) {
				pstmt.setString(1, pfVOi.getPf_prono());
				pstmt.setString(2, pfVOi.getPf_foodno());
				pstmt.executeUpdate();
				System.out.printf("PromoFoodDAO: Deleting \"%s\" - \"%s\"...%n", pfVOi.getPf_prono(),
						pfVOi.getPf_foodno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromoFoodDAO: Delete total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: Delete total %d rows failed.%n", pfVOList.size());
				;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void insert(List<PromoFoodVO> pfVOList, Connection con) {
		try {
			pstmt = con.prepareStatement(SQL_INSERT);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pfVOList.size());
			for (PromoFoodVO pfVOi : pfVOList) {
				pstmt.setString(1, pfVOi.getPf_prono());
				pstmt.setString(2, pfVOi.getPf_foodno());
				pstmt.setInt(3, pfVOi.getPf_price());
				pstmt.executeUpdate();
				System.out.printf("PromoFoodDAO: Inserting(promotion) \"%s\"-\"%s\"...%n", pfVOi.getPf_prono(),
						pfVOi.getPf_foodno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			System.out.printf("PromoFoodDAO: Inserted(promotion) total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		}catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoFoodDAO: Insert(promotion) total %d rows failed.%n", pfVOList.size());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public List<PromoFoodVO> queryActiveByFood_no(String pf_foodno) {
		List<PromoFoodVO> pfVOList = new ArrayList<PromoFoodVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYACTIVEBYFOODNO);
			pstmt.setString(1, pf_foodno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromoFoodVO pfVO = new PromoFoodVO();
				pfVO.setPf_prono(rs.getString("PF_PRONO"));
				pfVO.setPf_foodno(rs.getString("PF_FOODNO"));
				pfVO.setPf_price(rs.getInt("PF_PRICE"));
				pfVOList.add(pfVO);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pfVOList;
	}
	
	//新增by李承璋
	@Override
	public PromoFoodVO queryActiveLowPriceByFood_no(String pf_foodno) {
		PromoFoodVO pfVO = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYACTIVELOWPRICEBYFOODNO);
			pstmt.setString(1, pf_foodno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pfVO = new PromoFoodVO();
				pfVO.setPf_price(rs.getInt(1));
				
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return pfVO;
	}
	//新增by李承璋

//	public static void main(String[] args) {
//		String pf_prono = "P000000001";
//		String pf_foodno = "F000000006";
//		int pf_price = 1000;
//		PromoFoodVO pfVO = new PromoFoodVO(pf_prono, pf_foodno, pf_price);
//		PromoFoodDAO pfDAO = new PromoFoodDAO();
////		// Test Promo_Food Insert DAO
////		pfDAO.insert(pfVO);
////		// Test Promo_Food List Insert DAO
////		List<PromoFoodVO> pfVOList = new ArrayList<>();
////		String[] foodnoList = {"F000000001", "F000000003", "F000000004"};
////		for(int i = 0; i < foodnoList.length; i++) {
////			PromoFoodVO pfVOi = new PromoFoodVO(pf_prono, foodnoList[i], pf_price);
////			pfVOList.add(pfVOi);
////		}
////		pfDAO.insert(pfVOList);
////		// Test Promo_Food Update DAO
////		pfDAO.update(pfVO);
////		// Test Promo_Food Query By PRO_NO
////		List<PromoFoodVO> pfVOList = pfDAO.queryByPro_no(pf_prono);
////		for (PromoFoodVO pfVOi : pfVOList) {
////			System.out.println("===============================");
////			System.out.println("pf_prono : " + pfVOi.getPf_prono());
////			System.out.println("PE_CAMPNO : " + pfVOi.getPf_foodno());
////			System.out.println("pf_price : " + pfVOi.getPf_price());
////		}
////		// Test Promo_Food Delete DAO
////		pfDAO.delete(pfVO);
////		// Test Promo_Food List Delete DAO
////		List<PromoFoodVO> pfVOList = pfDAO.queryByPro_no(pf_prono);
////		pfVOList.remove(0);
////		pfDAO.delete(pfVOList);
////		// Test Promo_Food Query By PRO_FOODNO
////		List<PromoFoodVO> pfVOList = pfDAO.queryActiveByFood_no(pf_foodno);
////		for(PromoFoodVO pfVOi : pfVOList) {
////			System.out.println(pfVOi.getPf_prono());
////			System.out.println(pfVOi.getPf_foodno());
////			System.out.println(pfVOi.getPf_price());
////		}
//	}
}
