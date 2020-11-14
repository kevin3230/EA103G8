<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Object JavaBean -->
<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="campSvc" scope="page" class="com.camp.model.CampService" />
<jsp:useBean id="eqptSvc" scope="page" class="com.equipment.model.EquipmentService" />
<jsp:useBean id="foodSvc" scope="page" class="com.food.model.FoodService" />
<!-- Order JavaBean -->
<jsp:useBean id="omSvc" scope="page" class="com.ordermaster.model.OrderMasterService" />
<jsp:useBean id="ocSvc" scope="page" class="com.ordercamp.model.OrderCampService" />
<jsp:useBean id="oeSvc" scope="page" class="com.ordereqpt.model.OrderEqptService" />
<jsp:useBean id="ofSvc" scope="page" class="com.orderfood.model.OrderFoodService" />

<%--
request.setAttribute("om_no", "O000000016");
--%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PLAMPLING</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/carcamp/css/reservedNav.css">
	<style type="text/css">
		div.orderData{
			margin-bottom: 30px;
		}
		div.order_item:hover{
			background-color: #D0D3D4;
		}
	</style>
</head>
<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
	<!-- 預約導覽列開始 -->
	<%@ include file="/front-end/carcamp/reservedNav.jsp" %>
	<!-- 預約導覽列結束 -->
	
	<%@ include file="/front-end/index/Notice.jsp"%>
	
	<div class="container">
		<div class="orderData" id="orderMaster">
			<h3>訂單詳情</h3>
			<div class="row border-bottom order_item">
				<div class="col-4 h6 mt-2">訂單編號</div>
				<div class="col mt-2">${om_no}</div>
			</div>
			<div class="row border-bottom order_item">
				<div class="col-4 h6">會員姓名</div>
				<div class="col">${memSvc.getOneMem(omSvc.getOneOrderMaster(om_no).om_memno).mem_name}</div>
			</div>
			<div class="row border-bottom order_item">
				<div class="col-4 h6">訂單金額</div>
				<div class="col">${omSvc.getOneOrderMaster(om_no).om_txnamt}</div>
			</div>
			<div class="row border-bottom order_item">
				<div class="col-4 h6">成立時間</div>
				<div class="col">${omSvc.getOneOrderMaster(om_no).om_estbl}</div>
			</div>			
			<div class="row order_item">
				<div class="col-4 h6">訂單狀態</div>
				<c:choose>
					<c:when test="${omSvc.getOneOrderMaster(om_no).om_stat == 0}">
						<div class="col">已取消</div>
					</c:when>
					<c:when test="${omSvc.getOneOrderMaster(om_no).om_stat == 1}">
						<div class="col">未付款</div>
					</c:when>
					<c:when test="${omSvc.getOneOrderMaster(om_no).om_stat == 2}">
						<div class="col">已付款</div>
					</c:when>
				</c:choose>
			</div>
		</div>
		<div class="orderData" id="orderCamp">
			<h3>營位明細</h3>
			<div class="row">
				<div class="col-3 h5 mt-2">營位名稱</div>
				<div class="col-2 h5 mt-2">單價</div>
				<div class="col-2 h5 mt-2">數量</div>
				<div class="col h5 mt-2">開始日期</div>
				<div class="col h5 mt-2">結束日期</div>
			</div>
			<c:forEach var="ocVO" items="${ocSvc.getOrderCampsByOmno(om_no)}">
			<c:if test="${ocVO.oc_stat == 1}">
			<div class="row border-top order_item">
				<div class="col-3">${campSvc.getOneCamp(ocVO.oc_campno).camp_name}</div>
				<div class="col-2">${ocVO.oc_price}</div>
				<div class="col-2">${ocVO.oc_qty}</div>
				<div class="col">${ocVO.oc_start}</div>
				<div class="col">${ocVO.oc_end}</div>
			</div>
			</c:if>
			</c:forEach>
		</div>
		<div class="orderData" id="orderEqpt">
			<h3>裝備明細</h3>
			<div class="row">
				<div class="col-3 h5 mt-2">裝備名稱</div>				
				<div class="col-2 h5 mt-2">單價</div>
				<div class="col-2 h5 mt-2">數量</div>
				<div class="col h5 mt-2">開始日期</div>
				<div class="col h5 mt-2">結束日期</div>
			</div>
			<c:forEach var="oeVO" items="${oeSvc.getOrderEqptsByOmno(om_no)}">
			<c:if test="${oeVO.oe_stat == 1}">
			<div class="row border-top order_item">
				<div class="col-3">${eqptSvc.getOneEquipment(oeVO.oe_eqptno).eqpt_name}</div>				
				<div class="col-2">${oeVO.oe_price}</div>
				<div class="col-2">${oeVO.oe_qty}</div>
				<div class="col">${oeVO.oe_expget}</div>
				<div class="col">${oeVO.oe_expback}</div>
			</div>
			</c:if>
			</c:forEach>
		</div>
		<div class="orderData" id="orderFood">
			<h3>食材明細</h3>
			<div class="row">
				<div class="col-3 h5 mt-2">食材名稱</div>
				<div class="col-2 h5 mt-2">單價</div>
				<div class="col-2 h5 mt-2">數量</div>
			</div>
			<c:forEach var="ofVO" items="${ofSvc.getOrderFoodsByOmno(om_no)}">
			<c:if test="${ofVO.of_stat == 1}">
			<div class="row border-top order_item">
				<div class="col-3">${foodSvc.getOneFood(ofVO.of_foodno).food_name}</div>
				<div class="col-2">${ofVO.of_price}</div>
				<div class="col-2">${ofVO.of_qty}</div>
			</div>
			</c:if>
			</c:forEach>
		</div>
		<div class="row mx-auto mt-3 mb-5">
			<a href="<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp" class="btn btn-primary mx-auto">至訂單瀏覽</a>
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
<script type="text/javascript">
	// Reserve navbar
	$("#step5").addClass("here");
	
</script>
</html>