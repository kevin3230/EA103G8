var searchList = null;

$(function() {
	// 縣市選項設值
	$("#campArea li").each(function() {
		$(this).attr("value", $(this).text());
	});

	// 設定全選/全取消的行為
	$("#areaSelector .selectAll").click(function() {
		$("#areaSelector > ul").find("li").addClass("active");
	});

	$("#areaSelector .cancelAll").click(function() {
		$("#areaSelector > ul").find("li").removeClass("active");
	});

	$("#typeSelector .selectAll").click(function() {
		$("#typeSelector > ul").find("li").addClass("active");
	});

	$("#typeSelector .cancelAll").click(function() {
		$("#typeSelector > ul").find("li").removeClass("active");
	});

	$("#datePicker .cancel").click(function() {
		$("#datePicker input").val("");
	});

	// 設定除全選/全取消外的共同行為
	$("#areaSelector > ul").find("li").click(function() {
		$(this).toggleClass("active");
	});

	$("#typeSelector > ul").find("li").click(function() {
		$(this).toggleClass("active");
	});

	$("#promoSelector > ul").find("li").click(function() {
		$(this).toggleClass("active");
	});

	// 設定大地區選項
	$("#northern").click(function() {
		var li = $("#campArea").find("li:lt(4)");
		if ($(this).hasClass("active"))
			li.addClass("active");
		else
			li.removeClass("active");
	});

	$("#central").click(function() {
		var li = $("#campArea").find("li:gt(3):lt(3)");
		if ($(this).hasClass("active"))
			li.addClass("active");
		else
			li.removeClass("active");
	});

	$("#southern").click(function() {
		var li = $("#campArea").find("li:gt(6):lt(5)");
		if ($(this).hasClass("active"))
			li.addClass("active");
		else
			li.removeClass("active");
	});

	$("#eastern").click(function() {
		var li = $("#campArea").find("li:gt(11)");
		if ($(this).hasClass("active"))
			li.addClass("active");
		else
			li.removeClass("active");
	});

	// 註冊篩選條件變更事件
	$("#filter input").change(function() {
		search();
	});

	$("#filter li").click(function() {
		search();
	});

	// 隱藏底部的頁數切換器
	hiddenSubPageComp();
});

// 取資料
function search() {
	var filter = {};
	filter.campName = getValue("campName");
	filter.campStart = getValue("campStart");
	filter.campEnd = getValue("campEnd");
	filter.campArea = getValue("campArea");
	filter.campType = getValue("campType");
	filter.campPromo = getValue("campPromo");
	filter.campMinPrice = getValue("campMinPrice");
	filter.campMaxPrice = getValue("campMaxPrice");

	$.ajax({
		url: contextPath + "/search/search.do",
		type: "POST",
		data: {
			action: "searchCamp",
			filter: JSON.stringify(filter)
		},
		success: function(data) {
			searchList = JSON.parse(data);
			ndata = searchList.length;	// 給pageSelector使用
			editNdataInfo();
			editPageNumber();

			if (ndata === 0) {
				hiddenSubPageComp();
				disablePageComp();
			} else {
				showSubPageComp();
				enablePageComp();
			}
		}
	});
}

function getValue(varName) {
	var dom = $("#" + varName);
	if (dom.prop("tagName") === "INPUT") {
		return dom.val();
	} else if (varName === "campPromo") {
		return $("#" + varName + " .active").attr("value");
	} else {
		var val = [];
		$("#" + varName + " .active").each(function() {
			val.push($(this).attr("value"));
		});
		return val;
	}
}

function showPage(page) {
	var tbody = $("#resultList tbody");
	tbody.empty();

	var listHTML = `<td class="img-container mr-3"><img src=""></td>
					<td class="info-container">
						<div class="font-weight-bold fs24 vd_cgname"></div>
						<div class="flex-fill vd_cgaddr"></div>
						<div class="d-flex justify-content-end align-items-end">
							NT$&nbsp;
							<span class="minPrice"></span>
							&nbsp;元
							<span class="fs14">起</span>
						</div>
						<div class="d-flex justify-content-end">
							<button class="d-none">加入追蹤</button>
							<button class="mt-1"><a>預約營位</a></button>
						</div>
					</td>`;

	var ndataPerPage = $(".ndataPerPage:first").val();
	for (let i = (page - 1) * ndataPerPage; i < Math.min(page * ndataPerPage, searchList.length); i++) {
		var obj = searchList[i];

		var a = $("<a>").attr({
			"href": contextPath + "/cgintro/cgintro.do?action=goToCGIntro&vd_no=" + obj.vd_no,
			"target": "_blank"
		});

		var tr = $("<tr>").addClass("d-flex").html(listHTML);
		tr.find("a").attr("href", contextPath + "/carcamp/carCamp.do?action=reserveCamp&vd_no=" + obj.vd_no);
		tr.find("img").attr("src", contextPath + "/search/search.do?action=getOneCGIPPic&vd_no=" + obj.vd_no);
		tr.find(".vd_cgname").append(a.text(obj.vd_cgname));
		tr.find(".vd_cgaddr").text(obj.vd_cgaddr);
		if (obj.minListPrice === obj.minPrice) {
			tr.find(".minPrice").addClass("fs24").text(formatMoney(obj.minPrice));
		} else {
			tr.find(".minPrice").before($("<del>").text(formatMoney(obj.minListPrice)));
			tr.find(".minPrice").addClass("text-danger font-weight-bold fs28").text(formatMoney(obj.minPrice));
		}
		tbody.append(tr);
	}
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