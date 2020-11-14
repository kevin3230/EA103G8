<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.waterfall.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
	WaterfallVO waterfallVO = (WaterfallVO) request.getAttribute("waterfallVO"); //waterfallServlet.java(Concroller), 存入req的waterfallVO物件
	List<WFReplyVO> list = (List<WFReplyVO>) request.getAttribute("list");
	WFReplyVO wfreplyVO = (WFReplyVO) request.getAttribute("wfreplyVO");
	
	WFPicVO wfpicVO = (WFPicVO) request.getAttribute("wfpicVO");
	List<WFPicVO> listPic = (List<WFPicVO>) request.getAttribute("listPic");

%>
<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
<html>
<head>
<title>${waterfallVO.wf_title}</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/waterfall/css/listOne.css">
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

</head>
<body >

	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
	

<div  id="all" >

<div style="margin:30 0; text-align:left;">
<a class="btn btn-secondary  btn-lg" href="<%=request.getContextPath() %>/front-end/waterfall/listAllWaterfall.jsp" role="button"><i class="fas fa-long-arrow-alt-left fa-lg"></i> 回列表</a>
</div>

<!-- picture -->
<c:if test="${not empty listPic}">
	<c:if test="${fn:length(listPic) == 1}">
		<c:forEach var="wfpicVO" items="${listPic}" begin="0" end="20">
			<div id="onePic" >
				<img src="<%=request.getContextPath()%>/waterfall/WFPic.do?action=getPic&wfp_no=${wfpicVO.wfp_no}" class="blogImg img-fluid" alt="">
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${fn:length(listPic) > 1}">
		<div id="carouselExampleIndicators" class="carousel slide " data-ride="carousel" >
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<c:if test="${fn:length(listPic) > 2}">
					<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
				</c:if>
				<c:if test="${fn:length(listPic) > 3}">
					<li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
				</c:if>
				<c:if test="${fn:length(listPic) > 4}">
					<li data-target="#carouselExampleIndicators" data-slide-to="4"></li>
				</c:if>
			</ol>
			<div class="carousel-inner" >
				<div class="carousel-item active">
				<c:forEach var="wfpicVO" items="${listPic}" begin="0" end="0">
	            	<img  src="<%=request.getContextPath()%>/waterfall/WFPic.do?action=getPic&wfp_no=${wfpicVO.wfp_no}" class="d-block " alt="...">
	          	</c:forEach>
	          	</div>
				<c:if test="${fn:length(listPic) > 1}">
				<c:forEach var="wfpicVO" items="${listPic}" begin="1" end="20">
					<div class="carousel-item">
		            	<img src="<%=request.getContextPath()%>/waterfall/WFPic.do?action=getPic&wfp_no=${wfpicVO.wfp_no}" class="d-block " alt="...">
		            </div>
		        </c:forEach>
				</c:if>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>	
		</div>
	</c:if>
</c:if>
<!-- picture -->
<div class="container border rounded" id="boxShadow" >
<!--*** 本文開始 ***-->

<div class="share-border"></div>
<div ><h2><b>${waterfallVO.wf_title}</b></h2></div>
<div class="share-border"></div>
<div >
	<span><i class="far fa-user"></i></span>
	<span>  ${membersSvc.getOneMem(waterfallVO.wf_memno).mem_alias }</span>
</div>
<div class="share-border" ></div>
<div class="text-left text-justify" style="font-size:18px; background-color:#eff5f5;width:80%; margin:20 auto;" >${waterfallVO.wf_content}</div>
<div class="share-border"></div>
<div class="text-right font-italic"> 最後編輯時間${waterfallVO.wf_edit}</div>
<div class="share-border"></div>

<!--*** 本文結束 ***-->
<!--*** 所有回覆開始 ***-->
	  <div style="text-align:right; margin:10;">
      <%@ include file="page/page3.file"%>
      </div>
    <c:forEach var="wfreplyVO" items="${list}" begin="<%=pageIndex%>"
			   end="<%=pageIndex+rowsPerPage-1%>">
         <div class="card card border-0">
         
       
         <c:if test="${wfreplyVO.wfr_stat == 1 }">
            <h5 class="card-header ">${list.indexOf(wfreplyVO)+1}樓</h5>
            <div class="card-body">
              <h5 class="card-title"><i class="far fa-user"></i>${membersSvc.getOneMem(wfreplyVO.wfr_memno).mem_alias }</h5>
              <p class="card-text">${wfreplyVO.wfr_content}</p>
              <p class="text-muted text-right"><i>最後編輯時間${wfreplyVO.wfr_edit}</i></p>
            </div>
<!-- 下拉選單 (檢舉/刪除/修改) start -->
	<c:if test = "${not empty memVO.mem_no }" >
    <div class="dropdown" id="drop">
        <button class="btn btn-primary-outline" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        	<i class="fas fa-ellipsis-v"></i>
        </button>
        <div class="dropdown-menu dropdown-menu-right" id="menu" aria-labelledby="dropdownMenu2">
			<c:if test="${wfreplyVO.wfr_memno == memVO.mem_no }">	
		  		<FORM METHOD="post"
					  ACTION="<%=request.getContextPath()%>/waterfall/wfreply.do" style="margin-bottom: 0px;">
					  <input type="submit" class="dropdown-item" value="修改"> 
					  <input type="hidden" name="wfr_no" value="${wfreplyVO.wfr_no}"> 
					  <input type="hidden" name="action" value="getOne_For_Update">
				</FORM>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/waterfall/wfreply.do" style="margin-bottom: 0px;">
					  <input type="submit" class="dropdown-item" value="刪除" onclick="if(confirm('您確定送出嗎?')) return true;else return false"> 
                      <input type="hidden" name="wfr_no" value="${wfreplyVO.wfr_no}"> 
                      <input type="hidden" name="wf_no" value="${waterfallVO.wf_no}">
					  <input type="hidden" name="action" value="fake_delete_wfreply">
				</FORM>
			</c:if>
			<c:if test="${wfreplyVO.wfr_memno != memVO.mem_no }">	
				<button type="button" class="dropdown-item" data-toggle="modal" data-target="#${wfreplyVO.wfr_no}" data-whatever="${waterfallVO.wf_title}">檢舉</button>
			</c:if>	
        </div>
      </div>
<!-- modal -->
		<div class="modal fade" id="${wfreplyVO.wfr_no}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">檢舉回覆</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <form METHOD="post" ACTION="<%=request.getContextPath()%>/wfreport/wfreport.do">
		        <div class="modal-body">
		          <div class="form-group">
		            <label for="message-text" class="col-form-label">Message:</label>
		            <textarea class="form-control" id="message-text" name="rep_content" rows="10"></textarea>
		          </div>
		      	</div>
		      	<div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <input type="hidden" name="wf_no" value="${waterfallVO.wf_no}">
                    <input type="hidden" name="wfr_no"  value="${wfreplyVO.wfr_no}">
                    <input type="hidden" name="mem_no" value="${memVO.mem_no }">
                    <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>">
                    <input type="hidden" name="action" value="insert_wfreport">
                    <input type="submit" class="btn btn-primary" value="Send Report">
		      	</div>
		      </form>
		    </div>
		  </div>
		</div>		
		</c:if>     
<!-- modal -->
<!-- 下拉選單 (檢舉/刪除/修改)  end -->            
          
          </c:if>
          </div> 
        
        <c:if test="${wfreplyVO.wfr_stat == 0 }">
          <div class="card card border-0">
            <h5 class="card-header">${list.indexOf(wfreplyVO)+1}樓</h5>
            <div class="card-body">
              <h6 class="text-muted"><i>-被刪除的回覆就像變了心的女友一樣^_^</i></h6>
            </div>
          </div>
        </c:if>     
      
 
    </c:forEach>

<%@ include file="page/page4.file"%>
<!--*** 所有回覆結束 ***-->	
<c:if test="${not empty memVO.mem_no }">
<!--*** 新增回覆開始 ***-->
<p style="margin:0 auto 50 auto;text-align:center;">
  <button class="btn btn-dark" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
    新增回覆
  </button>
</p>
<div class="collapse" id="collapseExample">
  <div class="card card-body">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/waterfall/wfreply.do" name="form1">
    <table class="table table-bordered">
      <tbody>
        <tr>
          <th scope="row">回覆內容</th>
          <td><textarea class="form-control" id="exampleFormControlTextarea1" placeholder="請輸入回覆"
          				name="wfr_content" rows="10" cols="70"><%= (wfreplyVO==null)? "" : wfreplyVO.getWfr_content()%></textarea></td>
        </tr>
      </tbody>
    </table>
    	 <input type="hidden" name="wf_no" value="<%=waterfallVO.getWf_no()%>">
    	 <input type="hidden" name="wfr_memno" value="${memVO.mem_no }">
		 <input type="hidden" name="action" value="insert">
		 <div class="row justify-content-center">
		 <input type="submit" class="btn btn-light btn-sm" value="送出">
		 </div>
	 </FORM>

  </div>
</div>	
<!--*** 新增回覆結束 ***-->
</c:if>
<c:if test="${empty memVO.mem_no }">
<div style="width:300px;height:50px;margin:0 auto;text-align:center;" >想回覆？就<a href="<%=request.getContextPath() %>/front-end/index/MemVdSignInSignUp.jsp" >登入</a></div>
</c:if>
</div>

</div>
	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->
<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>

<script>
$('#exampleModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) 
	  var recipient = button.data('whatever')
	  var modal = $(this)
	  modal.find('.modal-title').text('Report reply of --  ' + recipient + ' --')
	  modal.find('.modal-body input').val(recipient)
	})
</script>
</body>
</html>