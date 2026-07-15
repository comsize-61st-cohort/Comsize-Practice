<%@page import="model.entity.TaskBean"%>
<%@page import="model.entity.UserBean"%>
<%@page import="model.entity.StatusBean"%>
<%@page import="model.entity.CategoryBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク編集画面</title>
</head>
<body>
	<%
		List<CategoryBean> categoryList = (List<CategoryBean>)session.getAttribute("categoryList");
		List<StatusBean> statusList = (List<StatusBean>)session.getAttribute("statusList");
		List<UserBean> userList = (List<UserBean>)session.getAttribute("userList");
		TaskBean taskBean = (TaskBean)session.getAttribute("taskBean");
		boolean identificationFlag = (boolean) session.getAttribute("identificationFlag");
		String alterError = (String)request.getAttribute("alterError");
	%>
	
	<% 
		//フラグがtrueなら
		if (identificationFlag) {
	%>
	
		<h1>タスク編集フォーム</h1>
		<hr>
		
	<%
		if(alterError != null){
	%>
			<p style="color: red;"><%=alterError%></p>
	<%
		}
	%>
		
		<form method="POST">
			<table border=1>
				<tr>
					<td>タスク名</td>
					<td><input type="text" name="taskName" value="<%=taskBean.getTaskName() %>" maxlength="50" required></td>
				</tr>
			
				<tr>
					<td>カテゴリ情報</td>
					<td><select name ="categoryId">
						<% for(CategoryBean bean : categoryList) { %>
							<% if(bean.getCategoryName().equals(taskBean.getCategoryName())) {%>
								<option value = "<%=bean.getCategoryId()%>" selected><%=bean.getCategoryName()%></option>
							<% } else { %>
								<option value = "<%=bean.getCategoryId()%>"><%=bean.getCategoryName() %></option>
							<% } %>
						<% } %>
						</select>
					</td>
				</tr>
			
				<tr>
					<td>期限</td>
					<td><input type="date" name="limitDate" id="limitDate" value="<%=taskBean.getLimitDate()%>"></td>
				</tr>
			
				<tr>
					<td>担当者情報</td>
					<td><select name ="userId">
						<% for(UserBean bean : userList) { %>
							<% if(bean.getUserName().equals(taskBean.getUserName())) {%>
								<option value = "<%=bean.getUserId()%>" selected><%=bean.getUserName()%></option>
							<% } else { %>
								<option value = "<%=bean.getUserId()%>"><%=bean.getUserName() %></option>
							<% } %>
						<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<td>ステータス情報</td>
					<td><select name ="statusCode">
						<% for(StatusBean bean : statusList) { %>
							<% if(bean.getStatusName().equals(taskBean.getStatusName())) {%>
								<option value = "<%=bean.getStatusCode()%>" selected><%=bean.getStatusName()%></option>
							<% } else { %>
								<option value = "<%=bean.getStatusCode()%>"><%=bean.getStatusName() %></option>
							<% } %>
						<% } %>
						</select>
					</td>
				</tr>
				<tr>
					<td>メモ</td>
					<td>
						<%if(taskBean.getMemo() != null) {%>
							<input type="text" name="memo" value="<%=taskBean.getMemo() %>">
						<%} else {%>
							<input type="text" name="memo" value=" " maxlength="100">
						<%}%>
					</td>
				</tr>
			</table>
			
			<input type = "submit" value="一覧画面に戻る" formaction="task-list-servlet">
			<input type = "submit" value="編集完了" formaction="task-alter-servlet">
		</form>
	<%
		} else {
	%>
		<h1>タスクの編集は本人しか行うことはできません。</h1>	
	
		<form action="task-list-servlet" method="POST">
			<input type="submit" value="一覧画面に戻る">
		</form>
	
	<%
		}
	%>
</body>
</html>