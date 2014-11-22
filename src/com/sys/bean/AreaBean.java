package com.sys.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.sys.dao.JdbcDAO;


public class AreaBean {
	public static void getAllArea(){
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = null;
		Connection conn = null;
		try {
			conn = JdbcDAO.getConnection();
			sql = "select * from my_area m start with m.area_id=1000201 connect by prior   m.parent_id=m.area_id";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString(1)+"---"+rs.getString(2));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JdbcDAO.close(rs, pst, conn);
		}
	}
	
	public static void main(String [] args){
		AreaBean.getAllArea();
	}
	
	
	
}
