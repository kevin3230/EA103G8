<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.promotion.model.*"%>
<%@ page import="com.promocamp.model.*"%>
<%@ page import="com.promoeqpt.model.*"%>
<%@ page import="com.promofood.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.food.model.*"%>
<%@ page import="com.vendor.model.*"%>

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
			
				<!-- 以下內容為範例，請自行發揮 -->			
				<div class="row justify-content-center">
					<div class="col-10">
								
						<div class="row mt-3">
							<div class="col">
								<h1 style="display: inline;">促銷專案管理</h1>
							</div>
						</div>
						<div class="row mt-3 mb-3">
							<div class="col">
								<a
									href="<%=request.getContextPath()%>/front-end/promotion/promoCreate.jsp">
									<button class="btn btn-primary">新增促銷專案</button>
								</a>
								<button class="btn btn-secondary disabled" id="deleteBtn">刪除促銷專案</button>
							</div>
						</div>
						<table class="table table-hover">
							<thead>
								<tr class="d-flex">
									<th class="col-1">#</th>
									<th class="col-4">促銷專案名稱</th>
									<th class="col-3">促銷開始日期</th>
									<th class="col-3">促銷結束日期</th>
									<th class="col-1">狀態</th>
								</tr>
							</thead>
							<tbody>
								<jsp:useBean id="proSvc" scope="page"
									class="com.promotion.model.PromotionService" />
								<c:forEach var="proVO" items="${proSvc.getAllByVdno(vd_no)}" varStatus="proVOi">
									<tr class="d-flex">
										<td class="col-1">
											<input type="checkbox" class="pro_ck" id="pro_stat_${proVO.pro_no}">
											<label for="pro_stat_${proVO.pro_no}">${proVOi.count}</label>
										</td>
										<td class="col-4"><a
											href="<%=request.getContextPath()%>/front-end/promotion/promoDetail.jsp?pro_no=${proVO.pro_no}">${proVO.pro_name}</a>
											<input type="hidden" value="${proVO.pro_no}">	
										</td>
										<td class="col-3">${proVO.pro_start}</td>
										<td class="col-3">${proVO.pro_end}</td>
										<td class="col-1"><c:if test="${proVO.pro_stat == 1}">
										啟用
									</c:if> <c:if test="${proVO.pro_stat == 0}">
										未啟用
									</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
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

	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
</body>
<script type="text/javascript">
	// Constants Setting
	const promotionServletURL = "<%=request.getContextPath()%>/promotion/PromotionServlet";
	
	// Batch promotion delete on promoHome.jsp
	var deleteBtn = document.getElementById("deleteBtn");
	var pro_cks = document.querySelectorAll("input.pro_ck");
	pro_cks.forEach(function(pro_ck){
		pro_ck.onclick = function(){
			var isChecked = 0;
			pro_cks.forEach(function(cki){
				isChecked += cki.checked;
			});
			if(isChecked > 0){
				deleteBtn.setAttribute("class", "btn btn-danger");
				
			}else{
				deleteBtn.setAttribute("class", "btn btn-secondary disabled");
			}
		}
	});
	deleteBtn.onclick = function(){
		if(!this.className.includes("disabled")){
			pro_noList = [];
			pro_cks.forEach(function(pro_ck){
				if(pro_ck.checked){
					var pro_no = pro_ck.id.split("_")[2];
					pro_noList.push(pro_no);	
				}
			});
			var deleteConfirm = confirm("準備刪除 " + pro_noList.length + "筆促銷專案。" +"\n確定要刪除嗎？");
			if(deleteConfirm){
				// form data
				var formData = new FormData();
				formData.append("action", "deleteInHome");
				formData.append("pro_noArr", JSON.stringify(pro_noList));
				// ajax request
				var xhr = new XMLHttpRequest();
				xhr.open("POST", promotionServletURL);
		        xhr.send(formData);
		        xhr.onreadystatechange = function(){
		            if(this.readyState == 4){
		            	console.log("Delete promotions request complete.");
		                if(this.status == 200){
		                	console.log("Delete promotions request network OK.");
		                	if(xhr.responseText === "1"){
		                		alert("成功刪除" + pro_noList.length + "筆促銷專案！");
		                		window.location.href = "<%=request.getContextPath()%>/front-end/promotion/promoHome.jsp";
		                	}else{
		                		alert("刪除失敗");
		                	}
		                	
		                	
		                }else{
		                	console.log("Delete promotions request network Failed. " + this.status);
		                }
		            }
		        }
			}
		}
	}
	
</script>
</html>