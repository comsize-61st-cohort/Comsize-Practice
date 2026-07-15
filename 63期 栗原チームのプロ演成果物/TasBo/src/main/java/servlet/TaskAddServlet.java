package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.CategoryDAO;
import model.dao.StatusDAO;
import model.dao.TaskDAO;
import model.dao.UserDAO;
import model.entity.TaskBean;

/**
 * Servlet implementation class TaskAddServlet
 */
@WebServlet("/task-add-servlet")
public class TaskAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		CategoryDAO categoryDao = new CategoryDAO();
		UserDAO userDao = new UserDAO();
		StatusDAO statusDao = new StatusDAO();
		//sessionを取得
		HttpSession session = request.getSession();

		try {

			session.setAttribute("categoryList", categoryDao.selectAll());

			session.setAttribute("userList", userDao.selectAll());

			session.setAttribute("statusList", statusDao.selectAll());

			RequestDispatcher rd = request.getRequestDispatcher("/task-add.jsp");

			rd.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */

	//POSTリクエストを受け取る
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		try {
			
			//文字コードをUTF-8に設定
			request.setCharacterEncoding("UTF-8");

			//フォームの値を取得
			String taskName = request.getParameter("taskName");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			String dateStr = request.getParameter("limitDate");
			String userId = request.getParameter("userId");
			String statusCode = request.getParameter("statusCode");
			String memo = request.getParameter("memo");

			// 日付チェック
			//①日付が入力されているかどうかの確認。
			//日付が入力されている場合のみ、以降の日付チェックを行う
			if (dateStr != null && !dateStr.isEmpty()) {

				//②画面から送られてきた日付の文字列を日付型(LocalDate)に変換
				LocalDate limitDate = LocalDate.parse(dateStr);

				//③isBeforeメソッドを使いきょうの日付と比較して
				//昨日以前の日付が入力されていた際エラーメッセージを表記する
				//保存するデータ名と実際に表示させる文言をtask-add.jspに送る
				if (limitDate.isBefore(LocalDate.now())) {

					request.setAttribute("errorMsg", "本日以降の日付を入力してください");

					RequestDispatcher rd = request.getRequestDispatcher("/task-add.jsp");

					rd.forward(request, response);
					return;
				}
			}
			// タスク名チェック
			//タスク名が50文字を超過していた際
			//エラーメッセージを表記する
			//保存するデータ名と実際に表示させる文言をtask-add.jspに送る
			if (taskName != null && taskName.length() > 50) {
				request.setAttribute("errorMsg", "タスク名の入力可能文字数を超えています。");

				RequestDispatcher rd = request.getRequestDispatcher("/task-add.jsp");
				rd.forward(request, response);
				return;
			}

			// メモチェック
			//メモが100文字を超過していた際
			//エラーメッセージを表記する
			//保存するデータ名と実際に表示させる文言をtask-add.jspに送る
			if (memo != null && memo.length() > 100) {
				request.setAttribute("errorMsg", "メモの入力可能文字数を超えています。");

				RequestDispatcher rd = request.getRequestDispatcher("/task-add.jsp");
				rd.forward(request, response);
				return;
			}

			//TaskBeanを作成
			TaskBean task = new TaskBean();

			//Beanに値をセット
			task.setTaskName(taskName);
			task.setCategoryId(categoryId);

			//日付を変換してセット
			if (dateStr != null && !dateStr.isEmpty()) {
				task.setLimitDate(LocalDate.parse(dateStr));
			}

			//残りの項目をセット
			task.setUserId(userId);
			task.setStatusCode(statusCode);
			task.setMemo(memo);

			//DAOのインスタンス化
			TaskDAO dao = new TaskDAO();

			//DBへ登録されるとカウントが＋１される
			int count = dao.insert(task);

			//登録に成功したらadd-success.jspへ遷移
			if (count > 0) {
				RequestDispatcher rd = request.getRequestDispatcher("add-success.jsp");
				rd.forward(request, response);

				//登録に失敗したらadd-failure.jspへ遷移
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("add-failure.jsp");
				rd.forward(request, response);
			}

			//DB接続失敗やSQLエラーなどが発生した場合の例外処理
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {

			//エラー内容をコンソールに出力
			e.printStackTrace();
			//エラー画面へ遷移
			RequestDispatcher rd = request.getRequestDispatcher("add-failure.jsp");
			rd.forward(request, response);
		}
	}
}