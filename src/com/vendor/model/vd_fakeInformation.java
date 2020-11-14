package com.vendor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/vd_fakeInformation")
public class vd_fakeInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		//資料庫部分
		VendorDAO dao = new VendorDAO();
		VendorService vendorSvc = new VendorService();
		VendorVO fakeInformation = new VendorVO();
		
		//讀取json檔案部分
		String str = null;
	    String data="";
	    
//		File jfile = new File(req.getContextPath() + "/front-end/index/campSite_v2.0.json");	//這個不行
		File jfile = new File(getServletContext().getRealPath("/front-end/index/campSite_v2.0.json"));
		 
		FileInputStream fis = new FileInputStream(jfile);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		
		  while((str = br.readLine())!=null) {
              data = data + str + "\n";
		  }
		  	
        List<VendorVO> fileList = new ArrayList();
		JSONArray jsonData;
		try {
			jsonData = new JSONArray(data);
			out.println(jsonData);
    		  for (int i = 0; i < jsonData.length(); i++) {
    			  JSONObject vd_data = jsonData.getJSONObject(i);
    			  String vd_cgname = vd_data.getString("name");
    			  String vd_region = vd_data.getString("region");
    			  double vd_lat = vd_data.getDouble("lat");
    			  double vd_lon = vd_data.getDouble("lon");
    			  String vd_cgaddr = vd_data.getString("address");
    			  
				String vd_email = (genRandom(5)).toString()+ "@" + (genRandom(3)).toString( ) + ".com";
				String vd_pwd = (genRandom(3)).toString();
				String vd_name = (genRandomword(3)).toString();
				String vd_id = (genRandomEn(1)).toString()+ (genRandomnumber(9)).toString();
				java.sql.Date vd_birth = new java.sql.Date((new java.util.Date()).getTime());
				String vd_mobile = (genRandomnumber(10)).toString();
				String vd_cgtel = (genRandomnumber(10)).toString();
				String vd_taxid = (genRandomnumber(8)).toString();
				String vd_acc = (genRandomnumber(14)).toString();
				Integer vd_stat = 1;
				Integer vd_cgstat = 1;
				
				fakeInformation = vendorSvc.addfakeInformation(vd_email, vd_pwd, vd_name,
						vd_id, vd_birth, vd_mobile,
						vd_cgname, vd_cgtel, vd_cgaddr,
						vd_taxid, vd_acc, vd_stat,
						vd_cgstat, vd_lat, vd_lon);
				
				fis.close();
				br.close();
    		 }
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		//輸出json檔案部分
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