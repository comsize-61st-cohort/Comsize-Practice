package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.StatusBean;

class StatusDAOTest extends StatusDAO {

	@Test
	void test_selectAll_成功() {

		//DAO(テスト対象)のインスタンス化
		StatusDAO dao = new StatusDAO();

		//リストの宣言
		List<StatusBean> statusList = null;

		//メソッド使用
		try {

			statusList = dao.selectAll();

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
			
		//assert
		assertNotNull(statusList);

	}
}

