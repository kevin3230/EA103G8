<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.camp.model.*"%>

<%
	String vd_no = (String)session.getAttribute("vd_no");
	CampVO campVO = (CampVO) request.getAttribute("campVO");
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
					<h3>新增營位</h3>
				</div>
				<form method="post" action="<%=request.getContextPath()%>/camp/camp.do" name="form1" enctype="multipart/form-data" novalidate>
					<div class="table-responsive">
						<table class="table table-striped">
							<tbody>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位名稱：</th>
									<td><input type="text" name="camp_name" class="form-control needs-validation" id="camp_name" maxlength="10" placeholder="營位名稱" value="<%=(campVO == null) ? "" : campVO.getCamp_name()%>" />
	                                   </td>
								</tr>
								<jsp:useBean id="ctSvc" scope="page" class="com.camptype.model.CampTypeService" />
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位類型：</th>
									<td>
										<select name="ct_no" class="form-control" id="ct_no">
                                    	<c:forEach var="campTypeVO" items="${ctSvc.all}">
                                    		<option value="${campTypeVO.ct_no}">${campTypeVO.ct_name}
										</c:forEach>
                               			</select>
									</td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位數量：</th>
									<td><input type="text" name="camp_qty" class="form-control needs-validation" id="camp_qty" maxlength="3" placeholder="營位數量" value="<%=(campVO == null) ? "" : campVO.getCamp_qty()%>" /></td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位定價：</th>
									<td><input type="text" name="camp_price" class="form-control needs-validation" id="camp_price" maxlength="10" placeholder="營位定價" value="<%=(campVO == null) ? "" : campVO.getCamp_price()%>" /></td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位狀態：</th>
									<td>
										<select name="camp_stat" class="form-control" id="camp_stat">
											<option value="0" selected>草稿</option>
											<option value="1">下架</option>
											<option value="2">上架</option>
										</select>
									</td>
								</tr>
								<tr class="form-group">
									<th scope="row" class="text-nowrap text-center align-middle">營位照片：</th>
									<td>
										<div class="form-row">
  											<div class="custom-file col-9">
    											<input type="file" class="custom-file-input form-control-file needs-validation" name="camp_pic" id="camp_pic" aria-describedby="cancelPic">
    											<label class="custom-file-label" for="camp_pic" data-browse="瀏覽">選擇檔案</label>								
 									 		</div>
                                            <div class="col">
                                                <button class="btn btn-outline-secondary" type="button" id="cancelPic" disabled>取消</button>
                                            </div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="d-flex justify-content-between align-items-start">
							<input type="hidden" name="camp_vdno" value="<%=vd_no%>">
							<input type="hidden" name="action" value="insert">
                        	<input type="submit" class="btn btn-outline-info" value="送出新增">
                        	<button class="btn btn-outline-secondary" id="smBtn">神奇小按鈕</button>
                    	</div>
					</div>
				</form>
				
				<div class="d-flex my-3 justify-content-center align-items-start" id="preview">
               	   <img src="<%=request.getContextPath()%>/front-end/camp/images/p1.jpg" id="originalPic" class="img-fluid mh-100" alt="" />
               	</div>
			</div>
		</div>
	</div>

	<!-- Footer開始 -->
	<%@ include file="/front-end/index/footer.jsp"%>
	<!-- Footer結束 -->

    <script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
    <script	src="<%=request.getContextPath()%>/front-end/sidebar/js/vendorSidebar.js"></script>
    <%@ include file="/front-end/index/Notice.jsp"%>
    <script>
        function init() {
            var originalPic = document.getElementById("originalPic");
            var camp_pic = document.getElementById("camp_pic");
            var preview = document.getElementById('preview');
            var filename = document.getElementsByClassName('custom-file-label');

           // 使用於取消更新圖片法
            var cancelPic = document.getElementById('cancelPic'); 

            camp_pic.addEventListener('change', function() {
                var files = camp_pic.files;               
                if (files !== null) {
                    var file = files[0];
                    var reader = new FileReader();
                    reader.addEventListener('load', function() {
                        
                        // 隱藏原圖片
                    	originalPic.style.display="none";

                    	// 確認有無已存在的newPic，若有則清空
                        newPic = document.getElementById("newPic");
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
                        img.setAttribute('id', "newPic");

                        // 將包裝好的img放到preview
                        preview.append(img);

                        // 取消更新圖片按鈕生效
                        cancelPic.removeAttribute('disabled');

                    });
                    // 開始進行讀取
                    reader.readAsDataURL(file);
                }

            });
			
            // 取消更新圖片         
            cancelPic.addEventListener('click', function(e) {
            	e.preventDefault();
            	var newPic = document.getElementById('newPic');
            	newPic.remove();
            	originalPic.style.display="";
                camp_pic.value="";
                filename[0].innerText="選擇檔案";
                cancelPic.setAttribute('disabled',true);
            });

            // 顯示錯誤訊息：
            // 0-營位名稱類型錯誤
            // 1-營位數量類型錯誤
            // 2-營位定價類型錯誤
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
			
            // 神奇小按鈕
            var camp_nameArr = ['親子區', '親水區', '櫻花區', '河畔區', '草地區'];
            var camp_qtyArr = [5, 3, 6, 4, 8];
            var camp_priceArr = [5000, 2500, 3200, 2300, 2000];
            var camp_name = document.getElementById("camp_name");
            var camp_qty = document.getElementById("camp_qty");
            console.log(camp_qty.getAttribute('name'));
            var camp_price = document.getElementById("camp_price");
            var smBtn = document.getElementById("smBtn");
            smBtn.addEventListener('click', function(e) {
            	e.preventDefault();
            	function random() {
            		var rand = Math.random();
	            	var rand2 = Math.floor(rand * 5);
    	        	return rand2;
        			}
            	var i = random()
            	camp_name.value = camp_nameArr[i];
            	camp_qty.value = camp_qtyArr[i];
            	camp_price.value = camp_priceArr[i];
            });
        }

        window.onload = init;
    </script>	
</body>
</html>