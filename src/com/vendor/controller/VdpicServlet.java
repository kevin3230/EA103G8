package com.vendor.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vendor.model.*;

@WebServlet("/vendor/VdpicServlet")
public class VdpicServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		doGet(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
	
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		String vd_no = req.getParameter("vd_no");
		VendorService vdSvc = new VendorService();
		VendorVO vendorVO = vdSvc.getVdpic(vd_no);
		
		if (vd_no == null)
			return;
		
		out.write(vendorVO.getVd_brc());
		out.flush();
		out.close();
	
	}
}
