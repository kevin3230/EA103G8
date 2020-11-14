<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>

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
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<title>VendorInfo</title>
<style>
  .formargin {
    min-height:calc(90vh - 100px);
    margin-top: 1rem;
  }
  #latlng {
    text-align: center;
  }
  .pretitle{
    display: inline-block;
    text-align: center;
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
    <%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
    <!-- sidebar放這邊 ，col設定為2 -->
  
      <div class="col-10">
      
        <!-- 以下內容為範例，請自行發揮 -->      
        <div class="row justify-content-center text-center">

          <div class="container col-6 formargin">
          	<h3>業者資訊</h3>
          	<table class="table">
          		<!-- <tr><th>業者編號</th><td>${vendorVO.vd_no}</td></tr> -->
          		<tr><th>信箱</th><td>${vendorVO.vd_email}</td></tr>
          		<!-- <tr><th>密碼</th><td>${vendorVO.vd_pwd}</td></tr> -->
          		<tr><th>負責人姓名</th><td>${vendorVO.vd_name}</td></tr>
          		<tr><th>身份證字號</th><td>${vendorVO.vd_id}</td></tr>
          		<tr><th>生日</th><td>${vendorVO.vd_birth}</td></tr>
          		<tr><th>負責人手機</th><td>${vendorVO.vd_mobile}</td></tr>
          		<tr><th>露營區名稱</th><td>${vendorVO.vd_cgname}</td></tr>
          		<tr><th>連絡電話</th><td>${vendorVO.vd_cgtel}</td></tr>
          		<tr><th>地址</th><td>${vendorVO.vd_cgaddr}
                          <div id="latlng">( ${vendorVO.vd_lat}, ${vendorVO.vd_lon})</div></td></tr>
          		<tr><th>統一編號</th><td>${vendorVO.vd_taxid}</td></tr>
          		<tr><th>匯款帳戶資訊</th><td>${vendorVO.vd_acc}</td></tr>
          		<tr><th>註冊日期</th><td>${vendorVO.vd_regdate}</td></tr>
          		<!-- <tr><th>帳號狀態</th><td>${vendorVO.vd_stat}</td></tr> -->
          		<tr><th>露營區狀態</th><td id="vd_cgstat"></td></tr>
          		<!-- <tr><th>露營區緯度</th><td>${vendorVO.vd_lat}</td></tr>
          		<tr><th>露營區經度</th><td>${vendorVO.vd_lon}</td></tr> -->
              <tr><th>營業登記</th><td><div class="pretitle">                                      
                                      <!-- Button trigger modal 圖片預覽按鈕-->
                                      <span id="smpreview" data-toggle="modal" data-target="#exampleModalCenter">
                                      <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=${vendorVO.vd_no}" alt="">
                                      </span></div></td></tr>
          	</table>
          	<div class="sub_btn">
          		<form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8">
          			<input type="hidden" name="action" value="vdUpdateSubmit">
          			<input type="hidden" name="vd_no" value="${vendorVO.vd_no}">
          			<input type="submit" name="修改資料" value="修改資料" class="btn btn-outline-secondary btn-sm">
          			<input type="button" value="返回首頁" onclick="location.href='<%= request.getContextPath()%>/front-end/index/index.jsp'" class="btn btn-outline-secondary btn-sm">
          		</form>
          	</div>

            <!-- Modal 圖片預覽-->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h6 class="modal-title" id="exampleModalCenterTitle">My Business Registration Certificate</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body" id="preview">
                    <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=${vendorVO.vd_no}" alt="">
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
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
<script>
  $("#vd_cgstat").text((${vendorVO.vd_cgstat} === 1)?'開啟':'關閉');
</script>
</body>
</html>