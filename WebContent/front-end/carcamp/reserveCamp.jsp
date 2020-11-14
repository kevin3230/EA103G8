<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.List"%>
<%@ page import="com.carcamp.model.*"%>

<%
	// 先從request取(新訂單或訂單未完成)，沒有再從session取(修改訂單，且須搭配主訂單編號)
	List<CarCampVO> carCampVOList = (List<CarCampVO>) request.getAttribute("carCampVOList");
	if (carCampVOList != null && carCampVOList.size() > 0) {
		pageContext.setAttribute("carCampVO", carCampVOList.get(0));
	}
	else {
		String om_no = request.getParameter("om_no");
		carCampVOList = (List<CarCampVO>) session.getAttribute("carCampVOList");
		if ((carCampVOList != null && carCampVOList.size() > 0) && om_no != null)
			pageContext.setAttribute("carCampVO", carCampVOList.get(0));
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>預約營位</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/carcamp/css/reservedNav.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/carcamp/css/reserveCamp.css">
</head>
<body>
	<jsp:useBean id="vendorSvc" scope="page" class="com.vendor.model.VendorService"/>
	<jsp:useBean id="campSvc" scope="page" class="com.camp.model.CampService"/>

	<!-- header開始 -->
	<%@ include file="/front-end/index/header.jsp" %>
	<!-- header結束 -->

	<!-- 預約導覽列開始 -->
	<%@ include file="/front-end/carcamp/reservedNav.jsp" %>
	<!-- 預約導覽列結束 -->

	<!-- 本文開始 -->
	<div class="container">
		<div class="row justify-content-center">
			<ul id="top" class="mb-3"></ul>
		</div>
		<!-- 選擇營位區域 -->
		<div class="row justify-content-center">
			<div id="mainTitle" class="col-10 py-2">
				<div class="fs20">
					${vendorSvc.getOneVendor(vd_no).vd_cgname}
				</div>
				<div>
					<ul class="d-inline-block">
						<li class="bg-light text-dark d-inline-block px-1 mr-1 fs12">悠閒</li>
						<li class="bg-light text-dark d-inline-block px-1 mr-1 fs12">適合家庭</li>
						<li class="bg-light text-dark d-inline-block px-1 mr-1 fs12">適合情侶</li>
						<li class="bg-light text-dark d-inline-block px-1 mr-1 fs12">適合獨自旅遊</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="row justify-content-center">
			<div class="col-10 fs15 border">
				<img class="border mb-2" src="<%=request.getContextPath()%>/front-end/carcamp/images/map9-1.jpg">
				<span class="fs16">請選擇營位區域：</span>
				<select id="campPicker" class="mb-2">
					<option value=""></option>
					<c:forEach var="campVO" items="${campSvc.getCampsByVdno(vd_no)}">
						<c:if test="${campVO.camp_stat == 2}">
							<option value="${campVO.camp_no}">
								${campVO.camp_name}
							</option>
						</c:if>
					</c:forEach>
				</select>
			</div>
		</div>

		<!-- 選擇預計入住日期 -->
		<div id="selectDate" class="d-none">
			<div class="row justify-content-center">
				<div class="title col-10">
					請點擊您預計入住日期
				</div>
			</div>
			<!-- 選擇月份 -->
			<div class="row justify-content-center">
				<div id="monthPicker" class="col-10 justify-content-center">
					<ul class="d-flex justify-content-center">
						<!-- 4個月份選單 -->
						<li></li>
						<li></li>
						<li></li>
						<li></li>
					</ul>
				</div>
			</div>
			<!-- 月曆 -->
			<div class="row justify-content-center">
				<div class="col-10 text-center px-0">
					<table id="calendar">
						<thead>
							<tr>
								<th class="sunday">日</th>
								<th class="workDay">一</th>
								<th class="workDay">二</th>
								<th class="workDay">三</th>
								<th class="workDay">四</th>
								<th class="workDay">五</th>
								<th class="saturday">六</th>
							</tr>
						</thead>
						<tbody id="calBody"></tbody>
					</table>
				</div>
			</div>
		</div>

		<!-- 選擇營位數量及天數 -->
		<div id="selectQty">
			<div class="row justify-content-center">
				<div class="title col-10">
					請輸入營位數量及預計入住天數
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-10 px-0">
					<form id="reservedForm" method="POST" action="<%=request.getContextPath()%>/carcamp/carCamp.do">
						<table id="info">
							<tr>
								<th>露營地名稱</th>
								<td>${vendorSvc.getOneVendor(vd_no).vd_cgname}</td>
							</tr>
							<tr>
								<th>營位區域</th>
								<td>
									<div>${campSvc.getOneCamp(carCampVO.cc_campno).camp_name}</div>
									<input type="hidden" value="${carCampVO.cc_campno}" name="cc_campno" class="is-invalid">
									<div class="invalid-feedback"></div>
								</td>
							</tr>
							<tr>
								<th>預計入住日期</th>
								<td>
									<div></div>
									<input type="hidden" value="${carCampVO.cc_start}" name="cc_start" class="is-invalid">
									<div class="invalid-feedback"></div>
								</td>
							</tr>
							<tr>
								<th>營位數量</th>
								<td>
									<select name="cc_qty" class="is-invalid"></select>&nbsp;個
									<div class="invalid-feedback"></div>
								</td>
							</tr>
							<tr>
								<th>停留天數</th>
								<td>
									<select name="duration" class="is-invalid"></select>&nbsp;天
									<div class="invalid-feedback"></div>
								</td>
							</tr>
						</table>
						<c:if test="${not empty param.om_no}">
							<input type="hidden" name="om_no" value="${param.om_no}">
						</c:if>
						<input type="hidden" name="vd_no" value="${vd_no}">
						<input type="hidden" name="action" value="addCarCamp">
					</form>
				</div>
			</div>
		</div>
		<div id="listDetail" class="d-none">
			<div class="row justify-content-center">
				<div class="title col-10">
					請確認使用營地費用
				</div>
			</div>
			<div class="row justify-content-center">
				<div class="col-10 px-0">
					<table id="estimation">
						<thead>
							<tr class="text-center">
								<td></td>
								<td></td>
								<td></td>
								<td>單價</td>
								<td>數量</td>
								<td>金額</td>
							</tr>
						</thead>
						<tbody id="estBody">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="btn" class="row justify-content-center">
			<button type="button" id="return" onclick="history.back()" class="btn btn-lg mr-3" disabled>返回上一頁</button>
			<button type="button" onclick="reservedForm.submit()" class="btn btn-danger btn-lg ml-3">下一步 (租借裝備)</button>
		</div>
	</div>
	<!-- 本文結束 -->


	<!-- footer開始 -->
<%-- 	<%@ include file="/front-end/index/footer.jsp" %> --%>
	<!-- footer結束 -->


	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<%@ include file="/front-end/index/Notice.jsp"%>
	<script>
		var contextPath = "<%=request.getContextPath()%>";
		var dayOfMon = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
		var dayName = ["日", "一", "二", "三", "四", "五", "六"];
		var now = new Date();
		var today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
		var campVO = null;
		var campAvailVO = null;
		var promotionVO = null;
		var promoCampVO = null;

		// 原有資料
		var cc_campno = "${carCampVO.cc_campno}"
		var cc_start = "${carCampVO.cc_start}";
		var cc_end = "${carCampVO.cc_end}";
		var cc_qty = "${carCampVO.cc_qty}";
		var action = "${param.action}";
		// 錯誤訊息
		var errorMsgs = <%=request.getAttribute("errorMsgs")%>;

		$(function() {
			// 預約步驟標示
			$("#step1").addClass("here");

			// 產生月份選項
			var i = 0;
			$("#monthPicker").find("li").each(function() {
				var epoch = new Date(now.getFullYear(), now.getMonth() + i++);
				var value = regexpInt(epoch.getFullYear(), 4) + "-" + regexpInt(epoch.getMonth() + 1, 2);
				var html = epoch.getFullYear() + "年&nbsp;<strong>" + (epoch.getMonth() + 1) + "</strong>&nbsp;月";
				$(this).attr("value", value).html(html);

				// 月曆選項事件註冊
				$(this).click(function() {
					$("#monthPicker .active").toggleClass("active");
					$(this).toggleClass("active");
					genCalendar($(this).attr("value"));

					if (campAvailVO !== null && cc_start.length > 0) {
						var index = cc_start.lastIndexOf("-");
						if ($(this).attr("value") === cc_start.substring(0, index))
							$("#" + cc_start).click();
					}
				});

				if (i === 1)
					$(this).click();
			});

			// 選擇營位
			$("#campPicker").change(function() {
				getCampVO($(this).val());

				$(this).prop("disabled", true);						// 暫時鎖定營位選擇器
				$(this).find("[value='']").prop("disabled", true);	// 永久鎖定空白選項
				$("#selectDate").removeClass("d-none");				// 永久顯示日期選擇月曆

				// 修改顯示資訊與表單內容
				var campName = $(this).find(":selected").text();
				$("[name='cc_campno']").prev().text(campName);
				$("[name='cc_campno']").val($(this).val());	
			});

			$("[name='cc_qty']").change(function() {
				editDurSelector();
				genEstTable();
			});

			$("[name='duration']").change(function() {
				editCc_qtySelector();
				genEstTable();
			});

			// 代入原有資料(如果有)
			if (cc_campno.length > 0) {
				$("#campPicker").val(cc_campno).change();
				
				// 確保得到營位剩餘資料後再代入其他資訊
				var handler = setInterval(function() {		
					if (campAvailVO == null)
						console.log("我在絕 情谷底");
					else {
						clearInterval(handler);

						if (cc_start.length > 0) {
							var index = cc_start.lastIndexOf("-");
							$("li[value='" + cc_start.substring(0, index) + "']").click();
							// $("#" + cc_start).click();
						}
						if (cc_qty.length > 0) {
							$("[name='cc_qty']").find("option[value='" + cc_qty + "']").prop("selected", true);
						}
						if (cc_end.length > 0) {
							var dayLength = (new Date(cc_end) - new Date(cc_start)) / 86400000;
							$("[name='duration']").find("option[value='" + dayLength + "']").prop("selected", true);
						}

						genEstTable();
					}
				}, 250);
			}

			// 輸出錯誤訊息
			$.each(errorMsgs, function(index, str) {
				var name = str.split(":")[0];
				var msg = str.split(":")[1];
				if (name === "top")
					$("#top").append($("<li>").text(msg));
				else
					$("[name='" + name + "']").next().text(msg);
			});
		});

		function getCampVO(cc_campno) {
			$.ajax({
				url: contextPath + "/carcamp/carCamp.do",
				type: "POST",
				data: {
					action: "getOneCamp",
					camp_no: cc_campno
				},
				// async: false,
				success: function(data) {
					campVO = JSON.parse(data);
					getPromoCampVO(cc_campno);
					getCampAvailVO(cc_campno);
				}
			});
		}

		function getCampAvailVO(cc_campno) {
			$.ajax({
				url: contextPath + "/carcamp/carCamp.do",
				type: "POST",
				data: {
					action: "getCampAvailsByCampno",
					camp_no: cc_campno
				},
				// async: false,
				success: function(data) {
					campAvailVO = JSON.parse(data);
					campAvailVOSoup();
					console.log(campAvailVO);
					
					$("#campPicker").prop("disabled", false);	// 解除鎖定營位選擇器
					showCampAvail();
					editCc_qtySelector();
					editDurSelector();
					genEstTable();
				}
			});
		}

		function getPromoCampVO(cc_campno) {
			$.ajax({
				url: contextPath + "/carcamp/carCamp.do",
				type: "POST",
				data: {
					action: "getPromoCampsByCampno",
					camp_no: cc_campno
				},
				async: false,
				success: function(data) {
					promoCampVO = JSON.parse(data);
					$.each(promoCampVO, function(index, e) {
						getPromotionVO(e.pc_prono);
						e.pro_start = promotionVO.pro_start;
						e.pro_end = promotionVO.pro_end;
					});
				}
			});
		}

		function getPromotionVO(pc_prono) {
			$.ajax({
				url: contextPath + "/carcamp/carCamp.do",
				type: "POST",
				data: {
					action: "getOnePromo",
					pro_no: pc_prono
				},
				async: false,
				success: function(data) {
					promotionVO = JSON.parse(data);
					promotionVO.pro_start = new Date(promotionVO.pro_start);
					promotionVO.pro_end = new Date(promotionVO.pro_end);
				}
			});
		}

		function genCalendar(yr_mon) {
			var str = yr_mon.split("-");
			var yr = parseInt(str[0]);
			var mon = parseInt(str[1]);
			if (mon === 2) {
				if (yr % 400 === 0 ||
					yr % 4 === 0 && yr % 100 !== 0)
					dayOfMon[1] = 29;
				else
					dayOfMon[1] = 28;					
			}

			// 產生月曆
			$("#calBody").empty();		// 移除之前的月曆
			var firstDay = new Date(yr, mon - 1, 1).getDay();		// 該月第一天是星期幾
			var date = 1;
			while (date <= dayOfMon[mon - 1]) {
				var tr = $("<tr>");
				for (let day = 0; day < 7; day++) {
					var td = $("<td>");
					// 顏色樣式
					if (date === 1 && day < firstDay || date > dayOfMon[mon - 1]) {		// 該月1日不是星期日 或 該月不是結束在星期六
						td.addClass("void");
					} else {
						if (day === 0) {
							td.addClass("sunday");
						} else if (day === 6) {
							td.addClass("saturday");
						} else {
							td.addClass("workDay");
						}

						td.append($("<div>").attr("id", fullDate(yr, mon, date)).text(date));
						if (new Date(yr, mon - 1, date).getTime() === today.getTime())
							td.append($("<div>").text("—"));
						else
							td.append($("<div>")).append($("<div>")).append($("<div>"));
						date++;
					}
					tr.append(td);
				}
				$("#calBody").append(tr);
			}
			if (campAvailVO !== null)
				showCampAvail();
		}

		function showCampAvail() {
			$(".avail").off("click");								// 取消原本註冊事件
			$("#calBody").find("*").removeClass("avail vacancy full few del");		// 移除原本class
			$("#calBody").find("div:nth-child(3)").text(null);		// 清除原本金額
			$("#calBody").find("div:nth-child(4)").text(null);		// 清除原本金額

			for (let i = 0; i < campAvailVO.length; i++) {
				var td = $("#" + campAvailVO[i].ca_date).parent();
				campAvailVO[i].ca_qty > campVO.camp_qty * 0.2

				if (campAvailVO[i].ca_qty > 0) {
					td.addClass("avail");
					if (campAvailVO[i].ca_qty > campVO.camp_qty * 0.2)
						td.find("div:nth-child(2)").addClass("vacancy").text("充足");
					else
						td.find("div:nth-child(2)").addClass("few").text("少量");

					if (campAvailVO[i].price < campVO.camp_price) {
						td.find("div:nth-child(3)").addClass("del").text(formatMoney(campVO.camp_price));
						td.find("div:nth-child(4)").addClass("discount").text(formatMoney(campAvailVO[i].price));
					}
					else {
						td.find("div:nth-child(3)").text(formatMoney(campVO.camp_price));
					}
				} else {
					td.find("div:nth-child(2)").addClass("full").text("客滿");
				}
			}

			// 若active的日期變成客滿
			if ($("#calendar .active").find(".full").length) {
				$("#calendar .active").removeClass("active");
				cc_start = null;
				$("[name='cc_start']").val(null);
				$("[name='cc_start']").prev().text(null);

 				editCc_qtySelector();
				editDurSelector();
				genEstTable();
			}

			$(".avail").click(function() {
				$("#calendar .active").toggleClass("active");
				$(this).toggleClass("active");

				// 設定開始住宿日
				cc_start = $(this).find("[id]").attr("id");
				var str = cc_start.split("-");
				var day = new Date(cc_start).getDay();			// 取得星期幾
				var text = str[0] + "年" + str[1] + "月" + str[2] + "日 (" + dayName[day] + ")";
				$("[name='cc_start']").val(cc_start);
				$("[name='cc_start']").prev().text(text);
				
 				editCc_qtySelector();	// 修改營位數量選單
				editDurSelector();		// 修改停留天數選單
				genEstTable("avail");			// 顯示明細
			});
		}

		function genEstTable(fromWhere) {
			// console.log("genEstTable from: " + fromWhere);
			if (cc_start.length === 0 ||		// 尚未選取入住日期
				$("[name='cc_qty']").val() === null || $("[name='duration']").val() === null) {
				$("#listDetail").addClass("d-none");
				return;
			}
			
			$("#estBody").empty();		// 清空原有估價
			$("#listDetail").removeClass("d-none");

			var str = cc_start.split("-");
			var yr = parseInt(str[0]);
			var mon = parseInt(str[1]);
			var date = parseInt(str[2]);
			var qty = parseInt($("[name='cc_qty']").val());
			var dur = parseInt($("[name='duration']").val());
			var total = 0;

			for (let i = 1; i <= dur; i++) {
				var epoch = new Date(yr, mon - 1, date + i - 1);
				var tr = null;
				var th = null;
				var td = null;
				var dateStr = fullDate(epoch.getFullYear(), (epoch.getMonth() + 1), epoch.getDate());
				var index = campAvailVO.findIndex(function(x) { return x.ca_date === dateStr; })

				// 單品項合計
				tr = $("<tr>");
				th = $("<th>").attr("rowspan", "2").addClass("text-center align-top pt-3");
				th.append($("<div>").text("第" + i + "晚"));
				th.append($("<div>").text(epoch.getFullYear() + "年" + (epoch.getMonth() + 1) + "月" + epoch.getDate() + "日"));
				tr.append(th);
				tr.append($("<th>").text("使用營地費用"));
				tr.append($("<td>").addClass("px-3").text("成人"));
				tr.append($("<td>").addClass("text-right px-3").text(formatMoney(campAvailVO[index].price) + "元"));	// 單價
				tr.append($("<td>").addClass("text-center").text(qty));		// 數量
				tr.append($("<td>").addClass("text-right fs14 text-danger px-3").text(formatMoney(campAvailVO[index].price * qty) + "元"));
				$("#estimation tbody").append(tr);

				// 單日合計
				td = $("<td>").attr("colspan", "5").addClass("checkout");
				td.append($("<span>").addClass("mr-3").text("第" + i + "晚"));
				td.append($("<span>").text("小計"));
				td.append($("<span>").addClass("subtotal fs16").text(formatMoney(campAvailVO[index].price * qty) + "元"));
				$("#estimation tbody").append($("<tr>").append(td));
				total += campAvailVO[index].price * qty;
			}

			// 全日合計
			var td = $("<td>").attr("colspan", "6").addClass("checkout");
			td.append($("<span>").text("合計"));
			td.append($("<span>").attr("id", "sum").addClass("fs20").text(formatMoney(total) + "元"));
			$("#estimation tbody").append($("<tr>").append(td));
		}

		function editCc_qtySelector() {
			// 1. 同時滿足天數和數量
			// 2. 滿足數量
			// 3. 天數和數量都不滿足

			// 若沒有選擇日期，清空選單
			if (cc_start.length === 0) {
				$("[name='cc_qty']").empty();
				return;
			}

			// 記錄原有設定
			var qty = ($("[name='cc_qty']").val() === null) ? 0 : parseInt($("[name='cc_qty']").val());
			var dur = ($("[name='duration']").val() === null) ? 0 : parseInt($("[name='duration']").val());

			// 清空選單
			$("[name='cc_qty']").empty();

			var index = campAvailVO.findIndex(function(x) { return x.ca_date === cc_start; });
			var nopt = 9;
			for (var i = 0; i < dur; i++) {
				if (index + i >= campAvailVO.length ||
					campAvailVO[index + i].ca_qty === 0 || campAvailVO[index + i].ca_qty < qty) {	// 現有數量"不"足以支撐原本天數
					dur = 0;
					break;
				} else {
					nopt = Math.min(nopt, campAvailVO[index + i].ca_qty);		// 若天數不變，需要管期間每天的數量
				}
			}

			if (dur === 0) {
				$("[name='duration']").val(null);
				nopt = Math.min(campAvailVO[index].ca_qty, 9);		// 若天數有改變，則只需要管當天的數量
			}

			for (let i = 1; i <= nopt; i++)
				$("[name='cc_qty']").append($("<option>").val(i).text(i));

			if (qty > nopt)		// 現有數量不足
				$("[name='cc_qty']").val(null);
			else
				$("[name='cc_qty']").val(qty);
		}

		function editDurSelector() {
			if (cc_start.length === 0) {
				$("[name='duration']").empty();
				return;
			}

			// 記錄原有設定
			var qty = ($("[name='cc_qty']").val() === null) ? 0 : parseInt($("[name='cc_qty']").val());
			var dur = ($("[name='duration']").val() === null) ? 0 : parseInt($("[name='duration']").val());

			// 清空選單
			$("[name='duration']").empty();

			var index = campAvailVO.findIndex(function(x) { return x.ca_date === cc_start; });
			for (var i = 0; i < 9; i++) {
				if (index + i >= campAvailVO.length ||
					campAvailVO[index + i].ca_qty === 0 || campAvailVO[index + i].ca_qty < qty) {		// 現有數量"不"足以支撐原本天數
					break;
				} else {
					$("[name='duration']").append($("<option>").val(i + 1).text(i + 1));
				}
			}

			if (i === 0)		// 現有數量無法滿足原本數量
				$("[name='cc_qty']").val(null)

			if (i < dur)
				$("[name='duration']").val(null);
			else
				$("[name='duration']").val(dur);
		}

		function fullDate(yr, mon, date) {
			return regexpInt(yr, 4) + "-" + regexpInt(mon, 2) + "-" + regexpInt(date, 2);
		}

		function regexpInt(num, digit) {
			var str = num.toString();
			if (str.length < digit) {
				for (let i = str.length; i < digit; i++)
					str = "0" + str;
			}
			return str;
		}

		function formatMoney(money) {
			var regexp = /(\d+)(\d{3})((,\d{3})*)/;
			if (typeof(money) !== "string")
				money = money.toString();
			if (!regexp.test(money))
				return money;
			else {
				money = money.replace(regexp, "$1,$2$3");
				return formatMoney(money);
			}
		}

		function campAvailVOSoup() {
			var t = today.getTime();
			for (let i = 1; i <= 90; i++) {		// 只考慮未來90天
				var epoch = new Date(t += 86400000);
				var dateStr = fullDate(epoch.getFullYear(), (epoch.getMonth() + 1), epoch.getDate());
				var index = campAvailVO.findIndex(function(x) { return x.ca_date === dateStr; });

				var price = campVO.camp_price;
				for (let j = 0; j < promoCampVO.length; j++) {
					if (new Date(dateStr) >= promoCampVO[j].pro_start && new Date(dateStr) <= promoCampVO[j].pro_end) {
						if (promoCampVO[j].pc_price < price)
							price = promoCampVO[j].pc_price;
					}
				}

				if (index >= 0) {
					campAvailVO[index].price = price;

					// 修改訂單模式要先把數量加回
					if (action === "editOrder" &&
						new Date(dateStr) >= new Date(cc_start) && new Date(dateStr) < new Date(cc_end)) {
						campAvailVO[index].ca_qty += parseInt(cc_qty);
					}
				}
				else {
					campAvailVO.push({
						ca_campno: campVO.camp_no,
						ca_qty: campVO.camp_qty,
						ca_date: dateStr,
						price: price
					});
				}
			}

			campAvailVO.sort(function(a, b) {
				var a_date = parseInt(a.ca_date.replaceAll("-", ""));
				var b_date = parseInt(b.ca_date.replaceAll("-", ""));
				return a_date - b_date;
			});
		};
	</script>
</body>
</html>