package com.chinasofti.hrsys;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chinasofti.hrsys.dao.BaseDao;
import com.chinasofti.hrsys.dao.UserDao;
import com.chinasofti.hrsys.entity.User;

public class Test {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		BaseDao baseDao = new BaseDao();
		Connection conn = baseDao.getConnection();
		
		// 一次会话
		PreparedStatement ps = conn.prepareStatement("select * from t_user");
		// 查询操作会返回一个结果集
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			System.out.println(rs.getString("username"));
			System.out.println(rs.getString("userpwd"));
		}
		
		conn.close();
		
		UserDao userDao = new UserDao();
		User user = new User();
		user.setUserName("avatar");
		user.setUserPwd("123456");
		Integer result = userDao.userRegist(user);
		
		if(result == 1) {
			System.out.println("注册成功");
		}
		
	}

}
