<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<div class="d-flex flex-column col-2 text-center" id="vdSidebar" style="border: 1px solid #a6a6a6;">
		<h3 class="my-3">業者專區</h3>
		<a class="nav-link" href="<%= request.getContextPath()%>/vendor/VendorServlet?action=vdGetInfo">基本資料</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/front-end/membersorder/vendorsOrder.jsp">訂單管理</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/cgintro/cgintro.do?action=goToCGIntro&vd_no=${vendorVO.vd_no}">營區資訊</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/front-end/camp/select_page.jsp">商品上架</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/front-end/vendor/VendorLeaseEqpt.jsp">裝備出租</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/front-end/vendorfoodstatic/foodStatic.jsp">食材管理</a>
		<a class="nav-link" href="<%=request.getContextPath()%>/front-end/promotion/promoHome.jsp">促銷專案</a>
	    <div>
	      <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8">
	        <input type="hidden" name="action" value="vdSignOutSubmit">
	        <input type="submit" name="業者登出" value="業者登出" class="btn btn-outline-secondary btn-sm" id="logout">
	      </form>
	    </div>
	</div>


