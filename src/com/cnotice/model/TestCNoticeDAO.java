package com.cnotice.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestCNoticeDAO")
public class TestCNoticeDAO extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		CNoticeDAO dao = new CNoticeDAO();
		CNoticeVO cnv = new CNoticeVO();
		
		cnv.setCn_memno("M000000001");
		cnv.setCn_omno("O000000001");
		cnv.setCn_content("您於V000000001成立了一筆新訂單，訂單編號:O000000001");
		cnv.setCn_type("add");
		cnv.setCn_stat(1);
		dao.add(cnv);
	}
}

