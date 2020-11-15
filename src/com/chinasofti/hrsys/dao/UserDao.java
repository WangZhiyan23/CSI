package com.chinasofti.hrsys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import com.chinasofti.hrsys.entity.User;
import com.chinasofti.hrsys.entity.WorkTime;
import com.chinasofti.hrsys.utils.PageHelper;

public class UserDao extends BaseDao {
	/**
	 * 验证登录
	 * @param userName
	 * @param userPwd
	 * @return
	 */
	public User checkLogin(String userName, String userPwd) {
		User user = null;
		Connection conn = super.getConnection();
		// statement 和 PreparedStatement
		String sql = "select * from t_user where username=? and userpwd=?";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, userName);
			ps.setString(2, userPwd);
			// 查询操作会返回一个结果集
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setUserName(rs.getString("username"));
				user.setUserId(rs.getInt("userid"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return user;
	}
	
	public int userRegist(User user) {
		Integer result = 0;
		Connection conn = super.getConnection();
		String sql = "insert into t_user (username,userpwd) values(?, ?)";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserPwd());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	public int userWorkTime(WorkTime workTime) {
		Integer result = 0;
		Connection conn = super.getConnection();
		String sql = "insert into t_worktime (userid,facetime) values(?, ?)";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, workTime.getUserId());
			ps.setTimestamp(2, workTime.getFaceTime());;
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	
	/**
	 * 查询全部用户信息
	 */
	public List<User> getAllUserList() {
		// 定义SQL
		String sql = "select * from t_user";
		// 创建数据库连接
		Connection conn = super.getConnection();
		// statement
		PreparedStatement ps = null;
		// resultSet
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			// 遍历结果集
			while(rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("userid"));
				user.setUserName(rs.getString("username"));
				user.setPhoneNo(rs.getString("phoneno"));
				user.setQq(rs.getString("qq"));
				user.setAge(rs.getInt("age"));
				user.setEmail(rs.getString("email"));
				user.setGender(rs.getInt("gender"));
				user.setFaceImage(rs.getString("faceImage"));
				user.setStatus(rs.getInt("status"));
				userList.add(user);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ps.close();
				rs.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return userList;
	}
	
	public int createUser(User user) {
		Integer result = 0;
		Connection conn = super.getConnection();
		String sql = "insert into t_user "
				+ "(username, gender, qq, phoneno, age, email) "
				+ "values(?, ?, ?, ?, ?, ?)";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setInt(2, user.getGender());
			ps.setString(3, user.getQq());
			ps.setString(4, user.getPhoneNo());
			ps.setInt(5, user.getAge());
			ps.setString(6, user.getEmail());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	// 更新用户
	public int updateUser(User user) {
		Integer result = 0;
		Connection conn = super.getConnection();
		String sql = "update t_user "
				+ "set username=?, gender=?, qq=?, phoneno=?, age=?, email=? "
				+ " where userid=?";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setInt(2, user.getGender());
			ps.setString(3, user.getQq());
			ps.setString(4, user.getPhoneNo());
			ps.setInt(5, user.getAge());
			ps.setString(6, user.getEmail());
			ps.setInt(7, user.getUserId());
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	// CV大法，更新人脸数据
	public int updateUserFaceImg(User user) {
		Integer result = 0;
		Connection conn = super.getConnection();
		String sql = "update t_user set faceImage=? where userid=?";
		// 一次会话
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getFaceImage());
			ps.setInt(2, user.getUserId());
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		return result;
	}
	
	
	/**
	 * 查询全部用户信息
	 */
	public List<User> getUserPageList(User user, PageHelper pageHelper) {
		// 定义SQL
		String sql = "select * from t_user where 1=1 "; // 便于添加查询参数
		if(user.getUserName() != null && !user.getUserName().equals("")) {
			sql += " and username like ? ";
		}
		// 算出起始行
		int startRow = (pageHelper.getCurrentPage() - 1) * pageHelper.getPageSize();
		sql += " limit " + startRow + "," + pageHelper.getPageSize();
		// 创建数据库连接
		Connection conn = super.getConnection();
		// statement
		PreparedStatement ps = null;
		// resultSet
		ResultSet rs = null;
		List<User> userList = new ArrayList<User>();
		
		try {
			ps = conn.prepareStatement(sql);
			if(user.getUserName() != null && !user.getUserName().equals("")) {
				ps.setString(1, "%" + user.getUserName() + "%");
			}
			rs = ps.executeQuery();
			// 遍历结果集
			while(rs.next()) {
				User temp = new User();
				temp.setUserId(rs.getInt("userid"));
				temp.setUserName(rs.getString("username"));
				temp.setPhoneNo(rs.getString("phoneno"));
				temp.setQq(rs.getString("qq"));
				temp.setAge(rs.getInt("age"));
				temp.setEmail(rs.getString("email"));
				temp.setGender(rs.getInt("gender"));
				temp.setFaceImage(rs.getString("faceImage"));
				temp.setStatus(rs.getInt("status"));
				userList.add(temp);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ps.close();
				rs.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return userList;
	}
	
	/**
	 * 统计用户的个数
	 */
	public int getUserCount(User user) {
		
		int result = 0;
		// 定义SQL
		String sql = "select count(*) from t_user where 1=1 "; // 便于添加查询参数
		if(user.getUserName() != null && !user.getUserName().equals("")) {
			sql += " and username like ? ";
		}
		
		// 创建数据库连接
		Connection conn = super.getConnection();
		// statement
		PreparedStatement ps = null;
		// resultSet
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement(sql);
			if(user.getUserName() != null && !user.getUserName().equals("")) {
				ps.setString(1, "%" + user.getUserName() + "%");
			}
			rs = ps.executeQuery();
			// 遍历结果集
			if(rs.next()) {
				result = rs.getInt(1);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				ps.close();
				rs.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return result;
	}
	
}
