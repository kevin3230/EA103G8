<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.vendor.model.*"%>

<%
	VendorVO vendorVO = (VendorVO)request.getAttribute("vendorVO"); //VendorServlet.java(Concroller), 存入req的vendorVO物件
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>listOneVendor</title>
<style type="text/css" media="screen">
  .container, h3{
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
                  	<h3>業者基本資料</h3>
                  	<table class="table">	
                  		<tr><th>業者編號</th><td>${vendorVO.vd_no}</td></tr>
                  		<tr><th>信箱</th><td>${vendorVO.vd_email}</td></tr>
                  		<!-- <tr><th>密碼</th><td>${vendorVO.vd_pwd}</td></tr> -->
                  		<tr><th>負責人姓名</th><td>${vendorVO.vd_name}</td></tr>
                  		<tr><th>身份證字號</th><td>${vendorVO.vd_id}</td></tr>
                  		<tr><th>生日</th><td>${vendorVO.vd_birth}</td></tr>
                  		<tr><th>負責人手機</th><td>${vendorVO.vd_mobile}</td></tr>
                  		<tr><th>露營區名稱</th><td>${vendorVO.vd_cgname}</td></tr>
                  		<tr><th>連絡電話</th><td>${vendorVO.vd_cgtel}</td></tr>
                  		<tr><th>地址</th><td>${vendorVO.vd_cgaddr}</td></tr>
                  		<tr><th>統一編號</th><td>${vendorVO.vd_taxid}</td></tr>
                  		<tr><th>匯款帳戶資訊</th><td>${vendorVO.vd_acc}</td></tr>
                  		<tr><th>註冊日期</th><td>${vendorVO.vd_regdate}</td></tr>
                  		<tr><th>帳號狀態</th><td id="vd_stat"></td></tr>
                  		<tr><th>露營區狀態</th><td id="vd_cgstat"></td></tr>
                  		<tr><th>露營區緯度</th><td>${vendorVO.vd_lat}</td></tr>
                  		<tr><th>露營區經度</th><td>${vendorVO.vd_lon}</td></tr>
                  	</table>

                  	<div class="sub_btn">	
                  		<FORM METHOD="post" ACTION="<%= request.getContextPath()%>/vendor/VendorServlet">
                  			<input type="hidden" name="action" value="back_vdUpdateSubmit">
                  			<input type="hidden" name="vd_no" value="${vendorVO.vd_no}">
                        <input type="hidden" name="vd_pwd" value="${vendorVO.vd_pwd}">
                  			<input type="submit" name="修改" value="修改" class="btn btn-outline-secondary btn-sm">
                  			<input type="button" value="返回查詢頁面" onclick="location.href='<%= request.getContextPath()%>/back-end/vendor/serchVendorInfo.jsp'" class="btn btn-outline-secondary btn-sm">
                  		</FORM>
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
    $("#vd_stat").text((${vendorVO.vd_stat} === 1)?'正常':'停權');
    $("#vd_cgstat").text((${vendorVO.vd_cgstat} === 1)?'開啟':'關閉');
  </script>
</body>

</html>
