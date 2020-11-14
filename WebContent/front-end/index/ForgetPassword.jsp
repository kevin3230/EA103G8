<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/members/assets/Members.css">
<title>ForgetPassword</title>
<style>
	.formargin {
	    margin-top: 3%;
	    min-height:calc(90vh - 100px);
	    padding-top: 7%;
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
		<h3 class="memSelect">忘記密碼</h3>
		<form action="<%=request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8" id="memSignIn" novalidate>    
	      <table class="table table-hover">
	      <tr><th>請輸入信箱</th><td><input type="text" name="password_email" class="form-control"></td></tr>
	      </table>
	      <div>
	      <input type="hidden" name="action" value="getPassword">
	      <input type="submit" name="送出" class="btn btn-outline-secondary btn-sm btnwidth">
	      </div>
	    </form>
	    <div class="col-2" id="getPassword"><a href="<%=request.getContextPath()%>/front-end/index/MemVdSignInSignUp.jsp">返回登入</a></div>
    </div>

    <div id="alertmsg">
    	已發送Mail通知,請至信箱收取！    	
    </div>

<%@ include file="/front-end/index/footer.jsp" %>

<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
</body>
<script>
	//顯示錯誤處理
    var errorMsgs = <%= request.getAttribute("errorMsgs")%>;
    // console.log(errorMsgs);

    window.alertmsg = function() {
    	$("#alertmsg").css("display", "block");
    };

    $.each(errorMsgs,function(name, value){
      var name = name;
      var msg = value;
      for (var k = 0; k < $("form input").length; k++){ //form表單內input的長度
            if($("form").find("input:eq(" + k + ")").attr("name") === name){  //取得input的名字
            $("form").find("input:eq(" + k + ")").addClass("form-control is-invalid");
            $("form").find("input:eq(" + k + ")").after("<div class=\"errorMsgs invalid-feedback fas fa-campground fa-1g\">" + msg + "</div>");
          }
        }
        
      if(name === "OK!"){  
        alertmsg();
        $("#alertmsg").click(function(){
        	window.location.href ="../front-end/index/index.jsp";
        	})
        }
    });
</script>

</html>