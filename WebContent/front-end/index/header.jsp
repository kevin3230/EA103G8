<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.vendor.model.*"%>

<%
	MembersVO memVO2 = (MembersVO)session.getAttribute("memVO");
	VendorVO vendorVO2 = (VendorVO)session.getAttribute("vendorVO");
%>

<div id="header">
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-0 justify-content-end fixed-top">
		<!-- logo -->
		<div class="d-flex flex-grow-1">
			<a href="<%=request.getContextPath()%>/front-end/index/index.jsp">
				<img id="logo" src="<%=request.getContextPath()%>/front-end/index/images/logo/logo3_trans.png" alt="PLAMPING">
			</a>
		</div>

		<!-- 導覽列上的選項 -->
		<div class="collapse navbar-collapse flex-grow-0 bg-dark" id="navbarNav">	<!-- #navbarNav: 為了hamburger -->
			<ul class="navbar-nav">
				<!-- .action: 標示目前位置 -->
				<li class="nav-item d-flex align-items-center justify-content-center">
					<a class="nav-link px-3 text-header" href="<%=request.getContextPath()%>/front-end/search/search.jsp">尋找營區</a>
				</li>
				<li class="nav-item d-flex align-items-center justify-content-center">
					<a class="nav-link px-3 text-header" href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">瀑布牆</a>
				</li>
				<li class="nav-item d-flex align-items-center justify-content-center">
					<a class="nav-link px-3 text-header" href="<%=request.getContextPath()%>/front-end/information/activity.jsp">活動快報</a>
				</li>
				<li class="nav-item d-flex align-items-center justify-content-center">
					<a class="nav-link px-3 text-header" href="<%=request.getContextPath()%>/front-end/information/guide.jsp">露營指南</a>
				</li>
				<li class="nav-item d-flex align-items-center justify-content-center">
					<a class="nav-link px-3 text-header" href="<%=request.getContextPath()%>/front-end/information/eqptIntro.jsp">裝備介紹</a>
				</li>
			</ul>
		</div>

		<!-- icon -->
		<div id="navIcon" class="ml-3 d-flex align-items-center">
			<c:if test="<%=vendorVO2 == null && memVO2 == null%>">
				<a class="text-header px-3" href="#" class="envelopeicon">
					<i class="fas fa-envelope fa-lg"></i>
				</a>
			</c:if>

			<c:if test="<%=vendorVO2 != null || memVO2 != null%>">
				<div class="dropdown">
					<a class="text-header px-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown">
						<i class="fas fa-envelope fa-lg"></i></a>
					<div class="dropdown-menu dropdown-menu-right mt-2" id="newnotice">
					</div>
				</div>
			</c:if>

			<a class="text-header px-3" href="<%=request.getContextPath()%>/front-end/carorder/confirmOrder.jsp">
				<i class="fas fa-shopping-cart fa-lg"></i>
			</a>

			<c:if test="<%=memVO2 == null && vendorVO2 == null%>">
				<a class="text-header px-3" href="<%=request.getContextPath()%>/front-end/index/MemVdSignInSignUp.jsp"><i class="fas fa-user-circle fa-lg"></i></a>
			</c:if>
			
			<c:if test="<%=memVO2 != null || vendorVO2 != null%>">
				<div class="dropdown">
					<a class="text-header px-3" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown"><i class="fas fa-user-circle fa-lg"></i></a>
	
					<div class="dropdown-menu dropdown-menu-right mt-2">
						<c:if test="<%=memVO2 != null%>">
							<a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/members/MembersInfo.jsp">會員專區</a>
							<a class="dropdown-item" href="<%=request.getContextPath()%>/members/MembersServlet?action=memUpdateSubmit&mem_no=M000000001">修改會員資料</a>
							<a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp">訂單管理</a>
							<div class="dropdown-divider"></div>
		 					<a class="dropdown-item" href="<%=request.getContextPath()%>/members/MembersServlet?action=memSignOutSubmit">登出</a>
	 					</c:if>
	 					
	 					<c:if test="<%=vendorVO2 != null && memVO2 == null%>">
							<a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/vendor/VendorInfo.jsp">業者專區</a>
							<div class="dropdown-divider"></div>
		 					<a class="dropdown-item" href="<%=request.getContextPath()%>/vendor/VendorServlet?action=vdSignOutSubmit">登出</a>
	 					</c:if>
					</div>
				</div>
			</c:if>
		</div>

		<!-- hamburger -->
		<button id="hamburger" class="navbar-toggler ml-3" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	</nav>
</div>