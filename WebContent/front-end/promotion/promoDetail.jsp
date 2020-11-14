<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>
<%@ page import="com.promotion.model.*"%>
<%@ page import="com.promocamp.model.*"%>
<%@ page import="com.promoeqpt.model.*"%>
<%@ page import="com.promofood.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.food.model.*"%>
<%@ page import="com.vendor.model.*"%>

<jsp:useBean id="proSvc" scope="page" 
	class="com.promotion.model.PromotionService" />
<jsp:useBean id="pcSvc" scope="page"
	class="com.promocamp.model.PromoCampService" />
<jsp:useBean id="peSvc" scope="page"
	class="com.promoeqpt.model.PromoEqptService" />
<jsp:useBean id="pfSvc" scope="page"
	class="com.promofood.model.PromoFoodService" />
<jsp:useBean id="campSvc" scope="page"
	class="com.camp.model.CampService" />
<jsp:useBean id="eqptSvc" scope="page"
	class="com.equipment.model.EquipmentService" />
<jsp:useBean id="foodSvc" scope="page"
	class="com.food.model.FoodService" />

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PLAMPING</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<style>
	body {
    	background-color: #f2f2f2;
    	color: #444;
	}
	form.promoForm{
		padding: 20px;
		border: 1px #D0D3D4 solid;
		border-radius: 15px;
		margin-bottom: 20px;
	}
	#Promotion{
		margin-bottom: 30px;
		border-bottom: 1px #D0D3D4 solid;
	}
	#PromoCamp{
		margin: 30px 0;
		border-bottom: 1px #D0D3D4 solid;
	}
	#PromoEqpt{
		margin: 30px 0;
		border-bottom: 1px #D0D3D4 solid;
	}
	#PromoFood{
		margin-top: 30px;
		border-bottom: 1px #D0D3D4 solid;
	}
	h3{
		margin-bottom: 20px;
	}
	button.close:focus{
		outline: none !important;
	}
	div.form-row-template{
		display: none;
	}
	div.emptyProItem{
		color: gray;
	}
</style>
</head>

<%--
String vd_no = (String)session.getAttribute("vd_no");
String loginURL = request.getContextPath() + "/front-end/promotion/pseudoLogin.html";
if(vd_no == null){
	response.sendRedirect(loginURL);
}
--%>
<%
VendorVO vdVO = (VendorVO)session.getAttribute("vendorVO");
String vd_no = vdVO.getVd_no();
request.setAttribute("vd_no", vd_no);
%>
<%
	String pro_no = null;
	if(request.getParameter("pro_no") == null){
		pro_no = (String)request.getAttribute("pro_no");
	}else{
		pro_no = request.getParameter("pro_no");
	}
	pageContext.setAttribute("pro_no", pro_no);
	pageContext.setAttribute("proVO", proSvc.getOnePro(pro_no));
	pageContext.setAttribute("pcList", pcSvc.getByPc_prono(pro_no));
	pageContext.setAttribute("peList", peSvc.getByPe_prono(pro_no));
	pageContext.setAttribute("pfList", pfSvc.getByPf_prono(pro_no));
%>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%> 
	<!-- Header結束 -->
	
	<div class="container-fluid">
		<div class="row">
		<!-- sidebar放這邊 ，col設定為2 -->		
		<%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
		<!-- sidebar放這邊 ，col設定為2 -->
	
			<div class="col-10">
			
							
				<div class="row justify-content-center">
					<div class="col-10">
			<!-- 以下內容為範例，請自行發揮 -->
					<div class="row mt-3">
						<div class="col">
							<h1 style="display: inline;">促銷專案詳情</h1>
						</div>
					</div>
					<div class="row mt-3 mb-3">
						<div class="col">
							<a href="<%=request.getContextPath()%>/front-end/promotion/promoHome.jsp">
								<button class="btn btn-link">回促銷專案管理</button>
							</a>
							<button class="btn btn-danger" id="deleteBtn" style="float: right;">刪除促銷專案</button>
							<button class="btn btn-primary" id="editBtn" style="float: right;">編輯促銷專案</button>
						</div>
					</div>
					<form class="promoForm" method="POST" action="<%=request.getContextPath()%>/promotion/PromotionServlet">
						<input type="hidden" name="action" value="edit">
						<input type="hidden" name="pro_no" value="${pro_no}">
						<div id="Promotion">
							<h3>促銷專案</h3>
							<div class="form-row">	
								<div class="form-group col">
									<label for="pro_name">促銷專案名稱</label>
									<input type="text" name="pro_name" value="${proVO.pro_name}" class="form-control" id="pro_name" disabled>
								</div>
							</div>
							<div class="form-row">
								<div class="form-group col">
									<label for="pro_start">促銷開始日期</label>
									<input type="text" name="pro_start" value="${proVO.pro_start}" class="form-control" id="pro_start" autocomplete="off" disabled>
								</div>
								<div class="form-group col">
									<label for="pro_end">促銷結束日期</label>
									<input type="text" name="pro_end" value="${proVO.pro_end}" class="form-control" id="pro_end" autocomplete="off" disabled>
								</div>
							</div>
							<div class="form-group form-check">
									<input type="checkbox" name="pro_stat" value="${proVO.pro_stat}" class="form-check-input" id="pro_stat" disabled>
									<label for="pro_stat">啟用</label>
							</div>
						</div>
						<div id="PromoCamp">
							<h3>促銷營位</h3>
							<div class="form-row form-row-template">
								<button type="button" class="close" aria-label="Close">
					  				<span aria-hidden="true">&times;</span>
								</button>
								<div class="form-group col-5 pname">
									<label for="pc_campno">營位名稱</label>
										<select name="pc_campno" class="form-control" id="pc_campno" disabled>
											<option value="" selected>請選擇營位...</option>
											<c:forEach var="campVO" items="${campSvc.getExistCampsByVdno(vd_no)}">
													<option value="${campVO.camp_no}">${campVO.camp_name}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group col-3 pprice">
									<label for="pc_price">促銷價</label>
									<input type="number" name="pc_price" value="" class="form-control" id="pc_price">
								</div>
								<div class="form-group col-3 oprice">
									<label for="camp_price">原價</label>
									<input type="text" name="camp_price" value="" class="form-control-plaintext" id="camp_price" readonly>
								</div>
							</div>
							<c:choose>
								<c:when test="${pcList.size() == 0}">
									<div class="form-row emptyProItem justify-content-center" id="campEmpty">尚未選擇任何營位</div>
								</c:when>
								<c:when test="${pcList.size() != 0}">
									<c:forEach var="pcVO" items="${pcList}" varStatus="pcVOs">
									<div class="form-row">
										<button type="button" class="close invisible" aria-label="Close">
							  				<span aria-hidden="true">&times;</span>
										</button>
										<div class="form-group col-5 pname">
											<label for="pc_campno${pcVOs.index+1}">營位名稱</label>
												<select name="pc_campno" class="form-control" id="pc_campno${pcVOs.index+1}" disabled>
														<option value="">請選擇營位...</option>
													<c:forEach var="campVO" items="${campSvc.getExistCampsByVdno(vd_no)}">
															<c:if test="${pcVO.pc_campno == campVO.camp_no}">
																<option value="${campVO.camp_no}" selected>${campVO.camp_name}</option>
															</c:if>
															<c:if test="${pcVO.pc_campno != campVO.camp_no}">
																<option value="${campVO.camp_no}">${campVO.camp_name}</option>
															</c:if>
													</c:forEach>
												</select>
										</div>
										<div class="form-group col-3 pprice">
											<label for="pc_price${pcVOs.index+1}">促銷價</label>
											<input type="number" name="pc_price" value="${pcVO.pc_price}" class="form-control" id="pc_price${pcVOs.index+1}" disabled>
										</div>
										<div class="form-group col-3 oprice">
											<label for="camp_price${pcVOs.index+1}">原價</label>
											<input type="text" name="camp_price" value="${campSvc.getOneCamp(pcVO.pc_campno).camp_price}" class="form-control-plaintext" id="camp_price${pcVOs.index+1}" readonly>
										</div>
									</div>
									</c:forEach>
								</c:when>
							</c:choose>
							<button id="campBtn" type="button" class="addBtn btn btn-light d-flex mb-1 invisible">
								<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-plus-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
				  					<path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
				  					<path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
								</svg>
							</button>
						</div>
						<div id="PromoEqpt">
							<h3>促銷裝備</h3>
							<div class="form-row form-row-template">
								<button type="button" class="close" aria-label="Close">
					  				<span aria-hidden="true">&times;</span>
								</button>
								<div class="form-group col-5 pname">
									<label for="pe_eqptno">裝備名稱</label>
										<select name="pe_eqptno" class="form-control" id="pe_eqptno" disabled>
											<option value="" selected>請選擇裝備...</option>
											<c:forEach var="eqptVO" items="${eqptSvc.getExistEqptsByVdno(vd_no)}">
													<option value="${eqptVO.eqpt_no}">${eqptVO.eqpt_name}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group col-3 pprice">
									<label for="pe_price">促銷價</label>
									<input type="number" name="pe_price" value="" class="form-control" id="pe_price">
								</div>
								<div class="form-group col-3 oprice">
									<label for="eqpt_price">原價</label>
									<input type="text" name="eqpt_price" value="" class="form-control-plaintext" id="eqpt_price" readonly>
								</div>
							</div>
							<c:choose>
								<c:when test="${peList.size() == 0}">
									<div class="form-row emptyProItem justify-content-center" id="eqptEmpty">尚未選擇任何裝備</div>
								</c:when>
								<c:when test="${peList.size() != 0}">
									<c:forEach var="peVO" items="${peList}" varStatus="peVOs">
									<div class="form-row">
										<button type="button" class="close invisible" aria-label="Close">
							  				<span aria-hidden="true">&times;</span>
										</button>
										<div class="form-group col-5 pname">
											<label for="pe_eqptno${peVOs.index+1}">裝備名稱</label>
												<select name="pe_eqptno" class="form-control" id="pe_eqptno${peVOs.index+1}" disabled>
														<option value="">請選擇裝備...</option>
													<c:forEach var="eqptVO" items="${eqptSvc.getExistEqptsByVdno(vd_no)}">
															<c:if test="${peVO.pe_eqptno == eqptVO.eqpt_no}">
																<option value="${eqptVO.eqpt_no}" selected>${eqptVO.eqpt_name}</option>
															</c:if>
															<c:if test="${peVO.pe_eqptno != eqptVO.eqpt_no}">
																<option value="${eqptVO.eqpt_no}">${eqptVO.eqpt_name}</option>
															</c:if>
													</c:forEach>
												</select>
										</div>
										<div class="form-group col-3 pprice">
											<label for="pe_price${peVOs.index+1}">促銷價</label>
											<input type="number" name="pe_price" value="${peVO.pe_price}" class="form-control" id="pe_price${peVOs.index+1}" disabled>
										</div>
										<div class="form-group col-3 oprice">
											<label for="eqpt_price${peVOs.index+1}">原價</label>
											<input type="text" name="eqpt_price" value="${eqptSvc.getOneEquipment(peVO.pe_eqptno).eqpt_price}" class="form-control-plaintext" id="eqpt_price${peVOs.index+1}" readonly>
										</div>
									</div>
									</c:forEach>
								</c:when>
							</c:choose>
							<button id="eqptBtn" type="button" class="addBtn btn btn-light d-flex mb-1 invisible">
								<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-plus-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
				  					<path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
				  					<path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
								</svg>
							</button>
						</div>
						<div id="PromoFood">
							<h3>促銷食材</h3>
							<div class="form-row form-row-template">
								<button type="button" class="close" aria-label="Close">
					  				<span aria-hidden="true">&times;</span>
								</button>
								<div class="form-group col-5 pname">
									<label for="pf_foodno">食材名稱</label>
										<select name="pf_foodno" class="form-control" id="pf_foodno" disabled>
											<option value="" selected>請選擇食材...</option>
											<c:forEach var="foodVO" items="${foodSvc.getExistFoodsByVdno(vd_no)}">
													<option value="${foodVO.food_no}">${foodVO.food_name}</option>
											</c:forEach>
										</select>
								</div>
								<div class="form-group col-3 pprice">
									<label for="pf_price">促銷價</label>
									<input type="number" name="pf_price" value="" class="form-control" id="pf_price">
								</div>
								<div class="form-group col-3 oprice">
									<label for="food_price">原價</label>
									<input type="text" name="food_price" value="" class="form-control-plaintext" id="food_price" readonly>
								</div>
							</div>
							<c:choose>
								<c:when test="${pfList.size() == 0}">
									<div class="form-row emptyProItem justify-content-center" id="foodEmpty">尚未選擇任何食材</div>
								</c:when>
								<c:when test="${pfList.size() != 0}">
									<c:forEach var="pfVO" items="${pfList}" varStatus="pfVOs">
									<div class="form-row">
										<button type="button" class="close invisible" aria-label="Close">
							  				<span aria-hidden="true">&times;</span>
										</button>
										<div class="form-group col-5 pname">
											<label for="pf_foodno${pfVOs.index+1}">食材名稱</label>
												<select name="pf_foodno" class="form-control" id="pf_foodno${pfVOs.index+1}" disabled>
														<option value="">請選擇食材...</option>
													<c:forEach var="foodVO" items="${foodSvc.getExistFoodsByVdno(vd_no)}">
															<c:if test="${pfVO.pf_foodno == foodVO.food_no}">
																<option value="${foodVO.food_no}" selected>${foodVO.food_name}</option>
															</c:if>
															<c:if test="${pfVO.pf_foodno != foodVO.food_no}">
																<option value="${foodVO.food_no}">${foodVO.food_name}</option>
															</c:if>
													</c:forEach>
												</select>
										</div>
										<div class="form-group col-3 pprice">
											<label for="pf_price${pfVOs.index+1}">促銷價</label>
											<input type="number" name="pf_price" value="${pfVO.pf_price}" class="form-control" id="pf_price${pfVOs.index+1}" disabled>
										</div>
										<div class="form-group col-3 oprice">
											<label for="food_price${pfVOs.index+1}">原價</label>
											<input type="text" name="food_price" value="${foodSvc.getOneFood(pfVO.pf_foodno).food_price}" class="form-control-plaintext" id="food_price${pfVOs.index+1}" readonly>
										</div>
									</div>
									</c:forEach>
								</c:when>
							</c:choose>
							<button id="foodBtn" type="button" class="addBtn btn btn-light d-flex mb-1 invisible">
								<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-plus-circle" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
				  					<path fill-rule="evenodd" d="M8 15A7 7 0 1 0 8 1a7 7 0 0 0 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
				  					<path fill-rule="evenodd" d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
								</svg>
							</button>
						</div>
						<input id="sendBtn" type="submit" class="btn btn-primary mt-3 invisible" value="完成編輯">
						<a href="<%=request.getContextPath()%>/front-end/promotion/promoDetail.jsp?pro_no=<%=pro_no%>">
							<button type="button" class="btn btn-danger mt-3 invisible">取消編輯</button>
						</a>
					</form>
			<!-- 以上內容為範例，請自行發揮 -->
					</div>
				</div>
				
				
			</div>
		</div>
	</div>

	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->
	
</body>
<script type="text/javascript">
	// Constants Setting
	const promotionServletURL = "<%=request.getContextPath()%>/promotion/PromotionServlet";
	
	// pro_stat to checked or not & set default value to 1.
	var pro_stat_initial = document.querySelector("input[name='pro_stat']");
	if(pro_stat_initial.value == "1"){
		pro_stat_initial.checked = true;
	}
	pro_stat_initial.value = 1;
	
	// register edit button onclick event.
	var editBtn = document.getElementById("editBtn");
	editBtn.onclick = function(){
		// input element remove disabled attribute
		var inputDOMs = document.querySelectorAll("input");
		inputDOMs.forEach(function(inputDOM){
			inputDOM.removeAttribute("disabled");
		});
		// select element remove disabled attribute
		var selectDOMs = document.querySelectorAll("select");
		selectDOMs.forEach(function(selectDOM){
			selectDOM.removeAttribute("disabled");
		});
		// add icon & delete icon & cancel button available
		var addBtns = document.querySelectorAll("button.invisible");
		addBtns.forEach(function(addBtn){
			addBtn.classList.remove("invisible");
		});
		// send button available
		var sendBtn = document.getElementById("sendBtn");
		sendBtn.classList.remove("invisible");
		// disabled edit button
		editBtn.disabled = true;
		// pro_start, pro_end earlier than today will remain disabled
		var pro_start = Date.parse(document.getElementById("pro_start").value);
		var pro_end = Date.parse(document.getElementById("pro_end").value);
		var today = new Date().setHours(0,0,0,0);
		if(pro_start < today){
			document.getElementById("pro_start").disabled = true;
		}
		if(pro_end < today){
			document.getElementById("pro_end").disabled = true;
		}
	}
	
	// register camp, eqpt, food add button onclick event.
	var addBtns = document.querySelectorAll("button.addBtn");
	var addLabeli = 0;
	addBtns.forEach(function(addBtn){
		addBtn.onclick = function(){
			var item = this.parentElement.querySelector("div.form-row-template").cloneNode(true);
			item.classList.remove("form-row-template");
			// register delete icon onclick event.
			item.querySelector("button.close").onclick = function(){
				this.parentElement.remove();
			}
			// change item_name field label & id when adding item.
			var item_name = item.querySelector("div.pname");
			var item_name_label = item_name.querySelector("label");
			item_name_label.htmlFor = item_name_label.htmlFor + "c" + addLabeli;
			var item_name_select = item_name.querySelector("select");
			item_name_select.id = item_name_select.id + "c" + addLabeli;
			// change item_promotionprice field label & id when adding item.
			var item_pprice = item.querySelector("div.pprice");
			var item_pprice_label = item_pprice.querySelector("label");
			item_pprice_label.htmlFor = item_pprice_label.htmlFor + "c" + addLabeli;
			var item_pprice_input = item_pprice.querySelector("input");
			item_pprice_input.id = item_pprice_input.id + "c" + addLabeli;
			// change item_originalprice field label & id when adding item.
			var item_oprice = item.querySelector("div.oprice");
			var item_oprice_label = item_oprice.querySelector("label");
			item_oprice_label.htmlFor = item_oprice_label.htmlFor + "c" + addLabeli;
			var item_oprice_input = item_oprice.querySelector("input");
			item_oprice_input.id = item_oprice_input.id + "c" + addLabeli;
			addLabeli ++;
			// ajax update oprice field.
			item_pprice.style.display = "none";
			item_oprice.style.display = "none";
			selectOnchangeShowPriceFields(item_name_select);
			this.before(item);
			
			// clear empty area
			if(addBtn.id == "campBtn"){
				document.getElementById("campEmpty").remove();
			}else if(addBtn.id == "eqptBtn"){
				document.getElementById("eqptEmpty").remove();
			}else if(addBtn.id == "foodBtn"){
				document.getElementById("foodEmpty").remove();
			}
		}
	});
	
	// register camp, eqpt, food existing dropdown field onchange event.
	var selectFields = document.querySelectorAll("select");
	selectFields.forEach(function(select){
		selectOnchangeShowPriceFields(select);
	});
	function selectOnchangeShowPriceFields(selectDOM){
		selectDOM.onchange = function(){
			var formRow = this.parentElement.parentElement;
			var ppriceDiv = formRow.querySelector("div.pprice");
			var opriceDiv = formRow.querySelector("div.oprice");
			if(this.value != ""){
				ppriceDiv.style.display = "inline";
				opriceDiv.style.display = "inline";
			}else{
				ppriceDiv.style.display = "none";
				opriceDiv.style.display = "none";
			}
			ajax_getOriginalPrice(this);
		}
	}
	// ajax request to get original price.
	function ajax_getOriginalPrice(selectDOM){
		// create form data send to PromotionServlet.
		var formData = new FormData();
		formData.append("action", "ajax_getOriginalPrice");
		var item_no = selectDOM.value;
		formData.append("item_no", item_no);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", promotionServletURL);
        xhr.send(formData);
        xhr.onreadystatechange = function(){
            if(this.readyState == 4){
            	console.log("Select " + item_no + " request complete.");
                if(this.status == 200){
                	console.log("Select " + item_no + " request network OK.");
                    selectDOM.parentElement.parentElement.querySelector("div.oprice").querySelector("input").value = xhr.responseText;
                    console.log("Vendor : " + "<%=session.getAttribute("vd_no")%>" + ", Item_no : " + item_no + ", original price : " + xhr.responseText);
                }else{
                	console.log("Select " + item_no + " request network Failed. " + this.status);
                }
            }
        }
	}
	
	// register camp, eqpt, food existing delete icon onclick event.
	var deleteBtns = document.querySelectorAll("button.close");
	deleteBtns.forEach(function(deleteBtn){
		deleteBtn.onclick = function(){
			this.parentElement.remove();
		}
	});
	
	// register delete promotion button onclick event.
	var deleteBtn = document.getElementById("deleteBtn");
	deleteBtn.onclick = function(){
		var pro_name = document.querySelector("input[name='pro_name']").value;
		var deleteConfirm = confirm("準備刪除  " + pro_name + " 促銷專案。" +"\n確定要刪除嗎？");
		if(deleteConfirm){
			var formData = new FormData();
			formData.append("action", "deleteInDetail");
			formData.append("pro_no", "${pro_no}");
			var xhr = new XMLHttpRequest();
			xhr.open("POST", promotionServletURL);
			xhr.send(formData);
			xhr.onreadystatechange = function(){
				if(this.readyState == 4){
					console.log("promoDetail deleteBtn request complete.");
					if(this.status == 200){
						console.log("promoDetail deleteBtn network OK.");
						alert(pro_name + " 已成功刪除！");
						window.location.href = "<%=request.getContextPath()%>/front-end/promotion/promoHome.jsp";
					}else{
						console.log("promoDetail deleteBtn network failed " + this.status);
						alert(pro_name + " 刪除失敗。");
					}
				}
			}
		}
	}
	
	// register submit button to cancel disabled effect of pro_start & pro_end
	document.getElementById("sendBtn").onclick = function(){
		document.getElementById("pro_start").disabled = false;
		document.getElementById("pro_end").disabled = false;
	}
	
	/********** Error Handling **********/
	<c:if test="${not empty requestScope.errorMsg || not empty requestScope.duplicateMsg}">
	<!-- Pre-process the promoDetail when PromotionServlet forward back with errorMsg. -->
		// PromoCamp remove param.pro_no DB data that create by JSP forEach loop.
		var pcRowList = document.getElementById("PromoCamp").querySelectorAll("div.form-row");
		for(let i = 1; i < pcRowList.length; i++){
			pcRowList[i].remove();
		}
		// PromoEqpt remove param.pro_no DB data that create by JSP forEach loop.
		var peRowList = document.getElementById("PromoEqpt").querySelectorAll("div.form-row");
		for(let i = 1; i < peRowList.length; i++){
			peRowList[i].remove();
		}
		// PromoFood remove param.pro_no DB data that create by JSP forEach loop.
		var pfRowList = document.getElementById("PromoFood").querySelectorAll("div.form-row");
		for(let i = 1; i < pfRowList.length; i++){
			pfRowList[i].remove();
		}
		// Open all fields to be able to edit.
		document.querySelectorAll("input:disabled").forEach(function(inputDOM){
			inputDOM.disabled = false;
		});
		document.querySelectorAll("select:disabled").forEach(function(selectDOM){
			selectDOM.disabled = false;
		});
		// Show submit & cancel button
		document.getElementById("sendBtn").classList.remove("invisible");
		document.getElementById("sendBtn").nextElementSibling.querySelector("button").classList.remove("invisible");
		// Show addBtn
		document.querySelectorAll("button.addBtn").forEach(function(addBtn){
			addBtn.classList.remove("invisible");
		});
		
	<!-- Fill in previous field value -->
		// Promotion
		document.querySelector("input[name='pro_name']").value = "${requestScope.pro_name}";
		document.querySelector("input[name='pro_start']").value = "${requestScope.pro_start}";
		document.querySelector("input[name='pro_end']").value = "${requestScope.pro_end}";
		<c:if test="${not empty requestScope.pro_stat}">
			document.querySelector("input[name='pro_stat']").checked = true;
		</c:if>
		// pro_start, pro_end earlier than today will be disabled
		var pro_startV = Date.parse("${requestScope.pro_start}");
		var pro_endV = Date.parse("${requestScope.pro_end}");
		var todayV = new Date().setHours(0,0,0,0);
		if(pro_startV < todayV){
			document.querySelector("input[name='pro_start']").disabled = true;
		}
		if(pro_endV < todayV){
			document.querySelector("input[name='pro_end']").disabled = true;
		}
		// PromoCamp
		var camp_template = document.getElementById("PromoCamp").querySelector("div.form-row-template");
		<c:forEach var="pc_campno" items="${pc_campnoList}" varStatus="pcStatus">
			var camp_item${pcStatus.count} = camp_template.cloneNode(true);
			camp_item${pcStatus.count}.classList.remove("form-row-template");
			var camp_options${pcStatus.count} = camp_item${pcStatus.count}.querySelectorAll("option");
			camp_options${pcStatus.count}[0].selected = false;
			for(let i = 0; i < camp_options${pcStatus.count}.length; i++){
				if(camp_options${pcStatus.count}[i].value === "${pc_campno}"){
					camp_options${pcStatus.count}[i].selected = true;
					break;
				}
			}
			<c:if test="${not empty requestScope.pc_priceList[pcStatus.index]}">
				camp_item${pcStatus.count}.querySelector("input[name = 'pc_price']").value = ${requestScope.pc_priceList[pcStatus.index]};
			</c:if>
			<c:if test="${not empty requestScope.camp_priceList[pcStatus.index]}">
				camp_item${pcStatus.count}.querySelector("input[name = 'camp_price']").value = ${requestScope.camp_priceList[pcStatus.index]};	
			</c:if>
			// register button behavior
			selectOnchangeShowPriceFields(camp_item${pcStatus.count}.querySelector("select"));
			camp_item${pcStatus.count}.querySelector("button.close").onclick = function(){
				this.parentElement.remove();
			}
			// append in html
			document.getElementById("campBtn").before(camp_item${pcStatus.count});
		</c:forEach>
		// PromoEqpt
		var eqpt_template = document.getElementById("PromoEqpt").querySelector("div.form-row-template");
		<c:forEach var="pe_eqptno" items="${pe_eqptnoList}" varStatus="peStatus">
			var eqpt_item${peStatus.count} = eqpt_template.cloneNode(true);
			eqpt_item${peStatus.count}.classList.remove("form-row-template");
			var eqpt_options${peStatus.count} = eqpt_item${peStatus.count}.querySelectorAll("option");
			eqpt_options${peStatus.count}[0].selected = false;
			for(let i = 0; i < eqpt_options${peStatus.count}.length; i++){
				if(eqpt_options${peStatus.count}[i].value === "${pe_eqptno}"){
					eqpt_options${peStatus.count}[i].selected = true;
					break;
				}
			}
			<c:if test="${not empty requestScope.pe_priceList[peStatus.index]}">
				eqpt_item${peStatus.count}.querySelector("input[name = 'pe_price']").value = ${requestScope.pe_priceList[peStatus.index]};
			</c:if>
			<c:if test="${not empty requestScope.eqpt_priceList[peStatus.index]}">
				eqpt_item${peStatus.count}.querySelector("input[name = 'eqpt_price']").value = ${requestScope.eqpt_priceList[peStatus.index]};	
			</c:if>
			// register button behavior
			selectOnchangeShowPriceFields(eqpt_item${peStatus.count}.querySelector("select"));
			eqpt_item${peStatus.count}.querySelector("button.close").onclick = function(){
				this.parentElement.remove();
			}
			// append in html
			document.getElementById("eqptBtn").before(eqpt_item${peStatus.count});
		</c:forEach>
		// PromoFood
		var food_template = document.getElementById("PromoFood").querySelector("div.form-row-template");
		<c:forEach var="pf_foodno" items="${pf_foodnoList}" varStatus="pfStatus">
			var food_item${pfStatus.count} = food_template.cloneNode(true);
			food_item${pfStatus.count}.classList.remove("form-row-template");
			var food_options${pfStatus.count} = food_item${pfStatus.count}.querySelectorAll("option");
			food_options${pfStatus.count}[0].selected = false;
			for(let i = 0; i < food_options${pfStatus.count}.length; i++){
				if(food_options${pfStatus.count}[i].value === "${pf_foodno}"){
					food_options${pfStatus.count}[i].selected = true;
					break;
				}
			}
			<c:if test="${not empty requestScope.pf_priceList[pfStatus.index]}">
				food_item${pfStatus.count}.querySelector("input[name = 'pf_price']").value = ${requestScope.pf_priceList[pfStatus.index]};
			</c:if>
			<c:if test="${not empty requestScope.food_priceList[pfStatus.index]}">
				food_item${pfStatus.count}.querySelector("input[name = 'food_price']").value = ${requestScope.food_priceList[pfStatus.index]};	
			</c:if>
			// register button behavior
			selectOnchangeShowPriceFields(food_item${pfStatus.count}.querySelector("select"));
			food_item${pfStatus.count}.querySelector("button.close").onclick = function(){
				this.parentElement.remove();
			}
			// append in html
			document.getElementById("foodBtn").before(food_item${pfStatus.count});
		</c:forEach>
	</c:if>
	<!-- Promotion fields errorMsg -->
	<c:if test="${not empty requestScope.errorMsg}">
		<c:if test="${fn:length(requestScope.errorMsg.pro_name) > 0}">
			var pro_name = document.querySelector("input[name='pro_name']");
			pro_name.classList.add("is-invalid");
			<c:forEach var="msg" items="${requestScope.errorMsg.pro_name}" varStatus="msgStatus">
				var msg${msgStatus.count} = document.createElement("div");
				msg${msgStatus.count}.classList.add("invalid-feedback");
				msg${msgStatus.count}.innerText = "${msg}";
				pro_name.after(msg${msgStatus.count});
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(requestScope.errorMsg.pro_start) > 0}">
			var pro_start = document.querySelector("input[name='pro_start']");
			pro_start.classList.add("is-invalid");
			<c:forEach var="msg" items="${requestScope.errorMsg.pro_start}" varStatus="msgStatus">
				var msg${msgStatus.count} = document.createElement("div");
				msg${msgStatus.count}.classList.add("invalid-feedback");
				msg${msgStatus.count}.innerText = "${msg}";
				pro_start.after(msg${msgStatus.count});
			</c:forEach>
		</c:if>
		<c:if test="${fn:length(requestScope.errorMsg.pro_end) > 0}">
			var pro_end = document.querySelector("input[name='pro_end']");
			pro_end.classList.add("is-invalid");
			<c:forEach var="msg" items="${requestScope.errorMsg.pro_end}" varStatus="msgStatus">
				var msg${msgStatus.count} = document.createElement("div");
				msg${msgStatus.count}.classList.add("invalid-feedback");
				msg${msgStatus.count}.innerText = "${msg}";
				pro_end.after(msg${msgStatus.count});
			</c:forEach>
		</c:if>
	<!-- PromoCamp fields errorMsg -->
		var campItems = document.getElementById("PromoCamp").querySelectorAll("div.form-row");
		<c:forEach var="campMsg" items="${requestScope.errorMsg.pro_campList}" varStatus="msgStatus">
			<c:if test="${not empty campMsg}">
				// add error msg
				campItems[${msgStatus.count}].querySelector("input[name = 'pc_price']").classList.add("is-invalid");
				var msg = document.createElement("div");
				msg.classList.add("invalid-feedback");
				msg.innerText = "${campMsg}";
				campItems[${msgStatus.count}].querySelector("input[name = 'pc_price']").after(msg);
			</c:if>
		</c:forEach>
	<!-- PromoEqpt fields errorMsg -->
        var eqptItems = document.getElementById("PromoEqpt").querySelectorAll("div.form-row");
        <c:forEach var="eqptMsg" items="${requestScope.errorMsg.pro_eqptList}" varStatus="msgStatus">
            <c:if test="${not empty eqptMsg}">
                // add error msg
                eqptItems[${msgStatus.count}].querySelector("input[name = 'pe_price']").classList.add("is-invalid");
                var msg = document.createElement("div");
                msg.classList.add("invalid-feedback");
                msg.innerText = "${eqptMsg}";
                eqptItems[${msgStatus.count}].querySelector("input[name = 'pe_price']").after(msg);
            </c:if>
        </c:forEach>
    <!-- PromoFood fields errorMsg -->
        var foodItems = document.getElementById("PromoFood").querySelectorAll("div.form-row");
        <c:forEach var="foodMsg" items="${requestScope.errorMsg.pro_foodList}" varStatus="msgStatus">
            <c:if test="${not empty foodMsg}">
                // add error msg
                foodItems[${msgStatus.count}].querySelector("input[name = 'pf_price']").classList.add("is-invalid");
                var msg = document.createElement("div");
                msg.classList.add("invalid-feedback");
                msg.innerText = "${foodMsg}";
                foodItems[${msgStatus.count}].querySelector("input[name = 'pf_price']").after(msg);
            </c:if>
        </c:forEach>
    <!-- Empty camp, eqpt food item -->
    	<c:if test="${requestScope.errorMsg.isEmptyItem[0] == '1'}">
    		alert("尚無選擇任何促銷項目！");
    	</c:if>
	</c:if>
	<%-- Duplicate PromoCamp, PromoEqpt, PromoFood items --%>
	<c:if test="${not empty requestScope.duplicateMsg}">
		var selectedOptions = document.querySelectorAll("option:checked");
		<c:forEach var="itemNo" items="${requestScope.duplicateMsg}">
			selectedOptions.forEach(function(selectedOption){
				if(selectedOption.value === "${itemNo.key}"){
					selectedOption.parentElement.classList.add("is-invalid");
					var msg = document.createElement("div");
					msg.classList.add("invalid-feedback");
					msg.innerText = "${itemNo.value}";
					selectedOption.parentElement.after(msg);
				}
			});
		</c:forEach>
	</c:if>
	<%-- Create complete forward to promoDetail.jsp --%>
	<c:if test="${not empty requestScope.createResult}">
		alert("${proVO.pro_name} 新增成功！");
	</c:if>
	<%-- Create complete forward to promoDetail.jsp --%>
	<c:if test="${not empty requestScope.editResult}">
		alert("${proVO.pro_name} 編輯成功！");
	</c:if>
</script>

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#pro_start').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   <%-- value: '<%=hiredate%>', // value:   new Date(), --%>
           //disabledDates:        ['2020/10/20','2020/10/21','2020/10/22'], // 去除特定不含
           //startDate:	            '2020/10/20',  // 起始日
           minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
           scrollInput: false
        });
        $('#pro_end').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   <%-- value: '<%=hiredate%>', // value:   new Date(), --%>
            //disabledDates:        ['2020/10/20','2020/10/21','2020/10/22'], // 去除特定不含
            //startDate:	            '2020/10/20',  // 起始日
            minDate:               '-1970-01-01', // 去除今日(不含)之前
            //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
            scrollInput: false
         });
</script>
<!-- EA103G8 header-footer content import start -->
	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
<!-- EA103G8 header-footer content import end -->
</html>