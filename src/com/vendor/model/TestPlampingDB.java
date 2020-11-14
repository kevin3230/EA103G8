package com.vendor.model;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.naming.*;
import javax.sql.*;
import java.sql.*;

@WebServlet("/TestPlampingDB")
public class TestPlampingDB extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = res.getWriter();

		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
			if (ds != null) {
				Connection conn = ds.getConnection();

				if (conn != null) {
					out.println("Got Connection: " + conn.toString());
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select * from vendor");
					while (rs.next()) {
						out.println("vd_no = " + rs.getString(1));
					}
					conn.close();
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}