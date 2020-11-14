<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.members.model.*"%>

<%
	MembersVO memVO = (MembersVO) session.getAttribute("memVOupdate"); //MembersServlet.java (Concroller) 存入session的memVOupdate物件
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>back_MembersInfoUpdate</title>
<style type="text/css" media="screen">
  form, h3{
    text-align: center;
  }
  form .btn{
    margin:0 auto;
  }
  .container{
    width: 50%
  }
  .table{
    background-color:#FFF;
  }
  .table th, .table tr, .table td{
    border: 1px solid #a6a6a6;
  }
  .table th{
    width: 30%;
  }
  label input{
    margin:0 15px;
  }
  input[type="text"],
  input[type="password"],
  input[type="date"]{
    border-radius:4px;
    border:1px solid #c8cccf;
    color:#6a6f77;
    width: 100%;
  }
  .sub_btn{
    display: inline;
  }
  .sub_btn input{
    margin:0 20px;
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
              <div class="row">
                <div class="col-12">

                  <div class="container">

                    <%-- 錯誤表列 --%>
                    <!-- <c:if test="${not empty errorMsgs}">
                    	<font style="color:red">請修正以下錯誤:</font>
                    	<ul>
                    		<c:forEach var="message" items="${errorMsgs}">
                    			<li style="color:red">${message}</li>
                    		</c:forEach>
                    	</ul>
                    </c:if> -->

                    	<form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8" novalidate>
                    		<h3>後台會員資訊修改</h3>
                    		<table class="table">
                    			<tr><th>會員編號</th><td><%=memVO.getMem_no()%></td></tr>
                    			<tr><th>信箱</th><td><%=memVO.getMem_email()%></td></tr>
                    			<!-- <tr><th>密碼</th><td><input type="text" name="mem_pwd" value="<%=memVO.getMem_pwd()%>" /></td></tr> -->
                    			<tr><th>姓名</th><td><%=memVO.getMem_name()%></td></tr>
                    			<tr><th>暱稱</th><td><%=memVO.getMem_alias()%></td></tr>
                    			<tr><th>性別</th><td><%=memVO.getMem_gender()%></td></tr>
                    			<tr><th>生日</th><td><%=memVO.getMem_birth()%></td></tr>
                    			<tr><th>手機號碼</th><td><%=memVO.getMem_mobile()%></td></tr>
                    			<tr><th>市話</th><td><%=memVO.getMem_tel()%></td></tr>
                    			<tr><th>地址</th><td><%=memVO.getMem_addr()%></td></tr>
                    			<tr><th>會員類型</th><td id="mem_type"></td></tr>
                    			<!--<tr><th>會員類型</th><td><%=memVO.getMem_type()%></td></tr>-->
                    			<tr><th>註冊日期</th><td><%=memVO.getMem_regdate()%></td></tr>
                    			<tr><th>帳號狀態</th><td><select name="mem_stat" id="mem_stat">
                                                  <option class="statSelect" value="0">停權</option>
                                                  <option class="statSelect" value="1">正常</option></select></td></tr>
                    		</table> 
                    		<div class="sub_btn">
                     			<input type="hidden" name="action" value="back-update">
                     			<input type="hidden" name="mem_no" value="<%=memVO.getMem_no()%>">
                     			<input type="hidden" name="mem_email" value="<%=memVO.getMem_email()%>">
	                  	        <input type="hidden" name="mem_pwd" value="<%=memVO.getMem_pwd()%>">
	                    	    <input type="hidden" name="mem_pwd2" value="<%=memVO.getMem_pwd()%>">
	                        	<input type="hidden" name="mem_name" value="<%=memVO.getMem_name()%>">
	                        	<input type="hidden" name="mem_alias" value="<%=memVO.getMem_alias()%>">
	        	                <input type="hidden" name="mem_gender" value="<%=memVO.getMem_gender()%>">
	            	            <input type="hidden" name="mem_birth" value="<%=memVO.getMem_birth()%>">
	                	        <input type="hidden" name="mem_mobile" value="<%=memVO.getMem_mobile()%>">
	                    	    <input type="hidden" name="mem_tel" value="<%=memVO.getMem_tel()%>">
	      	                	<input type="hidden" name="mem_addr" value="<%=memVO.getMem_addr()%>">
                     			<input type="hidden" name="mem_type" value="<%=memVO.getMem_type()%>">
                     			<input type="hidden" name="mem_regdate" value="<%=memVO.getMem_regdate()%>">
                     			<input type="hidden" name="mem_stat" value="<%=memVO.getMem_stat()%>">
                     			<input type="submit" name="送出修改" value="送出修改" class="btn btn-outline-secondary btn-sm">
                     			<input type="button" value="返回查詢頁面" onclick="location.href='<%= request.getContextPath()%>/back-end/members/serchMemInfo.jsp'" class="btn btn-outline-secondary btn-sm">
                     		</div>
                    	</form>
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
  <%@ include file="/back-end/logout.jsp" %> >

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

    var vd_stat = "<%=memVO.getMem_type()%>";
    if (vd_stat === "1"){
      $("#mem_type").text("一般會員");
    }else if (vd_stat === "0"){
      $("#mem_type").text("非會員");
    }else if (vd_stat === "2"){
      $("#mem_type").text("其他");
    }
    

    //帳號狀態欄selected
      var stat = document.getElementsByClassName('statSelect');
      for (var i =0; i < stat.length; i++){
        if (stat[i].value === '<%=memVO.getMem_stat()%>'){
        stat[i].setAttribute('selected', 'true');
            }
      }

    //   //性別欄checked
    //   var gender = document.getElementsByClassName('gender');
    //   for (var i =0; i < gender.length; i++){
    //     if (gender[i].value === '<%=memVO.getMem_gender()%>'){
    //     gender[i].setAttribute('checked', 'true');
    //   }
    //   console.log('<%=memVO.getMem_gender()%>');
    // }
  </script>
</body>

</html>
