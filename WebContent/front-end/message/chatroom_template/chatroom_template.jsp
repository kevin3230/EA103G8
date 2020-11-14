<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- <% --%>
// String mem_no = (String) session.getAttribute("mem_no");
// if (mem_no == null) {
// 	response.sendRedirect(request.getContextPath() + "/front-end/message/pseudoLogin2.jsp");
// }
// request.setAttribute("userName", mem_no);
<%-- %> --%>

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
	href="chatroom_template.css" />
<style type="text/css">
div.unreadNum{
 	display: inline-block;
 	border-radius: 50%;
	border: 1px solid black;
	width: 20px;
	height: 20px;
	position: relative;
	float: right;
	bottom: 10px;
	text-align: center;
	verticle-align: center;
	line-height: 18px;
	font-size: 10px;
}

</style>
</head>
<body>
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
            <div class="chat_list active_chat">
              <div class="chat_people">
				<div class="chat_img"><i class="fas fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>愛琴海露營區 </h5>
                  <div class="unreadNum">1</div>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿里山露營區</h5>
                   <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>墾丁大街露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="fas fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>喜馬拉雅山露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿哩鋪搭露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿哩鋪搭露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿哩鋪搭露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿哩鋪搭露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
            <div class="chat_list">
              <div class="chat_people">
                <div class="chat_img"><i class="far fa-circle"></i></div>
                <div class="chat_ib">
                  <h5>阿哩鋪搭露營區 </h5>
                  <span class="chat_date">Dec 25</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="mesgs">
          <div class="msg_head">
          	<i class="far fa-minus-square"></i>
          </div>
          <div class="msg_history">
            <div class="incoming_msg">
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>Test which is a new approach to have all
                    solutions</p>
                  <span class="time_date"> 11:01 AM    |    June 9</span></div>
              </div>
            </div>
            <div class="outgoing_msg">
              <div class="sent_msg">
                <p>Test which is a new approach to have all
                  solutions</p>
                <span class="time_date"> 11:01 AM    |    June 9</span> </div>
            </div>
            <div class="incoming_msg">
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>Test, which is a new approach to have</p>
                  <span class="time_date"> 11:01 AM    |    Yesterday</span></div>
              </div>
            </div>
            <div class="outgoing_msg">
              <div class="sent_msg">
                <p>Apollo University, Delhi, India Test</p>
                <span class="time_date"> 11:01 AM    |    Today</span> </div>
            </div>
            <div class="incoming_msg">
              <div class="received_msg">
                <div class="received_withd_msg">
                  <p>We work directly with our designers and suppliers,
                    and sell direct to you, which means quality, exclusive
                    products, at a price anyone can afford.</p>
                  <span class="time_date"> 11:01 AM    |    Today</span></div>
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
      
    
      
    </div></div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/chatroom_template.js"></script>
</html>