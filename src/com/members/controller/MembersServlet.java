package com.members.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.members.model.*;
import com.vendor.controller.*;
import com.vendor.model.*;

@WebServlet("/members/MembersServlet")
public class MembersServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action"); // JSP action
		
		//***會員註冊頁***//
		if ("memSignUpSubmit".equals(action)) {
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			// add by 敬修，清除註冊前一切資訊，但保留location
			HttpSession session = req.getSession();
			session.removeAttribute("memVO");
			session.removeAttribute("mem_no");
			// add by 敬修，清除註冊前一切資訊，但保留location

			try {
				String str_email = req.getParameter("mem_email");
//				String str_pwd = req.getParameter("mem_pwd");
				String str_name = req.getParameter("mem_name");
				String str_alias = req.getParameter("mem_alias");
				String str_gender = req.getParameter("mem_gender");
				String str_birth = req.getParameter("mem_birth");
				java.sql.Date date_birth = null;
				String str_mobile = req.getParameter("mem_mobile");
				String str_tel = req.getParameter("mem_tel");
				String str_addr = req.getParameter("mem_addr");

				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("mem_email", "欄位不能為空，請填入資料");
				}
//				if(str_pwd == null || (str_pwd.trim()).length() == 0) {
//					errorMsgs.put("mem_pwd", "欄位不能為空，請填入資料");
//				}
				if(str_name == null || (str_name.trim()).length() == 0) {
					errorMsgs.put("mem_name", "欄位不能為空，請填入資料");
				}
				if(str_alias == null || (str_alias.trim()).length() == 0) {
					errorMsgs.put("mem_alias", "欄位不能為空，請填入資料");
				}
				if(str_gender == null || (str_gender.trim()).length() == 0) {
					errorMsgs.put("mem_gender", "欄位不能為空，請填入資料");
				}
				if(str_birth == null || (str_birth.trim()).length() == 0) {
					errorMsgs.put("mem_birth", "欄位不能為空，請填入資料");
				}
				if(str_mobile == null || (str_mobile.trim()).length() == 0) {
					errorMsgs.put("mem_mobile", "欄位不能為空，請填入資料");
				}
				if(str_tel == null || (str_tel.trim()).length() == 0) {
					errorMsgs.put("mem_tel", "欄位不能為空，請填入資料");
				}
				if(str_addr == null || (str_addr.trim()).length() == 0) {
					errorMsgs.put("mem_addrselct", "欄位不能為空，請填入資料");
				}
				
				MembersVO memVO = new MembersVO();				//含有輸入格式錯誤的empVO物件,也需存入req
				memVO.setMem_email(str_email);
				memVO.setMem_name(str_name);
				memVO.setMem_alias(str_alias);
				memVO.setMem_gender(str_gender);
				memVO.setMem_mobile(str_mobile);
				memVO.setMem_tel(str_tel);
				memVO.setMem_addr(str_addr);
				
				req.setAttribute("memVO", memVO);				//含有輸入格式錯誤的memVO物件,也需存入req
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 信箱是否已被註冊 ************************/
				MembersDAO dao = new MembersDAO();
				MembersVO memVOcheck = dao.checkByEmail(str_email);
				if (memVOcheck != null) {
					errorMsgs.put("mem_email", "此信箱已被註冊，請重新確認");
				}
				
				/************************ 信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				
//				/************************ 密碼格式、長度是否正確 ************************/
//				String pswReg = "^[(a-zA-Z0-9_)]{3,20}$";
//				if (!str_pwd.trim().matches(pswReg)){
//					errorMsgs.put("mem_pwd", "密碼只能是英文字母、數字和_ , 且長度必需在3到20之間");
//				}
							
				/************************ 姓名、暱稱格式、長度是否正確 ************************/
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				String aliasReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				if (!str_name.trim().matches(nameReg)){
					errorMsgs.put("mem_name", "姓名只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				if (!str_alias.trim().matches(aliasReg)){
					errorMsgs.put("mem_alias", "暱稱只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				
				/************************ 性別是否正確 ************************/
				if (!((str_gender.trim()).equals("男") || str_gender.trim().equals("女") || str_gender.trim().equals("無"))) {
					errorMsgs.put("mem_gender", "請輸入正確性別男or女or無");
				}
				
				/************************ 生日是否正確 ************************/
				try {
					date_birth = java.sql.Date.valueOf(req.getParameter("mem_birth").trim());
					
					Calendar cal = Calendar.getInstance();
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
					cal.set(Calendar.MILLISECOND,  0 );
					java.util.Date nowCal = cal.getTime();
				    long nowDate = nowCal.getTime();
					long birth = date_birth.getTime();
					
					if (birth >= nowDate) {
						errorMsgs.put("mem_birth", "生日日期不能超過當前日期");
					}
					memVO.setMem_birth(date_birth);
					
				}catch (IllegalArgumentException e) {
					errorMsgs.put("mem_birth", "請輸入正確日期格式");
				}
				
				/************************ 手機、市話輸入格式是否正確 ************************/	
				Long memMobile = null;
				Long memTel = null;
				try {
					memMobile = Long.parseLong(str_mobile);
				}catch(NumberFormatException e) {
					errorMsgs.put("mem_mobile", "手機只能填入數字");
				}
				try {
					memTel = Long.parseLong(str_tel);
				}catch(NumberFormatException e) {
					errorMsgs.put("mem_tel", "市話只能填入數字");
				}
				
				if ((str_mobile.trim()).length() != 10) {
					errorMsgs.put("mem_mobile", "手機號碼長度不正確");
				}
				
				String telReg = "^[(0-9)]{9,10}$";
				if (!str_tel.trim().matches(telReg)){
					errorMsgs.put("mem_tel", "市話號碼長度不正確");
				}
				
				/************************ 地址長度是否過長 ************************/
				if ((str_addr.trim()).length() > 65) {
					errorMsgs.put("mem_addrselct", "地址長度最大值為65字元");
				}
				
				req.setAttribute("memVO", memVO);				//含有輸入格式錯誤的memVO物件,也需存入req
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 資訊無誤，開始註冊會員 ************************/
				MembersService memSvc = new MembersService();
				MembersVO memVOsignUp = new MembersVO();
				Integer int_type = 1;		//新註冊帳號會員類型為一般會員
				Integer int_stat = 1;		//新註冊帳號帳號狀態為有效
				
				utilities.GetRandom genRandom = new utilities.GetRandom();	//亂數產生
				String verificationcode = genRandom.genRandom(5).toString();
				String str_pwd = verificationcode;		//存入密碼

				MemSignUpMail signUpMail = new MemSignUpMail();		//傳送驗證Mail
				signUpMail.sendMail(str_email, str_name, verificationcode);
				
				memVOsignUp = memSvc.signUpMem(str_email, str_pwd, str_name, str_alias
						, str_gender, date_birth, str_mobile, str_tel, str_addr
						, int_type, int_stat);
	
				errorMsgs.put("SIGNUPOK!", "OK!");
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
//				String url = req.getContextPath() + "/front-end/index/index.jsp";	// 新增成功後轉交回首頁
//				res.sendRedirect(url);
				
				
			} catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料:" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignUp");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//***會員登入頁***//
		if ("memSignInSubmit".equals(action)) {
			/************************ 開始驗證登入帳號、密碼 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			// add by 敬修，清除登入前一切資訊，但保留location
			HttpSession session = req.getSession();
			session.removeAttribute("memVO");
			session.removeAttribute("mem_no");
			// add by 敬修，清除登入前一切資訊，但保留location

			try {
				String str_email = req.getParameter("mem_email");
				String str_pwd = req.getParameter("mem_pwd");
				
				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("mem_email", "欄位不能為空，請填入資料");
				}
				if (str_pwd == null || (str_pwd.trim()).length() == 0){
					errorMsgs.put("mem_pwd", "欄位不能為空，請填入資料");
				}
				
				MembersVO memVO = new MembersVO();
				memVO.setMem_email(str_email);
				memVO.setMem_pwd(str_pwd);
				
				req.setAttribute("memVO", memVO);				//含有輸入格式錯誤的memVO物件,也需存入req
				
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 確認會員信箱是否存在 ************************/
				MembersDAO dao = new MembersDAO();
				MembersVO memVOcheck = dao.checkByEmail(str_email);
				
				if (memVOcheck == null) {
					errorMsgs.put("mem_email", "此信箱不存在，請重新確認");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 確認會員信箱、密碼是否相符 ************************/
				String pswcheck = memVOcheck.getMem_pwd();  //取得密碼
				if (!pswcheck.equals(str_pwd)) {
					errorMsgs.put("mem_pwd", "密碼錯誤，請重新確認");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignIn");
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 資訊無誤，返回會員頁面 ************************/
				MembersService memSvc = new MembersService();
//				HttpSession session = req.getSession();		  					//取得session
				String mem_no = memVOcheck.getMem_no();   						//取得會員編號
				session.setAttribute("mem_no", mem_no); 						//會員編號存入session
				memVO = memSvc.getOneMem(mem_no);						//取得會員資訊
				session.setAttribute("memVO", memVO);							//會員資訊存入session
				String location = (String)session.getAttribute("location"); 	//查看有無來源網頁
				if (location != null) {
					res.sendRedirect(location);				  //如果有，重導回該網頁
				}else {
					res.sendRedirect(req.getContextPath() + "/front-end/members/MembersInfo.jsp");	//如果沒有，重導回首頁
				}
				
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					req.setAttribute("toSignInSignUp", "memSignIn");
					System.out.println(req.getAttribute("action"));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/MemVdSignInSignUp.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//***會員資料顯示頁(前台)***//
		if ("memGetInfo".equals(action)) {										//會員專區點選查詢會員資訊
			/************************ 顯示會員基本資料 ************************/
			String url = "/front-end/members/MembersInfo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);		//轉交MembersInfo.jsp
			successView.forward(req, res);
			return;//程式中斷
		}
		
		//***會員資料修改頁(前台)***//
		if ("memUpdateSubmit".equals(action)) {	
			String mem_no = req.getParameter("mem_no");							//取得會員編號
			MembersService memSvc = new MembersService();
			HttpSession session = req.getSession();
			MembersVO memVOupdate = memSvc.getOneMem(mem_no);					//新增memVOupdate，不與memVO重複
			session.setAttribute("memVOupdate", memVOupdate);         			//資料庫取出的memVOupdate物件,存入session
			session.setAttribute("updateAction", "memUpdateSubmit");			//儲存updateAction來源位置session
			/************************ 轉交修改會員基本資料頁 ************************/
			String url = "/front-end/members/MembersInfoUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);	 	//轉交MembersInfoUpdate.jsp
			successView.forward(req, res);
		}
		
		if ("update".equals(action) || "back-update".equals(action)) { 				// 來自MembersInfoUpdate.jsp的請求
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();

			HttpSession session = req.getSession();
			String updateAction = 
					(session.getAttribute("updateAction")).toString();		//取得updateAction來源位置session
			
			String url = null;
			if("memUpdateSubmit".equals(updateAction)) {					//判斷異常拋出後轉交回前台或後台頁面
				url = "/front-end/members/MembersInfoUpdate.jsp";
			}
			else if ("back_memUpdateSubmit".equals(updateAction)){
				url = "/back-end/members/back_MembersInfoUpdate.jsp";
			}
			
			try {
				String str_no = req.getParameter("mem_no");
				String str_email = req.getParameter("mem_email");
				String str_pwd = req.getParameter("mem_pwd");
				String str_pwd2 = req.getParameter("mem_pwd2");
				String str_name = req.getParameter("mem_name");
				String str_alias = req.getParameter("mem_alias");
				String str_gender = req.getParameter("mem_gender");
				java.sql.Date date_birth = null;
				String str_mobile = req.getParameter("mem_mobile");
				String str_tel = req.getParameter("mem_tel");
				String str_addr = req.getParameter("mem_addr");
				Integer int_type = Integer.parseInt(req.getParameter("mem_type"));
				Date date_regdate = java.sql.Date.valueOf(req.getParameter("mem_regdate"));
				Integer int_stat = Integer.parseInt(req.getParameter("mem_stat"));
				
				/************************ 欄位不能為空值 ************************/
				if (str_pwd == null	|| (str_pwd.trim()).length() == 0) {
					errorMsgs.put("mem_pwd", "欄位不能為空，請填入資料");
				}
				if (str_pwd2 == null	|| (str_pwd2.trim()).length() == 0) {
					errorMsgs.put("mem_pwd", "欄位不能為空，請填入資料");
				}
				if(str_name == null || (str_name.trim()).length() == 0) {
					errorMsgs.put("mem_name", "欄位不能為空，請填入資料");
				}
				if(str_alias == null || (str_alias.trim()).length() == 0) {
					errorMsgs.put("mem_alias", "欄位不能為空，請填入資料");
				}
				if(str_gender == null || (str_gender.trim()).length() == 0) {
					errorMsgs.put("mem_gender", "欄位不能為空，請填入資料");
				}
				if(str_mobile == null || (str_mobile.trim()).length() == 0) {
					errorMsgs.put("mem_mobile", "欄位不能為空，請填入資料");
				}
				if(str_tel == null || (str_tel.trim()).length() == 0) {
					errorMsgs.put("mem_tel", "欄位不能為空，請填入資料");
				}
				if(str_addr == null || (str_addr.trim()).length() == 0) {
					errorMsgs.put("mem_addrselct", "欄位不能為空，請填入資料");
				}
				if(int_type == null) {
					errorMsgs.put("mem_type", "欄位不能為空，請填入資料");
				}
				if(int_stat == null) {
					errorMsgs.put("mem_stat", "欄位不能為空，請填入資料");
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
					errorMsgs.put("mem_pwd", "密碼只能是英文字母、數字和_ , 且長度必需在3到20之間");
				}
				
				/************************ 密碼重複確認 ************************/
				if (!str_pwd.equals(str_pwd2)) {
					errorMsgs.put("mem_pwd2", "與確認密碼不符，請再次確認");
				}
				
				/************************ 姓名、暱稱格式、長度是否正確 ************************/
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				String aliasReg = "^[(\u4e00-\u9fa5)(a-zA-Z_)]{1,10}$";
				if (!str_name.trim().matches(nameReg)){
					errorMsgs.put("mem_name", "姓名只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				if (!str_alias.trim().matches(aliasReg)){
					errorMsgs.put("mem_alias", "暱稱只能是中、英文字母和_ , 且長度必需在1到10之間");
				}
				
				/************************ 性別是否正確 ************************/
				if (!(str_gender.equals("男") || str_gender.equals("女") || str_gender.equals("無"))) {
					errorMsgs.put("mem_gender", "請輸入正確性別男or女or無");
				}
				
				/************************ 生日是否正確 ************************/
				try {
					date_birth = java.sql.Date.valueOf(req.getParameter("mem_birth").trim());
					
					Calendar cal = Calendar.getInstance();
					cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
					cal.set(Calendar.MILLISECOND,  0 );
					java.util.Date nowCal = cal.getTime();
				    long nowDate = nowCal.getTime();
					long birth = date_birth.getTime();
					
					if (birth >= nowDate) {
						errorMsgs.put("mem_birth", "生日日期不能超過當前日期");
					}
					
				}catch (IllegalArgumentException e) {
					errorMsgs.put("mem_birth", "請輸入正確日期格式");
				}
						
				/************************ 手機、市話輸入格式是否正確 ************************/	
				Long memMobile = null;
				Long memTel = null;
				try {
					memMobile = Long.parseLong(str_mobile);
				}catch(NumberFormatException e) {
					errorMsgs.put("mem_mobile", "手機只能填入數字");
				}
				try {
					memTel = Long.parseLong(str_tel);
				}catch(NumberFormatException e) {
					errorMsgs.put("mem_tel", "市話只能填入數字");
				}
				
				if ((str_mobile.trim()).length() != 10) {
					errorMsgs.put("mem_mobile", "手機號碼長度不正確");
				}
				
				String telReg = "^[(0-9)]{9,10}$";
				if (!str_tel.trim().matches(telReg)){
					errorMsgs.put("mem_tel", "市話號碼長度不正確");
				}
				
				/************************ 地址長度是否過長 ************************/
				if ((str_addr.trim()).length() > 65) {
					errorMsgs.put("mem_addrselct", "地址長度最大值為65字元");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 資訊無誤，開始更新會員資訊 ************************/
				MembersService memSvc = new MembersService();
				MembersVO memVOupdate = memSvc.updateMem(str_no, str_email, str_pwd, str_name, str_alias
						, str_gender, date_birth, str_mobile, str_tel, str_addr, int_type, int_stat);
				
				memVOupdate = memSvc.getOneMem(str_no);
				session.setAttribute("memVO", memVOupdate);						//會員資訊更新後同步更新memVO的session
				session.removeAttribute("memVOupdate");							//移除更新用memVOupdate物件session

				if("memUpdateSubmit".equals(updateAction)) {					//判斷更新後轉交回前台或後台頁面
					url = "/front-end/members/MembersInfo.jsp";
				}
				else if ("back_memUpdateSubmit".equals(updateAction)){
					url = "/back-end/members/serchMemInfo.jsp";
				}
				session.removeAttribute("updateAction");						//移除來源位置session
				RequestDispatcher successView = req.getRequestDispatcher(url);	//更新成功後轉交回會員專區
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
		
		//***會員資料顯示頁(後台)***//
		if ("getOnebyMem_no".equals(action)) {							//***********會員"編號"單筆查詢***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_no = req.getParameter("mem_no");
				/************************ 欄位不能為空值 ************************/
				if (str_no == null || (str_no.trim()).length() == 0) {
					errorMsgs.put("mem_no", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 會員編號格式、長度是否正確 ************************/
				String memnoReg = "^M[(0-9)]{9}$";
				if (!str_no.trim().matches(memnoReg)){
					errorMsgs.put("mem_no", "會員編號必須是英文字母M開頭加9碼數字組成");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				MembersService memSvc  = new MembersService();
				MembersVO memVO = memSvc.getOneMem(str_no.trim());
				
				if (memVO == null) {
					errorMsgs.put("mem_no", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("memVO", memVO);						 			 // 資料庫取出的memVO物件,存入req
				String url = "/back-end/members/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);		 // 成功轉交 listOneMem.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}			
		
		if ("getOnebyMem_email".equals(action)) {							//***********會員"信箱"單筆查詢***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_email = req.getParameter("mem_email");
				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("mem_email", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 會員信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if(mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if(mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("mem_email", "信箱格式不正確");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				MembersService memSvc  = new MembersService();
				MembersVO memVO = memSvc.signInMem(str_email.trim());
				
				if (memVO == null) {
					errorMsgs.put("mem_email", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("memVO", memVO);						 			 // 資料庫取出的memVO物件,存入req
				String url = "/back-end/members/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);		 // 成功轉交 listOneMem.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if ("listOneDetails".equals(action)) {							//***********會員總表移轉詳細資訊***********
			/************************ 開始驗證提交表單 ************************/
			JSONObject errorMsgs = new JSONObject();
			
			try {
				String str_no = req.getParameter("mem_no");
				/************************ 欄位不能為空值 ************************/
				if (str_no == null || (str_no.trim()).length() == 0) {
					errorMsgs.put("mem_no", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************開始查詢資料***************************/
				MembersService memSvc  = new MembersService();
				MembersVO memVO = memSvc.getOneMem(str_no.trim());
				
				if (memVO == null) {
					errorMsgs.put("mem_no", "查無資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
			
				/*********************查詢完成,準備轉交(Send the Success view)*********************/
				req.setAttribute("memVO", memVO);						 			 // 資料庫取出的vendorVO物件,存入req
				String url = "/back-end/members/listOneMem.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);		 // 成功轉交 listOneVendor.jsp
				successView.forward(req, res);
			
				/***************************其他可能的錯誤處理***************************/
			}catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/members/serchMemInfo.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}
		}
	
		//***會員資料修改頁(後台)***//
		if ("back_memUpdateSubmit".equals(action)) {	
			
			String mem_no = req.getParameter("mem_no");				//取得會員編號
			HttpSession session = req.getSession();
			MembersService memSvc = new MembersService();
			MembersVO memVOupdate = memSvc.getOneMem(mem_no);					//新增memVOupdate，不與memVO重複
			session.setAttribute("memVOupdate", memVOupdate);         			//資料庫取出的memVOupdate物件,存入session
			session.setAttribute("updateAction", "back_memUpdateSubmit");		//儲存updateAction來源位置session
			/************************ 轉交修改會員基本資料頁 ************************/
			String url = "/back-end/members/back_MembersInfoUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);	 	//轉交MembersInfoUpdate.jsp
			successView.forward(req, res);
		}	
		
		//***會員登出***//
		if ("memSignOutSubmit".equals(action)) {
			HttpSession session = req.getSession();							//移除memVO、mem_no會員資訊session
			session.removeAttribute("memVO");
			session.removeAttribute("mem_no");
			// add by 敬修，清除location
			session.removeAttribute("location");
			// add by 敬修，清除location
			
			res.sendRedirect(req.getContextPath() + "/front-end/index/MemVdSignInSignUp.jsp");		//會員登出，重導回登入頁
		}
		
		//***忘記密碼***//
		if ("getPassword".equals(action)) {
			JSONObject errorMsgs = new JSONObject();
			
			try {
				MembersService memSvc = new MembersService();
				MembersVO memVO = new MembersVO();
				VendorService vendorSvc = new VendorService();
				VendorVO vendorVO = new VendorVO();
				
				String str_email = req.getParameter("password_email");			//取得會員編號
				
				/************************ 欄位不能為空值 ************************/
				if (str_email == null || (str_email.trim()).length() == 0) {
					errorMsgs.put("password_email", "欄位不能為空，請填入資料");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/ForgetPassword.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 信箱格式是否正確 ************************/		
				int mailcomfirm = str_email.indexOf("@");					
				if (mailcomfirm == -1 || mailcomfirm == 0 || 						//沒有@或@前無值
						(str_email.substring(mailcomfirm + 1)).length() == 0) {		//@後無值
					errorMsgs.put("password_email", "信箱格式不正確");
				}
				
				int mailcomfirm2 = str_email.indexOf(".");					
				if (mailcomfirm2 == -1 || mailcomfirm2 == 0 || 						//沒有.或.前無值
						(str_email.substring(mailcomfirm2 + 1)).length() == 0) {	//.後無值
					errorMsgs.put("password_email", "信箱格式不正確");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/ForgetPassword.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/************************ 確認信箱是否存在,如有開始查詢信箱資料 ************************/
				memVO = memSvc.signInMem(str_email);
				vendorVO = vendorSvc.signInVendor(str_email);
				
				if (memVO != null) {
					utilities.GetRandom genRandom = new utilities.GetRandom();	//亂數產生
					String verificationcode = genRandom.genRandom(5).toString();
					String str_pwd = verificationcode;
					String str_no = memVO.getMem_no();
					String str_name = memVO.getMem_name();
					
					GetMemPasswordSignUpMail signUpMail = new GetMemPasswordSignUpMail();		//傳送驗證Mail
					signUpMail.sendMail(str_email, str_name, verificationcode);
					
					memVO = memSvc.pswupdateMem(str_no, str_pwd);
					
				}
				
				if (vendorVO != null){
					utilities.GetRandom genRandom = new utilities.GetRandom();	//亂數產生
					String verificationcode = genRandom.genRandom(5).toString();
					String str_pwd = verificationcode;
					String str_no = vendorVO.getVd_no();
					String str_name = vendorVO.getVd_name();
					
					GetVdPasswordSignUpMail signUpMail = new GetVdPasswordSignUpMail();		//傳送驗證Mail
					signUpMail.sendMail(str_email, str_name, verificationcode);
					
					vendorVO = vendorSvc.pswupdateVendor(str_no, str_pwd);
					
				}
				if (memVO == null && vendorVO == null) {
					errorMsgs.put("password_email", "此信箱不存在，請重新確認");
				}
				//轉送回提交頁面
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/ForgetPassword.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				errorMsgs.put("OK!", "OK!");
				if (errorMsgs.length() != 0) {
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/ForgetPassword.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
			} catch (Exception e) {
				try {
					errorMsgs.put("otherEx", "無法取得資料:" + e.getMessage());
					req.setAttribute("errorMsgs", (errorMsgs.toString()));
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/index/ForgetPassword.jsp");
					failureView.forward(req, res);
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			}	
		}
		
	}
}
