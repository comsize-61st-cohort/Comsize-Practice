package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.check.ValidityCheck;
import model.dao.UserDAO;
import model.entity.UserBean;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//dispatcherでログイン画面に遷移
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//エンコーディング形式指定
		request.setCharacterEncoding("UTF-8");

		//tryブロックの開始
		try {

			//げっぱら
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");

			//妥当性チェックのメソッドを使用し文字の長さを確認、結果をflagに
			boolean userValidityFlag = ValidityCheck.userValidityCheck(userId, password);

			//妥当性チェックの結果がtrueの場合、ログイン処理を行う
			if (userValidityFlag) {

				//userDAOのインスタンス化
				UserDAO dao = new UserDAO();

				//DAOのメソッドを使用し、ユーザ名を受け取る
				UserBean user = dao.login(userId, password);

				//sessionを取得
				HttpSession session = request.getSession();

				//受け取ったリストが空でなかった場合(ログインに成功した場合)
				if (user != null) {

					//セッションにBeanインスタンスをセット
					session.setAttribute("user", user);

					//dispatcherでメニュー画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("menu.jsp");
					rd.forward(request, response);

					//ログインに失敗した場合
				} else {

					//ログイン失敗判定用のフラグを設定
					String errorMessage = "ユーザIDまたはパスワードが正しくありません";

					//セッションにフラグをセット
					session.setAttribute("errorMessage", errorMessage);

					//dispatcherでログイン画面に遷移
					RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
					rd.forward(request, response);

				}

			}	else {

				//ログイン失敗判定用のフラグを設定
				String errorMessage = "ユーザIDまたはパスワードが正しくありません";
				
				//sessionを取得
				HttpSession session = request.getSession();
				
				//セッションにフラグをセット
				session.setAttribute("errorMessage", errorMessage);

				//dispatcherでログイン画面に遷移
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);

			}

		} catch (SQLException | ClassNotFoundException | NullPointerException e) {

			//sessionを取得
			HttpSession session = request.getSession();
			
			//ログイン失敗判定用のフラグを設定
			String errorMessage = "ユーザIDまたはパスワードが正しくありません";

			//セッションにフラグをセット
			session.setAttribute("errorMessage", errorMessage);

			//dispatcherでログイン画面に遷移
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);

		}
	}

}
