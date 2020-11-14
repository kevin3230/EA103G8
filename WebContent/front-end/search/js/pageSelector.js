// 使用此js需要在引用的地方建立showPage(page)的function

var ndata = 0;

$(function() {
	$(".ndataPerPage").change(function() {
		$(".ndataPerPage").val($(this).val());	// 同步所有.ndataPerPage
		editPageNumber();
	});

	$(".pageNumber").change(function() {
		$(".pageNumber").val($(this).val());	// 同步所有.pageNumber
		showPage($(this).val());
	});
});

function editPageNumber() {
	$(".pageNumber").empty();
	if (ndata > 0) {
		var ndataPerPage = $(".ndataPerPage:first").val();
		var npage = Math.ceil(ndata / ndataPerPage);
		for (let i = 1; i <= npage; i++) {
			$(".pageNumber").append($("<option>").val(i).text("第" + i + "頁"));
		}
	}
	showPage(1);
}

function disablePageComp() {
	 $(".pageSelector").children("select").prop("disabled", true);
}

function enablePageComp() {
	$(".pageSelector").children("select").prop("disabled", false);
}

function editNdataInfo() {
	$(".ndataInfo").val("共 " + ndata + " 筆");
}

function hiddenSubPageComp() {
	$(".pageSelector:gt(0)").addClass("d-none");
	$(".pageSelector:gt(0)").removeClass("d-flex");
}

function showSubPageComp() {
	$(".pageSelector:gt(0)").addClass("d-flex");
	$(".pageSelector:gt(0)").removeClass("d-none");
}