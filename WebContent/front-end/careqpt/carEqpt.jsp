<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.vendor.model.*"%>
<%@ page import="com.eqptavail.model.*"%>
<%@ page import="com.equipment.model.*"%>
<%@ page import="com.carcamp.model.*"%>
<%@ page import="com.promotion.model.*"%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="date"/>

<>

<!DOCTYPE html>

<html lang="en">

<head>
<meta charset="UTF-8">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
<title>租借裝備</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/utility/css/bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/header.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/index/assets/css/footer.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/carcamp/css/reservedNav.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/careqpt/css/image_view.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/careqpt/css/reserve.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/careqpt/css/rsv_process.css"  type="text/css">
<style type="text/css">
	img{
		width: auto;
		height: auto;
		max-width: 100%;
		max-height: 100%;	
	}
	.submitbutton{
		margin:15px;
	}
	.remove {
		color:black;
		font-size: 12px;
		text-decoration: line-through;
	}
	.price {
		color:black;
		font-size: 15px!important;
		height:60px;
	}
	.prom{
		font-size: 16px;
	}
	#btn{
		width:100%;
		margin-left:0rem;
		margin-top: 15px;
		margin-bottom: 10px;
	}
	.btn{
		width:45%;
	}
	#return{
	margin-right:15px;
	}
	#submit{
	margin-left:15px;
	}
	
    .dialogbox{
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        margin: auto;
        overflow: auto;
    }
    
    div.loadingdiv {
    height: 100%;
    /*100%覆蓋網頁內容, 避免user在loading時進行其他操作*/    width: 100%;
    position: fixed;
    z-index: 99999;
    /*須大於網頁內容*/    top: 0;
    left: 0;
    display: block;
    background: #000;
    opacity: 0.6;
    text-align: center;
	}

	div.loadingdiv img {
	    position: relative;
	    vertical-align: middle;
	    text-align: center;
	    margin: 0 auto;
	    margin-top: 50vh;
	}
    
    
    
    
    .cssload-triangle {
	position: absolute;
	left: 0;
	right: 0;
	width: 0px;
	height: 0px;
	margin: -24px auto;
	border-left: 24px solid transparent;
	border-right: 24px solid transparent;
	border-bottom: 42px solid rgb(153,207,83);
	transform-origin: 0 0;
		-o-transform-origin: 0 0;
		-ms-transform-origin: 0 0;
		-webkit-transform-origin: 0 0;
		-moz-transform-origin: 0 0;
	transform-origin: 50% 90%;
		-o-transform-origin: 50% 90%;
		-ms-transform-origin: 50% 90%;
		-webkit-transform-origin: 50% 90%;
		-moz-transform-origin: 50% 90%;
	animation: cssload-spin 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-o-animation: cssload-spin 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-ms-animation: cssload-spin 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-webkit-animation: cssload-spin 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-moz-animation: cssload-spin 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
}
.cssload-triangle:after, .cssload-triangle:before {
	content: "";
	position: absolute;
	top: 42px;
	width: 0;
	height: 0;
	border-left: 24px solid transparent;
	border-right: 24px solid transparent;
	border-bottom: 42px solid rgba(153,207,83,0.98);
}
.cssload-triangle:after {
	left: 0px;
	border-bottom: 42px solid rgb(31,181,41);
	animation: cssload-shiftright 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-o-animation: cssload-shiftright 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-ms-animation: cssload-shiftright 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-webkit-animation: cssload-shiftright 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-moz-animation: cssload-shiftright 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
}
.cssload-triangle:before {
	left: -49px;
	border-bottom: 42px solid rgb(103,199,24);
	animation: cssload-shiftleft 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-o-animation: cssload-shiftleft 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-ms-animation: cssload-shiftleft 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-webkit-animation: cssload-shiftleft 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
		-moz-animation: cssload-shiftleft 1.15s cubic-bezier(0.56, 1.55, 0.51, 0.74) infinite;
}





@keyframes cssload-spin {
	0% {
		transform: scale(1) translateY(0);
	}
	50% {
		transform: scale(0.5) translateY(19px);
	}
	100% {
		transform: scale(1) translateY(0);
	}
}

@-o-keyframes cssload-spin {
	0% {
		-o-transform: scale(1) translateY(0);
	}
	50% {
		-o-transform: scale(0.5) translateY(19px);
	}
	100% {
		-o-transform: scale(1) translateY(0);
	}
}

@-ms-keyframes cssload-spin {
	0% {
		-ms-transform: scale(1) translateY(0);
	}
	50% {
		-ms-transform: scale(0.5) translateY(19px);
	}
	100% {
		-ms-transform: scale(1) translateY(0);
	}
}

@-webkit-keyframes cssload-spin {
	0% {
		-webkit-transform: scale(1) translateY(0);
	}
	50% {
		-webkit-transform: scale(0.5) translateY(19px);
	}
	100% {
		-webkit-transform: scale(1) translateY(0);
	}
}

@-moz-keyframes cssload-spin {
	0% {
		-moz-transform: scale(1) translateY(0);
	}
	50% {
		-moz-transform: scale(0.5) translateY(19px);
	}
	100% {
		-moz-transform: scale(1) translateY(0);
	}
}

@keyframes cssload-shiftleft {
	0% {
		transform: translate3drgb(0,0,0);
	}
	50% {
		transform: translate3d(24px, -42px, 0);
	}
	100% {
		transform: translate3drgb(0,0,0);
	}
}

@-o-keyframes cssload-shiftleft {
	0% {
		-o-transform: translate3drgb(0,0,0);
	}
	50% {
		-o-transform: translate3d(24px, -42px, 0);
	}
	100% {
		-o-transform: translate3drgb(0,0,0);
	}
}

@-ms-keyframes cssload-shiftleft {
	0% {
		-ms-transform: translate3drgb(0,0,0);
	}
	50% {
		-ms-transform: translate3d(24px, -42px, 0);
	}
	100% {
		-ms-transform: translate3drgb(0,0,0);
	}
}

@-webkit-keyframes cssload-shiftleft {
	0% {
		-webkit-transform: translate3drgb(0,0,0);
	}
	50% {
		-webkit-transform: translate3d(24px, -42px, 0);
	}
	100% {
		-webkit-transform: translate3drgb(0,0,0);
	}
}

@-moz-keyframes cssload-shiftleft {
	0% {
		-moz-transform: translate3drgb(0,0,0);
	}
	50% {
		-moz-transform: translate3d(24px, -42px, 0);
	}
	100% {
		-moz-transform: translate3drgb(0,0,0);
	}
}

@keyframes cssload-shiftright {
	0% {
		transform: translate3drgb(0,0,0);
	}
	50% {
		transform: translate3d(-24px, -42px, 0);
	}
	100% {
		transform: translate3drgb(0,0,0);
	}
}

@-o-keyframes cssload-shiftright {
	0% {
		-o-transform: translate3drgb(0,0,0);
	}
	50% {
		-o-transform: translate3d(-24px, -42px, 0);
	}
	100% {
		-o-transform: translate3drgb(0,0,0);
	}
}

@-ms-keyframes cssload-shiftright {
	0% {
		-ms-transform: translate3drgb(0,0,0);
	}
	50% {
		-ms-transform: translate3d(-24px, -42px, 0);
	}
	100% {
		-ms-transform: translate3drgb(0,0,0);
	}
}

@-webkit-keyframes cssload-shiftright {
	0% {
		-webkit-transform: translate3drgb(0,0,0);
	}
	50% {
		-webkit-transform: translate3d(-24px, -42px, 0);
	}
	100% {
		-webkit-transform: translate3drgb(0,0,0);
	}
}

@-moz-keyframes cssload-shiftright {
	0% {
		-moz-transform: translate3drgb(0,0,0);
	}
	50% {
		-moz-transform: translate3d(-24px, -42px, 0);
	}
	100% {
		-moz-transform: translate3drgb(0,0,0);
	}
}


	.loading{
		height:20rem;
        position: relative;
    	display: flex;
    	align-items: center;
	}
    
    .nodata{
    	text-align: center;
    	margin-top:13rem;
    	margin-bottom:13rem;
    	color: rgb(255, 255, 255);
		font-size: 50px;
		background-color: #ffffff;
		text-shadow: rgb(204, 204, 204) 0px 1px 0px, rgb(201, 201, 201) 0px 2px 0px, rgb(187, 187, 187) 0px 3px 0px, rgb(185, 185, 185) 0px 4px 0px, rgb(170, 170, 170) 0px 5px 0px, rgba(0, 0, 0, 0.1) 0px 6px 1px, rgba(0, 0, 0, 0.1) 0px 0px 5px, rgba(0, 0, 0, 0.3) 0px 1px 3px, rgba(0, 0, 0, 0.15) 0px 3px 5px, rgba(0, 0, 0, 0.2) 0px 5px 10px, rgba(0, 0, 0, 0.2) 0px 10px 10px, rgba(0, 0, 0, 0.1) 0px 20px 20px; 
    }
    
    .ins{
    	min-height: 52.7%; 
    }

    html,body { 
/*     	height: 100%; */
    } 

    
    
</style>
</head>

<body>
<!-- Header開始 -->
		<%@ include file="/front-end/index/header.jsp"%>
		<!-- Header結束 -->
			<%@ include file="/front-end/carcamp/reservedNav.jsp" %> 
		<!-- 預約導覽開始 -->
	
		<!-- 預約導覽結束 -->
			<div id="MainContent_divOption" class="option content">
				<div class="loading" style="hegith:100px;">
					<div class="cssload-triangle"></div>
				</div>
				<div  class="ins" >
				<form  id="formdata" onsubmit='return' class="login">
					<table class="style01">
						<tbody class="tbody">
							<tr class="row_header">
							</tr>
						</tbody>
					</table>
				</form>
				<div id="btn" class="row justify-content-center"></div>
				</div>
			</div>


		<!-- Footer開始 -->
		<%@ include file="/front-end/index/footer.jsp"%>
		<!-- Footer結束 -->
		
		
		
		
	<script type="text/javascript">
		var urls ="<%= request.getContextPath()%>/careqpt/carEqpt2.do";
		var mem_no ="${memVO.mem_no}";
		var om_no ="${param.om_no}"
        var urlimg = "<%=request.getContextPath()%>/getPic.do?action=getPic&eqpt_no=";
        var urlch = "<%=request.getContextPath()%>";
        var reback = "<%=request.getParameter("om_no")%>";
        var carCampVOList = "${carCampVOList}";
        var carEqptVOlist = "${carEqptVOList}";
	</script>
	<script src="<%=request.getContextPath()%>/utility/js/jquery-3.5.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/utility/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/index/assets/js/header.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/careqpt/js/careqpt.js"></script>
	
		
</body>
</html>