<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*" %>
<%@ page import="com.vendor.model.*" %>
<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="vdSvc" scope="page" class="com.vendor.model.VendorService" />

<%
String userNo = (String) session.getAttribute("userNo");
if (userNo == null) {
	response.sendRedirect(request.getContextPath() + "/front-end/message/pseudoLogin.jsp");
}else{
	if(userNo != ""){
		request.setAttribute("userNo", userNo);
		String userType = userNo.substring(0,1);
		request.setAttribute("userType", userType);	
	}
}
%>
<%--
MembersVO memVO = (MembersVO)session.getAttribute("memVO");
VendorVO vendorVO = (VendorVO)session.getAttribute("vendorVO");
String userNo = null;
if(memVO != null){
	userNo = memVO.getMem_no();
}else{
	userNo = vendorVO.getVd_no();
}
session.setAttribute("userNo", userNo);
--%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- EA103G8 header-footer content import start -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<!-- EA103G8 header-footer content import end -->

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/message/css/chatroom.css" />

</head>
<body>
<div>
	<c:if test="${userType == 'M'}">
	<h3>
		你是誰：${memSvc.getOneMem(userNo).mem_name}
		_${memSvc.getOneMem(userNo).mem_no}
	</h3>
	</c:if>
	<c:if test="${userType == 'V'}">
	<h3>
		你是誰：${vdSvc.getOneVendor(userNo).vd_name}
		_${vdSvc.getOneVendor(userNo).vd_no}
	</h3>
	</c:if>
</div>
<div style="margin-top: 30px;">
	<h3>Oracle中的會員</h3>
	<c:forEach var="memVO" items="${memSvc.getAllMem()}">
		<button class="btn btn-primary userBtn" value="${memVO.mem_no}">${memVO.mem_name}</button>
	</c:forEach>
</div>
<div style="margin-top: 30px;">
	<h3>Oracle中的業者</h3>
	<c:forEach var="vdVO" items="${vdSvc.getAll()}">
		<button class="btn btn-danger userBtn" value="${vdVO.vd_no}">${vdVO.vd_name}</button>
	</c:forEach>
</div>
<div id="chatIcon" onclick="showChatRoom();">
	<i class="far fa-comment-dots fa-5x fa-flip-horizontal"></i>
</div>
<div class="container" id="chatRoom">
<div class="messaging">
      <div class="inbox_msg">
        <div class="inbox_people">
          <div class="headind_srch">
            <div class="recent_heading">
              <h4>Recent</h4>
            </div>
            <!-- <div class="srch_bar">
              <div class="stylish-input-group">
                <input type="text" class="search-bar"  placeholder="Search" >
                <span class="input-group-addon">
                <button type="button"> <i class="fa fa-search" aria-hidden="true"></i> </button>
                </span> </div>
            </div> -->
          </div>
          <div class="inbox_chat">
<!--             <div class="chat_list active_chat"> -->
<!--               <div class="chat_people"> -->
<!-- 				<div class="chat_img"><i class="fas fa-circle"></i></div> -->
<!--                 <div class="chat_ib"> -->
<!--                   <h5>愛琴海露營區 </h5> -->
<!--                   <input type="hidden" value=""/> -->
<!--                   <span class="chat_date">Dec 25</span> -->
<!--                 </div> -->
<!--               </div> -->
<!--             </div> -->
          </div>
        </div>
        <div class="mesgs">
          <div class="msg_head">
          	<i class="far fa-minus-square"></i>
          </div>
          <div class="msg_history">
<!--             <div class="incoming_msg"> -->
<!--               <div class="received_msg"> -->
<!--                 <div class="received_withd_msg"> -->
<!--                   <p>Test which is a new approach to have all -->
<!--                     solutions</p> -->
<!--                   <span class="time_date"> 11:01 AM    |    June 9</span></div> -->
<!--               </div> -->
<!--             </div> -->
<!--             <div class="outgoing_msg"> -->
<!--               <div class="sent_msg"> -->
<!--                 <p>Test which is a new approach to have all -->
<!--                   solutions</p> -->
<!--                 <span class="time_date"> 11:01 AM    |    June 9</span> </div> -->
<!--             </div> -->
		  	<div id="emptyMsgArea">
		  		<i class="far fa-comments fa-4x"></i>
		  		<div>
		  			您尚未選擇收件人<br>點擊左側對象開始傳送訊息
		  		</div>
		  	</div>
          </div>
          <div class="type_msg">
            <div class="input_msg_write">
              <input type="text" class="write_msg" placeholder="Type a message" />
              <button class="msg_send_btn" type="button"><i class="fa fa-paper-plane-o" aria-hidden="true"></i></button>
            </div>
          </div>
        </div>
      </div>
      
      <div id="grayOutArea">
      </div>
      
    </div></div>
</body>

<!-- EA103G8 javascript library import start -->
<script src="<%= request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%= request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%= request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
<!-- EA103G8 javascript library import end -->

<script type="text/javascript">

<%-- If user login. --%>
<c:if test="${not empty userNo}">
var MyPoint = "/MessageWS/${userNo}";
var self = '${userNo}';
</c:if>
<c:if test="${empty userNo}">
var MyPoint = "/MessageWS/";
var self = '';
</c:if>

</script>

<script src="<%=request.getContextPath() %>/front-end/message/js/chatroom.js"></script>
</html>