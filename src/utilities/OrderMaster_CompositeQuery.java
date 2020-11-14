/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *        所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */


package utilities;

import java.util.*;

public class OrderMaster_CompositeQuery {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("mem_no".equals(columnName)) // 用於其他
			aCondition = "om_memno" + "= '" + value + "' ";
		else if ("vd_no".equals(columnName))
			aCondition = "om_vdno" + "= '" + value + "' ";
		else if ("startDate".equals(columnName))                          // 用於Oracle的date
			aCondition = "om_estbl" + " >= " + "to_date('" + value + "','yyyy-mm-dd')";
		else if ("endDate".equals(columnName))
			aCondition = "om_estbl" + " <= " + "to_date('" + value + " 23:59:59','yyyy-mm-dd HH24:MI:SS')";

		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);

				//System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("mem_no", new String[] { "M000000001" });
		map.put("vd_no", new String[] { "V000000001" });
		map.put("startDate", new String[] { "2020-01-01" });
		map.put("endDate", new String[] { "2020-12-29" });
		map.put("action", new String[] { "getMembersOrder" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from Order_Master "
				          + OrderMaster_CompositeQuery.get_WhereCondition(map)
				          + "order by om_no";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
