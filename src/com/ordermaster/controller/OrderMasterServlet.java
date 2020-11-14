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
import com.members.model.MembersVO;
import com.carcamp.model.*;
import com.careqpt.model.*;
import com.carfood.model.*;
import com.cnotice.controller.CVNoticeWebsocket;
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

@WebServlet("/carorder/OrderMasterServlet")
public class OrderMasterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date nowMs = new java.util.Date();
		String nowDate = dateFmt.format(nowMs);
		// Validate memVO is in session or not.
		HttpSession session = req.getSession();
		MembersVO memVO = (MembersVO)session.getAttribute("memVO");
		String mem_no = null;
		if(memVO != null) {
			mem_no = memVO.getMem_no();
			System.out.println("OrderMasterServlet : session.getAttribute(\"mem_no\") =  " + mem_no);
		}else {
			System.out.println("OrderMasterServlet : session.getAttribute(\"mem_no\") =  " + mem_no);
			res.sendRedirect(req.getContextPath() + "/front-end/carorder/errorPages/memVONotFound.html");
			return;
		}
		
		// Capture action parameter to do correspond things
		String action = req.getParameter("action");
		System.out.println("OrderMasterServlet : " + nowDate + " action = " + action);
		
		// first time enter confirmOrder.jsp to get DB data.
		if("editOrder".equals(action)) {
			
			/***** new order situation *****/
			if(req.getParameter("om_no") == null) {
				System.out.println("OrderMasterServlet : action = get, om_no = null.");
				// Model service instances initiation
				CarCampService ccSvc = new CarCampService();
				CarEqptService ceSvc = new CarEqptService();
				CarFoodService cfSvc = new CarFoodService();
				CampService campSvc = new CampService();
				EquipmentService eqptSvc = new EquipmentService();
				FoodService foodSvc = new FoodService();
				// Get CarItems data from DB.
				List<CarCampVO> ccVOList = ccSvc.getCarCampsByMemno(mem_no);
				List<CarEqptVO> ceVOList = ceSvc.getCarEqptsByMemno(mem_no);
				List<CarFoodVO> cfVOList = cfSvc.getOneCar(mem_no);
				// Transform CarItems to OrderItems.
				Map<String, List<OrderCampVO>> ocMap = createOrderCamp(ccVOList);
				Map<String, List<OrderEqptVO>> oeMap = createOrderEqpt(ceVOList);
				Map<String, List<OrderFoodVO>> ofMap = createOrderFood(cfVOList);
				List<OrderCampVO> ocVOList = ocMap.get("ocVOList");
				List<OrderEqptVO> oeVOList = oeMap.get("oeVOList");
				List<OrderFoodVO> ofVOList = ofMap.get("ofVOList");
				// Set OrderItems in session scope to confirmOrder.jsp.
				session.setAttribute("ocVOList", ocVOList);
				session.setAttribute("oeVOList", oeVOList);
				session.setAttribute("ofVOList", ofVOList);
				// Get vendorNo
				String vd_no = null;
				if(ccVOList.size() != 0) {
					vd_no = campSvc.getOneCamp(ccVOList.get(0).getCc_campno()).getCamp_vdno();
				}else if(ceVOList.size() != 0) {
					vd_no = eqptSvc.getOneEquipment(ceVOList.get(0).getCe_eqptno()).getEqpt_vdno();
				}else if(cfVOList.size() != 0) {
					vd_no = foodSvc.getOneFood(cfVOList.get(0).getCf_foodno()).getFood_vdno();
				}
				req.setAttribute("vd_no", vd_no);
				// Redirect to confirmOrder.jsp
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/confirmOrder.jsp");
				dispatcher.forward(req, res);
				return;
				
			/***** edit order situation *****/
			}else {
				String om_no = req.getParameter("om_no");
				System.out.println("OrderMasterServlet : action = get, om_no != null.");
				// Get CarItems data from session.
				List<CarCampVO> ccVOList = (List<CarCampVO>)session.getAttribute("carCampVOList");
				List<CarEqptVO> ceVOList = (List<CarEqptVO>)session.getAttribute("carEqptVOList");
				List<CarFoodVO> cfVOList = (List<CarFoodVO>)session.getAttribute("carFoodVOList");
				// Transform CarItems to OrderItems.
				Map<String, List<OrderCampVO>> ocMap = createOrderCamp_edit(om_no, ccVOList);
				Map<String, List<OrderEqptVO>> oeMap = createOrderEqpt_edit(om_no, ceVOList);
				Map<String, List<OrderFoodVO>> ofMap = createOrderFood(cfVOList);
				List<OrderCampVO> ocVOList = ocMap.get("ocVOList");
				List<OrderEqptVO> oeVOList = oeMap.get("oeVOList");
				List<OrderFoodVO> ofVOList = ofMap.get("ofVOList");
				// Set OrderItems in session scope to confirmOrder.jsp.
				session.setAttribute("ocVOList", ocVOList);
				session.setAttribute("oeVOList", oeVOList);
				session.setAttribute("ofVOList", ofVOList);
				// Redirect to confirmOrder.jsp
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/confirmOrder.jsp");
				dispatcher.forward(req, res);
				return;
			}
			
		}
		
		// creditCard checkout to create order.
		if("creditCard".equals(action)) {
			// General parameter instances initiation
			Map<String, String> errorMsg = new HashMap<String, String>();
			// Model service instances initiation
			CarCampService ccSvc = new CarCampService();
			CarEqptService ceSvc = new CarEqptService();
			CarFoodService cfSvc = new CarFoodService();
			CampService campSvc = new CampService();
			EquipmentService eqptSvc = new EquipmentService();
			FoodService foodSvc = new FoodService();
			OrderMasterService omSvc = new OrderMasterService();
			// Get parameters from confirmOrder.jsp & DB.
			String om_cardno = req.getParameter("om_cardno");
			List<CarCampVO> ccVOList = ccSvc.getCarCampsByMemno(mem_no);
			List<CarEqptVO> ceVOList = ceSvc.getCarEqptsByMemno(mem_no);
			List<CarFoodVO> cfVOList = cfSvc.getOneCar(mem_no);
			System.out.println("mem_no = " + mem_no);
			System.out.println("ccVOList = " + ccVOList);
			System.out.println("ceVOList = " + ceVOList);
			System.out.println("cfVOList = " + cfVOList);
			// Get vd_no from shopping cart table.
			String om_vdno = null;
			if(ccVOList.size() != 0) {
				om_vdno = campSvc.getOneCamp(ccVOList.get(0).getCc_campno()).getCamp_vdno();
			}else if(ceVOList.size() != 0) {
				om_vdno = eqptSvc.getOneEquipment(ceVOList.get(0).getCe_eqptno()).getEqpt_vdno();
			}else if(cfVOList.size() != 0) {
				om_vdno = foodSvc.getOneFood(cfVOList.get(0).getCf_foodno()).getFood_vdno();
			}else {
				errorMsg.put("isEmpty", "您尚未加入任何品項！");
				System.out.println("isEmpty : " + errorMsg.get("vd_no"));
				req.setAttribute("errorMsg", errorMsg);
			}
			
			// Transform CarItems to OrderItems
			Map<String, List<OrderCampVO>> ocMap = createOrderCamp(ccVOList);
			Map<String, List<OrderEqptVO>> oeMap = createOrderEqpt(ceVOList);
			Map<String, List<OrderFoodVO>> ofMap = createOrderFood(cfVOList);
			List<OrderCampVO> ocVOList = ocMap.get("ocVOList");
			List<OrderEqptVO> oeVOList = oeMap.get("oeVOList");
			List<OrderFoodVO> ofVOList = ofMap.get("ofVOList");
			// Validate camp & eqpt qty if available qty is not enough
			List<OrderCampVO> errOcVOList = ocMap.get("errOcVOList");
			List<OrderEqptVO> errOeVOList = oeMap.get("errOeVOList");
			List<OrderFoodVO> errOfVOList = ofMap.get("errOfVOList");
			if(errOcVOList != null) {
				for(OrderCampVO ocVOi : errOcVOList) {
					if(ocVOi.getOc_qty() == -999) {
						errorMsg.put("isCampAvail", String.format("%s 剩餘數量為空？", ocVOi.getOc_campno()));
						System.out.println("errorMsg.isCampAvail : " + errorMsg.get("isCampAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("ocVOList", ocVOList);
						break;
					}else if(ocVOi.getOc_qty() < 0) {
						errorMsg.put("isCampAvail", String.format("%s 剩餘數量不足。", ocVOi.getOc_campno()));
						System.out.println("errorMsg.isCampAvail : " + errorMsg.get("isCampAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("ocVOList", ocVOList);
						break;
					}
				}
			}
			if(errOeVOList != null) {
				for(OrderEqptVO oeVOi : oeVOList) {
					if(oeVOi.getOe_qty() == -999) {
						errorMsg.put("isEqptAvail", String.format("%s 剩餘數量為空？", oeVOi.getOe_eqptno()));
						System.out.println("errorMsg.isEqptAvail : " + errorMsg.get("isEqptAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", oeVOList);
						break;
					}else if(oeVOi.getOe_qty() < 0) {
						errorMsg.put("isEqptAvail", String.format("%s 剩餘數量不足。", oeVOi.getOe_eqptno()));
						System.out.println("errorMsg.isEqptAvail : " + errorMsg.get("isEqptAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", oeVOList);
						break;
					}
				}
			}
			if(errOfVOList != null) {
				for(OrderFoodVO ofVOi : ofVOList) {
					if(ofVOi.getOf_qty() == -999) {
						errorMsg.put("isFoodAvail", String.format("%s 剩餘數量為空？", ofVOi.getOf_foodno()));
						System.out.println("errorMsg.isFoodAvail : " + errorMsg.get("isFoodAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", ofVOList);
					}
				}
			}
			
			// Forward back with errorMsg map if error occurred.
			if(errorMsg.size() != 0) {
				System.out.println("isEmpty" + errorMsg.get("isEmpty"));
				System.out.println("isCampAvail" + errorMsg.get("isCampAvail"));
				System.out.println("isEqptAvail" + errorMsg.get("isEqptAvail"));
				System.out.println("isFoodAvail" + errorMsg.get("isFoodAvail"));
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/confirmOrder.jsp");
				dispatcher.forward(req, res);
				return;
			}
			
			// Insert & Update ORDER_MASTER, ORDER_CAMP, ORDER_EQPT, ORDER_FOOD CAMP_AVAIL, EQPT_AVAIL.
			OrderMasterVO omVO = new OrderMasterVO();
			omVO.setOm_memno(mem_no);
			omVO.setOm_vdno(om_vdno);
			omVO.setOm_txnamt(calculateOrderAmount(ocVOList, oeVOList, ofVOList));
			omVO.setOm_cardno(om_cardno.trim());
			omVO.setOm_stat(2);
			String om_no = omSvc.insertWithDetails(omVO, ocVOList, oeVOList, ofVOList, ccVOList, ceVOList, cfVOList);
			System.out.println("OrderMasterServlet insertWithDetails execute successfully : " + om_no);
			
			// Send notice
			omVO.setOm_no(om_no);
			CVNoticeWebsocket cvnObj = new CVNoticeWebsocket();
			cvnObj.newOrder(omVO);
			
			session.removeAttribute("ocVOList");
			session.removeAttribute("oeVOList");
			session.removeAttribute("ofVOList");
			req.setAttribute("om_no", om_no);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/checkoutComplete.jsp");
			dispatcher.forward(req, res);
			return;
		}
		
		/***** Edit OrderMaster action *****/
		// creditCard checkout to edit order.(update OrderMaster, update origin order items, create new order items.)
		if("creditCardByEdit".equals(action)) {
			// General parameter instances initiation
			Map<String, String> errorMsg = new HashMap<String, String>();
			// Model service instances initiation
			CampService campSvc = new CampService();
			EquipmentService eqptSvc = new EquipmentService();
			FoodService foodSvc = new FoodService();
			OrderMasterService omSvc = new OrderMasterService();
			// Get parameters from confirmOrder.jsp & session.
			String om_cardno = req.getParameter("om_cardno");
			String om_no = req.getParameter("om_no");
			OrderMasterVO omVO = omSvc.getOneOrderMaster(om_no);
			List<CarCampVO> ccVOList = (List<CarCampVO>)session.getAttribute("carCampVOList");
			List<CarEqptVO> ceVOList = (List<CarEqptVO>)session.getAttribute("carEqptVOList");
			List<CarFoodVO> cfVOList = (List<CarFoodVO>)session.getAttribute("carFoodVOList");
			System.out.println("mem_no = " + mem_no);
			System.out.println("ccVOList = " + ccVOList);
			System.out.println("ceVOList = " + ceVOList);
			System.out.println("cfVOList = " + cfVOList);
			// Validate session has OrderMasterVO or not.
			if(om_no == null) {
				errorMsg.put("isOm_noEmpty", "Parameter沒有om_no");
				System.out.println("isOm_noEmpty : " + errorMsg.get("omVO_empty"));
				req.setAttribute("errorMsg", errorMsg);
			}
			// Validate session has CarItems or not.
			String om_vdno = null;
			if(ccVOList.size() != 0) {
				om_vdno = campSvc.getOneCamp(ccVOList.get(0).getCc_campno()).getCamp_vdno();
			}else if(ceVOList.size() != 0) {
				om_vdno = eqptSvc.getOneEquipment(ceVOList.get(0).getCe_eqptno()).getEqpt_vdno();
			}else if(cfVOList.size() != 0) {
				om_vdno = foodSvc.getOneFood(cfVOList.get(0).getCf_foodno()).getFood_vdno();
			}else {
				errorMsg.put("isCarItemsEmpty", "您尚未加入任何品項！");
				System.out.println("isCarItemsEmpty : " + errorMsg.get("vd_no"));
				req.setAttribute("errorMsg", errorMsg);
			}
			
			// Transform CarItems to OrderItems
			Map<String, List<OrderCampVO>> ocMap = createOrderCamp(ccVOList);
			Map<String, List<OrderEqptVO>> oeMap = createOrderEqpt(ceVOList);
			Map<String, List<OrderFoodVO>> ofMap = createOrderFood(cfVOList);
			List<OrderCampVO> ocVOList = ocMap.get("ocVOList");
			List<OrderEqptVO> oeVOList = oeMap.get("oeVOList");
			List<OrderFoodVO> ofVOList = ofMap.get("ofVOList");
			// Validate camp & eqpt qty if available qty is not enough
			List<OrderCampVO> errOcVOList = ocMap.get("errOcVOList");
			List<OrderEqptVO> errOeVOList = oeMap.get("errOeVOList");
			List<OrderFoodVO> errOfVOList = ofMap.get("errOfVOList");
			if(errOcVOList != null) {
				for(OrderCampVO ocVOi : errOcVOList) {
					if(ocVOi.getOc_qty() == -999) {
						errorMsg.put("isCampAvail", String.format("%s 剩餘數量為空？", ocVOi.getOc_campno()));
						System.out.println("errorMsg.isCampAvail : " + errorMsg.get("isCampAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("ocVOList", ocVOList);
						break;
					}else if(ocVOi.getOc_qty() < 0) {
						errorMsg.put("isCampAvail", String.format("%s 剩餘數量不足。", ocVOi.getOc_campno()));
						System.out.println("errorMsg.isCampAvail : " + errorMsg.get("isCampAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("ocVOList", ocVOList);
						break;
					}
				}
			}
			if(errOeVOList != null) {
				for(OrderEqptVO oeVOi : oeVOList) {
					if(oeVOi.getOe_qty() == -999) {
						errorMsg.put("isEqptAvail", String.format("%s 剩餘數量為空？", oeVOi.getOe_eqptno()));
						System.out.println("errorMsg.isEqptAvail : " + errorMsg.get("isEqptAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", oeVOList);
						break;
					}else if(oeVOi.getOe_qty() < 0) {
						errorMsg.put("isEqptAvail", String.format("%s 剩餘數量不足。", oeVOi.getOe_eqptno()));
						System.out.println("errorMsg.isEqptAvail : " + errorMsg.get("isEqptAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", oeVOList);
						break;
					}
				}
			}
			if(errOfVOList != null) {
				for(OrderFoodVO ofVOi : ofVOList) {
					if(ofVOi.getOf_qty() == -999) {
						errorMsg.put("isFoodAvail", String.format("%s 剩餘數量為空？", ofVOi.getOf_foodno()));
						System.out.println("errorMsg.isFoodAvail : " + errorMsg.get("isFoodAvail"));
						req.setAttribute("errorMsg", errorMsg);
						req.setAttribute("oeVOList", ofVOList);
					}
				}
			}
			
			// Forward back with errorMsg map if error occurred.
			if(errorMsg.size() != 0) {
				System.out.println("isOm_noEmpty" + errorMsg.get("isOm_noEmpty"));
				System.out.println("isCarItemsEmpty" + errorMsg.get("isCarItemsEmpty"));
				System.out.println("isCampAvail" + errorMsg.get("isCampAvail"));
				System.out.println("isEqptAvail" + errorMsg.get("isEqptAvail"));
				System.out.println("isFoodAvail" + errorMsg.get("isFoodAvail"));
				RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/confirmOrder.jsp");
				dispatcher.forward(req, res);
				return;
			}
			
			// Insert & Update ORDER_MASTER, ORDER_CAMP, ORDER_EQPT, ORDER_FOOD CAMP_AVAIL, EQPT_AVAIL.
			omVO.setOm_txnamt(calculateOrderAmount(ocVOList, oeVOList, ofVOList));
			omVO.setOm_cardno(om_cardno.trim());
			String om_no_update = omSvc.updateWithDetails(omVO, ocVOList, oeVOList, ofVOList);
			System.out.println("OrderMasterServlet insertWithDetails execute successfully : " + om_no_update);
			session.removeAttribute("orderMasterVO");
			session.removeAttribute("ocVOList");
			session.removeAttribute("oeVOList");
			session.removeAttribute("ofVOList");
			req.setAttribute("om_no", om_no_update);
			RequestDispatcher dispatcher = req.getRequestDispatcher("/front-end/carorder/checkoutComplete.jsp");
			dispatcher.forward(req, res);
			return;
			
		}
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}
	
	private int calculateOrderAmount(List<OrderCampVO> ocVOList, List<OrderEqptVO> oeVOList, List<OrderFoodVO> ofVOList) {
		int result = 0;
		// Camp
		for(OrderCampVO ocVO : ocVOList) {
			result += ocVO.getOc_price() * ocVO.getOc_qty();
		}
		// Eqpt
		for(OrderEqptVO oeVO : oeVOList) {
			result += oeVO.getOe_price() * oeVO.getOe_qty();
		}
		// Food
		for(OrderFoodVO ofVO : ofVOList) {
			result += ofVO.getOf_price() * ofVO.getOf_qty();
		}
		return result;
	}
	
	private Map<String, List<OrderCampVO>> createOrderCamp(List<CarCampVO> ccVOList){
		/*
		 * This method will turn CarCampVO list to OrderCampVO list with promotion price & available qty validation.
		 * 
		 * INPUT:
		 * List<CarCampVO>
		 * 
		 * OUTPUT:
		 * Map<String, List<OrderCampVO>>
		 * Contains 2 List<OrderCampVO>:
		 * 1. ocVOList
		 * 	1-1 OC_PRICE will validate whether there are active PromoCamp or not.
		 * 	1-2 OC_QTY will validate whether CC_CAMPNO-CC_START ~ CC_CAMPNO-CC_END period has enough qty or not;
		 *    1-2-1 OC_QTY > 0, means CAMP_NO-CA_DATE has available qty.
		 *    1-2-2 OC_QTY < 0, means CAMP_NO-CA_DATE qty is not enough.
		 * 	1-3 Every CarCampVO in List<OrderCampVO> that OC_OMNO, OC_CHANGE, OC_ESTBL will be empty.
		 * 
		 * 2. errOcVOList
		 *  2-1 If CarCampVO CC_CAMPNO-CC_DATE pair qty is not available in DB.
		 *  2-2 If CarCampVO CC_CAMPNO-CC_DATE pair qty is null in DB.
		 *  
		 */
		Map<String, List<OrderCampVO>> result = new HashMap<String, List<OrderCampVO>>();
		List<OrderCampVO> ocVOList = new ArrayList<OrderCampVO>();
		List<OrderCampVO> errOcVOList = new ArrayList<OrderCampVO>();
		if(ccVOList == null) {
			result.put("ocVOList", ocVOList);
			result.put("errVOList", errOcVOList);
			return result;
		}
		CampService campSvc = new CampService();
		PromoCampService pcSvc = new PromoCampService();
		CampAvailService caSvc = new CampAvailService();
		for(CarCampVO ccVO : ccVOList) {
			OrderCampVO ocVO = new OrderCampVO();
			String cc_campno = ccVO.getCc_campno();
			CampVO campVO = campSvc.getOneCamp(cc_campno);
			/***** Validate Camp is in status 2(on-shelf) *****/
			if(campVO.getCamp_stat() == 2) {
				/***** Validate promotion price *****/
				int camp_price = campVO.getCamp_price();
				PromoCampVO pcVO = pcSvc.getActiveMinPriceByPc_campno(cc_campno);
				if(pcVO != null) {
					ocVO.setOc_price(pcVO.getPc_price());
				}else {
					ocVO.setOc_price(camp_price);
				}
				/***** Validate camp available quantity *****/
				java.sql.Date cc_start = ccVO.getCc_start();
				java.sql.Date cc_end = ccVO.getCc_end();
				List<java.sql.Date> cc_seqDateList = getDateSeqByStartEnd(cc_start, cc_end);
				for(java.sql.Date cc_seq : cc_seqDateList) {
					CampAvailVO caVO = caSvc.getOneCampAvail(cc_campno, cc_seq);
					// CampAvail existing CA_CAMPNO-CA_DATE keypair row data.
					if(caVO != null) {
						if(caVO.getCa_qty() >= ccVO.getCc_qty()) {
							ocVO.setOc_qty(ccVO.getCc_qty());
						}else {
							ocVO.setOc_qty(caVO.getCa_qty() - ccVO.getCc_qty());
							System.out.printf("OrderMasterServlet: %s-%s camp not enough %d.\n", ccVO.getCc_campno(), cc_seq, ocVO.getOc_qty());
							errOcVOList.add(ocVO);
							result.put("errOcVOList", errOcVOList);
							break;
						}
					// CampAvail didn't exist CA_CAMPNO-CA_DATE keypair row data.
					}else {
						int campMaxQty = campVO.getCamp_qty();
						// Camp max qty >= CarCamp qty 
						if(campMaxQty >= ccVO.getCc_qty()) {
							ocVO.setOc_qty(ccVO.getCc_qty());
						// Camp max qty < CarCamp qty 
						}else {
							ocVO.setOc_qty(campMaxQty - ccVO.getCc_qty());
							System.out.printf("OrderMasterServlet: %s-%s camp not enough %d.\n", ccVO.getCc_campno(), cc_seq, ocVO.getOc_qty());
							errOcVOList.add(ocVO);
							result.put("errOcVOList", errOcVOList);
							break;
						}
					}
				}
			/***** Camp is not in status 2(on-shelf) *****/
			}else {
				ocVO.setOc_price(campVO.getCamp_price());
				ocVO.setOc_qty(-999);
				System.out.printf("OrderMasterServlet: %s camp is not on-shelf. CAMP_STAT = %d \n", ccVO.getCc_campno(), campVO.getCamp_stat());
				errOcVOList.add(ocVO);
				result.put("errOcVOList", errOcVOList);
			}
			// Set other fields to OrderCampVO
			ocVO.setOc_campno(cc_campno);
			ocVO.setOc_listprice(campVO.getCamp_price());
			ocVO.setOc_start(ccVO.getCc_start());
			ocVO.setOc_end(ccVO.getCc_end());
			ocVOList.add(ocVO);
		}
		result.put("ocVOList", ocVOList);
		return result;
	}
	
	private Map<String, List<OrderEqptVO>> createOrderEqpt(List<CarEqptVO> ceVOList){
		/*
		 * This method will turn CarEqptVO list to OrderEqptVO list with promotion price & available qty validation.
		 * 
		 * INPUT:
		 * List<CarEqptVO>
		 * 
		 * OUTPUT:
		 * Map<String, List<OrderCampVO>>
		 * Contains 2 List<OrderCampVO>:
		 * 1. ocVOList
		 * 	1-1 OC_PRICE will validate whether there are active PromoCamp or not.
		 * 	1-2 OC_QTY will validate whether CC_CAMPNO-CC_START ~ CC_CAMPNO-CC_END period has enough qty or not;
		 *    1-2-1 OC_QTY > 0, means CAMP_NO-CA_DATE has available qty.
		 *    1-2-2 OC_QTY < 0, means CAMP_NO-CA_DATE qty is not enough.
		 * 	1-3 Every CarCampVO in List<OrderCampVO> that OC_OMNO, OC_CHANGE, OC_ESTBL will be empty.
		 * 
		 * 2. errOcVOList
		 *  2-1 If CarCampVO CC_CAMPNO-CC_DATE pair qty is not available in DB.
		 *  2-2 If CarCampVO CC_CAMPNO-CC_DATE pair qty is null in DB.
		 * 
		 */
		Map<String, List<OrderEqptVO>> result = new HashMap<String, List<OrderEqptVO>>();
		List<OrderEqptVO> oeVOList = new LinkedList<OrderEqptVO>();
		List<OrderEqptVO> errOeVOList = new LinkedList<OrderEqptVO>();
		if(ceVOList == null) {
			result.put("oeVOList", oeVOList);
			result.put("errOeVOList", errOeVOList);
			return result;
		}
		EquipmentService eqptSvc = new EquipmentService();
		PromoEqptService peSvc = new PromoEqptService();
		EqptAvailService eaSvc = new EqptAvailService();
		for(CarEqptVO ceVO : ceVOList) {
			OrderEqptVO oeVO = new OrderEqptVO();
			String ce_eqptno = ceVO.getCe_eqptno();
			EquipmentVO eqptVO = eqptSvc.getOneEquipment(ce_eqptno);
			/***** Validate Eqpt is in status 2(on-shelf) *****/
			if(eqptVO.getEqpt_stat() == 2) {
				/***** Validate promotion price *****/
				int eqpt_price = eqptVO.getEqpt_price();
				PromoEqptVO peVO = peSvc.getActiveMinPriceByPe_eqptno(ce_eqptno);
				if(peVO != null) {
					oeVO.setOe_price(peVO.getPe_price());
				}else {
					oeVO.setOe_price(eqpt_price);
				}
				/***** Validate eqpt available quantity *****/
				java.sql.Date ce_start = ceVO.getCe_expget();
				java.sql.Date ce_end = ceVO.getCe_expback();
				List<java.sql.Date> ce_seqDateList = getDateSeqByStartEnd_Eqpt(ce_start, ce_end);
				for(java.sql.Date ce_seq : ce_seqDateList) {
					EqptAvailVO eaVO = eaSvc.getOne(ce_eqptno, ce_seq);
					if(eaVO != null) {
						if(eaVO.getEa_qty() >= ceVO.getCe_qty()) {
							oeVO.setOe_qty(ceVO.getCe_qty());
						}else {
							oeVO.setOe_qty(eaVO.getEa_qty() - ceVO.getCe_qty());
							System.out.printf("OrderMasterServlet: %s-%s eqpt not enough %d.\n", ceVO.getCe_eqptno(), ce_seq, oeVO.getOe_qty());
							errOeVOList.add(oeVO);
							result.put("errOeVOList", errOeVOList);
							break;
						}
					}else {
						int eqptMaxQty = eqptVO.getEqpt_qty();
						// Eqpt max qty >= CarEqpt qty 
						if(eqptMaxQty >= ceVO.getCe_qty()) {
							oeVO.setOe_qty(ceVO.getCe_qty());
						// Eqpt max qty < CarEqpt qty 
						}else {
							oeVO.setOe_qty(eqptMaxQty - ceVO.getCe_qty());
							System.out.printf("OrderMasterServlet: %s-%s eqpt not enough %d.\n", ceVO.getCe_eqptno(), ce_seq, oeVO.getOe_qty());
							errOeVOList.add(oeVO);
							result.put("errOeVOList", errOeVOList);
							break;
						}
					}
				}
			/***** Eqpt is not in status 2(on-shelf) *****/
			}else {
				oeVO.setOe_price(eqptVO.getEqpt_price());
				oeVO.setOe_qty(-999);
				System.out.printf("OrderMasterServlet: %s eqpt is not on-shelf. EQPT_STAT = %d \n", ceVO.getCe_eqptno(), eqptVO.getEqpt_stat());
				errOeVOList.add(oeVO);
				result.put("errOeVOList", errOeVOList);
			}
			// Set other fields to OrderCampVO
			oeVO.setOe_eqptno(ce_eqptno);
			oeVO.setOe_listprice(eqptVO.getEqpt_price());
			oeVO.setOe_expget(ceVO.getCe_expget());
			oeVO.setOe_expback(ceVO.getCe_expback());
			oeVOList.add(oeVO);
		}
		result.put("oeVOList", oeVOList);
		return result;
	}
	
	private Map<String, List<OrderFoodVO>> createOrderFood(List<CarFoodVO> cfVOList){
		/*
		 * This method will turn CarFoodVO list to OrderFoodVO list with promotion price validation.
		 * 
		 * INPUT:
		 * List<CarFoodVO>
		 * 
		 * OUTPUT:
		 * List<OrderFoodVO>
		 * 1. OF_PRICE will validate whether there are active PromoFood or not.
		 * 2. Every CarFoodVO in List<OrderFoodVO> that OF_OMNO, OF_CHANGE, OF_ESTBL will be empty.
		 * 
		 */
		Map<String, List<OrderFoodVO>> result = new HashMap<String, List<OrderFoodVO>>();
		List<OrderFoodVO> ofVOList = new LinkedList<OrderFoodVO>();
		List<OrderFoodVO> errOfVOList = new LinkedList<OrderFoodVO>();
		if(cfVOList == null) {
			result.put("ofVOList", ofVOList);
			result.put("errOfVOList", errOfVOList);
			return result;
		}
		FoodService foodSvc = new FoodService();
		PromoFoodService pfSvc = new PromoFoodService();
		for(CarFoodVO cfVO : cfVOList) {
			OrderFoodVO ofVO = new OrderFoodVO();
			String cf_foodno = cfVO.getCf_foodno();
			FoodVO foodVO = foodSvc.getOneFood(cf_foodno);
			/***** Validate Food is in status 2(on-shelf) *****/
			if(foodVO.getFood_stat() == 2) {
				// Validate promotion price
				int food_price = foodVO.getFood_price();
				PromoFoodVO pfVO = pfSvc.getActiveMinPriceByPf_foodno(cf_foodno);
				if(pfVO != null) {
					ofVO.setOf_price(pfVO.getPf_price());
				}else {
					ofVO.setOf_price(food_price);
				}
				ofVO.setOf_qty(cfVO.getCf_qty());
			/***** Food is not in status 2(on-shelf) *****/
			}else {
				ofVO.setOf_price(foodVO.getFood_price());
				ofVO.setOf_qty(-999);
				errOfVOList.add(ofVO);
				result.put("errOfVOList", errOfVOList);
			}
			// Set other fields to OrderFoodVO
			ofVO.setOf_foodno(cf_foodno);
			ofVO.setOf_listprice(foodVO.getFood_price());
			ofVOList.add(ofVO);
		}
		result.put("ofVOList", ofVOList);
		return result;
	}
	
	private List<java.sql.Date> getDateSeqByStartEnd(java.sql.Date start_date, java.sql.Date end_date){
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = start_date.getTime();
		long endLong = end_date.getTime();
		long seqLong = startLong;
		long dayLong = 24 * 60 * 60 * 1000;
		while(seqLong < endLong) {
			String seqDateFmt = fmt.format(seqLong);
			result.add(java.sql.Date.valueOf(seqDateFmt));
			seqLong += dayLong;
		}
		return result;
	}
	
	private List<java.sql.Date> getDateSeqByStartEnd_Eqpt(java.sql.Date start_date, java.sql.Date end_date){
		List<java.sql.Date> result = new ArrayList<java.sql.Date>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		long startLong = start_date.getTime();
		long endLong = end_date.getTime();
		long seqLong = startLong;
		long dayLong = 24 * 60 * 60 * 1000;
		while(seqLong <= endLong) {
			String seqDateFmt = fmt.format(seqLong);
			result.add(java.sql.Date.valueOf(seqDateFmt));
			seqLong += dayLong;
		}
		return result;
	}
	
	private Map<String, List<OrderCampVO>> createOrderCamp_edit(String om_no, List<CarCampVO> ccVOList){
		Map<String, List<OrderCampVO>> result = new HashMap<String, List<OrderCampVO>>();
		List<OrderCampVO> ocVOList = new ArrayList<OrderCampVO>();
		List<OrderCampVO> errOcVOList = new ArrayList<OrderCampVO>();
		if(ccVOList == null || om_no == null) {
			result.put("ocVOList", ocVOList);
			result.put("errVOList", errOcVOList);
			return result;
		}
		/***** Service *****/
		CampService campSvc = new CampService();
		PromoCampService pcSvc = new PromoCampService();
		CampAvailService caSvc = new CampAvailService();
		OrderCampService ocSvc = new OrderCampService();
		/***** Get previous order camp items *****/
		List<OrderCampVO> oldOcVOList = ocSvc.getActiveOrderCampsByOmno(om_no);
		List<String> ocVONoList = new ArrayList<String>();
		for(OrderCampVO ocVOi : oldOcVOList) {
			ocVONoList.add(ocVOi.getOc_campno());
		}
		/***** Situation 01 CarCampVO is in previous OrderCampVO *****/
		System.out.println("OrderMasterServlet : before ccVOList = " + ccVOList.size());
		Iterator ccIter = ccVOList.iterator();
		while(ccIter.hasNext()) {
			CarCampVO ccVO = (CarCampVO)ccIter.next();
			String cc_campno = ccVO.getCc_campno();
			CampVO campVO = campSvc.getOneCamp(cc_campno);
			if(ocVONoList.contains(cc_campno)) {
				OrderCampVO ocVO = new OrderCampVO();
				PromoCampVO pcVO = pcSvc.getActiveMinPriceByPc_campno(cc_campno);
				if(pcVO != null) {
					ocVO.setOc_price(pcVO.getPc_price());
				}else {
					ocVO.setOc_price(campVO.getCamp_price());
				}
				ocVO.setOc_campno(ccVO.getCc_campno());
				ocVO.setOc_qty(ccVO.getCc_qty());
				ocVO.setOc_start(ccVO.getCc_start());
				ocVO.setOc_end(ccVO.getCc_end());
				ocVO.setOc_listprice(campVO.getCamp_price());
				ocVOList.add(ocVO);
				ccIter.remove();
			}
		}
		System.out.println("OrderMasterServlet : after ccVOList = " + ccVOList.size());
		/***** Situation 02 CarCampVO is not in previous OrderCampVO *****/
		for(CarCampVO ccVO : ccVOList) {
			OrderCampVO ocVO = new OrderCampVO();
			String cc_campno = ccVO.getCc_campno();
			CampVO campVO = campSvc.getOneCamp(cc_campno);
			/***** Validate Camp is in status 2(on-shelf) *****/
			if(campVO.getCamp_stat() == 2) {
				/***** Validate promotion price *****/
				int camp_price = campVO.getCamp_price();
				PromoCampVO pcVO = pcSvc.getActiveMinPriceByPc_campno(cc_campno);
				if(pcVO != null) {
					ocVO.setOc_price(pcVO.getPc_price());
				}else {
					ocVO.setOc_price(camp_price);
				}
				/***** Validate camp available quantity *****/
				java.sql.Date cc_start = ccVO.getCc_start();
				java.sql.Date cc_end = ccVO.getCc_end();
				List<java.sql.Date> cc_seqDateList = getDateSeqByStartEnd(cc_start, cc_end);
				for(java.sql.Date cc_seq : cc_seqDateList) {
					CampAvailVO caVO = caSvc.getOneCampAvail(cc_campno, cc_seq);
					// CampAvail existing CA_CAMPNO-CA_DATE keypair row data.
					if(caVO != null) {
						if(caVO.getCa_qty() >= ccVO.getCc_qty()) {
							ocVO.setOc_qty(ccVO.getCc_qty());
						}else {
							ocVO.setOc_qty(caVO.getCa_qty() - ccVO.getCc_qty());
							System.out.printf("OrderMasterServlet: %s-%s camp not enough %d.\n", ccVO.getCc_campno(), cc_seq, ocVO.getOc_qty());
							errOcVOList.add(ocVO);
							result.put("errOcVOList", errOcVOList);
							break;
						}
					// CampAvail didn't exist CA_CAMPNO-CA_DATE keypair row data.
					}else {
						int campMaxQty = campVO.getCamp_qty();
						// Camp max qty >= CarCamp qty 
						if(campMaxQty >= ccVO.getCc_qty()) {
							ocVO.setOc_qty(ccVO.getCc_qty());
						// Camp max qty < CarCamp qty 
						}else {
							ocVO.setOc_qty(campMaxQty - ccVO.getCc_qty());
							System.out.printf("OrderMasterServlet: %s-%s camp not enough %d.\n", ccVO.getCc_campno(), cc_seq, ocVO.getOc_qty());
							errOcVOList.add(ocVO);
							result.put("errOcVOList", errOcVOList);
							break;
						}
					}
				}
			/***** Camp is not in status 2(on-shelf) *****/
			}else {
				ocVO.setOc_price(campVO.getCamp_price());
				ocVO.setOc_qty(-999);
				System.out.printf("OrderMasterServlet: %s camp is not on-shelf. CAMP_STAT = %d \n", ccVO.getCc_campno(), campVO.getCamp_stat());
				errOcVOList.add(ocVO);
				result.put("errOcVOList", errOcVOList);
			}
			// Set other fields to OrderCampVO
			ocVO.setOc_campno(cc_campno);
			ocVO.setOc_listprice(campVO.getCamp_price());
			ocVO.setOc_start(ccVO.getCc_start());
			ocVO.setOc_end(ccVO.getCc_end());
			ocVOList.add(ocVO);
		}
		result.put("ocVOList", ocVOList);
		return result;
	}
	
	private Map<String, List<OrderEqptVO>> createOrderEqpt_edit(String om_no, List<CarEqptVO> ceVOList){
		Map<String, List<OrderEqptVO>> result = new HashMap<String, List<OrderEqptVO>>();
		List<OrderEqptVO> oeVOList = new LinkedList<OrderEqptVO>();
		List<OrderEqptVO> errOeVOList = new LinkedList<OrderEqptVO>();
		if(ceVOList == null || om_no == null) {
			result.put("oeVOList", oeVOList);
			result.put("errOeVOList", errOeVOList);
			return result;
		}
		/***** Service *****/
		EquipmentService eqptSvc = new EquipmentService();
		PromoEqptService peSvc = new PromoEqptService();
		EqptAvailService eaSvc = new EqptAvailService();
		OrderEqptService oeSvc = new OrderEqptService();
		/***** Get previous order camp items *****/
		List<OrderEqptVO> oldOeVOList = oeSvc.getActiveOrderEqptsByOmno(om_no);
		List<String> oeVONoList = new ArrayList<String>();
		for(OrderEqptVO oeVOi : oldOeVOList) {
			oeVONoList.add(oeVOi.getOe_eqptno());
		}
		/***** Situation 01 CarCampVO is in previous OrderCampVO *****/
		System.out.println("OrderMasterServlet : before ceVOList = " + ceVOList.size());
		Iterator ceIter = ceVOList.iterator();
		while(ceIter.hasNext()) {
			CarEqptVO ceVO = (CarEqptVO)ceIter.next();
			String ce_eqptno = ceVO.getCe_eqptno();
			EquipmentVO eqptVO = eqptSvc.getOneEquipment(ce_eqptno);
			if(oeVONoList.contains(ce_eqptno)) {
				OrderEqptVO oeVO = new OrderEqptVO();
				PromoEqptVO peVO = peSvc.getActiveMinPriceByPe_eqptno(ce_eqptno);
				if(peVO != null) {
					oeVO.setOe_price(peVO.getPe_price());
				}else {
					oeVO.setOe_price(eqptVO.getEqpt_price());
				}
				oeVO.setOe_eqptno(ceVO.getCe_eqptno());
				oeVO.setOe_qty(ceVO.getCe_qty());
				oeVO.setOe_expget(ceVO.getCe_expget());
				oeVO.setOe_expback(ceVO.getCe_expback());
				oeVO.setOe_listprice(eqptVO.getEqpt_price());
				oeVOList.add(oeVO);
				ceIter.remove();
			}
		}
		System.out.println("OrderMasterServlet : after ceVOList = " + ceVOList.size());
		/***** Situation 02 CarCampVO is not in previous OrderCampVO *****/
		for(CarEqptVO ceVO : ceVOList) {
			OrderEqptVO oeVO = new OrderEqptVO();
			String ce_eqptno = ceVO.getCe_eqptno();
			EquipmentVO eqptVO = eqptSvc.getOneEquipment(ce_eqptno);
			/***** Validate Eqpt is in status 2(on-shelf) *****/
			if(eqptVO.getEqpt_stat() == 2) {
				/***** Validate promotion price *****/
				int eqpt_price = eqptVO.getEqpt_price();
				PromoEqptVO peVO = peSvc.getActiveMinPriceByPe_eqptno(ce_eqptno);
				if(peVO != null) {
					oeVO.setOe_price(peVO.getPe_price());
				}else {
					oeVO.setOe_price(eqpt_price);
				}
				/***** Validate eqpt available quantity *****/
				java.sql.Date ce_start = ceVO.getCe_expget();
				java.sql.Date ce_end = ceVO.getCe_expback();
				List<java.sql.Date> ce_seqDateList = getDateSeqByStartEnd_Eqpt(ce_start, ce_end);
				for(java.sql.Date ce_seq : ce_seqDateList) {
					EqptAvailVO eaVO = eaSvc.getOne(ce_eqptno, ce_seq);
					if(eaVO != null) {
						if(eaVO.getEa_qty() >= ceVO.getCe_qty()) {
							oeVO.setOe_qty(ceVO.getCe_qty());
						}else {
							oeVO.setOe_qty(eaVO.getEa_qty() - ceVO.getCe_qty());
							System.out.printf("OrderMasterServlet: %s-%s eqpt not enough %d.\n", ceVO.getCe_eqptno(), ce_seq, oeVO.getOe_qty());
							errOeVOList.add(oeVO);
							result.put("errOeVOList", errOeVOList);
							break;
						}
					}else {
						int eqptMaxQty = eqptVO.getEqpt_qty();
						// Eqpt max qty >= CarEqpt qty 
						if(eqptMaxQty >= ceVO.getCe_qty()) {
							oeVO.setOe_qty(ceVO.getCe_qty());
						// Eqpt max qty < CarEqpt qty 
						}else {
							oeVO.setOe_qty(eqptMaxQty - ceVO.getCe_qty());
							System.out.printf("OrderMasterServlet: %s-%s eqpt not enough %d.\n", ceVO.getCe_eqptno(), ce_seq, oeVO.getOe_qty());
							errOeVOList.add(oeVO);
							result.put("errOeVOList", errOeVOList);
							break;
						}
					}
				}
			/***** Eqpt is not in status 2(on-shelf) *****/
			}else {
				oeVO.setOe_price(eqptVO.getEqpt_price());
				oeVO.setOe_qty(-999);
				System.out.printf("OrderMasterServlet: %s eqpt is not on-shelf. EQPT_STAT = %d \n", ceVO.getCe_eqptno(), eqptVO.getEqpt_stat());
				errOeVOList.add(oeVO);
				result.put("errOeVOList", errOeVOList);
			}
			// Set other fields to OrderCampVO
			oeVO.setOe_eqptno(ce_eqptno);
			oeVO.setOe_listprice(eqptVO.getEqpt_price());
			oeVO.setOe_expget(ceVO.getCe_expget());
			oeVO.setOe_expback(ceVO.getCe_expback());
			oeVOList.add(oeVO);
		}
		result.put("oeVOList", oeVOList);
		return result;
	}
	
//	public static void main(String[] args) {
//		OrderMasterServlet obj = new OrderMasterServlet();
////		// Test getDateSeqByStartEnd method
////		java.sql.Date start_date = java.sql.Date.valueOf("2020-10-01");
////		java.sql.Date end_date = java.sql.Date.valueOf("2020-10-02");
////		System.out.println(obj.getDateSeqByStartEnd(start_date, end_date));
////		System.out.println(obj.getDateSeqByStartEnd_Eqpt(start_date, end_date));
////		// Test empty ccVOList with createOrderCamp(ccVOList) result
////		List<CarCampVO> ccVOList = new ArrayList<CarCampVO>();
////		System.out.println(obj.createOrderCamp(ccVOList).get("ocVOList"));
//	}
}
