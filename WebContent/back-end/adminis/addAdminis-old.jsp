<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.adminis.model.*"%>

<%
	AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO");
%>
<%=adminisVO == null%>---${adminisVO.adminis_name}--

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工資料新增 - addAdminis.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>員工資料新增 - addAdminis.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="adminis.do" name="form1">
		<table>
			<tr>
				<td>姓名:</td>
				<td><input type="TEXT" name="adminis_name" size="45"
					value="<%= (adminisVO == null)? "測試" : adminisVO.getAdminis_name()%>" /></td>
			</tr>
			<tr>
				<td>密碼:</td>
				<td><input type="password" name="adminis_pwd" size="45"
					value="<%= (adminisVO == null)? "123456" : adminisVO.getAdminis_pwd()%>" /></td>
			</tr>

			<tr>
				<td>郵件:</td>
				<td><input type="email" name="adminis_email" size="45"
					value="<%= (adminisVO == null)? "EA103_99@gmail.com" : adminisVO.getAdminis_email()%>" /></td>
			</tr>
			<tr>
				<td>部門:</td>
				<td><input type="TEXT" name="adminis_dept" size="45"
					value="<%= (adminisVO == null)?  "測試部" : adminisVO.getAdminis_dept()%>" /></td>
			</tr>
			<tr>
				<td>權限值:</td>
				<td><input type="TEXT" name="adminis_pv" size="45"
					value="<%= (adminisVO == null)? "1234" : adminisVO.getAdminis_pv()%>" /></td>
			</tr>

		</table>
		<br> 
		<input type="hidden" name="action" value="insert"> 
		<input type="submit" value="送出新增">
	</FORM>
</body>



<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
								 <div class="form-group row p-4">
								<div class="custom-control custom-checkbox big col-sm-6 mb-3 ">
                                    <input type="checkbox" class="custom-control-input " id="customCheck">
                                    <label class="custom-control-label" for="customCheck">權限管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck1">
                                    <label class="custom-control-label" for="customCheck1">管理員管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6 mb-3">
                                    <input type="checkbox" class="custom-control-input" id="customCheck2">
                                    <label class="custom-control-label" for="customCheck2">收支管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck3">
                                    <label class="custom-control-label" for="customCheck3">文章檢舉管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6 mb-3 ">
                                    <input type="checkbox" class="custom-control-input" id="customCheck4">
                                    <label class="custom-control-label" for="customCheck4">FAQ管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck5">
                                    <label class="custom-control-label" for="customCheck5">活動快報管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6 mb-3">
                                    <input type="checkbox" class="custom-control-input" id="customCheck6">
                                    <label class="custom-control-label" for="customCheck6">裝備介紹管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck">
                                    <label class="custom-control-label" for="customCheck">露營指南管理</label>
                                  </div>  
								  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck">
                                    <label class="custom-control-label" for="customCheck">廠商帳號審核</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6 mb-3">
                                    <input type="checkbox" class="custom-control-input" id="customCheck">
                                    <label class="custom-control-label" for="customCheck">會員停權管理</label>
                                  </div>
                                  <div class="custom-control custom-checkbox big col-sm-6">
                                    <input type="checkbox" class="custom-control-input" id="customCheck">
                                    <label class="custom-control-label" for="customCheck">會員資料管理</label>
                                  </div>                                  
                                </div>       
<%-- <%  --%>
<!-- //   java.sql.Date hiredate = null; -->
<!-- //   try { -->
<!-- // 	    hiredate = adminisVO.getHiredate(); -->
<!-- //    } catch (Exception e) { -->
<!-- // 	    hiredate = new java.sql.Date(System.currentTimeMillis()); -->
<!-- //    } -->
<%-- %> --%>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" /> --%>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script> --%>

<!-- <style> -->
<!-- /*   .xdsoft_datetimepicker .xdsoft_datepicker { */ -->
<!-- /*            width:  300px;   /* width:  300px; */ */ -->
<!-- /*   } */ -->
<!-- /*   .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box { */ -->
<!-- /*            height: 151px;   /* height:  151px; */ */ -->
<!-- /*   } */ -->
<!--  </style>  -->

<!-- <script> -->
<!-- //         $.datetimepicker.setLocale('zh'); -->
<!-- //         $('#f_date1').datetimepicker({ -->
<!-- // 	       theme: '',              //theme: 'dark', -->
<!-- // 	       timepicker:false,       //timepicker:true, -->
<!-- // 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘) -->
<!-- // 	       format:'Y-m-d',         //format:'Y-m-d H:i:s', -->
<%-- 		   value: '<%=hiredate%>', // value:   new Date(), --%>
<!-- //            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含 -->
<!-- //            //startDate:	            '2017/07/10',  // 起始日 -->
<!-- //            //minDate:               '-1970-01-01', // 去除今日(不含)之前 -->
<!-- //            //maxDate:               '+1970-01-01'  // 去除今日(不含)之後 -->
<!-- //         }); -->



<!--         // ----------------------------------------------------------以下用來排定無法選擇的日期----------------------------------------------------------- -->

<!--         //      1.以下為某一天之前的日期無法選擇 -->
<!--         //      var somedate1 = new Date('2017-06-15'); -->
<!--         //      $('#f_date1').datetimepicker({ -->
<!--         //          beforeShowDay: function(date) { -->
<!--         //        	  if (  date.getYear() <  somedate1.getYear() ||  -->
<!--         //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) ||  -->
<!--         //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate()) -->
<!--         //              ) { -->
<!--         //                   return [false, ""] -->
<!--         //              } -->
<!--         //              return [true, ""]; -->
<!--         //      }}); -->


<!--         //      2.以下為某一天之後的日期無法選擇 -->
<!--         //      var somedate2 = new Date('2017-06-15'); -->
<!--         //      $('#f_date1').datetimepicker({ -->
<!--         //          beforeShowDay: function(date) { -->
<!--         //        	  if (  date.getYear() >  somedate2.getYear() ||  -->
<!--         //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) ||  -->
<!--         //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate()) -->
<!--         //              ) { -->
<!--         //                   return [false, ""] -->
<!--         //              } -->
<!--         //              return [true, ""]; -->
<!--         //      }}); -->


<!--         //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期) -->
<!--         //      var somedate1 = new Date('2017-06-15'); -->
<!--         //      var somedate2 = new Date('2017-06-25'); -->
<!--         //      $('#f_date1').datetimepicker({ -->
<!--         //          beforeShowDay: function(date) { -->
<!--         //        	  if (  date.getYear() <  somedate1.getYear() ||  -->
<!--         //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) ||  -->
<!--         //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate()) -->
<!--         //		             || -->
<!--         //		            date.getYear() >  somedate2.getYear() ||  -->
<!--         //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) ||  -->
<!--         //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate()) -->
<!--         //              ) { -->
<!--         //                   return [false, ""] -->
<!--         //              } -->
<!--         //              return [true, ""]; -->
<!--         //      }}); -->

<!-- </script> -->
</html>