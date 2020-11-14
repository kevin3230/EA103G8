<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>

<%	
	VendorVO vendorVO = (VendorVO) request.getAttribute("vendorVO"); //VendorServlet.java (Concroller) 存入req的vendorVO物件 (包括幫忙取出的vendorVO, 也包括輸入資料錯誤時的vendorVO物件)
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
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<title>VendorSignUp</title>
</head>
<style>
  .formargin {
    margin-top: 1%;
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

  <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" 
  enctype="multipart/form-data">
    <h3>業者註冊</h3>
       <table class="table">
        <tr><th>註冊信箱</th><td><input type="text" name="vd_email" id="vd_email" value="${vendorVO.vd_email}"></td></tr>
        <tr><th>註冊密碼</th><td><input type="password" name="vd_pwd" id="vd_pwd"></td></tr>
        <tr><th>負責人姓名</th><td><input type="text" name="vd_name" id="vd_name" value="${vendorVO.vd_name}"></td></tr>
        <tr><th>身份證字號</th><td><input type="text" name="vd_id" id="vd_id" value="${vendorVO.vd_id}"></td></tr>
        <tr><th>生日</th><td><input type="date" name="vd_birth" id="vd_birth" value="${vendorVO.vd_birth}"></td></tr>       
        <tr><th>負責人手機</th><td><input type="text" name="vd_mobile" id="vd_mobile" value="${vendorVO.vd_mobile}"></td></tr>
        <tr><th>露營區名稱</th><td><input type="text" name="vd_cgname" id="vd_cgname" value="${vendorVO.vd_cgname}"></td></tr>
        <tr><th>連絡電話</th><td><input type="text" name="vd_cgtel" id="vd_cgtel" value="${vendorVO.vd_cgtel}"></td></tr>
        <tr><th>地址</th><td><input type="text" name="vd_cgaddr" id="vd_cgaddr" value="${vendorVO.vd_cgaddr}"></td></tr>        
        <tr><th>統一編號</th><td><input type="text" name="vd_taxid" id="vd_taxid" value="${vendorVO.vd_taxid}"></td></tr>
        <tr><th>匯款帳戶資訊</th><td><input type="text" name="vd_acc" id="vd_acc" value="${vendorVO.vd_acc}"></td></tr>
        <tr><th>露營區緯度</th><td><input type="text" name="vd_lat" id="vd_lat" readonly="true"></td></tr>
        <tr><th>露營區經度</th><td><input type="text" name="vd_lon" id="vd_lon" readonly="true"></td></tr>
        <tr><th>營業登記</th><td><input type="file" name="vd_brc" id="myfile">
                                <div class="pretitle"><label>圖片預覽: </label>
                                <div id="preview"></div></div></td></tr>
       </table>
       <input type="hidden" name="action" value="vdSignUpSubmit">
       <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
   </form>
</div>
<button type="button" id="magicalbtn" class="btn btn-outline-danger btn-sm">神奇小按鈕</button>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

  <script type="text/javascript">
    //圖片上傳
    function init() {
      var myfile = document.getElementById("myfile");
      var preview = document.getElementById("preview");
      var img = document.getElementsByTagName('img');

          myfile.addEventListener('change', function() {
              var files = myfile.files;
              if (img.length > 0) {
                img[0].remove();
              }
              if (files !== null) {
                  var file = files[0];
                      if (file.type.indexOf('image') > -1) {
                          var reader = new FileReader();
                          reader.addEventListener('load', function(e) {
                              var img = document.createElement('img'); //創建圖片
                              img.setAttribute('src', e.target.result);
                              preview.append(img);
                          });
                          reader.readAsDataURL(file);
                      } else {
                          alert("請上傳圖片");
                  }
                  
              }
          });
      }
    window.onload = init;
        
    //神奇小按鈕
    $("#magicalbtn").click(function(){
      $("#vd_email").val("C8763@SAO.com");
      $("#vd_pwd").val("123")
      $("#vd_name").val("銅鼓核仁");
      $("#vd_id").val("A123456789");
      $("#vd_birth").val("2008-10-07");
      $("#vd_mobile").val("0987654321");
      $("#vd_cgname").val("Under_World");
      $("#vd_cgtel").val("0357924680");
      $("#vd_cgaddr").val("高雄市茂林區多納里大鬼湖1號");
      $("#vd_taxid").val("86437531");
      $("#vd_acc").val("70031415926535")
      }
    );

    //獲取經緯度
    var geocoder;

    function initMap() {
      //取得地址欄位資訊
      $("#vd_cgaddr").blur(function() {
          console.log($("#vd_cgaddr").val());
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