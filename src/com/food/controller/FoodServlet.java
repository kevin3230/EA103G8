package com.food.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.food.model.*;

@WebServlet("/food/food.do")
@MultipartConfig
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOneFood_For_Display".equals(action)) { // 來自foodHome.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("food_no");
				String strReg = "^[F][0-9]{9}$";
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入食材編號");
				} else if (!str.trim().matches(strReg)) {
					errorMsgs.add("食材編號格式不正確，請以大寫F開頭，後接9碼阿拉伯數字");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food/foodHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String food_no = str;
				
				/***************************2.開始查詢資料*****************************************/
				FoodService foodSvc = new FoodService();
				FoodVO foodVO = foodSvc.getOneFood(food_no);
				if (foodVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food/foodHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodVO", foodVO); // 資料庫取出的foodVO物件,存入req
				String url = "/front-end/food/listOneFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneFood.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/foodHome.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOneFood_For_Update".equals(action)) { // 來自listAllFood.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String food_no = req.getParameter("food_no");
				
				/***************************2.開始查詢資料****************************************/
				FoodService foodSvc = new FoodService();
				FoodVO foodVO = foodSvc.getOneFood(food_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("foodVO", foodVO);         // 資料庫取出的foodVO物件,存入req
				String url = "/front-end/food/updateFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateFood.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自updateFood.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String food_no = req.getParameter("food_no").trim();
				
				String food_vdno = req.getParameter("food_vdno").trim();
				
				String food_name = req.getParameter("food_name");
				if (food_name == null || food_name.trim().length() == 0) {
					errorMsgs.add("0-食材名稱: 請勿空白");
				}

				String food_ftno = req.getParameter("ft_no").trim();
				
				Integer food_price = null;
				try {
					food_price = new Integer(req.getParameter("food_price").trim());
					if (food_price < 0) {
						errorMsgs.add("1-食材定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					food_price = 0;
					errorMsgs.add("1-食材定價: 請輸入數字");
				}
				
				String food_intro = req.getParameter("food_intro").trim();
				
				Integer food_stat = new Integer(req.getParameter("food_stat").trim());
				
				// 尚未進行防錯、尚未有刪除圖片功能 
				byte[] food_pic = null;
				Part part = req.getPart("food_pic");
				if (part.getSubmittedFileName().trim().length() == 0) {	// 沒有更新照片的情況下使用原照片資料
					FoodService foodSvc = new FoodService();
					FoodVO foodVO = foodSvc.getOneFood(food_no);
					food_pic = foodVO.getFood_pic();
				} else {					
					String contentType = part.getContentType();
					if ("image/".equals(contentType.substring(0,6))) { // 確認檔案是否為image/*類型
						try {
							food_pic = getPicBytes(part);
						} catch (IOException e) {
							errorMsgs.add(e.getMessage());
						}
					} else {
						FoodService foodSvc = new FoodService();
						FoodVO foodVO = foodSvc.getOneFood(food_no);
						food_pic = foodVO.getFood_pic();
						errorMsgs.add("3-裝備照片: 請上傳圖片檔");
					}
				}
				
				FoodVO foodVO = new FoodVO();
				foodVO.setFood_no(food_no);
				foodVO.setFood_vdno(food_vdno);
				foodVO.setFood_name(food_name);
				foodVO.setFood_ftno(food_ftno);
				foodVO.setFood_price(food_price);
				foodVO.setFood_intro(food_intro);
				foodVO.setFood_stat(food_stat);
				foodVO.setFood_pic(food_pic);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodVO", foodVO); // 含有輸入格式錯誤的foodVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/food/updateFood.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				FoodService foodSvc = new FoodService();
				foodVO = foodSvc.updateFood(food_no, food_vdno, food_name, food_ftno, food_price, food_intro, food_stat, food_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("foodVO", foodVO); // 資料庫update成功後,正確的的foodVO物件,存入req
				String url = "/front-end/food/listOneFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneFood.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/updateFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("insert".equals(action)) { // 來自addFood.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String food_vdno = req.getParameter("food_vdno").trim();
				
				String food_name = req.getParameter("food_name");
				if (food_name == null || food_name.trim().length() == 0) {
					errorMsgs.add("0-食材名稱: 請勿空白");
				}

				String food_ftno = req.getParameter("ft_no").trim();

				Integer food_price = null;
				try {
					food_price = new Integer(req.getParameter("food_price").trim());
					if (food_price < 0) {
						errorMsgs.add("1-食材定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					food_price = 0;
					errorMsgs.add("1-食材定價: 請輸入數字");
				}
				
				String food_intro = req.getParameter("food_intro").trim();

				Integer food_stat = new Integer(req.getParameter("food_stat").trim());

				byte[] food_pic = null;
				Part part = req.getPart("food_pic");
				String contentType = part.getContentType();
				if ("image/".equals(contentType.substring(0,6))) { // 確認檔案是否為image/*類型
					try {
						food_pic = getPicBytes(part);
					} catch (IOException e) {
						errorMsgs.add(e.getMessage());
					}
				} else if (part.getSubmittedFileName().trim().length() == 0) { // 不允許不上傳照片
					errorMsgs.add("3-食材照片: 請上傳圖片");
				} else {
					errorMsgs.add("3-食材照片: 請上傳圖片檔");
				}
				
				FoodVO foodVO = new FoodVO();
				foodVO.setFood_vdno(food_vdno);
				foodVO.setFood_name(food_name);
				foodVO.setFood_ftno(food_ftno);
				foodVO.setFood_price(food_price);
				foodVO.setFood_intro(food_intro);
				foodVO.setFood_stat(food_stat);
				foodVO.setFood_pic(food_pic);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("foodVO", foodVO); // 含有輸入格式錯誤的foodVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/food/addFood.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				FoodService foodSvc = new FoodService();
				foodVO = foodSvc.addFood(food_vdno, food_name, food_ftno, food_price, food_intro, food_stat, food_pic);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFood.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/food/addFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllFood.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String food_no = req.getParameter("food_no");
				
				/***************************2.開始刪除資料***************************************/
				FoodService foodSvc = new FoodService();
				foodSvc.deleteFood(food_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateStatTo2".equals(action)) { // 來自listAllFood.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
												
				/***************************2.開始修改資料*****************************************/
				FoodService foodSvc = new FoodService();
				foodSvc.updateFoodStatTo2(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllFood.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateStatTo1".equals(action)) { // 來自listAllFood.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
												
				/***************************2.開始修改資料*****************************************/
				FoodService foodSvc = new FoodService();
				foodSvc.updateFoodStatTo1(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllFood.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}		
		
		if ("deleteSelected".equals(action)) { // 來自listAllFood.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
							
				/***************************2.開始刪除資料*****************************************/
				FoodService foodSvc = new FoodService();
				foodSvc.deleteSelected(stringArray);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/food/listAllFood.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/food/listAllFood.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
	
	// 將Part轉成byte[]
	protected static byte[] getPicBytes(Part part) throws IOException {
		InputStream is = part.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = is.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
			baos.flush();
		}
		baos.close();
		is.close();
		
		byte[] pic = baos.toByteArray(); // 將baos轉為資料流並存入byte[] pic
		return pic;
	}

}
