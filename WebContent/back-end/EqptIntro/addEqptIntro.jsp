<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.eqptintro.model.*"%>

<%
 EqptIntroVO eqptintroVO = (EqptIntroVO) request.getAttribute("eqptintroVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>裝備介紹新增 - addEqptIntro.jsp</title>

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
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #b2663e;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>裝備介紹新增 - addEqptIntro.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/back-end/eqptIntro/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/eqptIntro/images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" name="form1">
<table>
	<tr>
		<td>管理員編號:</td>
		<td><input type="TEXT" name="ei_adminisno" size="45" 
			 value="<%= (eqptintroVO==null)? "A000000009" : eqptintroVO.getEi_adminisno()%>" /></td>
	</tr>
	<tr>
		<td>標題:</td>
		<td><input type="TEXT" name="ei_title" size="45"
			 value="<%= (eqptintroVO==null)? "HELLO" : eqptintroVO.getEi_title()%>" /></td>
	</tr>
	<tr>
		<td>內文:</td>
		<td><input type="TEXT" name="ei_content" size="45"
			 value="<%= (eqptintroVO==null)? "123123" : eqptintroVO.getEi_content()%>" /></td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>