<%@page import="model.entity.CommentBean"%>
<%@page import="java.util.List"%>
<%@page import="model.entity.TaskBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント閲覧</title>
</head>
<body>
	<%
	//エラー表示
	String error = (String)request.getAttribute("error");
	if(error != null){
	%>

	<p style="color:red;"><%= error %></p>

	<%
	}
	%>
	
	
	<%
	//セッションからTaskBean,コメントリストを取得
	TaskBean task = (TaskBean) session.getAttribute("task");
	List<CommentBean> commentList = (List<CommentBean>) session.getAttribute("commentList");
	
	//リクエストスコープからエラーメッセージを取得
	String alreadyDeleteMessage = (String) request.getAttribute("alreadyDeleteMessage");
	
	//削除確認フラグがfalseなら
	if (alreadyDeleteMessage != null){
	%>
	
		<h2><%= alreadyDeleteMessage %></h2>
	
		<form action="task-list-servlet" method="post">
			<input type="submit" value="戻る">
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
		
		<h2>コメント一覧</h2>
		
		<%if(commentList != null && !commentList.isEmpty()) { %>
		<form  method="GET">
			<table border = 1>
			
				<tr>
					<th>選択</th>
					<th>コメント投稿者</th>
					<th>コメント</th>
					<th>コメント投稿日時</th>
				
				<tr>
			<%
			for(CommentBean bean : commentList){
			%>	
				<tr>
					<td><input type="radio" name="commentId" value="<%=bean.getCommentId()%>" required></td>
					<td><%=bean.getUserName()%></td>
					<td><%=bean.getComment()%></td>
					<td><%=bean.getUpdateDatetime().toString().substring(0, 16)%></td>
				</tr>
			
			<%
			}
			%>
		
			</table>	
			<% 
			} else {
			%>		
				<h3>コメントが投稿されていません</h3>
			<%
			}
			%>
			<br>
			
			<%	if(commentList != null && !commentList.isEmpty()) { %>
				<input type = "submit" value="コメント削除" formaction="comment-delete-servlet">
			</form>
			
			<%
			}
			%>
	
	<form action = "comment-add-servlet" method = "GET">
		<input type = "submit" value = "コメント投稿">
	</form>
				
	<form action = "task-list-servlet" method = "POST">
		<input type = "submit" value = "戻る">
	</form>
		
	<%
	}
	%>
	
</body>
</html>