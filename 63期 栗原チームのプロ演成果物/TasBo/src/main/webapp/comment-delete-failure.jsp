<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラー画面</title>
</head>
<body>

	<%
	
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
		
				<h2>削除に失敗しました</h2>
				<form action="comment-list.jsp" method="post">
					<input type="submit" value="一覧画面に戻る">
				</form>
		
			<%
			//本人ではない場合(特定フラグがnullではない場合)
			} else {
			%>
				<h2><%= identificationMessage %></h2>
		
				<form action="comment-list-servlet" method="post">
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