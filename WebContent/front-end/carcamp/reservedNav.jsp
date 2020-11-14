<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.carcamp.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="java.util.List"%>

<%
	String om_no = request.getParameter("om_no");
	String vd_no = (String) request.getAttribute("vd_no");
	MembersVO memVO3 = (MembersVO) session.getAttribute("memVO");
	
	CarCampService dao = new CarCampService();
	List<CarCampVO> carCampVOList2 = dao.getCarCampsByMemno(memVO3.getMem_no());
	
	// 判斷狀態
	Boolean editOrder = false;
	Boolean undone = false;
	Boolean newOrder = false;
	if (om_no != null)
		editOrder = true;
	else if (carCampVOList2.size() > 0)
		undone = true;
	else if (vd_no != null)
		newOrder = true;
	
	System.out.println("editOrder " + editOrder);
	System.out.println("undone: " + undone);
	System.out.println("newOrder: " + newOrder);
	
	// 得知目前在第幾步驟
	String[] servletPath = new String[5];
	servletPath[0] = "reserveCamp.jsp";
	servletPath[1] = "carEqpt.jsp";
	servletPath[2] = "reserveFood.jsp";
	servletPath[3] = "confirmOrder.jsp";
	servletPath[4] = "checkoutComplete.jsp";
	
	Boolean[] step = {false, false, false, false, false};
	for (int i = 0; i < servletPath.length; i++) {
		if (request.getServletPath().lastIndexOf(servletPath[i]) > 0)
			step[i] = true;
	}
%>

<div class="container">
	<div class="row">
		<div id="reservedNav" class="col-12">
			<ul class="d-flex justify-content-center">
				<li id="step1">
					<c:if test="<%=step[0] || step[4]%>">
						<a>
					</c:if>
					<c:if test="<%=!(step[0] || step[4])%>">
						<c:if test="<%=editOrder%>">
							<a href="<%=request.getContextPath()%>/carcamp/carCamp.do?action=editOrder&om_no=<%=om_no%>">
						</c:if>
						<c:if test="<%=undone%>">
							<a href="<%=request.getContextPath()%>/carcamp/carCamp.do?action=reserveCamp">
						</c:if>
						<c:if test="<%=newOrder%>">
							<a href="<%=request.getContextPath()%>/carcamp/carCamp.do?action=reserveCamp&vd_no=<%=vd_no%>">
						</c:if>
					</c:if>
						<strong>01</strong>
						&nbsp;預約營位&nbsp;&nbsp;&gt;
					</a>
				</li>
				<li id="step2">
					<c:if test="<%=step[1] || step[4] || newOrder%>">
						<a>
					</c:if>
					<c:if test="<%=!(step[1] || step[4] || newOrder)%>">
						<c:if test="<%=editOrder%>">
							<a href="<%=request.getContextPath()%>/front-end/careqpt/carEqpt.jsp?action=editOrder&om_no=<%=om_no%>">
						</c:if>
						<c:if test="<%=!editOrder%>">
							<a href="<%=request.getContextPath()%>/front-end/careqpt/carEqpt.jsp">
						</c:if>
					</c:if>
								&nbsp;&nbsp;<strong>02</strong>
								&nbsp;租借裝備&nbsp;&nbsp;&gt;
							</a>
				</li>
				<li id="step3">
					<c:if test="<%=step[2] || step[4] || newOrder%>">
						<a>
					</c:if>
					<c:if test="<%=!(step[2] || step[4] || newOrder)%>">
						<c:if test="<%=editOrder%>">
							<a href="<%=request.getContextPath()%>/front-end/carfood/reserveFood.jsp?action=editOrder&om_no=<%=om_no%>">
						</c:if>
						<c:if test="<%=!editOrder%>">
							<a href="<%=request.getContextPath()%>/front-end/carfood/reserveFood.jsp">
						</c:if>
					</c:if>
								&nbsp;&nbsp;<strong>03</strong>
								&nbsp;訂購食材&nbsp;&nbsp;&gt;
							</a>
				</li>
				<li id="step4">
					<c:if test="<%=step[3] || step[4] || newOrder%>">
						<a>
					</c:if>
					<c:if test="<%=!(step[3] || step[4] || newOrder)%>">
						<c:if test="<%=editOrder%>">
							<a href="<%=request.getContextPath()%>/carorder/OrderMasterServlet?action=editOrder&om_no=<%=om_no%>">
						</c:if>
						<c:if test="<%=!editOrder%>">
							<a href="<%=request.getContextPath()%>/carorder/OrderMasterServlet?action=editOrder">
						</c:if>
					</c:if>
							&nbsp;&nbsp;<strong>04</strong>
							&nbsp;訂單確認&nbsp;&nbsp;&gt;
						</a>
				</li>
				<li id="step5">
					&nbsp;&nbsp;<strong>05</strong>
					&nbsp;預約完成
				</li>
			</ul>
		</div>
	</div>
</div>