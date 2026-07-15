<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
</head>
<body>

	<h1>メニュー</h1>
	<hr>
	
	<form action="task-add-servlet" method="GET">
		<input type="submit" value="タスク登録">
	</form>
	
	<form action="task-list-servlet" method="post">
		<input type="submit" value="タスク一覧表示">
	</form>
	
	<form action="logout.jsp" method="post">
		<input type="submit" value="ログアウト">
	</form>

</body>
</html>