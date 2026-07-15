package model.entity;

import java.io.Serializable;

public class UserBean implements Serializable {
	
	//フィールドの定義
	private String userId;
	private String password;
	private String userName;
	
	//getter,setterの生成
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String useName) {
		this.userName = useName;
	}
	
	
	

}
