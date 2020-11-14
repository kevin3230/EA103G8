package com.members.controller;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MemSignUpMail {
	static String to;
	static String str_name;
	static String verificationcode;
	
	public MemSignUpMail() {
	}
	
	public MemSignUpMail(String to, String str_name, String verificationcode) {
		this.to = to;
		this.str_name = str_name;
		this.verificationcode = verificationcode;
	}

	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String str_name, String verificationcode) {
		
		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			
			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
			// ●須將myGmail的【安全性較低的應用程式存取權】打開
			final String myGmail = "gustavosandia@gmail.com";
			final String myGmail_password = "wert79815";
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
			message.setSubject("PLAMPING認證通知");
			// 設定信中的內容
//			message.setText(messageText);
			message.setContent("Hello! " + str_name + " 歡迎註冊成為PLAMPING會員，請謹記開通密碼:<h3 style=\"color:red;\"> " + verificationcode + "</h3><br/>"
		      		+ "並點擊以下連結進行登入修改密碼:<br/><a href= \"http://localhost:8081/EA103G8/members/MemMailSignIn?mem_email=" + to
		      		+ "\">更改密碼</a>","text/html; charset=UTF-8");

			Transport.send(message);
			System.out.println("傳送成功!");
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}
}