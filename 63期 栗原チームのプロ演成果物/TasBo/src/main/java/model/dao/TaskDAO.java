package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.TaskBean;

public class TaskDAO {

	public int insert(TaskBean task) throws  ClassNotFoundException, SQLException {
		

		//SQL文の作成
	    String sql =
	        "INSERT INTO t_task " +
	        "(task_name, category_id, limit_date, user_id, status_code, memo) " +
	        "VALUES (?, ?, ?, ?, ?, ?)";

	    try (
	    	//DB接続とPreparedStatement生成
	    	Connection con = ConnectionManager.getConnection();
	    	PreparedStatement pstmt = con.prepareStatement(sql)) {

	    	//タスク名をセット
	    	pstmt.setString(1, task.getTaskName());
	    	

	    	//カテゴリIDをセット
	    	pstmt.setInt(2, task.getCategoryId());

	    	//期限日をセット
	    	//valueOfでString型に変換
	    	// LocalDate型の期限日をSQLのDATE型へ変換してセット
	    	if (task.getLimitDate() != null) {
	    		pstmt.setDate(3, java.sql.Date.valueOf(task.getLimitDate()));
	    	} else {
	    		pstmt.setNull(3, java.sql.Types.DATE);
	    	}

	    	//ユーザーIDをセット
	    	pstmt.setString(4, task.getUserId());
	    	//ステータスコードをセット
	    	pstmt.setString(5, task.getStatusCode());

	    	//メモをセット
	    	pstmt.setString(6, task.getMemo());

	    	//SQL実行
	    	return pstmt.executeUpdate();
	    }
	}
	//リストを作成
	public List<TaskBean> selectAll() throws ClassNotFoundException, SQLException {
	        
		//返すList作成
	    List<TaskBean> taskList = new ArrayList<TaskBean>();
	        
	    String sql = "SELECT t1.update_datetime,t1.task_id,t1.task_name,t2.category_name,t1.limit_date,t1.user_id,t3.user_name,t4.status_name,t1.memo FROM t_task t1 JOIN m_category t2 ON t1.category_id = t2.category_id JOIN m_user t3 ON t1.user_id = t3.user_id JOIN m_status t4 ON t1.status_code = t4.status_code ORDER BY t1.task_id ASC";
	        
	    try (Connection con = ConnectionManager.getConnection();
	    	//Connectionクラスが持つcreateStatementメソッド
	    	Statement stmt = con.createStatement();
	    	ResultSet res = stmt.executeQuery(sql)){
	        	
	    	while(res.next()) {
	    		TaskBean taskBean = new TaskBean();
	    		
	    		taskBean.setTaskId(res.getInt("task_id"));
	    		taskBean.setTaskName(res.getString("task_name"));
	    		taskBean.setCategoryName(res.getString("category_name"));
	    		//toLocalDateは年月日部分を取り出すメソッド
	    		//toLocalDate()が原因でNullPointerExeption発生していたと考えられるので、条件を追加しました。
	    		if(res.getDate("limit_date") != null) {
	    			taskBean.setLimitDate(res.getDate("limit_date").toLocalDate());
	    		}
	    		taskBean.setUserId(res.getString("user_id"));
	    		taskBean.setUserName(res.getString("user_name"));
	    		taskBean.setStatusName(res.getString("status_name"));
	    		taskBean.setMemo(res.getString("memo"));
	    		taskBean.setUpdateDatetime(res.getTimestamp("update_datetime"));
	    		
	    		taskList.add(taskBean);
	    	}
	            
	    }
	    return taskList;
	}


	    /**
	    * 引数のタスクIDのカラムを削除するメソッド
	     * @param taskId
	     * @return
	     * @throws SQLException
	     * @throws ClassNotFoundException
	     */
	public int delete(int taskId) throws SQLException, ClassNotFoundException {

		//SQL文の用意
		String sql = "DELETE FROM t_task WHERE task_id = ?";

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
	
	//タスクの編集に関するメソッド
	public int alter(TaskBean task) throws SQLException, ClassNotFoundException {
		
		//更新時刻で多重に別タブで更新できてしまう事象を止めたいためにTIMESTAMPをWHERE句に追加中、更新日時がされていなかったのでupdate_datetime = CURRENT_TIMESTAMPを追加して更新日時も更新されるように
		String sql = "UPDATE t_task SET task_name = ?,category_id = ?,limit_date = ?,user_id = ?,status_code = ?,memo = ?,update_datetime = CURRENT_TIMESTAMP WHERE task_id = ? AND update_datetime = ?";
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt
				= con.prepareStatement(sql)) {
			
			pstmt.setString(1,task.getTaskName());
			pstmt.setInt(2,task.getCategoryId());
			
			//期限日をセット
	    	//setDate()はjava.sql.Dateしか受け取れない(JDBCのPreparedStatementが受け取れる型に合わせるため変換する)
	    	if (task.getLimitDate() != null) {
	    		pstmt.setDate(3, java.sql.Date.valueOf(task.getLimitDate()));
	    	} else {
	    		pstmt.setNull(3, java.sql.Types.DATE);
	    	}
	    	
	    	pstmt.setString(4, task.getUserId());
	    	pstmt.setString(5, task.getStatusCode());
	    	pstmt.setString(6, task.getMemo());
	    	pstmt.setInt(7, task.getTaskId());
	    	//ステートメントの追加のため、8番目を追加しました。
	    	pstmt.setTimestamp(8, task.getUpdateDatetime());
			
			return pstmt.executeUpdate();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
