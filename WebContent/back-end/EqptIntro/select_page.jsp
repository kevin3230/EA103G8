<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM EqptIntro: Home</title>

<style>
  #div1{
  text-align: center;
  margin: 0px auto;
  }
  table#table-1 {
	width: 100%;
	background-color: #b2663e;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge white;
    height: 80px;
    text-align: center;
    margin: 0px auto;
  }
  table#table-1 h4 {
    color: white;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
  #div3{
  text-align: center;
  }
  #div2{
  display: inline-block;
  text-align: left;
  }
  
</style>

</head>
<body >

<table id="table-1">
   <tr><td><h3>IBM EqptIntro: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<div id="div1">
<p>This is the Home page for IBM EqptIntro: Home</p>
</div>

<div id="div3">
<div id="div2">
<h3>資料查詢:</h3>
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/eqptIntro/listAllEqptIntro.jsp'>List</a> all EqptIntro.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" >
        <b>輸入裝備介紹編號 (如EI00000009):</b>
        <input type="text" name="ei_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="eqptintroSvc" scope="page" class="com.eqptintro.model.EqptIntroService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" >
       <b>選擇裝備介紹編號:</b>
       <select size="1" name="ei_no">
         <c:forEach var="eqptintroVO" items="${eqptintroSvc.all}" > 
          <option value="${eqptintroVO.ei_no}">${eqptintroVO.ei_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/EqptIntro/eqptintro.do" >
       <b>選擇裝備介紹標題:</b>
       <select size="1" name="ei_no">
         <c:forEach var="eqptintroVO" items="${eqptintroSvc.all}" > 
          <option value="${eqptintroVO.ei_no}">${eqptintroVO.ei_title}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>裝備介紹管理</h3>

<ul>
  <li><a href='<%=request.getContextPath()%>/back-end/eqptIntro/addEqptIntro.jsp'>Add</a> a new EqptIntro.</li>
</ul>
</div>
</div>
</body>
</html>