package filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginLoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 【取得 session】
		HttpSession session = req.getSession();
		// 【從 session 判斷此user是否登入過】
		Object vd_no = session.getAttribute("vd_no");
		Object mem_no = session.getAttribute("mem_no");
		
		if (vd_no != null || mem_no != null) {
			String location = "";
			if (req.getQueryString() == null)
				location = req.getRequestURI();
			else
				location = req.getRequestURI() + "?" + req.getQueryString();
			
			session.setAttribute("location", location);
			res.sendRedirect(req.getContextPath() + "/front-end/index/index.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}
}