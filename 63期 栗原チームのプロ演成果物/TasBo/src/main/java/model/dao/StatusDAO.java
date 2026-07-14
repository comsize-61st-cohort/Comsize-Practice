package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.entity.StatusBean;

public class StatusDAO {
	//ステータス一覧
	public List<StatusBean> selectAll() throws ClassNotFoundException, SQLException{
		//返すリスト作成
		List<StatusBean> statusList = new ArrayList<StatusBean>();
		
		String sql = "SELECT * FROM m_status";
		
		try(Connection con = ConnectionManager.getConnection();
				Statement stmt = con.createStatement();
				ResultSet res = stmt.executeQuery(sql)){
			
			while(res.next()) {
				StatusBean statusBean = new StatusBean();
				
				statusBean.setStatusCode(res.getString("status_code"));
				statusBean.setStatusName(res.getString("status_name"));
				
				statusList.add(statusBean);
			}
		} 
		return statusList;	
	}
}
