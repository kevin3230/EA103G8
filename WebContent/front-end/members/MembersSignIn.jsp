<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/members/assets/Members.css">
<title>MembersSignIn</title>
<style type="text/css" media="screen">
.formargin {
    margin-top: 10%;
    min-height:calc(90vh - 200px);
}
</style>
</head>
<body>

<%@ include file="/front-end/index/header.jsp" %>

<div class="container formargin">

    <%-- 錯誤表列 --%>
    <c:if test="${not empty errorMsgs}">
      <font style="color:red"><i class="fas fa-campground fa-1g"></i>請修正以下錯誤:</font>
        <c:forEach var="message" items="${errorMsgs}">
          <div style="color:red"><i class="fas fa-map-signs fa-1g"></i>${message}</div>
        </c:forEach>
    </c:if>

 	<form action="<%=request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">
        <h3>會員登入</h3>
        <table class="table">
        <tr><th>會員信箱</th><td><input type="text" name="mem_email"></td></tr>
        <tr><th>會員密碼</th><td><input type="password" name="mem_pwd"></td></tr>
        </table>
        <input type="hidden" name="action" value="memSignInSubmit">
        <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
    </form>
</div>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
</body>
</html>