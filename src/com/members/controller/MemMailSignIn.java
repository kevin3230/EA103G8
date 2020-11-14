package com.members.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.members.model.MembersService;
import com.members.model.MembersVO;

@WebServlet("/members/MemMailSignIn")
public class MemMailSignIn extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}       

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		MembersService memSvc = new MembersService();
		
		String mem_email = req.getParameter("mem_email");
		MembersVO memVO = new MembersVO();
		memVO = memSvc.signInMem(mem_email);								//取得會員資訊
		
		HttpSession session = req.getSession();		  					//取得session
		session.setAttribute("memVO", memVO);							//會員資訊存入session
	
		res.sendRedirect(req.getContextPath() + "/front-end/members/MembersInfo.jsp");	//重導回會員首頁
	
	}

}
