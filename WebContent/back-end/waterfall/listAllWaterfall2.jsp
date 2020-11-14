<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.waterfall.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	WaterfallService waterfallSvc = new WaterfallService();
    List<WaterfallVO> list = waterfallSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有瀑布牆文章 - listAllWaterfall.jsp</title>

<style>
  table#table-1 {
	background-color: #b2663e;
    border: 2px solid lightgray;
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
		 <h3>所有瀑布牆文章 - listAllWaterfall.jsp</h3>
		 <h4><a href="<%=request.getContextPath()%>/front-end/waterfall/select_page.jsp">
		 <img src="<%=request.getContextPath()%>/front-end/images/tomcat.png" width="100" height="32" border="0">回首頁
		 </a></h4>
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
		<th>文章編號</th>
		<th>發文者會員編號</th>
		<th>文章標題</th>
		<th>文章內容</th>
		<th>最後編輯時間</th>
		<th>文章狀態</th>
		<th>修改</th>
		<th>查看</th>
		
	</tr>
	
	<%@ include file="page1.file" %> 
	<c:forEach var="waterfallVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<c:set var="string1" value="${waterfallVO.wf_content}"/>	
		<c:set var="string2" value="${fn:substring(string1, 0, 40)}"/>
		<tr>
			<td>${waterfallVO.wf_no}</td>
			<td>${waterfallVO.wf_memno}</td>
			<td>${waterfallVO.wf_title}</td>
			<td>${string2}...</td>
			<td>${waterfallVO.wf_edit}</td>
			<c:if test="${waterfallVO.wf_stat ==1}">
								<td>有效</td>
							</c:if>
							<c:if test="${waterfallVO.wf_stat ==0}">
								<td>無效</td>
							</c:if> 
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/waterfall/waterfall.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="wf_no"  value="${waterfallVO.wf_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/waterfall/waterfall.do" style="margin-bottom: 0px;">
			     <input type="submit" value="(read more)">
			     <input type="hidden" name="wf_no"  value="${waterfallVO.wf_no}">
			     <input type="hidden" name="action" value="getOne_For_Display"></FORM>
			</td>
		</tr>
		
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>