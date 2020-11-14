<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.vendor.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>預約露營地入口</title>
</head>
<body>
	<%
		VendorDAO vendorDAO = new VendorDAO();
		session.setAttribute("vendorVO", vendorDAO.findByPrimaryKey("V000000001"));
	
		MembersService memSvc = new MembersService();
		MembersVO membersVO = memSvc.getOneMem("M000000002");
		session.setAttribute("memVO", membersVO);
	%>

	<form id="form" action="<%=request.getContextPath()%>/carcamp/carCamp.do" method="GET">
		<input type="hidden" name="action" value="reserveCamp">
		<input type="hidden" name="vd_no" value="V000000001">
	</form>

	<script>
// 		form.submit();
	</script>
</body>
</html>