package com.waterfall.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;

import com.waterfall.model.*;
@WebServlet("/waterfall/WFPic.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class WFPicServlet extends HttpServlet {
	
	static DataSource ds;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		

		if ("getPic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
					
			try {
				/*************************** 1.接收請求參數 ****************************************/
				String wfp_no = req.getParameter("wfp_no");
				
				/***************************2.開始查詢資料*****************************************/
				WFPicService wfpicSvc = new WFPicService();
				WFPicVO wfpicVO = wfpicSvc.getOneWFPic(wfp_no);
				
				res.setContentType("image/jpg");
				ServletOutputStream out = res.getOutputStream();
						
				// byte[]轉InputStream
				ByteArrayInputStream bin = new ByteArrayInputStream(wfpicVO.getWfp_pic());
						
				byte[] buffer = new byte[4*1024];
				int len;
				while ((len = bin.read(buffer)) != -1) {
					out.write(buffer, 0 , len);
				}
				bin.close();
				
			/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
					
		}

		if ("delete_pic".equals(action)) { // 來自listAllWaterfall.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wf_no = new String(req.getParameter("wf_no"));
				String wfp_no = new String(req.getParameter("wfp_no"));
				System.out.println(wf_no);
				/***************************2.開始刪除資料***************************************/
				WFPicService wfpicSvc = new WFPicService();
				wfpicSvc.deleteWFPic(wfp_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/	
				String url = "/waterfall/waterfall.do?action=getOne_For_Update&wf_no="+wf_no;
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//				successView.forward(req, res);
//				String url = "/front-end/waterfall/update_waterfall_input.jsp?wf_no="+wf_no;
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

	public void init() throws ServletException {
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new UnavailableException("Couldn't get db connection");
		}
	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
