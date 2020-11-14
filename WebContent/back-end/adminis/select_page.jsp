<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


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
	href="<%=request.getContextPath()%>/vendor/fontawesome-free/css/all.min.css"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/sb-admin-2.min.css"
	rel="stylesheet">
<!-- Custom styles for this page -->
<link
	href="<%=request.getContextPath()%>/vendor/datatables/dataTables.bootstrap4.min.css"
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
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>

				<ul>
					<li><a href='listAllAdminis.jsp'>List</a> all Adminiss. <br>
					<br></li>


					<li>
						<FORM METHOD="post" ACTION="adminis.do">
							<b>輸入員工編號 (如A000000001):</b> <input type="text" name="adminis_no">
							<input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="送出">
						</FORM>
					</li>

					<jsp:useBean id="adminisSvc" scope="page"
						class="com.adminis.model.AdminisService" />

					<li>
						<FORM METHOD="post" ACTION="adminis.do">
							<b>選擇員工編號:</b> <select size="1" name="adminis_no">
								<c:forEach var="adminisVO" items="${adminisSvc.all}">
									<option value="${adminisVO.adminis_no}">${adminisVO.adminis_no}
								</c:forEach>
							</select> <input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="送出">
						</FORM>
					</li>

					<li>
						<FORM METHOD="post" ACTION="adminis.do">
							<b>選擇員工姓名:</b> <select size="1" name="adminis_no">
								<c:forEach var="adminisVO" items="${adminisSvc.all}">
									<option value="${adminisVO.adminis_no}">${adminisVO.adminis_name}
								</c:forEach>
							</select> <input type="hidden" name="action" value="getOne_For_Display">
							<input type="submit" value="送出">
						</FORM>
					</li>
				</ul>

				<!-- End of Main Content -->

				<!-- Footer -->

				<!-- End of Footer -->
				<%@ include file="/back-end/index-footer.jsp"%>
			</div>
			<!-- End of Content Wrapper -->

		</div>
		<!-- End of Page Wrapper -->

		<!-- Scroll to Top Button-->
		<a class="scroll-to-top rounded" href="#page-top"
			style="display: none;"> <i class="fas fa-angle-up"></i>
		</a>

		<!-- Logout Modal-->
  <%@ include file="/back-end/logout.jsp" %> 

		<script
			src="<%=request.getContextPath()%>/vendor/jquery/jquery.min.js"></script>
		<script
			src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
		<!-- Core plugin JavaScript-->
		<script
			src="<%=request.getContextPath()%>/vendor/jquery-easing/jquery.easing.min.js"></script>
		<!-- Custom scripts for all pages-->
		<script src="<%=request.getContextPath()%>/js/sb-admin-2.min.js"></script>
		<!-- Page level plugins -->
		<script
			src="<%=request.getContextPath()%>/vendor/datatables/jquery.dataTables.js"></script>
		<script
			src="<%=request.getContextPath()%>/vendor/datatables/dataTables.bootstrap4.min.js"></script>
		<!-- Page level custom scripts -->
		<script src="<%=request.getContextPath()%>/js/demo/datatables-demo.js"></script>
</body>

</html>
