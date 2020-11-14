<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.vendor.model.*"%>
<%@ page import="com.members.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
<title>createCNotice</title>
</head>
<body>
	<div class="infotitle">
		<div class="orderinfo">
		<input type="button" id="sendorder" class="button" value="Connect" onclick="connect();" />
		<input type="submit" id="sendMessage" class="button" value="Send" onclick="sendMessage();" />
		<input type="button" id="disconnect" class="button" value="Disconnect" onclick="disconnect();" /> 
		</div>
	</div>
	<div id="notice">
	</div>

</body>
<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script>
	var MyPoint = "/CVNoticeWebsocket/{M000000001}";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var webSocket;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			var message = jsonObj.content;
			
			$("#notice").append(message + "<br>");
		};

		// webSocket.onclose = function(event) {
		// 	console.log("Disconnected!");
		// };
	}
	
	function sendMessage() {
			var jsonObj = {
				"sender" : 'M000000001',
				"receiver" : 'V000000001',
				"orderno" : 'O000000001',
				"content" : "",
				"type" : 'add'
			};
			webSocket.send(JSON.stringify(jsonObj));
		}
	
	function disconnect() {
		webSocket.close();
		$("#notice").fadeToggle("slow");
	}
	
	function updateFriendName(name) {
		statusOutput.innerHTML = name;
	}
</script>
</html>