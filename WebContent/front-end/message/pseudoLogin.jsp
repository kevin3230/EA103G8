<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>PLAMPING_message_pseudoLogin</title>
<style type="text/css">
	div{
		margin: auto;
		text-align: center;
	}
</style>
</head>
<body>
	<div>
		<h1>編號輸入(會員、業者)</h1>
		<form action="<%=request.getContextPath() %>/message/NameServlet">
			<input type="hidden" name="action" value="pseudoLogin">
<!-- 			<input type="text" name="userNo" value="M000000001" placeholder="Type in user number." required> -->
			<input type="text" name="userNo" value="M000000001" placeholder="Type in user number.">
			<input type="submit">
		</form>
	</div>
</body>
</html>