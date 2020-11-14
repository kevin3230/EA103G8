<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.eqptintro.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    EqptIntroService eqptintroSvc = new EqptIntroService();
    List<EqptIntroVO> list = eqptintroSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有裝備介紹 - listAllEqptIntro.jsp</title>

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
	width: 800px;
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有裝備介紹 - listAllEqptIntro.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/eqptIntro/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/eqptIntro/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		<th>裝備介紹編號</th>
		<th>管理員編號</th>
		<th>裝備介紹標題</th>
		<th>裝備介紹內容</th>
		<th>最後編輯時間</th>
		<th>發表狀態</th>
		<th>修改</th>
		<th>刪除</th>
		
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="eqptintroVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${eqptintroVO.ei_no}</td>
			<td>${eqptintroVO.ei_adminisno}</td>
			<td>${eqptintroVO.ei_title}</td>
			<td>${eqptintroVO.ei_content}</td>
			<td>${eqptintroVO.ei_edit}</td>
			<td>${eqptintroVO.ei_stat}</td> 
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="ei_no"  value="${eqptintroVO.ei_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="ei_no"  value="${eqptintroVO.ei_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>