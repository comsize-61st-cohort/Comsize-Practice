<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.entity.UserBean"%>
<%@ page import="model.entity.CategoryBean"%>
<%@ page import="model.entity.StatusBean"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク登録</title>
</head>
<body>


	<%
	
	//セッションスコープで送られてきたデータの呼び出し
	List<CategoryBean> categoryList = (List<CategoryBean>) session.getAttribute("categoryList");
	List<UserBean> userList = (List<UserBean>) session.getAttribute("userList");
	List<StatusBean> statusList = (List<StatusBean>) session.getAttribute("statusList");
	
	//エラー表記の表示
	String errorMsg = (String) request.getAttribute("errorMsg");
	if (errorMsg != null) {
	%>
	<p style="color: red;"><%=errorMsg%></p>
	<%
	}
	%>


	<form action="task-add-servlet" method="post">

		<h3>タスク登録</h3>

		<table border="1">

			<tr>
				<th>タスク名</th>
				<td><input type="text" name="taskName" required></td>
			</tr>

			<tr>
				<th>カテゴリ情報</th>
				<td><select name="categoryId">

						<%
						for (CategoryBean category : categoryList) {
						%>

						<option value="<%=category.getCategoryId()%>">
							<%=category.getCategoryName()%>
						</option>

						<%
						}
						%>

				</select></td>
			</tr>


			<tr>
				<th>期限</th>
				<td><input type="date" name="limitDate" id="limitDate"></td>



			</tr>

			<tr>
				<th>担当者</th>
				<td><select name="userId">

						<%
						for (UserBean user : userList) {
						%>

						<option value="<%=user.getUserId()%>">
							<%=user.getUserName()%>
						</option>

						<%
						}
						%>

				</select></td>
			</tr>

			<tr>
				<th>ステータス情報</th>
				<td><select name="statusCode">
						<%
						for (StatusBean status : statusList) {
						%>
						<option value="<%=status.getStatusCode()%>">
							<%=status.getStatusName()%>
						</option>
						<%
						}
						%>
				</select></td>
			</tr>

			<tr>
				<th>メモ</th>
				<td><input type="text" name="memo"></td>
			</tr>

		</table>

		<br> <input type="submit" value="登録"> <input type="reset"
			value="クリア">

	</form>

	<br>
	<br>

	<form action="menu.jsp" method="get">
		<input type="submit" value="メニュー画面へ">
	</form>

</body>
</html>