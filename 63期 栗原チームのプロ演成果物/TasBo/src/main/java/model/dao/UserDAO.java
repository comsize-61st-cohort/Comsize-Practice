package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.UserBean;

public class UserDAO {

	//メソッドの定義
	public UserBean login(String userId, String password) throws SQLException, ClassNotFoundException {

		//Beanのインスタンス化
		UserBean user = new UserBean();

		//SQL文の用意
		String sql = "SELECT user_id, user_name FROM m_user WHERE user_id = ? AND password = ?";

		//DB接続
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//プレースホルダに値をセット
			pstmt.setString(1, userId);
			pstmt.setString(2, password);

			//実行
			ResultSet res = pstmt.executeQuery();

			//ループ開始
			if (res.next()) {

				//Beanのセッターに追加
				user.setUserId(res.getString("user_id"));
				user.setUserName(res.getString("user_name"));
				user.setPassword(password);

				return user;

				//登録がなかった場合、参照値がnullのインスタンスを返す
			} else {

				return user = null;

			}
		}

	}
	
	/**
	 * ユーザ一覧のリストを返すメソッド
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserBean> selectAll() throws SQLException, ClassNotFoundException {

		//リストの宣言
		List<UserBean> userList = new ArrayList<>();

		//SQL文の用意
		String sql = "SELECT * FROM m_user";

		//DB接続
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			//実行
			ResultSet res = pstmt.executeQuery();

			//ループの開始
			while (res.next()) {
				
				//Beanのインスタンス化
				UserBean user = new UserBean();
				
				//Beanに値をセット
				user.setUserId(res.getString("user_id"));
				user.setPassword(res.getString("password"));
				user.setUserName(res.getString("user_name"));
				
				
				//リストにBeanを追加
				userList.add(user);
			}

			//リストを返す
			return userList;
		}

	}
}
