<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.adminis.model.*"%>

<%
	AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO"); //EmpServlet.java (Concroller) �s�Jreq��adminisVO���� (�]�A�������X��adminisVO, �]�]�A��J��ƿ��~�ɪ�adminisVO����)
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>���u��ƭק� - update_Adminis_input.jsp</title>

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
				<h3>���u��ƭק� - update_Adminis_input.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/adminis/select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">�^����</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>��ƭק�:</h3>

	<%-- ���~��C --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">�Эץ��H�U���~:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/adminis/adminis.do" name="form1">
		<table>
			<tr>
				<td>���u�s��:<font color=red><b>*</b></font></td>
				<td><%=adminisVO.getAdminis_no()%></td>
			</tr>
			<tr>
				<td>�m�W:</td>
				<td><input type="TEXT" name="adminis_name" size="45"
					value="<%= adminisVO.getAdminis_name()%>" /></td>
			</tr>
			<tr>
				<td>�K�X:</td>
				<td><input type="password" name="adminis_pwd" size="45"
					value="<%= adminisVO.getAdminis_pwd()%>" /></td>
			</tr>

			<tr>
				<td>�l��:</td>
				<td><input type="email" name="adminis_email" size="45"
					value="<%= adminisVO.getAdminis_email()%>" /></td>
			</tr>
			<tr>
				<td>����:</td>
				<td><input type="TEXT" name="adminis_dept" size="45"
					value="<%= adminisVO.getAdminis_dept()%>" /></td>
			</tr>
			<tr>
				<td>�v����:</td>
				<td><input type="TEXT" name="adminis_pv" size="45"
					value="<%= adminisVO.getAdminis_pv()%>" /></td>
			</tr>
			<tr>
				<td>�޲z�����A:</td>
				<td><input type="TEXT" name="adminis_stat" size="45"
					value="<%= adminisVO.getAdminis_stat()%>" /></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="update"> <input
			type="hidden" name="adminis_no" value="<%=adminisVO.getAdminis_no()%>">
		<input type="submit" value="�e�X�ק�"> 
	</FORM>
</body>




</html>