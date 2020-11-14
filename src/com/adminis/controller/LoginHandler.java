package com.adminis.controller;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminis.model.AdminisService;
import com.adminis.model.AdminisVO;

import javax.servlet.annotation.WebServlet;

@WebServlet("/loginhandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// 【檢查使用者輸入的帳號(account) 密碼(password)是否有效】
	// 【實際上應至資料庫搜尋比對】
	protected boolean allowUser(String adminis_no, String adminis_pwd) {

		AdminisService adminisSvc = new AdminisService();
		List<AdminisVO> list = adminisSvc.getAll();
		for (AdminisVO adminis : list) {
			if (adminis_no.equals(adminis.getAdminis_no()) && adminis_pwd.equals(adminis.getAdminis_pwd())) {
				return true;
			}
		}
		return false;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();

		// 【取得使用者 帳號(account) 密碼(password)】
		String adminis_no = req.getParameter("adminis_no");
		String adminis_pwd = req.getParameter("adminis_pwd");

		// 【檢查該帳號 , 密碼是否有效】
		if (!allowUser(adminis_no, adminis_pwd)) { // 【帳號 , 密碼無效時】
			out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
			out.println("<BODY>你的帳號 , 密碼無效!<BR>");
			out.println("請按此重新登入 <A HREF=" + req.getContextPath() + "/back-end-login.jsp>重新登入</A>");
			out.println("</BODY></HTML>");
		} else { // 【帳號 , 密碼有效時, 才做以下工作】
			HttpSession session = req.getSession();
			AdminisVO adminisVO = new AdminisVO();
			AdminisService adminisSvc = new AdminisService();
			List<AdminisVO> list = adminisSvc.getAll();
			for (AdminisVO adminis : list) {
				if (adminis_no.equals(adminis.getAdminis_no())) {
					adminisVO = adminis;
					break;
				}
			}
			session.setAttribute("adminis_no", adminis_no); // *工作1: 才在session內做已經登入過的標識
			session.setAttribute("adminisVO", adminisVO);
//	     	session.setAttribute("authorityList", authorityList);

			try {
				String location = (String) session.getAttribute("location");
				if (location != null) {
					session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
					res.sendRedirect(location);
					return;
				}
			} catch (Exception ignored) {
			}

			res.sendRedirect(req.getContextPath() + "/back-end/wfreport/listAllWfreport.jsp"); // *工作3:
																					// (-->如無來源網頁:則重導至login_success.jsp)
		}
	}
}