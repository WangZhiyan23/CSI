<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="top" >				
	<span>欢迎<%=session.getAttribute("userName") %>登录，今天是好天气!</span>
	<span><a href="#" data-toggle="modal" data-target="#faceModal">人脸注册</a></span>
	<span><a href="<%=request.getContextPath()%>/logout">退出</a></span>
</div>