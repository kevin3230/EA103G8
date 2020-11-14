package com.waterfall.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.waterfall.model.WFPicService;
import com.waterfall.model.WFPicVO;
import com.waterfall.model.WFReplyService;
import com.waterfall.model.WFReplyVO;
import com.waterfall.model.WaterfallService;
import com.waterfall.model.WaterfallVO;

@MultipartConfig
public class WaterfallServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		
		if ("getOne_For_Display".equals(action)) { // 顯示單篇文章

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String wf_no = req.getParameter("wf_no");

				/***************************2.開始查詢資料*****************************************/
				WaterfallService waterfallSvc = new WaterfallService();
				WaterfallVO waterfallVO = waterfallSvc.getOneWaterfall(wf_no);
				if (waterfallVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/waterfall/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				List<WFReplyVO> list = waterfallSvc.joinWFReply(wf_no);
				
				WFPicService wfpSvc = new WFPicService();
				List<WFPicVO> listPic = wfpSvc.getAll(wf_no);
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("waterfallVO", waterfallVO); // 資料庫取出的waterfallVO物件,存入req
				req.setAttribute("list", list);
				req.setAttribute("listPic", listPic);
				String url = "/front-end/waterfall/listOneWaterfall.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneWaterfall.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================

		if ("getOne_For_Update".equals(action)) { // 來自listAllWaterfall.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				String wf_no = new String(req.getParameter("wf_no"));
				
				/***************************2.開始查詢資料****************************************/
				WaterfallService waterfallSvc = new WaterfallService();
				WaterfallVO waterfallVO = waterfallSvc.getOneWaterfall(wf_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("waterfallVO", waterfallVO);         // 資料庫取出的waterfallVO物件,存入req
				String url = "/front-end/waterfall/update_waterfall_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_waterfall_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================
		
		if ("update".equals(action)) { // 來自update_waterfall_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String wf_no = req.getParameter("wf_no");
				String wf_memno = req.getParameter("wf_memno");
				String wf_title = req.getParameter("wf_title").trim();
				if (wf_title == null || wf_title.trim().length() == 0) {
					errorMsgs.add("文章標題請勿空白");
				}else if(wf_title.trim().length() > 30) {
					errorMsgs.add("文章標題請勿超過十字");
				}	
				String wf_content = req.getParameter("wf_content").trim();
				if (wf_content == null || wf_content.trim().length() == 0) {
					errorMsgs.add("文章內容請勿空白");
				}else if(wf_content.trim().length() > 1000) {
					errorMsgs.add("文章內容請勿超過五百字");
				}
				
				WaterfallVO waterfallVO = new WaterfallVO();
				waterfallVO.setWf_title(wf_title);
				waterfallVO.setWf_content(wf_content);
				waterfallVO.setWf_no(wf_no);
				waterfallVO.setWf_memno(wf_memno);
				Collection<Part> parts = req.getParts();
				List<WFPicVO> listPic = new ArrayList<WFPicVO>();
				
				if(parts!=null) {
//					(int i =0; i<parts.length;i++)
					for(Part part : parts) {
						String partName = part.getName();
						
						if(part.getContentType() != null && part.getSubmittedFileName().trim().length() != 0){
							InputStream is = part.getInputStream();
							
							byte[] wfp_pic = null;
							wfp_pic = new byte[is.available()];
							
							is.read(wfp_pic);
							is.close();
							
							WFPicVO wfpicVO = new WFPicVO();
							wfpicVO.setWfp_pic(wfp_pic);
							
							listPic.add(wfpicVO);
						}
					}
				}else {
					System.out.println("no pic");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("waterfallVO", waterfallVO); // 含有輸入格式錯誤的waterfallVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/waterfall/update_waterfall_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				WaterfallService waterfallSvc = new WaterfallService();
				waterfallSvc.updateWaterfall(wf_title, wf_content, wf_no);
				WFPicService wfpicSvc = new WFPicService();
				if(listPic!=null) {
					for(int i = 0;i<listPic.size();i++) {
						wfpicSvc.addWFPic(waterfallVO.getWf_no(), listPic.get(i).getWfp_pic());
					}
				}
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("waterfallVO", waterfallVO); // 資料庫update成功後,正確的的waterfallVO物件,存入req
//				String url ="/waterfall/waterfall.do?action=getOne_For_Display&wf_no="+wf_no;
				String url ="/front-end/waterfall/updateSuccess.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneWaterfall.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/update_waterfall_input.jsp");
				failureView.forward(req, res);
			}
		}

//===============================================================================================================

        if ("insert".equals(action)) { // 來自addWaterfall.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String wf_memno = req.getParameter("wf_memno");

				System.out.println(wf_memno);
				String wf_title = req.getParameter("wf_title").trim();
				if (wf_title == null || wf_title.trim().length() == 0) {
					errorMsgs.add("文章標題請勿空白");
				}else if(wf_title.trim().length() > 30) {
					errorMsgs.add("文章標題請勿超過十字");
				}
				String wf_content = req.getParameter("wf_content").trim();
				if (wf_content == null || wf_content.trim().length() == 0) {
					errorMsgs.add("文章內容請勿空白");
				}else if(wf_content.trim().length() > 1000) {
					errorMsgs.add("文章內容請勿超過五百字");
				}
				
				WaterfallVO waterfallVO = new WaterfallVO();
				waterfallVO.setWf_memno(wf_memno);
				waterfallVO.setWf_title(wf_title);
				waterfallVO.setWf_content(wf_content);
				
				Collection<Part> parts = req.getParts();
				List<WFPicVO> listPic = new ArrayList<WFPicVO>();
				
				if(parts!=null) {
//					(int i =0; i<parts.length;i++)
					for(Part part : parts) {
						String partName = part.getName();
						
						if(part.getContentType() != null && part.getSubmittedFileName().trim().length() != 0){
							InputStream is = part.getInputStream();
							
							byte[] wfp_pic = null;
							wfp_pic = new byte[is.available()];
							
							is.read(wfp_pic);
							is.close();
							
							WFPicVO wfpicVO = new WFPicVO();
							wfpicVO.setWfp_pic(wfp_pic);
							
							listPic.add(wfpicVO);
						}
					}
				}else {
					System.out.println("no pic");
				}
				

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("waterfallVO", waterfallVO); // 含有輸入格式錯誤的waterfallVO物件,也存入req
//					req.setAttribute("wfpicVO", wfpicVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/waterfall/addWaterfall.jsp");
					failureView.forward(req, res);
					return;
				}
				/***************************2.開始新增資料***************************************/
				
				WaterfallService waterfallSvc = new WaterfallService();
				waterfallVO = waterfallSvc.addWaterfall(wf_memno, wf_title, wf_content);
				
				WFPicService wfpicSvc = new WFPicService();
				if(listPic!=null) {
					for(int i = 0;i<listPic.size();i++) {
						wfpicSvc.addWFPic(waterfallVO.getWf_no(), listPic.get(i).getWfp_pic());
					}
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
//				String url = "/EA103G8/front-end/waterfall/addSuccess.jsp";
//				res.sendRedirect(url);
				
				String url = "/front-end/waterfall/addSuccess.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllWaterfall.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/addWaterfall.jsp");
				failureView.forward(req, res);
			}
		}

//===============================================================================================================		

		if ("delete".equals(action)) { // 來自listAllWaterfall.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wf_no = new String(req.getParameter("wf_no"));
				String whichpage = new String(req.getParameter("whichpage"));
				System.out.println(wf_no);
				/***************************2.開始刪除資料***************************************/
				WFPicService wfpicSvc = new WFPicService();
				wfpicSvc.deleteWFPic(wf_no);
				
				WFReplyService wfreplySvc = new WFReplyService();
				wfreplySvc.deleteWFReply(wf_no);
				
				WaterfallService waterfallSvc = new WaterfallService();
				waterfallSvc.deleteWaterfall(wf_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/waterfall/listAllWaterfall.jsp?whichPage="+whichpage;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
		
//===============================================================================================================		
		if ("fake_delete".equals(action)) { // 來自listAllWaterfall.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				String wf_no = new String(req.getParameter("wf_no"));
				String whichpage = new String(req.getParameter("whichpage"));
				System.out.println(wf_no);
				/***************************2.開始刪除資料***************************************/
				
				WaterfallService waterfallSvc = new WaterfallService();
				waterfallSvc.fakeDeleteWaterfall(wf_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/waterfall/listAllWaterfall.jsp?whichPage="+whichpage;
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/waterfall/listAllWaterfall.jsp");
				failureView.forward(req, res);
			}
		}
		
	}
}
