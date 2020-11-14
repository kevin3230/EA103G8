<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO"); //EmpServlet.java(Concroller), 存入req的adminisVO物件
%>
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>AllAdminis - Tables</title>
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
    <%@ include file="/back-end/index-sidebar.jsp" %> 
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <%@ include file="/back-end/index-header.jsp" %> 
        <!-- End of Topbar -->

        <!-- Begin Page Content -->
				<div class="container-fluid">
					<div class="card shadow mb-4">
						<div class="card-header py-3" style="display: inline-block;">
							<h6  class="m-0 font-weight-bold text-primary" style="display: inline-block; padding:10px; ">管理員管理</h6>
						</div> 
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered"  width="100%"	cellspacing="0">
								<tr>
									<th>管理員編號</th>
									<th>姓名</th>
									<th>郵件</th>
									<th>部門</th>
									<th>註冊日期</th>
									<th>權限值</th>
								</tr>
								<tr>
									<td><%=adminisVO.getAdminis_no()%></td>
									<td><%=adminisVO.getAdminis_name()%></td>
									<td><%=adminisVO.getAdminis_email()%></td>
									<td><%=adminisVO.getAdminis_dept()%></td>
									<td><%=adminisVO.getAdminis_regdate()%></td>
									<td><%=adminisVO.getAdminis_pv()%></td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- /.container-fluid -->
		</div>
      <!-- End of Main Content -->

      <!-- Footer -->
       <%@ include file="/back-end/index-footer.jsp" %> 
      <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top" style="display: none;">
    <i class="fas fa-angle-up"></i>
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
</body>

</html>
