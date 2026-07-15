package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.CommentBean;
import model.entity.TaskBean;

class CommentDAOTest extends CommentDAO {

	@Test
	void test_select_成功() {

		//DAO(テスト対象)のインスタンス化
		TaskDAO taskDao = new TaskDAO();

		//TaskBeanのインスタンス化
		TaskBean task = new TaskBean();

		//タスク名の宣言
		String taskName = "JUnitテスト用タスク登録";
		int categoryId = 1;
		String userId = "admin";
		String statusCode = "00";

		//タスクIDの宣言
		int taskId = 0;

		//テスト用の値をセット
		task.setTaskName(taskName);
		task.setCategoryId(categoryId);
		task.setUserId(userId);
		task.setStatusCode(statusCode);

		try {

			//登録のメソッドを使用
			taskDao.insert(task);

			//SQL文の用意
			String taskSql = "select task_id from t_task where task_name = ?";

			//DB接続
			try (Connection con = ConnectionManager.getConnection();
					PreparedStatement pstmt = con.prepareStatement(taskSql)) {

				//プレースホルダに値をセット
				pstmt.setString(1, taskName);

				//実行
				ResultSet res = pstmt.executeQuery();

				//ループ開始
				if (res.next()) {

					//結果のタスクIDを変数にセット
					taskId = res.getInt("task_id");
				}
			}

			//DAO(テスト対象)のインスタンス化
			CommentDAO commentDao = new CommentDAO();

			//Beanのインスタンス化
			CommentBean comment = new CommentBean();

			//コメントidの宣言
			int commentId = 0;

			//Beanに値をセット
			comment.setTaskId(taskId);
			comment.setUserId(userId);
			comment.setComment("JUnitテスト用コメント");

			//登録のメソッド実行
			commentDao.insert(comment);

			//SQL文の用意
			String commentSql = "select comment_id from t_comment where task_id = ?";

			//DB接続
			try (Connection con = ConnectionManager.getConnection();
					PreparedStatement pstmt = con.prepareStatement(commentSql)) {

				//プレースホルダに値をセット
				pstmt.setInt(1, taskId);

				//実行
				ResultSet res = pstmt.executeQuery();

				//ループ開始
				if (res.next()) {

					//結果のタスクIDを変数にセット
					commentId = res.getInt("comment_id");
				}
			}

			//リストの宣言
			List<CommentBean> commentList = null;

			//テスト対象のメソッドを使いリストを取得
			commentList = commentDao.select(taskId);

			//assert
			assertNotNull(commentList);

			//コメントを削除
			commentDao.commentIdDelete(commentId);

			//タスクを削除
			taskDao.delete(taskId);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test
	void test_taskIdDelete_成功() {

		//DAO(テスト対象)のインスタンス化
		TaskDAO taskDao = new TaskDAO();

		//TaskBeanのインスタンス化
		TaskBean task = new TaskBean();

		//タスク名の宣言
		String taskName = "JUnitテスト用タスク登録";
		int categoryId = 1;
		String userId = "admin";
		String statusCode = "00";

		//タスクIDの宣言
		int taskId = 0;

		//テスト用の値をセット
		task.setTaskName(taskName);
		task.setCategoryId(categoryId);
		task.setUserId(userId);
		task.setStatusCode(statusCode);

		try {

			//登録のメソッドを使用
			taskDao.insert(task);

			//SQL文の用意
			String taskSql = "select task_id from t_task where task_name = ?";

			//DB接続
			try (Connection con = ConnectionManager.getConnection();
					PreparedStatement pstmt = con.prepareStatement(taskSql)) {

				//プレースホルダに値をセット
				pstmt.setString(1, taskName);

				//実行
				ResultSet res = pstmt.executeQuery();

				//ループ開始
				if (res.next()) {

					//結果のタスクIDを変数にセット
					taskId = res.getInt("task_id");
				}
			}

			//DAO(テスト対象)のインスタンス化
			CommentDAO commentDao = new CommentDAO();

			//Beanのインスタンス化
			CommentBean comment = new CommentBean();

			//Beanに値をセット
			comment.setTaskId(taskId);
			comment.setUserId(userId);
			comment.setComment("JUnitテスト用コメント");

			//登録のメソッド実行
			commentDao.insert(comment);

			//コメントを削除
			commentDao.taskIdDelete(taskId);

			//タスクを削除
			taskDao.delete(taskId);

		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
