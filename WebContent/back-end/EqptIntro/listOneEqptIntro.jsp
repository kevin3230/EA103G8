<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eqptintro.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
EqptIntroVO eqptintroVO = (EqptIntroVO) request.getAttribute("eqptintroVO"); 
//EqptIntroServlet.java(Concroller), 存入req的eqptintroVO物件
%>

<html>
<head>
<title>裝備介紹 - listOneEqptIntro.jsp</title>

<style>
  table#table-1 {
	background-color: #b2663e;
    border: 2px solid white;
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
    border: 1px solid #b2663e;
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
		 <h3>裝備介紹 - listOneEqptIntro.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/eqptIntro/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/eqptIntro/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>裝備介紹編號</th>
		<th>管理員編號</th>
		<th>裝備介紹標題</th>
		<th>裝備介紹內容</th>
		<th>發表狀態</th>
	</tr>
	<tr>
		<td>${eqptintroVO.ei_no}</td>
		<td>${eqptintroVO.ei_adminisno}</td>
		<td>${eqptintroVO.ei_title}</td>
		<td>${eqptintroVO.ei_content}</td>
		<td>${eqptintroVO.ei_stat}</td> 
	</tr>
</table>

</body>
</html>