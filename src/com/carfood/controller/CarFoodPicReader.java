package com.carfood.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/CarFoodPicReader/CarFoodPicReader.do")
public class CarFoodPicReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Connection con;
	
    public CarFoodPicReader() {
        super();
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			Statement stmt = con.createStatement();
			String food_no = req.getParameter("food_no").trim();
			String food_vdno = req.getParameter("food_vdno").trim();
			ResultSet rs = stmt.executeQuery(
				"SELECT food_pic FROM FOOD WHERE food_vdno='" + food_vdno + "' AND food_no='" + food_no +"' AND food_stat=2");

			if (rs.next()) {
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("food_pic"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {
					out.write(buf, 0, len);
				}
				in.close();
			} else {
				//res.sendError(HttpServletResponse.SC_NOT_FOUND);
				InputStream in = getServletContext().getResourceAsStream("\"" + req.getContextPath()+ "/front-end/carfood/images/null2.jpg");
				byte[] buf = new byte[in.available()];
				in.read(buf);
				out.write(buf);
				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			//System.out.println(e);
			InputStream in = getServletContext().getResourceAsStream("\"" + req.getContextPath()+ "/front-end/carfood/images/null2.jpg");
			byte[] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			in.close();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		try {
			Context ctx = new javax.naming.InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/PlampingDB");
			con = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			if (con != null) con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}
