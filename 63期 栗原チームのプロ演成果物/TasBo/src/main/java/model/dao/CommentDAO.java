package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CommentBean;

public class CommentDAO {

	
	public List<CommentBean> select(int taskId) throws ClassNotFoundException, SQLException {

		//List作成
		List<CommentBean> commentList = new ArrayList<CommentBean>();

		//SQL文の宣言
		String sql = "SELECT t1.comment_id, t1.task_id, t2.task_name, t1.user_id, t3.user_name,t1.comment, t1.update_datetime FROM t_comment t1 JOIN t_task t2 ON t1.task_id = t2.task_id JOIN m_user t3 ON t1.user_id = t3.user_id WHERE t1.task_id = ? ORDER BY t1.comment_id ASC";

		//DB接続
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			
			//プレースホルダに値をセット
			pstmt.setInt(1, taskId);
			
			//実行
			ResultSet res = pstmt.executeQuery();

			//ループの開始
			while (res.next()) {
				
				//CommentBeanのインスタンス化
				CommentBean comment = new CommentBean();
				
				//Beanに値をセット
				comment.setCommentId(res.getInt("comment_id"));
				comment.setTaskId(res.getInt("task_id"));
				comment.setTaskName(res.getString("task_name"));
				comment.setUserId(res.getString("user_id"));
				comment.setUserName(res.getString("user_name"));
				comment.setComment(res.getString("comment"));
				//getTimestampで取得できる
				comment.setUpdateDatetime(res.getTimestamp("update_datetime"));
				
				//リストにBeanを追加
				commentList.add(comment);
			}
		}
		//リストを返す
		return commentList;
	}
	
	/**
	 * 引数のタスクidのカラムを削除するメソッド
	 * @param taskId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int taskIdDelete(int taskId) throws SQLException, ClassNotFoundException {

		//SQL文の用意
		String sql = "delete from t_comment where task_id = ?";

		//DB接続
		try (Connection con = ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {

			//プレースホルダに値をセット
			pstmt.setInt(1, taskId);

			//実行
			int resultCount = pstmt.executeUpdate();

			return resultCount;

		}
	}
	
	/**
	 * 引数のコメントidのカラムを削除するメソッド
	 * @param commentId
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int commentIdDelete(int commentId) throws SQLException, ClassNotFoundException {

		//SQL文の用意
		String sql = "delete from t_comment where comment_id = ?";

		//DB接続
		try (Connection con = ConnectionManager.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {

			//プレースホルダに値をセット
			pstmt.setInt(1, commentId);

			//実行
			int resultCount = pstmt.executeUpdate();

			return resultCount;
			
		}
	}
	
	public int insert(CommentBean comment)
	        throws ClassNotFoundException, SQLException {

	    String sql =
	        "INSERT INTO t_comment(task_id, user_id, comment, update_datetime) "
	      + "VALUES(?, ?, ?, CURRENT_TIMESTAMP)";

	    try(Connection con = ConnectionManager.getConnection();
	        PreparedStatement pstmt = con.prepareStatement(sql)) {

	        pstmt.setInt(1, comment.getTaskId());
	        pstmt.setString(2, comment.getUserId());
	        pstmt.setString(3, comment.getComment());

	        return pstmt.executeUpdate();
	    }
	}

}
