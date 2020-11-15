<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="assets/js/jquery.min.js"></script>
<script type="text/javascript" src="assets/js/bootstrap.bundle.min.js"></script>
<!-- 添加注册人脸的模态框 -->
<div class="modal fade" id="faceModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">注册人脸</h5>
                <button type="button" class="close waves-effect waves-light" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            	<div style="border:dotted 1px red;">
            		<video id="video" width="100%" height="300" autoplay></video>
					<canvas id="canvas" width="530" height="300" style="display: none;"></canvas>
            	</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary waves-effect waves-light" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary waves-effect waves-light" onclick="faceCatch()">注册</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	// 编写加载摄像头的js代码
	var video = document.getElementById("video"); //获取video标签
	var context = canvas.getContext("2d");
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
 		
 	// 截图保存到画布
 	function faceCatch(){
 		context.drawImage(video, 0, 0, 530, 300);
 		// 提取canvas里的图片
 		base64Img = getBase64() // 图片的数据部分
 		//将base64Img数据存放到数据库
 		// AJAX：原生的XMLHttpRequest这个对象，使用jQuery自带的AJAX库
 		// json：{'key1': value1, 'key2': value2, 'key3':[{"keyx":valuex}]}
 		// javascript：天然支持JSON
 		jsonObj = {userName: 'zhang', money : 99999999, hobbys:['b', 'f', 'p'], keyx: {keyn: 'nnn'}}
 		//alert(jsonObj.userName)
 		// jsonStr 
 		jsonStr = "{\"userName\":\"zhangsan\"}"
 		// 将JSON对象转为JSON字符串：stringify()
 		//alert(JSON.stringify(jsonObj))
 		// 将JSON字符串转变JSON对象
 		//alert(JSON.parse(jsonStr).userName)
 		// $是jQuery对象，异步请求
 		$.ajax({
 			type: "post",  // 请求方法
 			url: "<%=request.getContextPath()%>/face",       // 请求地址, 一个字符串
 			data: {"faceImg": base64Img,
 				   "opr": "regFace"},
 			dataType: "json", // 回调时的返回数据类型
 			success: function (json){
 				// 写返回成功，处理代码
 				if(json.message == 1){
 					alert("人脸注册成功");
 				}else{
 					alert("人脸注册失败");
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

	};
</script>