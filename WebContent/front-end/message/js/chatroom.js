/***** If user login PLAMPING *****/
if(self == ""){
	document.getElementById("chatIcon").style.display = "none";
}

/***** Html element register actions *****/
// show chatroom when click fixed chat icon.
function showChatRoom(){
	document.getElementById("chatRoom").style.display = "block";
	document.getElementById("chatIcon").style.display = "none";
}

// close chatroom when click minimize icon.
document.querySelectorAll("i.fa-minus-square").forEach(function(minusIcon){
	minusIcon.onclick = function(){
		console.log("chatroom.js : minus clicked");
		document.getElementById("chatRoom").style.display = "none";
		document.getElementById("chatIcon").style.display = "block";
	}
});


// press enter to send message
document.querySelector("input.write_msg").onkeydown = function(event){
	if(event.keyCode == 13){
		sendMessage();
	}
};
// click sendBtn to send message
document.querySelector("button.msg_send_btn").onclick = function(){
	sendMessage();
};

// offline when close html.
document.querySelector("body").onunload = function(){
	webSocket.close();
}

// click "message me" button in cgintro
document.querySelectorAll("button.userBtn").forEach(function(btn){
	var receiver = btn.value;
	btn.onclick = function(){
		// check if vendor is in chatlist before or not.
		var isExist = false;
		var chatIbList = document.querySelectorAll("div.chat_ib");
		for(let i = 0; i < document.querySelectorAll("div.chat_ib").length; i++){
			var chatIbNo = chatIbList[i].querySelector("input").value;
			if(receiver === chatIbNo){
				isExist = true;
				break;
			}
		}
		// if vendor not in chat list, add new chat list.
		if(!isExist){
			registerNewChatList(self, receiver);	
		// add "alive_chat" class to the clicked vendor.
		}else{
			chatListPopTop(receiver, 1);
		}
		// show chat room when click "message me" button.
		showChatRoom();
	}
});
// send request to show new receiver when click "message me" button.
function registerNewChatList(sender, receiver){
	var registerMsg = {
		type: "button",
		sender: sender,
		receiver: receiver,
		message: "",
		timeLong: new Date().getTime()
	}
	webSocket.send(JSON.stringify(registerMsg));
}


/***** WebSocket event function *****/
//var MyPoint = "/MessageWS/${userNo}";
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

//var self = '${userNo}';
var webSocket;

// html tag DOM
var messageArea = document.querySelector("div.msg_history"); // div to store messages.
var chatListArea = document.querySelector("div.inbox_chat"); // div to store receivers.

document.querySelector("body").onload = function(){
	if(self != ""){
		webSocketFunc();
	}else{
		console.log("chatroom.jsp : this is a visitor.");	
	}
}

function webSocketFunc(){
	// create a websocket
	webSocket = new WebSocket(endPointURL);

	webSocket.onopen = function() {
		console.log(self + " Chatroom Connect Success!");
	};

	webSocket.onmessage = function(event) {
		var jsonObj = JSON.parse(event.data);
		
		if("initial" === jsonObj.type){
			// triggered when self logged in
			initialChatList(jsonObj);
		}
		
		if("status" === jsonObj.type){
			// triggered when anyone logged in
			console.log("96" + jsonObj.userNo);
			updateChatListStatus(jsonObj);
		}
		
		if("history" === jsonObj.type){
			// triggered when click on chat_list
			fillHistoryMsg(jsonObj);
			messageArea.scrollTop = messageArea.scrollHeight;
		}
		
		if("chat" === jsonObj.type){
			var sender = jsonObj.sender;
			var senderName = jsonObj.senderName;
			var receiver = jsonObj.receiver;
			var message = jsonObj.message;
			var time = parseInt(jsonObj.timeLong);
			// 1. sender receive chat message
			if(self === sender){
				messageArea.appendChild(createOutgoingMsg(message, time));
				messageArea.scrollTop = messageArea.scrollHeight;
			// 2. receiver receive chat message
			}else if(self === receiver){
				// 2-1 receiver received sender's message before.
				if(isChatBefore(sender, receiver)){
					// 2-1-1 receiver is focusing on sender.
					if(isReceiverFocusSender(sender, receiver)){
						messageArea.appendChild(createIncomingMsg(message, time));
						messageArea.scrollTop = messageArea.scrollHeight;
					// 2-1-2 receiver is not focusing on sender.
					}else{
						chatListPopTop(sender, 0);
					}
				// 2-2 receiver never received sender's message before.
				}else{
					var newChatList = createChatList(sender, senderName, time, 1);
					chatListArea.prepend(newChatList);
					clickChatList();
				}
			}
		}
		
		if("button" === jsonObj.type){
			var sender = jsonObj.sender;
			var receiver = jsonObj.receiver;
			var receiverName = jsonObj.receiverName;
			var receiverStatus = parseInt(jsonObj.receiverStatus);
			var time = parseInt(jsonObj.timeLong);
			var newChatList = createChatList(receiver, receiverName, time, receiverStatus);
			chatListArea.prepend(newChatList);
			clickChatList();
			newChatList.click();
		}
		
	};

	webSocket.onclose = function(event) {
		console.log(self + " Disconnected from chatroom.");
		// method01. add offline grayout div
		document.querySelector("div#grayOutArea").style.display = "block";
		// change msg area status to user.
		document.querySelector("div#emptyMsgArea").style.display = "none";
		document.querySelector("div#disconnectMsgArea").style.display = "block";
		
		// method02. add offline effect directly
//		document.querySelector("div#chatRoom").classList.add("offline");
//		document.querySelectorAll("div.chat_list").forEach(function(chatList){
//			chatList.classList.add("offLineBlock");
//		});
//		document.querySelector("div.input_msg_write").classList.add("offLineBlock");
	};
}

function isReceiverFocusSender(sender, receiver){
	var result = false;
	document.querySelectorAll("div.chat_list").forEach(function(chatList){
		if(chatList.classList.contains("active_chat")){
			var chatListNo = chatList.querySelector("input").value;
			if(sender === chatListNo){
				result =  true;
			}
		}
	});
	return result;
}

function isChatBefore(sender, receiver){
	result = false;
	document.querySelectorAll("div.chat_list").forEach(function(chatList){
		var chatListNo = chatList.querySelector("input").value;
		if(sender === chatListNo){
			result = true;
		}
	});
	return result;
}

function sendMessage() {
	var inputMessage = document.querySelector("input.write_msg");
	var friend = document.querySelector("div.active_chat").querySelector("input").value;
	var message = inputMessage.value.trim();
	var timeLong = new Date().getTime();

	if (message === "") {
		alert("Input a message");
		inputMessage.focus();
	} else if (friend === "") {
		alert("Choose a friend");
	} else {
		var jsonObj = {
			"type" : "chat",
			"sender" : self,
			"receiver" : friend,
			"message" : message,
			"timeLong" : timeLong.toString()
		}
		webSocket.send(JSON.stringify(jsonObj));
		inputMessage.value = "";
		inputMessage.focus();
	}
}

function chatListPopTop(userNo, isClick){
	/*
	isClick = 0, means don't click pop top item to focus on it.
	isClick = 1, means need to click pop top item to focus on it.
	 */
	var inboxChatDiv = document.querySelector("div.inbox_chat");
	var userNoChatList = null;
	document.querySelectorAll("div.chat_list").forEach(function(chatList){
		var chatListNo = chatList.querySelector("input").value;
		console.log("chatroom.js chatListNo = " + chatListNo);
		if(userNo === chatListNo){
			userNoChatList = chatList.cloneNode(true);
			chatList.remove();
		}
	});
	if(userNoChatList == null){
		console.log("chatListPopTop error : " + userNo + " is not on chat_list.");
	}else{
		inboxChatDiv.prepend(userNoChatList);
		clickChatList();
		if(isClick == 1){
			userNoChatList.click();
		}
	}
}

// click chat_list item to highlight bg-color & send history msg request.
function clickChatList(){
	var chatLists = document.querySelectorAll("div.chat_list");
	chatLists.forEach(function(chatList){
		chatList.onclick = function(){
			// Remove all chat_list div active_chat class name.
			chatLists.forEach(function(chatList2){
				chatList2.classList.remove("active_chat");
			});
			// Add target chat_list class name.
			this.classList.add("active_chat");
			// Send message to back-end
			var msg = {
					type: "history",
					sender: self,
					receiver: this.querySelector("input").value,
					message: "",
					timeLong: new Date().getTime()
			}
			webSocket.send(JSON.stringify(msg));
		}
	});
}

function fillHistoryMsg(jsonObj){
	var msgHistoryDiv = document.querySelector("div.msg_history");
	msgHistoryDiv.innerHTML = "";
	var historyMsg = JSON.parse(jsonObj.message);
	for(let i = 0; i < historyMsg.length; i++){
		let msgData = JSON.parse(historyMsg[i]);
		let msg = msgData.message;
		let time = parseInt(msgData.timeLong);
		if(self === msgData.sender){
			msgHistoryDiv.append(createOutgoingMsg(msg, time));
			
		}else{
			msgHistoryDiv.append(createIncomingMsg(msg, time));
		}
	}
}

function initialChatList(jsonObj){
	var usersWithName = jsonObj.usersWithName;
	var usersWithTime = jsonObj.usersWithTime;
	var usersWithStatus = jsonObj.usersWithStatus;
	var inboxChatDiv = document.querySelector("div.inbox_chat");
	for(let i = 0; i < Object.keys(usersWithTime).length; i++){
		let userNo = Object.keys(usersWithTime)[i];
		let userName = usersWithName[userNo];
		let status = parseInt(usersWithStatus[userNo]);
		let lastTime = parseInt(usersWithTime[userNo]);
		let chatList = createChatList(userNo, userName, lastTime, status);
		inboxChatDiv.appendChild(chatList);
	}
	clickChatList();
}

function updateChatListStatus(jsonObj){
	var userNo = jsonObj.userNo;
	var status = parseInt(jsonObj.status);
	var lastTime = parseInt(jsonObj.lastTimeLong);
	document.querySelectorAll("div.chat_ib").forEach(function(chat_ib){
		if(userNo === chat_ib.querySelector("input").value){
			if(status == 1){
				chat_ib.previousElementSibling.firstElementChild.setAttribute("class", "fas fa-circle");
				console.log(chat_ib.previousElementSibling.firstElementChild);
			}else{
				chat_ib.previousElementSibling.firstElementChild.setAttribute("class", "far fa-circle");
			}
		}
	});
}

function createChatList(userNo, userName, timeLong, status){
	// Create HTML tag
	var chatListDiv = document.createElement("div");
	chatListDiv.classList.add("chat_list");
	var chatPeopleDiv = document.createElement("div");
	chatPeopleDiv.classList.add("chat_people");
	var chatImgDiv = document.createElement("div");
	chatImgDiv.classList.add("chat_img");
	if(status == 0){
		chatImgDiv.innerHTML = '<i class="far fa-circle"></i>';
	}else{
		chatImgDiv.innerHTML = '<i class="fas fa-circle"></i>';
	}
	var chatIbDiv = document.createElement("div");
	chatIbDiv.classList.add("chat_ib");
	chatIbDiv.innerHTML = '<h5>' + userName + '</h5><input type="hidden" value="' + userNo + '"/>';
	var chatDateSpan = document.createElement("span");
	chatDateSpan.classList.add("chat_date");
	// Parse timeLong to monStr, dayStr
	var lastTimeDate = new Date(timeLong);
	var monStr = lastTimeDate.toLocaleString('en-US', { month: 'short' });
	var dayStr = lastTimeDate.getDate(); 
	chatDateSpan.innerText = monStr + " " + dayStr;
	// Combine
	chatListDiv.appendChild(chatPeopleDiv);
	chatPeopleDiv.appendChild(chatImgDiv);
	chatImgDiv.after(chatIbDiv);
	chatIbDiv.append(chatDateSpan);
	return chatListDiv;
}

function createIncomingMsg(message, timeLong){
	var timeDate = new Date(timeLong);
	// Create HTML tag
	var incomingMsgDiv = document.createElement("div");
	incomingMsgDiv.classList.add("incoming_msg");
	var receivedMsgDiv = document.createElement("div");
	receivedMsgDiv.classList.add("received_msg");
	var receivedWithdMsgDiv = document.createElement("div");
	receivedWithdMsgDiv.classList.add("received_withd_msg");
	receivedWithdMsgDiv.innerHTML = '<p>' + message + '</p>';
	var timeDateSpan = document.createElement("span");
	timeDateSpan.classList.add("time_date");
	var timeFormatStr = timeDate.toLocaleTimeString("en-US", {hour:'2-digit', minute:'2-digit'});
	var dateFormatStr = timeDate.toLocaleDateString('en-US', {month:'short', day:'2-digit'});
	timeDateSpan.innerText = timeFormatStr + '    |    ' + dateFormatStr;
	// Combine
	incomingMsgDiv.appendChild(receivedMsgDiv);
	receivedMsgDiv.appendChild(receivedWithdMsgDiv);
	receivedWithdMsgDiv.append(timeDateSpan);
	return incomingMsgDiv;
}

function createOutgoingMsg(message, timeLong){
	var timeDate = new Date(timeLong);
	// Create HTML tag
	var outgoingMsgDiv = document.createElement("div");
	outgoingMsgDiv.classList.add("outgoing_msg");
	var sentMsgDiv = document.createElement("div");
	sentMsgDiv.classList.add("sent_msg");
	sentMsgDiv.innerHTML = '<p>' + message + '</p>';
	var timeDateSpan = document.createElement("span");
	timeDateSpan.classList.add("time_date");
	var timeFormatStr = timeDate.toLocaleTimeString("en-US", {hour:'2-digit', minute:'2-digit'});
	var dateFormatStr = timeDate.toLocaleDateString('en-US', {month:'short', day:'2-digit'});
	timeDateSpan.innerText = timeFormatStr + '    |    ' + dateFormatStr;
	// Combine
	outgoingMsgDiv.appendChild(sentMsgDiv);
	sentMsgDiv.append(timeDateSpan);
	return outgoingMsgDiv;
}

