package com.message.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	// 此範例key的設計為(發送者名稱:接收者名稱)，實際應採用(發送者會員編號:接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();

	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = pool.getResource();;
		jedis.auth("123456");
		List<String> historyData = jedis.lrange(key, 0, -1);
		jedis.close();
		return historyData;
	}

	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		jedis.rpush(senderKey, message);
		jedis.rpush(receiverKey, message);

		jedis.close();
	}
	
	public static Set<String> getHistoryFriends(String sender){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		// Get all sender's friends.
		Set<String> srpairSet = jedis.keys(sender + "*"); // sender:receiver pair
		if(srpairSet.size() == 0) {
//			System.out.printf("JedisHandleMessage : %s has no friends. \n", sender);
			jedis.close();
			return new HashSet<String>();
		}
		Set<String> friendSet = new HashSet<String>();
		for(String srpair : srpairSet) {
			friendSet.add(srpair.split(":")[1]);
		}
		// Get lastChatTime-friend pair data
		Map<Long, String> timeFriendMap = new TreeMap<Long, String>();
		for(String friend : friendSet) {
			String key = new StringBuilder(sender).append(":").append(friend).toString();
			List<String> historyData = jedis.lrange(key, 0, -1);
			JsonArray historyJson = JsonParser.parseString(historyData.toString()).getAsJsonArray();
			long maxTimeLong = 0;
			for(JsonElement json : historyJson) {
				long chatTimeLong = json.getAsJsonObject().get("timeLong").getAsLong();
				if(chatTimeLong > maxTimeLong) {
					maxTimeLong = chatTimeLong;
				}
			}
			timeFriendMap.put(maxTimeLong, friend);
		}
		// Transform treeMap sort result to ArrayList<String>.
		List<String> resultList = new ArrayList<String>(timeFriendMap.values());
		Collections.reverse(resultList);
		Set<String> result = new LinkedHashSet<String>(resultList);
		
		jedis.close();
		return result;
	}
	
	public static Map<String, Long> getHistoryFriendsWithTime(String sender){
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		// Get all sender's history users.
		Set<String> srpairSet = jedis.keys(sender + "*"); // sender:receiver pair
		if(srpairSet.size() == 0) {
//			System.out.printf("JedisHandleMessage : %s has no friends. \n", sender);
			jedis.close();
			return new HashMap<String, Long>();
		}
		Set<String> friendSet = new HashSet<String>();
		for(String srpair : srpairSet) {
			friendSet.add(srpair.split(":")[1]);
		}
		// Get lastChatTime-friend pair data
		Map<Long, String> timeFriendMap = new TreeMap<Long, String>();
		for(String friend : friendSet) {
			String key = new StringBuilder(sender).append(":").append(friend).toString();
			List<String> historyData = jedis.lrange(key, 0, -1);
			JsonArray historyJson = JsonParser.parseString(historyData.toString()).getAsJsonArray();
			long maxTimeLong = 0;
			for(JsonElement json : historyJson) {
				long chatTimeLong = json.getAsJsonObject().get("timeLong").getAsLong();
				if(chatTimeLong > maxTimeLong) {
					maxTimeLong = chatTimeLong;
				}
			}
			timeFriendMap.put(maxTimeLong, friend);
		}
		// Transform treeMap sort result to ArrayList<String>.
		LinkedHashMap<String, Long> result = new LinkedHashMap<String, Long>();
		
//		// result order : from small to large
//		for(long treeMapKey : timeFriendMap.keySet()) {
//			result.put(timeFriendMap.get(treeMapKey), treeMapKey);
//		}
		
		// result order : from large to small
		List<Long> treeMapKeys = new ArrayList<Long>(); // last chat time
		for(long treeMapKey : timeFriendMap.keySet()) {
			treeMapKeys.add(treeMapKey);
		}
		for(int i = treeMapKeys.size() - 1; i >= 0; i--) {
			result.put(timeFriendMap.get(treeMapKeys.get(i)), treeMapKeys.get(i));
		}
		
		jedis.close();
		return result;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getHistoryFriendsWithTime("M000000001"));
////		String aa = new Gson().toJson(getHistoryFriends("M000000003"));
////		System.out.println(aa);
//	}
}
