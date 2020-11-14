<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Adminis: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Adminis: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Adminis: Home</p>

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
  <li><a href='listAllAdminis.jsp'>List</a> all Adminiss.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="adminis.do" >
        <b>輸入員工編號 (如A000000001):</b>
        <input type="text" name="adminis_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="adminisSvc" scope="page" class="com.adminis.model.AdminisService" />
   
  <li>
     <FORM METHOD="post" ACTION="adminis.do" >
       <b>選擇員工編號:</b>
       <select size="1" name="adminis_no">
         <c:forEach var="adminisVO" items="${adminisSvc.all}" > 
          <option value="${adminisVO.adminis_no}">${adminisVO.adminis_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="adminis.do" >
       <b>選擇員工姓名:</b>
       <select size="1" name="adminis_no">
         <c:forEach var="adminisVO" items="${adminisSvc.all}" > 
          <option value="${adminisVO.adminis_no}">${adminisVO.adminis_name}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>


<h3>員工管理</h3>

<ul>
  <li><a href='addAdminis.jsp'>Add</a> a new Adminis.</li>
</ul>

</body>
</html>