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

/**
 * Servlet implementation class CommentListServlet
 */
@WebServlet("/comment-list-servlet")
public class CommentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentListServlet() {
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

			//user情報がなければログイン画面へ遷移
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			//転送
			rd.forward(request, response);

			//ログイン済みの場合	
		} else {

			//tryブロックの開始
			try {

				//エンコーディング形式指定
				request.setCharacterEncoding("UTF-8");

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

					//dispatcherでコメント一覧画面に遷移
					//下の処理に行くとうまく判定できない
					RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
					rd.forward(request, response);

				}

				//セッションにTaskBeanをセット
				session.setAttribute("task", task);

				//CommentBeanのリストを宣言
				List<CommentBean> commentList;

				//CommentDAOのインスタンス化
				CommentDAO commentDao = new CommentDAO();

				//メソッドを使用しコメントのリストを取得
				commentList = commentDao.select(taskId);

				//セッションにコメントリストを入れる
				session.setAttribute("commentList", commentList);

				//コメント一覧画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				rd.forward(request, response);

			} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

				e.printStackTrace();

				RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
				rd.forward(request, response);
			}

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//tryブロックの開始
		try {

			//セッション取得
			HttpSession session = request.getSession();

			//エンコーディング形式指定
			request.setCharacterEncoding("UTF-8");

			//セッションからタスクBeanを受け取る
			TaskBean task = (TaskBean) session.getAttribute("task");

			//セッションのタスクBeanからタスクidを取得
			int taskId = task.getTaskId();

			//TaskDAOのインスタンス化
			TaskDAO dao = new TaskDAO();

			//※処理の変更、メソッドを使用しtaskListを取得
			//変更前 セッションから取得していた 別タブでの削除、編集対策のためDBから情報を持ってくる必要がある
			List<TaskBean> taskList = dao.selectAll();

			//TaskBeanを宣言
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
				String alreadyDeleteMessage = "対象のタスクが見つかりません。";

				//リクエストスコープにメッセージをセット
				request.setAttribute("alreadyDeleteMessage", alreadyDeleteMessage);

				//dispatcherでコメント一覧画面に遷移
				//下の処理に行くとうまく判定できない
				RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
				rd.forward(request, response);

			}

			//セッションにTaskBeanをセット
			session.setAttribute("task", task);

			//CommentBeanのリストを宣言
			List<CommentBean> commentList;

			//CommentDAOのインスタンス化
			CommentDAO commentDao = new CommentDAO();

			//メソッドを使用しコメントのリストを取得
			commentList = commentDao.select(taskId);

			//セッションにコメントリストを入れる
			session.setAttribute("commentList", commentList);

			//コメント一覧画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("comment-list.jsp");
			rd.forward(request, response);

		} catch (NullPointerException | NumberFormatException | ClassNotFoundException | SQLException e) {

			e.printStackTrace();

			RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
			rd.forward(request, response);
		}

	}

}
