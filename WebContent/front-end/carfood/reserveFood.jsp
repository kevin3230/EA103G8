<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="com.orderfood.model.*"%>
<%@ page import="com.ordermaster.model.*"%>
<%@ page import="com.foodtype.model.*"%>
<%@ page import="com.carfood.model.*"%>
<%@ page import="com.carcamp.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.promofood.model.*"%>
<%@ page isELIgnored="true" %>

<%
	//測試用寫死
	//MembersVO memVOtest = new MembersVO();
	//memVOtest.setMem_no("M000000001");
	//session.setAttribute("memVO", memVOtest);
	//測試用寫死
	MembersVO memVO = (MembersVO)session.getAttribute("memVO");
	String mem_no = memVO.getMem_no();

	//System.out.println("om_no:" + request.getParameter("om_no"));
	
	FoodService foodSvc = new FoodService();
	CarCampService carCampSvc = new CarCampService();
	CampService campSvc = new CampService();
	OrderMasterService omSvc = new OrderMasterService();
	String food_vdno = "";
	if(request.getParameter("om_no") != null){
		OrderMasterVO ordermasterVO = omSvc.getOneOrderMaster(request.getParameter("om_no"));
		food_vdno = ordermasterVO.getOm_vdno();
	}else{
		List<CarCampVO> carCamplist = carCampSvc.getCarCampsByMemno(mem_no);
		CampVO campVO = campSvc.getOneCamp(carCamplist.get(0).getCc_campno());
		food_vdno = campVO.getCamp_vdno();
	}
	//food_vdno = "V000000001";//假業者
	//System.out.println(food_vdno);
	List<FoodVO> foodtypelist = foodSvc.getVendorFoodType(food_vdno, 2);
	pageContext.setAttribute("foodtypelist", foodtypelist);
	CarFoodService carFoodSvc = new CarFoodService();
	/*取出List<CarFoodVO>食材購物車集合*/
	List<CarFoodVO> carFoodlist = carFoodSvc.getOneCar(mem_no);
	/*取得購物車食材的vd_no*/
	String carFood_vdno = "";
	if(carFoodlist.size() != 0){
		carFood_vdno = foodSvc.getOneFood(carFoodlist.get(0).getCf_foodno()).getFood_vdno();
	}
  	pageContext.setAttribute("carFoodlist",carFoodlist);
%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>訂購食材</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/carcamp/css/reservedNav.css">
<style>
.block{
		width: 25%;
		display: inline-block;
		float: left;
		box-sizing: border-box;
		padding: 0.1em;
		margin: 0.5%;
		text-align: center;
		border: 2px solid black;
}
#sideBar {
	text-align: center;
	border: 2px solid black;
}

#block {
	width: 100%;
	display: inline-block;
	float: left;
	box-sizing: border-box;
	margin: auto;
	border: 2px solid black;
	min-height: 32em;
}

#showCar {
	width: 100%;
	display: inline-block;
	box-sizing: border-box;
	margin: auto;
	border: 2px solid black;
}

.btn-link {
	text-decoration: none;
}
.btn-link:hover {
	text-decoration: none;
}
.picContainer{
	height: 18rem;
	display:flex;
	align-items:center;
	justify-content:center;
}
.pic {
    width: auto;
    heigh: auto;
    max-width:100%;
    max-heigh:100%;
}
.itemimg{
	width: 100%;
}
.oneItem{
		display: none;
		position: absolute;
		top: 0%;
		left: 0%;
		width: 140%;
		border: 0.2em solid black;
		background-color: white;
		z-index: 5;
}
#backgroundImg{
	width: 100%;
	height: 100%;
	z-index: 4;
	opacity: 0.9;
	position: fixed;
	top: 0;
	left: 0;
	display: none;
}
#backgroundImg>img{
	width: 100%;
	height: 100%;
}
.table{
    table-layout: fixed;
}
#backstep {
	background-color: #DADADA;
	color: rgb(121, 100, 100);
}
#backstep:hover {
	background-color: rgb(198, 200, 200);
}
.table td{
	vertical-align: middle;
}
</style>
</head>
<body>
	<!-- header開始 -->
	<%@ include file="/front-end/index/header.jsp"%>
	<!-- header結束 -->


	<!-- 預約導覽列開始 -->
	<%@ include file="/front-end/carcamp/reservedNav.jsp"%>
	<!-- 預約導覽列結束 -->


	<!-- 本文開始 -->
	<div class="container">
		<div class="row justify-content-center">

			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<div id="backgroundImg">
				<img src="<%=request.getContextPath() %>/front-end/carfood/images/backgroundImg.jpg" alt="無法顯示背景圖" onclick="closeOneItem()">
			</div>

			<div class="col-10 py-2 px-0">
				
				<div id="sideBar">
					食材分類：
					<ul style="display: inline-block">
						<%
							if (foodtypelist.size() == 0) {
						%>
						<li style="display: inline-block">&emsp; 露營區尚無食材販賣&emsp; </li>
						<%}%>
						
						<%
							for (int index = 0; index < foodtypelist.size(); index++) {
								FoodVO foodVO = foodtypelist.get(index);
								FoodTypeService foodtypeSvc = new FoodTypeService();
								FoodTypeVO foodtypeVO = foodtypeSvc.getOneFoodType(foodVO.getFood_ftno());
						%>
						<li style="display: inline-block">
							<button class="getFood btn btn-link" id="<%=foodVO.getFood_ftno()%>">&emsp; <%=foodtypeVO.getFt_name()%>&emsp; </button>
						</li>
						<%}%>
					</ul>
				</div>
			</div>

			<div class="col-10 py-2">
				<div class="row justify-content-center">
		            <div id="block">
		            <%
						if (foodtypelist.size() == 0) {
					%>
					<h5>&emsp; 露營區尚無食材販賣&emsp; </h5>
					<%}%>
		            </div>
		        </div>
				<div class="col-12 py-2 px-0" >
					<form action="<%=request.getContextPath() %>/carfood/Carfood.do" id="myform" method="post">
						<input type="hidden" name="action" value="editOrder">
						<% if(request.getParameter("om_no") != null){ %>
						<input type="hidden" name="om_no" value="<%=request.getParameter("om_no")%>">
						<%}%>
	                    <table class="table table-striped table-secondary table-bordered col-12" id="details">
	                        <thead>
	                            <tr><th scope="col" style="width: 25%">食材名稱&emsp;&emsp;</th><th scope="col" style="width: 15%">單價</th><th scope="col" style="width: 15%">促銷價格</th><th scope="col" style="width: 10%">數量</th><th scope="col" style="width: 10%">小計</th><th scope="col"></th></tr>
	                        </thead>
	                        <tbody>
	                        <%
	                        //測試修改訂單
	                        //OrderMasterService omSvc = new OrderMasterService();
	                        //OrderMasterVO omVO = omSvc.getOneOrderMaster("O000000002");
	                        //session.setAttribute("orderMasterVO", omVO);
	                        //測試
	                        /*如果session有orderMasterVO就是修改訂單*/
	                        if(request.getParameter("om_no") != null && session.getAttribute("carFoodVOList") == null){
	                        	//request.getParameter("om_no")
	                        	//session.getAttribute("orderMasterVO")
	                        	String om_no2 = request.getParameter("om_no");
	                        	//String om_no2 = ((OrderMasterVO)session.getAttribute("orderMasterVO")).getOm_no();
	                        	
	                        	
	                        	/*取得食材訂單明細*/
	                        	OrderFoodService orderFoodSvc = new OrderFoodService();
	                        	List<OrderFoodVO> orderFoodList = orderFoodSvc.getActiveOrderFoodsByOmno(om_no2);
	                        	List<CarFoodVO> carFoodVOList = new ArrayList<CarFoodVO>();
	                        	/*create carFoodVOList*/
	                        	for(int i = 0; i < orderFoodList.size(); i++){ 
		                        	
		                        	OrderFoodVO orderFoodVO = orderFoodList.get(i);
		                        	FoodVO foodVO = foodSvc.getOneFood(orderFoodVO.getOf_foodno());
		                        	CarFoodVO carFoodVO = new CarFoodVO();
		                        	PromoFoodService pfSvc = new PromoFoodService();
		                			PromoFoodVO pfVO = pfSvc.getActiveLowPriceByPf_foodno(foodVO.getFood_no());
		                			String pf_price = "";
		                    		if(pfVO.getPf_price() == 0){
		                    			pf_price = "原價";
		                    		}else{
		                    			pf_price = Integer.toString(pfVO.getPf_price());
		                    		}
		                    		carFoodVO.setCf_foodno(foodVO.getFood_no());
		                    		carFoodVO.setCf_memno(mem_no);
		                    		carFoodVO.setCf_qty(orderFoodVO.getOf_qty());
		                    		carFoodVOList.add(carFoodVO);
	                        	}
	                        	session.setAttribute("carFoodVOList", carFoodVOList);
	                        }
	                        	/*新增訂單的購物車或session有"carFoodVOList"*/
	                        	if(food_vdno.equals(carFood_vdno) || session.getAttribute("carFoodVOList") != null){
	                        		if(request.getParameter("om_no") != null && session.getAttribute("carFoodVOList") != null){
	                        			carFoodlist = (List<CarFoodVO>)session.getAttribute("carFoodVOList");
	                        		}
	                        		for(int i = 0; i < carFoodlist.size(); i++){ 
	                        	
		                        	CarFoodVO carFoodVO = carFoodlist.get(i);
		                        	FoodVO foodVO = foodSvc.getOneFood(carFoodVO.getCf_foodno());
		                        	PromoFoodService pfSvc = new PromoFoodService();
		                			PromoFoodVO pfVO = pfSvc.getActiveLowPriceByPf_foodno(foodVO.getFood_no());
		                			String pf_price = "";
		                    		if(pfVO.getPf_price() == 0){
		                    			pf_price = "原價";
		                    		}else{
		                    			pf_price = Integer.toString(pfVO.getPf_price());
		                    		}
	                        %>
		                        	<tr id="<%=foodVO.getFood_no() %>">
		                        		<td><%=foodVO.getFood_name() %><input type="hidden" name="cf_foodno" value="<%=foodVO.getFood_no()%>"></td>
		                        		<td name="price"><%=foodVO.getFood_price() %></td>
		                        		<td name="listprice"><%=pf_price %></td>
		                        		<td name="qty"><%=carFoodVO.getCf_qty() %><input type="hidden" name="cf_qty" value="<%=carFoodVO.getCf_qty()%>"></td>
		                        		<%if(pfVO.getPf_price() == 0){ %>
		                        			<td name="sum"><%=foodVO.getFood_price() * carFoodVO.getCf_qty()%></td>
		                        		<%}else{ %>
		                        			<td name="sum"><%=pfVO.getPf_price() * carFoodVO.getCf_qty()%></td>
		                        		<%} %>
		                        		<td>
		                        			<button class="badge badge-secondary px-3 mx-1" type="button"><h5>-</h5></button>
		                        			<button class="badge badge-secondary px-3 mx-1" type="button"><h5>+</h5></button>
		                        			<button class="badge badge-secondary px-3 mx-1" type="button"><h5>刪除</h5></button>
		                        		</td>
		                        	</tr>
	                        <%		}
	                        	} 
	                        %>
	                        <tr id="sumRow">
                        		<td>總計</td><td></td><td></td><td></td><td id="moneySum"></td><td></td>
                        	</tr>
	                        </tbody>
	                    </table>
	                </form>
        		</div>
				<div class="row justify-content-center">
					<div class="d-inline-block">
						<h4>
						<% if(request.getParameter("om_no") != null){ %>
							<button class="btn btn-lg mr-3" id="backstep" onclick="javascript:location.href='<%=request.getContextPath()%>/front-end/careqpt/carEqpt.jsp?action=editOrder&om_no=<%=request.getParameter("om_no")%>'">返回上一頁&emsp;&emsp;</button>
						<%}else{ %>
							<button class="btn btn-lg mr-3" id="backstep" onclick="javascript:location.href='<%=request.getContextPath()%>/front-end/careqpt/carEqpt.jsp'">返回上一頁&emsp;&emsp;</button>
						<%} %>
						</h4>
					</div>
					<%-- 連結記得改成前往CarFoodServlet的連結--%>
					<div class="d-inline-block">
						<h4>
							<button class="btn btn-danger btn-lg ml-3" id="nextstep" form="myform" type="submit">下一步(訂單確認)</button>
						</h4>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 本文結束 -->


	<!-- footer開始 -->
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- footer結束 -->

	<script
		src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<%@ include file="/front-end/index/Notice.jsp"%>	
	<script>
		$("#step3").addClass("here");
		$(".getFood").click(function(){
			var food_ftno = this.id;
			
			$.ajax({
				url: "<%= request.getContextPath()%>/carfood/Carfood.do",
            	type: "POST",
            	data: {
                    action: "getFood",
                    food_ftno: food_ftno,
                    food_vdno: "<%=food_vdno%>"
                },
                success: function(data) {
                	$("#block").empty();
                	
                    //console.log("data:" + data);
                    var map = JSON.parse(data);
                    //console.log("map:" + map);
                    //console.log("map長度:" + Object.keys(map).length);
                    //console.log("foodVOList長度:" + map['foodVOList'].length);
                    //console.log("物件內容" + map['foodVOList'][0]['food_ftno'] + map['foodVOList'][0]['food_vdno']);
                    foodtype(map);
                    registEventListener();
                }
			});
			
		});
		
		function foodtype(map){
			var n = map['foodVOList'].length;
			
			for (let i = 0; i < n; i++){
				var block = `
					<div class="foodname">${map['foodVOList'][i]['food_name']}</div>
					<div class="picContainer">
			    		<img class="linkToItems pic" id="" src="<%=request.getContextPath() %>/CarFoodPicReader/CarFoodPicReader.do?food_no=${map['foodVOList'][i]['food_no']}&food_vdno=${map['foodVOList'][i]['food_vdno']}">
			    	</div>
			    	<br>
			    	<button type="button" class="items btn btn-dark mt-1" id="">點我看詳情</button>
			    	<br>
			    	<div class="d-inline-block px-0">價格:$</div><div class="d-inline-block price px-0" value="">${map['foodVOList'][i]['food_price']}</div>
			 		<br>
			    	<div class="d-inline-block lowprice px-0">促銷價格:$</div>
			    	<br>
			    	數量:
			    	<select name="qty" id="">
			            
			        </select>
			        <div class="oneItem justify-content-center" id="">
						<div><h3>${map['foodVOList'][i]['food_name']}</h3></div>
						<img class="itemimg" src="<%=request.getContextPath() %>/CarFoodPicReader/CarFoodPicReader.do?food_no=${map['foodVOList'][i]['food_no']}&food_vdno=${map['foodVOList'][i]['food_vdno']}">
						<br>
						介紹:${map['foodVOList'][i]['food_intro']}
					</div>
			        <br>
			        <button class="btn btn-dark addcar">加入購物車</button>
		        `;
        		$("#block").append($("<div>").addClass("d-inline-block col-4 py-2").attr("id", map['foodVOList'][i]['food_no']).html(block));
        		$("#block").find("#" + map['foodVOList'][i]['food_no']).find(".linkToItems").attr("id", i);
        		$("#block").find("#" + map['foodVOList'][i]['food_no']).find(".items").attr("id", i);
        		$("#block").find("#" + map['foodVOList'][i]['food_no']).find(".oneItem").attr("id", i);
        		$("#block").find("#" + map['foodVOList'][i]['food_no']).find(".price").attr("value", map['foodVOList'][i]['food_price']);
        		var pf_price;
        		if(map['pfVOList'][i]['pf_price'] == 0){
        			pf_price = "原價";
        		}else{
        			pf_price = map['pfVOList'][i]['pf_price'];
        		}
        		let lowpriceDiv = $("<div>").addClass("d-inline-block listprice px-0").attr({"name" : "listprice", "value": pf_price}).text(pf_price);
        		$("#block").find("#" + map['foodVOList'][i]['food_no']).find(".lowprice").after(lowpriceDiv);
        		for (let j = 1; j < 31; j++) {
                    let option = $("<option>").attr("value", j).text(j);
                    $("#block").find("#" + map['foodVOList'][i]['food_no']).find("[name='qty']").append(option);
                }
        		let food_id = map['foodVOList'][i]['food_no'];
        		$("#" + map['foodVOList'][i]['food_no']).find(".addcar").click(function() {
        			//console.log(food_id);
        			if ($("tbody").find("[id='" + food_id + "']").length == 0) {
        				//console.log($("tbody").find("[id='" + food_id + "']").length);
                        var tr = $("<tr>").attr("id", food_id);
                        var foodname = $(this).parent().find(".foodname").text();
                        var price = $(this).parent().find(".price").attr("value");
                        var listprice = $(this).parent().find(".listprice").attr("value");
                        var qty = $(this).parent().find("[name='qty']").val();

                        var cf_foodno = $("<input>").attr({
                            "type": "hidden",
                            "name": "cf_foodno",
                            "value": food_id
                        });
                        var cf_qty = $("<input>").attr({
                            "type": "hidden",
                            "name": "cf_qty",
                            "value": qty
                        });

                        var buttonminus = $("<button>").addClass("badge badge-secondary px-3 mx-1").attr("type", "button").append($("<h5>").text("-")).click(function() {
                            let oldqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
                            if (oldqty == 1) {
                                $("tbody").find("[id='" + food_id + "']").remove();
                            }
                            $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text(parseInt(oldqty) - 1);
                            let oldsum = $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text();
                            let price = $("tbody").find("[id='" + food_id + "']").find("[name='price']").text();
                            <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
                            if(/^\d+$/.test($("tbody").find("[id='" + food_id + "']").find("[name='listprice']").text())){
                            	price = $("tbody").find("[id='" + food_id + "']").find("[name='listprice']").text();
                            }
                            $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text(parseInt(oldsum) - parseInt(price));
                            let sumqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
                            let cf_qty = $("<input>").attr({
                                "type": "hidden",
                                "name": "cf_qty",
                                "value": sumqty
                            });
                            $("tbody").find("[id='" + food_id + "']").find("[name='qty']").append(cf_qty);
                            dynamicMoneySum();
                        });
                        var buttonadd = $("<button>").addClass("badge badge-secondary px-3 mx-1").attr("type", "button").append($("<h5>").text("+")).click(function() {
                            let oldqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
                            if(parseInt(oldqty) + 1 > 99){
                            	alert("數量不能大於99");
                            }else{
	                            $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text(parseInt(oldqty) + 1);
	                            let oldsum = $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text();
	                            let price = $("tbody").find("[id='" + food_id + "']").find("[name='price']").text();
	                            <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
	                            if(/^\d+$/.test($("tbody").find("[id='" + food_id + "']").find("[name='listprice']").text())){
	                            	price = $("tbody").find("[id='" + food_id + "']").find("[name='listprice']").text();
	                            }
	                            $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text(parseInt(oldsum) + parseInt(price));
	                            let sumqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
	                            let cf_qty = $("<input>").attr({
	                                "type": "hidden",
	                                "name": "cf_qty",
	                                "value": sumqty
	                            });
	                            $("tbody").find("[id='" + food_id + "']").find("[name='qty']").append(cf_qty);
	                            dynamicMoneySum();
                            }
                        });
                        var buttondelete = $("<button>").addClass("badge badge-secondary px-3 mx-1").attr("type", "button").append($("<h5>").text("刪除")).click(function() {
                            $("tbody").find("[id='" + food_id + "']").remove();
                            dynamicMoneySum();
                        });
                        var buttongroup = $("<td>");
                        buttongroup.append(buttonminus).append(buttonadd).append(buttondelete);
                        tr.append($("<td>").text(foodname).append(cf_foodno));
                        tr.append($("<td>").attr("name", "price").text(price));
                        tr.append($("<td>").attr("name", "listprice").text(listprice));
                        tr.append($("<td>").attr("name", "qty").text(qty).append(cf_qty));
                        <%--如果促銷價格不是數字就用price計算小計金額--%>
                        if(!/^\d+$/.test(listprice)){
                        	tr.append($("<td>").attr("name", "sum").text(parseInt(price) * parseInt(qty)));
                        }else{
                        	tr.append($("<td>").attr("name", "sum").text(parseInt(listprice) * parseInt(qty)));
                        }
                        tr.append(buttongroup);
                        $("#sumRow").before(tr);
                        dynamicMoneySum();

                    } else {
                        let oldqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
                        let qty = $(this).parent().find("[name='qty']").val();
                        if(parseInt(oldqty) + parseInt(qty) > 99){
                        	alert("數量不能大於99");
                        }else{
	                        $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text(parseInt(oldqty) + parseInt(qty));
	                        let sumqty = $("tbody").find("[id='" + food_id + "']").find("[name='qty']").text();
	                        let cf_qty = $("<input>").attr({
	                            "type": "hidden",
	                            "name": "cf_qty",
	                            "value": sumqty
	                        });
	                        $("tbody").find("[id='" + food_id + "']").find("[name='qty']").append(cf_qty);
	
	                        let oldsum = $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text();
	                        let price = $(this).parent().find(".price").attr("value");
	                        <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
	                        if(/^\d+$/.test($(this).parent().find(".listprice").attr("value"))){
	                        	price = $(this).parent().find(".listprice").attr("value");
	                        }
	                        let qty2 = $(this).parent().find("[name='qty']").val();
	                        $("tbody").find("[id='" + food_id + "']").find("[name='sum']").text(parseInt(oldsum) + parseInt(qty2) * parseInt(price));
	                        dynamicMoneySum();
                        }
                    }
        		});
			}
        	
		}
		function registEventListener(){
			var items = document.getElementsByClassName('linkToItems');
			for(var i = 0; i < items.length; i++){
				items[i].addEventListener('click', function(){
					var showdiv = document.getElementsByClassName('oneItem');
					showdiv[this.id].style.display="block";
					var bgdiv = document.getElementById('backgroundImg');
					bgdiv.style.display="block";
				},false);
			}
			
			var items = document.getElementsByClassName('items');
			for(var i = 0; i < items.length; i++){
				items[i].addEventListener('click', function(){
					var showdiv = document.getElementsByClassName('oneItem');
					showdiv[this.id].style.display="block";
					var bgdiv = document.getElementById('backgroundImg');
					bgdiv.style.display="block";
				},false);
			}
			
			var oneItem = document.getElementsByClassName('oneItem');
			for(var i = 0; i < oneItem.length; i++){
				oneItem[i].addEventListener('click', function(){
					var showdiv = document.getElementsByClassName('oneItem');
					showdiv[this.id].style.display="none";
					var bgdiv = document.getElementById('backgroundImg');
					bgdiv.style.display="none";
				},false);
			}
		}
		function closeOneItem(){
			var oneItem = document.getElementsByClassName('oneItem');
			var showdiv = document.getElementsByClassName('oneItem');
			for(var i = 0; i < oneItem.length; i++){
				showdiv[i].style.display="none";
				var bgdiv = document.getElementById('backgroundImg')
				bgdiv.style.display="none";
			}
		}
		function dynamicMoneySum(){
			var sum = 0;
			$("td[name='sum']").each(function(){
				sum = sum + parseInt($(this).text());
			});
			$("#moneySum").text(sum);
		}
		$(function(){
				<%
				//註冊食材購物車的按鈕click事件
				if(food_vdno.equals(carFood_vdno)){
					for(int i = 0; i < carFoodlist.size(); i++){
						CarFoodVO carFoodVO = carFoodlist.get(i);
	                	FoodVO foodVO = foodSvc.getOneFood(carFoodVO.getCf_foodno());
				%>
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(1)").click(function() {
		                let oldqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                if (oldqty == 1) {
		                    $("tbody").find("[id='<%=foodVO.getFood_no() %>']").remove();
		                    dynamicMoneySum();
		                }
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text(parseInt(oldqty) - 1);
		                let oldsum = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text();
		                let price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='price']").text();
		                <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
		                if(/^\d+$/.test($("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text())){
		                	price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text();
		                }
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text(parseInt(oldsum) - parseInt(price));
		                let sumqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                let cf_qty = $("<input>").attr({
		                    "type": "hidden",
		                    "name": "cf_qty",
		                    "value": sumqty
		                });
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").append(cf_qty);
		                dynamicMoneySum();
		            });
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(2)").click(function() {
		                let oldqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                if(parseInt(oldqty) + 1 > 99){
                        	alert("數量不能大於99");
                        }else{
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text(parseInt(oldqty) + 1);
			                let oldsum = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text();
			                let price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='price']").text();
			                <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
			                if(/^\d+$/.test($("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text())){
			                	price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text();
			                }
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text(parseInt(oldsum) + parseInt(price));
			                let sumqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
			                let cf_qty = $("<input>").attr({
			                    "type": "hidden",
			                    "name": "cf_qty",
			                    "value": sumqty
			                });
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").append(cf_qty);
			                dynamicMoneySum();
                        }
		            });
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(3)").click(function() {
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").remove();
		                dynamicMoneySum();
		            });
	            <%	}
	            }%>
	            //註冊食材訂單的按鈕click事件
	            <%
				if(request.getParameter("om_no") != null){
					//session.getAttribute("orderMasterVO")
					String om_no2 = request.getParameter("om_no");
					//String om_no2 = ((OrderMasterVO)session.getAttribute("orderMasterVO")).getOm_no();
					/*取得食材訂單明細*/
                	OrderFoodService orderFoodSvc = new OrderFoodService();
                	List<OrderFoodVO> orderFoodList = orderFoodSvc.getOrderFoodsByOmno(om_no2);
					for(int i = 0; i < orderFoodList.size(); i++){
						OrderFoodVO orderFoodVO = orderFoodList.get(i);
	                	FoodVO foodVO = foodSvc.getOneFood(orderFoodVO.getOf_foodno());
				%>
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(1)").click(function() {
		                let oldqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                if (oldqty == 1) {
		                    $("tbody").find("[id='<%=foodVO.getFood_no() %>']").remove();
		                }
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text(parseInt(oldqty) - 1);
		                let oldsum = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text();
		                let price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='price']").text();
		                <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
		                if(/^\d+$/.test($("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text())){
		                	price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text();
		                }
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text(parseInt(oldsum) - parseInt(price));
		                let sumqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                let cf_qty = $("<input>").attr({
		                    "type": "hidden",
		                    "name": "cf_qty",
		                    "value": sumqty
		                });
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").append(cf_qty);
		                dynamicMoneySum();
		            });
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(2)").click(function() {
		                let oldqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
		                if(parseInt(oldqty) + 1 > 99){
                        	alert("數量不能大於99");
                        }else{
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text(parseInt(oldqty) + 1);
			                let oldsum = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text();
			                let price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='price']").text();
			                <%--如果促銷價格是數字就用促銷價格計算小計金額--%>
			                if(/^\d+$/.test($("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text())){
			                	price = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='listprice']").text();
			                }
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='sum']").text(parseInt(oldsum) + parseInt(price));
			                let sumqty = $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").text();
			                let cf_qty = $("<input>").attr({
			                    "type": "hidden",
			                    "name": "cf_qty",
			                    "value": sumqty
			                });
			                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("[name='qty']").append(cf_qty);
			                dynamicMoneySum();
                        }
		            });
					$("tbody").find("[id='<%=foodVO.getFood_no() %>']").find("button:nth-of-type(3)").click(function() {
		                $("tbody").find("[id='<%=foodVO.getFood_no() %>']").remove();
		                dynamicMoneySum();
		            });
	            <%	}
	            }%>
	            
	            
			/*預設顯示第一個食材分類的食物商品*/
			<% if(foodtypelist.size() > 0){%>
			$.ajax({
				url: "<%= request.getContextPath()%>/carfood/Carfood.do",
            	type: "POST",
            	data: {
                    action: "getFood",
                    food_ftno: "<%=foodtypelist.get(0).getFood_ftno()%>",
                    food_vdno: "<%=food_vdno%>"
                },
                success: function(data) {
                	$("#block").empty();
                    var map2 = JSON.parse(data);
                    foodtype(map2);
                    registEventListener();
                }
			});
			<%}%>
			dynamicMoneySum();
		});
	</script>
</body>
</html>