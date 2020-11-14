package com.vendor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.careqpt.model.*;
import com.equipment.model.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ordereqpt.model.*;
import com.vendor.model.*;

@WebServlet("/vendor/LeaseEqpt.do")
public class VendorLeaseEqptServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = res.getWriter();
		
		String action = req.getParameter("action");
		
		if ("getAllByToday".equals(action)) { 
			String om_vdno = req.getParameter("om_vdno");
			
			JsonArray jsonArray = new JsonArray();
			
			
			VendorLeaseEqptService dao = new VendorLeaseEqptService();
			List<VendorLeaseEqptVO> list = dao.getAllByToday(om_vdno);
			for(VendorLeaseEqptVO vendorLeaseEqptVO : list) {
				JsonObject jsonObject = new JsonObject();
//				jsonObject.addProperty("show", "");
				jsonObject.addProperty("number", "");
				jsonObject.addProperty("mem_name", vendorLeaseEqptVO.getMem_name());
				jsonObject.addProperty("om_memno", vendorLeaseEqptVO.getOm_memno());
				jsonObject.addProperty("om_no",vendorLeaseEqptVO.getOm_no());
				jsonObject.addProperty("om_vdno",vendorLeaseEqptVO.getOm_vdno());
				jsonObject.addProperty("oc_start",vendorLeaseEqptVO.getOc_start().toString());
				jsonObject.addProperty("oc_end",vendorLeaseEqptVO.getOc_end().toString());
				jsonArray.add(jsonObject);
			}
			JsonObject data = new JsonObject();
			data.add("data", jsonArray);
			pw.print(data);
		}
		if ("getOmEqptByomno".equals(action)) { 
			String om_no = req.getParameter("om_no");
			
			VendorLeaseEqptService dao = new VendorLeaseEqptService();
			List<VendorLeaseEqptVO> list = dao.getOrderEqptsByOmno(om_no);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
		if ("updataGetTime".equals(action)) { 
			String oe_no = req.getParameter("oe_no");
			String om_no = req.getParameter("om_no");
			
			VendorLeaseEqptService dao = new VendorLeaseEqptService();
			OrderEqptService orderEqptSvc = new OrderEqptService();
			orderEqptSvc.updatagettime(oe_no);
			List<VendorLeaseEqptVO> list = dao.getOrderEqptsByOmno(om_no);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
		if ("updataBackTime".equals(action)) { 
			OrderEqptService orderEqptSvc = new OrderEqptService();
			VendorLeaseEqptService dao = new VendorLeaseEqptService();
			EquipmentService eqptSvc = new EquipmentService();
			CarEqptService CESvc =new CarEqptService();
			
			
			String oe_no = req.getParameter("oe_no");
			String om_no = req.getParameter("om_no");
			Integer oe_reqty = new Integer(req.getParameter("oe_reqty").trim());
			Integer oe_qty = new Integer(req.getParameter("oe_qty").trim());
			Integer iq=0;
			
			List<VendorLeaseEqptVO> list = dao.getOrderEqptsByOmno(om_no);
			if(oe_reqty != oe_qty){
				iq = oe_qty - (oe_qty - oe_reqty);
				
				eqptSvc.updatEqptQty(oe_no, iq);
				CESvc.updateCarEqpt(oe_no, iq);
				
			}
			orderEqptSvc.updatabacktime(oe_no, oe_reqty);
			list = dao.getOrderEqptsByOmno(om_no);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
	}	
}
