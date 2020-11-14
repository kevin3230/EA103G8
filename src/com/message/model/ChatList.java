package com.message.model;

import java.util.Map;

public class ChatList {
	private String type;
	// the user changing the state
	private String userNo;
	// users with name.
	private Map<String, String> usersWithName;
	// users with last chat time.
	private Map<String, Long> usersWithTime;
	// users with current online status
	private Map<String, Integer> usersWithStatus;

	public ChatList(String type, String userNo, Map<String, String> usersWithName, Map<String, Long> usersWithTime,
			Map<String, Integer> usersWithStatus) {
		super();
		this.type = type;
		this.userNo = userNo;
		this.usersWithName = usersWithName;
		this.usersWithTime = usersWithTime;
		this.usersWithStatus = usersWithStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Map<String, String> getUsersWithName() {
		return usersWithName;
	}

	public void setUsersWithName(Map<String, String> usersWithName) {
		this.usersWithName = usersWithName;
	}

	public Map<String, Long> getUsersWithTime() {
		return usersWithTime;
	}

	public void setUsersWithTime(Map<String, Long> usersWithTime) {
		this.usersWithTime = usersWithTime;
	}

	public Map<String, Integer> getUsersWithStatus() {
		return usersWithStatus;
	}

	public void setUsersWithStatus(Map<String, Integer> usersWithStatus) {
		this.usersWithStatus = usersWithStatus;
	}

}
