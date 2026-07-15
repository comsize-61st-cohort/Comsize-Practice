package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.check.ValidityCheck;
import model.dao.CategoryDAO;
import model.dao.StatusDAO;
import model.dao.TaskDAO;
import model.dao.UserDAO;
import model.entity.CategoryBean;
import model.entity.StatusBean;
import model.entity.TaskBean;
import model.entity.UserBean;

/**
 * Servlet implementation class TaskAlterServlet
 */
@WebServlet("/task-alter-servlet")
public class TaskAlterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskAlterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//リクエストのエンコーディング方式(ない場合文字化けする)
		request.setCharacterEncoding("UTF-8");
		
		//セッション取得
		HttpSession session = request.getSession();
				
		//セッションスコープにユーザ情報がセットされていない場合
		if(session.getAttribute("user") == null) {
			//user情報がなければログイン画面へ
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			//転送
			rd.forward(request, response);
		} else {
			
			//tryブロックの開始(NullPointerExeptionとナンバーフォーマット用)
			//NullPointerExeptionの書き方をやめ、task == nullの条件文
			try {

				//本人確認用のフラグを定義
				boolean identificationFlag;

				//エンコーディング形式指定
				request.setCharacterEncoding("UTF-8");

				//getParameterでtaskIdを受け取る
				int taskId = Integer.parseInt(request.getParameter("taskId"));
				
				//DAOから直接メソッドで一覧を取得する形に変更したいのでインスタンス化
				TaskDAO dao = new TaskDAO();

				//(旧)セッションからtaskListを取得
				//(新)メソッドを使用し一覧取得
				List<TaskBean> taskList = null;
				try {
					taskList = dao.selectAll();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO 自動生成された catch ブロック
					//e.printStackTrace();
					request.setAttribute("alterError", "入力フォームに必要な情報の取得に失敗しました。");
					
					//一覧画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
					//転送
					rd.forward(request, response);
				}
				
				//TaskBeanを宣言
				TaskBean task = null;

				//taskListから,送られたtaskIdに該当するBeanを取り出す
				for (int i = 0; i < taskList.size(); i++) {

					if (taskList.get(i).getTaskId() == taskId) {

						task = taskList.get(i);
						session.setAttribute("taskBean", task);
						break;
					}
				}
				
				//タスクが見つからなかった場合
				if(task == null) {
					//エラーメッセージをリクエストスコープへ
					request.setAttribute("alterError", "対象のタスクが見つかりません。");
					
					//一覧画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
					//転送
					rd.forward(request, response);
				}

				//セッションからログイン中のユーザ情報を取得
				UserBean user = (UserBean) session.getAttribute("user");

				//TaskBeanとUserBeanのユーザidを比較
				if (task.getUserId().equals(user.getUserId())) {

					//合致していた場合、フラグにtrueをセット
					identificationFlag = true;

					//一致していなかった場合（本人ではなかった場合）
				} else {
					
					//フラグにfalseをセット
					identificationFlag = false;

				}
				
				//セッションにフラグをセット
				session.setAttribute("identificationFlag", identificationFlag);
				
			//タスクIDが改ざんされた想定
			} catch (NumberFormatException e) {
				
				request.setAttribute("alterError", "不正な操作が行われました。");
				
				//例外が発生した場合、一覧画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
				//転送
				rd.forward(request, response);

			}
			
			//使用するメソッドのあるDAOのインスタンス化
			CategoryDAO categoryDao = new CategoryDAO();
			StatusDAO statusDao = new StatusDAO();
			UserDAO userDao = new UserDAO();
			
			//
			session.setAttribute("taskId", request.getParameter("taskId"));
						
			List<CategoryBean> categoryList;
			List<StatusBean> statusList;
			List<UserBean> userList;
					
			try {
				categoryList = categoryDao.selectAll();
				statusList = statusDao.selectAll();
				userList = userDao.selectAll();
						
				session.setAttribute("categoryList", categoryList);
				session.setAttribute("statusList", statusList);
				session.setAttribute("userList", userList);
						
				//転送準備
				RequestDispatcher rd = request.getRequestDispatcher("task-alter-form.jsp");
				//転送
				rd.forward(request, response);
						
			} catch (ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック
				//e.printStackTrace();
					
				request.setAttribute("alterError", "入力フォームに必要な情報の取得に失敗しました。");
					
				RequestDispatcher rd = request.getRequestDispatcher("task-list-servlet");
				//転送
				rd.forward(request, response);
					
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//リクエストのエンコーディング方式(ない場合文字化けする)
		request.setCharacterEncoding("UTF-8");
		
		//System.out.println("updateDatetime = " + request.getParameter("updateDatetime"));
		
		//セッションの取得		
		HttpSession session = request.getSession();
		
		//使用するメソッドのあるDAOのインスタンス化	
		TaskDAO dao = new TaskDAO();
		
		//編集後の値の保持用のBean
		TaskBean alterTask = new TaskBean();
		
		//対応済み。一覧から編集画面に飛ぶときに変な値を指定された場合は止められる
		alterTask.setTaskId(Integer.parseInt((String) session.getAttribute("taskId")));
		
		//タスク名をチェックして、不当な形で合った場合に入力フォームに再度遷移
		if(ValidityCheck.taskNameVaridityCheck(request.getParameter("taskName"))) {
			alterTask.setTaskName(request.getParameter("taskName"));
		} else {
			request.setAttribute("alterError", "タスク名の入力が正しくありません。");
			//転送準備
			RequestDispatcher rd = request.getRequestDispatcher("task-alter-form.jsp");
			//転送
			rd.forward(request, response);
		}

		alterTask.setCategoryId(Integer.parseInt(request.getParameter("categoryId")));
		
		
		//LocalDate型は変換しなければsetLimitDateを使用できない。
		//alterTask.setLimitDate(LocalDate.parse(request.getParameter("limitDate")));
		
		if(request.getParameter("limitDate") != null && !request.getParameter("limitDate").isEmpty()) {
			//入力された期限が本日以前であった場合
			if(LocalDate.parse(request.getParameter("limitDate")).isBefore(LocalDate.now())){
				//alterTask.setLimitDate(LocalDate.parse(request.getParameter("limitDate")));
				request.setAttribute("alterError", "本日以降の日付を入力してください。。");
				//転送準備
				RequestDispatcher rd = request.getRequestDispatcher("task-alter-form.jsp");
				//転送
				rd.forward(request, response);
				return;
			} else {
				alterTask.setLimitDate(LocalDate.parse(request.getParameter("limitDate")));
			}
		} else {
		    alterTask.setLimitDate(null);
		}
		
		alterTask.setUserId(request.getParameter("userId"));
		alterTask.setStatusCode(request.getParameter("statusCode"));
		//メモの妥当性チェック
		if(ValidityCheck.memoVaridityCheck(request.getParameter("memo"))) {
			alterTask.setMemo(request.getParameter("memo"));
		} else {
			request.setAttribute("alterError", "メモの入力が正しくありません。");
			//転送準備
			RequestDispatcher rd = request.getRequestDispatcher("task-alter-form.jsp");
			//転送
			rd.forward(request, response);
		}
		
		//修正後の処理
		//sessionにある"taskBean"属性にある更新日時を取り出したいのでsessionから変更前の情報を取得
		TaskBean taskBean = (TaskBean)session.getAttribute("taskBean");
		
		if(taskBean.getUpdateDatetime() != null) {
			alterTask.setUpdateDatetime(taskBean.getUpdateDatetime());
		}
		
		try {
			int count = dao.alter(alterTask);
			
			if(count == 1) {
				//転送準備
				RequestDispatcher rd = request.getRequestDispatcher("task-alter-success.jsp");
				//転送
				rd.forward(request, response);
			} else {
				//転送準備
				RequestDispatcher rd = request.getRequestDispatcher("task-alter-failure.jsp");
				//転送
				rd.forward(request, response);
				
			}
			
		} catch (NumberFormatException | NullPointerException | ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			//e.printStackTrace();
			//転送準備
			RequestDispatcher rd = request.getRequestDispatcher("task-alter-failure.jsp");
			//転送
			rd.forward(request, response);
		}
		
	}

}
