<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.waterfall.model.*"%>
<%@ page import="com.members.model.*"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
WaterfallVO waterfallVO = (WaterfallVO) request.getAttribute("waterfallVO"); 
MembersVO memVO =  (MembersVO)session.getAttribute("memVO");
%>
<jsp:useBean id="wfpicSvc" scope="page" class="com.waterfall.model.WFPicService" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<title>文章修改 - update_waterfall_input.jsp</title>
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/utility/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/header.css">
	<link rel="stylesheet" href="<%= request.getContextPath()%>/front-end/index/assets/css/footer.css">
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>

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
  textarea {
	border: 0 none;
	cursor: pointer;
	-webkit-border-radius: 5px;
	border-radius: 5px;
}
  #all {
	width:80%;
	margin: 20px auto;
	min-height:700px;
}
#left{
	
}
 h4 {
    display: inline;
    text-align: left;
  }
  #listPic{
  width:200px;
  display:inline-block;
  }
  #pic{
  border: 1px solid #DBDBDB;
  width:200px;
  height:150px;
  overflow: hidden;
  }
  #pic img{
    width:100%;
    height:100%;
  }
  #button{
  display:flex;
  position: absolute;
  top:95%;
  left:85%;
  }
</style>

</head>
<body bgcolor='white'>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp" %> 
	<!-- Header結束 -->
<div id="all" class="min-vh-100">

<div style="margin:10 0;">
<h4><a href="<%=request.getContextPath()%>/front-end/waterfall/listAllWaterfall.jsp" class="badge badge-info"><i class="fas fa-long-arrow-alt-left fa-lg"></i> 回列表</a></h4>
</div>

<h3>資料修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

    <div class="container">
	  	<div class="row">
		    <div class="col-6" id="left">
			    <form method="post" action="<%=request.getContextPath()%>/waterfall/waterfall.do" name="form1" enctype="multipart/form-data" onsubmit="return fileCountCheck(this);">
			      <div class="form-group">
			        <label ><h4>文章標題</h4></label>
			        <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="title" name="wf_title" value="<%=waterfallVO.getWf_title()%>" />
			      </div>
			      <div class="form-group">
			        <label ><h4>文章內容</h4></label>
			        <textarea class="form-control" id="editor" name="wf_content" ><%=waterfallVO.getWf_content()%></textarea>
			      </div>
			      <div >
			      	<input type="file" class="btn btn-light btn-sm" id="wfp_pic" name="wfp_pic" multiple="multiple" />
			      </div>
			      <br />
			      
			      <input type="hidden" name="wf_no" value="<%=waterfallVO.getWf_no()%>">
			      <input type="hidden" name="wf_memno" value="${memVO.mem_no })">
			      <input type="hidden" name="action" value="update" />
			      <div  id="button">
			      	<input type="submit" class="btn btn-light btn-mi" value="送出修改" />
			      </div>
			    </form>
		    </div>
		    <div class="col-6">
<!--  -->    
			    <c:set var="listPic" value="${wfpicSvc.getAll(waterfallVO.wf_no)}"/>
			    <c:if test="${not empty listPic}">
			    <c:forEach var="wfpicVO" items="${listPic}" begin="0" end="10" >
			    <input type="hidden" id="hadPic" value="${fn:length(listPic)}">
			    
			    <div id="listPic" class="col-6" >
			    	<div id="pic">
			      		<img src="<%=request.getContextPath()%>/waterfall/WFPic.do?action=getPic&wfp_no=${wfpicVO.wfp_no}" class="img-fluid" alt="...">
			    	</div>
			    	<div class="text-center" >
							<FORM METHOD="post"
								  ACTION="<%=request.getContextPath()%>/waterfall/WFPic.do" >
								  <input type="submit" class="btn btn-light btn-sm" value="刪除"> 
								  <input type="hidden" name="wf_no" value="${waterfallVO.wf_no}">
								  <input type="hidden" name="wfp_no" value="${wfpicVO.wfp_no}"> 
								  <input type="hidden" name="action" value="delete_pic">
							</FORM>	
			    	</div>
			    </div>
			    </c:forEach>
			    </c:if>
			    <c:if test="${empty listPic}">
			    <input type="hidden" id="hadPic" value=0>
			    </c:if>
			    <div id="preview_imgs" >
     			<p>目前沒有新圖片</p>
   			    </div>
   				<br />
<!--  -->     
		    </div>
	  	</div>
	</div>
    
</div>
	<!-- Footer開始 -->	
	<%@ include file="/front-end/index/footer.jsp" %>
	<!-- Footer結束 -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
	
</body>
<script src="https://cdn.ckeditor.com/ckeditor5/23.0.0/classic/ckeditor.js"></script>
<script>
    ClassicEditor
        .create( document.querySelector( '#editor' ) , {
            toolbar: [ 'heading','undo','redo','bold', 'italic', 'bulletedList', 'numberedList', 'blockQuote']
        })
        .then(editor => {
        console.log(editor);
   	    })
        .catch( error => {
            console.error( error );
        } );
   
    var fileCount2 = document.getElementById("hadPic").value;
    if(fileCount2 == null){
    	fileCount2 = 0;
    };
    console.log(fileCount2);
	var hadPic = parseInt(fileCount2);
	function fileCountCheck(objForm) {
		if(window.File && window.FileList) {
			var fileCount = document.getElementById("wfp_pic").files.length;
			if((fileCount + hadPic) > 5) {
				window.alert('總圖片數不能超過5個');
				return false;
			}
		} else {
			window.alert('抱歉，你的瀏覽器不支援FileAPI，請升級瀏覽器！');
		}
	}

   </script>
   <script>
$("#wfp_pic").change(function(){
  $("#preview_imgs").html(""); // 清除預覽
  readURL(this);
});

function readURL(input){
  if (input.files && input.files.length >= 0) {
    for(var i = 0; i < input.files.length; i ++){
      var reader = new FileReader();
      reader.onload = function (e) {
        var img = $("<img width='200' height='150' >").attr('src', e.target.result);
        $("#preview_imgs").append(img);
      }
      reader.readAsDataURL(input.files[i]);
    }
  }else{
     var noPictures = $("<p>目前沒有圖片</p>");
     $("#preview_imgs").append(noPictures);
  }
}
</script>
</html>