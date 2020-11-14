package com.cnotice.model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cnotice.controller.CVNoticeWebsocket;
import com.ordermaster.model.*;

@WebServlet("/neworderNotice")
public class neworderNotice extends HttpServlet {
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
       
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	
		CVNoticeWebsocket cvn = new CVNoticeWebsocket();
		
		OrderMasterService oms = new OrderMasterService();
		OrderMasterJDBCDAO ojdbc = new OrderMasterJDBCDAO();
		OrderMasterVO ovo = new OrderMasterVO();
	
		ovo = oms.addOrderMaster("O000000002", "M000000013", "V000000001", 1, "XXX",
				99999, "1111222233334444");
		
		cvn.newOrder(ovo);
	}

}
