<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*" %>
<%@ page import="com.carcamp.model.*, com.careqpt.model.*, com.carfood.model.*" %>
<%@ page import="com.ordermaster.model.*" %>
<%@ page import="com.members.model.*" %>

<!-- Object JavaBean -->
<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="campSvc" scope="page" class="com.camp.model.CampService" />
<jsp:useBean id="eqptSvc" scope="page" class="com.equipment.model.EquipmentService" />
<jsp:useBean id="foodSvc" scope="page" class="com.food.model.FoodService" />
<!-- ShoppingCart JavaBean -->
<jsp:useBean id="ccSvc" scope="page" class="com.carcamp.model.CarCampService" />
<jsp:useBean id="ceSvc" scope="page" class="com.careqpt.model.CarEqptService" />
<jsp:useBean id="cfSvc" scope="page" class="com.carfood.model.CarFoodService" />
<!-- Camp, Equipment available quantity by date JavaBean -->
<jsp:useBean id="caSvc" scope="page" class="com.campavail.model.CampAvailService" />
<jsp:useBean id="eaSvc" scope="page" class="com.eqptavail.model.EqptAvailService" />
<!-- Promotion JavaBean -->
<jsp:useBean id="proSvc" scope="page" class="com.promotion.model.PromotionService" />
<jsp:useBean id="pcSvc" scope="page" class="com.promocamp.model.PromoCampService" />
<jsp:useBean id="peSvc" scope="page" class="com.promoeqpt.model.PromoEqptService" />
<jsp:useBean id="pfSvc" scope="page" class="com.promofood.model.PromoFoodService" />
<!-- OrderMaster JavaBean -->
<jsp:useBean id="omSvc" scope="page" class="com.ordermaster.model.OrderMasterService" />

<!DOCTYPE html>

<%--
//General fake data
int listprice = 1000;
int price = 800;
java.sql.Date start = java.sql.Date.valueOf("2020-10-04");
java.sql.Date end = java.sql.Date.valueOf("2020-10-06");

// OrderMaster fake data
String om_no = "O000000001";
OrderMasterService omSvc0 = new OrderMasterService();
OrderMasterVO omVO = omSvc0.getOneOrderMaster(om_no);
session.setAttribute("orderMasterVO", omVO);

// CarCamp fake data
List<CarCampVO> ccVOList = new ArrayList<CarCampVO>();
ccVOList.add(new CarCampVO("C000000001", "M000000001", 4, start, end));
ccVOList.add(new CarCampVO("C000000002", "M000000001", 5, start, end));

// CarEqpt fake data
List<CarEqptVO> ceVOList = new ArrayList<CarEqptVO>();
ceVOList.add(new CarEqptVO("E000000001", "M000000001", 4, start, end));
ceVOList.add(new CarEqptVO("E000000002", "M000000001", 5, start, end));

// CarFood fake data
List<CarFoodVO> cfVOList = new ArrayList<CarFoodVO>();
cfVOList.add(new CarFoodVO("F000000004", "M000000001", 4));
cfVOList.add(new CarFoodVO("F000000005", "M000000001", 5));


// Set OrderItems List to session
session.setAttribute("ccVOList", ccVOList);
session.setAttribute("ceVOList", ceVOList);
session.setAttribute("cfVOList", cfVOList);

// Redirect to OrderMasterServlet
res.sendRedirect(request.getContextPath() + "/carorder/OrderMasterServlet?om_no=O000000001");

--%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PLAMPING&nbsp;Shopping Cart</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/carcamp/css/reservedNav.css">
	<style type="text/css">
		div#CarOrder{
			min-height: 660px;
		}
		div > h3{
			display: inline;
			cursor: pointer;
		}
		div.price > b{
			color: red;
		}
		div.order_item:hover{
			background-color: #D0D3D4;
		}
		i.fa-edit{
			cursor: pointer;
		}
		div#orderAmount{
			font-weight: bold;
		}
		.transparent {
		    opacity: 0.2;
		}
		div.nullItem{
			color: gray;
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

	<div class="container" id="CarOrder">
		<div>
			<c:if test="${empty param.om_no}">
				<a href="<%=request.getContextPath()%>/front-end/carfood/reserveFood.jsp" class="btn btn-link">回訂購食材</a>
			</c:if>
			<c:if test="${not empty param.om_no}">
				<a href="<%=request.getContextPath()%>/front-end/carfood/reserveFood.jsp?om_no=${param.om_no}&action=editOrder" class="btn btn-link">回訂購食材</a>
			</c:if>
		</div>
		<div class="orderItem" id="order_camp">
			<div class="mb-4 mt-3">
				<i class="fas fa-minus"></i>
				<h3 class="orderItemTitle" data-toggle="collapse" data-target="#order_camp_content">預約營位</h3>
			</div>
			<div class="orderItemContent collapse show" id="order_camp_content">
				<div class="row order_title">
					<div class="col-1"></div>
					<div class="col-3 h5">營位名稱</div>
					<div class="col-2 h5">預約開始日期</div>
					<div class="col-2 h5">預約結束日期</div>
					<div class="col-1 h6">訂購單價</div>
					<div class="col-1 h6">預約數量</div>
					<div class="col-1 h6">預約天數</div>
					<div class="col-1 h6">金額小計</div>
				</div>
				<c:choose>
					<c:when test="${ocVOList.size()==0}">
						<div class="row pb-4 pt-4 border-top justify-content-center nullItem">
							<h5>尚無任何營位</h5>
						</div>
					</c:when>
					<c:when test="${ocVOList.size()!=0}">
						<c:forEach var="orderCampVO" items="${ocVOList}" varStatus="i">
						<div class="row pb-2 pt-2 border-top order_item">
							<div class="col-1">${i.count}</div>
							<div class="col-3">
								${campSvc.getOneCamp(orderCampVO.oc_campno).getCamp_name()}
							</div>
							<div class="col-2 start_date">${orderCampVO.oc_start}</div>
							<div class="col-2 end_date">${orderCampVO.oc_end}</div>
							<div class="col-1 price">
								<c:if test="${not empty pcSvc.getActiveByPc_campno(orderCampVO.oc_campno)}">
									<del>${campSvc.getOneCamp(orderCampVO.oc_campno).getCamp_price()}</del><br><b>${pcSvc.getActiveMinPriceByPc_campno(orderCampVO.oc_campno).getPc_price()}</b>
<%-- 									<b>${pcSvc.getActiveMinPriceByPc_campno(orderCampVO.oc_campno).getPc_price()}</b> --%>
								</c:if>
								<c:if test="${empty pcSvc.getActiveByPc_campno(orderCampVO.oc_campno)}">
									${campSvc.getOneCamp(orderCampVO.oc_campno).getCamp_price()}
								</c:if>
							</div>
							<c:choose>
								<c:when test="${orderCampVO.oc_qty == -999}">
									<div class="col-1 text-center quantity" style="font-weight: bold; color: red;">已下架</div>
								</c:when>
								<c:when test="${orderCampVO.oc_qty < 0}">
									<div class="col-1 text-center quantity" style="font-weight: bold; color: red;">已滿位</div>
								</c:when>
								<c:otherwise>
									<div class="col-1 text-center quantity">${orderCampVO.oc_qty}</div>
								</c:otherwise>
							</c:choose>
							<div class="col-1 text-center days"></div>
							<div class="col-1 amount"></div>
						</div>
						</c:forEach>
					</c:when>
				</c:choose>
				
			</div>
		</div>
		<div class="orderItem" id="order_eqpt">
			<div class="mb-4 mt-3">
				<i class="fas fa-minus"></i>
				<h3 class="orderItemTitle" data-toggle="collapse" data-target="#order_eqpt_content">租借裝備</h3>
			</div>
			<div class="orderItemContent collapse show" id="order_eqpt_content">
				<div class="row order_title">
					<div class="col-1"></div>
					<div class="col-3 h5">裝備名稱</div>
					<div class="col-2 h5">預約開始日期</div>
					<div class="col-2 h5">預約結束日期</div>
					<div class="col-1 h6">訂購單價</div>
					<div class="col-1 h6">預約數量</div>
					<div class="col-1 h6">預約天數</div>
					<div class="col-1 h6">金額小計</div>
				</div>
				
				<c:choose>
					<c:when test="${oeVOList.size()==0}">
						<div class="row pb-4 pt-4 border-top justify-content-center nullItem">
							<h5>尚無任何裝備</h5>
						</div>
					</c:when>
					<c:when test="${oeVOList.size()!=0}">
						<c:forEach var="orderEqptVO" items="${oeVOList}" varStatus="i">
						<div class="row pb-2 pt-2 border-top order_item">
							<div class="col-1">${i.count}</div>
							<div class="col-3">${eqptSvc.getOneEquipment(orderEqptVO.oe_eqptno).getEqpt_name()}</div>
							<div class="col-2 start_date">${orderEqptVO.oe_expget}</div>
							<div class="col-2 end_date">${orderEqptVO.oe_expback}</div>
							<div class="col-1 price">
								<c:if test="${not empty peSvc.getActiveByPe_eqptno(orderEqptVO.oe_eqptno)}">
									<del>${eqptSvc.getOneEquipment(orderEqptVO.oe_eqptno).getEqpt_price()}</del><br><b>${peSvc.getActiveMinPriceByPe_eqptno(orderEqptVO.oe_eqptno).getPe_price()}</b>
<%-- 									<b>${peSvc.getActiveMinPriceByPe_eqptno(orderEqptVO.oe_eqptno).getPe_price()}</b> --%>
								</c:if>
								<c:if test="${empty peSvc.getActiveByPe_eqptno(orderEqptVO.oe_eqptno)}">
									${eqptSvc.getOneEquipment(orderEqptVO.oe_eqptno).getEqpt_price()}
								</c:if>
							</div>
							<c:choose>
								<c:when test="${orderEqptVO.oe_qty == -999}">
									<div class="col-1 text-center quantity" style="font-weight: bold; color: red;">已下架</div>
								</c:when>
								<c:when test="${orderEqptVO.oe_qty < 0}">
									<div class="col-1 text-center quantity" style="font-weight: bold; color: red;">已租罄</div>
								</c:when>
								<c:otherwise>
									<div class="col-1 text-center quantity">${orderEqptVO.oe_qty}</div>
								</c:otherwise>
							</c:choose>
							<div class="col-1 text-center days"></div>
							<div class="col-1 amount"></div>
						</div>
						</c:forEach>
					</c:when>
				</c:choose>
				
			</div>
		</div>
		<div class="orderItem" id="order_food">
			<div class="mb-4 mt-3">
				<i class="fas fa-minus"></i>
				<h3 class="orderItemTitle" data-toggle="collapse" data-target="#order_food_content">訂購食材</h3>
			</div>
			<div class="orderItemContent collapse show" id="order_food_content">
				<div class="row order_title">
					<div class="col-1"></div>
					<div class="col-7 h5">食材名稱</div>
					<div class="col-1 h6">訂購單價</div>
					<div class="col-1 h6">訂購數量</div>
					<div class="col-1"></div>
					<div class="col-1 h6">金額小計</div>
				</div>
				
				<c:choose>
					<c:when test="${ofVOList.size()==0}">
						<div class="row pb-4 pt-4 border-top justify-content-center nullItem">
							<h5>尚無任何食材</h5>
						</div>
					</c:when>
					<c:when test="${ofVOList.size()!=0}">
		                <c:forEach var="orderFoodVO" items="${ofVOList}" varStatus="i">
		                <div class="row pb-2 pt-2 border-top order_item">
		                    <div class="col-1">${i.count}</div>
		                    <div class="col-7">${foodSvc.getOneFood(orderFoodVO.of_foodno).getFood_name()}</div>
		                    <div class="col-1 price">
		                        <c:if test="${not empty pfSvc.getActiveByPf_foodno(orderFoodVO.of_foodno)}">
		                            <del>${foodSvc.getOneFood(orderFoodVO.of_foodno).getFood_price()}</del><br><b>${pfSvc.getActiveMinPriceByPf_foodno(orderFoodVO.of_foodno).getPf_price()}</b>
<%-- 		                            <b>${pfSvc.getActiveMinPriceByPf_foodno(orderFoodVO.of_foodno).getPf_price()}</b> --%>
		                        </c:if>
		                        <c:if test="${empty pfSvc.getActiveByPf_foodno(orderFoodVO.of_foodno)}">
		                            ${foodSvc.getOneFood(orderFoodVO.of_foodno).getFood_price()}
		                        </c:if>
		                    </div>
		                    <c:choose>
								<c:when test="${orderFoodVO.of_qty == -999}">
									<div class="col-1 text-center quantity" style="font-weight: bold; color: red;">已下架</div>
								</c:when>
								<c:otherwise>
									<div class="col-1 text-center quantity">${orderFoodVO.of_qty}</div>
								</c:otherwise>
							</c:choose>
							<div class="col-1"></div>
		                    <div class="col-1 amount"></div>
		                </div>
		                </c:forEach>
		            </c:when>
		        </c:choose>
			</div>
		</div>
		<div class="mt-4" id="amount_calculate">
			<div class="row">
				<div class="col-11"></div>
				<div class="col-1 h6">當前加總</div>
			</div>
			<div class="row">
				<div class="col-11"></div>
				<div class="col-1 totalAmount" id="visibleAmount"></div>
			</div>
		</div>
		<br>
		<br>
		<br>
		<br>
		<br>
		<div id="order_checkout">
			<h3>帳款資訊</h3>
			<div class="mb-3 mt-3" id="order_checkout_content">
				<div class="row mb-2">
					<div class="col-2">預約營位</div>
					<div class="col-1"></div>
					<div class="col-1 text-right oriAmount" id="campOriAmount"></div>
				</div>
				<div class="row mb-2">
					<div class="col-2">租借裝備</div>
					<div class="col-1"></div>
					<div class="col-1 text-right oriAmount" id="eqptOriAmount"></div>
				</div>
				<div class="row mb-2">
					<div class="col-2">訂購食材</div>
					<div class="col-1 text-right"><i class="fas fa-plus"></i></div>
					<div class="col-1 text-right oriAmount" id="foodOriAmount"></div>
				</div>
				<div class="row border-top mb-2">
					<div class="col-2">原價總金額</div>
					<div class="col-1"></div>
					<div class="col-1 text-right oriAmount" id="totalOriAmount"></div>
				</div>
				<div class="row mb-2">
					<div class="col-2">折扣</div>
					<div class="col-1 text-right"><i class="fas fa-minus"></i></div>
					<div class="col-1 text-right" id="totalDiscount"></div>
				</div>
				<div class="row border-top mb-2">
					<div class="col-2">結帳總金額</div>
					<div class="col-1"></div>
					<div class="col-1 text-right totalAmount" id="orderAmount"></div>
				</div>
			</div>
		</div>
		<div class="mb-3 mt-3">
			<button class="btn btn-primary" data-toggle="modal" data-target="#creditCardForm" id="creditCardCheckoutBtn">信用卡結帳</button>
		</div>
	</div>
	
	<!-- Credit card information form -->
	<form method="POST" class="needs-validation" novalidate action="<%=request.getContextPath()%>/carorder/OrderMasterServlet">
		<input type="hidden" name="action" value="creditCard">
		<c:if test="${not empty param.om_no}">
			<input type="hidden" name="om_no" value="${param.om_no}">
		</c:if>
	<div class="modal fade" id="creditCardForm">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="creditCardFormTitle">信用卡結帳</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
			    <div class="form-group owner">
			        <label for="owner">持有人</label>
			        <input type="text" class="form-control" id="owner" required minlength="3">
			        <div class="invalid-feedback">
			        	請輸入正確的持有人
			        </div>
			    </div>
			    <div class="form-group CVV">
			        <label for="cvv">安全碼</label>
			        <input type="text" class="form-control" id="cvv" required>
			        <div class="invalid-feedback">
			        	請輸入正確的安全碼
			        </div>
			    </div>
			    <div class="form-group" id="card-number-field">
			        <label for="cardNumber">信用卡號碼</label>
			        <input type="text" class="form-control" id="cardNumber" name="om_cardno" required>
			        <div class="invalid-feedback">
			        	請輸入正確的信用卡號碼
			        </div>
			    </div>
			    <div class="form-group" id="expiration-date">
			        <label>到期日</label>
			        <select required>
			            <option value="01">01</option>
			            <option value="02">02 </option>
			            <option value="03">03</option>
			            <option value="04">04</option>
			            <option value="05">05</option>
			            <option value="06">06</option>
			            <option value="07">07</option>
			            <option value="08">08</option>
			            <option value="09">09</option>
			            <option value="10">10</option>
			            <option value="11">11</option>
			            <option value="12">12</option>
			        </select>
			        <select required>
			            <option value="2020"> 2020</option>
			            <option value="2021"> 2021</option>
			            <option value="2022"> 2022</option>
			            <option value="2023"> 2023</option>
			            <option value="2024"> 2024</option>
			            <option value="2025"> 2025</option>
			        </select>
			    </div>
			    <div class="form-group" id="credit_cards">
			        <img src="<%=request.getContextPath() %>/front-end/carorder/images/visa.jpg" id="visa">
			        <img src="<%=request.getContextPath() %>/front-end/carorder/images/mastercard.jpg" id="mastercard">
			        <img src="<%=request.getContextPath() %>/front-end/carorder/images/amex.jpg" id="amex">
			    </div>
	      </div>
	      <div class="modal-footer">
	        <button type="submit" class="btn btn-primary" id="confirm-purchase">確認結帳</button>
	        <button type="button" class="btn btn-danger" data-dismiss="modal" id="cancel-purchase">取消</button>
	        <button type="button" id="autoComplete">自動填入</button>
	      </div>
	    </div>
	  </div>
	</div>
	</form>

	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->

	<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
</body>
<script src="js/jquery.payform.min.js" charset="utf-8"></script>
<script src="js/script.js"></script>
<script type="text/javascript">
	// Change order navbar current step style.
	$("#step4").addClass("here");
	
	// Order item title onclick behavior.(1.collapse icon 2.total amount)
	var item_collapse_status = [1,1,1];
	document.querySelectorAll("h3.orderItemTitle").forEach(function(title){
		title.onclick = function(){
			var orderItemContent = this.parentElement.nextElementSibling;
			// 1.collapse icon
			if(orderItemContent.className.includes("show")){
				this.previousElementSibling.setAttribute("class", "fas fa-plus");
			}else{
				this.previousElementSibling.setAttribute("class", "fas fa-minus");
			}
			// 2.total amount
			var currentContentId = orderItemContent.id;
			var currentTotalAmount = 0;
			document.querySelectorAll("div.orderItemContent").forEach(function(item, index){
				if(item.className.includes("show") && item.id === currentContentId){
					currentTotalAmount += 0;
				}else if(item.className.includes("show") && item.id !== currentContentId){
					item.querySelectorAll("div.amount").forEach(function(itemAmountDiv){
						if(isNaN(parseInt(itemAmountDiv.innerText))){
							currentTotalAmount += 0;	
						}else{
							currentTotalAmount += parseInt(itemAmountDiv.innerText);
						}
						
					});
				}else if(!item.className.includes("show") && item.id === currentContentId){
					item.querySelectorAll("div.amount").forEach(function(itemAmountDiv){
						if(isNaN(parseInt(itemAmountDiv.innerText))){
							currentTotalAmount += 0;
						}else{
							currentTotalAmount += parseInt(itemAmountDiv.innerText);	
						}
					});
				}
			});
			document.getElementById("visibleAmount").innerText = currentTotalAmount;
		}
	});
	
	// Calculate item amount =  price * quantity * days
	document.querySelectorAll("div.order_item").forEach(function(orderItem){
		var priceDiv = orderItem.querySelector("div.price");
		var price = null;
		// Determine if there are in promotion price or not.
		if(priceDiv.querySelector("b") != null){
			price = parseInt(priceDiv.querySelector("b").innerText);
		}else{
			price = parseInt(priceDiv.innerText);
		}
		var quantity = parseInt(orderItem.querySelector("div.quantity").innerText);
		// Calculate days of camp & eqpt
		var days = null;
		if("order_camp" === orderItem.parentElement.parentElement.id){
			var start_date_str = orderItem.querySelector("div.start_date").innerText;
			var start_date = new Date(start_date_str);
			var end_date_str = orderItem.querySelector("div.end_date").innerText;
			var end_date = new Date(end_date_str);
			days = (end_date - start_date) / (24 * 3600 * 1000);
			orderItem.querySelector("div.days").innerText = days;
		}else if("order_eqpt" === orderItem.parentElement.parentElement.id){
			var start_date_str = orderItem.querySelector("div.start_date").innerText;
			var start_date = new Date(start_date_str);
			var end_date_str = orderItem.querySelector("div.end_date").innerText;
			var end_date = new Date(end_date_str);
			days = (end_date - start_date) / (24 * 3600 * 1000) + 1;
			orderItem.querySelector("div.days").innerText = days;
		}else{
			days = 1;
		}
		// Determine if quantity is still available or not.
		if(isNaN(quantity)){
			orderItem.querySelector("div.amount").innerText = "-";	
		}else{
			orderItem.querySelector("div.amount").innerText = price * quantity * days;
		}
	});
	
	// Calculate total amount to checkout info.
	var totalAmountVal = 0;
	document.querySelectorAll("div.amount").forEach(function(amountItem){
		var itemAmount = parseInt(amountItem.innerText);
		if(isNaN(itemAmount)){
			totalAmountVal += 0;	
		}else{
			totalAmountVal += itemAmount;	
		}
	});
	document.querySelectorAll("div.totalAmount").forEach(function(totalAmount){
		totalAmount.innerText = totalAmountVal;
	});
	
	// Calculate checkout item amount of camp, eqpt, food.
	itemDiv_ids = ["order_camp", "order_eqpt", "order_food"];
	document.getElementById("campOriAmount").innerText = getItemOriginTotalAmount("order_camp");
	document.getElementById("eqptOriAmount").innerText = getItemOriginTotalAmount("order_eqpt");
	document.getElementById("foodOriAmount").innerText = getItemOriginTotalAmount("order_food");
	function getItemOriginTotalAmount(itemDiv_id){
		var itemOriginAmount = 0;
		document.getElementById(itemDiv_id).querySelectorAll("div.order_item").forEach(function(orderItem){
			var priceDiv = orderItem.querySelector("div.price");
			var originPrice = null;
			if(priceDiv.querySelector("b") != null){
				originPrice = parseInt(priceDiv.querySelector("del").innerText);
			}else{
				originPrice = parseInt(priceDiv.innerText);
			}
			var quantity = parseInt(orderItem.querySelector("div.quantity").innerText);
			// Determine if quantity is still available or not.
			if(isNaN(quantity)){
				itemOriginAmount += 0;
			}else{
				itemOriginAmount += originPrice * quantity;
			}
		});
		return itemOriginAmount;
	}
	
	// Calculate checkout origin total amount
	$("#totalOriAmount").text(
			parseInt($("#campOriAmount").text()) +
			parseInt($("#eqptOriAmount").text()) +
			parseInt($("#foodOriAmount").text())
	);
	
	// Calculate checkout discount total amount
	$("#totalDiscount").text(parseInt($("#totalOriAmount").text()) - parseInt($("#orderAmount").text()));
	
	// register credit card modal hidden event behavior.
	$("#creditCardForm").on("hidden.bs.modal", function () {
	    $(this).find("input").val("");
	    $("#visa").removeClass("transparent");
	    $("#mastercard").removeClass("transparent");
	    $("#amex").removeClass("transparent");
	    $("form").removeClass("was-validated");
	});
	
	// credit card form bs4 validation
	document.querySelector("form").onsubmit = function(event){
		if(this.checkValidity() === false){
			event.preventDefault();
			this.classList.add("was-validated");
		}
	}
	
	// credit card auto complete button
	$("#autoComplete").click(function(){
		$("#owner").val("Chang-Pu-Kong");
		$("#cvv").val("123");
		$("#cardNumber").val("4711222233334444");
	});
	
	// block checkoutBtn if orderitems not available.
	$("div.quantity").each(function(index, qtyDiv){
		var qtyVal = parseInt(qtyDiv.innerText);
		console.log("qtyVal = " + qtyVal);
		if(isNaN(qtyVal)){
			$("#creditCardCheckoutBtn").prop("disabled", true);
		}
	});
	
	// if session has OrderMasterVO, change creditCard form action to "creditCardByEdit"
	var om_no = "${param.om_no}";
	if("" !== om_no){
		$("input[name='action']").val("creditCardByEdit");
	}
</script>
</html>