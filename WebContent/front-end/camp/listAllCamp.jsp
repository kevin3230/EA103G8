<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.camptype.model.*"%>

<%	
	String vd_no = (String) session.getAttribute("vd_no");
	String ct_no = (String) request.getParameter("ct_no");

	CampService campSvc = new CampService();
	List<CampVO> list = null;
	if (ct_no == null || ct_no.equals("null") || (ct_no.trim()).length() == 0) {
		list = campSvc.getCampsByVdnoWithoutDeleted(vd_no);
	} else {
		list = campSvc.getCampsByVdnoCtno(vd_no, ct_no);
	}
	pageContext.setAttribute("list", list);
	
	CampTypeService ctSvc = new CampTypeService();
	pageContext.setAttribute("ctSvc", ctSvc);
	
	Set<String> ctSet = campSvc.getAllCt_no(vd_no);
	pageContext.setAttribute("ctSet", ctSet);
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

	#bigPic {
		height: 350px;
	}

	#smPic {
		height: 100px;
		filter: drop-shadow(2px 4px 2px #666);
	}

	#smPic {
		height:100px;
	}
	
    .hightlight {
            animation: blink 0.5s linear;
            background-color: darkgray;
    }
	
    @keyframes blink {
            0% {
                background-color: #abc;
            }
            40% {
                background-color: white;
            }
            60% {
                background-color: lightgray;
            }            
            100% {
                background-color: darkgray;
            }
        }
    
</style>
</head>

<body>
	<!-- Header開始 -->
	<%@ include file="/front-end/index/header.jsp"%>
	<!-- Header結束 -->

	<div class="container-fluid min-vh-100">
		<div class="row min-vh-100 justify-content-start">		
			
			<!-- sidebar放這邊 ，col設定為2 -->		
			<%@ include file="/front-end/sidebar/vendorSidebar.jsp" %>
			<!-- sidebar放這邊 ，col設定為2 -->	
				 
			<div class="col-8 offset-1">
				<div class="d-flex m-3 justify-content-between align-items-center">
					<h3>營位一覽</h3>
					<form method="post" action="<%=request.getRequestURI()%>">
						<div class="input-group">
  							<select class="custom-select" name="ct_no" id="ct_no" aria-label="Example select with button addon">
  								<option value="null">全部類型
    							<c:forEach var="ct_no" items="${ctSet}">
		                    		<option value="${ct_no}"> ${ctSvc.getOneCampType(ct_no).ct_name}
	 		            		</c:forEach>
  							</select>
  							<div class="input-group-append">
    							<input type="submit" class="btn btn-outline-secondary" value="查詢">
  							</div>
						</div>
					</form>
				</div>
				<div class="table-responsive">
					<table class="table table-hover">
						<thead class="thead">
							<tr class="table-secondary">
								<th scope="col" class="text-center align-top">
									<form>										
										<div class="form-check">
											<input class="form-check-input mt-2" type="checkbox" id="checkAll">
										</div>
									</form>
								</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位編號</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位名稱</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位類型</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位數量</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位定價</th>
								<th scope="col" class="text-nowrap text-center align-middle">營位狀態</th>
								<th scope="col" colspan="2"></th>
							</tr>
						</thead>
						<tbody>
                    	<%@ include file="page1.file"%>
             			<c:forEach var="campVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr id ="${campVO.camp_no}_row" class="dataRow" onclick="selectRow()">
								<th scope="row" class="text-center">
									<form>										
										<div class="form-check mt-2">
											<input class="form-check-input checkedRow" type="checkbox" value="${campVO.camp_no}">
										</div>
									</form>
								</th>
	          	      			<td class="text-center align-middle">${campVO.camp_no}</td>
								<td class="text-center align-middle">${campVO.camp_name}</td>
								<td class="text-center align-middle">${ctSvc.getOneCampType(campVO.camp_ctno).ct_name}</td>
								<td class="text-center align-middle">${campVO.camp_qty}</td>
								<td class="text-center align-middle">${campVO.camp_price}</td>
								<td class="text-center align-middle campStat">${campVO.camp_stat}</td>
								<td class="text-center align-middle px-0">
									<form method="post" action="<%=request.getContextPath()%>/camp/camp.do">
										<input type="submit" class="btn btn-outline-info" value="修改">
		     							<input type="hidden" name="camp_no"  value="${campVO.camp_no}">
		     							<input type="hidden" name="action"	value="getOne_For_Update">
		     						</form>
		     					</td>
		     					<td class="text-center align-middle px-0">
		  							<form method="post" action="<%=request.getContextPath()%>/camp/camp.do">
			     						<input type="submit" class="btn btn-outline-danger" value="刪除">
			     						<input type="hidden" name="camp_no"  value="${campVO.camp_no}">
			     						<input type="hidden" name="action" value="delete">
			     					</form>
				     					
								</td>
							</tr>
						</c:forEach>
							<tr>
								<td scope="row" colspan="9" class="p-1">
									<button type="button" class="btn btn-outline-info" id="updateStatTo2">多筆上架</button>
									<button type="button" class="btn btn-outline-info" id="updateStatTo1">多筆下架</button>
									<button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#delMultiModal">多筆刪除</button>
								</td>
							</tr>
						</tbody>
					</table>
					<%@ include file="page2.file" %>
				</div>
	
				<!-- 刪除多筆提示視窗 -->
				<div class="modal fade" id="delMultiModal" tabindex="-1" role="dialog" aria-labelledby="delMultiModalLabel" aria-hidden="true">
  					<div class="modal-dialog" role="document">
    					<div class="modal-content">
      						<div class="modal-header">
      							<h5 class="modal-title">確定刪除多筆營位？</h5>
      						</div>
      						<div class="modal-footer">
      							<button type="button" class="btn btn-secondary" id="deleteSelected">確定</button>
        						<button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
      						</div>
    					</div>
  					</div>
				</div>
				
					
				<div>
        			<% 	for (int i = 0; i < list.size(); i++) { //以欄位第一張照片為預覽首圖
        					if (i == pageIndex) {
        						CampVO campVO = list.get(i);
	        					byte[] b = campVO.getCamp_pic();
	    	    				if (b != null) {
    	    						String bts = Base64.getEncoder().encodeToString(b);	 %>
					<div class="d-flex my-1 justify-content-center align-items-start" id="bigPic">
						<img src="data:image/jpeg;base64,<%=bts%>" class="img-fluid mh-100" alt="" />
					</div>
					<%  }}}  %>
        	        <div id="picBox" class="mb-3">
            	        <div class="row justify-content-center align-items-start">
                	 	<% 	for (int i = 0; i <list.size(); i++) {
                    			if(i >= pageIndex && i <= pageIndex+rowsPerPage-1) {
                    				CampVO campVO = list.get(i);
                    				String picId = campVO.getCamp_no();
	       	        				byte[] b = campVO.getCamp_pic();
	        	        			if (b != null) {
    		            				String bts = Base64.getEncoder().encodeToString(b);	%>
        		       		<div class="col-sm-2 p-1 d-flex justify-content-center align-items-center" id="smPic">
	            		   		<img src="data:image/jpeg;base64,<%=bts%>" id="<%=picId%>" class="img-fluid mh-100" alt="" onclick="selectPic()" />
	               			</div>
		                <%  }}}  %>
						</div>
	        		</div>
				</div>
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
		
		$(document).ready(function(){
			// 將營位狀態由數字轉中文
			var statArray = ["草稿", "下架", "上架"];
			var campStat = $(".campStat");		
			for (var i = 0; i < campStat.length; i++) {
				var stat = campStat[i].innerText;
				campStat[i].innerText = statArray[stat];
			}
			
			var checkedRow = $(".checkedRow");
			var arr = [];
			// 多筆上架			
			$("#updateStatTo2").click(function(){
				for (var i = 0; i < checkedRow.length; i++) {
					if (checkedRow[i].checked) {
						arr.push(checkedRow[i].value);
					}
				}
				var str = JSON.stringify(arr);
				$.ajax({
  					url: "<%=request.getContextPath()%>/camp/camp.do",
  					type: "post",
  					data:{
  						action: "updateStatTo2",
  						jsonStr: str
  					},
  					success: function(data) {
    					alert("多筆上架成功");
    					location.reload();
  					}
				});

			});

			// 多筆下架
			$("#updateStatTo1").click(function(){
				for (var i = 0; i < checkedRow.length; i++) {
					if (checkedRow[i].checked) {
						arr.push(checkedRow[i].value);
					}
				}
				var str = JSON.stringify(arr);
				$.ajax({
  					url: "<%=request.getContextPath()%>/camp/camp.do",
  					type: "post",
  					data:{
  						action: "updateStatTo1",
  						jsonStr: str
  					},
  					success: function(data) {
    					alert("多筆下架成功");
    					location.reload();
  					}
				});

			});

			// 多筆刪除
			$("#deleteSelected").click(function(){
				for (var i = 0; i < checkedRow.length; i++) {
					if (checkedRow[i].checked) {
						arr.push(checkedRow[i].value);
					}
				}

				var str = JSON.stringify(arr);
				$.ajax({
					url: "<%=request.getContextPath()%>/camp/camp.do",
					type: "post",
  					data:{
  						action: "deleteSelected",
  						jsonStr: str
  					},
  					success: function(data) {
	    				location.reload();
  					}
				});	
			});
			
			
			// checkAll
			$("#checkAll").click(function() {
			 	var result = $("#checkAll").prop("checked");
				$(".checkedRow").each(function() {
					$(this).prop("checked", result);
				});				
			});

		});

		// 由欄選圖片
		function selectRow(e) {
			var selectedRow = event.target.closest('tr');
			var picId = selectedRow.id.substring(0, 10);
			var picSrc = document.getElementById(picId).getAttribute("src");
			showPic(selectedRow, picSrc);
		}

		// 由圖片選欄
		function selectPic(e){
			var picSrc = event.target.src;
			var picId = event.target.id;
			var selectedRow = document.getElementById(picId+"_row");
			showPic(selectedRow, picSrc);
		}

		// show大圖片
		function showPic(selectedRow, picSrc) {
			var bigPic = document.getElementById('bigPic').innerHTML='<img src='+picSrc+' class="img-fluid mh-100" alt="" />';
			var row = document.getElementsByClassName("dataRow");
			for (var i = 0; i < row.length; i++) {
				row[i].classList.remove('hightlight');
			}
			selectedRow.classList.add('hightlight');
		}
		
	</script>
</body>
</html>