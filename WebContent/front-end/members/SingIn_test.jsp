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
<title>SignIn_test</title>
<style type="text/css" media="screen">
html, body {
    margin: 0px;
    padding: 0px;
    height: 100vh;
    background: linear-gradient(350deg, #f2f2f2 70%, transparent 95%), url(../index/images/9index_c4_main2.jpg);
    background: -webkit-linear-gradient(350deg, #f2f2f2 70%, transparent 95%), url(../index/images/index_c4_main2.jpg);
    background-repeat: no-repeat;
    background-position: right;
    background-attachment: fixed;
    background-size: contain;
}
.formargin {
    margin-top: 3%;
    min-height:calc(90vh - 100px);
}
.toSignUp{
    color: #00ffff;
    font-size: 15px;
    text-shadow: 0.1em 0.1em 0.5em #000,-0.1em -0.1em 0.5em #000;
    margin: 2% 0 2% -5%;
    display: block;
}
.toSignIn{   
    color: #ffd700;
    font-size: 15px;
    text-shadow: 0.1em 0.1em 0.5em #000,-0.1em -0.1em 0.5em #000;
    margin: 2% 0 2% -5%;
    display: none;
}
.selectViewbox{
    width: 100%;
    height: 35px;
    overflow: hidden;
}
.memSelectbox, .vdSelectbox{
    text-align: center;
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
}
.viewbox{
    margin: 10px auto 0 auto;
    width: 100%;
    height: 300px;
    overflow: hidden;
    transition: all 1.0s;
}
.signInviewbox{
    width: 100%;
    height: 600px;
    overflow: hidden;
    white-space: nowrap;
    transition: all 1.0s;
}
.signUpviewbox{
    width: 100%;
    height: 850px;
    overflow: hidden;
    white-space: nowrap;
    transition: all 1.0s;
}
.memSelect, .vdSelect {
    margin: 0 4px 0 4px;
    display: inline;
    transition: all 0.7s;
}
.vdSelect{
    color: #a6a6a6;
}
.memContainer, .vdContainer {
    overflow: hidden;
    display: inline;
}
.memContainer{
    margin: 30px 10% 30px 5%;
    transition: all 1.0s;
    vertical-align: top;
}
.vdContainer{
    margin: 30px 5% 30px 10%;
    transition: all 1.0s;
    vertical-align: top;
}
span form {
    width: 85%;
    display: inline-block;
}
#line{
    width: 1px;
    height: 11px;
    padding-top: 3px;
    border: 1px solid #444;
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
    <div class="toSignUp col-6">還沒註冊?立即點擊成為白金之星會員</div>
    <div class="toSignIn col-6">已經是白金之星喔拉!</div>

    <div  class="selectViewbox">
        <div class="memSelectbox"><h3 class="memSelect">會員登入</h3><span id="line"></span><h3 class="vdSelect">業者登入</h3></div>
        <div class="vdSelectbox"><h3 class="memSelect">會員註冊</h3><span id="line"></span><h3 class="vdSelect">業者註冊</h3></div>     
    </div>

    <div class="viewbox">
    <div class="signInviewbox">
        <span class="memContainer">
         	<form action="<%=request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">    
                <table class="table">
                <tr><th>會員信箱</th><td><input type="text" name="mem_email" id="mem_name"></td></tr>
                <tr><th>會員密碼</th><td><input type="password" name="mem_pwd"></td></tr>
                </table>
                <div>
                <input type="hidden" name="action" value="memSignInSubmit">
                <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
                </div>
            </form>
        </span>
        <span class="vdContainer">
            <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8">    
                <table class="table">
                <tr><th>業者信箱</th><td><input type="text" name="vd_email"></td></tr>
                <tr><th>業者密碼</th><td><input type="password" name="vd_pwd"></td></tr>
                </table>
                <div>
                <input type="hidden" name="action" value="vdSignInSubmit">
                <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
                </div>
            </form>
        </span>          
    </div>
    
    <div class="signUpviewbox">
        <span class="memContainer">
            <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8"> 
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
                <div>
                <input type="hidden" name="action" value="memSignUpSubmit">
                <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
                </div>
            </form>   
        </span>
        
        <span class="vdContainer">
            <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" 
              enctype="multipart/form-data">
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
                <div>
                <input type="hidden" name="action" value="vdSignUpSubmit">
                <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm">
                </div>
            </form>
        </span>
    </div>
    </div>
</div>
<button type="button" id="memMagicalbtn" class="btn btn-outline-danger btn-sm">會員神奇小按鈕</button>
<button type="button" id="vdMagicalbtn" class="btn btn-outline-danger btn-sm">業者神奇小按鈕</button>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<script>
    function init() {
    $(".toSignUp").click(function(){
        $(".toSignIn").css("display", "block");
        $(".toSignUp,.memSelectbox").css("display", "none");
        $(".signInviewbox").css("marginTop", "-600px");
        $(".viewbox").css("height", "850px");
    });

    $(".toSignIn").click(function(){
        $(".toSignIn").css("display", "none");
        $(".toSignUp,.memSelectbox").css("display", "block");
        $(".signInviewbox").css("marginTop", "0px");
        $(".viewbox").css("height", "300px");
    });

    $(".vdSelect").click(function(){
        $(".memContainer").css("marginLeft", "-100%");
        $(".memSelect").css("color", "#a6a6a6");
        $(".vdSelect").css("color", "#444");
        // 
    });
    $(".memSelect").click(function(){
        $(".memContainer").css("marginLeft", "5%");
        $(".vdSelect").css("color", "#a6a6a6");
        $(".memSelect").css("color", "#444");
        // 
    });

    //members
    //性別欄checked
    var gender = document.getElementsByClassName('gender');
    for (var i =0; i < gender.length; i++){
      if (gender[i].value === '${memVO.mem_gender}'){
        gender[i].setAttribute('checked', 'true');
      }
    }

    //vendor
    //神奇小按鈕
    $("#memMagicalbtn").click(function(){
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

    //圖片上傳
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
        
    //神奇小按鈕
    $("#vdMagicalbtn").click(function(){
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
    window.onload = init;
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0yuHIUOGU1dx1CO_o1gOYuN6AkNlGSbQ&libraries=places&callback=initMap" async defer></script>
</body>
</html>