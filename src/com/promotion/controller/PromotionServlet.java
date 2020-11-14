package com.promotion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.equipment.model.EquipmentService;
import com.equipment.model.EquipmentVO;
import com.food.model.FoodService;
import com.food.model.FoodVO;
import com.google.gson.*;
import com.promocamp.model.PromoCampService;
import com.promocamp.model.PromoCampVO;
import com.promoeqpt.model.PromoEqptService;
import com.promoeqpt.model.PromoEqptVO;
import com.promofood.model.PromoFoodService;
import com.promofood.model.PromoFoodVO;
import com.promotion.model.PromotionService;
import com.promotion.model.PromotionVO;

@WebServlet("/promotion/PromotionServlet")
@MultipartConfig
public class PromotionServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String action = req.getParameter("action");
		System.out.println(action);
		
		// edit from promoDetail.jsp
		if("edit".equals(action)) {
			/********** Parameters from promoDetail.jsp **********/
			// promotion
			String pro_no = req.getParameter("pro_no");
			String pro_name = req.getParameter("pro_name");
			String pro_start = req.getParameter("pro_start");
			String pro_end = req.getParameter("pro_end");
			String pro_stat = req.getParameter("pro_stat");
			// camp
			String[] pc_campnoList = req.getParameterValues("pc_campno");
			String[] pc_priceList = req.getParameterValues("pc_price");
			String[] camp_priceList = req.getParameterValues("camp_price");
			// eqpt
			String[] pe_eqptnoList = req.getParameterValues("pe_eqptno");
			String[] pe_priceList = req.getParameterValues("pe_price");
			String[] eqpt_priceList = req.getParameterValues("eqpt_price");
			// food
			String[] pf_foodnoList = req.getParameterValues("pf_foodno");
			String[] pf_priceList = req.getParameterValues("pf_price");
			String[] food_priceList = req.getParameterValues("food_price");
			// camp, eqpt, food items will contain front-end form-row-template empty data, remove it.
			pc_campnoList = Arrays.copyOfRange(pc_campnoList, 1, pc_campnoList.length);
			pc_priceList = Arrays.copyOfRange(pc_priceList, 1, pc_priceList.length);
			camp_priceList = Arrays.copyOfRange(camp_priceList, 1, camp_priceList.length);
			pe_eqptnoList = Arrays.copyOfRange(pe_eqptnoList, 1, pe_eqptnoList.length);
			pe_priceList = Arrays.copyOfRange(pe_priceList, 1, pe_priceList.length);
			eqpt_priceList = Arrays.copyOfRange(eqpt_priceList, 1, eqpt_priceList.length);
			pf_foodnoList = Arrays.copyOfRange(pf_foodnoList, 1, pf_foodnoList.length);
			pf_priceList = Arrays.copyOfRange(pf_priceList, 1, pf_priceList.length);
			food_priceList = Arrays.copyOfRange(food_priceList, 1, food_priceList.length);
			
			/********** Parameters Validation **********/
			Map<String, ArrayList<String>> errorMsg = new HashMap<String, ArrayList<String>>();
			// Promotion
			errorMsg.put("pro_name", new ArrayList<String>());
			errorMsg.put("pro_start", new ArrayList<String>());
			errorMsg.put("pro_end", new ArrayList<String>());
			// Promotion blank field
			if(pro_name.isEmpty()) {
				errorMsg.get("pro_name").add("促銷專案名稱不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			if(pro_start.isEmpty()) {
				errorMsg.get("pro_start").add("促銷開始日期不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			if(pro_end.isEmpty()) {
				errorMsg.get("pro_end").add("促銷結束日期不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			// Promotion start date & end date can't earlier than today.
			SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String todayFmt = dateFmt.format(new java.util.Date()); // today yyyy-MM-dd
				long today = dateFmt.parse(todayFmt).getTime(); // today 00:00 long time
				if(!pro_start.isEmpty()) {
					java.util.Date start_date = dateFmt.parse(pro_start);
					long pro_start_db = new PromotionService().getOnePro(pro_no).getPro_start().getTime();
					if(pro_start_db != start_date.getTime()) { // if user change pro_start
						if(start_date.getTime() < today) {
							errorMsg.get("pro_start").add("開始日期不可早於今日日期");
							req.setAttribute("errorMsg",  errorMsg);
						}
					}
				}
				if(!pro_end.isEmpty()) {
					java.util.Date end_date = dateFmt.parse(pro_end);
					long pro_end_db = new PromotionService().getOnePro(pro_no).getPro_end().getTime();
					if(pro_end_db != end_date.getTime()) { // if user change pro_end
						if(end_date.getTime() < today) {
							errorMsg.get("pro_end").add("結束日期不可早於今日日期");
							req.setAttribute("errorMsg",  errorMsg);
						}
					}
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			// Promotion start_date > end_date
			if(!pro_start.isEmpty() && !pro_end.isEmpty()) {
				try {
					java.util.Date start_date = dateFmt.parse(pro_start);
					java.util.Date end_date = dateFmt.parse(pro_end);
					// start_date > end_date
					if(start_date.getTime() > end_date.getTime()) {
						errorMsg.get("pro_start").add("開始日期不可晚於結束日期");
						req.setAttribute("errorMsg",  errorMsg);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			// PromoCamp validate empty parameter & price logic
			ArrayList<String> pro_campList = itemErrorMsgList(pc_campnoList, pc_priceList, camp_priceList);
			for(String msg : pro_campList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_campList", pro_campList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			// PromoEqpt validate empty parameter & price logic
			ArrayList<String> pro_eqptList = itemErrorMsgList(pe_eqptnoList, pe_priceList, eqpt_priceList);
			for(String msg : pro_eqptList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_eqptList", pro_eqptList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			// PromoFood validate empty parameter & price logic
			ArrayList<String> pro_foodList = itemErrorMsgList(pf_foodnoList, pf_priceList, food_priceList);
			for(String msg : pro_foodList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_foodList", pro_foodList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			
			// Duplicate camp, eqpt, food items
			Map<String, String> duplicateItem = duplicateItemFunc(pc_campnoList, pe_eqptnoList, pf_foodnoList);
			if(duplicateItem.size() != 0) {
				req.setAttribute("duplicateMsg", duplicateItem);
			}
			
			// Whether any camp, eqpt, food item has been selected or not.
			ArrayList<String> isEmptyItem = isEmptyItemFunc(pc_campnoList, pe_eqptnoList, pf_foodnoList);
			if("1".equals(isEmptyItem.get(0))) {
				errorMsg.put("isEmptyItem", isEmptyItem);
				req.setAttribute("errorMsg", errorMsg);
			}
			
			// forward to promoCreate.jsp with errorMsg map.
			if(req.getAttribute("errorMsg") != null) {
				req.setAttribute("pro_name", pro_name);
				req.setAttribute("pro_start", pro_start);
				req.setAttribute("pro_end", pro_end);
				req.setAttribute("pro_stat", pro_stat);
				req.setAttribute("pc_campnoList", pc_campnoList);
				req.setAttribute("pc_priceList", pc_priceList);
				req.setAttribute("camp_priceList", camp_priceList);
				req.setAttribute("pe_eqptnoList", pe_eqptnoList);
				req.setAttribute("pe_priceList", pe_priceList);
				req.setAttribute("eqpt_priceList", eqpt_priceList);
				req.setAttribute("pf_foodnoList", pf_foodnoList);
				req.setAttribute("pf_priceList", pf_priceList);
				req.setAttribute("food_priceList", food_priceList);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoDetail.jsp");
				dispatcher.forward(req, res);
				return;
			}
			// forward to promoCreate.jsp with duplicateMsg map.
			if(req.getAttribute("duplicateMsg") != null) {
				req.setAttribute("pro_name", pro_name);
				req.setAttribute("pro_start", pro_start);
				req.setAttribute("pro_end", pro_end);
				req.setAttribute("pro_stat", pro_stat);
				req.setAttribute("pc_campnoList", pc_campnoList);
				req.setAttribute("pc_priceList", pc_priceList);
				req.setAttribute("camp_priceList", camp_priceList);
				req.setAttribute("pe_eqptnoList", pe_eqptnoList);
				req.setAttribute("pe_priceList", pe_priceList);
				req.setAttribute("eqpt_priceList", eqpt_priceList);
				req.setAttribute("pf_foodnoList", pf_foodnoList);
				req.setAttribute("pf_priceList", pf_priceList);
				req.setAttribute("food_priceList", food_priceList);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoDetail.jsp");
				dispatcher.forward(req, res);
				return;
			}
			
			/********** Parameters manipulate with DB **********/
			// promotion
			PromotionService proSvc = new PromotionService();
			PromotionVO proVO = proSvc.getOnePro(pro_no);
			proVO.setPro_name(pro_name);
			proVO.setPro_start(Date.valueOf(pro_start));
			proVO.setPro_end(Date.valueOf(pro_end));
			if(pro_stat == null) {
				proVO.setPro_stat(0);
			}else {
				proVO.setPro_stat(Integer.parseInt(pro_stat));
			}
			proSvc.update(proVO);
			System.out.println("Promotion : " + pro_no + " updated.");
			// promo_camp update, insert, delete
			PromoCampService pcSvc = new PromoCampService();
			List<PromoCampVO> pcVOList_DB = pcSvc.getByPc_prono(pro_no);
			int[] updatedPcArr = new int[pcVOList_DB.size()]; // default value: 0
			for(int i : nonEmptyIndex(pc_campnoList)) {
				String pc_campno = pc_campnoList[i];
				int updateFlag = 0; // check if the item is existing in previous promotion set or not.
				for(int j = 0; j < pcVOList_DB.size(); j++) {
					String pc_campno_DB = pcVOList_DB.get(j).getPc_campno();
					// Update promo_camp
					if(pc_campno.equals(pc_campno_DB)) {
						PromoCampVO pcVO = pcVOList_DB.get(j);
						pcVO.setPc_price(Integer.parseInt(pc_priceList[i]));
						pcSvc.update(pcVO);
						System.out.println("PromoCamp : " + pc_campnoList[i] + " updated.");
						updatedPcArr[j] = 1;
						updateFlag = 1;
					}
				}
				// Insert promo_camp
				if(updateFlag == 0) {
					PromoCampVO pcVO = new PromoCampVO();
					pcVO.setPc_prono(pro_no);
					pcVO.setPc_campno(pc_campnoList[i]);
					pcVO.setPc_price(Integer.parseInt(pc_priceList[i]));
					pcSvc.insert(pcVO);
					System.out.println("PromoCamp : " + pc_campnoList[i] + " inserted.");
				}
			}
			// Delete promo_camp
			for(int i = 0; i < updatedPcArr.length; i++) {
				if(updatedPcArr[i] == 0) {
					PromoCampVO pcVO = pcVOList_DB.get(i);
					pcSvc.delete(pcVO);
					System.out.println("PromoCamp : " + pcVO.getPc_campno() + " deleted.");
				}
			}
			
			// promo_eqpt update, insert, delete
			PromoEqptService peSvc = new PromoEqptService();
			List<PromoEqptVO> peVOList_DB = peSvc.getByPe_prono(pro_no);
			int[] updatedPeArr = new int[peVOList_DB.size()]; // default value: 0
			for(int i : nonEmptyIndex(pe_eqptnoList)) {
				String pe_eqptno = pe_eqptnoList[i];
				int updateFlag = 0; // check if the item is existing in previous promotion set or not.
				for(int j = 0; j < peVOList_DB.size(); j++) {
					String pe_eqptno_DB = peVOList_DB.get(j).getPe_eqptno();
					// Update promo_eqpt
					if(pe_eqptno.equals(pe_eqptno_DB)) {
						PromoEqptVO peVO = peVOList_DB.get(j);
						peVO.setPe_price(Integer.parseInt(pe_priceList[i]));
						peSvc.update(peVO);
						System.out.println("PromoEqpt : " + pe_eqptnoList[i] + " updated.");
						updatedPeArr[j] = 1;
						updateFlag = 1;
					}
				}
				// Insert promo_eqpt
				if(updateFlag == 0) {
					PromoEqptVO peVO = new PromoEqptVO();
					peVO.setPe_prono(pro_no);
					peVO.setPe_eqptno(pe_eqptnoList[i]);
					peVO.setPe_price(Integer.parseInt(pe_priceList[i]));
					peSvc.insert(peVO);
					System.out.println("PromoEqpt : " + pe_eqptnoList[i] + " inserted.");
				}
			}
			// Delete promo_eqpt
			for(int i = 0; i < updatedPeArr.length; i++) {
				if(updatedPeArr[i] == 0) {
					PromoEqptVO peVO = peVOList_DB.get(i);
					peSvc.delete(peVO);
					System.out.println("PromoEqpt : " + peVO.getPe_eqptno() + " deleted.");
				}
			}
			
			// promo_food update, insert, delete
			PromoFoodService pfSvc = new PromoFoodService();
			List<PromoFoodVO> pfVOList_DB = pfSvc.getByPf_prono(pro_no);
			int[] updatedPfArr = new int[pfVOList_DB.size()]; // default value: 0
			for(int i : nonEmptyIndex(pf_foodnoList)) {
				String pf_foodno = pf_foodnoList[i];
				int updateFlag = 0; // check if the item is existing in previous promotion set or not.
				for(int j = 0; j < pfVOList_DB.size(); j++) {
					String pf_foodno_DB = pfVOList_DB.get(j).getPf_foodno();
					// Update promo_food
					if(pf_foodno.equals(pf_foodno_DB)) {
						PromoFoodVO pfVO = pfVOList_DB.get(j);
						pfVO.setPf_price(Integer.parseInt(pf_priceList[i]));
						pfSvc.update(pfVO);
						System.out.println("PromoFood : " + pf_foodnoList[i] + " updated.");
						updatedPfArr[j] = 1;
						updateFlag = 1;
					}
				}
				// Insert promo_food
				if(updateFlag == 0) {
					PromoFoodVO pfVO = new PromoFoodVO();
					pfVO.setPf_prono(pro_no);
					pfVO.setPf_foodno(pf_foodnoList[i]);
					pfVO.setPf_price(Integer.parseInt(pf_priceList[i]));
					pfSvc.insert(pfVO);
					System.out.println("PromoFood : " + pf_foodnoList[i] + " inserted.");
				}
			}
			// Delete promo_food
			for(int i = 0; i < updatedPfArr.length; i++) {
				if(updatedPfArr[i] == 0) {
					PromoFoodVO pfVO = pfVOList_DB.get(i);
					pfSvc.delete(pfVO);
					System.out.println("PromoFood : " + pfVO.getPf_foodno() + " deleted.");
				}
			}
			
			// Complete edit to DB, send redirect to promoDetail.jsp with newly inserted pro_no.
//			res.sendRedirect(req.getContextPath() + "/front-end/promotion/promoDetail.jsp?pro_no=" + pro_no);
			req.setAttribute("editResult", "1");
			req.setAttribute("pro_no", pro_no);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoDetail.jsp");
			dispatcher.forward(req, res);
		}
		
		// create promotion in promoCreate.jsp
		if("create".equals(action)) {
			/********** Parameters from promoCreate.jsp **********/
			// vendor number
			HttpSession session = req.getSession();
			String vd_no = (String)session.getAttribute("vd_no");
			// promotion
			String pro_name = req.getParameter("pro_name");
			String pro_start = req.getParameter("pro_start");
			String pro_end = req.getParameter("pro_end");
			String pro_stat = req.getParameter("pro_stat");
			// camp
			String[] pc_campnoList = req.getParameterValues("pc_campno");
			String[] pc_priceList = req.getParameterValues("pc_price");
			String[] camp_priceList = req.getParameterValues("camp_price");
			// eqpt
			String[] pe_eqptnoList = req.getParameterValues("pe_eqptno");
			String[] pe_priceList = req.getParameterValues("pe_price");
			String[] eqpt_priceList = req.getParameterValues("eqpt_price");
			// food
			String[] pf_foodnoList = req.getParameterValues("pf_foodno");
			String[] pf_priceList = req.getParameterValues("pf_price");
			String[] food_priceList = req.getParameterValues("food_price");
			// camp, eqpt, food items will contain front-end form-row-template empty data, remove it.
			pc_campnoList = Arrays.copyOfRange(pc_campnoList, 1, pc_campnoList.length);
			pc_priceList = Arrays.copyOfRange(pc_priceList, 1, pc_priceList.length);
			camp_priceList = Arrays.copyOfRange(camp_priceList, 1, camp_priceList.length);
			pe_eqptnoList = Arrays.copyOfRange(pe_eqptnoList, 1, pe_eqptnoList.length);
			pe_priceList = Arrays.copyOfRange(pe_priceList, 1, pe_priceList.length);
			eqpt_priceList = Arrays.copyOfRange(eqpt_priceList, 1, eqpt_priceList.length);
			pf_foodnoList = Arrays.copyOfRange(pf_foodnoList, 1, pf_foodnoList.length);
			pf_priceList = Arrays.copyOfRange(pf_priceList, 1, pf_priceList.length);
			food_priceList = Arrays.copyOfRange(food_priceList, 1, food_priceList.length);
			
			/********** Parameters Validation **********/
			Map<String, ArrayList<String>> errorMsg = new HashMap<String, ArrayList<String>>();
			// Promotion
			errorMsg.put("pro_name", new ArrayList<String>());
			errorMsg.put("pro_start", new ArrayList<String>());
			errorMsg.put("pro_end", new ArrayList<String>());
			// Promotion blank field
			if(pro_name.isEmpty()) {
				errorMsg.get("pro_name").add("促銷專案名稱不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			if(pro_start.isEmpty()) {
				errorMsg.get("pro_start").add("促銷開始日期不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			if(pro_end.isEmpty()) {
				errorMsg.get("pro_end").add("促銷結束日期不可為空");
				req.setAttribute("errorMsg", errorMsg);
			}
			// Promotion start date & end date can't earlier than today.
			SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				String todayFmt = dateFmt.format(new java.util.Date()); // today yyyy-MM-dd
				long today = dateFmt.parse(todayFmt).getTime(); // today 00:00 long time
				if(!pro_start.isEmpty()) {
					java.util.Date start_date = dateFmt.parse(pro_start);
					if(start_date.getTime() < today) {
						errorMsg.get("pro_start").add("開始日期不可早於今日日期");
						req.setAttribute("errorMsg",  errorMsg);
					}
				}
				if(!pro_end.isEmpty()) {
					java.util.Date end_date = dateFmt.parse(pro_end);
					if(end_date.getTime() < today) {
						errorMsg.get("pro_end").add("結束日期不可早於今日日期");
						req.setAttribute("errorMsg",  errorMsg);
					}
				}
			}catch (ParseException e) {
				e.printStackTrace();
			}
			// Promotion start_date > end_date
			if(!pro_start.isEmpty() && !pro_end.isEmpty()) {
				try {
					java.util.Date start_date = dateFmt.parse(pro_start);
					java.util.Date end_date = dateFmt.parse(pro_end);
					// start_date > end_date
					if(start_date.getTime() > end_date.getTime()) {
						errorMsg.get("pro_start").add("開始日期不可晚於結束日期");
						req.setAttribute("errorMsg",  errorMsg);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			// PromoCamp validate empty parameter & price logic
			ArrayList<String> pro_campList = itemErrorMsgList(pc_campnoList, pc_priceList, camp_priceList);
			for(String msg : pro_campList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_campList", pro_campList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			// PromoEqpt validate empty parameter & price logic
			ArrayList<String> pro_eqptList = itemErrorMsgList(pe_eqptnoList, pe_priceList, eqpt_priceList);
			for(String msg : pro_eqptList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_eqptList", pro_eqptList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			// PromoFood validate empty parameter & price logic
			ArrayList<String> pro_foodList = itemErrorMsgList(pf_foodnoList, pf_priceList, food_priceList);
			for(String msg : pro_foodList) {
				if(!msg.isEmpty()) {
					errorMsg.put("pro_foodList", pro_foodList);
					req.setAttribute("errorMsg", errorMsg);
					break;
				}
			}
			
			// Duplicate camp, eqpt, food items
			Map<String, String> duplicateItem = duplicateItemFunc(pc_campnoList, pe_eqptnoList, pf_foodnoList);
			if(duplicateItem.size() != 0) {
				req.setAttribute("duplicateMsg", duplicateItem);
			}
			
			// Whether any camp, eqpt, food item has been selected or not.
			ArrayList<String> isEmptyItem = isEmptyItemFunc(pc_campnoList, pe_eqptnoList, pf_foodnoList);
			if("1".equals(isEmptyItem.get(0))) {
				errorMsg.put("isEmptyItem", isEmptyItem);
				req.setAttribute("errorMsg", errorMsg);
			}
			
			// forward to promoCreate.jsp with errorMsg map.
			if(req.getAttribute("errorMsg") != null) {
				req.setAttribute("pro_name", pro_name);
				req.setAttribute("pro_start", pro_start);
				req.setAttribute("pro_end", pro_end);
				req.setAttribute("pro_stat", pro_stat);
				req.setAttribute("pc_campnoList", pc_campnoList);
				req.setAttribute("pc_priceList", pc_priceList);
				req.setAttribute("camp_priceList", camp_priceList);
				req.setAttribute("pe_eqptnoList", pe_eqptnoList);
				req.setAttribute("pe_priceList", pe_priceList);
				req.setAttribute("eqpt_priceList", eqpt_priceList);
				req.setAttribute("pf_foodnoList", pf_foodnoList);
				req.setAttribute("pf_priceList", pf_priceList);
				req.setAttribute("food_priceList", food_priceList);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoCreate.jsp");
				dispatcher.forward(req, res);
				return;
			}
			// forward to promoCreate.jsp with duplicateMsg map.
			if(req.getAttribute("duplicateMsg") != null) {
				req.setAttribute("pro_name", pro_name);
				req.setAttribute("pro_start", pro_start);
				req.setAttribute("pro_end", pro_end);
				req.setAttribute("pro_stat", pro_stat);
				req.setAttribute("pc_campnoList", pc_campnoList);
				req.setAttribute("pc_priceList", pc_priceList);
				req.setAttribute("camp_priceList", camp_priceList);
				req.setAttribute("pe_eqptnoList", pe_eqptnoList);
				req.setAttribute("pe_priceList", pe_priceList);
				req.setAttribute("eqpt_priceList", eqpt_priceList);
				req.setAttribute("pf_foodnoList", pf_foodnoList);
				req.setAttribute("pf_priceList", pf_priceList);
				req.setAttribute("food_priceList", food_priceList);
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoCreate.jsp");
				dispatcher.forward(req, res);
				return;
			}
			
			/********** Parameters insert to DB **********/
			String pro_no = null;
			// PromotionVO
			PromotionVO proVO = new PromotionVO();
			proVO.setPro_name(pro_name);
			proVO.setPro_start(Date.valueOf(pro_start));
			proVO.setPro_end(Date.valueOf(pro_end));
			proVO.setPro_vdno(vd_no);
			if(pro_stat == null) {
				proVO.setPro_stat(0);
			}else {
				proVO.setPro_stat(Integer.parseInt(pro_stat));
			}
			// PromoCampVO List
			List<PromoCampVO> pcVOList = new ArrayList<PromoCampVO>();
			for(int i : nonEmptyIndex(pc_campnoList)) {
				PromoCampVO pcVO = new PromoCampVO();
				pcVO.setPc_campno(pc_campnoList[i]);
				pcVO.setPc_price(Integer.parseInt(pc_priceList[i]));
				pcVOList.add(pcVO);
			}
			// PromoEqptVO List
			List<PromoEqptVO> peVOList = new ArrayList<PromoEqptVO>();
			for(int i : nonEmptyIndex(pe_eqptnoList)) {
				PromoEqptVO peVO = new PromoEqptVO();
				peVO.setPe_eqptno(pe_eqptnoList[i]);
				peVO.setPe_price(Integer.parseInt(pe_priceList[i]));
				peVOList.add(peVO);
			}
			// PromoFoodVO List
			List<PromoFoodVO> pfVOList = new ArrayList<PromoFoodVO>();
			for(int i : nonEmptyIndex(pf_foodnoList)) {
				PromoFoodVO pfVO = new PromoFoodVO();
				pfVO.setPf_foodno(pf_foodnoList[i]);
				pfVO.setPf_price(Integer.parseInt(pf_priceList[i]));
				pfVOList.add(pfVO);
			}
			// Call PromotionService to execute PromotionDAO insert method.
			PromotionService proSvc = new PromotionService();
			pro_no = proSvc.insertWithItems(proVO, pcVOList, peVOList, pfVOList);
			
//			// Complete insert to DB, send redirect to promoDetail.jsp with newly inserted pro_no.
//			res.sendRedirect(req.getContextPath() + "/front-end/promotion/promoDetail.jsp?pro_no=" + pro_no);
			req.setAttribute("createResult", "1");
			req.setAttribute("pro_no", pro_no);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/promotion/promoDetail.jsp");
			dispatcher.forward(req, res);
		}
		
		// delete promotion in promoDetail.jsp (ajax)
		if("deleteInDetail".equals(action)) {
			String pro_no = req.getParameter("pro_no");
			PromotionService proSvc = new PromotionService();
			PromoCampService pcSvc = new PromoCampService();
			PromoEqptService peSvc = new PromoEqptService();
			PromoFoodService pfSvc = new PromoFoodService();
			// PromoCamp delete
			List<PromoCampVO> pcList = pcSvc.getByPc_prono(pro_no);
			if(pcList.size() != 0) {
				pcSvc.delete(pcList);
			}
			// PromoEqpt delete
			List<PromoEqptVO> peList = peSvc.getByPe_prono(pro_no);
			if(peList.size() != 0) {
				peSvc.delete(peList);
			}
			// PromoEqpt delete
			List<PromoFoodVO> pfList = pfSvc.getByPf_prono(pro_no);
			if(pfList.size() != 0) {
				pfSvc.delete(pfList);
			}
			// Promotion delete
			proSvc.delete(proSvc.getOnePro(pro_no));
		}
		
		// delete promotions in promoHome.jsp (ajax)
		if("deleteInHome".equals(action)) {
			// Parameters from promoHome.jsp
			String pro_noArr = req.getParameter("pro_noArr");
			JsonArray pro_noJArr = JsonParser.parseString(pro_noArr).getAsJsonArray();
			for(JsonElement pro_noJSON : pro_noJArr) {
				String pro_no = pro_noJSON.getAsString();
				PromotionService proSvc = new PromotionService();
				PromoCampService pcSvc = new PromoCampService();
				PromoEqptService peSvc = new PromoEqptService();
				PromoFoodService pfSvc = new PromoFoodService();
				// PromoCamp delete
				List<PromoCampVO> pcList = pcSvc.getByPc_prono(pro_no);
				if(pcList.size() != 0) {
					pcSvc.delete(pcList);
				}
				// PromoEqpt delete
				List<PromoEqptVO> peList = peSvc.getByPe_prono(pro_no);
				if(peList.size() != 0) {
					peSvc.delete(peList);
				}
				// PromoEqpt delete
				List<PromoFoodVO> pfList = pfSvc.getByPf_prono(pro_no);
				if(pfList.size() != 0) {
					pfSvc.delete(pfList);
				}
				// Promotion delete
				PromotionVO proVO = proSvc.getOnePro(pro_no);
				proSvc.delete(proVO);
			}
			out.print("1");
		}
		
		// ajax_getOriginalPrice
		if("ajax_getOriginalPrice".equals(action)) {
			// vendor number form session
			HttpSession session = req.getSession();
			String vd_no = (String)session.getAttribute("vd_no");
			// Create camp, eqpt, food service instance
			CampService campSvc = new CampService();
			EquipmentService eqptSvc = new EquipmentService();
			FoodService foodSvc = new FoodService();
			// Get parameters from promoCreate.jsp ajax call
			String item_no = req.getParameter("item_no");
			String item_type = "";
			if(!item_no.isEmpty()) {
				item_type = item_no.substring(0, 1);
			}
			if("C".equals(item_type)) {
				List<CampVO> campList = campSvc.getExistCampsByVdno(vd_no);
				for(CampVO camp : campList) {
					if(item_no.equals(camp.getCamp_no())) {
						out.print(camp.getCamp_price());
					}
				}
			}else if("E".equals(item_type)) {
				List<EquipmentVO> eqptList = eqptSvc.getAll();
				for(EquipmentVO eqpt : eqptList) {
					if(vd_no.equals(eqpt.getEqpt_vdno())) {
						if(item_no.equals(eqpt.getEqpt_no())) {
							out.print(eqpt.getEqpt_price());
						}
					}
				}
			}else if("F".equals(item_type)) {
				List<FoodVO> foodList = foodSvc.getAll();
				for(FoodVO food : foodList) {
					if(vd_no.equals(food.getFood_vdno())) {
						if(item_no.equals(food.getFood_no())) {
							out.print(food.getFood_price());
						}
					}
				}
			}else {
				out.print("");
			}
		}
		
		// check if session has vd_no or not.
		if("pseudoLogin".equals(action)) {
			HttpSession session = req.getSession();
			session.setAttribute("vd_no", req.getParameter("vd_no"));
			res.sendRedirect(req.getContextPath() + "/front-end/promotion/promoHome.jsp");
		}
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	private ArrayList<String> itemErrorMsgList(String[] itemNoArr, String[] itemPPriceArr, String[] itemOPriceArr){
		/*
		 * Validate empty & promo_price > origin_price of camp, eqpt, food (only validate one type at once).
		 * 
		 * INPUT 
		 * PK, promo_price, origin_price String[] of camp or eqpt or food.
		 * 
		 * OUTPUT
		 * ArrayList<"error message">, "" means no error to that item.
		 * 
		 */
		ArrayList<String> result = new ArrayList<String>();
		for(int i = 0; i < itemNoArr.length; i++) { 
		    // 1. empty itemNo in promoCreate.jsp (user didn't choose food option in select field)
		    if(itemNoArr[i].isEmpty()) {
		    	result.add("");
		    // 2. non-empty item in promoCreate.jsp
		    }else {
		        // 2-1. promo_price is empty
		        if(itemPPriceArr[i].isEmpty()) {
		        	result.add("促銷價格不可為空");
		        // 2-2. promo_price is not empty
		        }else {
		            // 2-2-1. promo_price higher than origin_price
		            if(Integer.parseInt(itemPPriceArr[i]) > Integer.parseInt(itemOPriceArr[i])) {
		            	result.add("促銷價格不可高於原價");
		            }else {
		            	result.add("");
		            }
		        }
		    }
		}
		return result;
	}
	
	private Map<String, String> duplicateItemFunc(String[] pcArr, String[] peArr, String[] pfArr){
		/*
		 * Create Map<"item PK", "error message"> to check if there's any duplicate items.
		 * If duplicate occur, then append itemPK & errroMsg to the output map.
		 * 
		 * INPUT
		 * pcArr, peArr, pfArr are String[] from front-end form data that user submitted to servlet.
		 * 
		 * OUTPUT
		 * Map<"item PK", "error message">
		 * 
		 */
		Map<String, String> result = new HashMap<String, String>();
		String[] duplicateCamp = duplicateElement(pcArr);
		String[] duplicateEqpt = duplicateElement(peArr);
		String[] duplicateFood = duplicateElement(pfArr);
		if(duplicateCamp.length != 0) {
			for(String duplicateCampi : duplicateCamp) {
				result.put(duplicateCampi, "促銷營位不可重複選擇");
			}
		}
		if(duplicateEqpt.length != 0) {
			for(String duplicateEqpti : duplicateEqpt) {
				result.put(duplicateEqpti, "促銷裝備不可重複選擇");
			}
		}
		if(duplicateFood.length != 0) {
			for(String duplicateFoodi : duplicateFood) {
				result.put(duplicateFoodi, "促銷食材不可重複選擇");
			}
		}
		return result;
	}
	private String[] duplicateElement(String[] stringArr) {
		List<String> duplicateList = new ArrayList<String>();
		for(int i = 0; i < stringArr.length; i++) {
			if(!"".equals(stringArr[i])) {
				for(int j = 1; j < stringArr.length; j++ ) {
					if(stringArr[i].equals(stringArr[j]) && i != j) {
						duplicateList.add(stringArr[i]);
						break;
					}
				}
			}
		}
		String[] result = new String[duplicateList.size()];
		return duplicateList.toArray(result);
	}
	
	private ArrayList<String> isEmptyItemFunc(String[] pcArr, String[] peArr, String[] pfArr) {
		/*
		 * Create an size=1 ArrayList<String> that indicate whether user select any item or not in promoXXXXX.jsp
		 * 
		 * INPUT
		 * pcArr, peArr, pfArr are String[] from front-end form data that user submitted to servlet.
		 * 
		 * OUTPUT
		 * an ArrayList<String> only contains one value to indicate whether pcArr, peArr, pfArr has any value or not.
		 * "0" -> user didn't choose any items of camp, eqpt, food.
		 * "1" -> user choose at lease one item of camp, eqpt, food.
		 * P.S. String format to fit errorMsg format(Map<String, List<String>>).
		 * 
		 */
		ArrayList<String> result = new ArrayList<String>();
		for(String pc_campno : pcArr) {
			if(!("".equals(pc_campno))) {
				result.add("0");
				break;
			}
		}
		if(result.size() == 0) {
			for(String pe_eqptno : peArr) {
				if(!("".equals(pe_eqptno))) {
					result.add("0");
					break;
				}
			}
		}
		if(result.size() == 0) {
			for(String pf_foodno : pfArr) {
				if(!("".equals(pf_foodno))) {
					result.add("0");
					break;
				}
			}
		}
		if(result.size() == 0) {
			result.add("1");
		}
		return result;
	};
	
	private Integer[] nonEmptyIndex(String[] itemNoArr) {
		List<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i < itemNoArr.length; i++) {
			if(!itemNoArr[i].isEmpty()) {
				temp.add(i);
			}
		}
		return temp.toArray(new Integer[temp.size()]);
	}
}
