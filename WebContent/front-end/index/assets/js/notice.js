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

                $("#newnotice").append(`<div class=\"newmess\" onclick=\"sendMessage();\">` + message + `</div>`);
                $(".newmess").attr("value", value);
                $(".newmess").attr("owner", owner);
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