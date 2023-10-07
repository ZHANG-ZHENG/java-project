package cn.xfyun;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import com.google.gson.Gson;

import cn.hutool.json.JSONUtil;
import cn.xfyun.Ifasrdemo.JsonParse;
import cn.xfyun.bean.Result;
import cn.xfyun.sign.LfasrSignature;
import cn.xfyun.utils.HttpUtil;

public class XfThread extends Thread{
	
		private String AUDIO_FILE_PATH = "C:/Users/Administrator/Desktop/ffmpeg6_mp4_wav/input/test1-audio.mp4";
		private String HOST = "https://raasr.xfyun.cn";
		private String appid = "7aeae7e9";
		private String keySecret = "4c8db480fe27824d4c3fe217fda576de";
		private int threadNum;

		public XfThread(int threadNum) {
			this.threadNum = threadNum;
		}
	
		@Override
		public void run() {
			try {
				Long startTime = System.currentTimeMillis();
				System.out.println(this.threadNum+"线程开始运行");
				
		        HashMap<String, Object> map = new HashMap<>(16);
		        File audio = new File(AUDIO_FILE_PATH);
		        String fileName = audio.getName();
		        long fileSize = audio.length();
		        map.put("appId", appid);
		        map.put("fileSize", fileSize);
		        map.put("fileName", fileName);
		        map.put("duration", "200");
		        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
		        map.put("signa", lfasrSignature.getSigna());
		        map.put("ts", lfasrSignature.getTs());

		        String paramString = HttpUtil.parseMapToPathParam(map);
		        //System.out.println("upload paramString:" + paramString);

		        String url = HOST + "/v2/api/upload" + "?" + paramString;
		        //System.out.println("upload_url:"+ url);
		        String response = HttpUtil.iflyrecUpload(url, new FileInputStream(audio));
		        
		        

		        System.out.println(this.threadNum+"upload response:" + response);
		        Result result = JSONUtil.toBean(response, Result.class);
		        String orderId = "";
		        if("000000".equals(result.getCode())) {
		        	System.out.println(this.threadNum+"上传 用时"+(System.currentTimeMillis()-startTime)+"科大讯飞文件上传回复成功:orderId="+result.getContent().getOrderId());
		        	orderId  =result.getContent().getOrderId();
		        }else {
		        	System.out.println(this.threadNum+"科大讯飞文件上传回复失败");
		        	return;
		        }

		        getResult(orderId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
	
		private void getResult(String orderId) {
			try {
		        Long startTime = System.currentTimeMillis();
		        HashMap<String, Object> map2 = new HashMap<>(16);
		        map2.put("orderId", orderId);
		        LfasrSignature lfasrSignature2 = new LfasrSignature(appid, keySecret);
		        map2.put("signa", lfasrSignature2.getSigna());
		        map2.put("ts", lfasrSignature2.getTs());
		        map2.put("appId", appid);
		        //map.put("resultType", "transfer,predict");//加上predict异常？
		        map2.put("resultType", "transfer");
		        String paramString2 = HttpUtil.parseMapToPathParam(map2);
		        String url2 = HOST + "/v2/api/getResult" + "?" + paramString2;
		        //System.out.println("\nget_result_url:" + url2);
		        while (true) {
		            String response2 = HttpUtil.iflyrecGet(url2);
		            Gson gson = new Gson();
		            JsonParse jsonParse = gson.fromJson(response2, JsonParse.class);
		            //System.out.println("结果:" + response2);
		            if (jsonParse.content.orderInfo.status == 4) {
		                System.out.println(this.threadNum+"解析完成:" +" 用时"+(System.currentTimeMillis()-startTime)+ " " + response2);
		                break;
		            } else if (jsonParse.content.orderInfo.status == -1) {
		                System.out.println(this.threadNum+"订单失败" + response2);
		                break;
		            } else {
		                //System.out.println(this.threadNum+"进行中...，状态为:" + jsonParse.content.orderInfo.status);
		                //建议使用回调的方式查询结果，查询接口有请求频率限制
		                Thread.sleep(30000);
		            }
		        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
