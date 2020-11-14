<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.waterfall.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	WaterfallService waterfallSvc = new WaterfallService();
	List<WaterfallVO> list = waterfallSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="membersSvc" scope="page" class="com.members.model.MembersService" />
<jsp:useBean id="wfpicSvc" scope="page" class="com.waterfall.model.WFPicService" />

<html>
<head>

<title>所有瀑布牆文章 </title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/waterfall/css/listAllWaterfall.css">

	
	
</head>
<body id="top" style="background-color:white">
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
	
      <div >
        <img id="search" src="<%=request.getContextPath() %>/front-end/waterfall/img/search.png">
        <input id="input" type="text" placeholder="  search" name="search" >
      </div>
<div id="all">
<div style="text-align:left;margin-bottom:30px">
<a class="btn btn-secondary btn-lg" href="<%=request.getContextPath() %>/front-end/waterfall/addWaterfall.jsp" role="button"><i class="fas fa-edit "></i> 新增文章</a>
</div>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<div style="text-align:right; margin-bottom: 10;">
		<%@ include file="page/pageWaterfall1.file"%>
	</div>

<div class="row row-cols-1 row-cols-lg-3 masonry">

<c:forEach var="waterfallVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<%-- <c:set var="string1" value="${waterfallVO.wf_content}"/>	
		<c:set var="short_content" value="${fn:substring(string1, 0, 30)}"/> --%>
		<c:set var="short_content" value="${waterfallVO.wf_content}"/>
		<c:set var="listPic" value="${wfpicSvc.getAll(waterfallVO.wf_no)}"/>

<c:if test="${waterfallVO.wf_stat == 1 }"> 
<div class="col mb-4 item">
<c:if test="${not empty memVO.mem_no }">
<!-- 下拉選單 (檢舉/刪除/修改) start -->
    <div class="dropdown" id="drop">
        <button class="btn btn-primary-outline" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        	<i class="fas fa-ellipsis-v"></i>
        </button>
        <div class="dropdown-menu dropdown-menu-right" id="menu" aria-labelledby="dropdownMenu2">
			<c:if test="${waterfallVO.wf_memno == memVO.mem_no }">	
		  		<FORM METHOD="post"
					  ACTION="<%=request.getContextPath()%>/waterfall/waterfall.do"
					  style="margin-bottom: 0px;">
					  <input type="submit" class="dropdown-item" value="修改"> 
					  <input type="hidden" name="wf_no" value="${waterfallVO.wf_no}"> 
					  <input type="hidden" name="action" value="getOne_For_Update">
				</FORM>
				<FORM METHOD="post"
					  ACTION="<%=request.getContextPath()%>/waterfall/waterfall.do"
					  style="margin-bottom: 0px;">
					  <input type="submit" class="dropdown-item" value="刪除" id="submitBtn" onclick="if(confirm('您確定送出嗎?')) return true;else return false"> 
					  <input type="hidden" name="wf_no" value="${waterfallVO.wf_no}"> 
					  <input type="hidden" name="action" value="fake_delete">
					  <input type="hidden" name="whichpage" value="<%=whichPage%>">
				</FORM>
			</c:if>
			
			<c:if test="${waterfallVO.wf_memno != memVO.mem_no }">	
				<button type="button" class="dropdown-item" data-toggle="modal" data-target="#${waterfallVO.wf_no}" data-whatever="${waterfallVO.wf_title}">檢舉</button>
			</c:if>	
        </div>
      </div>
<!-- REPORTmodal -->
		<div class="modal fade" id="${waterfallVO.wf_no}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLabel">檢舉文章</h5>
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
			        <input type="hidden" name="mem_no" value="${memVO.mem_no}">
					<input type="hidden" name="action" value="insert_wfreport">
			        <input type="submit" class="btn btn-primary" value="Send Report"> 
		      	</div>
		      </form>
		    </div>
		  </div>
		</div>		     
<!-- modal -->
<!-- 下拉選單 (檢舉/刪除/修改)  end -->
</c:if>
    <div class="card card ">
    <c:if test="${not empty listPic}">
    <c:forEach var="wfpicVO" items="${listPic}" begin="0" end="0">
      <img src="<%=request.getContextPath()%>/waterfall/WFPic.do?action=getPic&wfp_no=${wfpicVO.wfp_no}" class="card-img-top" alt="...">
    </c:forEach>
    </c:if>
      <div class="card-body" >
        <h4 class="card-title"><a style="color:#2f4f4f;text-decoration:none;" href="<%=request.getContextPath()%>/waterfall/waterfall.do?action=getOne_For_Display&wf_no=${waterfallVO.wf_no}">${waterfallVO.wf_title}</a></h4>
        <div class="share-border"></div>
        <div style="color:gray;">
        <div class="wfcontent text-justify">
        ${short_content}
        </div>
        <br>
        <h6 class="card-text "><i class="far fa-user"></i> ${membersSvc.getOneMem(waterfallVO.wf_memno).mem_alias }</h6>
        <p class="card-text text-left"><i>${waterfallVO.wf_edit}</i></p>
        </div>
        <br>
        <p class="card-text" style="text-align:center;">
        	<a class="btn btn-outline-secondary btn-sm " href="<%=request.getContextPath()%>/waterfall/waterfall.do?action=getOne_For_Display&wf_no=${waterfallVO.wf_no}" role="button">(read more)</a>
        </p>
      </div>
    </div>
</div>			<!-- class="col mb-4" -->
</c:if>	
</c:forEach>	<!-- each card -->

</div>




	<div id="page2">
		<%@ include file="page/pageWaterfall2.file"%>
	</div>
</div>
	
<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/waterfall/css/masonry.pkgd.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

<%@ include file="/front-end/index/Notice.jsp"%>

<script>
/* 檢舉 */
$('#exampleModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) 
	  var recipient = button.data('whatever')
	  var modal = $(this)
	  modal.find('.modal-title').text('Report article -- ' + recipient + ' --')
	  modal.find('.modal-body input').val(recipient)
	})

/* 卡片陰影 */
$(document).ready(function() {
console.log("document is ready");
  $( ".card" ).hover(
  function() {
    $(this).addClass('shadow-lg'); 
  }, function() {
    $(this).removeClass('shadow-lg');
  }
  
);
});

 /* masonry */
$(function(){
	$('.masonry').masonry({
		itemSelector: '.item'
	});
}); 
/* shortContent */
var wfcontents = document.querySelectorAll('div.wfcontent');
wfcontents.forEach(function(wfc){
	console.log(wfc);
	
	var text = wfc.querySelectorAll('p')[0].innerText.substring(0,30)+'...'; 
	wfc.innerHTML = "";
	wfc.innerText = text;
});
</script>
</body>
</html>