<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.adminis.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO"); //EmpServlet.java(Concroller), 存入req的adminisVO物件
%>

<html>
<head>
<title>員工資料 - listOneadmin.jsp</title>

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
	width: 600px;
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

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>員工資料 - ListOneEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>管理員編號</th>
		<th>姓名</th>
		<th>郵件</th>
		<th>部門</th>
		<th>註冊日期</th>
		<th>權限值</th>
		<th>管理員狀態</th>
	</tr>
	<tr>
		<td><%=adminisVO.getAdminis_no()%></td>
		<td><%=adminisVO.getAdminis_name()%></td>
		<td><%=adminisVO.getAdminis_email()%></td>
		<td><%=adminisVO.getAdminis_dept()%></td>
		<td><%=adminisVO.getAdminis_regdate()%></td>
		<td><%=adminisVO.getAdminis_pv()%></td>
		<td><%=adminisVO.getAdminis_stat()%></td>
	</tr>
</table>

</body>
</html>