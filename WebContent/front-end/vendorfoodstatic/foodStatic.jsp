<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂單食材數量統計</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<style>
.formargin {
    min-height:calc(90vh - 180px);
}

</style>
</head>
<body>

<%@ include file="/front-end/index/header.jsp" %>

	<div class="row formargin">
		<jsp:include page="/front-end/sidebar/vendorSidebar.jsp" flush="true" />

		<div class="container col-9">
		        <div class="row justify-content-center my-3">
		        <%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
		        
		        
		        
		        <div class="col-10">
		            <div class="col badge badge-secondary">
		                <h3 class=" my-0">訂單食材數量統計</h3>
		            </div>
	                <form action="#">
	                    <div class="form-row justify-content-center my-2">
	                    	<div class="col-5 px-0">
		                        <label class="col-5 px-0" for="startDate">
		                        	<h5 class="my-0">開始日期</h5>
		                        </label>
		                        <input name="startDate" id="startDate" class="form-control col-6 d-inline-block px-0" type="date" required>
		                        <div class="invalid-feedback offset-6 col-6">
						         	 請選擇日期
						        </div>
						    </div>
							<div class="col-5 px-0">
		                        <label class="col-5 px-0" for="endDate">
		                        	<h5 class="my-0">結束日期</h5>
		                        </label>
		                        <input name="endDate" id="endDate" class="form-control col-6 d-inline-block px-0" type="date">
		                        <div class="invalid-feedback offset-6 col-6">
									請選擇日期
						        </div>
						    </div>
	                        <button class="badge badge-secondary" id="getFoodStatic" type="button">送出</button>
	                    </div>
	                </form>
	                <div class="col-12">
	                    <select name="days"class="custom-select" id="period">
	                        <option value="0">今天</option>
	                        <option value="2">三天</option>
	                        <option value="4">五天</option>
	                        <option value="6">七天</option>
	                        <option value="29">三十天</option>
	                        <option value="-2">前三天</option>
	                        <option value="-4">前五天</option>
	                        <option value="-6">前七天</option>
	                        <option value="-29">前三十天</option>
	                    </select>
	                </div>
	                <div class="col-12">
	                	查詢日期從 <div class="col-3 d-inline-block" id="choosenFrom"></div>
	                	到 <div class="col-3 d-inline-block" id="choosenTo"></div>
	                </div>
	                <div class="col-12 my-2" id="showList">
	                    <table class="table-striped table-secondary table-bordered col-12">
	                        <thead>
	                            <tr>
	                            	<th>預訂領取日期</th>
	                            	<th>食材名稱</th>
	                            	<th>訂單小計數量</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	        </div>
	    </div>
    </div>
          
<%@ include file="/front-end/index/footer.jsp" %>

	<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<%@ include file="/front-end/index/Notice.jsp"%>
    <script>
        $("#getFoodStatic").click(function() {
            var startDateStr = $("#startDate").val();
            var endDateStr = $("#endDate").val();
            $("#choosenFrom").text("");
            $("#choosenTo").text("");
            if(startDateStr.length == 0){
            	$("#startDate").addClass("is-invalid");
            }else{
            	$("#startDate").removeClass("is-invalid");
            }
			if(endDateStr.length == 0){
				$("#endDate").addClass("is-invalid");
            }else{
            	$("#endDate").removeClass("is-invalid");
            }
			
			if(startDateStr.length != 0 && endDateStr.length != 0){
				$("#choosenFrom").text(startDateStr);
	            $("#choosenTo").text(endDateStr);
	            $.ajax({
	                url: "<%= request.getContextPath()%>/orderfood/orderfood.do",
	                type: "post",
	                data: {
	                    "action": "getFoodStatic",
	                    "startDate": startDateStr,
	                    "endDate": endDateStr
	                },
	                success: function(data) {
	                    $("tbody").empty();
						var list = JSON.parse(data);
						createTable(list);
	                }
	            });
			}
        });
        $("#startDate").change(function() {
        	if($("#startDate").val() != ""){
        		$("#endDate").attr("min",$("#startDate").val());
        	}else{
        		$("#endDate").removeAttr("min");
        	}
        });
        $("#endDate").change(function() {
        	if($("#endDate").val() != ""){
        		$("#startDate").attr("max",$("#endDate").val());
        	}else{
        		$("#startDate").removeAttr("max");
        	}
        });
        function createTable(list){
        	for (let i = 0; i < list.length; i++) {
                var tr = $("<tr>").addClass("col-12");
                
                tr.append($("<td>").text(list[i]["startDate"]));
                tr.append($("<td>").text(list[i]["foodName"]));
                tr.append($("<td>").text(list[i]["foodQty"]));
                $("tbody").append(tr);
            }
        }

        $("#period").change(function() {
            var today = new Date();
            var todayStr = getDayStr(today);
            if ($("#period").val() >= 0) {
                var startDateStr = todayStr;
                var endDate = new Date(today.getTime() + $("#period").val() * 86400000);
                var endDateStr = getDayStr(endDate);

            } else {
                var startDate = new Date(today.getTime() - 86400000 + $("#period").val() * 86400000);
                var endDateStr = getDayStr(new Date(today.getTime() - 86400000));
                var startDateStr = getDayStr(startDate);
            }

            $.ajax({
            	url: "<%= request.getContextPath()%>/orderfood/orderfood.do",
            	type: "post",
            	data: {
            		"action": "getFoodStatic",
            		"startDate": startDateStr,
            		"endDate": endDateStr
            	},
            	success: function(data) {
            		$("tbody").empty();
            		var list = JSON.parse(data);
            		createTable(list);
            	}
            });
            $("#choosenFrom").text(startDateStr);
            $("#choosenTo").text(endDateStr);
            $("#startDate").val(startDateStr);
            $("#endDate").val(endDateStr);
            
        });

        function getDayStr(day) {

            var dayStr = "";
            dayStr = day.getFullYear() + "-";
            if (day.getMonth() < 9) {
                dayStr = dayStr + "0" + (day.getMonth() + 1) + "-";
            } else {
                dayStr = dayStr + (day.getMonth() + 1) + "-";
            }
            if (day.getDate() < 10) {
                dayStr = dayStr + "0" + day.getDate();
            } else {
                dayStr = dayStr + day.getDate();
            }
            return dayStr;
        }
        
        $(function() {
            var today = new Date();
            var todayStr = getDayStr(today);
            $("#startDate").val(todayStr);
            $("#endDate").val(todayStr);
            $("#choosenFrom").text(todayStr);
            $("#choosenTo").text(todayStr);

            $.ajax({
            	url: "<%= request.getContextPath()%>/orderfood/orderfood.do",
            	type: "post",
            	data: {
            		"action": "getFoodStatic",
            		"startDate": todayStr,
            		"endDate": todayStr
            	},
            	success: function(data) {
            		//$("tbody").empty();
            		var list = JSON.parse(data);

            		createTable(list);
            	}
            });
        });
    </script>
</body>
</html>