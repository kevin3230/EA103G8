package com.careqpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.carcamp.model.*;
import com.careqpt.model.*;
import com.equipment.model.EquipmentService;
import com.equipment.model.EquipmentVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.ordereqpt.model.*;
import com.ordermaster.model.*;
import com.vendor.model.*;



@WebServlet("/careqpt/carEqpt2.do")
public class CarEqptServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = res.getWriter();

		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		if ("getAllBymem_no".equals(action)) { 
			
			CarEqptService2 dao = new CarEqptService2();
			String om_no = req.getParameter("om_no");
			List<CarCampVO> carCampVOList = new ArrayList<CarCampVO>();
			carCampVOList =  (List<CarCampVO>) session.getAttribute("carCampVOList");
			
			OrderMasterService omSvc = new OrderMasterService();
			EquipmentService EqptSvc = new EquipmentService();
			JsonArray jsonArray = new JsonArray();
			
			String str="";
			String mem_no = new String(req.getParameter("mem_no"));

			if(om_no=="") {
				List<CarEqptVO2> list = dao.getAllBymem_no(mem_no);
				str = new JSONArray(list).toString();
				pw.print(str);
			}else {
				OrderMasterVO orderMasterVO = omSvc.getOneOrderMaster(om_no);
				List<EquipmentVO> eqptlist = EqptSvc.getEquipmentsByVdno(orderMasterVO.getOm_vdno());
				for(EquipmentVO eqpt : eqptlist) {
					JsonObject jsonObject = new JsonObject();
					if(eqpt.getEqpt_stat()==2) {
					jsonObject.addProperty("eqpt_no", eqpt.getEqpt_no());
					jsonObject.addProperty("eqpt_price", eqpt.getEqpt_price());
					jsonObject.addProperty("eqpt_qty", eqpt.getEqpt_qty());
					jsonObject.addProperty("eqpt_name", eqpt.getEqpt_name());
					jsonObject.addProperty("cc_start", carCampVOList.get(0).getCc_start().toString());
					jsonObject.addProperty("cc_end", carCampVOList.get(0).getCc_end().toString());
//					jsonObject.addProperty("cc_start", "2020-11-03");
//					jsonObject.addProperty("cc_end", "2020-11-05");
					jsonArray.add(jsonObject);
					}
				}
				str = jsonArray.toString();
				pw.print(str);;
				
			}
		}
		
		
		if ("getAllByomno".equals(action)) { 
			VendorLeaseEqptService dao = new VendorLeaseEqptService();

			String om_no = new String(req.getParameter("oe_omno"));
			
			List<VendorLeaseEqptVO> list = dao.getOrderEqptsByOmno(om_no);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}

		if ("getOne".equals(action)) { 
			String ea_eqptno = req.getParameter("ea_eqptno");
			String ea_date = req.getParameter("ea_date");
			CarEqptService2 dao = new CarEqptService2();

			
			CarEqptVO2 carEqptVO2 = null;
			carEqptVO2 = dao.getOneEqptAvail(ea_eqptno, Date.valueOf(ea_date));
			pw.print(new JSONObject(carEqptVO2));
				
		}
		
		if ("getEqptAvailsByEqptno".equals(action)) {
			
			String start = req.getParameter("start");
			String om_no = req.getParameter("om_no");
			CarEqptService2 dao = new CarEqptService2();
			OrderEqptService oeSvc = new OrderEqptService();
			List<CarEqptVO2> list = new ArrayList<CarEqptVO2>();
			List<OrderEqptVO>  eqlist = new ArrayList<OrderEqptVO>();
					
			eqlist = oeSvc.getOrderEqptsByOmno(om_no);
			
			list = dao.getEqptAvailsByeadate( Date.valueOf(start));
			
			if(om_no=="") {
				String str = new JSONArray(list).toString();
				pw.print(str);
			}else {
				for(int i = 0 ; i < list.size() ; i++ ) {
					for(int j = 0 ; j<eqlist.size() ; j++ ) {
						if(eqlist.get(j).getOe_expget().toString().equals(list.get(i).getEa_date().toString())  && eqlist.get(j).getOe_eqptno().equals(list.get(i).getEa_eqptno())) {
							Integer eaqty = list.get(i).getEa_qty();
							Integer oeqty = eqlist.get(j).getOe_qty();
							
							list.get(i).setEa_qty(eaqty+oeqty);
						}
					}
				}
				String str = new JSONArray(list).toString();
				pw.print(str);
			}
			
		}
		
		if ("submitdata".equals(action)) {
			Gson gson = new Gson();
			CarEqptService2 CESvc2 = new CarEqptService2();
			CarEqptService CESvc = new CarEqptService();
			OrderEqptService oeSvc = new OrderEqptService();
			
			List<OrderEqptVO>  eqlist = new ArrayList<OrderEqptVO>();
			CarEqptVO2 carEqptVO2= null;
			Integer eaqty = null;
			List<CarEqptVO2> carEqptlist = new ArrayList<CarEqptVO2>() ;
			List<CarEqptVO> carEqptVOList = new ArrayList<CarEqptVO>();
			
			String jsonStr= req.getParameter("jsonArray");
			String memno = req.getParameter("mem_no");
			String om_no = req.getParameter("om_no");
			eqlist = oeSvc.getOrderEqptsByOmno(om_no);
			String no = null;
			int qty = 0;
			String start =null;
			String end = null;
			int i =0;
			boolean chick =true;
			JsonArray jsonArray = gson.fromJson(jsonStr, JsonArray.class);
			
			try {
				CESvc.deleteCarEqpt(memno);
				for (int index = 0; index < jsonArray.size(); index++) {
					JsonObject obj = jsonArray.get(index).getAsJsonObject();
//					System.out.println(obj.get("ce_eqptno").getAsString());
//					System.out.println(obj.get("ce_qty").getAsInt());
//					System.out.println(obj.get("ce_expget").getAsString());
//					System.out.println("====================================");
					String ce_eqptno = obj.get("ce_eqptno").getAsString();
					Integer ce_qty = obj.get("ce_qty").getAsInt();
					String ce_expget = obj.get("ce_expget").getAsString();
					CarEqptVO carEqptVO = new CarEqptVO();
					try {
						carEqptVO2 = CESvc2.getOneEqptAvail(ce_eqptno, Date.valueOf(ce_expget));
						eaqty = carEqptVO2.getEa_qty();
						if(om_no=="") {
							if(ce_qty > eaqty) {
								chick = false;
								carEqptlist.add(carEqptVO2);
							}
						}else {
							for(int j = 0 ; j<eqlist.size() ; j++ ) {
								if(eqlist.get(j).getOe_expget().toString().equals(ce_expget)  && eqlist.get(j).getOe_eqptno().equals(ce_eqptno)) {
									eaqty += eqlist.get(j).getOe_qty();
									if(ce_qty > eaqty) {
										chick = false;
										carEqptlist.add(carEqptVO2);
									}
								}
								
							}
						}
							
						
					} catch (NullPointerException npe) {
					}
					if(chick) {
						if( i == 0) {
						no = ce_eqptno;
						qty = ce_qty;
						start = ce_expget;
						end = ce_expget;
						i++;
						}
						if(index < jsonArray.size()-1 ) {
							if(ce_eqptno.equals(no) && ce_qty == qty) {
								 end = ce_expget;
							}else {
								if(om_no == ""){
									CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
								}else {
									carEqptVO.setCe_eqptno(no);
									carEqptVO.setCe_memno(memno);
									carEqptVO.setCe_qty(ce_qty);
									carEqptVO.setCe_expget(Date.valueOf(start));
									carEqptVO.setCe_expback(Date.valueOf(end));
									carEqptVOList.add(carEqptVO);
//									CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
								}
								no = ce_eqptno;
								qty = ce_qty;
								start = ce_expget;
								end = ce_expget;
							}
						}else {
								if(ce_eqptno.equals(no) && ce_qty == qty) {
									end = ce_expget;
									if(om_no==""){
										CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
									}else {
										carEqptVO.setCe_eqptno(no);
										carEqptVO.setCe_memno(memno);
										carEqptVO.setCe_qty(ce_qty);
										carEqptVO.setCe_expget(Date.valueOf(start));
										carEqptVO.setCe_expback(Date.valueOf(end));
										carEqptVOList.add(carEqptVO);
//										CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
									}
								}else {
									if(om_no==""){
										CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
										CESvc.addCarEqpt(ce_eqptno, memno, ce_qty, Date.valueOf(ce_expget), Date.valueOf(ce_expget));
									}else {
										carEqptVO.setCe_eqptno(no);
										carEqptVO.setCe_memno(memno);
										carEqptVO.setCe_qty(ce_qty);
										carEqptVO.setCe_expget(Date.valueOf(start));
										carEqptVO.setCe_expback(Date.valueOf(end));
										carEqptVOList.add(carEqptVO);
										carEqptVO = new CarEqptVO();
										carEqptVO.setCe_eqptno(ce_eqptno);
										carEqptVO.setCe_memno(memno);
										carEqptVO.setCe_qty(ce_qty);
										carEqptVO.setCe_expget(Date.valueOf(ce_expget));
										carEqptVO.setCe_expback(Date.valueOf(ce_expget));
										carEqptVOList.add(carEqptVO);
//										CESvc.addCarEqpt(no, memno, qty, Date.valueOf(start), Date.valueOf(end));
//										CESvc.addCarEqpt(ce_eqptno, memno, ce_qty, Date.valueOf(ce_expget), Date.valueOf(ce_expget));
									}
								
								}
							}
						}
				}
				if(chick) {
					if(om_no=="") {
						String str = "save";
						pw.print(str);
					}else {
						session.setAttribute("carEqptVOList", carEqptVOList);
						String str = "save";
						pw.print(str);
					}
				}else {
					String str = new JSONArray(carEqptlist).toString();
					pw.print(str);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if ("getPromotionByeqptno".equals(action)) {
			String eqptno = req.getParameter("eqptno");
			CarEqptService2 CESvc2 = new CarEqptService2();
			List<CarEqptVO2> list = CESvc2.getMinEqptPrice(eqptno);
			String str = new JSONArray(list).toString();
			pw.print(str);
			
		}
		
		if("getoldcareqpt".equals(action)) {
			
			CarEqptService ceSvc = new CarEqptService();
			OrderEqptService oeSvc = new OrderEqptService();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonArray jsonArray2 = new JsonArray();
			List<CarEqptVO> carEqptVOList =(List<CarEqptVO>) session.getAttribute("carEqptVOList");
			String om_no = req.getParameter("om_no");
			String mem_no = req.getParameter("mem_no");
			String jsonStr = null;
			if(om_no == "" || carEqptVOList != null ) {
				try {
					if(carEqptVOList==null) {
						List<CarEqptVO> list = ceSvc.getCarEqptsByMemno(mem_no);
						jsonStr = gson.toJson(list);
					}else {
						List<CarEqptVO> list = carEqptVOList;
						jsonStr = gson.toJson(list);
					}
					
					JsonArray jsonArray = gson.fromJson(jsonStr, JsonArray.class);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

					for (int index = 0; index < jsonArray.size(); index++) {
						JsonObject obj = jsonArray.get(index).getAsJsonObject();
						String ce_eqptno = obj.get("ce_eqptno").getAsString();
						Integer ce_qty = obj.get("ce_qty").getAsInt();
						String ce_expget = obj.get("ce_expget").getAsString();
						String ce_expback = obj.get("ce_expback").getAsString();
						java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(ce_expget);
						java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(ce_expback);
						long day = (date2.getTime()-date1.getTime())/(24*60*60*1000);
						
						if(day>0) {
							for(int i = 0 ; i<= day; i++) {
								cal.setTime(date1);
								cal.add(Calendar.DATE,i);
								java.util.Date date = cal.getTime();
								String reStr = sdf.format(date);
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("ce_eqptno", ce_eqptno);
								jsonObject.addProperty("ce_qty",ce_qty);
								jsonObject.addProperty("ce_expget",reStr);
								jsonObject.addProperty("ce_expback",reStr);
								jsonArray2.add(jsonObject);
							}
						}else {
							jsonArray2.add(obj);
						}
					}
					String str = jsonArray2.toString();
					pw.print(str);
				} catch (JsonSyntaxException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}else {
				try {
					List<OrderEqptVO> list = oeSvc.getOrderEqptsByOmno(om_no);
					jsonStr = gson.toJson(list);
					JsonArray jsonArray = gson.fromJson(jsonStr, JsonArray.class);
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");

					for (int index = 0; index < jsonArray.size(); index++) {
						JsonObject obj = jsonArray.get(index).getAsJsonObject();
						String oe_eqptno = obj.get("oe_eqptno").getAsString();
						Integer oe_qty = obj.get("oe_qty").getAsInt();
						String oe_expget = obj.get("oe_expget").getAsString();
						String oe_expback = obj.get("oe_expback").getAsString();
						java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(oe_expget);
						java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(oe_expback);
						long day = (date2.getTime()-date1.getTime())/(24*60*60*1000);
						if(day>0) {
							for(int i = 0 ; i<= day; i++) {
								cal.setTime(date1);
								cal.add(Calendar.DATE,i);
								java.util.Date date = cal.getTime();
								String reStr = sdf.format(date);
								JsonObject jsonObject = new JsonObject();
								jsonObject.addProperty("oe_eqptno", oe_eqptno);
								jsonObject.addProperty("oe_qty",oe_qty);
								jsonObject.addProperty("oe_expget",reStr);
								jsonObject.addProperty("oe_expback",reStr);
								jsonArray2.add(jsonObject);
							}
						}else {
							jsonArray2.add(obj);
						}
					}
					String str = jsonArray2.toString();
					pw.print(str);
				} catch (JsonSyntaxException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
					
		}
}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}