package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.vendor.model.*;

public class JSONImportExport {
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String userid = "PLAMPING";
	private static final String passwd = "PLAMPING";

	private static final String INSERT_STMT = "INSERT INTO vendor (vd_no, vd_email, vd_pwd, vd_name, vd_id, vd_birth, vd_mobile, vd_cgname, vd_cgtel, "
			+ "vd_cgaddr, vd_taxid, vd_acc, vd_stat, vd_cgstat, vd_lat, vd_lon) VALUES('V'|| LPAD(SEQ_VDNO.NEXTVAL, 9, '0'), "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static void main(String[] args) throws IOException {

		// 讀取json檔案部分
		String str = null;
		String data = "";

		File jfile = new File("WebContent/front-end/index/campSite_v2.json");

		FileInputStream fis = new FileInputStream(jfile);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);

		while ((str = br.readLine()) != null) {
			data = data + str + "\n";
		}

		// 資料庫部分
		JSONImportExport JDBCinfo = new JSONImportExport();
		JSONArray jsonData;

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			jsonData = new JSONArray(data);
//			System.out.println(jsonData);
			for (int i = 0; i < jsonData.length(); i++) {
				JSONObject vd_data = jsonData.getJSONObject(i);
				String vd_cgname = vd_data.getString("name");
				String vd_region = vd_data.getString("region");
				double vd_lat = vd_data.getDouble("lat");
				double vd_lon = vd_data.getDouble("lon");
				String vd_cgaddr = vd_data.getString("address");

				String vd_email = (JDBCinfo.genRandom(5)).toString() + "@" + (JDBCinfo.genRandom(3)).toString()
						+ ".com";
				String vd_pwd = (JDBCinfo.genRandom(3)).toString();
				String vd_name = (JDBCinfo.genRandomword(3)).toString();
				String vd_id = (JDBCinfo.genRandomEn(1)).toString() + (JDBCinfo.genRandomnumber(9)).toString();
				java.sql.Date vd_birth = new java.sql.Date((new java.util.Date()).getTime());
				String vd_mobile = (JDBCinfo.genRandomnumber(10)).toString();
				String vd_cgtel = (JDBCinfo.genRandomnumber(10)).toString();
				String vd_taxid = (JDBCinfo.genRandomnumber(8)).toString();
				String vd_acc = (JDBCinfo.genRandomnumber(14)).toString();
				Integer vd_stat = 1;
				Integer vd_cgstat = 1;

				// 匯入Batch
				pstmt.setString(1, vd_email);
				pstmt.setString(2, vd_pwd);
				pstmt.setString(3, vd_name);
				pstmt.setString(4, vd_id);
				pstmt.setDate(5, vd_birth);
				pstmt.setString(6, vd_mobile);
				pstmt.setString(7, vd_cgname);
				pstmt.setString(8, vd_cgtel);
				pstmt.setString(9, vd_cgaddr);
				pstmt.setString(10, vd_taxid);
				pstmt.setString(11, vd_acc);
				pstmt.setInt(12, vd_stat);
				pstmt.setInt(13, vd_cgstat);
				pstmt.setDouble(14, vd_lat);
				pstmt.setDouble(15, vd_lon);
				pstmt.addBatch();

			}
			pstmt.executeBatch();

			pstmt.close();
			fis.close();
			br.close();

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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

		// 輸出json檔案部分
		VendorJDBCDAO dao = new VendorJDBCDAO();
		File jdir = new File("c:\\javawork");
		if (!jdir.exists())
			jdir.mkdirs();

		File jfile2 = new File(jdir, "campSite_output.json");
		FileOutputStream fos = new FileOutputStream(jfile2);
		OutputStreamWriter osr = new OutputStreamWriter(fos, "UTF-8");
		BufferedWriter bw = new BufferedWriter(osr);
		PrintWriter pw = new PrintWriter(bw);

		List<VendorVO> outfileList = dao.getAll();
		String jsonStr = new JSONArray(outfileList).toString();
		pw.println(jsonStr);
		pw.flush();

		pw.close();
		bw.close();
		osr.close();
		fos.close();
	}

	public StringBuilder genRandom(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 123);
				if ((ran >= 48 && ran <= 57) || (ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomnumber(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 58);
				if ((ran >= 48 && ran <= 57))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomEn(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 123);
				if ((ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}

	public StringBuilder genRandomword(int len) {
		StringBuilder sb = new StringBuilder();
		int ran;
		char code;
		for (int i = 0; i < len; i++) {
			while (true) {
				ran = (int) (Math.random() * 40870);
				if ((ran >= 65 && ran <= 90) || (ran >= 97 && ran <= 122) || (ran >= 19968 && ran <= 40869))
					break;
			}
			code = (char) ran;
			sb.append(code);
		}
		return sb;
	}
}
