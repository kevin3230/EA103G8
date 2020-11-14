<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.equipment.model.*"%>

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
</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg">
				<h3>查詢裝備：</h3>
				
				<div class="row">
					<a href='<%=request.getContextPath()%>/front-end/equipment/listAllEquipment.jsp'>查詢所有裝備</a>
				</div>
				
				<form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do">
					<div class="row">
						<div class="col-sm-3">
							裝備編號：
						</div>
						<div class="col-sm-6">
							<input type="text" name="eqpt_no" id="eqpt_no" placeholder="裝備編號">
						</div>
						<div class="col-sm-3">
							<input type="hidden" name="action" value="getOneEquipment_For_Display">
							<input type="submit" value="送出">
						</div>
					</div>
				</form>
				
				<jsp:useBean id="eqptSvc" scope="page" class="com.equipment.model.EquipmentService" />
				<form method="post" action="<%=request.getContextPath()%>/equipment/equipment.do" >
					<div class="row">
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
       						<input type="submit" value="送出">
						</div>
					</div>
	     		</form>
	     		
	     		<h3>裝備管理</h3>
				
				<div class="row">
					<a href='<%=request.getContextPath()%>/front-end/equipment/addEquipment.jsp'>新增裝備</a>
				</div>
     		
			</div>
				
			<div class="col-lg">
				
				<h3>照片</h3>
					<span><img src="<%=request.getContextPath()%>/front-end/camp/templated-hielo/images2/p1.jpg" class="img-fluid" alt=""  /></span>
                
                <!-- <div class="row">
                    <%/* for(CampVO cVO: list) {
        				byte[] b = cVO.getCamp_pic();
        				if (b != null) {
        				String bts = Base64.getEncoder().encodeToString(b);
        			*/%>
                        <div class="col-sm-4"><span class="image fit"><img src="data:image/jpeg;base64,<%/*=bts*/%>" alt="" /></span></div>
                    <%/* } else { */%>
                    	<div class="col-sm-4"><span class="image fit"><img src="<%/*=request.getContextPath()*/%>/img/1.jpg" /></span></div>
                    <%/* }} */%>
                </div> -->
				
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
	<%@ include file="/front-end/index/Notice.jsp"%>
</body>
</html>