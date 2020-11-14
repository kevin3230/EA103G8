<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="d-flex flex-column col-2 text-center p-0" id="memSidebar" style="background-color:rgba(180,180,180,0.25);">

		<h3 class="my-3">會員專區</h3>

		<div class="card">
    		<div class="card-header aButton">
	 			<a class="btn btn-link" href="<%= request.getContextPath()%>/members/MembersServlet?action=memGetInfo">會員資料</a>
	 		</div>
	 	</div>

	 	<div class="card">
    		<div class="card-header aButton">
	 			<a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/carorder/confirmOrder.jsp">購物車</a>
	 		</div>
	 	</div>

	 	<div class="card">
    		<div class="card-header aButton">
	 			<a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp">訂單管理</a>
	 		</div>
	 	</div>

	 	<div class="card">
    		<div class="card-header aButton">
	 			<a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">瀑布牆</a>
	 		</div>
	 	</div>
	 	
	 	<div class="card">
    		<div class="card-header aButton">
	 			<a class="btn btn-link" href="<%=request.getContextPath()%>/front-end/information/faq.jsp">FAQ</a>
	 		</div>
	 	</div>

	</div>