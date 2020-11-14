package com.message.model;

public class OnlineStatus {
	private String type;
	private Integer status;
	private String userNo;
	private Long lastTimeLong;

	public OnlineStatus() {
	}

	public OnlineStatus(String type, Integer status, String userNo, Long lastTimeLong) {
		this.type = type;
		this.status = status;
		this.userNo = userNo;
		this.lastTimeLong = lastTimeLong;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Long getLastTimeLong() {
		return lastTimeLong;
	}

	public void setLastTimeLong(Long lastTimeLong) {
		this.lastTimeLong = lastTimeLong;
	}

}
