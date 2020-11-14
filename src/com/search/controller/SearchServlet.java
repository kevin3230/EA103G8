package com.search.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.cgintro.model.CGIPicService;
import com.cgintro.model.CGIPicVO;
import com.cgintro.model.CGIntroService;
import com.cgintro.model.CGIntroVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.search.model.SearchService;
import com.search.model.SearchVO;

@WebServlet("/search/search.do")
public class SearchServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		
		String action = req.getParameter("action");
		
		if ("searchCamp".equals(action)) {
			PrintWriter pw = res.getWriter();
			
			String filter = req.getParameter("filter");
			Gson gson = new Gson();
			JsonObject jsonObj = gson.fromJson(filter, JsonObject.class);
			
			SearchService dao = new SearchService();
			List<SearchVO> list = dao.getResult(jsonObj);
			pw.write(new JSONArray(list).toString());
			pw.close();
			
//			// 顯示搜尋結果
//			if (list.isEmpty())
//				System.out.println("沒有相符項目");
//			for (SearchVO searchVO : list) {
//				System.out.print(searchVO.getVd_no() + ", ");
//				System.out.print(searchVO.getVd_cgname() + ", ");
//				System.out.print(searchVO.getVd_cgaddr() + ", ");
//				System.out.print(searchVO.getMinListPrice() + ", ");
//				System.out.print(searchVO.getMinPrice());
//				System.out.println();
//			}
		}
		
		if ("getOneCGIPPic".equals(action)) {
			ServletOutputStream out = res.getOutputStream();
						
			try {
				String vd_no = req.getParameter("vd_no");
				
				CGIntroService cgIntroSvc = new CGIntroService();
				CGIntroVO cgIntroVO = cgIntroSvc.getCGIntroByVdno(vd_no);
				
				CGIPicService cgiPicSvc = new CGIPicService();
				List<CGIPicVO> list = cgiPicSvc.getCGIPicsByCgino(cgIntroVO.getCgi_no());
				out.write(list.get(0).getCgip_pic());
				
			} catch (NullPointerException e) {
				// 業者沒有露營區介紹 或 沒有介紹圖片
				return;
			} finally {
				out.close();
			}
		}
	}
}