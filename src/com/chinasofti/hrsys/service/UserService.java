package com.chinasofti.hrsys.service;

import java.util.List;

import com.chinasofti.hrsys.dao.UserDao;
import com.chinasofti.hrsys.entity.User;
import com.chinasofti.hrsys.entity.WorkTime;
import com.chinasofti.hrsys.utils.PageHelper;

public class UserService {
	public List<User> getUserList(){
		UserDao userDao = new UserDao();
		return userDao.getAllUserList();
	}
	
	public List<User> getUserPageList(User user, PageHelper pageHelper){
		UserDao userDao = new UserDao();
		return userDao.getUserPageList(user, pageHelper);
	}
	
	public int getUserCount(User user) {
		UserDao userDao = new UserDao();
		return userDao.getUserCount(user);
	}
	
	public int createUser(User user) {
		UserDao userDao = new UserDao();
		return userDao.createUser(user);
	}
	
	public int updateUser(User user) {
		UserDao userDao = new UserDao();
		return userDao.updateUser(user);
	}
	
	public int updateUserFaceImg(User user) {
		UserDao userDao = new UserDao();
		return userDao.updateUserFaceImg(user);
	}
	public int userWorkTime(WorkTime workTime) {
		UserDao userDao = new UserDao();
		return userDao.userWorkTime(workTime);
	}
}
