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
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/members/assets/Members.css">
<title>MembersInfo</title>
<style>
  .formargin {
    min-height:calc(90vh - 100px);
    margin-top: 1rem;
  }
</style>
</head>
<body>
  <!-- Header開始 -->
  <%@ include file="/front-end/index/header.jsp"%> 
  <!-- Header結束 -->
  
  <div class="container-fluid">
    <div class="row">
    <!-- sidebar放這邊 ，col設定為2 -->    
    <%@ include file="/front-end/sidebar/memberSidebar.jsp" %>
    <!-- sidebar放這邊 ，col設定為2 -->
  
      <div class="col-10">
      
        <!-- 以下內容為範例，請自行發揮 -->      
        <div class="row justify-content-center text-center">
          <div class="container col-6 formargin">
        	 <h3>會員資訊</h3>
        	 <table class="table">
      		    <!-- <tr><th>會員編號</th><td>${memVO.mem_no}</td></tr> -->
      	   	  <tr><th>信箱</th><td>${memVO.mem_email}</td></tr>
      		    <!-- <tr><th>密碼</th><td>${memVO.mem_pwd}</td></tr> -->
          		<tr><th>姓名</th><td>${memVO.mem_name}</td></tr>
      		    <tr><th>暱稱</th><td>${memVO.mem_alias}</td></tr>
      		    <tr><th>性別</th><td>${memVO.mem_gender}</td></tr>
      		    <tr><th>生日</th><td>${memVO.mem_birth}</td></tr>
      	    	<tr><th>手機號碼</th><td>${memVO.mem_mobile}</td></tr>
      	     	<tr><th>市話</th><td>${memVO.mem_tel}</td></tr>
      	    	<tr><th>地址</th><td>${memVO.mem_addr}</td></tr>
      	    	<!-- <tr><th>會員類型</th><td>${memVO.mem_type}</td></tr> -->
      	    	<tr><th>註冊日期</th><td>${memVO.mem_regdate}</td></tr>
      	    	<!-- <tr><th>帳號狀態</th><td>${memVO.mem_stat}</td></tr> -->
          	</table>
       	    <div class="sub_btn">
          		<form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">
        	 		   <input type="hidden" name="action" value="memUpdateSubmit">
        	 		   <input type="hidden" name="mem_no" value="${memVO.mem_no}">
        	 		   <input type="submit" name="修改資料" value="修改資料" class="btn btn-outline-secondary btn-sm">
           		   <input type="button" value="返回首頁" onclick="location.href='<%= request.getContextPath()%>/front-end/index/index.jsp'" class="btn btn-outline-secondary btn-sm">
          		</form>
          	</div>
          </div>
        </div>
        <!-- 以上內容為範例，請自行發揮 -->       
      </div>
    </div>
  </div>

  <!-- Footer開始 --> 
  <%@ include file="/front-end/index/footer.jsp"%>
  <!-- Footer結束 -->

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/notice.js"></script>
<script src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
<%@ include file="/front-end/index/Notice.jsp"%>
</body>
</html>