<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.vendor.model.*"%>

<%  
  MembersVO memVO = (MembersVO) request.getAttribute("memVO"); //MembersServlet.java (Concroller) 存入req的memVO物件 (包括幫忙取出的memVO, 也包括輸入資料錯誤時的memVO物件)
%>
<%  
  VendorVO vendorVO = (VendorVO) request.getAttribute("vendorVO"); //VendorServlet.java (Concroller) 存入req的vendorVO物件 (包括幫忙取出的vendorVO, 也包括輸入資料錯誤時的vendorVO物件)
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/members/assets/Members.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<title>MemVdSignInSignUp</title>
<style type="text/css" media="screen">
html, body {
    margin: 0px;
    padding: 0px;
    height: 100vh;
    /* background: linear-gradient(350deg, #f2f2f2 70%, transparent 95%), url("<%= request.getContextPath()%>/front-end/indeximages/9index_c4_main2.jpg");
    background: -webkit-linear-gradient(350deg, #f2f2f2 70%, transparent 95%), url("<%= request.getContextPath()%>/front-end/index/images/index_c4_main2.jpg");
    background-repeat: no-repeat;
    background-position: right;
    background-attachment: fixed;
    background-size: contain; */
    background-color: #f2f2f2;
}
.formargin {
    margin-top: 3%;
    min-height:calc(90vh - 100px);
}
.toSignUp{
    color: #00ffff;
    font-size: 15px;
    text-shadow: 0.1em 0.1em 0.5em #000,-0.1em -0.1em 0.5em #000;
    /* margin: 2% 0 2% -5%; */
    display: inline-block;
}
.toSignIn{   
    color: #ffd700;
    font-size: 15px;
    text-shadow: 0.1em 0.1em 0.5em #000,-0.1em -0.1em 0.5em #000;
    /* margin: 2% 0 2% -5%; */
    display: none;
}
.toSignUp:hover, .toSignIn:hover{
    cursor: pointer;
    text-decoration: underline;
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
    margin: 10px auto 10px auto;
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
    /* height: 1150px; */
    height: 100%;
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
#getPassword{
    display: inline-block;
}
.signIninorup{
    position: absolute;
    right: 0;
    margin-right:15px; 
}
.signUpinorup{
    position: relative;
   /*  margin-right:-530px;  */
   float: right;
}
.pwdSignbox{
    position: relative;
}
#alertmsg{
    height: 120px;
    width: 250px;
    position: fixed;
    top:50%;
    left:50%; 
    margin-left:-125px;
    margin-top:-125px;
    display: none;
    border: 1px solid #444;
    border-radius: 4px;
    background-color: #c8cccf;
    opacity: 0.9;
    text-align: center;
    line-height: 120px;
}
#alertmsg:hover{
    filter: brightness(1.1);
}
</style>
</head>
<body>

<%@ include file="/front-end/index/header.jsp" %>

<div class="container formargin">

    <%-- 錯誤表列 --%>
    <!-- <c:if test="${not empty errorMsgs}">
      <font style="color:red"><i class="fas fa-campground fa-1g"></i>請修正以下錯誤:</font>
        <c:forEach var="message" items="${errorMsgs}">
          <div style="color:red"><i class="fas fa-map-signs fa-1g"></i>${message}</div>
        </c:forEach>
    </c:if> -->

    <div  class="selectViewbox">
        <div class="memSelectbox"><h3 class="memSelect">會員登入</h3><span id="line"></span><h3 class="vdSelect">業者登入</h3></div>
        <div class="vdSelectbox"><h3 class="memSelect">會員註冊</h3><span id="line"></span><h3 class="vdSelect">業者註冊</h3></div>     
    </div>

    <div class="viewbox">
        <div class="signInviewbox">
            <span class="memContainer">
             	<form action="<%=request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8" id="memSignIn" novalidate>    
                    <table class="table table-hover">
                    <tr><th>會員信箱</th><td><input type="text" name="mem_email" class="form-control" value="${memVO.mem_email}"></td></tr>
                    <tr><th>會員密碼</th><td><input type="password" name="mem_pwd" class="form-control"></td></tr>
                    </table>
                    <div>
                    <input type="hidden" name="action" value="memSignInSubmit">
                    <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm btnwidth">
                    </div>
                </form>
            </span>
            <span class="vdContainer">
                <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" id="vdSignIn" novalidate>    
                    <table class="table table-hover">
                    <tr><th>業者信箱</th><td><input type="text" name="vd_email" class="form-control" value="${vendorVO.vd_email}"></td></tr>
                    <tr><th>業者密碼</th><td><input type="password" name="vd_pwd" class="form-control"></td></tr>
                    </table>
                    <div>
                    <input type="hidden" name="action" value="vdSignInSubmit">
                    <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm btnwidth">
                    </div>
                </form>
            </span>
            <div class="pwdSignbox">
                <div class="col-2" id="getPassword"><a href="<%=request.getContextPath()%>/front-end/index/ForgetPassword.jsp">忘記密碼?</a></div>
                <div class="toSignUp signIninorup">註冊成為白金之星會員</div>
                <div class="toSignIn signIninorup">已成為白金之星會員</div>  
            </div>       
        </div>

        <div class="signUpviewbox">
            <span class="memContainer">
                <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8" id="memSignUp" novalidate> 
                    <table class="table table-hover">
                    <tr><th>註冊信箱</th><td><input type="text" name="mem_email" id="mem_email" class="form-control" value="${memVO.mem_email}"></td></tr>
                    <!-- <tr><th>註冊密碼</th><td><input type="password" name="mem_pwd" class="form-control" id="mem_pwd"></td></tr> -->
                    <tr><th>姓名</th><td><input type="text" name="mem_name" id="mem_name" class="form-control" value="${memVO.mem_name}"></td></tr>
                    <tr><th>暱稱</th><td><input type="text" name="mem_alias" id="mem_alias" class="form-control" value="${memVO.mem_alias}"></td></tr>  
                    <tr><th>性別</th><td><div>
                    <label><input type="radio" name="mem_gender" value="男" class="gender">男</label>
                    <label><input type="radio" name="mem_gender" value="女" class="gender" id="mem_gender">女</label>
                    <label><input type="radio" name="mem_gender" value="無" class="gender">無</label></div>
                    </td></tr>
                    <tr><th>生日</th><td><input type="text" name="mem_birth" id="mem_birth" class="form-control" value="${memVO.mem_birth}"></td></tr>
                    <tr><th>手機</th><td><input type="text" name="mem_mobile" id="mem_mobile" class="form-control" value="${memVO.mem_mobile}" class="AA"></td></tr>
                    <tr><th>市話</th><td><input type="text" name="mem_tel" id="mem_tel" class="form-control" value="${memVO.mem_tel}"></td></tr>
                    <tr><th>住址</th><td><div class="form-row" name="mem_addr">
										    <div class="col-2">
										      	<select class="form-control" id="memcounties"></select>
										    </div>
										    <div class="col">
										      <input type="text" name="mem_addrselct" class="form-control memsimpleaddress">
										    </div>
									  	  </div></td></tr>

                    </table>
                    <div>
                    <input type="hidden" name="mem_addr" id="mem_addr" value="${memVO.mem_addr}">
                    <input type="hidden" name="action" value="memSignUpSubmit">
                    <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm btnwidth">
                    </div>
                    <div class="toSignUp signUpinorup">註冊成為白金之星會員</div>
                    <div class="toSignIn signUpinorup">已成為白金之星會員</div>  
                </form>   
            </span>
            
            
            <span class="vdContainer">
                <form action="<%= request.getContextPath()%>/vendor/VendorServlet" method="post" accept-charset="UTF-8" 
                  enctype="multipart/form-data" id="vdSignUp" novalidate>
                    <table class="table table-hover" >
                    <tr><th>註冊信箱</th><td><input type="text" name="vd_email" id="vd_email" class="form-control" value="${vendorVO.vd_email}"></td></tr>
                    <!-- <tr><th>註冊密碼</th><td><input type="password" name="vd_pwd" id="vd_pwd" class="form-control"></td></tr> -->
                    <tr><th>負責人姓名</th><td><input type="text" name="vd_name" id="vd_name" class="form-control" value="${vendorVO.vd_name}"></td></tr>
                    <tr><th>身份證字號</th><td><input type="text" name="vd_id" id="vd_id" class="form-control" value="${vendorVO.vd_id}"></td></tr>
                    <tr><th>生日</th><td><input type="text" name="vd_birth" id="vd_birth" class="form-control" value="${vendorVO.vd_birth}"></td></tr>       
                    <tr><th>負責人手機</th><td><input type="text" name="vd_mobile" id="vd_mobile" class="form-control" value="${vendorVO.vd_mobile}"></td></tr>
                    <tr><th>露營區名稱</th><td><input type="text" name="vd_cgname" id="vd_cgname" class="form-control" value="${vendorVO.vd_cgname}"></td></tr>
                    <tr><th>連絡電話</th><td><input type="text" name="vd_cgtel" id="vd_cgtel" class="form-control" value="${vendorVO.vd_cgtel}"></td></tr>
                    <tr><th>地址</th><td><div class="form-row" name="vd_cgaddr" id="vd_cgaddrlatlon">
										    <div class="col-2">
										      	<select class="form-control" id="counties"></select>
										    </div>
										    <div class="col">
										      <input type="text" name="vd_cgaddrselct" class="form-control simpleaddress">
										    </div>
									  	  </div></td></tr>                         
                    <tr><th>統一編號</th><td><input type="text" name="vd_taxid" id="vd_taxid" class="form-control" value="${vendorVO.vd_taxid}"></td></tr>
                    <tr><th>匯款帳戶資訊</th><td><input type="text" name="vd_acc" id="vd_acc" class="form-control" value="${vendorVO.vd_acc}"></td></tr>
                    <tr><th>營業登記</th><td><div class="custom-file">
                                                <input type="file" name="vd_brc" id="myfile" class="custom-file-input form-control-file">
                                                <label class="custom-file-label">上傳圖片</label>
                                            </div>
                                            <div class="pretitle">                                      
                                            <!-- Button trigger modal 圖片預覽按鈕-->
                                            <span id="smpreview" data-toggle="modal" data-target="#exampleModalCenter"></span>
                                            </div></td></tr>
                    <tr><th>營區地圖</th><td><div class="custom-file">
                                                <input type="file" name="vd_map" id="mapfile" class="custom-file-input form-control-file">
                                                <label class="custom-file-label">上傳圖片</label>
                                            </div>
                                            <div class="mappretitle">                                      
                                            <!-- Button trigger modal 圖片預覽按鈕-->
                                            <span id="smpreviewmap" data-toggle="modal" data-target="#exampleModalCenterMap"></span>
                                            </div></td></tr>
                                            
                    </table>
                    <div>
                    <input type="hidden" name="vd_cgaddr" id="vd_cgaddr" value="${vendorVO.vd_cgaddr}">
                    <input type="hidden" name="vd_lat" id="vd_lat" value="${vendorVO.vd_lat}">
                    <input type="hidden" name="vd_lon" id="vd_lon" value="${vendorVO.vd_lon}">
                    <input type="hidden" name="action" value="vdSignUpSubmit">
                    <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm btnwidth">
                    </div>
                    <div class="toSignUp signUpinorup">註冊成為白金之星會員</div>
                    <div class="toSignIn signUpinorup">已成為白金之星會員</div> 
                </form>
            </span>

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

            <!-- Modal 地圖圖片預覽-->
            <div class="modal fade" id="exampleModalCenterMap" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitleMap" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                  <div class="modal-header">
                    <h6 class="modal-title" id="exampleModalCenterTitle">My Business Registration Certificate</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                    </button>
                  </div>
                  <div class="modal-body" id="previewmap">
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary btn-sm" data-dismiss="modal">Close</button>
                  </div>
                </div>
              </div>
            </div>

        </div>
    </div>
    <div id="alertmsg">
        已發送Mail通知,請至信箱收取！       
    </div>
</div>
<button type="button" id="memMagicalbtn" class="btn btn-outline-danger btn-sm">會員神奇小按鈕</button>
<button type="button" id="vdMagicalbtn" class="btn btn-outline-danger btn-sm">業者神奇小按鈕</button>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<script src="<%= request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script>
    function init() {
    $(".toSignUp").click(function(){
        $(".toSignIn").css("display", "inline-block");
        $(".toSignUp,.memSelectbox").css("display", "none");
        $(".signInviewbox").css("marginTop", "-600px");
        $(".viewbox").css("height", "100%");
    });

    $(".toSignIn").click(function(){
        $(".toSignIn").css("display", "none");
        $(".toSignUp,.memSelectbox").css("display", "inline-block");
        $(".signInviewbox").css("marginTop", "0px");
        $(".viewbox").css("height", "300px");
    });

    $(".vdSelect").click(function(){
        $(".memContainer").css("marginLeft", "-100%");
        $(".memSelect").css("color", "#a6a6a6");
        $(".vdSelect").css("color", "#444"); 
    });
    $(".memSelect").click(function(){
        $(".memContainer").css("marginLeft", "5%");
        $(".vdSelect").css("color", "#a6a6a6");
        $(".memSelect").css("color", "#444");
    });

    //地址縣市下拉式選單
    var city = ["基隆市", "臺北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
                "臺中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "臺南市",
                "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", 
                "連江縣"];
   	for (var i = 0; i < city.length; i++){
   		$("#counties").append(`<option class="counties" value="` + city[i] + `">` + city[i] + `</option>`);
   		$("#memcounties").append(`<option class="counties" value="` + city[i] + `">` + city[i] + `</option>`);
   	}
   	//會員詳細地址
   	$(".memsimpleaddress").blur(function() {
	  	  var memcountyname = $("#memcounties").val();
		  var memsimpleaddress = $(".memsimpleaddress").val();
		  var memdetailaddress = memcountyname + memsimpleaddress;
		  $("#mem_addr").val(memdetailaddress);
      });
   	
   	//會員錯誤回報地址
   	var memereturnaddress = "${memVO.mem_addr}";
   	$("#memcounties").val((memereturnaddress).substring(0, 3));
	$(".memsimpleaddress").val((memereturnaddress).substring(3));

   	//業者錯誤回報地址
   	var returnaddress = "${vendorVO.vd_cgaddr}";
   	$("#counties").val((returnaddress).substring(0, 3));
	$(".simpleaddress").val((returnaddress).substring(3));

    //處裡錯誤回傳後顯示頁面
    var toSignInSignUp = "<%= request.getAttribute("toSignInSignUp")%>";  //取得servlet傳過來的判斷給哪個form表單的Attribute
    var formInput = ("#" + toSignInSignUp).toString();  //Attribute加上#變成設定好的fotm id

    $(document).ready(function(){
        var toSignInSignUp = "<%= request.getAttribute("toSignInSignUp")%>";
        // console.log(toSignInSignUp);
        if(toSignInSignUp === "vdSignIn"){
          $(".memContainer").css("marginLeft", "-100%");
          $(".memSelect").css("color", "#a6a6a6");
          $(".vdSelect").css("color", "#444"); 
        }else if(toSignInSignUp === "memSignUp"){
          $(".toSignIn").css("display", "block");
          $(".toSignUp,.memSelectbox").css("display", "none");
          $(".signInviewbox").css("marginTop", "-600px");
          $(".viewbox").css("height", "100%");
        }else if(toSignInSignUp === "vdSignUp"){
          $(".memContainer").css("marginLeft", "-100%");
          $(".memSelect").css("color", "#a6a6a6");
          $(".vdSelect").css("color", "#444");
          $(".toSignIn").css("display", "block");
          $(".toSignUp,.memSelectbox").css("display", "none");
          $(".signInviewbox").css("marginTop", "-600px"); 
          $(".viewbox").css("height", "100%");
        }
      });

    //顯示錯誤處理
    var errorMsgs = <%= request.getAttribute("errorMsgs")%>;
    // console.log(errorMsgs);


    //SIGNUPOK的話alert訊息&移轉
    window.alertmsg = function() {
        $("#alertmsg").css("display", "block");
    };

    $.each(errorMsgs,function(name, value){
    	var name = name;
    	var msg = value;
      for (var k = 0; k < $(formInput + " input").length; k++){ //form表單內input的長度
            if($(formInput).find("input:eq(" + k + ")").attr("name") === name && 
            $(formInput).find("input:eq(" + k + ")").attr("name") !== "mem_gender" &&
            $(formInput).find("input:eq(" + k + ")").attr("name") !== "vd_lat" &&
            $(formInput).find("input:eq(" + k + ")").attr("name") !== "vd_lon"){  //取得input的名字(先排除為radio的mem_gender以及經緯度)
            $(formInput).find("input:eq(" + k + ")").addClass("form-control is-invalid");
            $(formInput).find("input:eq(" + k + ")").after("<div class=\"errorMsgs invalid-feedback fas fa-campground fa-1g\">" + msg + "</div>");
          }
        }
      if(name === "mem_gender"){  //另外處理為radio的mem_gender
        $("form :radio:eq(1)").addClass("is-invalid");
        $(":radio:eq(1)").parent().parent().after("<div class=\"errorMsgs invalid-feedback fas fa-venus-mars fa-1g\">" + msg + "</div>");
        }

        if(name === "SIGNUPOK!"){  
        alertmsg();
        $("#alertmsg").click(function(){
            window.location.href ="../front-end/index/index.jsp";
            })
        }
    });

    //前端日期卡關
    $.datetimepicker.setLocale('zh');
      $('#mem_birth').datetimepicker({
        theme: '',              //theme: 'dark',
        timepicker:false,       //timepicker:true,
        step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
        format:'Y-m-d',         //format:'Y-m-d H:i:s',
        // value: "<--%= memVO.getMem_birth()%>", 
        value: "${memVO.mem_birth}",
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:             '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });

      $('#vd_birth').datetimepicker({
        theme: '',              //theme: 'dark',
        timepicker:false,       //timepicker:true,
        step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
        format:'Y-m-d',         //format:'Y-m-d H:i:s',
        // value: "<--%= memVO.getMem_birth()%>", 
        value: "${vendorVO.vd_birth}",
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

    //members
    //性別欄checked
    var gender = document.getElementsByClassName('gender');
    for (var i =0; i < gender.length; i++){
      if (gender[i].value === '${memVO.mem_gender}'){
        gender[i].setAttribute('checked', 'true');
      }
    }

    //神奇小按鈕
    $("#memMagicalbtn").click(function(){
        $("#mem_email").val("gustavosandia@gmail.com");
        $("#mem_pwd").val("123")
        $("#mem_name").val("沈燕撫");
        $("#mem_alias").val("大和撫子");
        $("#mem_gender").attr("checked", "true");
        $("#mem_birth").val("2002-10-10");
        $("#mem_mobile").val("0912345678");
        $("#mem_tel").val("0246813579");
        // $("#mem_addr").val("高雄市茂林區多納里大鬼湖1號")
        $("#memcounties").val("高雄市")
        $(".memsimpleaddress").val("茂林區多納里大鬼湖1號")
        $(".memsimpleaddress").blur();
      }
    );

    //vendor
    //圖片上傳
    var myfile = document.getElementById("myfile");
    var preview = document.getElementById("preview");
    var smpreview = document.getElementById("smpreview");

    var mapfile = document.getElementById("mapfile");
    var previewmap = document.getElementById("previewmap");
    var smpreviewmap = document.getElementById("smpreviewmap");

    var img = document.getElementsByTagName('img');

        myfile.addEventListener('change', function() {
            var files = myfile.files;
            if (preview.children.length > 0) {
                console.log(preview.children.length);
              preview.children[0].remove();
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

        mapfile.addEventListener('change', function() {
        var mapfiles = mapfile.files;
            if (previewmap.children.length > 0) {
                console.log(previewmap.children.length);
              previewmap.children[0].remove();
              smpreviewmap.children[0].remove();
          }

          if (mapfiles !== null) {
                var file = mapfiles[0];
                    if (file.type.indexOf('image') > -1) {
                        var reader = new FileReader();
                        reader.addEventListener('load', function(e) {
                            var img = document.createElement('img'); //創建圖片
                            img.setAttribute('src', e.target.result);
                            previewmap.append(img);
                            var img = document.createElement('img'); //創建小圖片
                            img.setAttribute('src', e.target.result);
                            smpreviewmap.append(img);
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
      $("#mapfile").change(function(){
        if($("#mapmyfile").val() !== ""){
          $(".mappretitle").css("display", "inline-block")
        }
      });

        
      //神奇小按鈕
      $("#vdMagicalbtn").click(function(){
        $("#vd_email").val("gustavosandia@gmail.com");
        $("#vd_pwd").val("123")
        $("#vd_name").val("夏猛禽");
        $("#vd_id").val("AA123456789");
        $("#vd_birth").val("2008-10-07");
        $("#vd_mobile").val("0987654321");
        $("#vd_cgname").val("愛禽海ㄟ露營區");
        $("#vd_cgtel").val("0357924680");
        // $("#vd_cgaddr").val("宜蘭縣頭城鎮龜山島401號");
        $("#counties").val("宜蘭縣");
        $(".simpleaddress").val("頭城鎮龜山島401號");
        $("#vd_taxid").val("86437531");
        $("#vd_acc").val("70031415926535")
        $(".simpleaddress").blur();   //產生經緯度用
        }
      );
    }

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
    window.onload = init;
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA0yuHIUOGU1dx1CO_o1gOYuN6AkNlGSbQ&libraries=places&callback=initMap" async defer></script>
</body>
</html>