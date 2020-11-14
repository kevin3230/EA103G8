<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.adminis.model.*"%>

<%
	AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO");
%>
  
<!DOCTYPE html>
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
<link href="<%=request.getContextPath()%>/utility/back-end/css/sb-admin-2.css"
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
			<div class="container">
		        <div class="card o-hidden border-0 shadow-lg my-5">
		            <div class="card-body p-0">
		                <!-- Nested Row within Card Body -->
		                <div class="row">
		                    <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
		                    <div class="col-lg-7">
		                        <div class="p-5">
		                            <div class="text-center">
		                                <h1 class="h4 text-gray-900 mb-4">管理員註冊</h1>
		                            </div>
		                            
									<c:if test="${not empty errorMsgs}">
										<font style="color: red">請修正以下錯誤:</font>
										<ul>
											<c:forEach var="message" items="${errorMsgs}">
												<li style="color: red">${message}</li>
											</c:forEach>
										</ul>
									</c:if>                            
		                            <form class="user" ACTION="<%=request.getContextPath()%>/adminis/adminis.do" METHOD="post">
		                                <div class="form-group">
		                                    <input type="text" name="adminis_name" style="color: black"  class="form-control form-control-user"  placeholder="姓名"
		                               		 value="<%= (adminisVO == null)? "測試" : adminisVO.getAdminis_name()%>" />
		                                
		                                </div>
		                                <div  class="form-group">
		                                    <input type="email" name="adminis_email" style="color: black"  class="form-control form-control-user" id="exampleInputEmail" placeholder="Email Address"
		                            		value="<%= (adminisVO == null)? "EA103_99@gmail.com" : adminisVO.getAdminis_email()%>" />
		                                </div>
		                                
		                                <div class="form-group">
		                                    <input type="text"  name="adminis_dept" style="color: black"  class="form-control form-control-user"  placeholder="部門"
		                              		 value="<%= (adminisVO == null)?  "測試部" : adminisVO.getAdminis_dept()%>" />
		                                </div>
		                                
		      							 <div class="form-group">
		                                    <input type="hidden" id="PV" name="adminis_pv" style="color: black"  class="form-control form-control-user"  placeholder="權限值"
		                              		value="<%= (adminisVO == null)? "0" : adminisVO.getAdminis_pv()%>" />
		                               	 </div> 
		                               	                            
		                                <jsp:useBean id="authoritySvc" scope="page" class="com.authority.model.AuthorityService" />
										                <%! int i = 1; %>
		                                 <div class="form-group row p-4">
		                    
		                                   <c:forEach  var="authorityVO" items="${authoritySvc.all}" >
		                                   	<div class="custom-control custom-checkbox big col-sm-6 mb-4 ">
		                                    <input type="checkbox" name="test"  class="custom-control-input" id="${authorityVO.auth_no}" value="<%= i %>">
		                                    <label class="custom-control-label" style="color: black"  for="${authorityVO.auth_no}">${authorityVO.auth_name}</label>
		                                  	</div>  
		                                  	<% i*=2; %>                                 		
		                                   </c:forEach> 
		                                  </div>   
		                                        <% i=1; %>        
										<input type="hidden" name="action" value="insert"> 
										<input type="submit" value="送出新增" class="btn btn-primary btn-user btn-block">
		                          </form>
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
	<script src="<%=request.getContextPath()%>/utility/back-end/js/index-js.js"></script>
</body>

</html>
