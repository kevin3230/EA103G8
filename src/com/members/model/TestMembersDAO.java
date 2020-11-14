package com.members.model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestMembersDAO")
public class TestMembersDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		
		res.setContentType("text/plain; charset=Big5");
		PrintWriter out = res.getWriter();

		MembersDAO dao = new MembersDAO();
		MembersVO mv = new MembersVO();
		
		mv = dao.findByPK("M000000001");
		
		out.print(mv.getMem_no() + ",");
		out.print(mv.getMem_email() + ",");
		out.print(mv.getMem_pwd() + ",");
		out.print(mv.getMem_name() + ",");
		out.print(mv.getMem_alias() + ",");
		out.print(mv.getMem_gender() + ",");
		out.print(mv.getMem_birth() + ",");
		out.print(mv.getMem_mobile() + ",");
		out.print(mv.getMem_tel() + ",");
		out.print(mv.getMem_addr() + ",");
		out.print(mv.getMem_type() + ",");
		out.print(mv.getMem_regdate() + ",");
		out.print(mv.getMem_stat());
		
//		EmpService aa = new EmpService();
//		EmpVO mem3= new EmpVO();
//		
//		mem3 = aa.getOneEmp(7001);
//		
//		System.out.print(mem3 + ",");
//		System.out.print(mem3.getEmpno() + ",");
//		System.out.print(mem3.getEname() + ",");
//		System.out.print(mem3.getJob() + ",");
//		System.out.print(mem3.getHiredate() + ",");
//		System.out.print(mem3.getSal() + ",");
//		System.out.print(mem3.getComm() + ",");
//		System.out.print(mem3.getDeptno() + ",");
		
	}
}
