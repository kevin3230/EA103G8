package com.adminis.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminis.model.*;

import javax.servlet.annotation.WebServlet;
@WebServlet("/adminis/adminis.do")
public class AdminisServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6797415726732746314L;

	protected boolean allowUser(String adminis_no, String adminis_pwd) {
		
		AdminisService adminisSvc = new AdminisService();
	    List<AdminisVO> list = adminisSvc.getAll();
	    for(AdminisVO adminis : list) {
	    	if(adminis_no.equals(adminis.getAdminis_no()) && adminis_pwd.equals(adminis.getAdminis_pwd())) {
	    		return true;
	    	}
	    }
		return false;
    }
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("adminis_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入員工編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				String adminis_no = null;
				try {
					adminis_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				AdminisService adminisSvc = new AdminisService();
				AdminisVO adminisVO = adminisSvc.getOneAdminis(adminis_no);
				if (adminisVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("adminisVO", adminisVO); // 資料庫取出的adminisVO物件,存入req
				String url = "/back-end/adminis/listOneAdminis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAdminis.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addAdminis.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String adminis_name = req.getParameter("adminis_name");
				String adminis_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (adminis_name == null || adminis_name.trim().length() == 0) {
					errorMsgs.add("姓名: 請勿空白");
				} else if (!adminis_name.trim().matches(adminis_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}
//
//				String adminis_pwd = req.getParameter("adminis_pwd").trim();
//				if (adminis_pwd == null || adminis_pwd.trim().length() == 0) {
//					errorMsgs.add("密碼: 請勿空白");
//				}

				String adminis_email = req.getParameter("adminis_email").trim();
				if (adminis_email == null || adminis_email.trim().length() == 0) {
					errorMsgs.add("EMAIL: 請勿空白");
				}

				String adminis_dept = req.getParameter("adminis_dept").trim();
				if (adminis_dept == null || adminis_dept.trim().length() == 0) {
					errorMsgs.add("部門: 請勿空白");
				}

				Integer adminis_pv = null;
				try {
					adminis_pv = new Integer(req.getParameter("adminis_pv").trim());
				} catch (NumberFormatException e) {
					adminis_pv = 2047;
					errorMsgs.add("權限值: 請填數字.");
				}
				


				AdminisVO adminisVO = new AdminisVO();
				adminisVO.setAdminis_name(adminis_name);
//				adminisVO.setAdminis_pwd(adminis_pwd);
				adminisVO.setAdminis_email(adminis_email);
				adminisVO.setAdminis_dept(adminis_dept);
				adminisVO.setAdminis_pv(adminis_pv);

				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminisVO", adminisVO); // 含有輸入格式錯誤的adminisVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/addAdminis.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				AdminisService adminisSvc = new AdminisService();
				adminisVO = adminisSvc.addAdminis(adminis_name, getAuthCode(), adminis_email, adminis_dept, adminis_pv);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/adminis/listAllAdminis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllAdminis.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/addAdminis.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllAdminis.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String adminis_no = new String(req.getParameter("adminis_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				AdminisService adminisSvc = new AdminisService();
				AdminisVO adminisVO = adminisSvc.getOneAdminis(adminis_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("adminisVO", adminisVO); // 資料庫取出的adminisVO物件,存入req
				String url = "/back-end/adminis/update_adminis_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_adminis_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/listAllAdminis.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_adminis_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String adminis_no = new String(req.getParameter("adminis_no").trim());

				String adminis_name = req.getParameter("adminis_name");
				String adminis_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (adminis_name == null || adminis_name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if (!adminis_name.trim().matches(adminis_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String adminis_pwd = req.getParameter("adminis_pwd").trim();
				if (adminis_pwd == null || adminis_pwd.trim().length() == 0) {
					errorMsgs.add("密碼: 請勿空白");
				}

				String adminis_email = req.getParameter("adminis_email").trim();
				if (adminis_email == null || adminis_email.trim().length() == 0) {
					errorMsgs.add("EMAIL: 請勿空白");
				}

				String adminis_dept = req.getParameter("adminis_dept").trim();
				if (adminis_dept == null || adminis_dept.trim().length() == 0) {
					errorMsgs.add("部門: 請勿空白");
				}
				
				Integer adminis_pv = null;
				try {
					adminis_pv = new Integer(req.getParameter("adminis_pv").trim());
				} catch (NumberFormatException e) {
					adminis_pv = 2047;
					errorMsgs.add("權限值: 請填數字.");
				}
				

				AdminisVO adminisVO = new AdminisVO();
				adminisVO.setAdminis_no(adminis_no);
				adminisVO.setAdminis_name(adminis_name);
				adminisVO.setAdminis_pwd(adminis_pwd);
				adminisVO.setAdminis_email(adminis_email);
				adminisVO.setAdminis_dept(adminis_dept);
				adminisVO.setAdminis_pv(adminis_pv);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminisVO", adminisVO); // 含有輸入格式錯誤的adminisVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/adminis/update_adminis_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}
				/*************************** 2.開始修改資料 *****************************************/
				AdminisService adminisSvc = new AdminisService();
				adminisVO = adminisSvc.updateAdminis(adminis_no, adminis_name, adminis_pwd, adminis_email, adminis_dept,
						adminis_pv);
				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				adminisVO = adminisSvc.getOneAdminis(adminis_no);
				req.setAttribute("adminisVO", adminisVO); // 資料庫update成功後,正確的的adminisVO物件,存入req
				String url = "/back-end/adminis/listOneAdminis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneAdminis.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/update_adminis_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllAdminis.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String adminis_no = new String(req.getParameter("adminis_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				AdminisService adminisSvc = new AdminisService();
				adminisSvc.deleteAdminis(adminis_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/adminis/listAllAdminis.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/listAllAdminis.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("swap".equals(action)) { // 來自listAllAdminis.jsp
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				HttpSession session = req.getSession();
				String adminis_no = req.getParameter("adminis_no");
				Integer adminis_stat =new Integer( req.getParameter("adminis_stat"));

				/*************************** 2.開始修改資料 ***************************************/
				AdminisService adminisSvc = new AdminisService();
				adminisSvc.swapAdminis(adminis_no, adminis_stat );
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//				String url = "/back-end/adminis/listAllAdminis.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
				try {
					String location = (String) session.getAttribute("location");
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}	
				

				res.sendRedirect(req.getContextPath() + "/back-end/index.jsp"); // *工作3:
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/adminis/listAllAdminis.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("login".equals(action)) {
		String adminis_no = req.getParameter("adminis_no");
		String adminis_pwd = req.getParameter("adminis_pwd");
		
		// 【檢查該帳號 , 密碼是否有效】
		if (!allowUser(adminis_no, adminis_pwd)) { // 【帳號 , 密碼無效時】
			out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
			out.println("<BODY>你的帳號 , 密碼無效!<BR>");
			out.println("請按此重新登入 <A HREF=" + req.getContextPath() + "/back-end/back-end-login.jsp>重新登入</A>");
			out.println("</BODY></HTML>");
		} else { // 【帳號 , 密碼有效時, 才做以下工作】
			HttpSession session = req.getSession();
			AdminisVO adminisVO = new AdminisVO();
			AdminisService adminisSvc = new AdminisService();
			List<AdminisVO> list = adminisSvc.getAll();
			for (AdminisVO adminis : list) {
				if (adminis_no.equals(adminis.getAdminis_no())) {
					adminisVO = adminis;
					break;
				}
			}
			adminisVO = adminisSvc.getOneAdminis(adminis_no);
			session.setAttribute("adminis_no", adminis_no); // *工作1: 才在session內做已經登入過的標識
			session.setAttribute("adminisVO", adminisVO);

			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
			}

			res.sendRedirect(req.getContextPath() + "/back-end/wfreport/listAllWfreport.jsp"); // *工作3:
																					// (-->如無來源網頁:則重導至login_success.jsp)
		}
		}
		
		
	}
	
	static String getAuthCode() {
		  String auth = "";
		        int[] random = new int[62];
		        int index = 0;
		        for (int i = '0'; i <= 'z'; i++) {
		            random[index] = i;
		            if(i == '9') {
		                i = 'A' - 1;
		            }
		            if(i == 'Z') {
		                i = 'a' - 1;
		            }
		            index++;
		        }
		        for(int i = 1; i <= 6; i++){
		            auth += (char)random[(int)(Math.random()*62)];
		        }
		        return auth;
		    }
	}

