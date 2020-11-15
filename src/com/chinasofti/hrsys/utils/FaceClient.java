package com.chinasofti.hrsys.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.face.AipFace;     // 就是百度的人脸识别的核心库，在线库，需要申请（没有离线库）
import com.baidu.aip.face.MatchRequest;



public class FaceClient {
	 private static volatile FaceClient faceClient;
	 private AipFace client =null;
	 private FaceClient(String APP_ID, String API_KEY, String SECRET_KEY) {
		 client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
	 }
	 public static FaceClient getInstance(String APP_ID, String API_KEY, String SECRET_KEY) {
       if (faceClient == null) {
           synchronized (FaceClient.class) {
               if (faceClient == null) {
            	   faceClient = new FaceClient(APP_ID, API_KEY, SECRET_KEY);
               }
           }
       }
       return faceClient;
   }
	 
	 public boolean faceContrast(String image1,String image2) {

		    // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
		    MatchRequest req1 = new MatchRequest(image1, "BASE64");
		    MatchRequest req2 = new MatchRequest(image2, "BASE64");
		    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		    requests.add(req1);
		    requests.add(req2);
		    
		    // // 此函数可检测图片中是否有人脸，以及性别、年龄、戴不戴眼镜、颜值等
		    // client.detect(image, imageType, options)
		    
		    JSONObject res = client.match(requests); //  
		    System.out.println(res.toString());
		    Object object=res.get("result");
		    System.out.println("cccc=="+object);
		    if(object==null || object.toString().equals("null")){
		    	return false;
		    }else{
		    	res=(JSONObject) object;
			    double result=res.getDouble("score"); // score里存放了比对结果
			    if(result>=95){
			    	return true;
			    }else{
			    	return false;
			    }
		    }
		    
		}

}
