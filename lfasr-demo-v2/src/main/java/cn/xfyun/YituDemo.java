package cn.xfyun;

import java.io.IOException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.yitu.Result;

import cn.hutool.json.JSONUtil;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * https://speech.yitutech.com/help/?typeP=LONGAUDIO
 * @author zz
 *
 */
public class YituDemo {
	public static void main(String[] args) {
		lasr();
		//query();
	}
	
	public static void lasr() {
		String lasrHost = "​long-asr-prod.yitutech.com";
		String devId = "23037";
		String devKey = "ZTA1MWQ3YWNiZjBkNGFhNmI3YjIxYzg1YzhjYmE3N2MZZZ=";
		Long startTime = System.currentTimeMillis();
        Map<String, String> signature = getSignature(devId, devKey);
        String content = null;
        String path = "https://" + lasrHost + "/v4/lasr";
        MediaType jsonType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Map<String,Object> reqMap = new HashMap<String,Object>();
        reqMap.put("audioUrl", "http://124.220.1.36:8014/audio-server/subtitles/downloadM4aFile?videoboxTaskItemId=195");
        reqMap.put("callback", "http://124.220.1.36:8014/audio-server/subtitles/yituGenerateSubtitlesCallback");
        Map<String,Object> speechConfig = new HashMap<String,Object>();
        speechConfig.put("byWords", true);
        reqMap.put("speechConfig", speechConfig);
       // String jsonStr = new Gson().toJson(reqMap);
        String jsonStr = JSONUtil.toJsonStr(reqMap);
        RequestBody body = RequestBody.create(jsonType,jsonStr);
        System.out.println("请求"+path+" 参数"+jsonStr);
        Request request = new Request.Builder().url(path)
                .addHeader("Content-Type", "application/json;charset=utf8")
                .addHeader("x-dev-id", signature.get("x-dev-id"))
                .addHeader("x-signature", signature.get("x-signature"))
                .addHeader("x-request-send-timestamp", signature.get("x-request-send-timestamp"))
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
            System.out.println("结果"+(System.currentTimeMillis()-startTime)+content);
            Result result =  JSONUtil.toBean(content, Result.class);
            Long queryStartTime =  System.currentTimeMillis();
            while(true) {
            	try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	query(result.getTaskId(),queryStartTime);
            }
            
        } catch (IOException e) {
            //log.error("process error: IOException: ", e);
        	 e.printStackTrace();
        }			
	}
	/**
	 * 请求https://​long-asr-prod.yitutech.com/v4/lasr 参数{"audioUrl":"http://124.220.1.36:8014/audio-server/subtitles/downloadM4aFile?videoboxTaskItemId=195"}
结果{"rtn":0,"message":"SAAS_SUCC","taskId":"7606274f08a74e3db898f2b15f6a2c17","audioId":"7606274f08a74e3db898f2b15f6a2c17","requestId":"8ca8eeb6bcc04912a92be2c29a309df1"}

{"rtn":0,"message":"SAAS_SUCC","requestId":"f68533186dd7489790b4f50387e194ff","taskId":"7606274f08a74e3db898f2b15f6a2c17","data":{"statusCode":2,"statusText":"PROGRESSING","speechResult":null}}
	 */
	public static void query(String taskId,Long startTime) {
		String lasrHost = "​long-asr-prod.yitutech.com";
		String devId = "23037";
		String devKey = "ZTA1MWQ3YWNiZjBkNGFhNmI3YjIxYzg1YzhjYmE3N2M=";
		//String taskId = "7606274f08a74e3db898f2b15f6a2c17";
		//Long startTime = System.currentTimeMillis();
        Map<String, String> signature = getSignature(devId, devKey);
        String content = null;
        String path = "http://" + lasrHost + "/v4/lasr/" + taskId;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path)
                .addHeader("Content-Type", "application/json;charset=utf8")
                .addHeader("x-dev-id", signature.get("x-dev-id"))
                .addHeader("x-signature", signature.get("x-signature"))
                .addHeader("x-request-send-timestamp", signature.get("x-request-send-timestamp"))
                .build();
        try {
            Response response = client.newCall(request).execute();
            content = response.body().string();
            System.out.println("解析用时"+(System.currentTimeMillis()-startTime)+content);
        } catch (IOException e) {
        	e.printStackTrace();
            //log.error("process error: IOException: ", e);
        }		
	}
	
    public static Map<String, String> getSignature(String devId, String devKey) {
        long timestamp = System.currentTimeMillis() / 1000L;
        String signKey = devId + timestamp;

        SecretKeySpec signingKey = new SecretKeySpec(devKey.getBytes(), "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
        } catch (Exception e) {
        	//logger.error("process error: Exception: ", e);
        	 e.printStackTrace();
        }
        byte[] bytes = mac.doFinal(signKey.getBytes());
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        String sig = formatter.toString();

        Map<String, String> map = new HashMap<>();
        map.put("x-dev-id", devId);
        map.put("x-signature", sig);
        map.put("x-request-send-timestamp", String.valueOf(timestamp));
        return map;
    }
}
