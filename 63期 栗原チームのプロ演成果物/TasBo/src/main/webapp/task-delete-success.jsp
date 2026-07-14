<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除完了画面</title>
</head>
<body>

	<%
	//リクエストスコープからエラーメッセージを受け取る
	String errorMessage = (String) request.getAttribute("errorMessage");
	
	%>
	
	
	<h2>削除に成功しました</h2>
	<form action="task-list-servlet" method="post">
		<input type="submit" value="一覧画面に戻る">
	</form>
	
	
</body>
</html>