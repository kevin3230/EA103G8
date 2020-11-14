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

@ServerEndpoint("/CVNoticeWebsocket2/{memvd_no}")
public class CVNoticeWebsocket2 {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	OrderMasterDAO omDAO = new OrderMasterDAO();
	MembersDAO memDAO = new MembersDAO();
	VendorDAO DAO = new VendorDAO();
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
		System.out.println(first_no);
		if (first_no.equals("V")) {
			for (VNoticeVO vnoticeVO : Vlist) {
				if (memvd_no.equals(vnoticeVO.getVn_vdno())) {
					Session receiverSession = sessionsMap.get(vnoticeVO.getVn_vdno());
					
					String sender = "SYSTEM";
					String receiver = vnoticeVO.getVn_vdno();
					String content = vnoticeVO.getVn_content();
//					String orderno = (vnoticeVO.getVn_content()).substring(content.lastIndexOf("O"));
					String orderno = "O";
					String type = vnoticeVO.getVn_type();
					String stat = (vnoticeVO.getVn_stat()).toString();
					
					OrderMessage newVdMessage = new OrderMessage(sender, receiver, orderno, content, type, stat);
					receiverSession.getBasicRemote().sendText(gson.toJson(newVdMessage));
					System.out.println(gson.toJson(newVdMessage));
				}
			}
		}else if (first_no.equals("M")) {
			for (CNoticeVO cnoticeVO : Clist) {
				if (memvd_no.equals(cnoticeVO.getCn_memno())) {
					Session receiverSession = sessionsMap.get(cnoticeVO.getCn_memno());
					
					String sender = "SYSTEM";
					String receiver = cnoticeVO.getCn_memno();
					String content = cnoticeVO.getCn_content();
//					String orderno = (cnoticeVO.getCn_content()).substring(content.lastIndexOf("O"));
					String orderno = "O";
					String type = cnoticeVO.getCn_type();
					String stat = (cnoticeVO.getCn_stat()).toString();
					
					OrderMessage newVdMessage = new OrderMessage(sender, receiver, orderno, content, type, stat);
					receiverSession.getBasicRemote().sendText(gson.toJson(newVdMessage));
					System.out.println(gson.toJson(newVdMessage));
				}
			}
		}
		
		String text = String.format("Session ID = %s, connected; userName = %s", userSession.getId(), memvd_no);
		System.out.println(text);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		OrderMessage orderMessage = gson.fromJson(message, OrderMessage.class);
		String sender = orderMessage.getSender();				//發信人編號
		String receiver = orderMessage.getReceiver();			//收信人編號
		String orderno = orderMessage.getOrderno();				//主訂單編號
		String type = orderMessage.getType();					//訊息類型
		
		if (type.equals("add")) {
			String memcontent = "您於" + receiver + "成立了一筆新訂單，訂單編號:" +  orderno;	//會員訊息內容
			String stat = "1";		//新增狀態為未讀訊息
			
			//members訊息儲存&發送
			CNoticeJDBCDAO cnDAO = new CNoticeJDBCDAO();
			CNoticeVO cnoticeVO = new CNoticeVO();
			cnoticeVO.setCn_memno(sender);
			cnoticeVO.setCn_content(memcontent);
			cnoticeVO.setCn_type(type);
			cnoticeVO.setCn_stat(1);	//未讀
			cnDAO.add(cnoticeVO);
			
			OrderMessage newMemMessage = new OrderMessage(sender, receiver, orderno, memcontent, type, stat);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(newMemMessage));
				System.out.println("newMemMessage = " + gson.toJson(newMemMessage));
				}
			
			String vdcontent = "您有一筆來自" + sender + "的新訂單，訂單編號:" +  orderno;		
			//業者訊息內容
			VNoticeJDBCDAO vnDAO = new VNoticeJDBCDAO();
			VNoticeVO vnoticeVO = new VNoticeVO();
			vnoticeVO.setVn_vdno(receiver);
			vnoticeVO.setVn_content(vdcontent);
			vnoticeVO.setVn_type(type);
			vnoticeVO.setVn_stat(1);	//未讀
			vnDAO.add(vnoticeVO);
			
			//vendor訊息儲存&發送
			OrderMessage newVdMessage = new OrderMessage(sender, receiver, orderno, vdcontent, type, stat);
			Session receiverSession = sessionsMap.get(receiver);
			if (receiverSession != null && receiverSession.isOpen()) {
				receiverSession.getAsyncRemote().sendText(gson.toJson(newVdMessage));
				System.out.println("newVdMessage = " + gson.toJson(newVdMessage));
				}
			}
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
