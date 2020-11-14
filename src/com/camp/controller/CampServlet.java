package com.camp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.camp.model.*;

@WebServlet("/camp/camp.do")
@MultipartConfig
public class CampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String camp_no = req.getParameter("camp_no");
				String strReg = "^[C][0-9]{9}$";

				if (camp_no == null || camp_no.trim().length() == 0) {
					errorMsgs.add("請輸入營位編號");
				} else if (!camp_no.trim().matches(strReg)) {
					errorMsgs.add("營位編號格式不正確，請以大寫C開頭，後接9碼阿拉伯數字");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/camp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				
				/***************************2.開始查詢資料*****************************************/
				CampService campSvc = new CampService();
				CampVO campVO = campSvc.getOneCamp(camp_no);
				System.out.println("campVO");
				if (campVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/camp/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("campVO", campVO); // 資料庫取出的campVO物件,存入req
				String url = "/front-end/camp/listOneCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCamp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllCamp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String camp_no = req.getParameter("camp_no");
				
				/***************************2.開始查詢資料****************************************/
				CampService campSvc = new CampService();
				CampVO campVO = campSvc.getOneCamp(camp_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("campVO", campVO);         // 資料庫取出的campVO物件,存入req
				String url = "/front-end/camp/updateCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateCamp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/listAllCamp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自updateCamp.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String camp_no = req.getParameter("camp_no").trim();
				
				String camp_vdno = req.getParameter("camp_vdno").trim();
				
				String camp_name = req.getParameter("camp_name");
				if (camp_name == null || camp_name.trim().length() == 0) {
					errorMsgs.add("0-營位名稱: 請勿空白");
				}

				String camp_ctno = req.getParameter("ct_no").trim();
				
				Integer camp_qty = null;
				try {
					camp_qty = new Integer(req.getParameter("camp_qty").trim());
					if (camp_qty < 0) {
						errorMsgs.add("1-營位數量: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					camp_qty = 0;
					errorMsgs.add("1-營位數量: 請輸入數字");
				}

				Integer camp_price = null;
				try {
					camp_price = new Integer(req.getParameter("camp_price").trim());
					if (camp_price < 0) {
						errorMsgs.add("2-營位定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					camp_price = 0;
					errorMsgs.add("2-營位定價: 請輸入數字");
				}

				Integer camp_stat = new Integer(req.getParameter("camp_stat").trim());
				
				byte[] camp_pic = null;
				Part part = req.getPart("camp_pic");
				if (part.getSubmittedFileName().trim().length() == 0) {	// 沒有更新照片的情況下使用原照片資料
					CampService campSvc = new CampService();
					CampVO campVO = campSvc.getOneCamp(camp_no);
					camp_pic = campVO.getCamp_pic();
				} else {
					String contentType = part.getContentType();
					if ("image/".equals(contentType.substring(0,6))) { // 確認檔案是否為image/*類型
						try {
							camp_pic = getPicBytes(part);
						} catch (IOException e) {
							errorMsgs.add(e.getMessage());
						}
					} else {
						CampService campSvc = new CampService();
						CampVO campVO = campSvc.getOneCamp(camp_no);
						camp_pic = campVO.getCamp_pic();
						errorMsgs.add("3-營位照片: 請上傳圖片檔");
					}
				}
				
				CampVO campVO = new CampVO();
				campVO.setCamp_no(camp_no);
				campVO.setCamp_vdno(camp_vdno);
				campVO.setCamp_name(camp_name);
				campVO.setCamp_ctno(camp_ctno);
				campVO.setCamp_qty(camp_qty);
				campVO.setCamp_price(camp_price);
				campVO.setCamp_stat(camp_stat);
				campVO.setCamp_pic(camp_pic);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("campVO", campVO); // 含有輸入格式錯誤的campVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/camp/updateCamp.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				CampService campSvc = new CampService();
				campVO = campSvc.updateCamp(camp_no, camp_vdno, camp_name, camp_ctno, camp_qty, camp_price, camp_stat, camp_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("campVO", campVO); // 資料庫update成功後,正確的的campVO物件,存入req
				String url = "/front-end/camp/listOneCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneCamp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/updateCamp.jsp");
				failureView.forward(req, res);
			}
		}
		

		if ("insert".equals(action)) { // 來自addCamp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String camp_vdno = req.getParameter("camp_vdno").trim();
				
				String camp_name = req.getParameter("camp_name");
				if (camp_name == null || camp_name.trim().length() == 0) {
					errorMsgs.add("0-營位名稱: 請勿空白");
				}

				String camp_ctno = req.getParameter("ct_no").trim();

				Integer camp_qty = null;
				try {
					camp_qty = new Integer(req.getParameter("camp_qty").trim());
					if (camp_qty < 0) {
						errorMsgs.add("1-營位數量: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					camp_qty = 0;
					errorMsgs.add("1-營位數量: 請輸入數字");
				}

				Integer camp_price = null;
				try {
					camp_price = new Integer(req.getParameter("camp_price").trim());
					if (camp_price < 0) {
						errorMsgs.add("2-營位定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					camp_price = 0;
					errorMsgs.add("2-營位定價: 請輸入數字");
				}

				Integer camp_stat = new Integer(req.getParameter("camp_stat").trim());

				byte[] camp_pic = null;	
				Part part = req.getPart("camp_pic");
				String contentType = part.getContentType();
				if ("image/".equals(contentType.substring(0,6))) { // 確認檔案是否為image/*類型
					try {
						camp_pic = getPicBytes(part);
					} catch (IOException e) {
						errorMsgs.add(e.getMessage());
					}
				} else if (part.getSubmittedFileName().trim().length() == 0) { // 不允許不上傳照片
					errorMsgs.add("3-營位照片: 請上傳圖片");
				} else {
					errorMsgs.add("3-營位照片: 請上傳圖片檔");
				}
				
				CampVO campVO = new CampVO();
				campVO.setCamp_vdno(camp_vdno);
				campVO.setCamp_name(camp_name);
				campVO.setCamp_ctno(camp_ctno);
				campVO.setCamp_qty(camp_qty);
				campVO.setCamp_price(camp_price);
				campVO.setCamp_stat(camp_stat);
				campVO.setCamp_pic(camp_pic);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("campVO", campVO); // 含有輸入格式錯誤的campVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/camp/addCamp.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				CampService campSvc = new CampService();
				campVO = campSvc.addCamp(camp_vdno, camp_name, camp_ctno, camp_qty, camp_price, camp_stat, camp_pic);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/camp/listAllCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllCamp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/camp/addCamp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllCamp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String camp_no = req.getParameter("camp_no");
				
				/***************************2.開始刪除資料***************************************/
				CampService campSvc = new CampService();
				campSvc.deleteCamp(camp_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/camp/listAllCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/listAllCamp.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("updateStatTo2".equals(action)) { // 來自listAllCamp.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
								
				/***************************2.開始修改資料*****************************************/
				CampService campSvc = new CampService();
				campSvc.updateCampStatTo2(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/camp/listAllCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllCamp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/listAllCamp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateStatTo1".equals(action)) { // 來自listAllCamp.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
								
				/***************************2.開始修改資料*****************************************/
				CampService campSvc = new CampService();
				campSvc.updateCampStatTo1(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/camp/listAllCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllCamp.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/listAllCamp.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("deleteSelected".equals(action)) { // 來自listAllCamp.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
							
				/***************************2.開始刪除資料*****************************************/
				CampService campSvc = new CampService();
				campSvc.deleteSelected(stringArray);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/camp/listAllCamp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/camp/listAllCamp.jsp");
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
		
		byte[] camp_pic = baos.toByteArray(); // 將baos轉為資料流並存入byte[] camp_pic
		return camp_pic;
	}
	
}
