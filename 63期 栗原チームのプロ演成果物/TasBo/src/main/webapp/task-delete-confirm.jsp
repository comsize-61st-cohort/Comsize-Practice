<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク削除確認画面</title>
</head>
<body>
	
	<%
	//セッションからTaskBeanを取得
	TaskBean task = (TaskBean) session.getAttribute("task");
	
	//セッションからフラグを取得
	String identificationMessage = (String) request.getAttribute("identificationMessage");
	String alreadyDeleteMessage = (String) request.getAttribute("alreadyDeleteMessage");
	
	//削除確認フラグがfalseなら
	if (alreadyDeleteMessage != null){
	%>
	
		<h2><%= alreadyDeleteMessage %></h2>
	
		<form action="task-list-servlet" method="post">
			<input type="submit" value="一覧画面に戻る">
		</form>
	
	<%
	} else {
	%>
	
		<% 
		//本人確認フラグがnullなら
		if (identificationMessage == null) {
		%>
	
		<h2>こちらのタスクを削除します、よろしいですか?</h2>
	
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
					<%if(task.getLimitDate() != null) { %>
						<%=task.getLimitDate()%>
					<% } else { %>
						<%=" " %>
					<% } %>
				</td>
				<td><%=task.getUserName()%></td>
				<td><%=task.getStatusName()%></td>
				<td>
					<%if(task.getMemo() != null) { %>
						<%=task.getMemo()%>
					<% } else { %>
						<%=" " %>
					<% } %>
				</td>
			</tr>
		</table><br>
	
		<form method="post">
			<input type="submit" value="削除" formaction="task-delete-servlet">
			<input type="submit" value="一覧画面に戻る" formaction="task-list-servlet">
		</form>
	
		<%
		//本人ではない場合(特定フラグがnullではない場合)
		} else {
	
		%>
		<h2>タスクの削除は本人しか行うことはできません。</h2>
	
		<form action="task-list-servlet" method="post">
			<input type="submit" value="一覧画面に戻る">
		</form>
	
		<%
		}
		%>
	
	<%
	}
	%>

</body>
</html>