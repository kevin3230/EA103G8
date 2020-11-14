package com.vnotice.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestVNoticeDAO")
public class TestVNoticeDAO extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		VNoticeDAO dao = new VNoticeDAO();
		VNoticeVO vnv = new VNoticeVO();
		
		vnv.setVn_vdno("V000000001");
		vnv.setVn_omno("O000000001");
		vnv.setVn_content("您有一張來自M000000001的新訂單,，訂單編號:O000000001");
		vnv.setVn_type("add");
		vnv.setVn_stat(1);
		dao.add(vnv);
	}
}
