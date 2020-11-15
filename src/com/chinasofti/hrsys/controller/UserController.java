package com.chinasofti.hrsys.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.hrsys.entity.User;
import com.chinasofti.hrsys.service.UserService;
import com.chinasofti.hrsys.utils.PageHelper;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 编写用户管理增删改查+分页的实现
		UserService userService = new UserService();
		// 编写相关操作的代码
		String opr = request.getParameter("opr");
		// 分页参数
		String currentPage = request.getParameter("currentPage");
		String pageSize    = request.getParameter("pageSize");
		String searchName    = request.getParameter("userName");
		
		if(currentPage == null || pageSize == null) {
			currentPage = "1";
			pageSize = "10";
		}
		
		PageHelper pageHelper = new PageHelper();
		pageHelper.setCurrentPage(Integer.parseInt(currentPage));
		pageHelper.setPageSize(Integer.parseInt(pageSize));
		
		if(opr != null && opr.equals("add")) {
			// 获取添加表单中的数据项
			String userName = request.getParameter("userName");
			String gender = request.getParameter("gender");
			String qq = request.getParameter("qq");
			String phoneno = request.getParameter("phoneno");
			String age = request.getParameter("age");
			String email = request.getParameter("email");
			
			// 将数据项封装成一个User 对象
			User user = new User();
			user.setUserName(userName);
			user.setGender(Integer.parseInt(gender));
			user.setQq(qq);
			user.setPhoneNo(phoneno);
			user.setEmail(email);
			user.setAge(Integer.parseInt(age));
			
			int result = userService.createUser(user);
			request.setAttribute("result", result);
		}else if(opr != null && opr.equals("update")) {
			// 获取添加表单中的数据项
			String userName = request.getParameter("userName");
			String gender = request.getParameter("gender");
			String qq = request.getParameter("qq");
			String phoneno = request.getParameter("phoneno");
			String age = request.getParameter("age");
			String email = request.getParameter("email");
			String userId = request.getParameter("userId");
			
			// 将数据项封装成一个User 对象
			User user = new User();
			user.setUserName(userName);
			user.setGender(Integer.parseInt(gender));
			user.setQq(qq);
			user.setPhoneNo(phoneno);
			user.setEmail(email);
			user.setAge(Integer.parseInt(age));
			user.setUserId(Integer.parseInt(userId));
			
			int result = userService.updateUser(user);
			request.setAttribute("result", result);
		}
		// 开始实现分页功能 
		User user = new User();
		if(opr != null && opr.equals("search")) {
			user.setUserName(searchName);
			request.setAttribute("searchUser", user);
		}
		List<User> userList = userService.getUserPageList(user, pageHelper);
		pageHelper.setDataList(userList);
		// 统计数据条数计算，总共有多少页
		pageHelper.setTotalCount(userService.getUserCount(user));
		if(pageHelper.getTotalCount() % pageHelper.getPageSize() == 0) {
			pageHelper.setTotalPage(pageHelper.getTotalCount() / pageHelper.getPageSize());
		}else {
			pageHelper.setTotalPage(pageHelper.getTotalCount() / pageHelper.getPageSize() + 1);
		}
		request.setAttribute("pageHelper", pageHelper);
		// 请求转发
		request.getRequestDispatcher("/main.jsp").forward(request, response);
	}

}
