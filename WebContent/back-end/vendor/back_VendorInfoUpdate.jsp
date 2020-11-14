`<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.vendor.model.*"%>

<%
	VendorVO vendorVO = (VendorVO) session.getAttribute("vdVOupdate"); //VendorServlet.java(Concroller), 存入req的vdVOupdate物件
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<title>back_VendorInfoUpdate</title>
<style>
  #latlng {
    text-align: center;
  }
  .pretitle{
    display: inline-block;
    text-align: center;
  }
  #preview img {
    height: auto;
}
#myfile{
    display: none;
}
</style>
<!-- Custom fonts for this template -->
<link
  href="<%=request.getContextPath()%>/utility/back-end/vendor/fontawesome-free/css/all.min.css"
  rel="stylesheet" type="text/css">
<link
  href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
  rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/utility/back-end/css/sb-admin-2.min.css"
  rel="stylesheet">
<!-- Custom styles for this page -->
<link
  href="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/dataTables.bootstrap4.min.css"
  rel="stylesheet">
<!-- Vendor.css -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
</head>
<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">
    <!-- Sidebar -->
    <%@ include file="/back-end/index-sidebar.jsp"%>
    <!-- End of Sidebar -->
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <!-- Topbar -->
        <%@ include file="/back-end/index-header.jsp"%>
        <!-- End of Topbar -->
        <!-- Begin Page Content -->
        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row formargin">
                  <div class="col-12">

                    <div class="container col-6">

                    <%-- 錯誤表列 --%>
                    <!-- <c:if test="${not empty errorMsgs}">
                    	<font style="color:red">請修正以下錯誤:</font>
                    	<ul>
                    		<c:forEach var="message" items="${errorMsgs}">
                    			<li style="color:red">${message}</li>
                    		</c:forEach>
                    	</ul>
                    </c:if> -->

                    	<form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" 
                        enctype="multipart/form-data" novalidate>
                      		<h3>後台業者資訊修改</h3>
                      		<table class="table">
                    			<tr><th>業者編號</th><td><%=vendorVO.getVd_no()%></td></tr>
                    			<tr><th>信箱</th><td><%=vendorVO.getVd_email()%></td></tr>
                    			<!-- <tr><th>密碼</th><td><%=vendorVO.getVd_pwd()%></td></tr> -->
                    			<tr><th>負責人姓名</th><td><%=vendorVO.getVd_name()%></td></tr>
                    			<tr><th>身份證字號</th><td><%=vendorVO.getVd_id()%></td></tr>
                    			<tr><th>生日</th><td><%=vendorVO.getVd_birth()%></td></tr>
                    			<tr><th>負責人手機</th><td><%=vendorVO.getVd_mobile()%></td></tr>
                    			<tr><th>露營區名稱</th><td><%=vendorVO.getVd_cgname()%></td></tr>
                    			<tr><th>連絡電話</th><td><%=vendorVO.getVd_cgtel()%></td></tr>
                    			<tr><th>地址</th><td><%=vendorVO.getVd_cgaddr()%></td></tr>
                    			<tr><th>統一編號</th><td><%=vendorVO.getVd_taxid()%></td></tr>
                    			<tr><th>匯款帳戶資訊</th><td><%=vendorVO.getVd_acc()%></td></tr>
                    			<tr><th>註冊日期</th><td><%=vendorVO.getVd_regdate()%></td></tr>
                          <tr><th>帳號狀態</th><td><select name="vd_stat" id="vd_stat">
                                                  <option class="statSelect" value="0">停權</option>
                                                  <option class="statSelect" value="1">正常</option></select></td></tr>
                    			<tr><th>露營區狀態</th><td id="vd_cgstat"></td></tr>
                    		<!-- 	<tr><th>露營區緯度</th><td><input type="text" name="vd_lat" id="vd_lat" value="<%=vendorVO.getVd_lat()%>" readonly="true" /></td></tr>
                    			<tr><th>露營區經度</th><td><input type="text" name="vd_lon" id="vd_lon" value="<%=vendorVO.getVd_lon()%>" readonly="true"/></td></tr> -->
                          <tr><th>營業登記</th><td><input type="file" name="vd_brc" id="myfile" />
                                  <div class="pretitle">                                      
                                  <!-- Button trigger modal 圖片預覽按鈕-->
                                  <span id="smpreview" data-toggle="modal" data-target="#exampleModalCenter">
                                  <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=<%=vendorVO.getVd_no()%>" alt="">
                                  </span></div></td></tr>
                    		</table> 
                    		<div class="sub_btn">
                     			<input type="hidden" name="action" value="update">
                     			<input type="hidden" name="vd_no" value="<%=vendorVO.getVd_no()%>">
                     			<input type="hidden" name="vd_email" value="<%=vendorVO.getVd_email()%>">
                          <input type="hidden" name="vd_pwd" value="<%=vendorVO.getVd_pwd()%>">
                          <input type="hidden" name="vd_pwd2" value="<%=vendorVO.getVd_pwd()%>">
                          <input type="hidden" name="vd_name" value="<%=vendorVO.getVd_name()%>">
                          <input type="hidden" name="vd_id" value="<%=vendorVO.getVd_id()%>">
                          <input type="hidden" name="vd_birth" value="<%=vendorVO.getVd_birth()%>">
                          <input type="hidden" name="vd_mobile" value="<%=vendorVO.getVd_mobile()%>">
                          <input type="hidden" name="vd_cgname" value="<%=vendorVO.getVd_cgname()%>">
                          <input type="hidden" name="vd_cgtel" value="<%=vendorVO.getVd_cgtel()%>">
                          <input type="hidden" name="vd_cgaddr" value="<%=vendorVO.getVd_cgaddr()%>">
                          <input type="hidden" name="vd_taxid" value="<%=vendorVO.getVd_taxid()%>">
                          <input type="hidden" name="vd_acc" value="<%=vendorVO.getVd_acc()%>">
                     			<input type="hidden" name="vd_regdate" value="<%=vendorVO.getVd_regdate()%>">
                     			<input type="hidden" name="vd_stat" value="<%=vendorVO.getVd_stat()%>">
                          <input type="hidden" name="vd_cgstat" value="<%=vendorVO.getVd_cgstat()%>">
                          <input type="hidden" name="vd_lat" id="vd_lat" value="<%=vendorVO.getVd_lat()%>">
                          <input type="hidden" name="vd_lon" id="vd_lon" value="<%=vendorVO.getVd_lon()%>">
                          <input type="hidden" name="vd_lon" id="vd_lon" value="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=<%=vendorVO.getVd_no()%>">
                     			<input type="submit" name="送出修改" value="送出修改" class="btn btn-outline-secondary btn-sm">
                     			<input type="button" value="返回查詢頁面" onclick="location.href='<%= request.getContextPath()%>/back-end/vendor/serchVendorInfo.jsp'" class="btn btn-outline-secondary btn-sm">
                    		</div>
                    	</form>
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
                              <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=<%=vendorVO.getVd_no()%>" alt="">
                            </div>
                            <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary btn-sm" data-dismiss="modal">Close</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
        </div>
        <!-- /.container-fluid -->
      </div>
        <!-- End of Main Content -->
      <!-- Footer -->
    <%@ include file="/back-end/index-footer.jsp"%>
      <!-- End of Footer -->
    </div>
    <!-- End of Content Wrapper -->
  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top"
    style="display: none;"> <i class="fas fa-angle-up"></i>
  </a>

  <!-- Logout Modal-->
  <%@ include file="/back-end/logout.jsp" %>

  <script src="<%=request.getContextPath()%>/utility/back-end/vendor/jquery/jquery.min.js"></script>
  <script
    src="<%=request.getContextPath()%>/utility/back-end/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <!-- Core plugin JavaScript-->
  <script
    src="<%=request.getContextPath()%>/utility/back-end/vendor/jquery-easing/jquery.easing.min.js"></script>
  <!-- Custom scripts for all pages-->
  <script src="<%=request.getContextPath()%>/utility/back-end/js/sb-admin-2.min.js"></script>
  <!-- Page level plugins -->
  <script
    src="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/jquery.dataTables.js"></script>
  <script
    src="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/dataTables.bootstrap4.min.js"></script>
  <!-- Page level custom scripts -->
  <script src="<%=request.getContextPath()%>/utility/back-end/js/demo/datatables-demo.js"></script>
  <script>
    function init() {
    //顯示錯誤處理
    var errorMsgs = <%= request.getAttribute("errorMsgs")%>;
    // console.log(errorMsgs);
    $.each(errorMsgs,function(name, value){
        var name = name;
        var msg = value;
        console.log(Object.keys(errorMsgs).length);
        console.log($("input").length);
        for (var k = 0; k < $("input").length; k++){
          if ($("input:eq(" + k + ")").attr("name") === name){
              $("input:eq(" + k + ")").addClass("form-control is-invalid");
              $("input:eq(" + k + ")").after("<div class=\"errorMsgs invalid-feedback fas fa-campground fa-1g\">" + msg + "</div>");
            }
        }
      });

console.log('<%=vendorVO.getVd_stat()%>');
      //帳號狀態欄selected
      var stat = document.getElementsByClassName('statSelect');
      for (var i =0; i < stat.length; i++){
        if (stat[i].value === '<%=vendorVO.getVd_stat()%>'){
        stat[i].setAttribute('selected', 'true');
            }
      }

      $("#vd_cgstat").text(("<%=vendorVO.getVd_cgstat()%>" === 1)?'開啟':'關閉');

    }
    window.onload = init;

    //獲取經緯度
    var geocoder;

    function initMap() {
      //取得地址欄位資訊
      $("#vd_cgaddr").blur(function() {
          address = $("#vd_cgaddr").val();

          //透過 google.maps.Geocoder() 物件建構子建立一個geocoder 物件實例
          geocoder = new google.maps.Geocoder();
          //Google Maps API 依據地址轉換成經緯度
          //results：根據地址轉換成經緯度的單筆或多筆結果
          //status：執行結果的狀態訊息
          geocoder.geocode({ 'address': address }, function(results, status) {
              if (status == 'OK') {
                  // 若轉換成功...
                  console.log(results);

                  var lat = (results[0].geometry.location.lat().toString()).substring(0, 7);
                  var lng = (results[0].geometry.location.lng().toString()).substring(0, 8);

                  $("#vd_lat").val(lat);
                  $("#vd_lon").val(lng);
                  
                  if($("#vd_cgaddr+#latlng").siblings().length === 0){  //地址欄下方顯示經緯度
                    $("#vd_cgaddr").after("<div id=\"latlng\">(" + lat +", " + lng +")</div>");
                  }else{
                     $("#latlng").text("(" + lat +", " + lng +")");
                  }
                  

              } else {
                  // 若轉換失敗...
                  console.log(status)
              }
          });
      });
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0yuHIUOGU1dx1CO_o1gOYuN6AkNlGSbQ&libraries=places&callback=initMap" async defer></script>
</body>
</html>
