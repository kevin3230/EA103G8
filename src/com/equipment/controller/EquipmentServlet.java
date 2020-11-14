package com.equipment.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.equipment.model.*;

@WebServlet("/equipment/equipment.do")
@MultipartConfig
public class EquipmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");//柏誼新增
		String action = req.getParameter("action");
		PrintWriter pw = res.getWriter();//柏誼新增
		
		
		if ("getOneEquipment_For_Display".equals(action)) { // 來自equipmentHome.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("eqpt_no");
				String strReg = "^[E][0-9]{9}$";
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入裝備編號");
				} else if (!str.trim().matches(strReg)) {
					errorMsgs.add("裝備編號格式不正確，請以大寫E開頭，後接9碼阿拉伯數字");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/equipment/equipmentHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				String eqpt_no = str;
				
				/***************************2.開始查詢資料*****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				EquipmentVO equipmentVO = eqptSvc.getOneEquipment(eqpt_no);
				if (equipmentVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/equipment/equipmentHome.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("equipmentVO", equipmentVO); // 資料庫取出的equipmentVO物件,存入req
				String url = "/front-end/equipment/listOneEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEquipment.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/equipmentHome.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOneEquipment_For_Update".equals(action)) { // 來自listAllEquipment.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String eqpt_no = req.getParameter("eqpt_no");
				
				/***************************2.開始查詢資料****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				EquipmentVO equipmentVO = eqptSvc.getOneEquipment(eqpt_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("equipmentVO", equipmentVO);         // 資料庫取出的equipmentVO物件,存入req
				String url = "/front-end/equipment/updateEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 updateEquipment.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/listAllEquipment.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自updateEquipment.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String eqpt_no = req.getParameter("eqpt_no").trim();
				
				String eqpt_vdno = req.getParameter("eqpt_vdno").trim();
				
				String eqpt_name = req.getParameter("eqpt_name");
				if (eqpt_name == null || eqpt_name.trim().length() == 0) {
					errorMsgs.add("0-裝備名稱: 請勿空白");
				}

				String eqpt_etno = req.getParameter("et_no").trim();
				
				Integer eqpt_qty = null;
				try {
					eqpt_qty = new Integer(req.getParameter("eqpt_qty").trim());
					if (eqpt_qty < 0) {
						errorMsgs.add("1-裝備數量: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					eqpt_qty = 0;
					errorMsgs.add("1-裝備數量: 請輸入數字");
				}

				Integer eqpt_price = null;
				try {
					eqpt_price = new Integer(req.getParameter("eqpt_price").trim());
					if (eqpt_price < 0) {
						errorMsgs.add("2=裝備定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					eqpt_price = 0;
					errorMsgs.add("2-裝備定價: 請輸入數字");
				}

				Integer eqpt_stat = new Integer(req.getParameter("eqpt_stat").trim());
				
				byte[] eqpt_pic = null;
				Part part = req.getPart("eqpt_pic");
				if (part.getSubmittedFileName().trim().length() == 0) {	// 沒有更新照片的情況下使用原照片資料
					EquipmentService eqptSvc = new EquipmentService();
					EquipmentVO equipmentVO = eqptSvc.getOneEquipment(eqpt_no);
					eqpt_pic = equipmentVO.getEqpt_pic();
				} else {					
					String contentType = part.getContentType();
					if ("image/".equals(contentType.substring(0,6))) { // 確認檔案是否為image/*類型
						try {
							eqpt_pic = getPicBytes(part);
						} catch (IOException e) {
							errorMsgs.add(e.getMessage());
						}
					} else {
						EquipmentService eqptSvc = new EquipmentService();
						EquipmentVO equipmentVO = eqptSvc.getOneEquipment(eqpt_no);
						eqpt_pic = equipmentVO.getEqpt_pic();
						errorMsgs.add("3-裝備照片: 請上傳圖片檔");
					}
				}

				EquipmentVO equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_no(eqpt_no);
				equipmentVO.setEqpt_vdno(eqpt_vdno);
				equipmentVO.setEqpt_name(eqpt_name);
				equipmentVO.setEqpt_etno(eqpt_etno);
				equipmentVO.setEqpt_qty(eqpt_qty);
				equipmentVO.setEqpt_price(eqpt_price);
				equipmentVO.setEqpt_stat(eqpt_stat);
				equipmentVO.setEqpt_pic(eqpt_pic);
				
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipmentVO", equipmentVO); // 含有輸入格式錯誤的equipmentVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/equipment/updateEquipment.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				equipmentVO = eqptSvc.updateEquipment(eqpt_no, eqpt_vdno, eqpt_name, eqpt_etno, eqpt_qty, eqpt_price, eqpt_stat, eqpt_pic);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("equipmentVO", equipmentVO); // 資料庫update成功後,正確的的equipmentVO物件,存入req
				String url = "/front-end/equipment/listOneEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEquipment.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/updateEquipment.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("insert".equals(action)) { // 來自addEquipment的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String eqpt_vdno = req.getParameter("eqpt_vdno").trim();
				
				String eqpt_name = req.getParameter("eqpt_name");
				if (eqpt_name == null || eqpt_name.trim().length() == 0) {
					errorMsgs.add("0-裝備名稱: 請勿空白");
				}

				String eqpt_etno = req.getParameter("et_no").trim();

				Integer eqpt_qty = null;
				try {
					eqpt_qty = new Integer(req.getParameter("eqpt_qty").trim());
					if (eqpt_qty < 0) {
						errorMsgs.add("1-裝備數量: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					eqpt_qty = 0;
					errorMsgs.add("1-裝備數量: 請輸入數字");
				}

				Integer eqpt_price = null;
				try {
					eqpt_price = new Integer(req.getParameter("eqpt_price").trim());
					if (eqpt_price < 0) {
						errorMsgs.add("2-裝備定價: 請勿輸入負數");
					}
				} catch (NumberFormatException e) {
					eqpt_price = 0;
					errorMsgs.add("2-裝備定價: 請輸入數字");
				}

				Integer eqpt_stat = new Integer(req.getParameter("eqpt_stat").trim());

				byte[] eqpt_pic = null;
				Part part = req.getPart("eqpt_pic");
				String contentType = part.getContentType();
				if ("image/".equals(contentType.substring(0,6)) ) { // 確認檔案是否為image/*類型
					try {
						eqpt_pic = getPicBytes(part);
					} catch (IOException e) {
						errorMsgs.add(e.getMessage());
					}
				} else if (part.getSubmittedFileName().trim().length() == 0) { // 不允許不上傳照片
					errorMsgs.add("3-裝備照片: 請上傳圖片");
				} else {
					errorMsgs.add("3-裝備照片: 請上傳圖片檔");
				}
								
				EquipmentVO equipmentVO = new EquipmentVO();
				equipmentVO.setEqpt_vdno(eqpt_vdno);
				equipmentVO.setEqpt_name(eqpt_name);
				equipmentVO.setEqpt_etno(eqpt_etno);
				equipmentVO.setEqpt_qty(eqpt_qty);
				equipmentVO.setEqpt_price(eqpt_price);
				equipmentVO.setEqpt_stat(eqpt_stat);
				equipmentVO.setEqpt_pic(eqpt_pic);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("equipmentVO", equipmentVO); // 含有輸入格式錯誤的eqptVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/equipment/addEquipment.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				EquipmentService eqptSvc = new EquipmentService();
				equipmentVO = eqptSvc.addEquipment(eqpt_vdno, eqpt_name, eqpt_etno, eqpt_qty, eqpt_price, eqpt_stat, eqpt_pic);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/front-end/equipment/listAllEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEquipment.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/equipment/addEquipment.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("delete".equals(action)) { // 來自listAllEquipment.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String eqpt_no = req.getParameter("eqpt_no");
				
				/***************************2.開始刪除資料***************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptSvc.deleteEquipment(eqpt_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/equipment/listAllEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/listAllEquipment.jsp");
				failureView.forward(req, res);
			}
		}	
		
		if ("updateStatTo2".equals(action)) { // 來自listAllEquipment.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
								
				/***************************2.開始修改資料*****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptSvc.updateEqptStatTo2(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/equipment/listAllEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllEquipment.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/listAllEquipment.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("updateStatTo1".equals(action)) { // 來自listAllEquipment.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
								
				/***************************2.開始修改資料*****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptSvc.updateEqptStatTo1(stringArray);
								
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/equipment/listAllEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllEquipment.jsp
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/listAllEquipment.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("deleteSelected".equals(action)) { // 來自listAllEquipment.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String jsonStr = req.getParameter("jsonStr");
				String[] stringArray = jsonStr.substring(2, jsonStr.length() - 2).split("\",\"");
							
				/***************************2.開始刪除資料*****************************************/
				EquipmentService eqptSvc = new EquipmentService();
				eqptSvc.deleteSelected(stringArray);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)*************/
				String url = "/front-end/equipment/listAllEquipment.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/equipment/listAllEquipment.jsp");
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
