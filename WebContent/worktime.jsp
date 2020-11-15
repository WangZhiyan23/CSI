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
    <title>刷脸考勤</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta content="刷脸考勤" name="description" />
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
                                                <h3>刷脸考勤</h3>
                                                <h3 id="message" style="color: green;"></h3>
                                                <h3 id="dateMsg" style="color: green;"></h3>
								                 <%  // el jstl
													 String message = (String)request.getAttribute("message");
													 if(message != null){ 
												 %>
													<%=message %>
													<%} %> 
                                            </div>
                                            <video id="video" width="100%" height="300" autoplay></video>
											<canvas id="canvas" width="530" height="300" style="display: none;"></canvas>
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
<script type="text/javascript">
	// 编写加载摄像头的js代码
	faceVideo();
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
 				   "opr": "workTime"},
 			dataType: "json", // 回调时的返回数据类型
 			success: function (json){
 				// 写返回成功，处理代码
 				if(json.message == 1){
 					document.getElementById("message").innerHTML="打开成功"
 					document.getElementById("dateMsg").innerHTML=json.faceTime
 				}else{
 					document.getElementById("message").innerHTML="刷脸失败，你的颜值太高了!"
 					document.getElementById("dateMsg").innerHTML=""
 				}
 			}
 		});
 	}
 	function getBase64() {
 		// base64编码
		var imgSrc = document.getElementById("canvas").toDataURL(
				"image/png");
		// 把二进制的图片信息，转为了base64的字符串
		// alert(imgSrc)
		return imgSrc.split("base64,")[1];// 去掉头

	}
 	setInterval(faceCatch, 10*1000);
</script>
	
</body>

</html>