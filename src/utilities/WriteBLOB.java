package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WriteBLOB {
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USERID = "PLAMPING";
	public static final String PASSWD = "PLAMPING";

	public static final String UPDATE_EQPT_STMT = "UPDATE EQUIPMENT SET EQPT_PIC= ? WHERE EQPT_NO = ?";
	public static final String UPDATE_FOOD_STMT = "UPDATE FOOD SET FOOD_PIC= ? WHERE FOOD_NO = ?";
	public static final String UPDATE_CAMP_STMT = "UPDATE CAMP SET CAMP_PIC= ? WHERE CAMP_NO = ?";
	public static final String UPDATE_CGI_PIC_STMT = "UPDATE CGI_PIC SET CGIP_PIC= ? WHERE CGIP_NO = ?";
	public static final String UPDATE_VENDOR_STMT = "UPDATE VENDOR SET VD_BRC = ?, VD_MAP = ? WHERE VD_NO = ?";
	public static final String GET_MULTI_BYCAMPPIC_STMT = "SELECT CAMP_NO FROM CAMP WHERE CAMP_PIC IS NULL";
	public static final String UPDATE_WATERFALL_STMT = "INSERT INTO WF_PIC(wfp_no, wfp_wfno, wfp_pic)" + "VALUES('WP' || LPAD(SEQ_WFNO.NEXTVAL, 8, '0'), ?, ?)";;
	public static final String EQPT_PATH = "images_fakeData/equipment";
	public static final String FOOD_PATH = "images_fakeData/food";
	public static final String CAMP_PATH = "images_fakeData/camp";
	public static final String CGI_PIC_PATH = "images_fakeData/cgi_pic";
	public static final String VENDOR_PATH = "images_fakeData/vendor";
	public static final String WATERFALL_PATH = "images_fakeData/waterfall";
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		FileInputStream fis = null;
		File dir = null;

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);

			// 修改裝備圖片
			pstmt = conn.prepareStatement(UPDATE_EQPT_STMT);
			dir = new File(EQPT_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(EQPT_PATH + "/" + filename);
				if (file.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("."));
				pstmt.setBytes(1, pic);
				pstmt.setString(2, pk);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("插入裝備圖片完成！");

			// 修改食材圖片
			pstmt = conn.prepareStatement(UPDATE_FOOD_STMT);
			dir = new File(FOOD_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(FOOD_PATH + "/" + filename);
				if (file.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("."));
				pstmt.setBytes(1, pic);
				pstmt.setString(2, pk);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("插入食材圖片完成！");

			// 修改營位圖片
			pstmt = conn.prepareStatement(UPDATE_CAMP_STMT);
			dir = new File(CAMP_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(CAMP_PATH + "/" + filename);
				if (file.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("."));
				pstmt.setBytes(1, pic);
				pstmt.setString(2, pk);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			pstmt = conn.prepareStatement(GET_MULTI_BYCAMPPIC_STMT);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pstmt = conn.prepareStatement(UPDATE_CAMP_STMT);

				// 隨機讀取一張圖
				int index = (int) (Math.random() * dir.list().length);
				File file = new File(CAMP_PATH + "/" + dir.list()[index]);
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				// 存入SQL指令
				String pk = rs.getString(1);
				pstmt.setBytes(1, pic);
				pstmt.setString(2, pk);
				pstmt.execute();
				pstmt.close();
			}
			System.out.println("插入營位圖片完成！");

			// 修改露營區介紹圖片
			pstmt = conn.prepareStatement(UPDATE_CGI_PIC_STMT);
			dir = new File(CGI_PIC_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(CGI_PIC_PATH + "/" + filename);
				if (file.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("."));
				pstmt.setBytes(1, pic);
				pstmt.setString(2, pk);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("插入露營區介紹圖片完成！");

			// 修改業者圖片
			pstmt = conn.prepareStatement(UPDATE_VENDOR_STMT);
			dir = new File(VENDOR_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(VENDOR_PATH + "/" + filename);
				if (file.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();

				File file2 = new File(VENDOR_PATH + "/" + filename);
				if (file2.isDirectory() || filename.lastIndexOf("_") > 0) // 排除資料夾、多出來的圖
					continue;
				fis = new FileInputStream(file2);
				byte[] pic2 = new byte[fis.available()];
				fis.read(pic2);
				fis.close();

				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("."));
				pstmt.setBytes(1, pic);
				pstmt.setBytes(2, pic2);
				pstmt.setString(3, pk);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("插入業者圖片完成！");
			
			// 修改waterfall圖片
			pstmt = conn.prepareStatement(UPDATE_WATERFALL_STMT);
			dir = new File(WATERFALL_PATH);
			for (String filename : dir.list()) {
				// 讀取本機圖片
				File file = new File(WATERFALL_PATH + "/" + filename);
				if (file.isDirectory() || ".DS_Store".equals(filename))	// 排除資料夾
					continue;
				fis = new FileInputStream(file);
				byte[] pic = new byte[fis.available()];
				fis.read(pic);
				fis.close();
				System.out.println(filename);
				// 存入SQL指令
				String pk = filename.substring(0, filename.lastIndexOf("_"));
				pstmt.setString(1, pk);
				pstmt.setBytes(2, pic);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			pstmt.close();
			System.out.println("插入waterfall圖片完成！");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
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
	}
}