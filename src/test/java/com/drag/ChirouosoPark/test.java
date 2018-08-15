package com.drag.ChirouosoPark;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.drag.chirouosopark.utils.HttpsUtil;

public class test {


	private static final float MINMONEY =0.01f;  
	private static final float MAXMONEY =200f;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
		test t = new test();
		String appid = "wx8456a262f82aad5b";
		String secret = "f237ea242a14d7e081db9729d025fbc5";
		String url = "https://kyfw.12306.cn/";
		String s = HttpsUtil.httpsRequest("https://api.weixin.qq.com/sns/jscode2session?appid=wx8456a262f82aad5b&secret=f237ea242a14d7e081db9729d025fbc5&js_code=033xrgPv0OPbWi1qWVQv0foROv0xrgPC&grant_type=authorization_code", "GET", null);
//		System.out.println(s);
		
		//https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=ACCESS_TOKEN;
//		String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", appid , secret);
//        JSONObject resultJson =null;
//        String result = HttpsUtil.httpsRequest(requestUrl, "POST", null);
//         try {
//             resultJson = JSON.parseObject(result);
//             String errmsg = (String) resultJson.get("errmsg");
//             if(!"".equals(errmsg) && errmsg != null){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
//                 System.out.println("error");
//             }
//         } catch (JSONException e) {
//             e.printStackTrace();
//         }
//         System.err.println((String) resultJson.get("access_token"));
//		JSONObject json = new JSONObject();
//		
//		json.put("touser", "oBJM95QDwXyPf50xtXzZS7iWA-24");
//		json.put("template_id", "Hgh29cMc2a1URsMyJWfM9mwTsCosbYkIl53o9HW42a8");
//		json.put("page", "pages/index/index");
//		json.put("form_id", "1532157992825");
//		
//		JSONObject keyword1 = new JSONObject();
//		keyword1.put("value", "拼团成功");
//		keyword1.put("color", "#000000");
//		
//		JSONObject data = new JSONObject();
//		data.put("keyword1", keyword1);
//		
//		json.put("data", data);
//		System.out.println(json.toJSONString());
//		System.out.println(t.sendTemplateMsg(json));
//		System.out.println(t.getTemplate());
//		int i = 5;
//		System.out.println(-i);
//		BigDecimal price = new BigDecimal(0.01);
//		BigDecimal num = new BigDecimal(1);
//		BigDecimal totalPrice = price.multiply(num).multiply(new BigDecimal(100));
//	    System.out.println(totalPrice.intValue());
//		try {
//			File cfgFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "apiclient_cert.p12");
//			System.out.println(cfgFile.getAbsolutePath());
//			System.out.println(cfgFile.getPath());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 try {
////			String certPath = cfgFile.getPath();
//			t.getResource();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Date nowTime = new Timestamp(System.currentTimeMillis());
//		Date tTime = new Timestamp();
//		int ptValidhours =1;
		Date d = new Date(12121212);
		int number = 3;
		for(int i = 0;i < number; i++) {
			System.out.println("aaa");
		}
	}
	
	 public void getResource() throws IOException{  
		 java.net.URL path = this.getClass().getResource("/apiclient_cert.p12");
//		 String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		 System.out.println(path);
	 }
	
	 public List getTemplate(){
		 	String token = this.getAccessToken();
	    	String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=%s", token);
	    	JSONObject json = new JSONObject();
			json.put("offset", "0");
			json.put("count", "10");
	        JSONObject resultJson =null;
//	        String result = HttpsUtil.httpsRequest(requestUrl, "POST", json.toString());
	        String result = HttpsUtil.doPost(requestUrl, json.toString(), "utf-8");
	         try {
	             resultJson = JSON.parseObject(result);
	         } catch (JSONException e) {
	             e.printStackTrace();
	         }
	         return (List) resultJson.get("list");
	    }
	
	public boolean sendTemplateMsg(JSONObject json) {
		boolean flag = false;
		String token = this.getAccessToken();
		String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s", token);
//		String result = HttpsUtil.httpsRequest(requestUrl, "POST", json.toJSONString());
		String result = HttpsUtil.doPost(requestUrl, json.toString(), "utf-8");
		if (result != null) {
			JSONObject jsonResult = JSON.parseObject(result);
			int errorCode = jsonResult.getInteger("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				flag = true;
			} else {
				System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				flag = false;
			}
		}
		return flag;
	}
	


	  public String getAccessToken(){
	    	String requestUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", "wx8456a262f82aad5b" , "f237ea242a14d7e081db9729d025fbc5");
	        JSONObject resultJson =null;
	        String result = HttpsUtil.httpsRequest(requestUrl, "GET", null);
	         try {
	             resultJson = JSON.parseObject(result);
	             String errmsg = (String) resultJson.get("errmsg");
	             if(!"".equals(errmsg) && errmsg != null){  
	                 return "error";
	             }
	         } catch (JSONException e) {
	             e.printStackTrace();
	         }
	         return (String) resultJson.get("access_token");
	    }
	    

}

