<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>

<%
	VendorVO vendorVO = (VendorVO) session.getAttribute("vdVOupdate"); //VendorServlet.java (Concroller) 存入session的vdVOupdate物件
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<title>VendorInfoUpdate</title>
</head>
<style>
  .formargin {
    min-height:calc(90vh - 100px);
    margin-top: 1rem;
  }
  #latlng{
  text-align: left;
  color: #6a6f77;
  }
  .pretitle{
  display: inline-block;
  }
  /* #Picbox{
    height: 700px;
  }
  #vdBrcPic{
    max-width: 100%;
    max-height: 100%;
    border:1px solid #a6a6a6;
    border-radius: 4px;
    display: block;
    margin: 15% auto;
  } */
</style>
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
          <div class="container formargin">

            <%-- 錯誤表列 --%>
            <!-- <c:if test="${not empty errorMsgs}">
              <font style="color:red"><i class="fas fa-campground fa-1g"></i>請修正以下錯誤:</font>
                <c:forEach var="message" items="${errorMsgs}">
                  <div style="color:red"><i class="fas fa-map-signs fa-1g"></i>${message}</div>
                </c:forEach>
            </c:if> -->

          	<form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" 
                enctype="multipart/form-data" novalidate>
          	<h3>業者資訊修改</h3>
          		<table class="table">
          			<!-- <tr><th>業者編號</th><td><%=vendorVO.getVd_no()%></td></tr> -->
          			<tr><th>信箱</th><td><%=vendorVO.getVd_email()%></td></tr>
          			<tr><th>密碼</th><td><input type="password" name="vd_pwd" id="vd_pwd" class="form-control" /></td></tr>
                <tr><th>確認密碼</th><td><input type="password" name="vd_pwd2" id="vd_pwd2" class="form-control" /></td></tr>
          			<tr><th>負責人姓名</th><td><input type="text" name="vd_name" id="vd_name" class="form-control" value="<%=vendorVO.getVd_name()%>" /></td></tr>
          			<tr><th>身份證字號</th><td><input type="text" name="vd_id" id="vd_id" class="form-control" value="<%=vendorVO.getVd_id()%>" /></td></tr>
          			<tr><th>生日</th><td><input type="text" name="vd_birth" id="vd_birth" class="form-control" value="<%=vendorVO.getVd_birth()%>" /></td></tr>
          			<tr><th>負責人手機</th><td><input type="text" name="vd_mobile" id="vd_mobile" class="form-control" value="<%=vendorVO.getVd_mobile()%>" /></td></tr>
          			<tr><th>露營區名稱</th><td><input type="text" name="vd_cgname" id="vd_cgname" class="form-control" value="<%=vendorVO.getVd_cgname()%>" /></td></tr>
          			<tr><th>連絡電話</th><td><input type="text" name="vd_cgtel" id="vd_cgtel" class="form-control" value="<%=vendorVO.getVd_cgtel()%>" /></td></tr>
          			<tr><th>地址</th><td><div class="form-row" name="vd_cgaddr" id="vd_cgaddrlatlon">
                        <div class="col-2">
                            <select class="form-control" id="counties"></select>
                        </div>
                        <div class="col">
                          <input type="text" name="vd_cgaddrselct" class="form-control simpleaddress">
                        </div>
                        </div></td></tr>
          			<tr><th>統一編號</th><td><input type="text" name="vd_taxid" id="vd_taxid" class="form-control" value="<%=vendorVO.getVd_taxid()%>" /></td></tr>
          			<tr><th>匯款帳戶資訊</th><td><input type="text" name="vd_acc" id="vd_acc" class="form-control" value="<%=vendorVO.getVd_acc()%>" /></td></tr>
          			<tr><th>註冊日期</th><td><%=vendorVO.getVd_regdate()%></td></tr>
          			<!-- <tr><th>帳號狀態</th><td><%=vendorVO.getVd_stat()%></td></tr> -->
          			<!-- <tr><th>露營區狀態</th><td><input type="text" name="vd_cgstat" id="vd_cgstat" class="form-control" value="<%=vendorVO.getVd_cgstat()%>" /></td></tr> -->
                <tr><th>露營區狀態</th><td><select name="vd_cgstat" id="vd_cgstat" class="form-control">
                                            <option class="cgstatSelect" value="0">關閉</option>
                                            <option class="cgstatSelect" value="1">開啟</option></select></td></tr>
                <tr><th>營業登記</th><td><div class="custom-file">
                                            <input type="file" name="vd_brc" id="myfile" class="custom-file-input form-control-file" />
                                            <label class="custom-file-label">上傳圖片</label>
                                        </div>
                                        <div class="pretitle">                                      
                                        <!-- Button trigger modal 圖片預覽按鈕-->
                                        <span id="smpreview" data-toggle="modal" data-target="#exampleModalCenter">
                                        <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=${vendorVO.vd_no}" alt="">
                                        </span></div></td></tr>
          		</table> 
          		<div class="sub_btn">
          			<input type="hidden" name="action" value="update">
          			<input type="hidden" name="vd_no" value="<%=vendorVO.getVd_no()%>">
          			<input type="hidden" name="vd_email" value="<%=vendorVO.getVd_email()%>">
                <input type="hidden" name="vd_cgaddr" id="vd_cgaddr" value="<%=vendorVO.getVd_cgaddr()%>">
          			<input type="hidden" name="vd_regdate" value="<%=vendorVO.getVd_regdate()%>">
          			<input type="hidden" name="vd_stat" value="<%=vendorVO.getVd_stat()%>">
                <input type="hidden" name="vd_lat" id="vd_lat" value="<%=vendorVO.getVd_lat()%>">
                <input type="hidden" name="vd_lon" id="vd_lon" value="<%=vendorVO.getVd_lon()%>">
          			<input type="submit" name="送出修改" value="送出修改" class="btn btn-outline-secondary btn-sm">
          			<input type="button" value="返回業者資訊" onclick="location.href='<%= request.getContextPath()%>/front-end/vendor/VendorInfo.jsp'" class="btn btn-outline-secondary btn-sm">
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
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-outline-secondary btn-sm" data-dismiss="modal">Close</button>
                    </div>
                  </div>
                </div>
              </div>

          </div>
          <!-- <div id="Picbox" class="col-4">
                  <img src="<%= request.getContextPath()%>/vendor/VdpicServlet?vd_no=${vendorVO.vd_no}" id="vdBrcPic" alt="">
          </div> -->
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
<script src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
<script src="<%= request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<%@ include file="/front-end/index/Notice.jsp"%>
<script type="text/javascript">
  function init() {
    //顯示錯誤處理
    var errorMsgs = <%= request.getAttribute("errorMsgs")%>;
    // console.log(errorMsgs);

    $.each(errorMsgs,function(name, value){
      var name = name;
      var msg = value;
      for (var k = 0; k < $("form input").length; k++){ //form表單內input的長度
            if($("form").find("input:eq(" + k + ")").attr("name") === name && 
            $("form").find("input:eq(" + k + ")").attr("name") !== "mem_gender" &&
            $("form").find("input:eq(" + k + ")").attr("name") !== "vd_lat" &&
            $("form").find("input:eq(" + k + ")").attr("name") !== "vd_lon"){  //取得input的名字(先排除為radio的mem_gender以及經緯度)
            $("form").find("input:eq(" + k + ")").addClass("form-control is-invalid");
            $("form").find("input:eq(" + k + ")").after("<div class=\"errorMsgs invalid-feedback fas fa-campground fa-1g\">" + msg + "</div>");
          }
        }
      if(name === "mem_gender"){  //另外處理為radio的mem_gender
        $("form :radio:eq(1)").addClass("is-invalid");
        $(":radio:eq(1)").parent().parent().after("<div class=\"errorMsgs invalid-feedback fas fa-venus-mars fa-1g\">" + msg + "</div>");
        }
    });

        //地址縣市下拉式選單
    var city = ["基隆市", "臺北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
                "臺中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "臺南市",
                "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", 
                "連江縣"];
    for (var i = 0; i < city.length; i++){
      $("#counties").append(`<option class="counties" value="` + city[i] + `">` + city[i] + `</option>`);
    }

    //業者錯誤回報地址
    var returnaddress = "${vendorVO.vd_cgaddr}";
    $("#counties").val((returnaddress).substring(0, 3));
    $(".simpleaddress").val((returnaddress).substring(3));


    //前端日期卡關
    $.datetimepicker.setLocale('zh');
      $('#mem_birth, #vd_birth').datetimepicker({
        theme: '',              //theme: 'dark',
        timepicker:false,       //timepicker:true,
        step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
        format:'Y-m-d',         //format:'Y-m-d H:i:s',
        value: "<%=vendorVO.getVd_birth()%>",
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:             '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });

    var somedate2 = new Date(Date.now());
      $('#mem_birth, #vd_birth').datetimepicker({
          beforeShowDay: function(date) {
            if ( date.getYear() >  somedate2.getYear() || 
              (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
              (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
             ) {
                  return [false, ""]
             }
             return [true, ""];
      }});

      //營區狀態欄selected
      var stat = document.getElementsByClassName('cgstatSelect');
      for (var i =0; i < stat.length; i++){
        if (stat[i].value === '<%=vendorVO.getVd_cgstat()%>'){
        stat[i].setAttribute('selected', 'true');
            }
      }
      
      //圖片上傳
      var myfile = document.getElementById("myfile");
      var preview = document.getElementById("preview");
      var smpreview = document.getElementById("smpreview");
      var img = document.getElementsByTagName('img');
      console.log(smpreview.children.length);
      console.log(preview.children.length);
          myfile.addEventListener('change', function() {
              var files = myfile.files;
              if (preview.children.length > 0) {
                preview.children[0].remove();
              }
              if (smpreview.children.length >= 1) {
                smpreview.children[0].remove();
              }

              if (files !== null) {
                  var file = files[0];
                      if (file.type.indexOf('image') > -1) {
                          var reader = new FileReader();
                          reader.addEventListener('load', function(e) {
                              var img = document.createElement('img'); //創建圖片
                              img.setAttribute('src', e.target.result);
                              preview.append(img);
                              var img = document.createElement('img'); //創建小圖片
                              img.setAttribute('src', e.target.result);
                              smpreview.append(img);
                          });
                          reader.readAsDataURL(file);
                      } else {
                          alert("請上傳圖片");
                  }               
              }
          });

        //顯示圖片預覽按鈕
        $("#myfile").change(function(){
          if($("#myfile").val() !== ""){
            $(".pretitle").css("display", "inline-block")
          }
        });
    }
    window.onload = init;

    //獲取經緯度
    var geocoder;

    function initMap() {
      //取得地址欄位資訊
      $(".simpleaddress").blur(function() {
        var countyname = $("#counties").val();
        var simpleaddress = $(".simpleaddress").val();
        var detailaddress = countyname + simpleaddress;
        $("#vd_cgaddr").val(detailaddress);
        var address = $("#vd_cgaddr").val();

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
                  
                  if($("#vd_cgaddrlatlon+#latlng").siblings().length === 0){  //地址欄下方顯示經緯度
                    $("#vd_cgaddrlatlon").after("<div id=\"latlng\">(" + lat +", " + lng +")</div>");
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