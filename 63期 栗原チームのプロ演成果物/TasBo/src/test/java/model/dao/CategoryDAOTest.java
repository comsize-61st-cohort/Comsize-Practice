package model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.entity.CategoryBean;

class CategoryDAOTest extends CategoryDAO {

	@Test
	void test_selectAll_成功() {

		//DAO(テスト対象)のインスタンス化
		CategoryDAO dao = new CategoryDAO();

		//リストの宣言
		List<CategoryBean> categoryList = null;

		//メソッド使用
		try {

			categoryList = dao.selectAll();

		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
			
		//assert
		assertNotNull(categoryList);

	}

}
