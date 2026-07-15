package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {
		//分類一覧
		public List<CategoryBean> selectAll() throws ClassNotFoundException, SQLException{
			//返すリスト作成
			List<CategoryBean> categoryList = new ArrayList<CategoryBean>();
			
			
			
			String sql = "SELECT * FROM m_category";
			
			
			
			try(Connection con = ConnectionManager.getConnection();
					Statement stmt = con.createStatement();
					ResultSet res = stmt.executeQuery(sql)){
				
				while(res.next()) {
					CategoryBean categoryBean = new CategoryBean();
					
					categoryBean.setCategoryId(res.getInt("category_id"));
					categoryBean.setCategoryName(res.getString("category_name"));
					
					categoryList.add(categoryBean);
				}
			} 
			return categoryList;	
		}
}
