package com.message.model;

public class ChatMessage {
	private String type;
	private String sender;
	private String receiver;
	private String message;
	private long timeLong;
	
	public ChatMessage(String type, String sender, String receiver, String message) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public long getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(long timeLong) {
		this.timeLong = timeLong;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
