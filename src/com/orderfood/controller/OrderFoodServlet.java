package com.orderfood.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.orderfood.model.OrderFoodService;
import com.vendor.model.VendorVO;


@WebServlet("/orderfood/orderfood.do")
public class OrderFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public OrderFoodServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = res.getWriter();
		HttpSession session = req.getSession();
		
		String action = req.getParameter("action");
		//System.out.println(action);
		
		if ("getFoodStatic".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				//測試用寫死
				//VendorVO vendorVOtest = new VendorVO();
				//vendorVOtest.setVd_no("V000000001");
				//session.setAttribute("vendorVO", vendorVOtest);
				//測試用寫死
				VendorVO vendorVO = (VendorVO)session.getAttribute("vendorVO");
				String vd_no = vendorVO.getVd_no();
				String startDate = req.getParameter("startDate");
				String endDate = req.getParameter("endDate");
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/vendorfoodstatic/foodStatic.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始查詢資料***************************************/
				OrderFoodService orderFoodSvc = new OrderFoodService();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();

				list = orderFoodSvc.getFoodStatic(vd_no, new java.sql.Timestamp(sdf.parse(startDate).getTime()), new java.sql.Timestamp(sdf.parse(endDate).getTime()));

				/***************************3.新增完成,準備轉交(Send the Success view)***********/								
				List<JSONObject> jsonList = new ArrayList<JSONObject>();
				for(int i = 0; i < list.size(); i++) {
					Map<String, String> map = list.get(i);
					JSONObject jsonObject = new JSONObject(map);
					jsonList.add(jsonObject);
				}
				
				String str = new JSONArray(jsonList).toString();
				pw.print(str);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("查詢資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/vendorfoodstatic/foodStatic.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
