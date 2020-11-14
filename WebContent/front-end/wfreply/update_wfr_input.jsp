<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.waterfall.model.*"%>

<%
	WFReplyVO wfreplyVO = (WFReplyVO) request.getAttribute("wfreplyVO"); 

%>
<jsp:useBean id="waterfallSvc" scope="page" class="com.waterfall.model.WaterfallService" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>回覆修改</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">

<style>
body{
	margin: 0px auto;
	background-color:#F8F8FF;
}
  .content {
	width: 100%;
	height: 400px;
	resize: none;
}

  .container {
	width:80%;
	margin: 20px auto;
}
 h4 {
    display: inline;
    text-align: left;
  }
</style>

</head>
<body bgcolor='white'>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->

<div id="all">

    <div class="container min-vh-100">
    	<div class="row justify-content-center">
	    	<h3>回覆修改</h3>
		</div>
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color:red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
		<div class="row justify-content-center">
    	<FORM>
	        <div class="card" style="width: 40rem;">
	            <div class="card-header">
	                <h5 class="card-title">文章標題</h5>
	                <div>
	                    ${waterfallSvc.getOneWaterfall(wfreplyVO.wfr_wfno).wf_title }
	                </div>
	            </div>
	            <div class="card-body">
	              <h5 class="card-title">回覆內容</h5>
	              <textarea name="wfr_content" rows="8" cols="72" ><%=wfreplyVO.getWfr_content()%></textarea>
	              
	              <input type="hidden" name="action" value="update">
	              <input type="hidden" name="wfr_no" value="<%=wfreplyVO.getWfr_no()%>">
	              <input type="hidden" name="wfr_wfno" value="<%=wfreplyVO.getWfr_wfno()%>">
	              <input type="hidden" name="wfr_no" value="<%=wfreplyVO.getWfr_memno()%>">
	              <br>
	            </div>
	        </div>
	        <br>
	        <div style="text-align:center;">
	        	<input type="submit" class="btn btn-light btn-mi" value="送出修改">
	        	<a class="btn btn-light btn-mi" href="<%=request.getContextPath()%>/waterfall/waterfall.do?action=getOne_For_Display&wf_no=${wfreplyVO.wfr_wfno}" role="button">取消修改</a>
	        </div>
	        
        </FORM>
        </div>
        
    </div>

</div>
	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->
	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	

</body>

</html>