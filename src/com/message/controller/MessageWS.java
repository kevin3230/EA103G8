package com.message.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.*;
import com.members.model.MembersService;
import com.message.model.*;
import com.vendor.model.VendorService;

@ServerEndpoint("/MessageWS/{userNo}")
public class MessageWS {
	
	private static Gson gson = new Gson();
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	private static Map<String, Set<String>> userNoGroupMap = new ConcurrentHashMap<>();
	
	@OnOpen
	public void onOpen(@PathParam("userNo") String userNo, Session userSession) {
		Set<String> userHistoryFriends = JedisHandleMessage.getHistoryFriends(userNo); // friend
		Map<String, Long> userHistoryFriendsWithTime = JedisHandleMessage.getHistoryFriendsWithTime(userNo); // friend(k) - last chat time long(v)
		
		// Put userSession to sessionsMap.
		sessionsMap.put(userNo, userSession);
		
		// Put userNo history chat targets as a group to userNoGroupMap.
		userNoGroupMap.put(userNo, userHistoryFriends);
		
		/***** Send userNo's history chat targets to front-end. *****/
		if(userHistoryFriends.size() > 0) {
			// Check history friends is online or not.
			Map<String, Integer> userHistoryFriendsWithStatus = new HashMap<String, Integer>();
			for(String userNoi : userHistoryFriends) {
				if(sessionsMap.get(userNoi) != null) {
					userHistoryFriendsWithStatus.put(userNoi, 1);
				}else {
					userHistoryFriendsWithStatus.put(userNoi, 0);
				}
			}
			// Get userName from userNo
			Map<String, String> userHistoryFriendsWithName = new HashMap<String, String>();
			for(String userNoi : userHistoryFriends) {
				if("M".equals(userNoi.substring(0, 1))) {
					MembersService memSvc = new MembersService();
					userHistoryFriendsWithName.put(userNoi, memSvc.getOneMem(userNoi).getMem_name());
				}else {
					VendorService vdSvc = new VendorService();
					userHistoryFriendsWithName.put(userNoi, vdSvc.getOneVendor(userNoi).getVd_cgname());
				}
			}
			// Create ChatListVO JSON and send to front-end.
			ChatList chatList = new ChatList("initial", userNo, userHistoryFriendsWithName, userHistoryFriendsWithTime, userHistoryFriendsWithStatus);
			String chatListJson = gson.toJson(chatList);
			userSession.getAsyncRemote().sendText(chatListJson);
			
			// Determine opening session is a member of opened session's group or not to update online status.
			for(String userNoi : sessionsMap.keySet()) {
				if(userNoGroupMap.get(userNoi).contains(userNo)) {
					OnlineStatus onlineStatusObj = new OnlineStatus("status", 1, userNo, userHistoryFriendsWithTime.get(userNoi));
					String onlineStatusJson = gson.toJson(onlineStatusObj);
					sessionsMap.get(userNoi).getAsyncRemote().sendText(onlineStatusJson);
				}
			}
		}
		
		/***** If userNo is a brand new user *****/
		if(userHistoryFriends.size() == 0) {
			System.out.println("MessageWS : " + userNo + " is a new chat user.");
		}
		
	}
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		
		ChatMessage incomingMsg = gson.fromJson(message, ChatMessage.class);
		String sender = incomingMsg.getSender();
		String receiver = incomingMsg.getReceiver();
		
		if("history".equals(incomingMsg.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
			}
		}
		
		if("chat".equals(incomingMsg.getType())) {
			Session receiverSession = sessionsMap.get(receiver);
			
			// Add sender's name in message to prevent newly message receiver
			VendorService vdSvc = new VendorService();
			MembersService memSvc = new MembersService();
			JsonObject chatJson = JsonParser.parseString(message).getAsJsonObject();
			String chatJsonMsg = null;
			if("M".equals(sender.substring(0, 1))) {
				chatJson.addProperty("senderName", memSvc.getOneMem(sender).getMem_name());
				chatJsonMsg = gson.toJson(chatJson);
			}else {
				chatJson.addProperty("senderName", vdSvc.getOneVendor(sender).getVd_cgname());
				chatJsonMsg = gson.toJson(chatJson);
			}
			
			// sender & receiver both online.
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(chatJsonMsg);
				userSession.getAsyncRemote().sendText(chatJsonMsg);
				JedisHandleMessage.saveChatMessage(sender, receiver, chatJsonMsg);
//				System.out.println("Message received, send to both side : " + chatJsonMsg);
			// sender online, leave message to receiver.
			}else if(userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(chatJsonMsg);
				JedisHandleMessage.saveChatMessage(sender, receiver, chatJsonMsg);
//				System.out.println("Message received, send to sender only : " + chatJsonMsg);
			}
		}
		
		if("button".equals(incomingMsg.getType())) {
			VendorService vdSvc = new VendorService();
			MembersService memSvc = new MembersService();
			JsonObject chatJson = JsonParser.parseString(message).getAsJsonObject();
			// Add receiver's name in message to show chat_list on sender's chat list.
			if("M".equals(receiver.substring(0, 1))) {
				chatJson.addProperty("receiverName", memSvc.getOneMem(receiver).getMem_name());
			}else {
				chatJson.addProperty("receiverName", vdSvc.getOneVendor(receiver).getVd_cgname());
				
			}
			// Check if receiver is online or offline
			if(sessionsMap.keySet().contains(receiver)) {
				chatJson.addProperty("receiverStatus", 1);
			}else {
				chatJson.addProperty("receiverStatus", 0);
			}
			if(userSession != null && userSession.isOpen()) {
				String chatJsonMsg = gson.toJson(chatJson);
				userSession.getAsyncRemote().sendText(chatJsonMsg);
			}
			// Add buttonUser to userNoGroupMap so to detect buttonUser will online or not.
			userNoGroupMap.get(sender).add(receiver);
		}

	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNoClose = null;
		Set<String> userNos = sessionsMap.keySet();
		
		// Remove closing session from maps & get userSession's userNo.
		for (String userNo : userNos) {
			if (sessionsMap.get(userNo).equals(userSession)) {
				userNoClose = userNo;
				sessionsMap.remove(userNo);
				userNoGroupMap.remove(userNo);
				break;
			}
		}
		
		// Send closing session status data to all online user which his group contains closing session.
		if (userNoClose != null) {
			OnlineStatus offlineStatusObj = new OnlineStatus("status", 0, userNoClose, new java.util.Date().getTime());
			String offlineStatusJson = gson.toJson(offlineStatusObj);
			Set<String> onlineUserNos = userNoGroupMap.keySet();
			for(String onlineUser : onlineUserNos) {
				if(userNoGroupMap.get(onlineUser).contains(userNoClose)) {
					sessionsMap.get(onlineUser).getAsyncRemote().sendText(offlineStatusJson);
				}
			}
		}

		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
				reason.getCloseCode().getCode(), userNos);
//		System.out.println(text);
	}
	
	private Set<Session> userNo2session(Set<String> userNoSet, Map<String, Session> sessionsMap){
		Set<Session> result = new HashSet<Session>();
		
		for(String userNoi : userNoSet) {
			Session userSession = sessionsMap.get(userNoi);
			if(userSession != null) {
				result.add(userSession);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
	}
	
}
