<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.food.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PLAMPING</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">

	<style>
		body {
        	background-color: #f2f2f2;
        	color: #444;
        }
        

	</style>
</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
		
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
	<div class="row">
		<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
	</div>
	</c:if>

	<div class="container-fluid min-vh-100">
		<div class="row min-vh-100">
		
			<!-- Navbar -->
			<%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
		
			<div class="col-8">
				
				<div class="mt-5 mb-3">
					<h3>業者專區</h3>
				</div>
					
  				<button class="btn btn-primary btn-lg btn-block" type="button" data-toggle="collapse" data-target="#multiCollapse1" aria-expanded="false" aria-controls="multiCollapse1">營區管理</button>
  					<div class="collapse multi-collapse p-3" id="multiCollapse1">

  						<form method="post"	action="<%=request.getContextPath()%>/camp/camp.do">
  							<div class="row m-1">
								<div class="col-sm-3">
									營位編號：
								</div>
								<div class="col-sm-6">
									<input type="text" name="camp_no" id="camp_no" placeholder="營位編號">
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOne_For_Display">
									<input type="submit" class="btn btn-outline-primary" value="送出">
								</div>
							</div>
						</form>

						<jsp:useBean id="campSvc" scope="page" class="com.camp.model.CampService" />
						<form method="post"	action="<%=request.getContextPath()%>/camp/camp.do">
							<div class="row m-1">
								<div class="col-sm-3">
									營位名稱：
								</div>
								<div class="col-sm-6">
									<select size="1" name="camp_no">
										<c:forEach var="campVO" items="${campSvc.getCampsByVdnoWithoutDeleted(\"V000000001\")}"> <!-- 先寫死 -->
											<option value="${campVO.camp_no}">${campVO.camp_name}
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOne_For_Display">
									<input type="submit" class="btn btn-outline-primary" value="送出">
								</div>
							</div>
						</form>

						<div class="row m-1">
							<a class="btn btn-outline-primary btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/camp/listAllCamp.jsp" role="button">查詢所有營位</a>
						</div>

						<div class="row m-1">
							<a class="btn btn-outline-primary btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/camp/addCamp.jsp" role="button">新增營位</a>
						</div>

					</div>					

				<button class="btn btn-success btn-lg btn-block" type="button" data-toggle="collapse" data-target="#multiCollapse2" aria-expanded="false" aria-controls="multiCollapse2">裝備管理</button>
  					<div class="collapse multi-collapse p-3" id="multiCollapse2">

  						<form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do">
							<div class="row m-1">
								<div class="col-sm-3">
									裝備編號：
								</div>
								<div class="col-sm-6">
									<input type="text" name="eqpt_no" id="eqpt_no" placeholder="裝備編號">
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOneEquipment_For_Display">
									<input type="submit" class="btn btn-outline-success" value="送出">
								</div>
							</div>
						</form>

						<jsp:useBean id="eqptSvc" scope="page" class="com.equipment.model.EquipmentService" />
						<form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do" >
							<div class="row m-1">
								<div class="col-sm-3">
									裝備名稱：
								</div>
								<div class="col-sm-6">
									<select size="1" name="eqpt_no">
         								<c:forEach var="equipmentVO" items="${eqptSvc.all}" > 
          									<option value="${equipmentVO.eqpt_no}">${equipmentVO.eqpt_name}
		         						</c:forEach>   
       								</select>
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOneEquipment_For_Display">
       								<input type="submit" class="btn btn-outline-success" value="送出">
								</div>
							</div>
	    		 		</form>

	    		 		<div class="row m-1">
							<a class="btn btn-outline-success btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/equipment/listAllEquipment.jsp" role="button">查詢所有裝備</a>
						</div>

						<div class="row m-1">
							<a class="btn btn-outline-success btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/equipment/addEquipment.jsp" role="button">新增裝備</a>
						</div>

  					</div>

				<button class="btn btn-info btn-lg btn-block" type="button" data-toggle="collapse" data-target="#multiCollapse3" aria-expanded="false" aria-controls="multiCollapse3">食材管理</button>
					<div class="collapse multi-collapse p-3" id="multiCollapse3">

						<form method="post" action="<%=request.getContextPath()%>/food/food.do">
							<div class="row m-1">
								<div class="col-sm-3">
									食材編號：
								</div>
								<div class="col-sm-6">
									<input type="text" name="food_no" id="food_no" placeholder="食材編號">
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOneFood_For_Display">
									<input type="submit" class="btn btn-outline-info" value="送出">
								</div>
							</div>
						</form>
						
						<jsp:useBean id="foodSvc" scope="page" class="com.food.model.FoodService" />
						<form method="post" action="<%=request.getContextPath()%>/food/food.do" >
							<div class="row m-1">
								<div class="col-sm-3">
									食材名稱：
								</div>
								<div class="col-sm-6">
									<select size="1" name="food_no">
         								<c:forEach var="foodVO" items="${foodSvc.all}" > 
		          							<option value="${foodVO.food_no}">${foodVO.food_name}
        		 						</c:forEach>   
       								</select>
								</div>
								<div class="col-sm-3">
									<input type="hidden" name="action" value="getOneFood_For_Display">
       								<input type="submit" class="btn btn-outline-info" value="送出">
								</div>
							</div>
			     		</form>

			     		<div class="row m-1">
							<a class="btn btn-outline-info btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/food/listAllFood.jsp" role="button">查詢所有食材</a>
						</div>

						<div class="row m-1">
							<a class="btn btn-outline-info btn-lg btn-block" href="<%=request.getContextPath()%>/front-end/food/addFood.jsp" role="button">新增食材</a>
						</div>

					</div>

			</div>

		</div>
	</div>

	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->

	<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
</body>
</html>