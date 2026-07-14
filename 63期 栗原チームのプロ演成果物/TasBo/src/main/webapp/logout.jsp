<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
</head>
<body>

	<h1>ログアウト</h1>
	
	<hr>
	
	<%
	//セッションの無効化
	session.invalidate();
	
	%>	
	
	<h2>ログアウトしました。</h2>
	
	<form action="login.jsp" method="post">
		<input type="submit" value="ログイン画面へ">
	</form>

</body>
</html>