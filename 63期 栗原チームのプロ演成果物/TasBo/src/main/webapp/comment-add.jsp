
<%@page import="java.util.List"%>
<%@page import="model.entity.CommentBean"%>
<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント投稿フォーム</title>
</head>
<body>

	<h1>コメント投稿フォーム</h1>

	<%
	//セッションからTaskBean,コメントリストを取得
	TaskBean task = (TaskBean) session.getAttribute("task");
	
	//リクエストスコープからエラーメッセージを取得
	String deleteErrorMessage = (String) request.getAttribute("deleteErrorMessage");
		
	//エラーメッセージがnullでなければ
	if (deleteErrorMessage != null){
	%>
		<h2><%= deleteErrorMessage %></h2>
	
		<form action="task-list-servlet" method="post">
			<input type="submit" value="一覧画面に戻る">
		</form>
		
	<%
	} else {
	%>
		<h2>対象タスク</h2>
	
		<table border="1">
			<tr>
				<th>タスク名</th>
				<th>カテゴリ</th>
				<th>期限</th>
				<th>担当者</th>
				<th>ステータス</th>
				<th>メモ</th>
			</tr>
			<tr>
				<td><%=task.getTaskName()%></td>
				<td><%=task.getCategoryName()%></td>
				<td>
					<%
					if (task.getLimitDate() != null) {
					%> <%=task.getLimitDate()%> <%
				 	} else {
				 	%> <%=" "%> <%
				 	}
				 	%>
				</td>
				<td><%=task.getUserName()%></td>
				<td><%=task.getStatusName()%></td>
				<td>
					<%
					if (task.getMemo() != null) {
					%> <%=task.getMemo()%> <%
				 	} else {
				 	%> <%=" "%> <%
				 	}
					%>
				</td>
			</tr>
		</table>
		<br>
	
		<br>
		<form action="comment-add-servlet" method="post">
			<table border="1">
				<tr>
					<th>コメント</th>
					<td><textarea name="comment" rows="5" cols="40" ></textarea>
					</td>
				</tr>
			</table>
	
			<input type="submit" value="投稿">
		</form>
		<form action="comment-list-servlet" method="get">
			<input type="submit" value="戻る">
		</form>

	<%
	}
	%>

</body>
</html>
