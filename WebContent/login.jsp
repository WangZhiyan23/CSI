<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// 应用上下文路径
	String path = request.getContextPath(); //  /hrsystest
%> 
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta content="登录" name="description" />
    <meta content="Test" name="author" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />

    <!-- App favicon -->
    <link rel="shortcut icon" href="<%=path%>/assets/images/favicon.ico">

    <!-- App css -->
    <link href="<%=path%>/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/assets/css/theme.min.css" rel="stylesheet" type="text/css" />

</head>

<body class="bg-primary">

    <div>
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="d-flex align-items-center min-vh-100">
                        <div class="w-100 d-block my-5">
                            <div class="row justify-content-center">
                                <div class="col-md-8 col-lg-5">
                                     <div class="card">
                                        <div class="card-body">
                                            <div class="text-center mb-4 mt-3">
                                                <h3>系统登录</h3>
								                 <%  // el jstl
													 String message = (String)request.getAttribute("message");
													 if(message != null){ 
												 %>
													<%=message %>
													<%} %> 
                                            </div>
                                            <form action="<%=path%>/login" method="post" class="p-2">
                                                <div class="form-group">
                                                    <label for="userName">用户名:</label>
                                                    <input class="form-control" name="userName" type="text" id="userName" required placeholder="用户名">
                                                </div>
                                                <div class="form-group">
                                                    <label for="userPwd">密码:</label>
                                                    <input class="form-control" name="userPwd" type="password" required id="userPwd" placeholder="请输入密码">
                                                </div>
            
                                                <div class="form-group mb-4 pb-3">
                                                    <div class="custom-control custom-checkbox checkbox-primary">
                                                        <input type="checkbox" class="custom-control-input" id="checkbox-signin">
                                                        <label class="custom-control-label" for="checkbox-signin">记住账号</label>
                                                    </div>
                                                </div>
                                                <div class="mb-3 text-center">
                                                    <button class="btn btn-primary btn-block" type="submit">登录</button>
                                                    <button class="btn btn-success btn-block" type="button" data-toggle="modal" data-target="#faceModal" onclick="faceVideo()">刷脸登录</button>
                                                </div>
                                            </form>
                                        </div>
                                        <!-- end card-body -->
                                    </div>
                                    <!-- end card -->
                                </div>
                                <!-- end col -->
                            </div>
                            <!-- end row -->
                        </div> <!-- end .w-100 -->
                    </div> <!-- end .d-flex -->
                </div> <!-- end col-->
            </div> <!-- end row -->
        </div>
        <!-- end container -->
    </div>
    <!-- end page -->

    <!-- jQuery  -->
    <script src="<%=path%>/assets/js/jquery.min.js"></script>
    <script src="<%=path%>/assets/js/bootstrap.bundle.min.js"></script>
    <script src="<%=path%>/assets/js/metismenu.min.js"></script>
    <script src="<%=path%>/assets/js/waves.js"></script>
    <script src="<%=path%>/assets/js/simplebar.min.js"></script>

    <!-- App js -->
    <script src="<%=path%>/assets/js/theme.js"></script>

	<!-- 添加注册人脸的模态框 -->
	<div class="modal fade" id="faceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="exampleModalLabel">刷脸登录</h5>
	                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	            	<div style="border:dotted 1px red;">
	            		<video id="video" width="100%" height="300" autoplay></video>	<!--  视频 -->
						<canvas id="canvas" width="530" height="300" style="display: none;"></canvas>   <!-- 画布 -->
	            	</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-secondary waves-effect waves-light" data-dismiss="modal">取消</button>
	                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="faceCatch()">登录</button>
	            </div>
	        </div>
	    </div>
	</div>
	<script type="text/javascript">
		// 编写加载摄像头的js代码
		function faceVideo(){
			var video = document.getElementById("video"); //获取video标签
	
			var con  ={
				audio:false,
				video:true,
				video:{
				width:1980,
				height:1024,
				}
			};	
			
	 		//导航 获取用户媒体对象
			navigator.mediaDevices.getUserMedia(con)
			.then(function(stream){
				try{
					video.src = window.URL.createObjectURL(stream);
				}catch(e){
					video.srcObject=stream;
				}
				
				video.onloadmetadate = function(e){
					video.play();
				}
			});
		}
		 		
	 	// 截图保存到画布
	 	function faceCatch(){
	 		var video = document.getElementById("video"); //获取video标签
	 		var context = canvas.getContext("2d");
	 		context.drawImage(video, 0, 0, 530, 300);
	 		// 提取canvas里的图片
	 		base64Img = getBase64() // 图片的数据部分
	 		
	 		$.ajax({
	 			type: "post",  // 请求方法
	 			url: "<%=request.getContextPath()%>/face",       // 请求地址
	 			data: {"faceImg": base64Img,
	 				   "opr": "faceLogin"},
	 			dataType: "json", // 回调时的返回数据类型
	 			success: function (json){
	 				// 写返回成功，处理代码
	 				if(json.message == 1){
	 					document.location.href="<%=request.getContextPath()%>/user"
	 				}else{
	 					alert("刷脸登录失败");
	 				}
	 			}
	 		});
	 	}
	 	function getBase64() {
	 		// Base64编码
			var imgSrc = document.getElementById("canvas").toDataURL(
					"image/png");
			// 把二进制的图片信息，转为了base64的字符串
			// alert(imgSrc)
			return imgSrc.split("base64,")[1];	// 去掉头

		};

	</script>
 		
</body>

</html>