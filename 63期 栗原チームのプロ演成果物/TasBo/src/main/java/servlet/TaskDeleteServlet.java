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
import model.entity.TaskBean;
import model.entity.UserBean;

/**
 * Servlet implementation class TaskDeleteServlet
 */
@WebServlet("/task-delete-servlet")
public class TaskDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskDeleteServlet() {
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

				//げっぱらした値がnullだった場合
				if (request.getParameter("taskId") == null) {

					//エラーメッセージの定義
					String deleteErrorMessage = "削除するタスクを選択してください。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("deleteErrorMessage", deleteErrorMessage);

					//一覧画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
					//転送
					rd.forward(request, response);

				}

				//げっぱらでtaskIdを受け取る
				int taskId = Integer.parseInt(request.getParameter("taskId"));

				//TaskDAOのインスタンス化
				TaskDAO dao = new TaskDAO();

				//※処理の変更、メソッドを使用しtaskListを取得
				//変更前 セッションから取得していた 別タブでの削除、編集対策のためDBから情報を持ってくる必要がある
				List<TaskBean> taskList = dao.selectAll();

				//TaskBeanを宣言
				TaskBean task = null;

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
					String alreadyDeleteMessage = "対象のタスクが見つかりません。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("alreadyDeleteMessage", alreadyDeleteMessage);

					//dispatcherで削除確認画面に遷移
					//下の処理に行くとうまく判定できない
					RequestDispatcher rd = request.getRequestDispatcher("task-delete-confirm.jsp");
					rd.forward(request, response);

				}

				//セッションからログイン中のユーザ情報を取得
				UserBean user = (UserBean) session.getAttribute("user");

				//TaskBeanとUserBeanのユーザidを比較
				if (task.getUserId().equals(user.getUserId())) {

					//セッションにTaskBeanをセット
					session.setAttribute("task", task);

					//一致していなかった場合（本人ではなかった場合）
				} else {

					//エラーメッセージの定義
					String identificationMessage = "タスクの削除は担当者本人のみ行えます。";

					//リクエストスコープにメッセージをセット
					request.setAttribute("identificationMessage", identificationMessage);

				}

				//dispatcherで削除確認画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("task-delete-confirm.jsp");
				rd.forward(request, response);

			} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

				//例外が発生した場合、一覧画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
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

		//セッションからTaskBeanを取得
		TaskBean task = (TaskBean) session.getAttribute("task");

		//セッションからUserBeanを取得
		UserBean user = (UserBean) session.getAttribute("user");

		//本人確認の処理を追加
		//TaskBeanとUserBeanのユーザidを比較
		if (task.getUserId().equals(user.getUserId())) {

			//※コメント機能で追加した処理
			//コメントDAOのインスタンス化
			CommentDAO commentDao = new CommentDAO();

			//tryブロックの開始
			try {

				//CommentDAOのメソッドを使い、タスクIDのコメントを削除
				commentDao.taskIdDelete(task.getTaskId());

				//taskDAOのインスタンス化
				TaskDAO dao = new TaskDAO();

				//削除のメソッドを使用し結果件数を取得
				int resultCount = dao.delete(task.getTaskId());

				//削除成功していた場合
				if (resultCount == 1) {

					//削除成功画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-delete-success.jsp");
					//転送
					rd.forward(request, response);

				} else {

					//削除失敗画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
					//転送
					rd.forward(request, response);

				}

			} catch (NullPointerException | ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック

				//削除失敗画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
				//転送
				rd.forward(request, response);
			}

			//一致していなかった場合（本人ではなかった場合）
		} else {
			//削除失敗画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("task-delete-failure.jsp");
			//転送
			rd.forward(request, response);

		}
	}
}
