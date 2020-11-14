<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.activity.model.*"%>


<% 
	ActivityService actSvc = new ActivityService();
	List<ActivityVO> list = actSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>活動快報</title>
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
	
	<div class="container min-vh-100">
		<div class="row justify-content-center align-items-center">
			<div class="col-8 my-3">
				<h3 class="text-left ml-3">活動快報</h3>
			</div>
			
			<div class="col-8">
				<c:forEach var="activityVO"  items="${list}">
				<div class="card">
					<div class="card-header" id="heading_${activityVO.act_no}">
						<h2 class="mb-0">
							<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse_${activityVO.act_no}" aria-expanded="false" aria-controls="collapse_${activityVO.act_no}">
								${activityVO.act_title}
							</button>
						</h2>
					</div>
					<div id="collapse_${activityVO.act_no}" class="collapse">
						<div class="card-body">
							${activityVO.act_content}
						</div>
					</div>
				</div>
			</c:forEach>
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