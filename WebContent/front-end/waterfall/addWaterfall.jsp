<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.waterfall.model.*"%>
<%@ page import="com.members.model.*"%>

<%
  WaterfallVO waterfallVO = (WaterfallVO) request.getAttribute("waterfallVO");
  
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>文章新增</title>
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
#all{
	width:900px;
	margin: 20px auto;
	min-height:500px;
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
<div id="all" class="min-vh-100">
<div style="margin:30 0; text-align:left;">
<a class="btn btn-secondary  btn-lg" href="<%=request.getContextPath() %>/front-end/waterfall/listAllWaterfall.jsp" role="button"><i class="fas fa-long-arrow-alt-left fa-lg"></i> 回列表</a>
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

    <form method="post" action="<%=request.getContextPath()%>/waterfall/waterfall.do" name="form1" enctype="multipart/form-data" onsubmit="return fileCountCheck(this);">
      <div class="form-group">
        <label ><h4>文章標題</h4></label>
        <input type="text" class="form-control" id="exampleFormControlInput1" placeholder="title" name="wf_title" value="<%= (waterfallVO==null)? "" : waterfallVO.getWf_title()%>" />
      </div>

      <div class="form-group">
        <label ><h4>文章內容</h4></label>
        <textarea class="form-control" id="editor" name="wf_content" ><%= (waterfallVO==null)? "" : waterfallVO.getWf_content()%></textarea>
      </div>
      <div>
      <h4>上傳圖片</h4>(最多五張)
      </div>
      <br />
      <div>
      	<input type="file" class="btn btn-light btn-sm" id="wfp_pic" name="wfp_pic" multiple="multiple" />
      </div>
      <br />
      <div id="preview_imgs" >
       <p>目前沒有圖片</p>
   	  </div>
      <input type="hidden" name="wf_memno" value="${memVO.mem_no }">
      <input type="hidden" name="action" value="insert" />
      <div class="row justify-content-center">
      	<input type="submit" class="btn btn-light btn-mi" value="送出新增" />
      </div>
    </form>


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
   

   function fileCountCheck(objForm) {
if(window.File && window.FileList) {
var fileCount = document.getElementById("wfp_pic").files.length;
if(fileCount > 5) {
window.alert('檔案數不能超過5個，你選擇了'+ fileCount +'個');
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
        var img = $("<img width='300' height='200'>").attr('src', e.target.result);
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