package com.sys.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcDAO {
	public static Connection getConnection(){
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance(); 
			String url="jdbc:oracle:thin:@127.0.0.1:1521:ORCL"; //orcl为数据库的SID 
			String user="system"; 
			String password="system"; 
			Connection conn= DriverManager.getConnection(url,user,password);
			return conn;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return null;
		
	}
	

	  
	  public static void close(ResultSet rs, Statement stmt, Connection conn)
	  {
	    if (rs != null) {
	      try {
	        rs.close();
	      } catch (SQLException ex) {
	        ex.printStackTrace();
	      }
	    }
	    if (stmt != null) {
	      try {
	        stmt.close();
	      } catch (SQLException ex) {
	        ex.printStackTrace();
	      }
	    }
	    if (conn != null)
	      try {
	        conn.close();
	      } catch (SQLException ex) {
	        ex.printStackTrace();
	      }
	  }
}
