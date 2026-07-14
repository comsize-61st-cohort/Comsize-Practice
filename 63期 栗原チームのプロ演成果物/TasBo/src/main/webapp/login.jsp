<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン</title>
</head>
<body>

	<%
	//セッションの取得
	String errorMessage = (String) session.getAttribute("errorMessage");
	
	%>
	

	<h1>ログイン</h1>
	<hr>
	
	<% 
	
	//ログインに失敗していた場合、失敗のメッセージを表示
	if (errorMessage != null) {
	
	%>
		<%=errorMessage %>
	
	<%
	} 
	%>
	
	<form action="login-servlet" method="post">
	
		ユーザID
		<input type="text" name="userId" required maxlength="24"><br>
		
		パスワード
		<input type="password" name="password" required maxlength="32"><br><br>
		
		<input type="submit" value="ログイン">
		
		<input type="reset" value="クリア">
		
	</form>
	
	

</body>
</html>