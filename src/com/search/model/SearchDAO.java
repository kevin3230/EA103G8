package com.search.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SearchDAO implements SearchDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SearchVO> getResult(JsonObject jsonObj) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		SearchVO searchVO = null;
		List<SearchVO> list = new ArrayList<>();

		try {
			String getResultStmt = "SELECT V.VD_NO, VD_CGNAME, VD_CGADDR, MIN(CAMP_PRICE), MIN(NVL(PC_PRICE, CAMP_PRICE)) AS MIN_PRICE\n"
					+ " FROM VENDOR V"
					+ " JOIN CAMP C ON V.VD_NO = C.CAMP_VDNO"
					+ " LEFT JOIN CAMP_TYPE CT ON C.CAMP_CTNO = CT.CT_NO"
					+ " LEFT JOIN\n"
					+ " (SELECT PRO_NO, PRO_START, PRO_END, PC_CAMPNO, PC_PRICE"
					+ " FROM PROMOTION P"
					+ " JOIN PROMO_CAMP PC ON P.PRO_NO = PC.PC_PRONO"
					+ " WHERE PRO_STAT = 1\n"
					+ getPromoCondition(jsonObj)
					+ ") P ON C.CAMP_NO = P.PC_CAMPNO"
					+ " WHERE VD_STAT = 1 AND VD_CGSTAT = 1 AND CAMP_STAT > 0\n"
					+ getConditions(jsonObj)
					+ " GROUP BY V.VD_NO, VD_CGNAME, VD_CGADDR"
					+ " ORDER BY MIN_PRICE";
			System.out.println(getResultStmt);

			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(getResultStmt);

			while (rs.next()) {
				searchVO = new SearchVO();
				searchVO.setVd_no(rs.getString(1));
				searchVO.setVd_cgname(rs.getString(2));
				searchVO.setVd_cgaddr(rs.getString(3));
				searchVO.setMinListPrice(rs.getInt(4));
				searchVO.setMinPrice(rs.getInt(5));
				list.add(searchVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} catch (ParseException e) {
			throw new RuntimeException("錯誤日期格式");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
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

	public String getPromoCondition(JsonObject jsonObj) {
		StringBuffer cond = new StringBuffer();
		String campStart = "";
		String campEnd = "";

		for (String key : jsonObj.keySet()) {
			if ("campStart".equals(key))
				campStart = jsonObj.get(key).getAsString();

			if ("campEnd".equals(key))
				campEnd = jsonObj.get(key).getAsString();
		}
		
		cond.append(" AND ");
		if (campStart.trim().length() > 0 && campEnd.trim().length() > 0) {
			cond.append("PRO_END >= TO_DATE('" + campStart + "','yyyy-mm-dd')"
					+ " AND PRO_START <= TO_DATE('" + campEnd + "','yyyy-mm-dd')");	
		} else {
			cond.append("PRO_START <= CURRENT_DATE AND PRO_END >= CURRENT_DATE");
		}
		
		return cond.toString();
	}

	public String getConditions(JsonObject jsonObj) throws ParseException {
		StringBuffer cond = new StringBuffer();
		String campStart = "";
		String campEnd = "";
		String campPromo = "";

		for (String key : jsonObj.keySet()) {
			if ("campName".equals(key)) {
				String value = jsonObj.get(key).getAsString();
				if (value.trim().length() > 0) {
					cond.append(" AND ");
					cond.append("VD_CGNAME LIKE '%" + value + "%'\n");
				}
			}

			if ("campArea".equals(key)) {
				JsonArray value = jsonObj.get(key).getAsJsonArray();
				int count = 0;
				for (JsonElement i : value) {
					if (count == 0)
						cond.append(" AND (");
					else
						cond.append(" OR ");

					cond.append("VD_CGADDR LIKE '%" + i.getAsString() + "%'");
					count++;
				}
				if (count > 0)
					cond.append(")\n");
			}

			if ("campType".equals(key)) {
				JsonArray value = jsonObj.get(key).getAsJsonArray();
				int count = 0;
				for (JsonElement i : value) {
					if (count == 0)
						cond.append(" AND (");
					else
						cond.append(" OR ");

					cond.append("CT_NO = '" + i.getAsString() + "'");
					count++;
				}
				if (count > 0)
					cond.append(")\n");
			}

			if ("campMinPrice".equals(key)) {
				String value = jsonObj.get(key).getAsString();
				if (value.trim().length() > 0) {
					cond.append(" AND ");
					cond.append("NVL(PC_PRICE, CAMP_PRICE) >= " + value + "\n");
				}
			}

			if ("campMaxPrice".equals(key)) {
				String value = jsonObj.get(key).getAsString();
				if (value.trim().length() > 0) {
					cond.append(" AND ");
					cond.append("NVL(PC_PRICE, CAMP_PRICE) <= " + value + "\n");
				}
			}

			if ("campPromo".equals(key)) {
				campPromo = jsonObj.get(key).getAsString();
				if ("1".equals(campPromo)) {
					cond.append(" AND ");
					cond.append("PC_PRICE IS NOT NULL\n");
				}
			}

			if ("campStart".equals(key))
				campStart = jsonObj.get(key).getAsString();

			if ("campEnd".equals(key))
				campEnd = jsonObj.get(key).getAsString();
		}

		// 起始日期跟結束日期同時存在才需要做
		if (campStart.trim().length() > 0 && campEnd.trim().length() > 0) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			int dayLength = (int) ((df.parse(campEnd).getTime() - df.parse(campStart).getTime()) / 86_400_000L) + 1;
			// 如果結束日期早於開始日期，禁止篩選出結果
			if (dayLength < 1)
				return " AND 0 = 1\n";
					
			cond.append(" AND ");
			// 期間內都要有空位
			cond.append("NOT EXISTS"
					+ " (SELECT 1 FROM CAMP_AVAIL CA"
					+ " WHERE C.CAMP_NO = CA.CA_CAMPNO AND CA_QTY = 0"
					+ " AND CA_DATE BETWEEN TO_DATE('" + campStart + "','yyyy-mm-dd') AND TO_DATE('" + campEnd + "','yyyy-mm-dd'))\n");

			// 期間內的任一天有空位即可
//			cond.append("((SELECT COUNT(1) FROM CAMP_AVAIL CA"
//					+ " WHERE C.CAMP_NO = CA.CA_CAMPNO"
//					+ " AND CA.CA_DATE BETWEEN TO_DATE('" + campStart + "','yyyy-mm-dd') AND TO_DATE('" + campEnd + "','yyyy-mm-dd')) < " + dayLength
//					+ " OR"
//					+ " EXISTS (SELECT 1 FROM CAMP_AVAIL CA"
//					+ " WHERE C.CAMP_NO = CA.CA_CAMPNO"
//					+ " AND CA_QTY > 0"
//					+ " AND CA_DATE BETWEEN TO_DATE('" + campStart + "','yyyy-mm-dd') AND TO_DATE('" + campEnd + "','yyyy-mm-dd')))\n");
		}

		if (cond.length() == 0) // 沒有任何選項就不要篩選出結果
			return " AND 0 = 1\n";
		else
			return cond.toString();
	}
}
