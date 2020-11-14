<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.members.model.*" %>
<%@ page import="com.vendor.model.*" %>
<jsp:useBean id="memSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="vdSvc" scope="page" class="com.vendor.model.VendorService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/front-end/message/css/chatroom.css" />

</head>
<body>
<div id="chatIcon" onclick="showChatRoom();">
	<i class="fas fa-comment-dots fa-5x fa-flip-horizontal"></i>
</div>
<div id="chatRoom">
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
		  	<div id="disconnectMsgArea">
		  		<i class="fas fa-comment-slash fa-4x"></i>
		  		<div>
		  			您目前已斷線!<br>請重新整理頁面再次連線
		  		</div>
		  	</div>
          </div>
          <div class="type_msg">
            <div class="input_msg_write row">
            	<div class="col-10"><input type="text" class="write_msg" placeholder="Type a message" /></div>
            	<div class="col-2 justify-content-center"><button class="btn msg_send_btn" type="button"><i class="far fa-paper-plane"></i></button></div>
            </div>
          </div>
        </div>
      </div>
      
      <div id="grayOutArea">
          <i class="far fa-minus-square"></i>
      </div>
      
    </div></div>
</body>

<script type="text/javascript">
<%-- If user not login. --%>
<c:if test="${empty vendorVO && empty memVO}">
var MyPoint = "/MessageWS/";
var self = '';
</c:if>
<%-- If member login. --%>
<c:if test="${not empty memVO}">
var MyPoint = "/MessageWS/${memVO.mem_no}";
var self = '${memVO.mem_no}';
</c:if>
<%-- If vendor login. --%>
<c:if test="${not empty vendorVO}">
var MyPoint = "/MessageWS/${vendorVO.vd_no}";
var self = '${vendorVO.vd_no}';
</c:if>
</script>

<script src="<%=request.getContextPath() %>/front-end/message/js/chatroom.js"></script>
</html>