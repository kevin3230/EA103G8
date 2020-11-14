package com.vnotice.model;

public class OrderMessage {
		private String sender;
		private String receiver; 
		private String orderno;
		private String content;
		private String type;
		private String stat;

		public OrderMessage(String sender, String receiver, String orderno, String content, 
				String type, String stat) {
			this.sender = sender;
			this.receiver = receiver;
			this.orderno = orderno;
			this.content = content;
			this.type = type;
			this.stat = stat;
		}
		
		public OrderMessage(String sender, String receiver, String content, String type, String stat) {
			this.sender = sender;
			this.receiver = receiver;
			this.content = content;
			this.type = type;
			this.stat = stat;
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

		public String getOrderno() {
			return orderno;
		}

		public void setOrderno(String orderno) {
			this.orderno = orderno;
		}
		
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
		
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getStat() {
			return stat;
		}

		public void setStat(String stat) {
			this.stat = stat;
		}
		
}
