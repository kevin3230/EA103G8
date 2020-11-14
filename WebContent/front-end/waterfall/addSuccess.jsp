<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="refresh" content ="3;url=<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp">
	
	<title>success</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<style>
	body{
	background-color:#F8F8FF;
	}
	#row{
	position:absolute;
	top:30%;
	left:45%;
	text-align:center;
	}
	</style>
</head>
<body onload="shownum()">
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%> 
	<!-- Header結束 -->
	
	<div class="container min-vh-100" >
		<div id="row">
			<div >
			<h2> 發文成功！</h2>
			</div>
			<div id="time" >
			</div>
			<div >
				<a href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp" >不想等待請點擊此</a>
			</div>
		</div>
	</div>
	

	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->

	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

	<script type="text/javascript"> 
	var i = 4; function shownum(){ 
	i=i-1; 
	document.getElementById("time").innerHTML=i+"秒後自動跳轉回列表";
	setTimeout('shownum()',1000); } 
	</script>
</body>
</html>