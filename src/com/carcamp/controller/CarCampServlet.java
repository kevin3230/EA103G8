package com.carcamp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.campavail.model.*;
import com.carcamp.model.*;
import com.members.model.MembersVO;
import com.ordercamp.model.*;
import com.ordermaster.model.*;
import com.promocamp.model.*;
import com.promotion.model.*;
import com.vendor.model.*;

@WebServlet("/carcamp/carCamp.do")
public class CarCampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DAY_LENGTH = 90;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = res.getWriter();

		HttpSession session = req.getSession();
		MembersVO memVO = (MembersVO) session.getAttribute("memVO");
		String action = req.getParameter("action");

		if ("reserveCamp".equals(action)) {
			String vd_no = req.getParameter("vd_no");

			// 檢查業者編號的合法性
			if (vd_no != null) {
				VendorService vendorSvc = new VendorService();
				VendorVO vendorVO = vendorSvc.getOneVendor(vd_no);
				if (vendorVO == null || vendorVO.getVd_stat() == 0 || vendorVO.getVd_cgstat() == 0) {
					res.sendRedirect(req.getContextPath() + "/front-end/cgintro/pageNotFound.html");
					return;
				}
			}

			// 取得購物車的資料
			CarCampService carCampSvc = new CarCampService();
			List<CarCampVO> carCampVOList = carCampSvc.getCarCampsByMemno(memVO.getMem_no());
			if (carCampVOList.size() > 0) {
				CampService campSvc = new CampService();
				String cc_vdno = campSvc.getOneCamp(carCampVOList.get(0).getCc_campno()).getCamp_vdno();
				if (vd_no == null || vd_no.equals(cc_vdno)) {
					req.setAttribute("carCampVOList", carCampVOList);
					vd_no = cc_vdno;
				}
			}

			// 轉交
			req.setAttribute("vd_no", vd_no);		// 請求參數可能沒有業者編號
			String url = "/front-end/carcamp/reserveCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		if ("editOrder".equals(action)) {
			System.out.println("HEEEEEEEEEE");
			String om_no = req.getParameter("om_no");
//			 若主訂單編號不存在request
			if (om_no == null) {
				res.sendRedirect(req.getContextPath() + "/front-end/cgintro/pageNotFound.html");
				return;
			}
			
			OrderMasterService omSvc = new OrderMasterService();
			OrderMasterVO orderMasterVO = omSvc.getOneOrderMaster(om_no);
			if (!memVO.getMem_no().equals(orderMasterVO.getOm_memno())) {
				res.sendRedirect(req.getContextPath() + "/front-end/carcamp/badThing.html");
				return;
			}
			
			List<CarCampVO> carCampVOList = (List<CarCampVO>) session.getAttribute("carCampVOList");
			if (carCampVOList == null) {
				OrderCampService ocSvc = new OrderCampService();
				List<OrderCampVO> orderCampList = ocSvc.getOrderCampsByOmno(om_no);
	
				// 將orderCampList塞進carCampVOList
				carCampVOList = new ArrayList<>();
				for (OrderCampVO i : orderCampList) {
					if (i.getOc_stat() == 1) {
						CarCampVO carCampVO = new CarCampVO();
						carCampVO.setCc_campno(i.getOc_campno());
						carCampVO.setCc_qty(i.getOc_qty());
						carCampVO.setCc_start(i.getOc_start());
						carCampVO.setCc_end(i.getOc_end());
						carCampVOList.add(carCampVO);					
					}
				}
			}
			
			req.setAttribute("vd_no", orderMasterVO.getOm_vdno());
			session.setAttribute("carCampVOList", carCampVOList);
			String url = "/front-end/carcamp/reserveCamp.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if ("getOneCamp".equals(action)) {
			String camp_no = req.getParameter("camp_no");
			
			CampService dao = new CampService();
			CampVO campVO = dao.getOneCamp(camp_no);
			pw.print(new JSONObject(campVO));
		}

		if ("getCampAvailsByCampno".equals(action)) {
			String camp_no = req.getParameter("camp_no");
			Date start = new Date(new java.util.Date().getTime() + 86_400_000L);
			Date end = new Date(start.getTime() + DAY_LENGTH * 86_400_000L);
			
			CampAvailService dao = new CampAvailService();
			List<CampAvailVO> list = dao.getCampAvailsByCampno(camp_no, start, end);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
		if ("getPromoCampsByCampno".equals(action)) {
			String camp_no = req.getParameter("camp_no");
			System.out.println("getPromoCampsByCampno: camp_no = " + camp_no);
			PromoCampService dao = new PromoCampService();
			List<PromoCampVO> list = dao.getAliveByCampno(camp_no);
			System.out.println("size: " + list.size());
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
		if ("getOnePromo".equals(action)) {
			String pro_no = req.getParameter("pro_no");
			PromotionService dao = new PromotionService();
			PromotionVO promotionVO = dao.getOnePro(pro_no);
			String str = new JSONObject(promotionVO).toString();
			pw.print(str);
		}

		if ("addCarCamp".equals(action)) {
			String om_no = req.getParameter("om_no");

			List<String> errorMsgs = new LinkedList<String>();
			CarCampVO carCampVO= null;
		
			try {
				// STAGE 1: 接收參數
				// 接收請求參數			
				String cc_campno = req.getParameter("cc_campno");
				if (cc_campno == null || cc_campno.trim().length() == 0)
					errorMsgs.add("cc_campno:請選擇營位區域");
				
				Date cc_start = null;
				try {
					cc_start = Date.valueOf(req.getParameter("cc_start"));
				} catch(IllegalArgumentException e) {
					errorMsgs.add("cc_start:請選擇預計入住日期");
				}
				
				Integer cc_qty = null;
				try {
					cc_qty = Integer.parseInt(req.getParameter("cc_qty"));					
				} catch(NumberFormatException e) {
					errorMsgs.add("cc_qty:請選擇營位數量");
				}
				
				Integer duration = null;
				Date cc_end = null;
				try {
					duration = Integer.parseInt(req.getParameter("duration"));					
					cc_end = new Date(cc_start.getTime() + duration * 86_400_000L);
				} catch(NumberFormatException e) {
					errorMsgs.add("duration:請選擇停留天數");
				}
				
				carCampVO = new CarCampVO();
				carCampVO.setCc_memno(memVO.getMem_no());
				carCampVO.setCc_campno(cc_campno);
				carCampVO.setCc_qty(cc_qty);
				carCampVO.setCc_start(cc_start);
				carCampVO.setCc_end(cc_end);

				// 傳送錯誤訊息
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", new JSONArray(errorMsgs).toString());
					req.setAttribute("carCampVO", carCampVO);
					String url = "/front-end/carcamp/reserveCamp.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}
				
				// STAGE 2: 資料驗證
				// 入住期間
				java.util.Date now = new java.util.Date();
				long maxTime = now.getTime() + DAY_LENGTH * 86_400_000L;
				if (now.getTime() >= cc_start.getTime())
					errorMsgs.add("cc_start:不在可預約期間");
				else if (maxTime <= cc_end.getTime())
					errorMsgs.add("duration:不在可預約期間");

				// 剩餘營位數量
				CampAvailService dao = new CampAvailService();
				List<CampAvailVO> list = dao.getCampAvailsByCampno(cc_campno, cc_start, cc_end);
				CarCampVO orderCarCampVO = null;
				
				if (cc_qty == 0) {
					errorMsgs.add("top:無預期錯誤");
				} else if (om_no != null) {
					// 取得原本訂單數量
					OrderMasterService omSvc = new OrderMasterService();
					OrderMasterVO orderMasterVO = omSvc.getOneOrderMaster(om_no);
					if (!memVO.getMem_no().equals(orderMasterVO.getOm_memno())) {
						res.sendRedirect(req.getContextPath() + "/front-end/carcamp/badThing.html");
						return;
					}
					
					OrderCampService ocSvc = new OrderCampService();
					List<OrderCampVO> orderCampList = ocSvc.getOrderCampsByOmno(om_no);
					orderCarCampVO = new CarCampVO();
					for (OrderCampVO i : orderCampList) {
						if (i.getOc_stat() == 1 && i.getOc_campno().equals(cc_campno)) {
							orderCarCampVO.setCc_campno(i.getOc_campno());
							orderCarCampVO.setCc_qty(i.getOc_qty());
							orderCarCampVO.setCc_start(i.getOc_start());
							orderCarCampVO.setCc_end(i.getOc_end());
							break;
						}
					}
				}
				
//				System.out.println("CarCampServlet cc_start: " + cc_start);
//				System.out.println("CarCampServlet cc_end: " + cc_end);
//				System.out.println("CarCampServlet list.size(): " + list.size());
//				System.out.println("CarCampServlet orderCarCampVO: " + orderCarCampVO);
				for (CampAvailVO campAvailVO : list) {
					if (campAvailVO.getCa_date().getTime() >= cc_start.getTime() &&
							campAvailVO.getCa_date().getTime() < cc_end.getTime()) {
						if ((om_no != null && cc_qty > campAvailVO.getCa_qty() + orderCarCampVO.getCc_qty()) ||
							(om_no == null && cc_qty > campAvailVO.getCa_qty())) {
							errorMsgs.add("cc_qty:營位剩餘數量不足");
//							System.out.println("CarCampServlet om_no: " + om_no);
//							if (orderCarCampVO != null)
//								System.out.println("CarCampServlet qty: " + orderCarCampVO.getCc_qty());
//							System.out.println("CarCampServlet avail camp_no: " + campAvailVO.getCa_campno());
//							System.out.println("CarCampServlet avail date: " + campAvailVO.getCa_date());
//							System.out.println("CarCampServlet avail qty: " + campAvailVO.getCa_qty());
						}
					}
				}


				// 傳送錯誤訊息
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("errorMsgs", new JSONArray(errorMsgs).toString());
					req.setAttribute("carCampVO", carCampVO);
					String url = "/front-end/carcamp/reserveCamp.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
					return;
				}

				// STAGE 3: 寫入表格(或session)
				if (om_no != null) {	// 修改訂單模式
					List<CarCampVO> carCampVOList = new ArrayList<>();
					carCampVOList.add(carCampVO);
					session.setAttribute("carCampVOList", carCampVOList);
					
				} else {				// 新增訂單模式
					CarCampService carCampSvc = new CarCampService();
					List<CarCampVO> oldList = carCampSvc.getCarCampsByMemno(memVO.getMem_no());
					// 車子有東西先清空
					if (oldList.size() > 0)
						carCampSvc.deleteCarCamp(memVO.getMem_no());
					carCampSvc.addCarCamp(cc_campno, memVO.getMem_no(), cc_qty, cc_start, cc_end);
				}

				// STAGE 4: 寫入成功轉交
				String url = "/front-end/careqpt/carEqpt.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) { // 其他未預期錯誤
				e.printStackTrace();
				errorMsgs.add("top:" + e.getMessage());
				req.setAttribute("errorMsgs", new JSONArray(errorMsgs).toString());
				req.setAttribute("carCampVO", carCampVO);
				String url = "/front-end/carcamp/reserveCamp.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}