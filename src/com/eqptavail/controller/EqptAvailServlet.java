package com.eqptavail.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eqptavail.model.*;




@WebServlet("/eqptavail/eqptAvail.do")
public class EqptAvailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter pw = res.getWriter();

		String action = req.getParameter("action");
		
		
		if ("getAllByEqptno".equals(action)) { 
			String ea_eqptno = req.getParameter("ea_eqptno");
			EqptAvailService dao = new EqptAvailService();
			List<EqptAvailVO> list = dao.getAllByEqptno(ea_eqptno);
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
	
		if ("getAll".equals(action)) { 
			EqptAvailService dao = new EqptAvailService();
			List<EqptAvailVO> list = dao.getAll();
			String str = new JSONArray(list).toString();
			pw.print(str);
		}
		
		if ("getOne".equals(action)) { 
			String ea_eqptno = req.getParameter("ea_eqptno");
			String ea_date = req.getParameter("ea_date");
			EqptAvailService dao = new EqptAvailService();
			
			EqptAvailVO eqptAvailVO = null;
			try {
				eqptAvailVO = dao.getOne(ea_eqptno, Date.valueOf(ea_date));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.print(new JSONObject(eqptAvailVO));
			
			
		}
	
}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}