package model.check;

import java.sql.SQLException;

import model.entity.TaskBean;

public class ValidityCheck {
	
	/**
	 * ユーザID、パスワードの長さをチェックするメソッド
	 * @param userId
	 * @param password
	 * @return
	 * @throws NullPointerException
	 */
	public static boolean userValidityCheck(String userId,String password) throws NullPointerException,NumberFormatException {

		if (userId.length() <= 24 && password.length() <= 32 && userId != "" && password != "") {

			return true;

		} else {

			return false;

		}

	}
	
	//String taskName,int categoryId,String userId,String statusCode,String memoのように１つずつ指定いていたがTaskBean型で送ることに
	public static boolean taskVaridityCheck(TaskBean alterTask) throws SQLException,NumberFormatException {
		if(alterTask.getTaskName().length() <= 50 && alterTask.getMemo().length() <= 100 && alterTask.getTaskName() != "") {
			return true;
		} else {
			return false;
		}
	}
	
	//編集時のタスク名の妥当性チェック
	public static boolean taskNameVaridityCheck(String taskName) {
		if(taskName.length() <= 50 && taskName != "") {
			return true;
		}
		return false;
	}
	
	//編集時のメモの妥当性チェック
	public static boolean memoVaridityCheck(String memo) {
		if(memo.length() <= 100) {
			return true;
		}
		return false;
	}
	
	
}
