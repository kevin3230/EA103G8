<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ordermaster.model.*"%>
<%@ page import="com.vendor.model.*"%>
<%
	//測試用寫死
	//VendorVO vdVOtest = new VendorVO();
	//vdVOtest.setVd_no("V000000001");
	//session.setAttribute("vendorVO", vdVOtest);
	//測試用寫死
	VendorVO vendorVO = (VendorVO)session.getAttribute("vendorVO");
	String vd_no = vendorVO.getVd_no();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>業者訂單管理</title>
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
<style>

.formargin {
    min-height:calc(90vh - 180px);
}

.showList {
	display: none;
}

</style>
</head>
<body>
<%@ include file="/front-end/index/header.jsp" %>

		<div class="row formargin">
			<jsp:include page="/front-end/sidebar/vendorSidebar.jsp" flush="true" />
			
			<div class="container col-9 ">
		        <div class="row justify-content-center my-2">
		        <%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
				
					<div class="col-10 badge badge-secondary">
	                    <h3 class=" my-0">業者訂單瀏覽</h3>
	                </div>
	                <div class="col-10 px-0">
	                    <form action="#">
	                    <div class="form-row justify-content-center my-2">
	                        <div class="col-5 d-inline-block ">
	                            <label class="col-4 col-form-label col-form-label-sm " for="startDate"><h5>開始日期</h5></label>
	                            <input name="startDate" id="startDate" class="form-control col-7 d-inline-block" type="date" required>
	                            <div class="invalid-feedback offset-5 col-6">請選擇日期</div>
	                        </div>
	                        <div class="col-5 d-inline-block ">
	                            <label class="col-4 col-form-label col-form-label-sm " for="endDate"><h5>結束日期</h5></label>
	                            <input name="endDate" id="endDate" class="form-control col-7 d-inline-block" type="date">
	                            <div class="invalid-feedback offset-5 col-6"> 請選擇日期</div>
	                        </div>
	                        <button class="badge badge-secondary mx-1" type="button" id="getMembersOrder"><h6>訂單查詢</h6></button>
	                        <button class="badge badge-secondary mx-1" type="button" id="getAll"><h6>查詢全部</h6></button>
	                    </div>
	                    </form>
	                </div>
					<div class="col-10 my-2" id="showList">
	                    <table class="table-striped table-secondary table-bordered col-12">
	                        <thead>
	                            <tr>
	                                <th>訂單編號</th>
	                                <th>露營區名稱</th>
	                                <th>下單時間</th>
	                                <th>訂單狀態</th>
	                                <th>訂單金額</th>
	                                <th>訂單操作</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                        	
	                        </tbody>
	                    </table>
	                    <div class="modal fade" id="confirmCancel" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
						  <div class="modal-dialog modal-dialog-centered" role="document">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalCenterTitle">注意</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          <span aria-hidden="true">&times;</span>
						        </button>
						      </div>
						      <div class="modal-body">
						        	確定取消訂單嗎?
						      </div>
						      <input type="hidden" name="cancelOm_no" id="cancelOm_no" value="">
						      <div class="modal-footer">
						        <button type="button" class="btn btn-secondary" data-dismiss="modal">不取消</button>
						        <button type="button" class="btn btn-primary" data-dismiss="modal" id="cancelOrder">確定取消</button>
						      </div>
						    </div>
						  </div>
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
	    $("#getMembersOrder").click(function() {
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
	                url: "<%= request.getContextPath()%>/carorder/EditOrderMasterServlet",
	                type: "post",
	                data: {
	                    "action": "getMembersOrder",
	                    "startDate": startDateStr,
	                    "endDate": endDateStr,
	                    "vd_no": "<%=vd_no %>"
	                },
	                success: function(data) {
	                    $("tbody").empty();
						var list = JSON.parse(data);
						createTable(list);
	                }
	            });
			}
        });
	    $("#cancelOrder").click(function(){
	    	var om_no = $("#cancelOm_no").val();
	    	$.ajax({
	    		url: "<%= request.getContextPath()%>/carorder/EditOrderMasterServlet",
	    		type: "post",
	    		data: {
	    			"action": "cancelOrder",
	    			"om_no": om_no
	    		},
	    		success: function(data){
	    			var list = JSON.parse(data);
					$("tbody").find($("[id='" + list["om_no"] + "']")).prev().remove();
					$("tbody").find($("[id='" + list["om_no"] + "']")).remove();
	    			//reloadOneRow(list);
	    		}
	    	});
	    });
	    $("#startDate").change(function() {
            // console.log($("#startDate").val());
            if ($("#startDate").val() != "") {
                $("#endDate").attr("min", $("#startDate").val());
            } else {
                $("#endDate").removeAttr("min");
            }
        });
        $("#endDate").change(function() {
            if ($("#endDate").val() != "") {
                $("#startDate").attr("max", $("#endDate").val());
            } else {
                $("#startDate").removeAttr("max");
            }
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
        function createTable(list){
        	var block = `
        		<td colspan="6">
        			<div class="col-11 my-2">
	        			<div class="mb-2">
		                    <h3 class="orderItemTitle">預約營位</h3>
		                </div>
		                <div class="order_camp_content">
			                <div class="row order_title">
		                        <div class="col-1"></div>
		                        <div class="col-3 h5">營位名稱</div>
		                        <div class="col-2 h5">開始日期</div>
		                        <div class="col-2 h5">結束日期</div>
		                        <div class="col-2 h6">訂購單價</div>
		                        <div class="col-1 h6">數量</div>
		                        <div class="col-1 h6">金額</div>
		                    </div>
	                    </div>
	        			<div class="mb-2">
		                    <h3 class="orderItemTitle">租借裝備</h3>
		                </div>
		                <div class="order_eqpt_content">
		                    <div class="row order_title">
		                        <div class="col-1"></div>
		                        <div class="col-3 h5">裝備名稱</div>
		                        <div class="col-2 h5">開始日期</div>
		                        <div class="col-2 h5">結束日期</div>
		                        <div class="col-2 h6">訂購單價</div>
		                        <div class="col-1 h6">數量</div>
		                        <div class="col-1 h6">金額</div>
		                    </div>
	                    </div>
	                    <div class="mb-2">
	                        <h3 class="orderItemTitle">訂購食材</h3>
	                    </div>
	                    <div class="order_food_content">
	                        <div class="row order_title">
	                            <div class="col-1"></div>
	                            <div class="col-7 h5">食材名稱</div>
	                            <div class="col-2 h6">訂購單價</div>
	                            <div class="col-1 h6">數量</div>
	                            <div class="col-1 h6">金額</div>
	                        </div>
	                    </div>
	                    
        			</div>
        		</td>
        	`;
        	
        	for (let i = 0; i < list.length; i++) {
                var tr = $("<tr>").addClass("col-12");
                
                tr.append($("<td>").text(list[i]["om_no"]));
                tr.append($("<td>").text(list[i]["cg_name"]));
                tr.append($("<td>").text(list[i]["om_estbl"]));
                switch (list[i]["om_stat"]){
                	case "0" :
                		tr.append($("<td>").text("已取消"));
                		break;
                	case "1" :
                		tr.append($("<td>").text("未付款"));
                		break;
                	case "2" :
                		tr.append($("<td>").text("已付款"));
                		break;
                	case "3" :
                		tr.append($("<td>").text("已完成"));
                		break;
                	default:
                		tr.append($("<td>").text(""));
                		console.log("取不到om_stat的值");
                }
                tr.append($("<td>").text("$" + list[i]["om_txnamt"]));
                //var buttonpay = $("<button>").addClass("badge badge-secondary").attr("type", "button").text("付款");
                var buttonview = $("<button>").addClass("badge badge-secondary mx-1 my-1").attr("type", "button").append($("<h6>").text("查看"));
                buttonview.click(function(){
                	$(this).parent().parent().next().toggle();
                });
                var buttoncancel = $("<button>").addClass("badge badge-secondary mx-1 my-1").attr({"type" : "button", "data-toggle" : "modal", "data-target" : "#confirmCancel"}).append($("<h6>").text("取消"));
                buttoncancel.click(function(){
                	$("#cancelOm_no").val(list[i]["om_no"]);
                });
                var buttongroup = $("<td>");
                switch (list[i]["om_stat"]){
            	case "0" :
            		buttongroup.append(buttonview);
            		break;
            	case "1" :
            		buttongroup.append(buttonview).append(buttoncancel);
            		break;
            	case "2" :
            		buttongroup.append(buttonview).append(buttoncancel);
            		break;
            	case "3" :
            		buttongroup.append(buttonview);
            		break;
            	default:
            		buttongroup.append(buttonview);
            	}
                tr.append(buttongroup);
                $("tbody").append(tr);
                
                var trdiv = $("<tr>").addClass("showList").attr("id", list[i]["om_no"]).html(block);
                $("tbody").append(trdiv);
                //console.log($("tbody").find($("[id='" + list[i]["om_no"] + "']")).find("input"));
                
               	
                for(var j = 0; j < list[i]["ocList"].length; j++){
	                var ocList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
	               		.append($("<div>").addClass("col-1"))
	               		.append($("<div>").addClass("col-3").text(list[i]["ocList"][j]["camp_name"]))
	               		.append($("<div>").addClass("col-2").text(list[i]["ocList"][j]["oc_start"]))
	               		.append($("<div>").addClass("col-2").text(list[i]["ocList"][j]["oc_end"]))
	               		.append($("<div>").addClass("col-2").text("$" +list[i]["ocList"][j]["oc_price"]))
	               		.append($("<div>").addClass("col-1").text(list[i]["ocList"][j]["oc_qty"]))
	               		.append($("<div>").addClass("col-1").text("$" +list[i]["ocList"][j]["oc_sum"]));
	               	$("tbody").find($("[id='" + list[i]["om_no"] + "']")).find($(".order_camp_content")).append(ocList);
                }
                for(var j =0; j < list[i]["oeList"].length; j++){
	               	var oeList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
	               		.append($("<div>").addClass("col-1"))
	               		.append($("<div>").addClass("col-3").text(list[i]["oeList"][j]["eqpt_name"]))
	               		.append($("<div>").addClass("col-2").text(list[i]["oeList"][j]["oe_expget"]))
	               		.append($("<div>").addClass("col-2").text(list[i]["oeList"][j]["oe_expback"]))
	               		.append($("<div>").addClass("col-2").text("$" +list[i]["oeList"][j]["oe_price"]))
	               		.append($("<div>").addClass("col-1").text(list[i]["oeList"][j]["oe_qty"]))
	               		.append($("<div>").addClass("col-1").text("$" +list[i]["oeList"][j]["oe_sum"]));
	               	$("tbody").find($("[id='" + list[i]["om_no"] + "']")).find($(".order_eqpt_content")).append(oeList);
                }
                for(var j =0; j < list[i]["ofList"].length; j++){
	               	var ofList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
	               		.append($("<div>").addClass("col-1"))
	               		.append($("<div>").addClass("col-7").text(list[i]["ofList"][j]["food_name"]))
	               		.append($("<div>").addClass("col-2").text("$" +list[i]["ofList"][j]["of_price"]))
	               		.append($("<div>").addClass("col-1").text(list[i]["ofList"][j]["of_qty"]))
	               		.append($("<div>").addClass("col-1").text("$" +list[i]["ofList"][j]["of_sum"]));
	               	$("tbody").find($("[id='" + list[i]["om_no"] + "']")).find($(".order_food_content")).append(ofList);
                }
            }
        }
        function reloadOneRow(list){
        	var block = `
        		<td colspan="6">
        			<div class="col-11 my-2">
	        			<div class="mb-2">
		                    <h3 class="orderItemTitle">預約營位</h3>
		                </div>
		                <div class="order_camp_content">
			                <div class="row order_title">
		                        <div class="col-1"></div>
		                        <div class="col-3 h5">營位名稱</div>
		                        <div class="col-2 h5">開始日期</div>
		                        <div class="col-2 h5">結束日期</div>
		                        <div class="col-2 h6">訂購單價</div>
		                        <div class="col-1 h6">數量</div>
		                        <div class="col-1 h6">金額</div>
		                    </div>
	                    </div>
	        			<div class="mb-2">
		                    <h3 class="orderItemTitle">租借裝備</h3>
		                </div>
		                <div class="order_eqpt_content">
		                    <div class="row order_title">
		                        <div class="col-1"></div>
		                        <div class="col-3 h5">裝備名稱</div>
		                        <div class="col-2 h5">開始日期</div>
		                        <div class="col-2 h5">結束日期</div>
		                        <div class="col-2 h6">訂購單價</div>
		                        <div class="col-1 h6">數量</div>
		                        <div class="col-1 h6">金額</div>
		                    </div>
	                    </div>
	                    <div class="mb-2">
	                        <h3 class="orderItemTitle">訂購食材</h3>
	                    </div>
	                    <div class="order_food_content">
	                        <div class="row order_title">
	                            <div class="col-1"></div>
	                            <div class="col-7 h5">食材名稱</div>
	                            <div class="col-2 h6">訂購單價</div>
	                            <div class="col-1 h6">數量</div>
	                            <div class="col-1 h6">金額</div>
	                        </div>
	                    </div>
        			</div>
        		</td>
        	`;
        	
            var tr = $("<tr>").addClass("col-12");
            
            tr.append($("<td>").text(list["om_no"]));
            tr.append($("<td>").text(list["cg_name"]));
            tr.append($("<td>").text(list["om_estbl"]));
            switch (list["om_stat"]){
            	case "0" :
            		tr.append($("<td>").text("已取消"));
            		break;
            	case "1" :
            		tr.append($("<td>").text("未付款"));
            		break;
            	case "2" :
            		tr.append($("<td>").text("已付款"));
            		break;
            	case "3" :
            		tr.append($("<td>").text("已完成"));
            		break;
            	default:
            		tr.append($("<td>").text(""));
            		console.log("取不到om_stat的值");
            }
            tr.append($("<td>").text("$" + list["om_txnamt"]));
            //var buttonpay = $("<button>").addClass("badge badge-secondary").attr("type", "button").text("付款");
            var buttonview = $("<button>").addClass("badge badge-secondary").attr("type", "button").text("查看");
            buttonview.click(function(){
            	$(this).parent().parent().next().toggle();
            });
            var buttoncancel = $("<button>").addClass("badge badge-secondary").attr({"type" : "button", "data-toggle" : "modal", "data-target" : "#exampleModalCenter"}).text("取消");
            buttoncancel.click(function(){
            	$("#cancelOm_no").val(list["om_no"]);
            });
            var buttongroup = $("<td>");
            switch (list["om_stat"]){
        	case "0" :
        		buttongroup.append(buttonview);
        		break;
        	case "1" :
        		buttongroup.append(buttonview).append(buttoncancel);
        		break;
        	case "2" :
        		buttongroup.append(buttonview).append(buttoncancel);
        		break;
        	case "3" :
        		buttongroup.append(buttonview);
        		break;
        	default:
        		buttongroup.append(buttonview);
        	}
            tr.append(buttongroup);
            $("tbody").find($("[id='" + list["om_no"] + "']")).before(tr);
            $("tbody").find($("[id='" + list["om_no"] + "']")).empty();
            //var trdiv = $("<tr>").addClass("showList").attr("id", list["om_no"]).html(block);
            $("tbody").find($("[id='" + list["om_no"] + "']")).append(block);
           	
            for(var j = 0; j < list["ocList"].length; j++){
             var ocList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
            		.append($("<div>").addClass("col-1"))
            		.append($("<div>").addClass("col-3").text(list["ocList"][j]["camp_name"]))
            		.append($("<div>").addClass("col-2").text(list["ocList"][j]["oc_start"]))
            		.append($("<div>").addClass("col-2").text(list["ocList"][j]["oc_end"]))
            		.append($("<div>").addClass("col-2").text("$" +list["ocList"][j]["oc_price"]))
            		.append($("<div>").addClass("col-1").text(list["ocList"][j]["oc_qty"]))
            		.append($("<div>").addClass("col-1").text("$" +list["ocList"][j]["oc_sum"]));
            	$("tbody").find($("[id='" + list["om_no"] + "']")).find($(".order_camp_content")).append(ocList);
            }
            for(var j =0; j < list["oeList"].length; j++){
            	var oeList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
            		.append($("<div>").addClass("col-1"))
            		.append($("<div>").addClass("col-3").text(list["oeList"][j]["eqpt_name"]))
            		.append($("<div>").addClass("col-2").text(list["oeList"][j]["oe_expget"]))
            		.append($("<div>").addClass("col-2").text(list["oeList"][j]["oe_expback"]))
            		.append($("<div>").addClass("col-2").text("$" +list["oeList"][j]["oe_price"]))
            		.append($("<div>").addClass("col-1").text(list["oeList"][j]["oe_qty"]))
            		.append($("<div>").addClass("col-1").text("$" +list["oeList"][j]["oe_sum"]));
            	$("tbody").find($("[id='" + list["om_no"] + "']")).find($(".order_eqpt_content")).append(oeList);
            }
            for(var j =0; j < list["ofList"].length; j++){
            	var ofList = $("<div>").addClass("row pb-2 pt-2 border-top order_item")
            		.append($("<div>").addClass("col-1"))
            		.append($("<div>").addClass("col-7").text(list["ofList"][j]["food_name"]))
            		.append($("<div>").addClass("col-2").text("$" +list["ofList"][j]["of_price"]))
            		.append($("<div>").addClass("col-1").text(list["ofList"][j]["of_qty"]))
            		.append($("<div>").addClass("col-1").text("$" +list["ofList"][j]["of_sum"]));
            	$("tbody").find($("[id='" + list["om_no"] + "']")).find($(".order_food_content")).append(ofList);
            }
        }
        $("#getAll").click(function(){
        	var today = new Date();
            var todayStr = getDayStr(today);
            $("#startDate").val(todayStr);
            $("#endDate").val(todayStr);
            
            $.ajax({
            	url: "<%= request.getContextPath()%>/carorder/EditOrderMasterServlet",
            	type: "post",
            	data: {
            		"action": "getMembersOrder",
            		"vd_no": "<%=vd_no %>"
            	},
            	success: function(data) {
            		$("tbody").empty();
            		var list = JSON.parse(data);
            		createTable(list);
            	}
            });
	    });
        $(function() {
            var today = new Date();
            var todayStr = getDayStr(today);
            $("#startDate").val(todayStr);
            $("#endDate").val(todayStr);
            
            $.ajax({
            	url: "<%= request.getContextPath()%>/carorder/EditOrderMasterServlet",
            	type: "post",
            	data: {
            		"action": "getMembersOrder",
            		"vd_no": "<%=vd_no %>"
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