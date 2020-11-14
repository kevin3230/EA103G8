<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>

<%	
	String vd_no = (String)session.getAttribute("vd_no");
	FoodVO foodVO = (FoodVO) request.getAttribute("foodVO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>PLAMPING</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/fontawesome-5.15.1/css/all.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/sidebar/css/vendorSidebar.css">
	
<style>

	body {
    	background-color: #f2f2f2;
    	color: #444;
	}

	#preview {
		height: 450px;
	}

</style>

</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%>
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
	
	<div class="container-fluid min-vh-100">
		<div class="row min-vh-100 justify-content-start">
			
			<!-- sidebar放這邊 ，col設定為2 -->		
			<%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
			<!-- sidebar放這邊 ，col設定為2 -->
			
			<div class="col-8 offset-1">
				<div class="d-flex m-3 justify-content-start align-items-center">
					<h3>修改食材</h3>
				</div>
				<form method="post" action="<%=request.getContextPath()%>/food/food.do" name="form1" enctype="multipart/form-data" novalidate>
					<div class="table-responsive">
						<table class="table table-striped">
							<tbody>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材編號：</th>
									<td><%=foodVO.getFood_no()%></td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材名稱：</th>
									<td><input type="text" name="food_name" class="form-control needs-validation" id="food_name" maxlength="10" placeholder="食材名稱" value="<%=(foodVO == null) ? "" : foodVO.getFood_name()%>" /></td>
								</tr>
								<jsp:useBean id="ftSvc" scope="page" class="com.foodtype.model.FoodTypeService" />
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材類型：</th>
									<td>
										<select name="ft_no" class="form-control" id="ft_no">
											<c:forEach var="foodTypeVO" items="${ftSvc.all}">
												<option value="${foodTypeVO.ft_no}" ${(foodTypeVO.ft_no==foodVO.food_ftno)? 'selected':''}>${foodTypeVO.ft_name}
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材定價：</th>
									<td><input type="text" name="food_price" class="form-control needs-validation" id="food_price" maxlength="10" placeholder="食材定價" value="<%=(foodVO == null) ? "" : foodVO.getFood_price()%>" /></td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材介紹：</th>
									<td>
										<textarea name="food_intro" class="form-control needs-validation" id="food_intro" maxlength="100" placeholder="上限100字" /><%=(foodVO == null) ? "" : foodVO.getFood_intro()%></textarea>
										<span class="text-secondary" id="wordsNum">0</span>
										<sapn class="text-secondary">/100</span>
									</td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">食材狀態：</th>
									<td>
										<select name="food_stat" class="form-control" id="food_stat">
											<option value="0" <%=(foodVO.getFood_stat() == 0) ? "selected" : ""%>>草稿</option>
											<option value="1" <%=(foodVO.getFood_stat() == 1) ? "selected" : ""%>>下架</option>
											<option value="2" <%=(foodVO.getFood_stat() == 2) ? "selected" : ""%>>上架</option>
										</select>
									</td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位照片：</th>
									<td>
										<div class="form-row">
  											<div class="custom-file col-9">
    											<input type="file" class="custom-file-input form-control-file needs-validation" name="food_pic" id="food_pic" aria-describedby="cancelPic">
    											<label class="custom-file-label" for="food_pic">選擇檔案</label>
 									 		</div>
  											<div class="col">
    											<button class="btn btn-outline-secondary" type="button" id="cancelPic" disabled>取消</button>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<input type="hidden" name="action" value="update">
						<input type="hidden" name="food_no" value="<%=foodVO.getFood_no()%>">
						<input type="hidden" name="food_vdno" value="<%=foodVO.getFood_vdno()%>">
						<input type="submit" class="btn btn-outline-info" value="送出修改">
					</div>
				</form>
				
				<%	byte[] b = foodVO.getFood_pic(); // 由資料庫取得
	                if (b != null) {
	    			String bts = Base64.getEncoder().encodeToString(b);  %>
	            <div class="d-flex my-3 justify-content-center align-items-start" id="preview">
	               	<img src="data:image/jpeg;base64,<%=bts%>" class="img-fluid mh-100" id="originalPic" alt="" />
	            </div>
	            <% } %>
			</div>
		</div>
	</div>

	<!-- Footer開始 -->
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->
	
	<script	src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
	<%@ include file="/front-end/index/Notice.jsp"%>
	<script>
		// 食材介紹字數監控
    	$(document).ready(function() {
	        $("#food_intro").bind('input propertychange', function () {    	        
        	    var wordsNum = $(this).val().length;
	            $('#wordsNum').text(wordsNum);
    	    });
    	});

		function init() {
			var originalPic = document.getElementById("originalPic");
            var food_pic = document.getElementById("food_pic");
            var preview = document.getElementById('preview');
            var filename = document.getElementsByClassName('custom-file-label');

            // 使用於取消更新圖片
            var cancelPic = document.getElementById('cancelPic');

            food_pic.addEventListener('change', function() {
                var files = food_pic.files;
                if (files !== null) {
                    var file = files[0];
                    // if (file.type.indexOf('image') > -1) { // 考慮交給後端處理
                        var reader = new FileReader();
                        reader.addEventListener('load', function() {
                            
                            // 隱藏原圖片
                            originalPic.style.display="none";

                            // 確認有無已存在的newPic，若有則清空
                        	var newPic = document.getElementById("newPic");
                        	if (newPic !== null ) {
                        		newPic.remove();
                        	}

                        	//填入檔名(目前使用的Bootstrap式樣無法自動顯示檔名)
                        	var name = file.name;
                        	filename[0].innerText = name;

                            // 新增img元素
                            var img = document.createElement('img');
                            // 賦予src屬性
                            img.setAttribute('src', reader.result);
                            img.classList.add('img-fluid');
                            img.classList.add('mh-100');
                            img.setAttribute('id', "newPic")
                           
                            // 將img放到preview
                            preview.append(img);

                            // 取消更新圖片按鈕生效 
                            cancelPic.removeAttribute('disabled');

                        });
                        // 開始進行讀取
                        reader.readAsDataURL(file);
                    // } else {
                    //     alert('請上傳圖片！'); // 考慮交給後端處理
                    // }
                }

            });
            
            // 取消更新圖片
            cancelPic.addEventListener('click', function(e) {
            	e.preventDefault();
            	var newPic = document.getElementById('newPic');
            	newPic.remove();
            	originalPic.style.display="";
                food_pic.value="";
                filename[0].innerText="選擇檔案";
                cancelPic.setAttribute('disabled',true);
            });

            // 顯示錯誤訊息：
            // 0-營位名稱類型錯誤
            // 1-營位定價類型錯誤
            // 3-營位照片類型錯誤
            var needsVal = document.getElementsByClassName('needs-validation'); //抓取needs-validation類別
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

            var food_introNum = document.getElementById("food_intro").value.length;
            var wordsNum = document.getElementById("wordsNum");
            wordsNum.innerText = food_introNum;
        }

        window.onload = init;
	</script>
</body>
</html>