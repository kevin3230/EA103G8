<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.adminis.model.*"%>
<%-- �����Ƚm�߱ĥ� Script ���g�k���� --%>

<%
AdminisVO adminisVO = (AdminisVO) request.getAttribute("adminisVO"); //EmpServlet.java(Concroller), �s�Jreq��adminisVO����
%>

<html>
<head>
<title>���u��� - listOneadmin.jsp</title>

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
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>�����Ƚm�߱ĥ� Script ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>���u��� - ListOneEmp.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>�޲z���s��</th>
		<th>�m�W</th>
		<th>�l��</th>
		<th>����</th>
		<th>���U���</th>
		<th>�v����</th>
		<th>�޲z�����A</th>
	</tr>
	<tr>
		<td><%=adminisVO.getAdminis_no()%></td>
		<td><%=adminisVO.getAdminis_name()%></td>
		<td><%=adminisVO.getAdminis_email()%></td>
		<td><%=adminisVO.getAdminis_dept()%></td>
		<td><%=adminisVO.getAdminis_regdate()%></td>
		<td><%=adminisVO.getAdminis_pv()%></td>
		<td><%=adminisVO.getAdminis_stat()%></td>
	</tr>
</table>

</body>
</html>