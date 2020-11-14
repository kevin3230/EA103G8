package com.vendor.controller;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONException;
import org.json.JSONObject;

import com.vendor.model.*;

@WebServlet("/vendor/VendorServlet")
@MultipartConfig
public class VendorServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); // JSP action
		
		//***業者註冊頁***//
		if ("vdSignUpSubmit".equals(action)) {
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			// add by 敬修，清除註冊前一切資訊
			HttpSession session = req.getSession();
			session.removeAttribute("memVO");
			session.removeAttribute("mem_no");
			session.removeAttribute("location");
			// add by 敬修，清除註冊前一切資訊

			try {
				String str_email = req.getParameter("vd_email");
//				String str_pwd = req.getParameter("vd_pwd");
				String str_name = req.getParameter("vd_name");
				String str_id = req.getParameter("vd_id");
				String str_birth = req.getParameter("vd_birth");
				java.sql.Date date_birth = null;
				String str_mobile = req.getParameter("vd_mobile");
				String str_cgname = req.getParameter("vd_cgname");
				String str_cgtel = req.getParameter("vd_cgtel");
				String str_cgaddr = req.getParameter("vd_cgaddr");
				String str_taxid = req.getParameter("vd_taxid");
				String str_acc = req.getParameter("vd_acc");
				String string_lat = req.getParameter("vd_lat");
				String string_lon = req.getParameter("vd_lon");
				byte[] vd_brc = null;

				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("vd_email", "欄位不能為空，請填入資料");
				}
//				if(str_pwd == null || (str_pwd.trim()).length() == 0) {
//					errorMsgs.put("vd_pwd", "欄位不能為空，請填入資料");
//				}
				if(str_name == null || (str_name.trim()).length() == 0) {
					errorMsgs.put("vd_name", "欄位不能為空，請填入資料");
				}
				if(str_id == null || (str_id.trim()).length() == 0) {
					errorMsgs.put("vd_id", "欄位不能為空，請填入資料");
				}
				if(str_birth == null || (str_birth.trim()).length() == 0) {
					errorMsgs.put("vd_birth", "欄位不能為空，請填入資料");
				}
				if(str_mobile == null || (str_mobile.trim()).length() == 0) {
					errorMsgs.put("vd_mobile", "欄位不能為空，請填入資料");
				}
				if(str_cgname == null || (str_cgname.trim()).length() == 0) {
					errorMsgs.put("vd_cgname", "欄位不能為空，請填入資料");
				}
				if(str_cgtel == null || (str_cgtel.trim()).length() == 0) {
					errorMsgs.put("vd_cgtel", "欄位不能為空，請填入資料");
				}
				if(str_cgaddr == null || (str_cgaddr.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入資料");
				}
				if(str_taxid == null || (str_taxid.trim()).length() == 0) {
					errorMsgs.put("vd_taxid", "欄位不能為空，請填入資料");
				}
				if(str_acc == null || (str_acc.trim()).length() == 0) {
					errorMsgs.put("vd_acc", "欄位不能為空，請填入資料");
				}
				if(string_lat == null || (string_lat.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入正確地址");
				}
				if(string_lon == null || (string_lon.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入正確地址");
				}
				
				VendorVO vendorVO = new VendorVO();				//含有輸入格式錯誤的empVO物件,也需存入req
				vendorVO.setVd_email(str_email);
				vendorVO.setVd_name(str_name);
				vendorVO.setVd_id(str_id);
				vendorVO.setVd_mobile(str_mobile);
				vendorVO.setVd_cgname(str_cgname);
				vendorVO.setVd_cgtel(str_cgtel);
				vendorVO.setVd_cgaddr(str_cgaddr);
				vendorVO.setVd_taxid(str_taxid);
				vendorVO.setVd_acc(str_acc);
				
				req.setAttribute("vendorVO", vendorVO);				//含有輸入格式錯誤的empVO物件,也需存入req
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 信箱是否已被註冊 ************************/
				VendorDAO dao = new VendorDAO();
				VendorVO vdVOcheck = dao.checkByEmail(str_email);
				if (vdVOcheck != null) {
					errorMsgs.put("vd_email", "此信箱已被註冊，請重新確認");
				}
				
				/************************ 信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
					
//				/************************ 密碼格式、長度是否正確 ************************/
//				String pswReg = "^[(a-zA-Z0-9_)]{3,20}$";
//				if (!str_pwd.trim().matches(pswReg)){
//					errorMsgs.put("vd_pwd", "密碼只能是英文字母、數字和_ , 且長度必需在3到20之間");
//				}
							
				/************************ 負責人姓名、格式、長度是否正確 ************************/
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				if (!str_name.trim().matches(nameReg)){
					errorMsgs.put("vd_name", "姓名、暱稱只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				
				/************************ 露營區名稱、格式、長度是否正確 ************************/
				String cgnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,20}$";
				if (!str_cgname.trim().matches(cgnameReg)){
					errorMsgs.put("vd_cgname", "露營區名稱只能是中、英文字母和_ , 且長度必需在1到20之間");
				}
				
				/************************ 身分證字號是否正確 ************************/
				String idReg = "^[A-Z][12][0-9]{8}$";
				if (!str_id.trim().matches(idReg)){
					errorMsgs.put("vd_id", "身分證字號須為英文1碼加數字9碼");
				}
				
				int idNu[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 
				               25, 26, 27, 28, 29, 32, 30, 31, 33};	
				String idEn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				
				String first =str_id.substring(0, 1);
				int index = 0;
				index = idEn.indexOf(first);
				int id0 = ((idNu[index] / 10) + (idNu[index] % 10) * 9);	//第一碼英文字母換算
				
				int id1_8 = 0;
				for(int i=1,j=8;i<9;i++) {
					id1_8 += (str_id.charAt(i) - '0') * j;					//-'0'>> unicode字元轉換 
					j--;
				 	}
				int id9 = (str_id.charAt(9) - 48);							//-48 >>ASCII轉換
				int checkNo = (id1_8 + id0) % 10;
				
				if (((10 - checkNo) % 10) != id9) {
					errorMsgs.put("vd_id", "身分證字號不正確，請重新輸入");
				}
				
				/************************ 生日是否正確 ************************/
				try {
					date_birth = java.sql.Date.valueOf(req.getParameter("vd_birth").trim());
					
					Calendar cal = Calendar.getInstance();
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
					cal.set(Calendar.MILLISECOND,  0 );
					java.util.Date nowCal = cal.getTime();
				    long nowDate = nowCal.getTime();
					long birth = date_birth.getTime();
					
					if (birth >= nowDate) {
						errorMsgs.put("vd_birth", "生日日期不能超過當前日期");
					}
					vendorVO.setVd_birth(date_birth);
					
				}catch (IllegalArgumentException e) {
					errorMsgs.put("vd_birth", "請輸入正確日期格式");
				}
				
				/************************ 負責人手機、連絡電話輸入格式是否正確 ************************/	
				Long vdMobile = null;
				Long vdTel = null;
				try {
					vdMobile = Long.parseLong(str_mobile);
				}catch(NumberFormatException e) {
					errorMsgs.put("vd_mobile", "負責人手機只能填入數字");
				}
				try {
					vdTel = Long.parseLong(str_cgtel);
				}catch(NumberFormatException e) {
					errorMsgs.put("vd_cgtel", "連絡電話只能填入數字");
				}
				
				if ((str_mobile.trim()).length() != 10) {
					errorMsgs.put("vd_mobile", "負責人手機長度不正確");
				}
				
				String telReg = "^[(0-9)]{9,10}$";
				if (!str_cgtel.trim().matches(telReg)){
					errorMsgs.put("vd_cgtel", "連絡電話長度不正確");
				}
				
				/************************ 地址長度是否過長 ************************/
				if ((str_cgaddr.trim()).length() > 65) {
					errorMsgs.put("vd_cgaddrselct", "地址長度最大值為65字元");
				}
				
				/************************ 統一編號是否正確 ************************/
				String taxidReg = "^[0-9]{8}$";
				if (!str_taxid.trim().matches(taxidReg)){
					errorMsgs.put("vd_taxid", "統一編號須為8碼數字");
				}
				
				/************************ 匯款帳戶資訊是否正確 ************************/
				String accReg = "^[0-9]{11,17}$";
				if (!str_acc.trim().matches(accReg)){
					errorMsgs.put("vd_acc", "匯款帳戶須為11碼~17碼數字");
				}
				
				/************************ 露營區緯度是否正確 ************************/
				Double double_lat = null;
				try {
					double_lat = Double.parseDouble(string_lat);
					
					String[] latNums = string_lat.split("\\.");		//split正規表示法，所以.需要跳脫
					int latIntLen = latNums[0].length();					//計算小數點前長度
					int latDecimalLen = latNums[1].length();				//計算小數點後長度
					
					if ( latIntLen != 2 || latDecimalLen != 4) {
						errorMsgs.put("vd_cgaddrselct", "緯度須為整數2碼，小數點4碼之數字");
					}
					vendorVO.setVd_lat(double_lat);					
					
				}catch(Exception e) {
					errorMsgs.put("vd_cgaddrselct", "緯度須為整數2碼，小數點4碼之數字");
				}
								
				/************************ 露營區經度是否正確 ************************/
				Double double_lon = null;
				try {
					double_lon = Double.parseDouble(string_lon);
					
					String[] lonNums = string_lon.split("\\.");		//split正規表示法，所以.需要跳脫
					int lonIntLen = lonNums[0].length();					//計算小數點前長度
					int lonDecimalLen = lonNums[1].length();				//計算小數點後長度
					
					if ( lonIntLen != 3 || lonDecimalLen != 4) {
						errorMsgs.put("vd_cgaddrselct", "經度須為整數3碼，小數點4碼之數字");
					}
					vendorVO.setVd_lon(double_lon);
					
				}catch(Exception e) {
					errorMsgs.put("vd_cgaddrselct", "經度須為整數3碼，小數點4碼之數字");
				}
						
				/************************ 營業登記是否已提供 ************************/
				try {
					Part part = req.getPart("vd_brc");
					InputStream in = part.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024 * 8];
					int len;
					while ((len = in.read(buf)) != -1) {
						baos.write(buf, 0, len);
						baos.flush();
						}
						baos.close();
						in.close();
						
						vd_brc = baos.toByteArray();				// 將baos轉為資料流並存入byte[] vd_brc
					} catch (IOException e) {
						errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					}
				
				if ( vd_brc == null || vd_brc.length ==0) {
					errorMsgs.put("vd_brc", "營業登記欄不得為空");
				}
				
				/************************ 營區地圖是否已提供 ************************/
				byte[] vd_map = null;
				try {
					Part part = req.getPart("vd_map");
					InputStream in = part.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024 * 8];
					int len;
					while ((len = in.read(buf)) != -1) {
						baos.write(buf, 0, len);
						baos.flush();
						}
						baos.close();
						in.close();
						
						vd_map = baos.toByteArray();				// 將baos轉為資料流並存入byte[] vd_brc
					} catch (IOException e) {
						errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					}
				
				if ( vd_map == null || vd_map.length ==0) {
					errorMsgs.put("vd_map", "營區地圖欄不得為空");
				}
				
				req.setAttribute("vendorVO", vendorVO);				//含有輸入格式錯誤的empVO物件,也需存入req			
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
					
				/************************ 資訊無誤，開始註冊會員 ************************/
				VendorService vendorSvc = new VendorService();
				VendorVO vendoVOsignUp = new VendorVO();
				Integer str_stat = 1;		//新註冊帳號業者帳號狀態為正常
				Integer str_cgstat = 0;		//新註冊帳號露營區狀態為關閉
				double_lat = Double.parseDouble(string_lat);
				double_lon = Double.parseDouble(string_lon);
				
				utilities.GetRandom genRandom = new utilities.GetRandom();	//亂數產生
				String verificationcode = genRandom.genRandom(5).toString();
				String str_pwd = verificationcode;		//存入密碼
				
				VdSignUpMail signUpMail = new VdSignUpMail();		//傳送驗證Mail
				signUpMail.sendMail(str_email, str_name, verificationcode);
				
				vendoVOsignUp = vendorSvc.addVendor(str_email, str_pwd, str_name, str_id
						, date_birth, str_mobile, str_cgname, str_cgtel, str_cgaddr,str_taxid
						,str_acc , str_stat, str_cgstat, double_lat, double_lon, vd_brc, vd_map);
	
				errorMsgs.put("SIGNUPOK!", "OK!");
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				String url = req.getContextPath() + "/front-end/index/index.jsp";	// 新增成功後轉交回首頁
//				res.sendRedirect(url);
				
		
			} catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//***業者登入頁***//
		if ("vdSignInSubmit".equals(action)) {
			/************************ 開始驗證登入帳號、密碼 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			// add by 敬修，清除登入前一切資訊
			HttpSession session = req.getSession();
			session.removeAttribute("memVO");
			session.removeAttribute("mem_no");
			session.removeAttribute("location");
			// add by 敬修，清除登入前一切資訊
			
			try {
				String str_email = req.getParameter("vd_email");
				String str_pwd = req.getParameter("vd_pwd");
				
				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("vd_email","欄位不能為空，請填入資料");
				}
				if (str_pwd == null || (str_pwd.trim()).length() == 0) {
					errorMsgs.put("vd_pwd","欄位不能為空，請填入資料");
				}
				
				VendorVO vendorVO = new VendorVO();
				vendorVO.setVd_email(str_email);
				vendorVO.setVd_pwd(str_pwd);
				
				req.setAttribute("vendorVO", vendorVO);				//含有輸入格式錯誤的memVO物件,也需存入req
				
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/************************ 確認業者信箱是否存在 ************************/
				VendorDAO dao = new VendorDAO();
				VendorVO vdVOcheck = dao.checkByEmail(str_email);
				
				if (vdVOcheck == null) {
					errorMsgs.put("vd_email", "此信箱不存在，請重新確認");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 確認業者信箱、密碼是否相符 ************************/
				String pswcheck = vdVOcheck.getVd_pwd();  //取得密碼
				if (!pswcheck.equals(str_pwd)) {
					errorMsgs.put("vd_pwd", "密碼錯誤，請重新確認");
				}

				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 資訊無誤，返回業者頁面 ************************/
				VendorService vendorSvc = new VendorService();
//				HttpSession session = req.getSession();		  					//取得session
				String vd_no = vdVOcheck.getVd_no();   							//取得業者編號
				session.setAttribute("vd_no", vd_no); 							//業者編號存入session
				vendorVO = vendorSvc.getOneVendor(vd_no);				//取得業者資訊
				System.out.println(vendorVO);
				session.setAttribute("vendorVO", vendorVO);						//業者資訊存入session
				String location = (String)session.getAttribute("location"); 	//查看有無來源網頁
				if (location != null) {
					res.sendRedirect(location);				  //如果有，重導回該網頁
				}else {
					res.sendRedirect(req.getContextPath() + "/front-end/vendor/VendorInfo.jsp");	//如果沒有，重導回首頁
				}
				
				
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "vdSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/membersorder/vendorsOrder.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//***業者資料顯示頁(前台)***//
		if ("vdGetInfo".equals(action)) {										//業者專區點選查詢業者資訊
			/************************ 顯示業者基本資料 ************************/
			String url = "/front-end/vendor/VendorInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);		//轉交VendorInfo.jsp
			successView.forward(req, res);
			return;//程式中斷
		}
		
		//***業者資料修改頁(前台)***//
		if ("vdUpdateSubmit".equals(action)) {	
			String vd_no = req.getParameter("vd_no");							//取得業者編號
			VendorService vendorSvc = new VendorService();
			HttpSession session = req.getSession();
			VendorVO vdVOupdate = vendorSvc.getOneVendor(vd_no);				//新增vdVOupdate，不與vendorVO重複
			session.setAttribute("vdVOupdate", vdVOupdate);         			//資料庫取出的vdVOupdate物件,存入session
			session.setAttribute("updateAction", "vdUpdateSubmit");				//儲存updateAction來源位置session
			/************************ 轉交修改業者基本資料頁 ************************/
			String url = "/front-end/vendor/VendorInfoUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);	 	//轉交VendorInfoUpdate.jsp
			successView.forward(req, res);
		}
		
		if ("update".equals(action) || "back-update".equals(action)) { 				// 來自VendorInfoUpdate.jsp的請求
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();

			HttpSession session = req.getSession();
			String updateAction = 
					(session.getAttribute("updateAction")).toString();		//取得updateAction來源位置session
			
			String url = null;
			if("vdUpdateSubmit".equals(updateAction)) {					//判斷異常拋出後轉交回前台或後台頁面
				url = "/front-end/vendor/VendorInfoUpdate.jsp";
			}
			else if ("back_vdUpdateSubmit".equals(updateAction)){
				url = "/back-end/vendor/back_VendorInfoUpdate.jsp";
			}
			
			try {
				String str_no = req.getParameter("vd_no");
				String str_email = req.getParameter("vd_email");
				String str_pwd = req.getParameter("vd_pwd");
				String str_pwd2 = req.getParameter("vd_pwd2");
				String str_name = req.getParameter("vd_name");	
				String str_id = req.getParameter("vd_id");
				java.sql.Date date_birth = null;
				String str_mobile = req.getParameter("vd_mobile");
				String str_cgname = req.getParameter("vd_cgname");
				String str_cgtel = req.getParameter("vd_cgtel");
				String str_cgaddr = req.getParameter("vd_cgaddr");
				String str_taxid = req.getParameter("vd_taxid");
				String str_acc = req.getParameter("vd_acc");
				Date date_regdate = java.sql.Date.valueOf(req.getParameter("vd_regdate"));
				Integer int_stat = Integer.parseInt(req.getParameter("vd_stat"));
				Integer int_cgstat = Integer.parseInt(req.getParameter("vd_cgstat"));
				String string_lat = req.getParameter("vd_lat");
				String string_lon = req.getParameter("vd_lon");
				byte[] vd_brc = null;

				/************************ 欄位不能為空值 ************************/
				if (str_pwd == null	|| (str_pwd.trim()).length() == 0) {
					errorMsgs.put("vd_pwd", "欄位不能為空，請填入資料");
				}
				if (str_pwd2 == null	|| (str_pwd2.trim()).length() == 0) {
					errorMsgs.put("vd_pwd2", "欄位不能為空，請填入資料");
				}
				if (str_name == null || (str_name.trim()).length() == 0) {
					errorMsgs.put("vd_name", "欄位不能為空，請填入資料");
				}
				if (str_id == null || (str_id.trim()).length() == 0) {
					errorMsgs.put("vd_id", "欄位不能為空，請填入資料");
				}
				if (str_mobile == null || (str_mobile.trim()).length() == 0) {
					errorMsgs.put("vd_mobile", "欄位不能為空，請填入資料");
				}
				if (str_cgname == null || (str_cgname.trim()).length() == 0) {
					errorMsgs.put("vd_cgname", "欄位不能為空，請填入資料");
				}
				if (str_cgtel == null || (str_cgtel.trim()).length() == 0) {
					errorMsgs.put("vd_cgtel", "欄位不能為空，請填入資料");
				}
				if (str_cgaddr == null || (str_cgaddr.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入資料");
				}
				if (str_taxid == null || (str_taxid.trim()).length() == 0) {
					errorMsgs.put("vd_taxid", "欄位不能為空，請填入資料");
				}
				if (str_acc == null || (str_acc.trim()).length() == 0) {
					errorMsgs.put("vd_acc", "欄位不能為空，請填入資料");
				}
				if (int_stat == null) {
					errorMsgs.put("vd_stat", "欄位不能為空，請填入資料");
				}
				if (int_cgstat == null) {
					errorMsgs.put("vd_cgstat", "欄位不能為空，請填入資料");
				}
				if (string_lat == null || (string_lat.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入正確地址");
				}
				if (string_lon == null || (string_lon.trim()).length() == 0) {
					errorMsgs.put("vd_cgaddrselct", "欄位不能為空，請填入正確地址");
				}

				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;//程式中斷
				}
								
				/************************ 密碼格式、長度是否正確 ************************/
				String pswReg = "^[(a-zA-Z0-9_)]{3,20}$";
				if (!str_pwd.trim().matches(pswReg)){
					errorMsgs.put("vd_pwd", "密碼只能是英文字母、數字和_ , 且長度必需在3到20之間");
				}
				
				/************************ 密碼重複確認 ************************/			
				if (!str_pwd.equals(str_pwd2)) {
					errorMsgs.put("vd_pwd2", "與確認密碼不符，請再次確認");
				}
				
				/************************ 負責人姓名、格式、長度是否正確 ************************/
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				if (!str_name.trim().matches(nameReg)){
					errorMsgs.put("vd_name", "姓名、暱稱只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				
				/************************ 露營區名稱、格式、長度是否正確 ************************/
				String cgnameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,20}$";
				if (!str_cgname.trim().matches(cgnameReg)){
					errorMsgs.put("vd_cgname", "露營區名稱只能是中、英文字母和_ , 且長度必需在1到20之間");
				}
				
				/************************ 身分證字號是否正確 ************************/
				String idReg = "^[A-Z][12][0-9]{8}$";
				if (!str_id.trim().matches(idReg)){
					errorMsgs.put("vd_id", "身分證字號須為英文1碼加數字9碼");
				}
				
				int idNu[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 
				               25, 26, 27, 28, 29, 32, 30, 31, 33};	
				String idEn = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				
				String first =str_id.substring(0, 1);
				int index = 0;
				index = idEn.indexOf(first);
				int id0 = ((idNu[index] / 10) + (idNu[index] % 10) * 9);	//第一碼英文字母換算
				
				int id1_8 = 0;
				for(int i=1,j=8;i<9;i++) {
					id1_8 += (str_id.charAt(i) - '0') * j;					//-'0'>> unicode字元轉換 
					j--;
				 	}
				int id9 = (str_id.charAt(9) - 48);							//-48 >>ASCII轉換
				int checkNo = (id1_8 + id0) % 10;
				
				if (((10 - checkNo) % 10) != id9) {
					errorMsgs.put("vd_id", "身分證字號不正確，請重新輸入");
				}
				
				/************************ 生日是否正確 ************************/
				try {
					date_birth = java.sql.Date.valueOf(req.getParameter("vd_birth").trim());
					
					Calendar cal = Calendar.getInstance();
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
					cal.set(Calendar.MILLISECOND,  0 );
					java.util.Date nowCal = cal.getTime();
				    long nowDate = nowCal.getTime();
					long birth = date_birth.getTime();
					
					if (birth >= nowDate) {
						errorMsgs.put("vd_birth", "生日日期不能超過當前日期");
					}
					
				}catch (IllegalArgumentException e) {
					errorMsgs.put("vd_birth", "請輸入正確日期格式");
				}
				
				/************************ 負責人手機、連絡電話輸入格式是否正確 ************************/	
				Long vdMobile = null;
				Long vdTel = null;
				try {
					vdMobile = Long.parseLong(str_mobile);
				}catch(NumberFormatException e) {
					errorMsgs.put("vd_mobile", "負責人手機只能填入數字");
				}
				try {
					vdTel = Long.parseLong(str_cgtel);
				}catch(NumberFormatException e) {
					errorMsgs.put("vd_cgtel", "連絡電話只能填入數字");
				}
				
				if ((str_mobile.trim()).length() != 10) {
					errorMsgs.put("vd_mobile", "負責人手機長度不正確");
				}
				
				String telReg = "^[(0-9)]{9,10}$";
				if (!str_cgtel.trim().matches(telReg)){
					errorMsgs.put("vd_cgtel", "連絡電話長度不正確");
				}
				
				/************************ 地址長度是否過長 ************************/
				if ((str_cgaddr.trim()).length() > 65) {
					errorMsgs.put("vd_cgaddrselct", "地址長度最大值為65字元");
				}
				
				/************************ 統一編號是否正確 ************************/
				String taxidReg = "^[0-9]{8}$";
				if (!str_taxid.trim().matches(taxidReg)){
					errorMsgs.put("vd_taxid", "統一編號須為8碼數字");
				}
				
				/************************ 匯款帳戶資訊是否正確 ************************/
				String accReg = "^[0-9]{11,17}$";
				if (!str_acc.trim().matches(accReg)){
					errorMsgs.put("vd_acc", "匯款帳戶須為11碼~17碼數字");
				}
				
				/************************ 露營區緯度是否正確 ************************/
				try {
					Double double_lat = Double.parseDouble(string_lat);
					
					String[] latNums = string_lat.split("\\.");		//split正規表示法，所以.需要跳脫
					int latIntLen = latNums[0].length();					//計算小數點前長度
					int latDecimalLen = latNums[1].length();				//計算小數點後長度
					
					if ( latIntLen != 2 || latDecimalLen != 4) {
						errorMsgs.put("vd_lat", "緯度須為整數2碼，小數點4碼之數字");
					}
				}catch(Exception e) {
					errorMsgs.put("vd_lat", "緯度須為整數2碼，小數點4碼之數字");
				}
				
				/************************ 露營區經度是否正確 ************************/
				try {
					Double double_lon = Double.parseDouble(string_lon);
					
					String[] lonNums = string_lon.split("\\.");		//split正規表示法，所以.需要跳脫
					int lonIntLen = lonNums[0].length();					//計算小數點前長度
					int lonDecimalLen = lonNums[1].length();				//計算小數點後長度
					
					if ( lonIntLen != 3 || lonDecimalLen != 4) {
						errorMsgs.put("vd_lon", "經度須為整數3碼，小數點4碼之數字");
					}
				}catch(Exception e) {
					errorMsgs.put("vd_lon", "經度須為整數3碼，小數點4碼之數字");
				}
				
				/************************ 營業登記是否已提供 ************************/
				try {
					Part part = req.getPart("vd_brc");
					InputStream in = part.getInputStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024 * 8];
					int len;
					while ((len = in.read(buf)) != -1) {
						baos.write(buf, 0, len);
						baos.flush();
						}
						baos.close();
						in.close();
						
						vd_brc = baos.toByteArray();				// 將baos轉為資料流並存入byte[] vd_brc
					} catch (IOException e) {
						errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					}
//				/************************ 營區地圖是否已提供 ************************/
//				byte[] vd_map = null;
//				try {
//					Part part = req.getPart("vd_map");
//					InputStream in = part.getInputStream();
//					ByteArrayOutputStream baos = new ByteArrayOutputStream();
//					byte[] buf = new byte[1024 * 8];
//					int len;
//					while ((len = in.read(buf)) != -1) {
//						baos.write(buf, 0, len);
//						baos.flush();
//						}
//						baos.close();
//						in.close();
//						
//						vd_map = baos.toByteArray();				// 將baos轉為資料流並存入byte[] vd_brc
//					} catch (IOException e) {
//						errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
//					}
				
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 資訊無誤，開始更新業者資訊 ************************/
				VendorService vendorSvc = new VendorService();
				Double double_lat = Double.parseDouble(string_lat);
				Double double_lon = Double.parseDouble(string_lon);
				VendorVO vdVOupdate;

				if ( vd_brc == null || vd_brc.length == 0) {		//沒有更新圖片
					vendorSvc.updatenopicVendor(str_no, str_email, str_pwd, str_name, str_id
							, date_birth, str_mobile, str_cgname, str_cgtel, str_cgaddr, str_taxid, str_acc,
							int_stat, int_cgstat, double_lat, double_lon);
				}else {												//有更新圖片
					vendorSvc.updateVendor(str_no, str_email, str_pwd, str_name, str_id
							, date_birth, str_mobile, str_cgname, str_cgtel, str_cgaddr, str_taxid, str_acc,
							int_stat, int_cgstat, double_lat, double_lon, vd_brc);
				}
				
				vdVOupdate = vendorSvc.getOneVendor(str_no);
				session.setAttribute("vendorVO", vdVOupdate);					//業者資訊更新後同步更新vendorVO的session
				session.removeAttribute("vdVOupdate");							//移除更新用vdVOupdate物件session

				if("vdUpdateSubmit".equals(updateAction)) {					//判斷更新後轉交回前台或後台頁面
					url = "/front-end/vendor/VendorInfo.jsp";
				}
				else if ("back_vdUpdateSubmit".equals(updateAction)){
					url = "/back-end/vendor/serchVendorInfo.jsp";
				}
				session.removeAttribute("updateAction");						//移除來源位置session
				RequestDispatcher successView = req.getRequestDispatcher(url);	//更新成功後轉交回業者專區
				successView.forward(req, res);
				
				
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}				
			}
		}
		
		//***業者資料顯示頁(後台)***//
		if ("getOnebyVd_no".equals(action)) {							//***********業者"編號"單筆查詢***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_no = req.getParameter("vd_no");
				/************************ 欄位不能為空值 ************************/
				if (str_no == null || (str_no.trim()).length() == 0) {
					errorMsgs.put("vd_no", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 業者編號格式、長度是否正確 ************************/
				String vdnoReg = "^V[(0-9)]{9}$";
				if (!str_no.trim().matches(vdnoReg)){
					errorMsgs.put("vd_no", "業者編號必須是英文字母V開頭加9碼數字組成");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				VendorService vendorSvc  = new VendorService();
				VendorVO vendorVO = vendorSvc.getOneVendor(str_no.trim());
				
				if (vendorVO == null) {
					errorMsgs.put("vd_no", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("vendorVO", vendorVO);						 			 // 資料庫取出的vendorVO物件,存入req
				String url = "/back-end/vendor/listOneVendor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);		 // 成功轉交 listOneVendor.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}			
		
		if ("getOnebyVd_email".equals(action)) {							//***********業者"信箱"單筆查詢***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_email = req.getParameter("vd_email");
				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("vd_email", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 業者信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("vd_email", "信箱格式不正確");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				VendorService vendorSvc  = new VendorService();
				VendorVO vendorVO = vendorSvc.signInVendor(str_email.trim());
				
				if (vendorVO == null) {
					errorMsgs.put("vd_email", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("vendorVO", vendorVO);						 			 // 資料庫取出的vendorVO物件,存入req
				String url = "/back-end/vendor/listOneVendor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);			 // 成功轉交 listOneVendor.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}				
			}
		}
		
		if ("listOneDetails".equals(action)) {							//***********業者總表移轉詳細資訊***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_no = req.getParameter("vd_no");
				/************************ 欄位不能為空值 ************************/
				if (str_no == null || (str_no.trim()).length() == 0) {
					errorMsgs.put("vd_no", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				VendorService vendorSvc  = new VendorService();
				VendorVO vendorVO = vendorSvc.getOneVendor(str_no.trim());
				
				if (vendorVO == null) {
					errorMsgs.put("vd_no", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
			
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("vendorVO", vendorVO);						 			 // 資料庫取出的vendorVO物件,存入req
				String url = "/back-end/vendor/listOneVendor.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);		 // 成功轉交 listOneVendor.jsp
				successView.forward(req, res);
			
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/vendor/serchVendorInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}		
			}
		}
		
		//***業者資料修改頁(後台)***//
		if ("back_vdUpdateSubmit".equals(action)) {	
			
			String vd_no = req.getParameter("vd_no");				//取得業者編號
			HttpSession session = req.getSession();
			VendorService vendorSvc  = new VendorService();
			VendorVO vdVOupdate = vendorSvc.getOneVendor(vd_no);				//新增vdVOupdate，不與vendorVO重複
			session.setAttribute("vdVOupdate", vdVOupdate);         			//資料庫取出的vdVOupdate物件,存入session
			session.setAttribute("updateAction", "back_vdUpdateSubmit");		//儲存updateAction來源位置session
			/************************ 轉交修改會員基本資料頁 ************************/
			String url = "/back-end/vendor/back_VendorInfoUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);	 	//轉交VendorInfoUpdate.jsp
			successView.forward(req, res);
		}	
		
		//***業者登出***//
		if ("vdSignOutSubmit".equals(action)) {
			HttpSession session = req.getSession();							//移除vendorVO、vd_no會員資訊session
			session.removeAttribute("vendorVO");
			session.removeAttribute("vd_no");
			// add by 敬修，清除location
			session.removeAttribute("location");
			// add by 敬修，清除location
			
			res.sendRedirect(req.getContextPath() + "/front-end/index/MemVdSignInSignUp.jsp");		//會員登出，重導回登入頁
		}
	}
}
