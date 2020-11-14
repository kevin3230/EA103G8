package com.eqptintro.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.eqptintro.model.*;

public class EqptIntroServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("ei_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備介紹編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqptIntro/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String ei_no = null;
				try {
					ei_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("裝備介紹編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqptIntro/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				EqptIntroService eqptintroSvc = new EqptIntroService();
				EqptIntroVO eqptintroVO = eqptintroSvc.getOneEqptIntro(ei_no);
				if (eqptintroVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqptIntro/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqptintroVO", eqptintroVO); // 資料庫取出的eqptintroVO物件,存入req
				String url = "/back-end/eqptIntro/listOneEqptIntro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEqptintro.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqptIntro/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEqptIntro.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String ei_no = new String(req.getParameter("ei_no"));
				
				/***************************2.開始查詢資料****************************************/
				EqptIntroService eqptintroSvc = new EqptIntroService();
				EqptIntroVO eqptintroVO = eqptintroSvc.getOneEqptIntro(ei_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("eqptintroVO", eqptintroVO);         // 資料庫取出的eqptintroVO物件,存入req
				String url = "/back-end/eqptIntro/update_eqptintro_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_eqptintro_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqptIntro/listAllEqptIntro.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================
		
		if ("update".equals(action)) { // 來自update_eqptintro_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String ei_no = new String(req.getParameter("ei_no").trim());
				
				String ei_adminisno = req.getParameter("ei_adminisno");
				String ei_adminisnoReg = "^[(a-zA-Z0-9)]{10}$";
				if (ei_adminisno == null || ei_adminisno.trim().length() == 0) {
					errorMsgs.add("管理員編號: 請勿空白");
				} else if(!ei_adminisno.trim().matches(ei_adminisnoReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("管理員編號: 只能是英文字母、數字, 且長度必需=10");
	            }
				
				String ei_title = req.getParameter("ei_title").trim();
				if (ei_title == null || ei_title.trim().length() == 0) {
					errorMsgs.add("標題請勿空白");
				}
				
				String ei_content = req.getParameter("ei_content").trim();
				if (ei_content == null || ei_content.trim().length() == 0) {
					errorMsgs.add("內文請勿空白");
				}

				Integer ei_stat = new Integer(req.getParameter("ei_stat").trim());

				EqptIntroVO eqptintroVO = new EqptIntroVO();
				eqptintroVO.setEi_adminisno(ei_adminisno);
				eqptintroVO.setEi_title(ei_title);
				eqptintroVO.setEi_content(ei_content);
				eqptintroVO.setEi_stat(ei_stat);
				eqptintroVO.setEi_no(ei_no);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eqptintroVO", eqptintroVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqptIntro/update_eqptintro_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EqptIntroService eqptintroSvc = new EqptIntroService();
				eqptintroSvc.updateEqptIntro(ei_adminisno, ei_title, ei_content, ei_stat, ei_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("eqptintroVO", eqptintroVO); // 資料庫update成功後,正確的的eqptintroVO物件,存入req
				String url = "/back-end/eqptIntro/listOneEqptIntro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOnelistOneEqptIntro.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("back-end/eqptIntro/update_eqptintro_input.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================

        if ("insert".equals(action)) { // 來自addEqptIntro.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String ei_adminisno = req.getParameter("ei_adminisno");
				String ei_adminisnoReg = "^[(a-zA-Z0-9)]{10}$";
				if (ei_adminisno == null || ei_adminisno.trim().length() == 0) {
					errorMsgs.add("管理員編號: 請勿空白");
				} else if(!ei_adminisno.trim().matches(ei_adminisnoReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("管理員編號: 只能是英文字母、數字 , 且長度必需=10");
	            }
				
				String ei_title = req.getParameter("ei_title").trim();
				if (ei_title == null || ei_title.trim().length() == 0) {
					errorMsgs.add("標題請勿空白");
				}
				
				String ei_content = req.getParameter("ei_content").trim();
				if (ei_content == null || ei_content.trim().length() == 0) {
					errorMsgs.add("內文請勿空白");
				}
				
				
				EqptIntroVO eqptintroVO = new EqptIntroVO();
				eqptintroVO.setEi_adminisno(ei_adminisno);
				eqptintroVO.setEi_title(ei_title);
				eqptintroVO.setEi_content(ei_content);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("eqptintroVO", eqptintroVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/eqptIntro/addEqptIntro.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				EqptIntroService eqptintroSvc = new EqptIntroService();
				eqptintroVO = eqptintroSvc.addEqptIntro(ei_adminisno, ei_title, ei_content);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/EqptIntro/listAllEqptIntro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqptIntro/addEqptIntro.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================
		
		if ("delete".equals(action)) { // 來自listAllEqptIntro.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String ei_no = new String(req.getParameter("ei_no"));
				
				/***************************2.開始刪除資料***************************************/
				EqptIntroService eqptintroSvc = new EqptIntroService();
				eqptintroSvc.deleteEqptIntro(ei_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/eqptIntro/listAllEqptIntro.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/eqptIntro/listAllEqptIntro.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
