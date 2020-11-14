package com.vendor.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vendor.model.VendorService;
import com.vendor.model.VendorVO;

@WebServlet("/vendor/VdMailSignIn")
public class VdMailSignIn extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}   

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		VendorService vendorSvc = new VendorService();
		
		String vd_email = req.getParameter("vd_email");
		VendorVO vendorVO = new VendorVO();
		vendorVO = vendorSvc.signInVendor(vd_email);								//取得會員資訊
		
		HttpSession session = req.getSession();		  						//取得session
		session.setAttribute("vendorVO", vendorVO);							//會員資訊存入session
	
		res.sendRedirect(req.getContextPath() + "/front-end/vendor/VendorInfo.jsp");	//重導回會員首頁
	
	}

}
