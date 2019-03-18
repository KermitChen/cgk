package com.dyst.dynamicservice.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;


/**
 * 动态勤务数据库连接
 * @author Administrator
 *
 */
public class OracleConnection {
	/**
	 * jdbc获取数据库连接方法
	 */
	public Connection getConnection() {
		//获取相关属性
		Properties pro = Getoracleuser.getProperties();
		String driver =pro.getProperty("jdbc.driverClassName");
		String url = pro.getProperty("jdbc.url");
		String user = pro.getProperty("jdbc.username");
		String password = pro.getProperty("jdbc.password");
		
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭数据库连接方法
	 * 把传递过来的 connection、statement、resultset关闭
	 * @param con
	 * @param st
	 * @param rs
	 */
	public void close(Connection con, Statement st, ResultSet rs){
		try{
			if(rs != null){
				rs.close();
			}
			if(st != null){
				st.close();
			}
			if(con != null){
				con.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}	
}