package com.promocamp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.promotion.model.PromotionVO;

import oracle.jdbc.OraclePreparedStatement;

public class PromoCampDAO implements PromoCampDAO_interface {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "PLAMPING";
	private static final String PASSWORD = "PLAMPING";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private static final String SQL_INSERT = "INSERT INTO PROMO_CAMP(PC_PRONO, PC_CAMPNO, PC_PRICE) VALUES(?,?,?)";
	private static final String SQL_UPDATE = "UPDATE PROMO_CAMP SET PC_PRICE=? WHERE (PC_PRONO=? AND PC_CAMPNO=?)";
	private static final String SQL_QUERYBYPRONO = "SELECT * FROM PROMO_CAMP WHERE PC_PRONO=?";
	
	// Edit by Yen-Fu Chen
	private static final String SQL_QUERYACTIVEBYCAMPNO =
			"SELECT PC_PRONO, PC_CAMPNO, PC_PRICE"
			+ " FROM PROMO_CAMP PC"
			+ " JOIN PROMOTION PRO ON PC.PC_PRONO = PRO.PRO_NO"
			+ " WHERE PRO.PRO_STAT = 1 AND CURRENT_DATE BETWEEN PRO.PRO_START AND PRO.PRO_END AND PC_CAMPNO = ?";
	
	// Add by Yen-Fu Chen
	private static final String SQL_QUERYALIVEBYCAMPNO =
			"SELECT PC_PRONO, PC_CAMPNO, PC_PRICE"
			+ " FROM PROMO_CAMP PC"
			+ " JOIN PROMOTION PRO ON PC.PC_PRONO = PRO.PRO_NO"
			+ " WHERE PRO.PRO_STAT = 1 AND CURRENT_DATE <= PRO.PRO_END AND PC_CAMPNO = ?";
	
	private static final String SQL_DELETE = "DELETE FROM PROMO_CAMP WHERE (PC_PRONO=? AND PC_CAMPNO=?)";

	@Override
	public void insert(PromoCampVO pcVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("PromoCampDAO insert(VO) get connection successfully.");
			pstmt = con.prepareStatement(SQL_INSERT);
			pstmt.setString(1, pcVO.getPc_prono());
			pstmt.setString(2, pcVO.getPc_campno());
			pstmt.setInt(3, pcVO.getPc_price());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoCampDAO: \"%s\" - \"%s\" insert successfully.%n", pcVO.getPc_prono(),
					pcVO.getPc_campno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: \"%s\" - \"%s\" insert failed.%n", pcVO.getPc_prono(),
						pcVO.getPc_campno());
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
	public void insert(List<PromoCampVO> pcVOList) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_INSERT);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pcVOList.size());
			for (PromoCampVO pcVOi : pcVOList) {
				pstmt.setString(1, pcVOi.getPc_prono());
				pstmt.setString(2, pcVOi.getPc_campno());
				pstmt.setInt(3, pcVOi.getPc_price());
				pstmt.executeUpdate();
				System.out.printf("PromoCampDAO: Inserting \"%s\"-\"%s\"...%n", pcVOi.getPc_prono(),
						pcVOi.getPc_campno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromoCampDAO: Insert total %d rows successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: Insert total %d rows failed.%n", pcVOList.size());
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
	public void update(PromoCampVO pcVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_UPDATE);
			pstmt.setInt(1, pcVO.getPc_price());
			pstmt.setString(2, pcVO.getPc_prono());
			pstmt.setString(3, pcVO.getPc_campno());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoCampDAO: \"%s\"-\"%s\" update successfully.%n", pcVO.getPc_prono(),
					pcVO.getPc_campno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: \"%s\"-\"%s\" update failed.%n", pcVO.getPc_prono(),
						pcVO.getPc_campno());
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.printf("PromoCampDAO: \"%s\"-\"%s\" update rollback failed.%n", pcVO.getPc_prono(),
						pcVO.getPc_campno());
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
	public List<PromoCampVO> queryByPro_no(String pc_prono) {
		List<PromoCampVO> pcVOList = new ArrayList<PromoCampVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYBYPRONO);
			pstmt.setString(1, pc_prono);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromoCampVO pcVO = new PromoCampVO();
				pcVO.setPc_prono(rs.getString("PC_PRONO"));
				pcVO.setPc_campno(rs.getString("PC_CAMPNO"));
				pcVO.setPc_price(rs.getInt("PC_PRICE"));
				pcVOList.add(pcVO);
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
		return pcVOList;
	}

	@Override
	public void delete(PromoCampVO pcVO) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			pstmt.setString(1, pcVO.getPc_prono());
			pstmt.setString(2, pcVO.getPc_campno());
			con.setAutoCommit(false);
			pstmt.executeUpdate();
			con.commit();
			System.out.printf("PromoCampDAO: \"%s\" - \"%s\" delete successfully.%n", pcVO.getPc_prono(),
					pcVO.getPc_campno());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: \"%s\" - \"%s\" delete failed.%n", pcVO.getPc_prono(),
						pcVO.getPc_campno());
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.printf("PromoCampDAO: \"%s\" - \"%s\" delete rollback failed.%n", pcVO.getPc_prono(),
						pcVO.getPc_campno());
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
	public void delete(List<PromoCampVO> pcVOList) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_DELETE);
			con.setAutoCommit(false);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pcVOList.size());
			for (PromoCampVO pcVOi : pcVOList) {
				pstmt.setString(1, pcVOi.getPc_prono());
				pstmt.setString(2, pcVOi.getPc_campno());
				pstmt.executeUpdate();
				System.out.printf("PromoCampDAO: Deleting \"%s\" - \"%s\"...%n", pcVOi.getPc_prono(),
						pcVOi.getPc_campno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			con.commit();
			System.out.printf("PromoCampDAO: Delete total %d rows Successfully.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: Delete total %d rows Failed.%n", pcVOList.size());
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
	public void insert(List<PromoCampVO> pcVOList, Connection con) {
		/*
		 * Connection from PromotionDAO to insert Promotion with corresponding items.
		 * This will achieve the whole master-detail transaction effect;
		 * one item failed to insert than whole promotion can't insert.
		 * 
		 */
		try {
			pstmt = con.prepareStatement(SQL_INSERT);
			((OraclePreparedStatement) pstmt).setExecuteBatch(pcVOList.size());
			for (PromoCampVO pcVOi : pcVOList) {
				pstmt.setString(1, pcVOi.getPc_prono());
				pstmt.setString(2, pcVOi.getPc_campno());
				pstmt.setInt(3, pcVOi.getPc_price());
				pstmt.executeUpdate();
				System.out.printf("PromoCampDAO: Inserting(promotion) \"%s\"-\"%s\"...%n", pcVOi.getPc_prono(),
						pcVOi.getPc_campno());
			}
			((OraclePreparedStatement) pstmt).sendBatch();
			System.out.printf("PromoCampDAO: Inserted(promotion) total %d rows.%n",
					((OraclePreparedStatement) pstmt).getExecuteBatch());

		} catch (SQLException e) {
			try {
				con.rollback();
				System.out.printf("PromoCampDAO: Insert(promotion) total %d rows failed.%n", pcVOList.size());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public List<PromoCampVO> queryActiveByCamp_no(String pc_campno) {
		List<PromoCampVO> pcVOList = new ArrayList<PromoCampVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYACTIVEBYCAMPNO);
			pstmt.setString(1, pc_campno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromoCampVO pcVO = new PromoCampVO();
				pcVO.setPc_prono(rs.getString("PC_PRONO"));
				pcVO.setPc_campno(rs.getString("PC_CAMPNO"));
				pcVO.setPc_price(rs.getInt("PC_PRICE"));
				pcVOList.add(pcVO);
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
		return pcVOList;
	}
	
	// Add by Yen-Fu Chen
	@Override
	public List<PromoCampVO> queryAliveByCamp_no(String pc_campno) {
		List<PromoCampVO> pcVOList = new ArrayList<PromoCampVO>();
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(SQL_QUERYALIVEBYCAMPNO);
			pstmt.setString(1, pc_campno);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PromoCampVO pcVO = new PromoCampVO();
				pcVO.setPc_prono(rs.getString("PC_PRONO"));
				pcVO.setPc_campno(rs.getString("PC_CAMPNO"));
				pcVO.setPc_price(rs.getInt("PC_PRICE"));
				pcVOList.add(pcVO);
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
		return pcVOList;
	}

//	public static void main(String[] args) {
//		String pc_prono = "P000000008";
//		String pc_campno = "C000000001";
//		int pc_price = 1000;
//		PromoCampVO pcVO = new PromoCampVO(pc_prono, pc_campno, pc_price);
//		PromoCampDAO pcDAO = new PromoCampDAO();
////		// Test Promo_Camp Insert DAO
////		pcDAO.insert(pcVO);
////		// Test Promo_Camp List Insert DAO
////		List<PromoCampVO> pcVOList = new ArrayList<>();
////		String[] campnoList = {"C000000001", "C000000002", "C000000003"};
////		for(int i = 0; i < campnoList.length; i++) {
////			PromoCampVO pcVOi = new PromoCampVO(pc_prono, campnoList[i], pc_price);
////			pcVOList.add(pcVOi);
////		}
////		pcDAO.insert(pcVOList);
////		// Test Promo_Camp Update DAO
////		pcDAO.update(pcVO);
////		// Test Promo_Camp Query By PRO_NO
////		List<PromoCampVO> pcVOList = pcDAO.queryByPro_no(pc_prono);
////		for (PromoCampVO pcVOi : pcVOList) {
////			System.out.println("===============================");
////			System.out.println("PC_PRONO : " + pcVOi.getPc_prono());
////			System.out.println("PC_CAMPNO : " + pcVOi.getPc_campno());
////			System.out.println("PC_PRICE : " + pcVOi.getPc_price());
////		}
////		// Test Promo_Camp Delete DAO
////		pcDAO.delete(pcVO);
////		// Test Promo_Camp List Delete DAO
////		List<PromoCampVO> pcVOList = pcDAO.queryByPro_no(pc_prono);
////		pcVOList.remove(0);
////		pcDAO.delete(pcVOList);
////		// Test Promo_Camp Query By CAMP_NO
////		List<PromoCampVO> pcVOList = pcDAO.queryActiveByCamp_no(pc_campno);
////		for(PromoCampVO pcVOi : pcVOList) {
////			System.out.println(pcVOi.getPc_prono());
////			System.out.println(pcVOi.getPc_campno());
////			System.out.println(pcVOi.getPc_price());
////		}
//	}
}
