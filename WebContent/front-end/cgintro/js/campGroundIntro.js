$(function() {
	var lastOrderLong = 90;		// 單位：日
	$("#lastOrderLong").text(lastOrderLong);

	var lastOrderDay = new Date(new Date().getTime() + 86400000 * 90);
	$("#lastOrderDay").text(fullDate(lastOrderDay.getFullYear(), lastOrderDay.getMonth() + 1, lastOrderDay.getDate()));

	// 生成thumbnail
	var thumbnail = $("#thumbnail ul");
	$("#mainPic img").each(function() {
		var img = $("<img>").attr("src", $(this).attr("src"));
		var li = $("<li>").addClass("pr-1 py-1");
		thumbnail.append(li.append(img));

		// 註冊thumbnail事件
		li.click(function() {
			$("#thumbnail .active").toggleClass("active");
			$("#mainPic .active").toggleClass("active");
			$("[src='" + $(this).find("img").attr("src") + "']").parent().toggleClass("active");
		});
	});

	// 展示預設大圖的thumbnail
	var activeImg = $("#mainPic .active img");
	$("#thumbnail").find("[src='" + activeImg.attr("src") + "']").parent().toggleClass("active");

	$("#mainPic").on('slid.bs.carousel', function () {
		var activeImg = $("#mainPic .active img");
		$("#thumbnail .active").toggleClass("active");
		$("#thumbnail").find("[src='" + activeImg.attr("src") + "']").parent().toggleClass("active");
	});
	
	$("#reserve").click(function() {
		window.open(contextPath + "/carcamp/carCamp.do?action=reserveCamp&vd_no=" + vd_no);
	});
});

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