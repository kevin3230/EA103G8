<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>1

<%
AdminisService AdminisSvc = new AdminisService();
    List<AdminisVO> list = AdminisSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有員工資料 - listAllAdminis.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有員工資料 - listAllAdminis.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>管理員編號</th>
		<th>姓名</th>
		<th>郵件</th>
		<th>註冊日期</th>
		<th>部門</th>
		<th>權限值</th>
		<th>管理員狀態</th>
		<th>修改</th>
		<th>解雇</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="adminisVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>" >
		
		<tr>
			<td>${adminisVO.adminis_no}</td>
			<td>${adminisVO.adminis_name}</td>
			<td>${adminisVO.adminis_email}</td>
			<td>${adminisVO.adminis_regdate}</td>
			<td>${adminisVO.adminis_dept}</td>
			<td>${adminisVO.adminis_pv}</td> 
	
			
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/adminis/adminis.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="adminis_no"  value="${adminisVO.adminis_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/adminis/adminis.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="adminis_no"  value="${adminisVO.adminis_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>