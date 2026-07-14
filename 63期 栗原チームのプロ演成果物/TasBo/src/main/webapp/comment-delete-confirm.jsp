<%@page import="model.entity.CommentBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント削除確認画面</title>
</head>
<body>
	
	<%
	//セッションからCommentBeanを取得
	CommentBean comment = (CommentBean) session.getAttribute("comment");
	
	//リクエストスコープからエラーメッセージを取得
	String deleteErrorMessage = (String) request.getAttribute("deleteErrorMessage");
	String identificationMessage = (String) request.getAttribute("identificationMessage");
	
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
		<% 
		//本人確認フラグがnullなら
		if (identificationMessage == null) {
		%>
	
			<h2>こちらのコメントを削除します、よろしいですか?</h2>
	
			<table border = 1>
				<tr>
					<th>コメント投稿者</th>
					<th>コメント</th>
					<th>コメント投稿日時</th>
				<tr>
				<tr>
					<td><%=comment.getUserName()%></td>
					<td><%=comment.getComment()%></td>
					<td><%=comment.getUpdateDatetime().toString().substring(0, 16)%></td>
				</tr>
			</table>
	
			<form method="post">
				<input type="submit" value="削除" formaction="comment-delete-servlet">
				<input type="submit" value="一覧画面に戻る" formaction="comment-list.jsp">
			</form>
	
		<%
		//本人ではない場合(特定フラグがnullではない場合)
		} else {
		%>
			<h2><%= identificationMessage %></h2>
	
			<form action="comment-list.jsp" method="post">
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