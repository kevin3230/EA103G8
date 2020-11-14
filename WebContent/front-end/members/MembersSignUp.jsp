<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>

<%	
	MembersVO memVO = (MembersVO) request.getAttribute("memVO"); //MembersServlet.java (Concroller) 存入req的memVO物件 (包括幫忙取出的memVO, 也包括輸入資料錯誤時的memVO物件)
%>

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
<title>MembersSignUp</title>
</head>
<style>
  .formargin {
    margin-top: 3%;
    min-height:calc(90vh - 200px);
  }
</style>    
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

  <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">
    <h3>會員註冊</h3>
      <table class="table">
        <tr><th>註冊信箱</th><td><input type="text" name="mem_email" id="mem_email" value="${memVO.mem_email}"></td></tr>
        <tr><th>註冊密碼</th><td><input type="password" name="mem_pwd"  id="mem_pwd"></td></tr>
        <tr><th>姓名</th><td><input type="text" name="mem_name" id="mem_name" value="${memVO.mem_name}"></td></tr>
        <tr><th>暱稱</th><td><input type="text" name="mem_alias" id="mem_alias" value="${memVO.mem_alias}"></td></tr>  
        <tr><th>性別</th><td>
        <label><input type="radio" name="mem_gender" value="男" class="gender">男</label>
        <label><input type="radio" name="mem_gender" value="女" class="gender" id="mem_gender">女</label>
        <label><input type="radio" name="mem_gender" value="無" class="gender">無</label>
        </td></tr>
        <tr><th>生日</th><td><input type="date" name="mem_birth" id="mem_birth" value="${memVO.mem_birth}"></td></tr>
        <tr><th>手機</th><td><input type="text" name="mem_mobile" id="mem_mobile" value="${memVO.mem_mobile}" class="AA"></td></tr>
        <tr><th>市話</th><td><input type="text" name="mem_tel" id="mem_tel" value="${memVO.mem_tel}"></td></tr>
        <tr><th>住址</th><td><input type="text" name="mem_addr" id="mem_addr" value="${memVO.mem_addr}"></td></tr>
      </table>
       <input type="hidden" name="action" value="memSignUpSubmit">
       <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
    </form>   
</div>
<button type="button" id="magicalbtn" class="btn btn-outline-danger btn-sm">神奇小按鈕</button>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

  <script>
    //性別欄checked
    var gender = document.getElementsByClassName('gender');
    for (var i =0; i < gender.length; i++){
      if (gender[i].value === '${memVO.mem_gender}'){
        gender[i].setAttribute('checked', 'true');
      }
    }

    //神奇小按鈕
    $("#magicalbtn").click(function(){
        $("#mem_email").val("niconiconi@niconi.com");
        $("#mem_pwd").val("123")
        $("#mem_name").val("沈燕撫");
        $("#mem_alias").val("大和撫子");
        $("#mem_gender").attr("checked", "true");
        $("#mem_birth").val("2002-10-10");
        $("#mem_mobile").val("0912345678");
        $("#mem_tel").val("0246813579");
        $("#mem_addr").val("宜蘭縣頭城鎮龜山島401號")
      }
    );

    //例外處理alert
    $(document).ready(function(){
      if ("${not empty errorMsgs}" === 'true'){
        console.log("${not empty errorMsgs}");
        window.alert(`<c:forEach var="message" items="${errorMsgs}">
                    ${message}
                    </c:forEach>`);
        }
      });
  </script>
</body>
</html>