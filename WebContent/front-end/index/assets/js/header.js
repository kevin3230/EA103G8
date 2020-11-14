$(function() {
	var loc = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
	$(".nav-item").each(function() {
		var href = $(this).find("a").attr("href");
		if (href.lastIndexOf(loc) !== -1)
			$(this).toggleClass("active");
	})
});

$(".nav-item").click(function() {
	$(".active").toggleClass("active");
	$(this).toggleClass("active");
});