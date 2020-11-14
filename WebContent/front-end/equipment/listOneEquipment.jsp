<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.eqpttype.model.*"%>

<%
	String vd_no = (String)session.getAttribute("vd_no");
	EquipmentVO equipmentVO = (EquipmentVO) request.getAttribute("equipmentVO");
	String[] statArray = {"草稿", "下架", "上架"}; // 裝備狀態使用
%>
<jsp:useBean id="etSvc" scope="page" class="com.eqpttype.model.EqptTypeService" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PLAMPING</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">

<style>

	body {
    	background-color: #f2f2f2;
    	color: #444;
	}

	#preview {
		height: 450px;
	}

</style>

</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%>
	<!-- Header結束 -->
		
	<div class="container-fluid min-vh-100">
		<div class="row min-vh-100 justify-content-start">
		
			<!-- sidebar放這邊 ，col設定為2 -->		
			<%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
			<!-- sidebar放這邊 ，col設定為2 -->
		
			<div class="col-8 offset-1">
				<div class="d-flex m-3 justify-content-start align-items-center">
					<h3>裝備資料</h3>
				</div>
				<div class="table-responsive">
					<table class="table table-striped">
						<tbody>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備編號</th>
								<td class="text-left"><%=equipmentVO.getEqpt_no()%></td>
							</tr>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備名稱</th>
								<td class="text-left"><%=equipmentVO.getEqpt_name()%></td>
							</tr>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備類型</th>
								<td class="text-left"><%=etSvc.getOneEqptType(equipmentVO.getEqpt_etno()).getEt_name()%></td>
							</tr>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備數量</th>
								<td class="text-left"><%=equipmentVO.getEqpt_qty()%></td>
							</tr>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備定價</th>
								<td class="text-left"><%=equipmentVO.getEqpt_price()%></td>
							</tr>
							<tr>
								<th scope="row" class="text-nowrap text-center">裝備狀態</th>
								<td class="text-left"><%=statArray[equipmentVO.getEqpt_stat()]%></td>
							</tr>
							<tr>	
								<td scope="row" colspan="2">
									<form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do">
										<div class="d-flex justify-content-start">
											<input type="hidden" name="eqpt_no" value="<%=equipmentVO.getEqpt_no()%>">
			     							<input type="hidden" name="action" value="getOneEquipment_For_Update">
											<input type="submit" class="btn btn-outline-info" value="修改">
			     						</div>
			     					</form>
								</td>								
							</tr>
						</tbody>
					</table>
				</div>
				<%  byte[] b = equipmentVO.getEqpt_pic(); // 由update的<input type="file">取得，就算沒放照片，還是會拿到髒東西...要透過controller管控
	              	String bts = Base64.getEncoder().encodeToString(b);
	               	if (bts.trim().length() != 0) { // 以防萬一，改用這個方法篩髒東西  %>
				<div class="d-flex my-3 justify-content-center align-items-start" id="preview">
	              	<img src="data:image/jpeg;base64,<%=bts%>" class="img-fluid mh-100" alt="" />
				</div>
	            <%  }  %>
			</div>
		</div>
	</div>

	<!-- Footer開始 -->
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->
	
	<script	src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
	<%@ include file="/front-end/index/Notice.jsp"%>
</body>
</html>