package com.cgintro.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.camp.model.CampService;
import com.camp.model.CampVO;
import com.cgintro.model.CGIPicService;
import com.cgintro.model.CGIPicVO;
import com.cgintro.model.CGIntroService;
import com.cgintro.model.CGIntroVO;

@WebServlet("/cgintro/cgintro.do")
public class CgintroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if ("getOneCampPic".equals(action)) {
			String camp_no = req.getParameter("camp_no");
			if (camp_no == null)
				return;
			
			CampService dao = new CampService();
			CampVO campVO = dao.getOneCamp(camp_no);
			if (campVO == null)
				return;
			
			ServletOutputStream out = res.getOutputStream();
			out.write(campVO.getCamp_pic());
			out.close();
		}
		
		if ("getOneCGIPPic".equals(action)) {
			String cgip_no = req.getParameter("cgip_no");
			if (cgip_no == null)
				return;

			CGIPicService dao = new CGIPicService();
			CGIPicVO cgiPicVO = dao.getOneCGIPic(cgip_no);
			if (cgiPicVO == null)
				return;
			
			ServletOutputStream out = res.getOutputStream();
			out.write(cgiPicVO.getCgip_pic());
			out.close();
		}
		
		if ("goToCGIntro".equals(action)) {
			String vd_no = req.getParameter("vd_no");
			
			CGIntroService dao = new CGIntroService();
			CGIntroVO cgIntroVO = dao.getCGIntroByVdno(vd_no);
			
			String url = null;
//			if (cgIntroVO == null)
//				url = "/front-end/cgintro/pageNotFound.html";
//			else {
				url = "/front-end/cgintro/campGroundIntro.jsp";
//			}
			RequestDispatcher view = req.getRequestDispatcher(url);
			view.forward(req, res);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}