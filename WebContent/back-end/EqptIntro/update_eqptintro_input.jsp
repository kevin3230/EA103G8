<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.eqptintro.model.*"%>

<%
  EqptIntroVO eqptintroVO = (EqptIntroVO) request.getAttribute("eqptintroVO"); 
//EqptIntroServlet.java (Concroller) 存入req的eqptintroVO物件 (包括幫忙取出的eqptintroVO, 也包括輸入資料錯誤時的eqptintroVO物件)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>裝備介紹修改 - update_eqptintro_input.jsp</title>

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
		 <h3>裝備介紹修改 - update_eqptintro_input.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/back-end/eqptIntro/select_page.jsp"><img src="<%=request.getContextPath()%>/back-end/eqptIntro/images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料修改:</h3>

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
		<td>裝備介紹編號:<font color=red><b>*</b></font></td>
		<td><%=eqptintroVO.getEi_no()%></td>
	</tr>
	<tr>
		<td>管理員編號:</td>
		<td><input type="TEXT" name="ei_adminisno" size="45" value="<%=eqptintroVO.getEi_adminisno()%>" /></td>
	</tr>
	<tr>
		<td>裝備介紹標題:</td>
		<td><input type="TEXT" name="ei_title" size="45"	value="<%=eqptintroVO.getEi_title()%>" /></td>
	</tr>
	<tr>
		<td>裝備介紹內容:</td>
		<td><input type="TEXT" name="ei_content" size="45"	value="<%=eqptintroVO.getEi_content()%>" /></td>
	</tr>
	<tr>
		<td>發表狀態:</td>
		<td><input type="TEXT" name="ei_stat" size="45"	value="<%=eqptintroVO.getEi_stat()%>" /></td>
	</tr>
	
		
</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="ei_no" value="<%=eqptintroVO.getEi_no()%>">
<input type="submit" value="送出修改"></FORM>
</body>

</html>