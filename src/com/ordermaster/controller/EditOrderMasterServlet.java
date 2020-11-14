package com.ordermaster.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.camp.model.*;
import com.equipment.model.*;
import com.food.model.*;
import com.carcamp.model.*;
import com.careqpt.model.*;
import com.carfood.model.*;
import com.campavail.model.*;
import com.eqptavail.model.*;
import com.promocamp.model.*;
import com.promoeqpt.model.*;
import com.promofood.model.*;
import com.vendor.model.VendorService;
import com.vendor.model.VendorVO;
import com.ordercamp.model.*;
import com.ordereqpt.model.*;
import com.orderfood.model.*;
import com.ordermaster.model.*;

@WebServlet("/carorder/EditOrderMasterServlet")
public class EditOrderMasterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date nowMs = new java.util.Date();
		String nowDate = dateFmt.format(nowMs);
		// Get mem_no from session
		HttpSession session = req.getSession();
		String mem_no = (String)session.getAttribute("mem_no");
		System.out.println("EditOrderMasterServlet : session.getAttribute(\"mem_no\") =  " + mem_no);
		// Capture action parameter to do correspond things
		String action = req.getParameter("action");
		System.out.println("EditOrderMasterServlet : " + nowDate + " action = " + action);
		
		//新增by李承璋
		if ("getMembersOrder".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				String om_memno = "";
				if(req.getParameter("mem_no") != null) {
					om_memno = mem_no;
				}else if(req.getParameter("vd_no") != null) {
					om_memno = req.getParameter("vd_no");
				}else {
					System.out.println("沒有傳入mem_no或vd_no參數");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始查詢資料***************************************/
					OrderMasterService omSvc = new OrderMasterService();
					VendorService vendorSvc = new VendorService();
					CampService campSvc = new CampService();
					EquipmentService eqptSvc = new EquipmentService();
					FoodService foodSvc = new FoodService();
					OrderCampService ocSvc = new OrderCampService();
					OrderEqptService oeSvc = new OrderEqptService();
					OrderFoodService ofSvc = new OrderFoodService();
					List<OrderMasterVO> omList = null;
						HashMap<String, String[]> map = new HashMap<String, String[]>(req.getParameterMap());
						omList = omSvc.getByMemno(map);
					JSONArray jsonarrayomList = new JSONArray();
					for(int i = 0; i < omList.size(); i++) {
						if(omList.get(i).getOm_stat() != 0) {//只取出不是已取消狀態的訂單顯示
							List<JSONObject> jsonListOc = new ArrayList<JSONObject>();
							List<JSONObject> jsonListOe = new ArrayList<JSONObject>();
							List<JSONObject> jsonListOf = new ArrayList<JSONObject>();
							VendorVO vendorVO = vendorSvc.getOneVendor(omList.get(i).getOm_vdno());
							List<OrderCampVO> ocList = ocSvc.getOrderCampsByOmno(omList.get(i).getOm_no());
							Integer totaltxnamt = 0;
							for(int j = 0; j < ocList.size(); j++) {
								if(ocList.get(j).getOc_stat() == 1) {//只顯示未取消狀態的明細
									CampVO campVO = campSvc.getOneCamp(ocList.get(j).getOc_campno());
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("camp_name", campVO.getCamp_name());
									jsonObject.put("oc_start", ocList.get(j).getOc_start().toString());
									jsonObject.put("oc_end", ocList.get(j).getOc_end().toString());
									jsonObject.put("oc_price", ocList.get(j).getOc_price());
									jsonObject.put("oc_qty", ocList.get(j).getOc_qty());
									int totalDays = (int)((ocList.get(j).getOc_end().getTime() - ocList.get(j).getOc_start().getTime()) / 86400000);
									String sum = Integer.toString(ocList.get(j).getOc_price()*ocList.get(j).getOc_qty() * totalDays);
									totaltxnamt += Integer.parseInt(sum);
									jsonObject.put("oc_sum", sum);
									jsonListOc.add(jsonObject);
								}
							}
							List<OrderEqptVO> oeList = oeSvc.getOrderEqptsByOmno(omList.get(i).getOm_no());
							for(int j =0; j < oeList.size(); j++) {
								if(oeList.get(j).getOe_stat() == 1) {//只顯示未取消狀態的明細
									EquipmentVO equipmentVO = eqptSvc.getOneEquipment(oeList.get(j).getOe_eqptno());
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("eqpt_name", equipmentVO.getEqpt_name());
									jsonObject.put("oe_expget", oeList.get(j).getOe_expget().toString());
									jsonObject.put("oe_expback", oeList.get(j).getOe_expback().toString());
									jsonObject.put("oe_price", oeList.get(j).getOe_price());
									jsonObject.put("oe_qty", oeList.get(j).getOe_qty());
									String sum = Integer.toString(oeList.get(j).getOe_price()*oeList.get(j).getOe_qty());
									totaltxnamt += Integer.parseInt(sum);
									jsonObject.put("oe_sum", sum);
									jsonListOe.add(jsonObject);
								}
							}
							List<OrderFoodVO> ofList = ofSvc.getOrderFoodsByOmno(omList.get(i).getOm_no());
							for(int j = 0; j< ofList.size(); j++) {
								if(ofList.get(j).getOf_stat() == 1) {//只顯示未取消狀態的明細
									FoodVO foodVO = foodSvc.getOneFood(ofList.get(j).getOf_foodno());
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("food_name", foodVO.getFood_name());
									jsonObject.put("of_price", ofList.get(j).getOf_price());
									jsonObject.put("of_qty", ofList.get(j).getOf_qty());
									String sum = Integer.toString(ofList.get(j).getOf_price()*ofList.get(j).getOf_qty());
									totaltxnamt += Integer.parseInt(sum);
									jsonObject.put("of_sum", sum);
									jsonListOf.add(jsonObject);
								}
							}
							JSONObject jsonObjectom = new JSONObject();
							jsonObjectom.put("om_no", omList.get(i).getOm_no());
							jsonObjectom.put("cg_name", vendorVO.getVd_cgname());
							jsonObjectom.put("om_estbl", omList.get(i).getOm_estbl().toString());
							jsonObjectom.put("om_stat", omList.get(i).getOm_stat().toString());
							jsonObjectom.put("om_txnamt", totaltxnamt.toString());
							jsonObjectom.put("ocList", jsonListOc);
							jsonObjectom.put("oeList", jsonListOe);
							jsonObjectom.put("ofList", jsonListOf);
							jsonarrayomList.put(jsonObjectom);
						}
					}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)***********/
					String str = jsonarrayomList.toString();
					out.print(str);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("查詢資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("cancelOrder".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***************************1.接收請求參數***************************************/
				String om_no = req.getParameter("om_no");
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始查詢資料***************************************/
					OrderMasterService omSvc = new OrderMasterService();
					VendorService vendorSvc = new VendorService();
					CampService campSvc = new CampService();
					EquipmentService eqptSvc = new EquipmentService();
					FoodService foodSvc = new FoodService();
					OrderCampService ocSvc = new OrderCampService();
					OrderEqptService oeSvc = new OrderEqptService();
					OrderFoodService ofSvc = new OrderFoodService();
					
					/*修改訂單狀態*/
					omSvc.cancelOrder(om_no);
					List<OrderCampVO> listoc = ocSvc.getOrderCampsByOmno(om_no);
					if(listoc.size() > 0) {
						for(OrderCampVO ocVO : listoc) {
							ocSvc.updateOrderCamp(ocVO.getOc_no(), 0);
						}
					}
					List<OrderEqptVO> listoe = oeSvc.getOrderEqptsByOmno(om_no);
					if(listoe.size() > 0) {
						for(OrderEqptVO oeVO : listoe) {
							oeSvc.updateOrderEqpt(oeVO.getOe_no(), 0);
						}
					}
					List<OrderFoodVO> listof = ofSvc.getOrderFoodsByOmno(om_no);
					if(listof.size() > 0) {
						for(OrderFoodVO ofVO : listof) {
							ofSvc.updateOrderFood(ofVO.getOf_foodno(), ofVO.getOf_omno(), ofVO.getOf_qty(), 0, ofVO.getOf_no());
						}
					}
					
					/*查詢要回傳的OrderMasterVO*/
					OrderMasterVO omVO = omSvc.getOneOrderMaster(om_no);
//					List<JSONObject> jsonListOc = new ArrayList<JSONObject>();
//					List<JSONObject> jsonListOe = new ArrayList<JSONObject>();
//					List<JSONObject> jsonListOf = new ArrayList<JSONObject>();
					JSONObject jsonObjectomVO = new JSONObject();
//						VendorVO vendorVO = vendorSvc.getOneVendor(omVO.getOm_vdno());
//						List<OrderCampVO> ocList = ocSvc.getOrderCampsByOmno(omVO.getOm_no());
//						Integer totaltxnamt = 0;
//						for(int j = 0; j < ocList.size(); j++) {
//							CampVO campVO = campSvc.getOneCamp(ocList.get(j).getOc_campno());
//							JSONObject jsonObject = new JSONObject();
//							jsonObject.put("camp_name", campVO.getCamp_name());
//							jsonObject.put("oc_start", ocList.get(j).getOc_start().toString());
//							jsonObject.put("oc_end", ocList.get(j).getOc_end().toString());
//							jsonObject.put("oc_price", ocList.get(j).getOc_price());
//							jsonObject.put("oc_qty", ocList.get(j).getOc_qty());
//							String sum = Integer.toString(ocList.get(j).getOc_price()*ocList.get(j).getOc_qty());
//							totaltxnamt += Integer.parseInt(sum);
//							jsonObject.put("oc_sum", sum);
//							jsonListOc.add(jsonObject);
//						}
//						List<OrderEqptVO> oeList = oeSvc.getOrderEqptsByOmno(omVO.getOm_no());
//						for(int j =0; j < oeList.size(); j++) {
//							EquipmentVO equipmentVO = eqptSvc.getOneEquipment(oeList.get(j).getOe_eqptno());
//							JSONObject jsonObject = new JSONObject();
//							jsonObject.put("eqpt_name", equipmentVO.getEqpt_name());
//							jsonObject.put("oe_expget", oeList.get(j).getOe_expget().toString());
//							jsonObject.put("oe_expback", oeList.get(j).getOe_expback().toString());
//							jsonObject.put("oe_price", oeList.get(j).getOe_price());
//							jsonObject.put("oe_qty", oeList.get(j).getOe_qty());
//							String sum = Integer.toString(oeList.get(j).getOe_price()*oeList.get(j).getOe_qty());
//							totaltxnamt += Integer.parseInt(sum);
//							jsonObject.put("oe_sum", sum);
//							jsonListOe.add(jsonObject);
//						}
//						List<OrderFoodVO> ofList = ofSvc.getOrderFoodsByOmno(omVO.getOm_no());
//						for(int j = 0; j< ofList.size(); j++) {
//							FoodVO foodVO = foodSvc.getOneFood(ofList.get(j).getOf_foodno());
//							JSONObject jsonObject = new JSONObject();
//							jsonObject.put("food_name", foodVO.getFood_name());
//							jsonObject.put("of_price", ofList.get(j).getOf_price());
//							jsonObject.put("of_qty", ofList.get(j).getOf_qty());
//							String sum = Integer.toString(ofList.get(j).getOf_price()*ofList.get(j).getOf_qty());
//							totaltxnamt += Integer.parseInt(sum);
//							jsonObject.put("of_sum", sum);
//							jsonListOf.add(jsonObject);
//						}
						jsonObjectomVO.put("om_no", omVO.getOm_no());
//						jsonObjectomVO.put("cg_name", vendorVO.getVd_cgname());
//						jsonObjectomVO.put("om_estbl", omVO.getOm_estbl().toString());
//						jsonObjectomVO.put("om_stat", omVO.getOm_stat().toString());
//						jsonObjectomVO.put("om_txnamt", totaltxnamt.toString());
//						jsonObjectomVO.put("ocList", jsonListOc);
//						jsonObjectomVO.put("oeList", jsonListOe);
//						jsonObjectomVO.put("ofList", jsonListOf);
				
				/***************************3.完成,準備轉交(Send the Success view)***********/
					String str = jsonObjectomVO.toString();
					out.print(str);
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("取消訂單失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if("editOrder".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String om_no = req.getParameter("om_no");
				
				if(session.getAttribute("carCampVOList") != null) {
					session.removeAttribute("carCampVOList");
				}
				if(session.getAttribute("carEqptVOList") != null) {
					session.removeAttribute("carEqptVOList");
				}
				if(session.getAttribute("carFoodVOList") != null) {
					session.removeAttribute("carFoodVOList");
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				/***************************2.查詢完成,準備轉交(Send the Success view)*************/
				//session.setAttribute("orderMasterVO", omVO); // 資料庫取出的ordermasterVO物件,存入session
				//req.setAttribute("om_no", om_no);
				String url = "/carcamp/carCamp.do";//om_no已經在membersOrder.jsp附上parameter
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneVendor.jsp
				successView.forward(req, res);
				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/membersorder/membersOrder.jsp");
				failureView.forward(req, res);
			}
		}
		//新增by李承璋
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

}
