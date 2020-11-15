package com.chinasofti.hrsys.dao;

// java的jdbc库
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 封装底层数据库访问方法
 * @author user
 *
 */
public class BaseDao {
	// 1.数据库访问的url和用户名和密码
	private String dbURL = "jdbc:mysql://localhost:3306/db_hrsys?useUnicode=true&characterEncoding=utf-8";
	private String dbUser = "root";
	private String dbPwd = "root";
	
	/**
	 * 建立数据库连接，返回连接对象
	 * @return
	 */
	public Connection getConnection() {
		// 2.访问数据库需要数据驱动
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 3.调用驱动管理器的 getConnection方法，建立数据库连接
			conn = DriverManager.getConnection(dbURL, dbUser, dbPwd);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
