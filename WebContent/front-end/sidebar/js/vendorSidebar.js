        $(function() {
            var loc = location.pathname.substring(location.pathname.lastIndexOf("/") + 1);
            // 監聽aButton同當前頁面網址時亮燈
            $(".aButton").each(function() {
                var href = $(this).find("a.btn").attr("href");
                if (href.lastIndexOf(loc) !== -1) {
                    $(this).toggleClass("card-header-active");
                    console.log($(this));
                }
            });
            
            // 點擊aButton時觸發內部a標籤連結
            $(".aButton").click(function() {
				 location = $(this).find("a.btn").attr("href");
            });
            
            // 監聽aButton2同當前頁面網址時亮燈，並讓其card-header-btn也亮燈並展開
            $("a.aButton2").each(function() {
                var href = $(this).attr("href");
                if (href.lastIndexOf(loc) !== -1) {
                    $(this).toggleClass("aButton2-active");
                    $(this).closest(".collapse").prev(".card-header-btn").toggleClass("card-header-active");
                    $(this).closest(".collapse").toggleClass("show");
                }
            });

            // 點擊card-header-btn展開底下折疊
            $(".card-header-btn").click(function(event) {
                $(event.target).find("button").click();
            });

        });