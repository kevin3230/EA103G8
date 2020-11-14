<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.faq.model.*"%>
<%@ page import="com.faqtype.model.*"%>

<%
	FAQTypeService faqtSvc = new FAQTypeService();
	List<FAQTypeVO> list = faqtSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>常見問答</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<style>
		body {
   			background-color: #f2f2f2;
   			color: #444;
		}

	/* #a {
		position: fixed;
		top: 55px;
		z-index: 1;
	} */
	/* #b {
		position: relative;
		top: 10px;
	} */
	.faqType>.card-header {
		background-color: darkgray;
	}
	.faqDetail {
		/* border: none; */
	}
	/* :target::before {
		content: "";
		display: block;
		height: 100px;
	} */

	/* #title{
		height: 80px;
		background-image: url('<%= request.getContextPath()%>/front-end/camp/templated-hielo/images2/C000000001jpg');
		background-size: cover;
        background-position: center;
        
	} */

	</style>
</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<div class="d-none">
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li class="errorMsgs">${message}</li>
				</c:forEach>
			</ul>
		</div>
	</c:if>
	
	<div class="container min-vh-100">
		<div class="row justify-content-center align-items-center">
			<div class="d-flex col-8 my-3 align-items-center" id="title">
				<h3 class="text-left ml-3">常見問題</h3>
			</div>
			<div class="col-8 mb-3">
			<form method="post" action="<%=request.getContextPath()%>/faq/faq.do" novalidate>
				<div class="form-row">
	  				<div class="custom-file col-5 ml-auto">
	    				<input class="form-control" id="search" type="search" placeholder="Search" name="keyword" aria-label="Search">
     					<input type="hidden" name="action"value="getFAQs_By_Keyword">								
	 				</div>
					<div class="col">
						<button class="btn btn-outline-secondary" type="submit">Search</button>
					</div>
				</div>
			</form>
			</div>
			
			
			
			<!-- 文章NAV -->
			<!-- <div class="col-8">
				<div id="list-FAQ" class="list-group">
					<a class="list-group-item list-group-item-action" href="#list-item-1">露營常見問題</a>
					<a class="list-group-item list-group-item-action" href="#list-item-2">裝備租借問題</a>
					<a class="list-group-item list-group-item-action" href="#list-item-3">付款問題</a>
					<a class="list-group-item list-group-item-action" href="#list-item-4">網站使用問題</a>
					<a class="list-group-item list-group-item-action" href="#list-item-4">其他常見問題</a>
				</div>
			</div> -->

			<!-- 文章NAV -->
			<div class="col-8">
				<ul class="nav justify-content-satrt mr-auto" id="navbarFAQ">
					<c:forEach var="faqtypeVO1" items="${list}">
  					<li class="nav-item">
    					<a class="nav-link" id="navbar_${faqtypeVO1.faqt_no}" href="#card_${faqtypeVO1.faqt_no}">${faqtypeVO1.faqt_name}</a>
					</li>
					</c:forEach>
				</ul>
			</div>

			<div class="col-8">		  
		  	 	<jsp:useBean id="faqSvc" scope="page" class="com.faq.model.FAQService" />
		  		<c:forEach var="faqtypeVO" items="${list}">		  	
        		<div class="card faqType my-3">
          			<div class="card-header">
               			<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse_${faqtypeVO.faqt_no}" aria-expanded="false" aria-controls="collapse_${faqtypeVO.faqt_no}" id="card_${faqtypeVO.faqt_no}">
        		   			${faqtypeVO.faqt_name}
            			</button>
          			</div>
          			<div id="collapse_${faqtypeVO.faqt_no}" class="collapse">
		            	<div class="card-body p-0">
		            	
		            		<!-- 卡片中的卡片 -->
							<c:forEach var="faqVO" items="${faqSvc.getFAQsByFaqtno(faqtypeVO.faqt_no)}">
  							<div class="card faqDetail my-1">
  				    			<div class="card-header">
			        				<h2 class="mb-0">
          								<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse_${faqVO.faq_no}" aria-expanded="false" aria-controls="collapse_${faqVO.faq_no}">
            								${faqVO.faq_title}
  				        				</button>
  				      				</h2>
      							</div>
			      				<div id="collapse_${faqVO.faq_no}" class="collapse">
        							<div class="card-body">
          								${faqVO.faq_content}
			        				</div>
        							<div class="card-footer text-muted">
        								${faqVO.faq_edit}
        							</div>
      							</div>
			    			</div>
    						</c:forEach>   			
			            </div>
          			</div>
        		</div>
        		</c:forEach>
	  		</div>
		</div>
	</div>

	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->

	<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<%@ include file="/front-end/index/Notice.jsp"%>
	<script>
		// 點選navbar按鈕即可展開其對應之FAQtype
		$(document).ready(function(){
			$(".nav-link").click(function(event){
				// event.preventDefault();
				$("#collapse_" + event.target.id.substring(7)).collapse('toggle');				
			});

			// 點選card-header即可觸發其button效果
			$(".card-header").click(function(event){
				$(event.target).find("button").click();
			});
		
		
			// 顯示錯誤訊息：
        	// 0-請輸入關鍵字
	        // 1-查無資料
    	    // 2-無法取得資料
	        var needsVal = document.getElementsByTagName('input'); //抓取input類型
    	    var errorMsgs = document.getElementsByClassName("errorMsgs"); //抓取錯誤訊息
        	if (errorMsgs.length > 0) {
            	for (var i = 0; i < errorMsgs.length; i++) {
                 	var errorNumber = errorMsgs[i].innerText.substring(0, 1); // 錯誤編號
	                 needsVal[errorNumber].classList.add('is-invalid') //將錯誤編號放入對應的input類型裡
    	             var errorContent = document.createElement('div');
        	         errorContent.classList.add("invalid-feedback");
                 	errorContent.innerText=errorMsgs[i].innerText.substring(2); // 錯誤內容
             	    needsVal[errorNumber].after(errorContent); //將錯誤內容放入對應的input類型後
             	}
        	 }
    	    
		});
	</script>
</body>
</html>