package com.waterfall.model;
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



public class WaterfallDAO implements WaterfallDAO_interface {


	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
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
		"INSERT INTO waterfall (wf_no, wf_memno, wf_title, wf_content) VALUES ('W' || LPAD(SEQ_WFNO.NEXTVAL, 9, '0'), ?, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT wf_no, wf_memno, wf_title, wf_content,to_char(wf_edit,'yyyy-mm-dd hh24:mi') wf_edit, wf_stat FROM waterfall order by wf_no DESC";
	private static final String GET_ONE_STMT = 
		"SELECT wf_no, wf_memno, wf_title, wf_content,to_char(wf_edit,'yyyy-mm-dd hh24:mi') wf_edit, wf_stat FROM waterfall where wf_no = ?";
	private static final String DELETE = 
		"DELETE FROM waterfall where wf_no = ?";
	private static final String UPDATE = 
		"UPDATE waterfall set wf_title=?, wf_content=? , wf_edit=systimestamp where wf_no = ?";
	private static final String JOINWFREPLY =
		"SELECT R.WFR_NO, R.wfr_memno, R.wfr_content, to_char(R.wfr_edit,'yyyy-mm-dd hh24:mi') wfr_edit, R.wfr_stat FROM WATERFALL W JOIN WF_REPLY R ON W.WF_NO = R.WFR_WFNO WHERE WFR_WFNO = ? order by R.WFR_NO asc";
	private static final String FAKE_DELETE =
		"UPDATE waterfall SET wf_stat = 0 WHERE wf_no = ?";
	@Override
	public String insert(WaterfallVO waterfallVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT,new String[] {"wf_no"});

			pstmt.setString(1, waterfallVO.getWf_memno());
			pstmt.setString(2, waterfallVO.getWf_title());
			pstmt.setString(3, waterfallVO.getWf_content());

			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next())
            {
                String last_inserted_id = rs.getString(1);
                System.out.println(last_inserted_id);
                return last_inserted_id;
            }
            return null;
            
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void update(WaterfallVO waterfallVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, waterfallVO.getWf_title());
			pstmt.setString(2, waterfallVO.getWf_content());
			pstmt.setString(3, waterfallVO.getWf_no());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(String wf_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setString(1, wf_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public WaterfallVO findByPrimaryKey(String wf_no) {
		
		WaterfallVO waterfallVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, wf_no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				waterfallVO = new WaterfallVO();
				waterfallVO.setWf_no(rs.getString("wf_no"));
				waterfallVO.setWf_memno(rs.getString("wf_memno"));
				waterfallVO.setWf_title(rs.getString("wf_title"));
				waterfallVO.setWf_content(rs.getString("wf_content"));
				waterfallVO.setWf_edit(rs.getString("wf_edit"));
				waterfallVO.setWf_stat(rs.getInt("wf_stat"));
			
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return waterfallVO;
	}

	@Override
	public List<WaterfallVO> getAll() {
		List<WaterfallVO> list = new ArrayList<WaterfallVO>();
		WaterfallVO waterfallVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				waterfallVO = new WaterfallVO();
				waterfallVO.setWf_no(rs.getString("wf_no"));
				waterfallVO.setWf_memno(rs.getString("wf_memno"));
				waterfallVO.setWf_title(rs.getString("wf_title"));
				waterfallVO.setWf_content(rs.getString("wf_content"));
				waterfallVO.setWf_edit(rs.getString("wf_edit"));
				waterfallVO.setWf_stat(rs.getInt("wf_stat"));
				list.add(waterfallVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	@Override
	public List<WFReplyVO> joinWFReply(String wfr_wfno) {
		List<WFReplyVO> list = new ArrayList<WFReplyVO>();
		WFReplyVO wfreplyVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(JOINWFREPLY);
			
			pstmt.setString(1, wfr_wfno);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// wfreplyVO 也稱為 Domain objects
				wfreplyVO = new WFReplyVO();
				wfreplyVO.setWfr_no(rs.getString("wfr_no"));
				wfreplyVO.setWfr_memno(rs.getString("wfr_memno"));
				wfreplyVO.setWfr_content(rs.getString("wfr_content"));
				wfreplyVO.setWfr_edit(rs.getString("wfr_edit"));
				wfreplyVO.setWfr_stat(rs.getInt("wfr_stat"));
				list.add(wfreplyVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public void fake_delete(String wf_no) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FAKE_DELETE);

			pstmt.setString(1, wf_no);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
}
