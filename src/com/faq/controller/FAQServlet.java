package com.faq.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.faq.model.*;


@WebServlet("/faq/faq.do")
public class FAQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getFAQs_By_Keyword".equals(action)) { // 來自faq.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("keyword");
				
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("0-請輸入關鍵字");
				} 
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/information/faq.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String keyword = str;
				
				/***************************2.開始查詢資料*****************************************/
				FAQService faqSvc = new FAQService();
				List<FAQVO> list = faqSvc.getFAQsByKeyword(keyword);
				if (list.isEmpty()) {
					errorMsgs.add("1-查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/information/faq.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("list", list); // 資料庫取出的foodVO物件,存入req
				String url = "/front-end/information/searchFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 searchFaq.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("2-無法取得資料" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/information/faq.jsp");
				failureView.forward(req, res);
			}
		}
				
	}

}
