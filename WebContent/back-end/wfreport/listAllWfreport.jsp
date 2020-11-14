<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.wfreport.model.*"%>

<%
    WFReportService wfreportSvc = new WFReportService();
    List<WFReportVO> list = wfreportSvc.getAll();
    pageContext.setAttribute("list",list);
%>
<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="waterfallSvc" scope="page" class="com.waterfall.model.WaterfallService" />
<jsp:useBean id="wfreplySvc" scope="page" class="com.waterfall.model.WFReplyService" />
<jsp:useBean id="adminisSvc" scope="page" class="com.adminis.model.AdminisService" />

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Report</title>
<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath()%>/utility/back-end/vendor/fontawesome-free/css/all.min.css"	rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/utility/back-end/css/sb-admin-2.min.css"	rel="stylesheet">
<!-- Custom styles for this page -->
<link href="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/dataTables.bootstrap4.min.css"	rel="stylesheet">
<style>
#list{
width:90%;
margin:10 auto;
}
#page2{
text-align: center;
}
</style>
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
		                	<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
								<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
							<!-- ReportList -->
							<div id="list">
							<div style="text-align:right; margin: 10 20;">
								<%@ include file="page/page1.file"%>
							</div>
							<table class="table table-hover">
							  <thead>
							    <tr>
							      <th scope="col">檢舉編號</th>
							      <th scope="col">被檢舉文章</th>
							      <th scope="col">被檢舉回覆</th>
							      <th scope="col">檢舉會員</th>
							      <th scope="col">檢舉原因</th>
							      <th scope="col">管理員</th>
							      <th scope="col">狀態</th>
							      <th scope="col">檢視</th>
							    </tr>
							  </thead>
							  <c:forEach var="wfreportVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">         
							  <c:set var="wf_title" value="${waterfallSvc.getOneWaterfall(wfreportVO.rep_wfno).wf_title }"/>
							  <c:set var="wfr_content" value="${wfreplySvc.getOneWFReply(wfreportVO.rep_wfrno).wfr_content }"/>	
							  <c:set var="rep_content" value="${wfreportVO.rep_content}"/>	
							  <c:set var="short_title" value="${fn:substring(wf_title, 0, 9)}"/>
							  <c:set var="short_content" value="${fn:substring(wfr_content, 0, 9)}"/>
							  <c:set var="short_reason" value="${fn:substring(rep_content, 0, 9)}"/>
							  
							  <tbody>
							    <tr>
							      <th scope="row">${wfreportVO.rep_no}</th>
							      <td>${short_title}</td>
							      <td>${short_content}</td>
							      <td>${membersSvc.getOneMem(wfreportVO.rep_memno).mem_alias }</td>
							      <td>${short_reason}</td>
							      <td>${adminisSvc.getOneAdminis(wfreportVO.rep_adminisno).adminis_name }</td>
							      <c:if test="${wfreportVO.rep_adminisno != null}">
								  	<td><font color=darkblue>已處理</font></td>
								  </c:if>
								  <c:if test="${wfreportVO.rep_adminisno == null}">
								  	<td><font color=red>未處理</font></td>
								  </c:if>
							      <td>
									<a href="<%=request.getContextPath()%>/wfreport/wfreport.do?action=getOne_For_Display&rep_no=${wfreportVO.rep_no}">
										<input type="submit"  class="btn btn-outline-secondary btn-sm" id="readMore" value="檢視">
									</a>
							      </td>
							    </tr>
							  </tbody>
							  </c:forEach>
							</table>
							<div id="page2">
								<%@ include file="page/page2.file" %>
							</div>
							</div>
							<!-- ReportList End -->
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
  <%@ include file="/back-end/logout.jsp" %> 

	<script src="<%=request.getContextPath()%>/utility/back-end/vendor/jquery/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/back-end/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Core plugin JavaScript-->
	<script src="<%=request.getContextPath()%>/utility/back-end/vendor/jquery-easing/jquery.easing.min.js"></script>
	<!-- Custom scripts for all pages-->
	<script src="<%=request.getContextPath()%>/utility/back-end/js/sb-admin-2.min.js"></script>
	<!-- Page level plugins -->
	<script src="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/jquery.dataTables.js"></script>
	<script src="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<!-- Page level custom scripts -->
	<script src="<%=request.getContextPath()%>/utility/back-end/js/demo/datatables-demo.js"></script>
</body>

</html>
