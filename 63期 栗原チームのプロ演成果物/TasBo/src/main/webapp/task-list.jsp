<%@page import="java.util.ArrayList"%>
<%@page import="model.entity.TaskBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
</head>
<body>
<%
	List<TaskBean> taskList = (List<TaskBean>)session.getAttribute("taskList");
	//編集エラー,削除エラーメッセージの取得
	String deleteErrorMessage = (String) request.getAttribute("deleteErrorMessage");
	//String alterErrorMessage = (String) request.getAttribute("alterErrorMessage");
	String alterError = (String) request.getAttribute("alterError");
%>
<h1>タスク一覧画面</h1>
<hr>

	<%
	if (deleteErrorMessage != null) {
	%>
	
		<%= deleteErrorMessage %>
	
	<%
	}
	%>
	
	<%
	if (alterError != null) {
	%>
	
		<%= alterError %>
	
	<%
	}
	%>

<form  method="GET">
	<table border = 1>
	<%if(taskList != null && !taskList.isEmpty()) { %>
		<tr>
			<th>選択</th>
			<th>タスク名</th>
			<th>カテゴリ</th>
			<th>期限</th>
			<th>担当者</th>
			<th>ステータス</th>
			<th>メモ</th>
			<th>コメント閲覧</th>
		<tr>
	<%
		for(TaskBean bean : taskList){
	%>	
			<tr>
				<td><input type="radio" name="taskId" value="<%=bean.getTaskId()%>" required></td>
				<td><%=bean.getTaskName()%></td>
				<td><%=bean.getCategoryName()%></td>
				<td>
					<%if(bean.getLimitDate() != null) { %>
						<%=bean.getLimitDate()%>
					<% } else { %>
						<%=" " %>
					<% } %>
				</td>
				<td><%=bean.getUserName()%></td>
				<td><%=bean.getStatusName()%></td>
				<td>
					<%if(bean.getMemo() != null) { %>
						<%=bean.getMemo()%>
					<% } else { %>
						<%=" " %>
					<% } %>
				</td>
				<td>
					<a href="comment-list-servlet?taskId=<%=bean.getTaskId() %>">コメント</a>
				</td>
			</tr>
	<%
		}
	%>
<% 
	} else {
%>		
		<tr>
			<td>表示するデータが存在しません</td>
		</tr>
<%
	}
%>
	</table>
	<br>
		<%if(taskList != null && !taskList.isEmpty()) { %>
			<input type = "submit" value="タスク編集" formaction="task-alter-servlet">
			<input type = "submit" value="タスク削除" formaction="task-delete-servlet">
	<%
		}
	%>
</form>

<form action = "menu.jsp" method = "GET">
	<input type = "submit" value = "戻る">
</form>
</body>
</html>