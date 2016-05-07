package com.hongqiwei;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public Connection getConn()
	{
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		String url = "jdbc:mysql://115.28.56.175:3306/sensormap?useUnicode=true&characterEncoding=UTF-8";
		try{
			conn = DriverManager.getConnection(url);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeAll(ResultSet rs,Statement stat,Connection conn)
	{
		if(rs!=null)
			try{
				rs.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		if(conn!=null)
			try{
				conn.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
	}

}
