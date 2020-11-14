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
import org.json.JSONObject;

import com.vendor.model.*;

@WebServlet("/vendor/VendorSiteList")
public class VendorSiteList extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
				throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		
		VendorService vendorSvc = new VendorService();
	    List<VendorVO> list = vendorSvc.getAllnopic();
	    
//	    for ( VendorVO vendorVO : list) {
//	    	String Str = vendorVO.getVd_cgaddr();
//	    	out.print(Str);
//	    }
	    
	    String jsonStr = new JSONArray(list).toString();
	    out.print(jsonStr);
	}
}
