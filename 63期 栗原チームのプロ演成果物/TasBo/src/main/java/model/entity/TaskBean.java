package model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

public class TaskBean implements Serializable {

    private int taskId;
    private int categoryId;
    private String taskName;
    private String categoryName;
    private LocalDate limitDate;  
    private String userName;
    private String statusName;
    private String memo;
    private String userId;
    private String statusCode;
    //編集の機能のために必要であったために追加しました。
    private Timestamp updateDatetime;
    
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public LocalDate getLimitDate() {
		return limitDate;
	}
	public void setLimitDate(LocalDate limitDate) {
		this.limitDate = limitDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	//編集関連で必要になったので復活させました
	public Timestamp getUpdateDatetime() {
	    return updateDatetime;
	}

	public void setUpdateDatetime(Timestamp updateDatetime) {
	    this.updateDatetime = updateDatetime;
	}
    
}