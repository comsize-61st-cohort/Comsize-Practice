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
 * Servlet implementation class CommentAddServlet
 */
@WebServlet("/comment-add-servlet")
public class CommentAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッション取得
		HttpSession session = request.getSession();

		//セッションスコープにユーザ情報がセットされていない場合
		if (session.getAttribute("user") == null) {

			//user情報がなければログイン画面へ遷移
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			//転送
			rd.forward(request, response);

			//ログイン済みの場合
		} else {

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
					String deleteErrorMessage = "対象のタスクが見つかりません。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("deleteErrorMessage", deleteErrorMessage);

					//dispatcherでコメント投稿画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
					rd.forward(request, response);

					return;

				}
				
				//dispatcherでコメント投稿画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-add.jsp");
				rd.forward(request, response);

			} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

				//例外が発生した場合、コメント一覧画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				//転送
				rd.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		try {
			request.setCharacterEncoding("UTF-8");

			HttpSession session = request.getSession();

			UserBean user = (UserBean) session.getAttribute("user");
			TaskBean task = (TaskBean) session.getAttribute("task");

			int taskId = task.getTaskId();
			String comment = request.getParameter("comment");
			
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
				String deleteErrorMessage = "対象のタスクが見つかりません。";

				//リクエストスコープにメッセージをセット
				request.setAttribute("deleteErrorMessage", deleteErrorMessage);

				//dispatcherでコメント投稿画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
				rd.forward(request, response);

				return;

			}

			// 入力チェック
			if (comment == null || comment.trim().isEmpty()) {
				request.setAttribute("error", "コメントを入力してください。");
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				rd.forward(request, response);
				return;
			}
			//文字数チェック
			if (comment.length() > 100) {
				request.setAttribute("error", "コメントは100文字以内で入力してください。");
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				rd.forward(request, response);
				return;
			}

			CommentBean bean = new CommentBean();
			bean.setTaskId(taskId);
			bean.setUserId(user.getUserId());
			bean.setComment(comment);

			CommentDAO commentDao = new CommentDAO();

			commentDao.insert(bean);


			//コメント一覧画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("comment-list-servlet?taskId=" + taskId);
			//転送
			rd.forward(request, response);

		} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

			//例外が発生した場合、コメント一覧画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
			//転送
			rd.forward(request, response);
		}	
	}

}
