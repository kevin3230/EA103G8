package com.wfreport.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.waterfall.model.WFReplyService;
import com.waterfall.model.WaterfallService;
import com.wfreport.model.*;

@WebServlet("/wfreport/wfreport.do")
public class WFReportServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自listAllWfreport.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String rep_no = req.getParameter("rep_no");
				
				/***************************2.開始查詢資料*****************************************/
				WFReportService wfreportSvc = new WFReportService();
				WFReportVO wfreportVO = wfreportSvc.getOneWFReport(rep_no);
				if (wfreportVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/wfreport/listAllWfreport.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("wfreportVO", wfreportVO);
				String url = "/back-end/wfreport/listOneWfreport.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/wfreport/listAllWfreport.jsp");
				failureView.forward(req, res);
			}
		}
		
//============================================================================================================
		
		if ("getOne_For_Update_Pass".equals(action)) { // 來自listOneWfreport.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String rep_no = req.getParameter("rep_no");
				String rep_adminisno = req.getParameter("rep_adminisno");
				String rep_wfno = req.getParameter("rep_wfno");
				String rep_wfrno = req.getParameter("rep_wfrno");
				
				/***************************2.開始修改資料****************************************/
				WFReportService wfreportSvc = new WFReportService();
				WFReportVO wfreportVO = wfreportSvc.updatePassWFReport(rep_adminisno, rep_no);
				if("".equals(rep_wfrno)) {
					WaterfallService waterfallSvc = new WaterfallService();
					waterfallSvc.fakeDeleteWaterfall(rep_wfno);
				}else  {
					WFReplyService wfreplySvc = new WFReplyService();
					wfreplySvc.fakeDeleteWFReply(rep_wfrno);
				}
	
				/***************************3.修改完成,準備轉交(Send the Success view)************/
				req.setAttribute("wfreportVO", wfreportVO); 
				String url = "wfreport.do?action=getOne_For_Display&rep_no="+rep_no;
				res.sendRedirect(url);
				
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/wfreport/wfreport.do?action=getOne_For_Display&rep_no=\"+rep_no");
				failureView.forward(req, res);
			}
		}

//============================================================================================================
		
		if ("getOne_For_Update_Unpass".equals(action)) { // 來自listOneWfreport.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String rep_no = req.getParameter("rep_no");
				String rep_adminisno = req.getParameter("rep_adminisno");
				
				
				/***************************2.開始修改資料****************************************/
				WFReportService wfreportSvc = new WFReportService();
				WFReportVO wfreportVO = wfreportSvc.updateUnpassWFReport(rep_adminisno, rep_no);
				
								
				/***************************3.修改完成,準備轉交(Send the Success view)************/
				req.setAttribute("wfreportVO", wfreportVO);        
				String url = "wfreport.do?action=getOne_For_Display&rep_no="+rep_no;
				res.sendRedirect(url);
//				RequestDispatcher successView = req.getRequestDispatcher(url);
//				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}

//============================================================================================================

        if ("insert_wfreport".equals(action)) {   
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL");
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String wf_no = req.getParameter("wf_no");
				String wfr_no = req.getParameter("wfr_no");
				String mem_no = req.getParameter("mem_no");
				String rep_content = req.getParameter("rep_content");
				if (rep_content == null || rep_content.trim().length() == 0) {
					errorMsgs.add("請勿空白");
				}else if(rep_content.trim().length() > 100) {
					errorMsgs.add("內容請勿超過一百字");
				}	
				
				WFReportVO wfreportVO = new WFReportVO();
				wfreportVO.setRep_wfno(wf_no);
				wfreportVO.setRep_wfrno(wfr_no);
				wfreportVO.setRep_memno(mem_no);
				wfreportVO.setRep_content(rep_content);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wfreportVO", wfreportVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				WFReportService wfreportSvc = new WFReportService();
				wfreportVO = wfreportSvc.addWFReport(wf_no, wfr_no, mem_no, rep_content);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				if("/front-end/waterfall/listOneWaterfall.jsp".equals(requestURL)) {
					String url = requestURL;
					WaterfallService waterfallSvc = new WaterfallService();
					req.setAttribute("waterfallVO", waterfallSvc.getOneWaterfall(wf_no)); // 資料庫取出的waterfallVO物件,存入req
					req.setAttribute("list", waterfallSvc.joinWFReply(wf_no));
					
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);	
				}else if(requestURL == null){
					String url = "/front-end/waterfall/listAllWaterfall.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); 
					successView.forward(req, res);	
					}
				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
//============================================================================================================
        
		
		
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//	
//			try {
//				/***************************1.接收請求參數***************************************/
//				Integer empno = new Integer(req.getParameter("empno"));
//				
//				/***************************2.開始刪除資料***************************************/
//				EmpService empSvc = new EmpService();
//				empSvc.deleteEmp(empno);
//				
//				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
//				String url = "/emp/listAllEmp.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add("刪除資料失敗:"+e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/emp/listAllEmp.jsp");
//				failureView.forward(req, res);
//			}
//		}
	}
}
