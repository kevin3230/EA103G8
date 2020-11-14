<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="campTypeSvc" scope="page" class="com.camptype.model.CampTypeService"/>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>搜尋露營區</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/search/css/search.css">
</head>
<body>

	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->

	<div id="search" class="container-fluid">
		<div class="row">
			<div class="col-2 px-0 offset-1">
				<div id="filter">
					<div>
						<h4 class="font-weight-bold">露營區名稱</h4>
						<div class="input">
							<input type="text" placeholder="請輸入露營區名稱" id="campName">
						</div>
					</div>
					<hr>
					<div id="datePicker">
						<div class="d-flex justify-content-between pr-2">
							<h4 class="font-weight-bold">日期</h4>
							<ul class="p-0">
								<li class="cancel">清除日期</li>
							</ul>
						</div>
						<div class="input">
							<span>預計入住日期：</span><br>
							<input type="date" class="mb-3" id="campStart"><br>
							<span>預計離開日期：</span><br>
							<input type="date" id="campEnd">
						</div>
					</div>
					<hr>
					<div id="areaSelector">
						<div class="d-flex justify-content-between pr-2">
							<h4 class="font-weight-bold">地區</h4>
							<ul class="p-0">
								<li class="selectAll">全選</li>
								<li class="cancelAll">全取消</li>
							</ul>
						</div>
						<ul>
							<li id="northern">北部</li>
							<li id="central">中部</li>
							<li id="southern">南部</li>
							<li id="eastern">東部</li>
						</ul>
						<ul id="campArea">
							<li>台北</li>
							<li>新北</li>
							<li>桃園</li>
							<li>新竹</li>
							<li>苗栗</li>
							<li>台中</li>
							<li>南投</li>
							<li>雲林</li>
							<li>嘉義</li>
							<li>台南</li>
							<li>高雄</li>
							<li>屏東</li>
							<li>宜蘭</li>
							<li>花蓮</li>
							<li>台東</li>
						</ul>
					</div>
					<hr>
					<div id="typeSelector">
						<div class="d-flex justify-content-between pr-2">
							<h4 class="font-weight-bold">類型</h4>
							<ul class="p-0">
								<li class="selectAll">全選</li>
								<li class="cancelAll">全取消</li>
							</ul>
						</div>
						<ul id="campType">
							<c:forEach var="campTypeVO" items="${campTypeSvc.all}">
								<li value="${campTypeVO.ct_no}">${campTypeVO.ct_name}</li>
							</c:forEach>
						</ul>
					</div>
					<hr>
					<div id="promoSelector">
						<h4 class="font-weight-bold">促銷方案</h4>
						<ul id="campPromo">
							<li value="1">有促銷方案</li>
						</ul>
					</div>
					<hr>
					<div>
						<h4 class="font-weight-bold">價格</h4>
						<div id="priceFilter" class="input">
							<input type="text" placeholder="最低價" id="campMinPrice">
							至
							<input type="text" placeholder="最高價" id="campMaxPrice">
						</div>
					</div>
					<hr>
				</div>
			</div>

			<div class="col-8 py-3 ml-5">

				<!-- pageSelector開始 -->
				<%@ include file="/front-end/search/pageSelector.jsp" %>
				<!-- pageSelector開始 -->

				<div id="resultList" class="mt-2">
					<table class="table table-hover">
						<tbody>
						</tbody>
					</table>
				</div>

				<!-- pageSelector開始 -->
				<%@ include file="/front-end/search/pageSelector.jsp" %>
				<!-- pageSelector開始 -->

			</div>

			<div class="col-9 d-none">
				<div id="resultBlock" class="pt-3">
					<div class="card">
						<div class="img-container">
							<img src="http://placehold.jp/1920x750.png" class="card-img-top">
						</div>
						<div class="card-body">
							<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						</div>
					</div>
					<div class="card">
						<div class="img-container">
							<img src="http://placehold.jp/1920x750.png" class="card-img-top">
						</div>
						<div class="card-body">
							<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						</div>
					</div>
					<div class="card">
						<div class="img-container">
							<img src="http://placehold.jp/1920x750.png" class="card-img-top">
						</div>
						<div class="card-body">
							<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						</div>
					</div>
					<div class="card">
						<div class="img-container">
							<img src="http://placehold.jp/1920x750.png" class="card-img-top">
						</div>
						<div class="card-body">
							<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						</div>
					</div>
					<div class="card">
						<div class="img-container">
							<img src="http://placehold.jp/1920x750.png" class="card-img-top">
						</div>
						<div class="card-body">
							<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Footer開始 -->	
<%-- 	<%@ include file="/front-end/index/footer.jsp" %> --%>
	<!-- Footer結束 -->

	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/search/js/pageSelector.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/search/js/search.js"></script>
	<%@ include file="/front-end/index/Notice.jsp"%>
	<script>
		var contextPath = "<%=request.getContextPath()%>";
	</script>
</body>
</html>