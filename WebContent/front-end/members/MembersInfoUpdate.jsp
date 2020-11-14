<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*"%>

<%
	MembersVO memVO = (MembersVO) session.getAttribute("memVOupdate"); //MembersServlet.java (Concroller) 存入session的memVOupdate物件
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
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/members/assets/Members.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<title>MembersInfoUpdate</title>
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
				  <div class="container formargin">

				  	<%-- 錯誤表列 --%>
					<!-- <c:if test="${not empty errorMsgs}">
					  <font style="color:red"><i class="fas fa-campground fa-1g"></i>請修正以下錯誤:</font>
					    <c:forEach var="message" items="${errorMsgs}">
					      <div style="color:red"><i class="fas fa-map-signs fa-1g"></i>${message}</div>
					    </c:forEach>
					</c:if> -->

				    <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8" novalidate>
				        <h3>會員資訊修改</h3>
				        <table class="table">
					       <!-- <tr><th>會員編號</th><td><%=memVO.getMem_no()%></td></tr> -->
					       <tr><th>信箱</th><td><%=memVO.getMem_email()%></td></tr>
					       <tr><th>密碼</th><td><input type="password" name="mem_pwd" class="form-control" /></td></tr>
				           <tr><th>確認密碼</th><td><input type="password" name="mem_pwd2" class="form-control" /></td></tr>
					       <tr><th>姓名</th><td><input type="text" name="mem_name" class="form-control" value="<%=memVO.getMem_name()%>" /></td></tr>
					       <tr><th>暱稱</th><td><input type="text" name="mem_alias" class="form-control" value="<%=memVO.getMem_alias()%>" /></td></tr>
					       <tr><th>性別</th><td><label><input type="radio" name="mem_gender" value="男" class="gender" />男</label>
				        				        <label><input type="radio" name="mem_gender" value="女" class="gender" />女</label>
				       				            <label><input type="radio" name="mem_gender" value="無" class="gender" />無</label></td></tr>
					       <tr><th>生日</th><td><input type="date" name="mem_birth" class="form-control" value="<%=memVO.getMem_birth()%>" /></td></tr>
					       <tr><th>手機號碼</th><td><input type="text" name="mem_mobile" class="form-control" value="<%=memVO.getMem_mobile()%>" /></td></tr>
					       <tr><th>市話</th><td><input type="text" name="mem_tel" class="form-control" value="<%=memVO.getMem_tel()%>" /></td></tr>
					       <tr><th>住址</th><td><div class="form-row" name="mem_addr">
										    <div class="col-2">
										      	<select class="form-control" id="memcounties"></select>
										    </div>
										    <div class="col">
										      <input type="text" name="mem_addrselct" class="form-control memsimpleaddress">
										    </div>
									  	  </div></td></tr>
					       <!-- <tr><th>會員類型</th><td><%=memVO.getMem_type()%></td></tr> -->
					       <tr><th>註冊日期</th><td><%=memVO.getMem_regdate()%></td></tr>
					       <!-- <tr><th>帳號狀態</th><td><%=memVO.getMem_stat()%></td></tr> -->
				        </table> 
				 	    <div class="sub_btn">
				            <input type="hidden" name="action" value="update">
				            <input type="hidden" name="mem_no" value="<%=memVO.getMem_no()%>">
				 		        <input type="hidden" name="mem_email" value="<%=memVO.getMem_email()%>">
				 		        <input type="hidden" name="mem_addr" id="mem_addr" value="<%=memVO.getMem_addr()%>">
				 		        <input type="hidden" name="mem_type" value="<%=memVO.getMem_type()%>">
				 		        <input type="hidden" name="mem_regdate" value="<%=memVO.getMem_regdate()%>">
				            <input type="hidden" name="mem_stat" value="<%=memVO.getMem_stat()%>">
				            <input type="submit" name="送出修改" value="送出修改" class="btn btn-outline-secondary btn-sm">
				            <input type="button" value="返回會員資訊" onclick="location.href='<%= request.getContextPath()%>/front-end/members/MembersInfo.jsp'" class="btn btn-outline-secondary btn-sm">
					    </div>
				    </form>
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
<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
<script src="<%= request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<%@ include file="/front-end/index/Notice.jsp"%>

	<script>
		//顯示錯誤處理
	    var errorMsgs = <%= request.getAttribute("errorMsgs")%>;
	    console.log(errorMsgs);
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

	    //地址縣市下拉式選單
	    var city = ["基隆市", "臺北市", "新北市", "桃園市", "新竹市", "新竹縣", "苗栗縣",
	                "臺中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "臺南市",
	                "高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "臺東縣", "澎湖縣", "金門縣", 
	                "連江縣"];
	   	for (var i = 0; i < city.length; i++){
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

	    //前端日期卡關
	    $.datetimepicker.setLocale('zh');
	      $('#mem_birth, #vd_birth').datetimepicker({
	        theme: '',              //theme: 'dark',
	        timepicker:false,       //timepicker:true,
	        step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	        format:'Y-m-d',         //format:'Y-m-d H:i:s',
	        value: "<%=memVO.getMem_birth()%>",
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

		//性別欄checked
	    var gender = document.getElementsByClassName('gender');
    	for (var i =0; i < gender.length; i++){
      	if (gender[i].value === '<%=memVO.getMem_gender()%>'){
        gender[i].setAttribute('checked', 'true');
      }
    }
	</script>
</body>
</html>