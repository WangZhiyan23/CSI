package com.chinasofti.hrsys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinasofti.hrsys.dao.UserDao;
import com.chinasofti.hrsys.entity.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login") // 指定访问当前Servlet的URL
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().append("欢迎访问 Servlet!").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 获取参数数据
		String userName = request.getParameter("userName"); 
		String userPwd  = request.getParameter("userPwd");	
		UserDao userDao = new UserDao();
		// 调用dao做数据库比对
		User user = userDao.checkLogin(userName, userPwd);
		// 实现硬编码登录
		if(user != null) {
			// session 会话跟踪技术  http是短连接 sessionid 在服务器在存放用户的登录信息
			
			HttpSession session = request.getSession();
			// 向session中存放用户的登录信息
			session.setAttribute("userName", userName);
			session.setAttribute("user", user);
			
			// 请求转发，可以直接转发到下一个servlet或者jsp
			request.getRequestDispatcher("/user").forward(request, response);
		}else { // 否则登录失败
			request.setAttribute("message", "登录失败，用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
