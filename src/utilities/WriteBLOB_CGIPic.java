package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class WriteBLOB_CGIPic {
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USERID = "PLAMPING";
	public static final String PASSWD = "PLAMPING";
	
	public static final String GET_VDNOS_FROM_VENDOR = "SELECT VD_NO FROM VENDOR";
	
	public static final String GET_CGINO_FROM_CGINTRO_BYVDNO =
			"SELECT CGI_NO FROM CG_INTRO"
			+ " WHERE CGI_VDNO = ? AND CGI_STAT = 1";
	
	public static final String GET_NROW_FROM_CGIPIC_BYCGINO =
			"SELECT COUNT(1) FROM CGI_PIC"
			+ " WHERE CGIP_CGINO = ?";
	
	public static final String INSERT_CGINTRO =
			"INSERT INTO CG_INTRO(CGI_NO, CGI_VDNO, CGI_CONTENT, CGI_STAT)"
			+ " VALUES ('I' || LPAD(SEQ_CGINO.NEXTVAL, 9, '0'), ?, ?, 1)";
	
	public static final String INSERT_CGIPIC =
			"INSERT INTO CGI_PIC(CGIP_NO, CGIP_CGINO, CGIP_PIC)"
			+ " VALUES ('IP' || LPAD(SEQ_CGIPNO.NEXTVAL, 8, '0'), ?, ?)";
	
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		
		File dir = new File("images_fakeData/joint_cgiPic_camp");
		String[] picFileNames = dir.list();

		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, USERID, PASSWD);
			
			// 從VENDOR表格讀取所有業者編號
			pstmt = conn.prepareStatement(GET_VDNOS_FROM_VENDOR);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String vd_no = rs.getString(1);
				
				// 確認露營區簡介是否存在
				pstmt2 = conn.prepareStatement(GET_CGINO_FROM_CGINTRO_BYVDNO);
				pstmt2.setString(1, vd_no);
				rs2 = pstmt2.executeQuery();
				String cgi_no = null;
				if (rs2.next())
					cgi_no = rs2.getString(1);
				else {
					// 如果不存在就新增露營區簡介
					String[] cols = {"CGI_NO"};
					pstmt3 = conn.prepareStatement(INSERT_CGINTRO, cols);
					pstmt3.setString(1, vd_no);
					pstmt3.setString(2, "");
					pstmt3.executeUpdate();
					
					rs3 = pstmt3.getGeneratedKeys();
					if (rs3.next())
						cgi_no = rs3.getString(1);
					else
						System.out.println("新增露營區簡介失敗： " + vd_no);
					rs3.close();
					pstmt3.close();
				}
				rs2.close();
				pstmt2.close();
				
				pstmt2 = conn.prepareStatement(GET_NROW_FROM_CGIPIC_BYCGINO);
				pstmt2.setString(1, cgi_no);
				rs2 = pstmt2.executeQuery();
				int data = 0;
				if (rs2.next())
					data = rs2.getInt(1);
				else {
					System.out.println("露營區介紹圖片數量查詢失敗" + cgi_no);
				}
				rs2.close();
				pstmt2.close();
				// 如果有圖片就不用插入
				if (data > 0)
					continue;
				
				// 隨機插入5到7張圖片給每個露營區介紹
				Set<String> picSet = new HashSet<>();
				int npic = (int)(Math.random() * 3) + 5;
				while (picSet.size() < npic) {
					int index = (int)(Math.random() * picFileNames.length);
					picSet.add(picFileNames[index]);
				}
				
				pstmt2 = conn.prepareStatement(INSERT_CGIPIC);
				for (String picFileName : picSet) {
					File file = new File(dir, picFileName);
					FileInputStream fis = null;
					
					try {
						fis = new FileInputStream(file);
						byte[] pic = new byte[fis.available()];
						fis.read(pic);
						
						pstmt2.setString(1, cgi_no);
						pstmt2.setBytes(2, pic);
						pstmt2.addBatch();
						
					} catch(IOException e) {
						e.printStackTrace();
					} finally {
						if (fis != null) {
							try{
								fis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				pstmt2.executeBatch();
				pstmt2.close();
			}
			rs.close();
			pstmt.close();
			
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