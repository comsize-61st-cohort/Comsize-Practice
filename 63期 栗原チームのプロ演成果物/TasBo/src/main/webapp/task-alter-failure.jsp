<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラー画面</title>
</head>
<body>

	<% String alterError = (String)request.getAttribute("alterError"); %>
		
	<h2>編集に失敗しました</h2>
	<hr>
	
	<%
		if(alterError != null){
	%>
			<p style="color: red;"><%=alterError%></p>
	<%
		}
	%>
	
	<form action="task-list-servlet" method="post">
		<input type="submit" value="一覧画面に戻る">
	</form>
	
	
</body>
</html>