package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CommentDAO;
import model.dao.TaskDAO;
import model.entity.CommentBean;
import model.entity.TaskBean;
import model.entity.UserBean;

/**
 * Servlet implementation class CommentDeleteServlet
 */
@WebServlet("/comment-delete-servlet")
public class CommentDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//セッション取得
		HttpSession session = request.getSession();

		//セッションスコープにユーザ情報がセットされていない場合
		if (session.getAttribute("user") == null) {
			//user情報がなければログイン
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			//転送
			rd.forward(request, response);

			//ログイン済みの場合	
		} else {

			//tryブロックの開始
			try {

				//エンコーディング形式指定
				request.setCharacterEncoding("UTF-8");

				//エラーメッセージを先に宣言
				String deleteErrorMessage = null;

				//げっぱらした値がnullだった場合
				if (request.getParameter("commentId") == null) {

					//エラーメッセージの定義
					String error = "削除するコメントを選択してください。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("error", error);

					//コメント一覧画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
					//転送
					rd.forward(request, response);

					return;

				}

				//セッションからタスクBeanを受け取る
				TaskBean task = (TaskBean) session.getAttribute("task");

				//セッションのタスクBeanからタスクidを取得
				int taskId = task.getTaskId();

				//TaskDAOのインスタンス化
				TaskDAO dao = new TaskDAO();

				//※処理の変更、メソッドを使用しtaskListを取得
				//変更前 セッションから取得していた 別タブでの削除、編集対策のためDBから情報を持ってくる必要がある
				List<TaskBean> taskList = dao.selectAll();

				//確認用にタスクBeanをnullに
				task = null;

				//taskListから,送られたtaskIdに該当するBeanを取り出す
				for (int i = 0; i < taskList.size(); i++) {

					//タスクリスト内のBeanとげっぱらしたIDが一致していた場合
					if (taskList.get(i).getTaskId() == taskId) {

						//BeanにリストのBeanを代入
						task = taskList.get(i);
						break;

					}
				}

				//該当するタスクが存在しなかった場合(TaskBeanがnullの場合)
				if (task == null) {

					//エラーメッセージの定義
					deleteErrorMessage = "対象のタスクが見つかりません。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("deleteErrorMessage", deleteErrorMessage);

					//dispatcherでコメント削除確認画面に遷移
					//下の処理に行くとうまく判定できない
					RequestDispatcher rd = request.getRequestDispatcher("comment-delete-confirm.jsp");
					rd.forward(request, response);

					return;

				}

				//げっぱらでコメントIDを取得
				int commentId = Integer.parseInt(request.getParameter("commentId"));

				//コメントDAOのインスタンス化
				CommentDAO commentDao = new CommentDAO();

				//リストを取得
				List<CommentBean> commentList = commentDao.select(taskId);

				//コメントBeanを宣言
				CommentBean comment = null;

				//コメントListから,送られたコメントIdに該当するBeanを取り出す
				for (int i = 0; i < commentList.size(); i++) {

					//コメントリスト内のBeanとげっぱらしたIDが一致していた場合
					if (commentList.get(i).getCommentId() == commentId) {

						//BeanにリストのBeanを代入
						comment = commentList.get(i);
						break;

					}
				}

				//該当するコメントが存在しなかった場合(TaskBeanがnullの場合)
				if (comment == null) {

					//エラーメッセージの定義
					deleteErrorMessage = "対象のコメントが見つかりません。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("deleteErrorMessage", deleteErrorMessage);

					//dispatcherでコメント削除確認画面に遷移
					//下の処理に行くとうまく判定できない
					RequestDispatcher rd = request.getRequestDispatcher("comment-delete-confirm.jsp");
					rd.forward(request, response);

					return;

				}

				//セッションからログイン中のユーザ情報を取得
				UserBean user = (UserBean) session.getAttribute("user");

				//CommentBeanとUserBeanのユーザidを比較
				if (comment.getUserId().equals(user.getUserId())) {

					//セッションにTaskBeanをセット
					session.setAttribute("comment", comment);

					//一致していなかった場合（本人ではなかった場合）
				} else {

					//エラーメッセージの定義
					String identificationMessage = "コメントの削除は担当者本人のみ行えます。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("identificationMessage", identificationMessage);

				}

				//コメント削除確認画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-delete-confirm.jsp");
				rd.forward(request, response);

			} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

				//例外が発生した場合、コメント一覧画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				//転送
				rd.forward(request, response);

			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//セッションの取得
		HttpSession session = request.getSession();

		//エラーメッセージを先に宣言
		String deleteErrorMessage = null;

		//tryブロックの開始
		try {

			//セッションからタスクBeanを受け取る
			TaskBean task = (TaskBean) session.getAttribute("task");

			//セッションのタスクBeanからタスクidを取得
			int taskId = task.getTaskId();

			//TaskDAOのインスタンス化
			TaskDAO dao = new TaskDAO();

			//※処理の変更、メソッドを使用しtaskListを取得
			//変更前 セッションから取得していた 別タブでの削除、編集対策のためDBから情報を持ってくる必要がある
			List<TaskBean> taskList = dao.selectAll();

			//確認用にタスクBeanをnullに
			task = null;

			//taskListから,送られたtaskIdに該当するBeanを取り出す
			for (int i = 0; i < taskList.size(); i++) {

				//タスクリスト内のBeanとげっぱらしたIDが一致していた場合
				if (taskList.get(i).getTaskId() == taskId) {

					//BeanにリストのBeanを代入
					task = taskList.get(i);
					break;

				}
			}

			//該当するタスクが存在しなかった場合(TaskBeanがnullの場合)
			if (task == null) {

				//エラーメッセージの定義
				deleteErrorMessage = "対象のタスクが見つかりません。";

				//リクエストスコープにメッセージをセット
				request.setAttribute("deleteErrorMessage", deleteErrorMessage);

				//dispatcherでコメント削除失敗画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-delete-failure.jsp");
				rd.forward(request, response);

				return;

			}
			
			//セッションからコメントBeanを取得
			CommentBean comment = (CommentBean) session.getAttribute("comment");

			//取得したBeanからコメントIDを取得
			int commentId = comment.getCommentId();

			//コメントDAOのインスタンス化
			CommentDAO commentDao = new CommentDAO();

			//リストを取得
			List<CommentBean> commentList = commentDao.select(taskId);

			//確認用にコメントBeanをnullに
			comment = null;

			//コメントListから,送られたコメントIdに該当するBeanを取り出す
			for (int i = 0; i < commentList.size(); i++) {

				//コメントリスト内のBeanとげっぱらしたIDが一致していた場合
				if (commentList.get(i).getCommentId() == commentId) {

					//BeanにリストのBeanを代入
					comment = commentList.get(i);
					break;

				}
			}

			//該当するコメントが存在しなかった場合(コメントBeanがnullの場合)
			if (comment == null) {

				//エラーメッセージの定義
				deleteErrorMessage = "対象のコメントが見つかりません。";

				//リクエストスコープにメッセージをセット
				request.setAttribute("deleteErrorMessage", deleteErrorMessage);

				//dispatcherでコメント削除失敗画面に遷移
				//下の処理に行くとうまく判定できない
				RequestDispatcher rd = request.getRequestDispatcher("comment-delete-failure.jsp");
				rd.forward(request, response);

				return;

			}

			//セッションからログイン中のユーザ情報を取得
			UserBean user = (UserBean) session.getAttribute("user");
			
			//CommentBeanとUserBeanのユーザidを比較
			if (comment.getUserId().equals(user.getUserId())) {
				
				//コメントDAOのメソッド使用し削除
				int resultCount = commentDao.commentIdDelete(commentId);

				//削除成功していた場合
				if (resultCount == 1) {

					//削除成功画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("comment-delete-success.jsp");
					//転送
					rd.forward(request, response);

				} else {

					//削除失敗画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("comment-delete-failure.jsp");
					//転送
					rd.forward(request, response);

				}
				
				//一致していなかった場合（本人ではなかった場合）
			} else {

				//エラーメッセージの定義
				String identificationMessage = "コメントの削除は担当者本人のみ行えます。";

				//リクエストスコープにメッセージをセット
				request.setAttribute("identificationMessage", identificationMessage);
				
				//dispatcherでコメント削除失敗画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-delete-failure.jsp");
				rd.forward(request, response);

			}
			
			

		} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

			//dispatcherでコメント削除失敗画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("comment-delete-failure.jsp");
			rd.forward(request, response);

		}

	}

}
