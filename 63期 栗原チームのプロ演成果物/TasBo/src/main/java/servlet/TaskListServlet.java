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

import model.dao.TaskDAO;
import model.entity.TaskBean;

/**
 * Servlet implementation class TaskListServlet
 */
@WebServlet("/task-list-servlet")
public class TaskListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//doPost(request, response);
		
		//セッション取得
		HttpSession session = request.getSession();
		
		//セッションスコープにユーザ情報がセットされていない場合
		if(session.getAttribute("user") == null) {
			//user情報がなければログイン画面へ
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			//転送
			rd.forward(request, response);
		} else {
			//doPostメソッドに処理を譲る。
			doPost(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		
		//タスク一覧取得に関するメソッドがあるDAOをインスタンス化
		TaskDAO taskDao = new TaskDAO();
		
		//TaskBean型のList作成
		List<TaskBean> taskList;
		
		//セッション取得
		HttpSession session = request.getSession();
		
		try {
			//タスク一覧の取得
			taskList = taskDao.selectAll();
			
			//セッションスコープにタスク一覧を入れる
			session.setAttribute("taskList", taskList);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			
		}
		
		//転送準備
		RequestDispatcher rd = request.getRequestDispatcher("task-list.jsp");
		//転送
		rd.forward(request, response);
	}

}
