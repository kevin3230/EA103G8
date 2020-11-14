<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>serchMemInfo</title>
<style type="text/css" media="screen">
  .container, h3{
    text-align: center;
  }
  .container{
    width: 50%
  }
  .textline{
    display: inline;
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
                  <c:if test="${not empty errorMsgs}">
                  	<font style="color:red">請修正以下錯誤:</font>
                  	<ul>
                  		<c:forEach var="message" items="${errorMsgs}">
                  			<li style="color:red">${message}</li>
                  		</c:forEach>
                  	</ul>
                  </c:if>

                      <h3>會員查詢</h3>
                      <div><a href="<%= request.getContextPath()%>/back-end/members/listAllMem.jsp">顯示所有會員資料</a></div>
                      <br>
                      <div class="textline"> 
                          <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">編號查詢
                              <input type="text" name="mem_no">
                              <input type="hidden" name="action" value="getOnebyMem_no">
                              <input type="submit" value="送出" class="btn btn-outline-secondary btn-sm">
                          </form>
                      </div>
                      <div class="textline">
                          <form action="<%= request.getContextPath()%>/members/MembersServlet" method="post" accept-charset="UTF-8">信箱查詢
                              <input type="text" name="mem_email">
                              <input type="hidden" name="action" value="getOnebyMem_email">
                              <input type="submit" value="送出" class="btn btn-outline-secondary btn-sm">
                          </form>
                      </div>
                      <br>
                      <input type="button" value="返回會員查詢" onclick="location.href='<%= request.getContextPath()%>/back-end/members/serchMemInfo.jsp'" class="btn btn-outline-secondary btn-sm">
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
