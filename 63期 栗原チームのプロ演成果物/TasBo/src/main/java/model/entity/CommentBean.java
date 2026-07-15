package model.entity;

import java.io.Serializable;

public class CommentBean implements Serializable {
	
	//フィールドの定義
	int commentId;
	int taskId;
	String taskName;
	String userId;
	String userName;
	String comment;
	//sqlのTimestamp型にする必要がある
	java.sql.Timestamp updateDatetime;
	
	public CommentBean() {
		
	}
	
	//getter,setterの自動生成
	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public java.sql.Timestamp getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(java.sql.Timestamp timestamp) {
		this.updateDatetime = timestamp;
	}
	
	

}
