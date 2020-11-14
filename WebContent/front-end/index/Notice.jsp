<script>
		var MyPoint;

		if ("${memVO.mem_no}" !== ""){
			MyPoint = "/CVNoticeWebsocket/${memVO.mem_no}";
		} else {
			MyPoint = "/CVNoticeWebsocket/${vendorVO.vd_no}";
		}

        var host = window.location.host;
        var wbpath = window.location.pathname;
        var webCtx = wbpath.substring(0, wbpath.indexOf('/', 1));
        var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

        var webSocket;

        window.onload = init;

        function init() {
            connect();
        }

        function connect() {
	        // create a websocket
	        webSocket = new WebSocket(endPointURL);

	        webSocket.onopen = function(event) {
	            console.log("Connect Success!");
	        };

	        webSocket.onmessage = function(event) {
	            var jsonObj = JSON.parse(event.data);
	            var message = jsonObj.content + "\r\n";
	            var value = jsonObj.orderno;
	            var owner = jsonObj.receiver;
	            console.log(jsonObj);


	            var url;
	           	var firstNo = owner.substring(0, 1);
	            if(firstNo === "M"){
	            	url = "<%=request.getContextPath()%>/front-end/membersorder/membersOrder.jsp";
	            }else if (firstNo === "V"){
	            	url = "<%=request.getContextPath()%>/front-end/membersorder/vendorsOrder.jsp";
	            }

	            $("#newnotice").append(`<a href=` + url + `><div class=\"newmess\" onclick=\"sendMessage();\">` + message + `</div></a>`);
	            $(".newmess").attr("value", value);
	            $(".newmess").attr("owner", owner); 		

		        var meslen = $(".newmess").length;
		        console.log(meslen);
		        if(meslen > 0){
		        	$("#navIcon a:eq(0)").css("color","#cd5c5c");
		        }

	        };

	        webSocket.onclose = function(event) {
	            console.log("Disconnected!");
	        };
        }

        function sendMessage() {
            var orderno = $(".newmess").attr("value");
            var receiver = $(".newmess").attr("owner");

            var jsonObj = {
                "orderno": orderno,
                "receiver": receiver,
                "stat": '0'
            };
            webSocket.send(JSON.stringify(jsonObj));
        }

        // OrderMessage newVdMessage = new OrderMessage(        sendername, receivername, orderno, content, type, stat);

        function disconnect() {
            webSocket.close();
            $("#notice").fadeToggle("slow");
        }

        function updateFriendName(name) {
            statusOutput.innerHTML = name;
        }
</script>