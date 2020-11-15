package com.chinasofti.hrsys.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chinasofti.hrsys.entity.User;
import com.chinasofti.hrsys.entity.WorkTime;
import com.chinasofti.hrsys.service.UserService;
import com.chinasofti.hrsys.utils.FaceClient;

/**
 * Servlet implementation class FaceController
 */
@WebServlet("/face")
public class FaceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// 这里又三个参数，从百度申请的
	private static String APP_ID = "15949958";
	private static String API_KEY = "fDCBonu1DWRf8FQUt6VrnqER";
	private static String SECRET_KEY = "561cLBZsXcmSGL7tvjqz4GZkGb8rCpjk";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FaceController() {
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
		// 开始写人脸识别相关的代码
		String faceImg = request.getParameter("faceImg");
		String opr     = request.getParameter("opr");
		UserService userService = new UserService();
		
		System.out.println(faceImg);
		String message = "{\"message\": 0}";
		if(opr != null && opr.equals("regFace")) {
			// 将上面的人脸图片字符串写入数据库, 从session中获取当前人脸的注册信息
			User user = (User)request.getSession().getAttribute("user");
			// 写入数据库
			User updateUser = new User();
			updateUser.setUserId(user.getUserId());
			updateUser.setFaceImage(faceImg);
		
			int result = userService.updateUserFaceImg(updateUser);
			if(result == 1) {
				message = "{\"message\": 1}";
			}
		}else if(opr != null && opr.equals("faceLogin")) {
			// 人脸登录
			FaceClient faceClient = FaceClient.getInstance(APP_ID, API_KEY, SECRET_KEY);
			User loginUser = null;
			// 读取所有的用户信息 然后比对
			List<User> userList = userService.getUserList();
			for(User temp : userList) {
				if(temp.getFaceImage() != null) {
					if(faceClient.faceContrast(faceImg, temp.getFaceImage())) {
						loginUser = temp;
						break;
					}
				}
			}
			if(loginUser != null) {
				request.getSession().setAttribute("userName", loginUser.getUserName());
				request.getSession().setAttribute("user", loginUser);
				message = "{\"message\": 1}";
			}
		}else if(opr != null && opr.equals("workTime")) {
			// 人脸登录
			FaceClient faceClient = FaceClient.getInstance(APP_ID, API_KEY, SECRET_KEY);
			User workUser = null;
			// 读取所有的用户信息 然后比对
			List<User> userList = userService.getUserList();
			for(User temp : userList) {
				if(temp.getFaceImage() != null) {
					if(faceClient.faceContrast(faceImg, temp.getFaceImage())) {
						workUser = temp;
						break;
					}
				}
			}
			if(workUser != null) {
				WorkTime workTime = new WorkTime();
				workTime.setUserId(workUser.getUserId());
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
				String nowStr = sdf.format(now);
				workTime.setFaceTime(new Timestamp(now.getTime()));
				int result = userService.userWorkTime(workTime);
				if(result == 1) {
					message = "{\"message\": 1, \"faceTime\":\"" + nowStr +"\"}";
				}
			}
		}
		
		// 返回提示信息
		PrintWriter pw = response.getWriter();
		pw.println(message);
		pw.flush();
		pw.close();
	}

}
