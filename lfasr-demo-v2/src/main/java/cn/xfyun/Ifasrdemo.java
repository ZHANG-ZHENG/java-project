package cn.xfyun;

import cn.hutool.json.JSONUtil;
import cn.xfyun.sign.LfasrSignature;
import cn.xfyun.utils.HttpUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.security.SignatureException;
import java.util.HashMap;


public class Ifasrdemo {
    private static final String HOST = "https://raasr.xfyun.cn";
    private static String AUDIO_FILE_PATH;
    private static final String appid = "6bb2f543";//35521aaf
    private static final String keySecret = "80c8ba3a59e46ad7ca6ff25b4afe437c";//aa8a37e5bd3260fb80f24fc89d35dc18

    private static final Gson gson = new Gson();

    static {
        try {
            //AUDIO_FILE_PATH = Ifasrdemo.class.getResource("/").toURI().getPath() + "/audio/合成音频.wav";
        	AUDIO_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\testbvn.mp4";//DKHJQ20230814102302171MM70ewgJKR0P6mKF
        	//AUDIO_FILE_PATH = "C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\test1-vn-java.mp4";//DKHJQ20230814102838002LP72Jez2UTexTX65
        } catch (Exception e) {//URISyntaxException
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
//        String result = upload();
//        String jsonStr = StringEscapeUtils.unescapeJavaScript(result);
//        String orderId = String.valueOf(JSONUtil.getByPath(JSONUtil.parse(jsonStr), "content.orderId"));
//        String resp = getResult(orderId);
//        write(resp);
    	for(int i=1;i<=100;i++) {
    		XfThread t = new XfThread(i);
    		t.start();
    	}

    }

    private static String upload() throws SignatureException, FileNotFoundException {
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
        System.out.println("upload paramString:" + paramString);

        String url = HOST + "/v2/api/upload" + "?" + paramString;
        System.out.println("upload_url:"+ url);
        String response = HttpUtil.iflyrecUpload(url, new FileInputStream(audio));

        System.out.println("upload response:" + response);
        return response;
    }

    private static String getResult(String orderId) throws SignatureException, InterruptedException {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("orderId", orderId);
        LfasrSignature lfasrSignature = new LfasrSignature(appid, keySecret);
        map.put("signa", lfasrSignature.getSigna());
        map.put("ts", lfasrSignature.getTs());
        map.put("appId", appid);
        //map.put("resultType", "transfer,predict");//加上predict异常？
        map.put("resultType", "transfer");
        String paramString = HttpUtil.parseMapToPathParam(map);
        String url = HOST + "/v2/api/getResult" + "?" + paramString;
        System.out.println("\nget_result_url:" + url);
        while (true) {
            String response = HttpUtil.iflyrecGet(url);
            JsonParse jsonParse = gson.fromJson(response, JsonParse.class);
            System.out.println("结果:" + response);
            if (jsonParse.content.orderInfo.status == 4) {
                System.out.println("订单完成:" + response);
                return response;
            } else if (jsonParse.content.orderInfo.status == -1) {
                System.out.println("订单失败" + response);
                break;
            } else {
                System.out.println("进行中...，状态为:" + jsonParse.content.orderInfo.status);
                //建议使用回调的方式查询结果，查询接口有请求频率限制
                Thread.sleep(7000);
            }
        }
        return null;
    }

    public static void write(String resp) throws IOException {
    	String orderResult = String.valueOf(JSONUtil.getByPath(JSONUtil.parse(resp), "content.orderResult"));
    	System.out.println("orderResult:"+orderResult);
        //将写入转化为流的形式
        BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\testbvn.txt"));
    	//BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\ffmpeg6_mp4_wav\\input\\test1-vn-java.txt"));
        String ss = resp;
        bw.write(ss);
        //关闭流
        bw.close();
        System.out.println("写入txt成功");
    }

    class JsonParse {
        Content content;
    }

    class Content {
        OrderInfo orderInfo;
    }

    class OrderInfo {
        Integer status;
    }
}
