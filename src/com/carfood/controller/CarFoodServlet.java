package com.carfood.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.carfood.model.*;
import com.food.model.*;
import com.foodtype.model.*;
import com.members.model.MembersVO;
import com.promofood.model.*;

@WebServlet("/carfood/Carfood.do")
public class CarFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public CarFoodServlet() {
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
		System.out.println(action);
		
		if ("getFood".equals(action)) {
			String food_ftno = req.getParameter("food_ftno");
			//System.out.println(food_ftno);
			String mem_no = req.getParameter("mem_no");
			String food_vdno = req.getParameter("food_vdno");
			FoodService foodSvc = new FoodService();
			List<FoodVO> foodVOList = foodSvc.getOneVendor(food_vdno, 2, food_ftno);
			//FoodTypeService foodtypeSvc = new FoodTypeService();
			//FoodTypeVO foodtypeVO = foodtypeSvc.getOneFoodType(food_ftno);
			PromoFoodService pfSvc = new PromoFoodService();
			List<PromoFoodVO> pfVOList = new ArrayList<PromoFoodVO>();
			for(FoodVO foodVO:foodVOList) {
				PromoFoodVO promofoodVO = pfSvc.getActiveLowPriceByPf_foodno(foodVO.getFood_no());
				pfVOList.add(promofoodVO);
			}
//			System.out.println(foodVOList.get(1).getFood_no());
//			System.out.println(foodVOList);
//			System.out.println(pfVOList);
			Map foodmap = new HashMap();
			foodmap.put("foodVOList", foodVOList);
			foodmap.put("pfVOList", pfVOList);
			
			String str = new JSONObject(foodmap).toString();
			
			pw.print(str);
		}
		
		if ("editOrder".equals(action)) { // 來自listAllCarFood.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數***************************************/
				//測試用寫死
				//MembersVO memVOtest = new MembersVO();
				//memVOtest.setMem_no("M000000001");
				//session.setAttribute("memVO", memVOtest);
				//測試用寫死
				MembersVO memVO = (MembersVO)session.getAttribute("memVO");
				String mem_no = memVO.getMem_no();
				//System.out.println(req.getParameter("om_no"));
				String[] cf_foodno = null;
				Integer[] cf_qty = null;
				List<CarFoodVO> carFoodVOList = null;
				if(req.getParameterValues("cf_foodno")!= null) {
					cf_foodno = req.getParameterValues("cf_foodno");
					cf_qty = new Integer[cf_foodno.length];
					carFoodVOList = new ArrayList<CarFoodVO>();
					
					for(int i = 0; i < cf_foodno.length; i++) {
						cf_qty[i] = new Integer(req.getParameterValues("cf_qty")[i].trim());
						
						if(cf_qty[i] < 1) {
							errorMsgs.add("數量不可小於1");
						}
						if(cf_qty[i] > 99) {
							errorMsgs.add("數量不可大於99");
						}
						CarFoodVO carFoodVO = new CarFoodVO();
						carFoodVO.setCf_foodno(cf_foodno[i]);
						carFoodVO.setCf_memno(mem_no);
						carFoodVO.setCf_qty(cf_qty[i]);
						carFoodVOList.add(carFoodVO);
					}
				}
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/carfood/reserveFood.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				/***************************2.開始新增資料***************************************/
				if(req.getParameter("om_no") != null){
					session.setAttribute("carFoodVOList", carFoodVOList);
				}else {
					CarFoodService carfoodSvc = new CarFoodService();
					if(cf_foodno != null) {
						carfoodSvc.updateTransaction(carFoodVOList);
					}else {
						carFoodVOList = carfoodSvc.getOneCar(mem_no);
						CarFoodVO carFoodVO = null;
						for(int i = 0; i < carFoodVOList.size(); i++) {
							carFoodVO = carFoodVOList.get(i);
							carfoodSvc.deleteCarFood(carFoodVO.getCf_foodno(), mem_no);
						}
					}
				}
				/***************************3.新增完成,準備轉交(Send the Success view)***********/								
				String url = "/carorder/OrderMasterServlet";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/carfood/reserveFood.jsp");
				failureView.forward(req, res);
			}
		}
		
		
	}

}
