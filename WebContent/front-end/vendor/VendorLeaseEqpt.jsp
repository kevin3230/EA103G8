<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>裝備租借管理</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/vendor/assets/Vendor.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/vendor/DataTables/datatables.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/vendor/DataTables/orderEqpt.css">
</head>
<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%>
	<!-- Header結束 -->
	<div class="container-fluid">
		<div class="row">
			<!-- sidebar放這邊 ，col設定為2 -->
			<%@ include file="/front-end/sidebar/vendorSidebar.jsp"%>
			<!-- sidebar放這邊 ，col設定為2 -->
			<div class="col-10">
				<!-- 以下內容為範例，請自行發揮 -->
				<div class="row justify-content-center text-center">
					<div class="col-10">
						<h1 class="ml1">租借裝備清單</h1>
						<table id="example" class="display" style="width: 100%">
							<thead>
								<tr>
									<th></th>
									<th>顧客姓名</th>
									<th>入住時間</th>
									<th>離開時間</th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
				<!-- 以上內容為範例，請自行發揮 -->
			</div>
		</div>
	</div>

	<!-- Footer開始 -->
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->
	<script type="text/javascript">
		var urls = "<%=request.getContextPath()%>/vendor/LeaseEqpt.do";
		var vd_no = "${vendorVO.vd_no}";
	</script>
	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/front-end/vendor/DataTables/datatables.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/front-end/vendor/DataTables/orderEqpt.js"></script>
</body>
</html>