<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<nav class="flex-column col-2" id="memSidebar">
		<h3>會員專區</h3>
	 	<a class="nav-link" href="<%= request.getContextPath()%>/members/MembersServlet?action=memGetInfo">會員資訊</a>
	 	<a class="nav-link" href="<%=request.getContextPath()%>/carcamp/carCamp.do?action=reserveCamp">購物車</a>
	 	<a class="nav-link" href="<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp">訂單管理</a>
	 	<a class="nav-link" href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">瀑布牆</a>
	 	<a class="nav-link" href="<%=request.getContextPath()%>/front-end/information/faq.jsp">FAQ</a>
	    <div>
		    <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">
		      <input type="hidden" name="action" value="memSignOutSubmit">
		      <input type="submit" name="會員登出" value="會員登出" class="btn btn-outline-secondary btn-sm" id="logout">
		    </form>
	    </div>
	</nav>