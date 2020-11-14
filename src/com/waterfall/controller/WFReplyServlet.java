package com.waterfall.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.waterfall.model.*;

public class WFReplyServlet extends HttpServlet {

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
				String str = req.getParameter("wfr_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入文章回覆編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/wfreply/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String wfr_no = null;
				try {
					wfr_no = new String(str);
				} catch (Exception e) {
					errorMsgs.add("文章回覆編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/wfreply/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				WFReplyVO wfreplyVO = wfreplySvc.getOneWFReply(wfr_no);
				if (wfreplyVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/wfreply/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("wfreplyVO", wfreplyVO); // 資料庫取出的wfreplyVO物件,存入req
				String url = "/front-end/wfreply/listOneWfr.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneWfr.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/wfreply/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllWfr.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String wfr_no = new String(req.getParameter("wfr_no"));
				
				/***************************2.開始查詢資料****************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				WFReplyVO wfreplyVO = wfreplySvc.getOneWFReply(wfr_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("wfreplyVO", wfreplyVO);         // 資料庫取出的wfreplyVO物件,存入req
				String url = "/front-end/wfreply/update_wfr_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_wfr_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/wfreply/listAllWfr.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================		
		
		if ("update".equals(action)) { // 來自update_wfreply_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String wfr_no = req.getParameter("wfr_no");
				String wfr_wfno = req.getParameter("wfr_wfno");
				
				String wfr_content = req.getParameter("wfr_content").trim();
				if (wfr_content == null || wfr_content.trim().length() == 0) {
					errorMsgs.add("回覆內容請勿空白");
				}else if(wfr_content.trim().length() > 100)	{
					errorMsgs.add("回覆內容請勿超過一百字");
				}

				
				WFReplyVO wfreplyVO = new WFReplyVO();
				wfreplyVO.setWfr_content(wfr_content);
				wfreplyVO.setWfr_no(wfr_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wfreplyVO", wfreplyVO); // 含有輸入格式錯誤的wfreplyVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/wfreply/update_wfr_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				wfreplySvc.updateWFReply(wfr_content, wfr_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("wfreplyVO", wfreplyVO); // 資料庫update成功後,正確的的wfreplyVO物件,存入req
//				String url = "/front-end/waterfall/listAllWaterfall.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneWfr.jsp
//				successView.forward(req, res);
				
				String url = "waterfall.do?action=getOne_For_Display&wf_no="+wfr_wfno;
				res.sendRedirect(url);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/wfreply/update_wfr_input.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================		

        if ("insert".equals(action)) { // 來自addWfr.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String wf_no = req.getParameter("wf_no");
				String wfr_wfnoReg = "^[(a-zA-Z0-9)]{10}$";
				
				String wfr_memno = req.getParameter("wfr_memno");
				String wfr_memnoReg = "^[(a-zA-Z0-9)]{10}$";
				if (wfr_memno == null || wfr_memno.trim().length() == 0) {
					errorMsgs.add("回覆者會員編號: 請勿空白");
				} else if(!wfr_memno.trim().matches(wfr_memnoReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("回覆者會員編號: 只能是英文字母、數字, 且長度必需=10");
	            }
				
				String wfr_content = req.getParameter("wfr_content").trim();
				if (wfr_content == null || wfr_content.trim().length() == 0) {
					errorMsgs.add("回覆內容請勿空白");
				}else if(wfr_content.trim().length() > 100) {
					errorMsgs.add("回覆內容請勿超過一百字");
				}
				
				WFReplyVO wfreplyVO = new WFReplyVO();
				wfreplyVO.setWfr_wfno(wf_no);
				wfreplyVO.setWfr_memno(wfr_memno);
				wfreplyVO.setWfr_content(wfr_content);
				
				WaterfallService waterfallSvc1 = new WaterfallService();
				WaterfallVO waterfallVOer = waterfallSvc1.getOneWaterfall(wf_no);
				List<WFReplyVO> list1 = waterfallSvc1.joinWFReply(wf_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("wfreplyVO", wfreplyVO); // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("waterfallVO", waterfallVOer); // 資料庫取出的waterfallVO物件,存入req
					req.setAttribute("list", list1);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/waterfall/listOneWaterfall.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				wfreplyVO = wfreplySvc.addWFReply(wf_no, wfr_memno, wfr_content);
				WaterfallService waterfallSvc = new WaterfallService();
				WaterfallVO waterfallVO = waterfallSvc.getOneWaterfall(wf_no);
				List<WFReplyVO> list = waterfallSvc.joinWFReply(wf_no);

				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("waterfallVO", waterfallVO); // 資料庫取出的waterfallVO物件,存入req
				req.setAttribute("list", list);
//				req.setAttribute("wfreplyVO", wfreplyVO);
				String url = "waterfall.do?action=getOne_For_Display&wf_no="+waterfallVO.getWf_no();
				res.sendRedirect(url);
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllWaterfall.jsp
//				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/wfreply/addWfr.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================		

        
		if ("delete_wfreply".equals(action)) { // 來自listAllWfr.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wfr_no = new String(req.getParameter("wfr_no"));
				String wf_no = new String(req.getParameter("wf_no"));
				/***************************2.開始刪除資料***************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				wfreplySvc.deleteWFReply(wfr_no);
				WaterfallService waterfallSvc = new WaterfallService();
				WaterfallVO waterfallVO = waterfallSvc.getOneWaterfall(wf_no);
				List<WFReplyVO> list = waterfallSvc.joinWFReply(wf_no);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				req.setAttribute("waterfallVO", waterfallVO); // 資料庫取出的waterfallVO物件,存入req
				req.setAttribute("list", list);
				String url = "/front-end/waterfall/listOneWaterfall.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
//===============================================================================================================		
		if ("fake_delete_wfreply".equals(action)) { // 來自listAllWfr.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wfr_no = new String(req.getParameter("wfr_no"));
				String wf_no = new String(req.getParameter("wf_no"));
				/***************************2.開始刪除資料***************************************/
				WFReplyService wfreplySvc = new WFReplyService();
				wfreplySvc.fakeDeleteWFReply(wfr_no);
				WaterfallService waterfallSvc = new WaterfallService();
				WaterfallVO waterfallVO = waterfallSvc.getOneWaterfall(wf_no);
				List<WFReplyVO> list = waterfallSvc.joinWFReply(wf_no);
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				req.setAttribute("waterfallVO", waterfallVO); // 資料庫取出的waterfallVO物件,存入req
				req.setAttribute("list", list);
				String url = "/front-end/waterfall/listOneWaterfall.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
