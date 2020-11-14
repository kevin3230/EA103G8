package com.careqpt.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.careqpt.model.*;



@WebServlet("/getPic.do")
public class GetPicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		res.setContentType("image/jpg");
		ServletOutputStream out = res.getOutputStream();
		
		if ("getPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String eqpt_no = req.getParameter("eqpt_no");
						
				/***************************2.開始查詢資料*****************************************/
				CarEqptService2 carEqptSvc = new CarEqptService2();
				CarEqptVO2 carEqptVO2 = carEqptSvc.getOnePic(eqpt_no);



				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(carEqptVO2.getEqpt_pic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
						
					
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/careqpt/carEqpt.jsp");
				failureView.forward(req, res);
			}
					
		}

		
}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}