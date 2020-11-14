<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.vendor.model.*"%>

<%
	VendorService vendorSvc = new VendorService();
	List<VendorVO> list = vendorSvc.getAll();
	pageContext.setAttribute("list",list);
%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>listAllVendor</title>
<style type="text/css" media="screen">
  .container-fluid, h3{
    text-align: center;
  }
  form .btn{
    margin:0 auto;
  }
  .table{
    background-color:#FFF;
  }
  .table th, .table tr, .table td{
    border: 1px solid #a6a6a6;
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
								<div class="container-fluid">
									<h3>業者總表</h3>
									<table class="table">
										<tr>
											<th>業者編號</th>
											<th>信箱</th>
											<th>負責人姓名</th>
											<th>身份證字號</th>
											<th>生日</th>
											<th>負責人手機</th>
											<th>露營區名稱</th>
											<th>連絡電話</th>
											<th>詳細資訊</th>
										</tr>
										<%@ include file="page1.file" %>
										<c:forEach var="vendorVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
										<tr>
											<td>${vendorVO.vd_no}</td>
											<td>${vendorVO.vd_email}</td>
											<td>${vendorVO.vd_name}</td>
											<td>${vendorVO.vd_id}</td>
											<td>${vendorVO.vd_birth}</td>
											<td>${vendorVO.vd_mobile}</td>
											<td>${vendorVO.vd_cgname}</td>
											<td>${vendorVO.vd_cgtel}</td>
											<td>
												<FORM METHOD="post" ACTION="<%= request.getContextPath()%>/vendor/VendorServlet">
													<input type="hidden" name="action" value="listOneDetails">
													<input type="hidden" name="vd_no" value="${vendorVO.vd_no}">
													<input type="submit" name="詳細資訊" value="詳細資訊" class="btn btn-outline-secondary btn-sm">
												</FORM>
											</td>
										</tr>
										</c:forEach>
									</table>
									<%@ include file="page2.file" %>
									<br>
									<input type="button" value="返回查詢頁面" onclick="location.href='<%= request.getContextPath()%>/back-end/vendor/serchVendorInfo.jsp'" class="btn btn-outline-secondary btn-sm">
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
</body>

</html>