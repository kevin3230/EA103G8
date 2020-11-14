package com.cnotice.controller;

import java.io.IOException;
import java.util.Collections;
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

import com.google.gson.Gson;
import com.ordermaster.model.*;
import com.members.model.*;
import com.vendor.model.*;
import com.cnotice.model.*;
import com.vnotice.model.*;

@ServerEndpoint("/CVNoticeWebsocket/{memvd_no}")
public class CVNoticeWebsocket {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	OrderMasterDAO omDAO = new OrderMasterDAO();
	OrderMasterVO omVO = new OrderMasterVO();
	MembersDAO memDAO = new MembersDAO();
	MembersVO memVO = new MembersVO();
	VendorDAO vdDAO = new VendorDAO();
	VendorVO vdVO = new VendorVO();
	CNoticeJDBCDAO cnDAO = new CNoticeJDBCDAO();
	CNoticeVO cnoticeVO = new CNoticeVO();
	List<CNoticeVO> Clist = cnDAO.getAllNew();
	VNoticeJDBCDAO vnDAO = new VNoticeJDBCDAO();
	VNoticeVO vnoticeVO = new VNoticeVO();
	List<VNoticeVO> Vlist = vnDAO.getAllNew();	
	
	@OnOpen
	public void onOpen(@PathParam("memvd_no") String memvd_no, Session userSession) throws IOException {
		sessionsMap.put(memvd_no, userSession);
		String first_no = memvd_no.substring(0, 1);
		if (first_no.equals("V")) {
			for (VNoticeVO vnoticeVO : Vlist) {
				if (memvd_no.equals(vnoticeVO.getVn_vdno())) {
					Session receiverSession = sessionsMap.get(vnoticeVO.getVn_vdno());
					//vendor訊息發送
					String senderno = (omDAO.findByPrimaryKey(vnoticeVO.getVn_omno())).getOm_memno();
					String sendername = (memDAO.findByPK(senderno)).getMem_alias();
					String receiverno = vnoticeVO.getVn_vdno();
					String receivername = (vdDAO.findByPrimaryKey(receiverno)).getVd_cgname();
					String orderno = vnoticeVO.getVn_omno();
					String content = vnoticeVO.getVn_content();
					String type = vnoticeVO.getVn_type();
					String stat = (vnoticeVO.getVn_stat()).toString();
					
					OrderMessage newVdMessage = new OrderMessage(senderno, receiverno, orderno, content, type, stat);
					receiverSession.getBasicRemote().sendText(gson.toJson(newVdMessage));
				}
			}
		}else if (first_no.equals("M")) {
			for (CNoticeVO cnoticeVO : Clist) {
				if (memvd_no.equals(cnoticeVO.getCn_memno())) {
					Session receiverSession = sessionsMap.get(cnoticeVO.getCn_memno());
					//members訊息發送
					String senderno = (omDAO.findByPrimaryKey(cnoticeVO.getCn_omno())).getOm_vdno();
					String sendername = (vdDAO.findByPrimaryKey(senderno)).getVd_cgname();
					String receiverno = cnoticeVO.getCn_memno();
					String receivername = (memDAO.findByPK(receiverno)).getMem_alias();
					String orderno = cnoticeVO.getCn_omno();
					String content = cnoticeVO.getCn_content();
					String type = cnoticeVO.getCn_type();
					String stat = (cnoticeVO.getCn_stat()).toString();
					
					OrderMessage newVdMessage = new OrderMessage(senderno, receiverno, orderno, content, type, stat);
					receiverSession.getBasicRemote().sendText(gson.toJson(newVdMessage));
					System.out.println(gson.toJson(newVdMessage));
				}
			}
		}
		
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), memvd_no);
		System.out.println(text);
	}

	public void newOrder(OrderMasterVO omVO) {
		//訊息儲存&發送
		String senderno = omVO.getOm_memno();
		String receiverno = omVO.getOm_vdno();
		String sendername = (memDAO.findByPK(senderno)).getMem_alias();
		String receivername = (vdDAO.findByPrimaryKey(receiverno)).getVd_cgname();
		String orderno = omVO.getOm_no();	
		System.out.println("CVNoticeWS : orderno = " + orderno);
		String type = "add";
		String stat = "1";
		//訊息內容
		String memcontent = sendername +"您於" + receivername +"成立了一筆新訂單，訂單編號:" + orderno;
		//members訊息儲存
		CNoticeJDBCDAO cnDAO = new CNoticeJDBCDAO();
		CNoticeVO cnoticeVO = new CNoticeVO();
		cnoticeVO.setCn_memno(senderno);
		cnoticeVO.setCn_omno(orderno);
		cnoticeVO.setCn_content(memcontent);
		cnoticeVO.setCn_type(type);
		cnoticeVO.setCn_stat(1);	//未讀
		cnDAO.add(cnoticeVO);
		
		//發送Websocket通知
		Session senderSession = sessionsMap.get(senderno);
		OrderMessage newMemMessage = new OrderMessage(senderno, receiverno, orderno, memcontent, type, stat);
		if (senderSession != null && senderSession.isOpen()) {
			senderSession.getAsyncRemote().sendText(gson.toJson(newMemMessage));
			System.out.println("newMemMessage = " + gson.toJson(newMemMessage));
		}
		
		//vendor訊息內容
		String vdcontent = receivername +"您有一筆" + sendername +"成立的新訂單，訂單編號:" + orderno;
		
		//vendor訊息儲存
		VNoticeJDBCDAO vnDAO = new VNoticeJDBCDAO();
		VNoticeVO vnoticeVO = new VNoticeVO();
		vnoticeVO.setVn_vdno(receiverno);
		vnoticeVO.setVn_omno(orderno);
		vnoticeVO.setVn_content(vdcontent);
		vnoticeVO.setVn_type(type);
		vnoticeVO.setVn_stat(1);	//未讀
		vnDAO.add(vnoticeVO);
		
		//發送Websocket通知
		Session receiverSession = sessionsMap.get(senderno);
		OrderMessage newVdMessage = new OrderMessage(receiverno, senderno, orderno, vdcontent, type, stat);
		if (receiverSession != null && receiverSession.isOpen()) {
			senderSession.getAsyncRemote().sendText(gson.toJson(newVdMessage));
			System.out.println("newVdMessage = " + gson.toJson(newVdMessage));
		
		}
	}
	
	
	@OnMessage
	public void onMessage(Session userSession, String message) {
		OrderMessage orderMessage = gson.fromJson(message, OrderMessage.class);
		String orderno = orderMessage.getOrderno();				//主訂單編號
		String receiver = orderMessage.getReceiver();			//回傳收信者
		String stat = orderMessage.getStat();					//改為0已讀
		String first_no = receiver.substring(0, 1);
		
		if (first_no.equals("V")) {
			VNoticeJDBCDAO vnDAO = new VNoticeJDBCDAO();
			VNoticeVO vnoticeVO = new VNoticeVO();
			
			vnoticeVO.setVn_stat(0);
			vnoticeVO.setVn_omno(orderno);
			vnDAO.updateStat(vnoticeVO);
			
		}else if (first_no.equals("M")) {
			CNoticeJDBCDAO cnDAO = new CNoticeJDBCDAO();
			CNoticeVO cnoticeVO = new CNoticeVO();
			
			cnoticeVO.setCn_stat(0);
			cnoticeVO.setCn_omno(orderno);
			cnDAO.updateStat(cnoticeVO);
			
		}

			
//		if (type.equals("add")) {
//			String memcontent = "您於" + receiver + "成立了一筆新訂單，訂單編號:" +  orderno;	//會員訊息內容
//			String stat = "1";		//新增狀態為未讀訊息
//			
//			//members訊息儲存&發送
//			CNoticeJDBCDAO cnDAO = new CNoticeJDBCDAO();
//			CNoticeVO cnoticeVO = new CNoticeVO();
//			cnoticeVO.setCn_memno(sender);
//			cnoticeVO.setCn_content(memcontent);
//			cnoticeVO.setCn_type(type);
//			cnoticeVO.setCn_stat(1);	//未讀
//			cnDAO.add(cnoticeVO);
//			
//			OrderMessage newMemMessage = new OrderMessage(sender, receiver, orderno, memcontent, type, stat);
//			if (userSession != null && userSession.isOpen()) {
//				userSession.getAsyncRemote().sendText(gson.toJson(newMemMessage));
//				System.out.println("newMemMessage = " + gson.toJson(newMemMessage));
//				}
//			
//			String vdcontent = "您有一筆來自" + sender + "的新訂單，訂單編號:" +  orderno;		
//			//業者訊息內容
//			VNoticeJDBCDAO vnDAO = new VNoticeJDBCDAO();
//			VNoticeVO vnoticeVO = new VNoticeVO();
//			vnoticeVO.setVn_vdno(receiver);
//			vnoticeVO.setVn_content(vdcontent);
//			vnoticeVO.setVn_type(type);
//			vnoticeVO.setVn_stat(1);	//未讀
//			vnDAO.add(vnoticeVO);
//			
//			//vendor訊息儲存&發送
//			OrderMessage newVdMessage = new OrderMessage(sender, receiver, orderno, vdcontent, type, stat);
//			Session receiverSession = sessionsMap.get(receiver);
//			if (receiverSession != null && receiverSession.isOpen()) {
//				receiverSession.getAsyncRemote().sendText(gson.toJson(newVdMessage));
//				System.out.println("newVdMessage = " + gson.toJson(newVdMessage));
//				}
//			}
		}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> memvd_nos = sessionsMap.keySet();
		for (String memvd_no : memvd_nos) {
			if (sessionsMap.get(memvd_no).equals(userSession)) {
				sessionsMap.remove(memvd_no);
				break;
			}
		}
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}
}
