// show chatroom when click fixed chat icon.
function showChatRoom(){
	document.getElementById("chatRoom").style.display = "block";
	document.getElementById("chatIcon").style.display = "none";
}

// close chatroom when click minimize icon.
document.querySelector("div.msg_head i").onclick = function(){
	document.getElementById("chatRoom").style.display = "none";
	document.getElementById("chatIcon").style.display = "block";
};

// click recent chat_list item to highlight bg-color.
var chatLists = document.querySelectorAll("div.chat_list");
chatLists.forEach(function(chatList){
	chatList.onclick = function(){
		chatLists.forEach(function(chatList2){
			chatList2.classList.remove("active_chat");
		});
		this.classList.add("active_chat");
	}
});