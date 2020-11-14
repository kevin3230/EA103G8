<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>
<%@ page import="com.wfreport.model.*"%>

<%
	WFReportVO wfreportVO = (WFReportVO) request.getAttribute("wfreportVO"); 
%>
<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="waterfallSvc" scope="page" class="com.waterfall.model.WaterfallService" />
<jsp:useBean id="wfreplySvc" scope="page" class="com.waterfall.model.WFReplyService" />
<jsp:useBean id="adminisSvc" scope="page" class="com.adminis.model.AdminisService" />

<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>Report</title>
<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath()%>/utility/back-end/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/utility/back-end/css/sb-admin-2.min.css" rel="stylesheet">
<!-- Custom styles for this page -->
<link href="<%=request.getContextPath()%>/utility/back-end/vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
<style type="text/css">
#one{
width: 80%;
margin: 20 auto;
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
		                	<div class="col">
		                	<%-- 錯誤表列 --%>
							<c:if test="${not empty errorMsgs}">
								<font style="color:red">請修正以下錯誤:</font>
								<ul>
									<c:forEach var="message" items="${errorMsgs}">
										<li style="color:red">${message}</li>
									</c:forEach>
								</ul>
							</c:if>
							<a href="<%=request.getContextPath()%>/back-end/wfreport/listAllWfreport.jsp">
								<input type="submit"  class="btn btn-outline-secondary btn-sm" style="margin:10 30;" value="回前頁">
							</a>
							<!-- ReportOne -->
							<div id="one">
							<table class="table table-bordered">
							  <thead>
							    <tr>
							      <th scope="col">檢舉編號</th>
							      <td>${wfreportVO.rep_no}</td>
							    </tr>
							    <tr>
							      <th scope="col">被檢舉文章標題</th>
							      <td>${waterfallSvc.getOneWaterfall(wfreportVO.rep_wfno).wf_title }</td>
							    </tr>
							    <tr>      
							      <th scope="col">被檢舉回覆內容</th>
							      <td>${wfreplySvc.getOneWFReply(wfreportVO.rep_wfrno).wfr_content }</td>
							    </tr>
							    <tr>      
							      <th scope="col">檢舉會員</th>
							      <td>${membersSvc.getOneMem(wfreportVO.rep_memno).mem_alias }</td>
							    </tr>
							    <tr>
							      <th scope="col">檢舉原因</th>
							      <td>${wfreportVO.rep_content}</td>
							    </tr>
							    <tr>
							      <th scope="col">管理員</th>
							      <td>${adminisSvc.getOneAdminis(wfreportVO.rep_adminisno).adminis_name }</td> 
							      
							    </tr>
							    <tr>      
							      <th scope="col">狀態</th>
							      <c:if test="${wfreportVO.rep_stat ==1}">
								  	<td>通過</td>
								  </c:if>
								  <c:if test="${wfreportVO.rep_stat ==0 and not empty wfreportVO.rep_adminisno }">
								  	<td>不通過</td>
								  </c:if>
								  <c:if test="${wfreportVO.rep_stat ==0 and empty wfreportVO.rep_adminisno}">
								  	<td>未處理</td>
								  </c:if>
							    </tr>
							    <tr>
								    <td colspan="2">
									    <FORM METHOD="post"ACTION="<%=request.getContextPath()%>/wfreport/wfreport.do">
										<input type="hidden"name="rep_no" value="${wfreportVO.rep_no}">
										<input type="hidden"name="rep_adminisno" value="${adminis_no}">
										<input type="hidden"name="rep_wfno" value="${wfreportVO.rep_wfno}">
										<input type="hidden"name="rep_wfrno" value="${wfreportVO.rep_wfrno}">
										<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
										<input type="hidden" name="action" value="getOne_For_Update_Pass">
										<div class="row justify-content-center">
										<input type="submit"  class="btn btn-outline-secondary btn-sm" id="readMore" value="同意檢舉">
										</div>
										</FORM>
									
									    <FORM METHOD="post"ACTION="<%=request.getContextPath()%>/wfreport/wfreport.do">
										<input type="hidden"name="rep_no" value="${wfreportVO.rep_no}">
										<input type="hidden"name="rep_adminisno" value="${adminis_no}">
										<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
										<input type="hidden" name="action" value="getOne_For_Update_Unpass">
										<div class="row justify-content-center">
										<input type="submit"  class="btn btn-outline-secondary btn-sm" id="readMore" value="不同意檢舉">
										</div>
										</FORM>	    
								    </td>
							    </tr>
							  </thead>
							</table>
							</div>
							<!-- ReportOne End -->
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
